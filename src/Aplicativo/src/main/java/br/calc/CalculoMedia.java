/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.calc;

import br.jpa.entity.Conta;
import br.jpa.entity.UsuarioConta;
import java.util.Collection;

/**
 *
 * @author Matheus Mollon
 */
public abstract class CalculoMedia {

    public static double calculoEstimativaTotal(Collection<UsuarioConta> usuarioConta) {
        double soma = 0.0;

        for (UsuarioConta uc : usuarioConta) {
            soma += uc.getUCValor();
        }
        return (soma / usuarioConta.size());
    }

}
