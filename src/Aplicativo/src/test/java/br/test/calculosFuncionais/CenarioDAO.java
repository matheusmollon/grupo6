/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.test.calculosFuncionais;

import br.jpa.entity.Conta;
import br.jpa.entity.Produto;
import br.jpa.entity.Usuario;
import br.jpa.entity.UsuarioConta;
import br.jpa.entity.UsuarioContaPK;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus Mollon
 */
public class CenarioDAO {

    private List<Usuario> usuarios;
    private List<UsuarioConta> usuarioConta;
    private List<Produto> produtos;
    private Conta conta;
    private static CenarioDAO cdao;

    private CenarioDAO() {
        this.usuarios = new ArrayList<>();
        this.usuarioConta = new ArrayList<>();
        this.produtos = new ArrayList<>();
        criarCenario();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<UsuarioConta> getUsuarioConta() {
        return usuarioConta;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Conta getConta() {
        return conta;
    }

    public static CenarioDAO getInstancia() {
        if (cdao == null) {
            cdao = new CenarioDAO();
        }
        return cdao;
    }

    public void criarCenario() {
        //Criando 3 usuários 
        Usuario u1 = new Usuario("User1", "1", "43 98684323");
        Usuario u2 = new Usuario("User2", "1", "43 98684323");
        Usuario u3 = new Usuario("User3", "1", "43 98684323");
        usuarios.add(u1);
        usuarios.add(u2);
        usuarios.add(u3);

        //Criando uma conta
        conta = new Conta(100, "Costela", 0.0, u1.getUNome(), 10, true);

        //adicionando usuários a conta
        UsuarioConta uc1 = new UsuarioConta(u1.getUNome(), 100);
        uc1.setConta(conta);
        uc1.setUsuario(u1);
        uc1.setUCValor(0.0);
        usuarioConta.add(uc1);

        UsuarioConta uc2 = new UsuarioConta(u2.getUNome(), 100);
        uc2.setConta(conta);
        uc2.setUsuario(u2);
        uc2.setUCValor(0.0);
        usuarioConta.add(uc2);

        UsuarioConta uc3 = new UsuarioConta(u3.getUNome(), 100);
        uc3.setConta(conta);
        uc3.setUsuario(u3);
        uc3.setUCValor(0.0);
        usuarioConta.add(uc3);
      
        //atribuindo UsuarioConta ao usuario
        List<UsuarioConta> uc1t = new ArrayList<>();
        uc1t.add(uc1);
        u1.setUsuarioContaCollection(uc1t);
        List<UsuarioConta> uc2t = new ArrayList<>();
        uc2t.add(uc2);
        u2.setUsuarioContaCollection(uc2t);
        List<UsuarioConta> uc3t = new ArrayList<>();
        uc3t.add(uc3);
        u3.setUsuarioContaCollection(uc3t);

        //Criando Produtos e atribuindo os usuários
        Produto p1 = new Produto(500, "Batata Frita", 30.00);
        List<Usuario> usersP1 = new ArrayList<>();
        usersP1.add(usuarios.get(0));
        usersP1.add(usuarios.get(2));
        p1.setUsuarioCollection(usersP1);
        p1.setCId(conta);
        produtos.add(p1);

        Produto p2 = new Produto(501, "Frango Empanado", 35.00);
        List<Usuario> usersP2 = new ArrayList<>();
        usersP2.add(usuarios.get(1));
        usersP2.add(usuarios.get(2));
        p2.setCId(conta);
        p2.setUsuarioCollection(usersP2);
        produtos.add(p2);

        Produto p3 = new Produto(502, "Torre de Chopp", 40.00);
        p3.setUsuarioCollection(usuarios);
        p3.setCId(conta);
        produtos.add(p3);

        //Atribuindo os produtos a conta
        conta.setProdutoCollection(produtos);
        conta.setUsuarioContaCollection(usuarioConta);

    }

}
