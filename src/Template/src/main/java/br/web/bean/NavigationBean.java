/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.web.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author hideki
 */
@ManagedBean
@RequestScoped
public class NavigationBean {

    /**
     * Creates a new instance of NavigationBean
     */
    public NavigationBean() {
    }
    
    public String redirecionarCadastroUsuario() {
        return "cadastrarUsuario?faces-redirect=true";
    }
    
    public String redirecionarAtualizarUsuario() {
        return "atualizarUsuario?faces-redirect=true";
    }
    
    public String redirecionarIndex() {
        return "index?faces-redirect=true";
    }
    
    public String redirecionarSistema() {
        return "sistema?faces-redirect=true";
    }
}
