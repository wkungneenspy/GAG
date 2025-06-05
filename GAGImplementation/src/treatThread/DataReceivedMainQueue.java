/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package treatThread;

import packageConfigFile.Config;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import packageSendReceivePrimitive.ReceiveSend;
import packageSendReceivePrimitive.SendReceive;
import org.json.JSONException;

/**
 *
 * @author willykk
 */
public class DataReceivedMainQueue extends Thread {
    
    Config config;
    String channel;
    SendReceive sr;
    String configPath;
    String schemaExecution;
    String instance;
    
    String openJMSHOME;
    
    public DataReceivedMainQueue(Config config,String channel,SendReceive sr,String configPath,String schemaExecution,String instance, String openJMSHOME){
        this.config = config;
        this.channel = channel;
        this.sr = sr;
        
        this.configPath = configPath;
        this.schemaExecution = schemaExecution;
        this.instance = instance;
        
        this.openJMSHOME = openJMSHOME;
    }
    public void run(){
        try {
            ReceiveSend rs = new ReceiveSend(configPath,schemaExecution,instance,openJMSHOME);
            rs.receiveSend(config,sr);
        } catch (JAXBException | JSONException | IOException | InterruptedException ex) {
            Logger.getLogger(DataReceivedMainQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
}
