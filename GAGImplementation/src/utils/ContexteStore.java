package utils;


import packageConfigFile.Config;
import packageService.Service;
import packageService.Use;
import object.Msg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author willyk
 */
public class ContexteStore {
    Msg msgTosend;
    
    Config config;
    
    Service service;
    
    Use currentUse; 
    
    String serviceFound;

    public ContexteStore() {
    }
    
    
    public Msg getMsgTosend() {
        return msgTosend;
    }

    public void setMsgTosend(Msg msgTosend) {
        this.msgTosend = msgTosend;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Use getCurrentUse() {
        return currentUse;
    }

    public void setCurrentUse(Use currentUse) {
        this.currentUse = currentUse;
    }

    public String getServiceFound() {
        return serviceFound;
    }

    public void setServiceFound(String serviceFound) {
        this.serviceFound = serviceFound;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getInstanceFolder() {
        return instanceFolder;
    }

    public void setInstanceFolder(String instanceFolder) {
        this.instanceFolder = instanceFolder;
    }
    
    int i;
    
    String instanceFolder;
    
    
}
