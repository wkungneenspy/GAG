/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monitor.client.application.beans.user;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import packageArtefact.Item;

/**
 *
 * @author willyk
 */
@ManagedBean(name = "serviceNode")
@ViewScoped
public class ServiceNode implements Serializable{
    
    String name;
    
    List<Item> inputs;
    
    List<Item> outputs;
    
    boolean isResolved;
    
    boolean isUse;

    boolean isManual;
    
    public ServiceNode() {
    }

//////    public ServiceNode(String name, boolean isResolved, boolean isUse, boolean isManual) {
//////        this.name = name;
//////        this.isResolved = isResolved;
//////        this.isUse = isUse;
//////        this.isManual = isManual;
//////    }

    public ServiceNode(String name, List<Item> inputs, List<Item> outputs, boolean isResolved, boolean isUse, boolean isManual) {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
        this.isResolved = isResolved;
        this.isUse = isUse;
        this.isManual = isManual;
    }
    
    
    
//    public ServiceNode(String name, boolean isResolved) {
//        this.name = name;
//        this.isResolved = isResolved;
//    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsResolved() {
        return isResolved;
    }

    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public boolean isIsUse() {
        return isUse;
    }

    public void setIsUse(boolean isUse) {
        this.isUse = isUse;
    }

    public boolean isIsManual() {
        return isManual;
    }

    public void setIsManual(boolean isManual) {
        this.isManual = isManual;
    }

    public List<Item> getInputs() {
        return inputs;
    }

    public void setInputs(List<Item> inputs) {
        this.inputs = inputs;
    }

    public List<Item> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Item> outputs) {
        this.outputs = outputs;
    }
    
    
}
