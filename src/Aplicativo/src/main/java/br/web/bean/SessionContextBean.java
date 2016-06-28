/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.web.bean;

import br.web.utils.SessionContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author hideki
 */
@ManagedBean
@RequestScoped
public class SessionContextBean {

    /**
     * Creates a new instance of SessionContextBean
     */
    public SessionContextBean() {
    }
    
    public void sistemaSessionContext() {
        SessionContext.getInstance().setSessionAttribute("cId", null);
        SessionContext.getInstance().setSessionAttribute("pId", null);
    }
    
    public void gerenciarContaSessionContext() {
        SessionContext.getInstance().setSessionAttribute("pId", null);
    }
    
}
