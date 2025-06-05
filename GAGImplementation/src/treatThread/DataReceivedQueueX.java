/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package treatThread;

import Manager.ConnectionManager;
import packageConfigFile.Config;
import packageService.Service;
import packageService.Use;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import packageSendReceivePrimitive.SendReceive;
import object.Msg;
import org.json.JSONException;

/**
 *
 * @author willyk
 */
public class DataReceivedQueueX extends Thread{
    Msg msgTosend;
    Config config;
    Service service;
    Use currentUse; 
    String serviceFound;
    int i;
    String instanceFolder;
    
    String configPath; //= "/home/willyk/config.xml"
    String openJMSConfigPath; // = "/home/willyk/dev/openjms-0.7.7-beta-1/config/openjms.xml";
    String schemaExecution;
    String instance;
    String openJMSHOME;
    
    /*public DataReceivedQueueX(Msg msgTosend, Config config, Service service,
    Use currentUse, String serviceFound, int i,String instanceFolder){
    this.msgTosend = msgTosend;
    this.config = config;
    this.service = service;
    this.currentUse = currentUse;
    this.instanceFolder = instanceFolder;
    this.serviceFound = serviceFound;
    this.i = i;
    }*/
    public DataReceivedQueueX(Msg msgTosend, Config config, Service service, Use currentUse, String serviceFound, int i, String instanceFolder, String configPath, String openJMSConfigPath, String schemaExecution, String instance, String openJMSHOME) {
        this.msgTosend = msgTosend;
        this.config = config;
        this.service = service;
        this.currentUse = currentUse;
        this.serviceFound = serviceFound;
        this.i = i;
        this.instanceFolder = instanceFolder;
        this.configPath = configPath;
        this.openJMSConfigPath = openJMSConfigPath;
        this.schemaExecution = schemaExecution;
        this.instance = instance;
        this.openJMSHOME = openJMSHOME;
    }
    
    
    
    public void run(){
        try {
            SendReceive rs = new SendReceive(configPath, openJMSConfigPath, schemaExecution, instance, openJMSHOME);
            try {
                ConnectionManager.receiveData(msgTosend, config, service, currentUse
                        , serviceFound, i,instanceFolder);
            } catch (    FileNotFoundException | InterruptedException | JSONException ex) {
                Logger.getLogger(DataReceivedQueueX.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(DataReceivedQueueX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] arg){
        
    }
}
