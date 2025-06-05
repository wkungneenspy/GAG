/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author willyk
 */
public class UseObject implements Serializable{

    private static final long serialVersionUID = 1L;
    
    String type;
    String serviceName;
    boolean order;
    String location;
    boolean manually;
    
    List<ItemObject> inputParam;
    
    List<ItemObject> outputParam;

    public UseObject() {
    }

    public UseObject(List<ItemObject> inputParam, List<ItemObject> outputParam) {
        this.inputParam = inputParam;
        this.outputParam = outputParam;
    }

    public List<ItemObject> getInputParam() {
        return inputParam;
    }

    public void setInputParam(List<ItemObject> inputParam) {
        this.inputParam = inputParam;
    }

    public List<ItemObject> getOutputParam() {
        return outputParam;
    }

    public void setOutputParam(List<ItemObject> outputParam) {
        this.outputParam = outputParam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isManually() {
        return manually;
    }

    public void setManually(boolean manually) {
        this.manually = manually;
    }
    
    
}
