package com.monitor.client.application.beans.user;


import java.io.Serializable;
//import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    boolean logged;
    
    boolean loggedAdmin;
    
    String role;
    
    @PostConstruct
    public void init(){
        role = "Generale";
    }
    
    public void navConnexion(){
    
    }
    
    public void disconnect(){
        
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLoggedAdmin() {
        return loggedAdmin;
    }

    public void setLoggedAdmin(boolean loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void onRowSelectEvent(SelectEvent event) {
//        EvenementBean evenement = (EvenementBean) event.getObject();
//
//        FacesMessage msg = new FacesMessage("Evenement Selected", ((EvenementBean) event.getObject()).getIdEven() + "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//
//        idEvent = evenement.getIdEven();
    }

    public void onRowUnselectEvent(UnselectEvent event) {
//        FacesMessage msg = new FacesMessage("Document Unselected", ((EvenementBean) event.getObject()).getIdEven() + "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDbselectEvent(SelectEvent event) {
//        FacesMessage msg = new FacesMessage("Document DB Selected", ((EvenementBean) event.getObject()).getIdEven() + "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
