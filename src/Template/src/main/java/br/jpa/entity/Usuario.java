/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hideki
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUnome", query = "SELECT u FROM Usuario u WHERE u.unome = :unome"),
    @NamedQuery(name = "Usuario.findByUsenha", query = "SELECT u FROM Usuario u WHERE u.usenha = :usenha"),
    @NamedQuery(name = "Usuario.findByUcelular", query = "SELECT u FROM Usuario u WHERE u.ucelular = :ucelular")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "unome")
    private String unome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usenha")
    private String usenha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ucelular")
    private String ucelular;

    public Usuario() {
    }

    public Usuario(String unome) {
        this.unome = unome;
    }

    public Usuario(String unome, String usenha, String ucelular) {
        this.unome = unome;
        this.usenha = usenha;
        this.ucelular = ucelular;
    }

    public String getUnome() {
        return unome;
    }

    public void setUnome(String unome) {
        this.unome = unome;
    }

    public String getUsenha() {
        return usenha;
    }

    public void setUsenha(String usenha) {
        this.usenha = usenha;
    }

    public String getUcelular() {
        return ucelular;
    }

    public void setUcelular(String ucelular) {
        this.ucelular = ucelular;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unome != null ? unome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.unome == null && other.unome != null) || (this.unome != null && !this.unome.equals(other.unome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.entity.Usuario[ unome=" + unome + " ]";
    }
    
}
