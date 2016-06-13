/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.web.bean;

import br.jpa.controller.UsuarioJpaController;
import br.jpa.entity.Usuario;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hideki
 */
@ManagedBean
@SessionScoped
public class AcessoBean {
    
    private Usuario usuario;

    /**
     * Creates a new instance of AcessoBean
     */
    public AcessoBean() {
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void validarUnomeUsenha() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TemplatePU");
        UsuarioJpaController ujc = new UsuarioJpaController(emf);
        boolean valid = true;

        Usuario usuarioBanco = ujc.findUsuario(usuario.getUnome());

        if (usuarioBanco == null || !usuarioBanco.getUsenha().equals(usuario.getUsenha())) {
            valid = false;
        }

        if (valid) {
            usuario.setUcelular(usuarioBanco.getUcelular());
            
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Template/faces/sistema.xhtml");
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Nome de usuário ou senha incorretos",
                            "Erro de acesso!"));
        }
    }
    
    public String logout() {        
        return "index?faces-redirect=true";
    }
}
