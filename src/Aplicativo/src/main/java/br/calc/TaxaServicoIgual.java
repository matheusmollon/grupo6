/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.calc;

import br.jpa.entity.Conta;
import br.jpa.entity.UsuarioConta;

/**
 *
 * @author Felipe
 */
public class TaxaServicoIgual extends CalculoValores {

    @Override
    public Conta calculoTaxaServico(Conta conta) {
        
        double taxaTotal = (conta.getCValor() * conta.getCTaxaServico()) / 100;
        double taxaFatia = taxaTotal / conta.getUsuarioContaCollection().size();
        
        conta.setCValor(conta.getCValor() + taxaTotal);
        
        for (UsuarioConta usuarioConta : conta.getUsuarioContaCollection()) {
            usuarioConta.setUCValor(usuarioConta.getUCValor() + taxaFatia);
        }
        
        return conta;
    }
    
}
