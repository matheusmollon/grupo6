/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.web.bean;

import br.calc.CalculoEstimativa;
import br.calc.CalculoValores;
import br.calc.TaxaServicoIgual;
import br.calc.TaxaServicoPorConsumo;
import br.jpa.controller.ContaJpaController;
import br.jpa.controller.UsuarioContaJpaController;
import br.jpa.controller.UsuarioJpaController;
import br.jpa.controller.exceptions.IllegalOrphanException;
import br.jpa.controller.exceptions.NonexistentEntityException;
import br.jpa.entity.Conta;
import br.jpa.entity.Usuario;
import br.jpa.entity.UsuarioConta;
import br.jpa.entity.UsuarioContaPK;
import br.web.utils.SessionContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Felipe
 */
@ManagedBean
@RequestScoped
public class ContaBean {

    private Conta conta;
    private UsuarioConta usuarioConta;
    private String divisao;

    public ContaBean() {
        this.conta = new Conta();
        this.usuarioConta = new UsuarioConta();
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public UsuarioConta getUsuarioConta() {
        return usuarioConta;
    }

    public void setUsuarioConta(UsuarioConta usuarioConta) {
        this.usuarioConta = usuarioConta;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    public Usuario getUsuarioSession() {
        return UsuarioJpaController.getInstance().findUsuario(SessionContext.getInstance().getSessionAttribute("uNome").toString());
    }

    public Conta getContaSession() {
        return ContaJpaController.getInstance().findConta((int) SessionContext.getInstance().getSessionAttribute("cId"));
    }

    public boolean renderButton(UsuarioConta usuarioConta) {
        if (usuarioConta.getConta().getCGerente().equals(usuarioConta.getUsuarioContaPK().getUNome())) {
            return false;
        }

        return true;
    }

    public void cadastrarConta() {
        this.conta.setCValor(0.00);
        this.conta.setCTaxaServico(0);
        this.conta.setCAberto(true);
        this.conta.setCGerente(SessionContext.getInstance().getSessionAttribute("uNome").toString());
        ContaJpaController.getInstance().create(conta);

        this.usuarioConta.setUsuario(this.getUsuarioSession());
        this.usuarioConta.setConta(conta);
        this.usuarioConta.setUCValor(0.00);
        try {
            UsuarioContaJpaController.getInstance().create(usuarioConta);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na criação de nova conta!", "Falha na criação de nova conta!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(ex.toString());
        }
    }

    public void atualizarNomeConta() {
        Conta contaAtualizada = this.getContaSession();
        contaAtualizada.setCNome(this.conta.getCNome());

        try {
            ContaJpaController.getInstance().edit(contaAtualizada);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/gerenciar_conta.xhtml");
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na atualização da conta!", "Falha na atualização da conta!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(ex.toString());
        }
    }

    public void excluirConta() {
        Conta contaExcluida = this.getContaSession();
        boolean excluir = true;

        for (UsuarioConta usuarioConta : contaExcluida.getUsuarioContaCollection()) {
            if (usuarioConta.getUCValor() != 0) {
                excluir = false;
            }
        }

        if (excluir && contaExcluida.getUsuarioContaCollection().size() == 1) {
            try {
                UsuarioContaPK usuarioContaPK = new UsuarioContaPK(contaExcluida.getCGerente(), contaExcluida.getCId());
                UsuarioContaJpaController.getInstance().destroy(usuarioContaPK);
                ContaJpaController.getInstance().destroy((int) SessionContext.getInstance().getSessionAttribute("cId"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/sistema.xhtml");
            } catch (IllegalOrphanException | NonexistentEntityException | IOException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na exclusão da conta!", "Falha na exclusão da conta!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contas com usuarios consumindo produtos ou mais de um usuário não podem ser excluidas!", "Contas com usuarios consumindo produtos não podem ser excluidas!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void tornarGerente(UsuarioConta usuarioConta) {
        Conta contaNovoGerente = this.getContaSession();
        contaNovoGerente.setCGerente(usuarioConta.getUsuarioContaPK().getUNome());
        System.out.println(contaNovoGerente.getCGerente());
        try {
            ContaJpaController.getInstance().edit(contaNovoGerente);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/visualizar_conta.xhtml");
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao substituir gerente!", "Falha ao substituir gerente!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(ex.toString());
        }
    }
    
    public double estimativaTotal() {   
        return CalculoEstimativa.calculoEstimativaTotal(ContaJpaController.getInstance().findContaPorNome(this.getContaSession().getCNome()));
    }
    
    public double estimativaIndividual() {
        Collection<UsuarioConta> usuarioContas = new ArrayList<>();
        Usuario usuario = this.getUsuarioSession();
        Conta contaAux = this.getContaSession();
        for (UsuarioConta usuarioConta1 : usuario.getUsuarioContaCollection()) {
            if(usuarioConta1.getConta().getCNome().equals(contaAux.getCNome()) && usuarioConta1.getUCValor() != 0.0) {
                usuarioContas.add(usuarioConta1);
            }
        }
        
        return CalculoEstimativa.calculoEstimativaIndividual(usuarioContas);
    }

    public void redirectFecharConta() {
        Conta contaFechada = this.getContaSession();

        if (contaFechada.getCValor() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conta sem consumo não pode ser fechada!", "Conta sem consumo não pode ser fechada!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/fechar_conta.xhtml");
            } catch (IOException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fechar conta!", "Erro ao fechar conta!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.out.println(ex.toString());
            }
        }
    }

    public void fecharConta() {
        Conta contaFechada = this.getContaSession();
        contaFechada.setCTaxaServico(this.conta.getCTaxaServico());
        contaFechada.setCAberto(false);

        try {
            ContaJpaController.getInstance().edit(contaFechada);
            this.atualizarValores(contaFechada);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/visualizar_conta.xhtml");
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao fechar a conta!", "Falha ao fechar a conta!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(ex.toString());
        }
    }

    private void atualizarValores(Conta contaAtualizada) {
        CalculoValores calculoValores;

        if (this.divisao.equals("igual")) {
            calculoValores = new TaxaServicoIgual();
        } else if (this.divisao.equals("por consumo")) {
            calculoValores = new TaxaServicoPorConsumo();
        } else {
            calculoValores = null;
        }

        if (calculoValores != null) {
            contaAtualizada = calculoValores.atualizarValores(this.getContaSession());

            try {
                for (UsuarioConta usuarioConta : contaAtualizada.getUsuarioContaCollection()) {
                    UsuarioContaJpaController.getInstance().edit(usuarioConta);
                }

                ContaJpaController.getInstance().edit(contaAtualizada);
            } catch (Exception ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na atualização da conta!", "Falha na atualização da conta!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.out.println(ex.toString());
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha na atualização da conta!", "Falha na atualização da conta!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
