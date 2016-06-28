/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.test.calculosFuncionais;

import br.calc.CalculoValores;
import br.calc.TaxaServicoPorConsumo;
import br.jpa.entity.Conta;
import br.jpa.entity.Produto;
import br.jpa.entity.UsuarioConta;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matheus Mollon
 */
public class calculoPorPessoaTeste {

    public calculoPorPessoaTeste() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public Conta getContaAtualizada() {
        CenarioDAO cdao = CenarioDAO.getInstancia();
        Conta c = cdao.getConta();
        log(c);
        CalculoValores cv = new TaxaServicoPorConsumo();
        Conta cAtualizada = cv.atualizarValores(c);
        log(cAtualizada);
        return cAtualizada;
    }

    public void log(Conta c) {
        System.out.println("TESTE:");
        System.out.println("conta gerente:" + c.getCGerente() + "nome " + c.getCNome() + "porc: " + c.getCTaxaServico() + " aberta: " + c.getCAberto() + "ID: " + c.getCId() + "Total de usuarios: " + c.getUsuarioContaCollection().size() + "Total de Produtos: " + c.getProdutoCollection().size());
        System.out.println("Usuários: ");
        for(UsuarioConta u: c.getUsuarioContaCollection()){
            System.out.println("user: "+u.getUsuario().getUNome());
            System.out.println("Conta:"+u.getConta().getCId());
            System.out.println("Valor"+u.getUCValor());
        }
        System.out.println("Produtos: ");
        for(Produto p: c.getProdutoCollection()){
            System.out.println("Produto: "+p.getPNome());
            System.out.println("Valor:"+p.getPValor());
            System.out.println("users"+p.getUsuarioCollection().size());
        }
    }

    @Test
    public void calculoPorPessoa1() {
        Conta c = getContaAtualizada();
        System.out.println("conta: " + c.getCNome());
        List<UsuarioConta> ucs = (List<UsuarioConta>) c.getUsuarioContaCollection();
        UsuarioConta uc = null;
        for (UsuarioConta uc1 : ucs) {
            if (uc1.getUsuario().getUNome().equals("User1")) {
                uc = uc1;
            }
        }
        assertEquals(31.16, uc.getUCValor(), 0.01);

    }

    @Test
    public void calculoPorPessoa2() {
        Conta c = getContaAtualizada();
        List<UsuarioConta> ucs = (List<UsuarioConta>) c.getUsuarioContaCollection();
        UsuarioConta uc = null;
        for (UsuarioConta uc1 : ucs) {
            if (uc1.getUsuario().getUNome().equals("User2")) {
                uc = uc1;
            }
        }
        assertEquals(33.91, uc.getUCValor(), 0.01);

    }

    @Test
    public void calculoPorPessoa3() {
        Conta c = getContaAtualizada();
        List<UsuarioConta> ucs = (List<UsuarioConta>) c.getUsuarioContaCollection();
        UsuarioConta uc = null;
        for (UsuarioConta uc1 : ucs) {
            if (uc1.getUsuario().getUNome().equals("User3")) {
                uc = uc1;
            }
        }
        assertEquals(50.42, uc.getUCValor(), 0.01);

    }

    @Test
    public void CalculoPorPessoaComparandoAsPartesComValorTotal() {
        Conta c = getContaAtualizada();
        List<UsuarioConta> ucs = (List<UsuarioConta>) c.getUsuarioContaCollection();
        double valorSomadoUsuario = 0.0;
        for (UsuarioConta uc : ucs) {
            valorSomadoUsuario += uc.getUCValor();
        }
        System.out.println("VaLOR SOMADO: "+valorSomadoUsuario);
        assertEquals(c.getCValor(), valorSomadoUsuario, 0.01);
    }
}
