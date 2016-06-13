/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.web.bean;

import br.jpa.entity.Usuario;
import br.jpa.controller.UsuarioJpaController;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hideki
 */
@ManagedBean
@RequestScoped
public class UsuarioBean {

    private Usuario usuario;

    @NotNull
    @Size(min = 1, max = 30)
    private String confirmaSenha;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public void cadastrarUsuario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TemplatePU");
        UsuarioJpaController ujc = new UsuarioJpaController(emf);

        if (usuario.getUsenha().equals(confirmaSenha)) {
            try {
                ujc.create(usuario);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Template/faces/index.xhtml");
            } catch (Exception ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no cadastro!", "Falha no cadastro!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.out.println(ex.toString());
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senhas não coincidem!", "Falha no cadastro!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
