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
 * @author hideki
 */
public abstract class CalculoEstimativa {

    public static double calculoEstimativaTotal(Collection<Conta> contas) {
        double soma = 0.0;
        int qtde = contas.size();

        if (qtde > 0) {
            for (Conta conta : contas) {
                soma += conta.getCValor();
            }

            return soma / qtde;
        } else { 
            return 0.0;
        }
    }

    public static double calculoEstimativaIndividual(Collection<UsuarioConta> usuarioContas) {
        double soma = 0.0;
        int qtde = usuarioContas.size();

        if (qtde > 0) {
            for (UsuarioConta usuarioConta : usuarioContas) {
                soma += usuarioConta.getUCValor();
            }

            return soma / qtde;
        } else {
            return 0.0;
        }
    }
}
