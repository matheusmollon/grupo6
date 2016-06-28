/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.web.bean;

import br.calc.CalculoValores;
import br.calc.TaxaServicoIgual;
import br.jpa.controller.ContaJpaController;
import br.jpa.controller.ProdutoJpaController;
import br.jpa.controller.UsuarioContaJpaController;
import br.jpa.controller.UsuarioJpaController;
import br.jpa.controller.exceptions.NonexistentEntityException;
import br.jpa.entity.Conta;
import br.jpa.entity.Produto;
import br.jpa.entity.Usuario;
import br.jpa.entity.UsuarioConta;
import br.web.utils.SessionContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Matheus Mollon
 */
@ManagedBean
@RequestScoped
public class ProdutoBean {

    private Produto produto;
    private String[] selecionados;

    public ProdutoBean() {
        if (SessionContext.getInstance().getSessionAttribute("pId") == null) {
            this.produto = new Produto();
        } else {
            this.produto = this.getProdutoSession();
            this.selecionados = new String[this.produto.getUsuarioCollection().size()];

            int i = 0;
            for (Usuario usuario : this.produto.getUsuarioCollection()) {
                selecionados[i] = usuario.getUNome();
                i++;
            }
        }
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String[] getSelecionados() {
        return selecionados;
    }

    public void setSelecionados(String[] selecionados) {
        this.selecionados = selecionados;
    }

    public Produto getProdutoSession() {
        return ProdutoJpaController.getInstance().findProduto((int) SessionContext.getInstance().getSessionAttribute("pId"));
    }

    public void cadastrarProduto() {
        Collection<Usuario> pUsuarios = new ArrayList<>();
        for (int i = 0; i < this.selecionados.length; i++) {
            pUsuarios.add(UsuarioJpaController.getInstance().findUsuario(selecionados[i]));
        }

        this.produto.setCId(ContaJpaController.getInstance().findConta((int) SessionContext.getInstance().getSessionAttribute("cId")));
        this.produto.setUsuarioCollection(pUsuarios);

        boolean resposta = ProdutoJpaController.getInstance().create(this.produto);
        if (resposta) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto: \""+produto.getPNome()+"\" armazenado com sucesso", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto: \""+produto.getPNome()+" \" não armazenado", ""));
        }
        this.atualizarValores();
    }

    public void excluirProduto(int pId) {
        try {
            boolean resposta = ProdutoJpaController.getInstance().destroy(pId);
            if (resposta) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto removido com sucesso", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto não foi removido", ""));
            }

            this.atualizarValores();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarProduto(int pId) {
        SessionContext.getInstance().setSessionAttribute("pId", pId);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/editar_produto.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizarProduto() {
        Collection<Usuario> pUsuarios = new ArrayList<>();
        for (int i = 0; i < this.selecionados.length; i++) {
            pUsuarios.add(UsuarioJpaController.getInstance().findUsuario(selecionados[i]));
        }

        this.produto.setUsuarioCollection(pUsuarios);

        try {
            ProdutoJpaController.getInstance().edit(this.produto);
            this.atualizarValores();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Aplicativo/faces/gerenciar_conta.xhtml");
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao editar produto!", "Falha ao editar produto!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(ex.toString());
        }
    }

    private void atualizarValores() {
        Conta contaAtualizada = new TaxaServicoIgual().atualizarValores(ContaJpaController.getInstance().findConta((int) SessionContext.getInstance().getSessionAttribute("cId")));

        try {
            for (UsuarioConta usuarioConta : contaAtualizada.getUsuarioContaCollection()) {
                UsuarioContaJpaController.getInstance().edit(usuarioConta);
            }

            ContaJpaController.getInstance().edit(contaAtualizada);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

}
