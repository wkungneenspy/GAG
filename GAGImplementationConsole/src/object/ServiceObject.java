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
public class ServiceObject implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    String serviceName;
    
    String type;
    
    List<ItemObject> listContext;
    
    List<UseObject> listUse;
    
    List<ItemObject> listProvid;

    public ServiceObject() {
    }

    public ServiceObject(String serviceName, List<ItemObject> listContext, List<UseObject> listUse, List<ItemObject> listProvid) {
        this.serviceName = serviceName;
        this.listContext = listContext;
        this.listUse = listUse;
        this.listProvid = listProvid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<ItemObject> getListContext() {
        return listContext;
    }

    public void setListContext(List<ItemObject> listContext) {
        this.listContext = listContext;
    }

    public List<UseObject> getListUse() {
        return listUse;
    }

    public void setListUse(List<UseObject> listUse) {
        this.listUse = listUse;
    }

    public List<ItemObject> getListProvid() {
        return listProvid;
    }

    public void setListProvid(List<ItemObject> listProvid) {
        this.listProvid = listProvid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
