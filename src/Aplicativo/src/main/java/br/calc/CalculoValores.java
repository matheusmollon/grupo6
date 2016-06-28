/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.calc;

import br.jpa.entity.Conta;
import br.jpa.entity.Produto;
import br.jpa.entity.Usuario;
import br.jpa.entity.UsuarioConta;
import java.util.Objects;

/**
 *
 * @author hideki
 */
public abstract class CalculoValores {
    
    public final Conta atualizarValores(Conta conta) {
        conta = zerarValores(conta);
        conta = calculoValorTotal(conta);
        conta = calculoValorIndividual(conta);
        conta = calculoTaxaServico(conta);
        
        return conta;
    }
    
    public Conta zerarValores(Conta conta) {
        
        conta.setCValor(0.0);
        
        for (UsuarioConta usuarioConta : conta.getUsuarioContaCollection()) {
            usuarioConta.setUCValor(0.0);
        }
        
        return conta;
    }
    public Conta calculoValorTotal(Conta conta) {        
        
        for (Produto produto : conta.getProdutoCollection()) {
            conta.setCValor(conta.getCValor() + produto.getPValor());
        }
        
        return conta;
    } 
    
    public Conta calculoValorIndividual(Conta conta) {
        
        for (Produto produto : conta.getProdutoCollection()) {
            double fatia = produto.getPValor() / produto.getUsuarioCollection().size();
            
            for (Usuario usuario : produto.getUsuarioCollection()) {
                for (UsuarioConta usuarioConta : usuario.getUsuarioContaCollection()) {
                    if (Objects.equals(usuarioConta.getConta().getCId(), conta.getCId())) {
                        usuarioConta.setUCValor(usuarioConta.getUCValor() + fatia);
                    }
                }
            }
        }        
        
        return conta;
    }
    
    public abstract Conta calculoTaxaServico(Conta conta);
    
}
