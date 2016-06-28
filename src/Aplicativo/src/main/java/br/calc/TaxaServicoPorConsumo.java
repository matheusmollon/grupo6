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
public class TaxaServicoPorConsumo extends CalculoValores {

    @Override
    public Conta calculoTaxaServico(Conta conta) {
        
        conta.setCValor(conta.getCValor() + ((conta.getCValor() * conta.getCTaxaServico()) / 100));
        
        for (UsuarioConta usuarioConta : conta.getUsuarioContaCollection()) {
            usuarioConta.setUCValor(usuarioConta.getUCValor() + ((usuarioConta.getUCValor() * conta.getCTaxaServico()) / 100));
        }
        
        return conta;
    }
    
}
