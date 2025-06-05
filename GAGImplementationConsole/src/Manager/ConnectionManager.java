/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import javax.xml.bind.JAXBException;
import object.Msg;
import org.json.JSONException;
import org.json.JSONObject;
import packageConfigFile.Config;
import packageSendReceivePrimitive.Receiverr;
import packageSendReceivePrimitive.SendReceive;
import static packageSendReceivePrimitive.SendReceive.affiche;
import static packageSendReceivePrimitive.SendReceive.getValueItemID;
import static packageSendReceivePrimitive.SendReceive.parcoursArtefactAndSetProvide;
import static packageSendReceivePrimitive.SendReceive.remplaceCurrentByService;
import static packageSendReceivePrimitive.SendReceive.*;
import packageService.Item;
import packageService.Service;
import packageService.Use;
import utils.CopyPasteFile;
import utils.LaunchOpenJMS;
import utils.UpdateFile;

/**
 *
 * @author willyk
 */
public class ConnectionManager {
    
   //ManagerConnection
   public static Service defineInputParam(Service globalInstance, Use currentUse, Msg msg) {
        if(msg != null){
            for (int i = 0; i < globalInstance.getUse().size(); i++) {
                if (globalInstance.getUse().get(i).getServiceName().equals(currentUse.getServiceName())) {
                    //Definir les paramatres en sortie du currentUse dans globalInstance
                    if (msg.getInParameter().size() == globalInstance.getUse().get(i).getInputParameter().getItem().size()) {
                        for (int j = 0; j < globalInstance.getUse().get(i).getInputParameter().getItem().size(); j++) {
                            //Il peut avoir un déphasage, faire un autre parcours dans cette boucle à coté du if
                            String tmp = (String) msg.getInParameter().get(j);
                            //---System.out.println("--- lol  " + tmp);
                            String nameMsg = tmp.split(":")[0];
                            String contentMsg = tmp.split(":")[1];
                            for (int l = 0; l < globalInstance.getUse().get(i).getInputParameter().getItem().size(); l++) {
                                //--System.out.println("Name  "+nameMsg+" celui de currentUse  "+globalInstance.getUse().get(i).getInputParameter().getItem().get(l).getName());
                                if (nameMsg.equals(globalInstance.getUse().get(i).getInputParameter().getItem().get(l).getName())) {
                                    globalInstance.getUse().get(i).getInputParameter().getItem().get(l).setContent(contentMsg);
                                    //--System.out.println(globalInstance.getUse().get(i).getInputParameter().getItem().get(l).getName()+
                                        //--    " SETCONTENT  "+globalInstance.getUse().get(i).getInputParameter().getItem().get(l).getContent());
                                }
                            }
                        }
                    } else
                       //Générons une exception incoherence de type
                       ;
                }
            }
        }
        //else{ //msg == null
            
            for (int i = 0; i < globalInstance.getUse().size(); i++) {
             
                if (globalInstance.getUse().get(i).getServiceName().equals(currentUse.getServiceName())){
                    
                    //InputParameter inp =;
                    for (int l = 0; l <  globalInstance.getUse().get(i).getInputParameter().getItem().size(); l++){
                        if( globalInstance.getUse().get(i).getInputParameter().getItem().get(l).getContent().equals("")){
                             globalInstance.getUse().get(i).getInputParameter().getItem().get(l).setContent(getValueItemID(globalInstance, globalInstance.getUse().get(i).getInputParameter().getItem().get(l).getRefItem()));
                        }
                    }
                }
            }
        //}
        return globalInstance;

    }
    
   //ManagerConnection
   public static Service defineOutputParam(Service globalInstance, Use currentUse, JSONObject msg) throws JSONException {

        for (int i = 0; i < globalInstance.getUse().size(); i++) {
            if (globalInstance.getUse().get(i).getServiceName().equals(currentUse.getServiceName())) {
                System.out.println(">>>>>>>>> \nSimple Message received");
                System.out.println(">>>>>>>>> Operation Name " + currentUse.getServiceName());

                //Definir les paramatres en sortie du currentUse dans globalInstance
                for (int j = 0; j < globalInstance.getUse().get(i).getOutputParameter().getItem().size(); j++) {
                    Object contentMsg = msg.get(globalInstance.getUse().get(i).getOutputParameter().getItem().get(j).getName());
                    if (contentMsg != null) {
                        globalInstance.getUse().get(i).getOutputParameter().getItem().get(j).setContent(contentMsg.toString());

                        System.out.println(">>>>>>>>> OutParam " + j + " Name " + globalInstance.getUse().get(i).getOutputParameter().getItem().get(j).getName() + " : Content " + contentMsg);
                    }
                }
                
                /*Provide provide = globalInstance.getProvide();
                for(int j=0; j<provide.getItem().size(); j++){
                    provide.getItem().get(j).setContent(getValueItemID(globalInstance, provide.getItem().get(j).getRefItem()));
                }
                globalInstance.setProvide(provide);*/
            }
        }
        //--

        return globalInstance;

    }
   
   //ManagerConnection
    public static Service defineOutputParam(Service globalInstance, Use currentUse, Msg msg) throws JAXBException, FileNotFoundException {
        if (msg.isCloseConnection()) {
            LaunchOpenJMS.shutdownJMS(SendReceive.openJMSHOME);
            Config c = RefineAndPersistenceManager.getConfigFile("packageConfigFile", SendReceive.configPath);
            
            int nbQueue = Integer.parseInt(msg.getChannel().split("queue")[1]);
            c.setNumberOfConnection(BigInteger.valueOf(nbQueue));
            RefineAndPersistenceManager.setConfigFile(c, "packageConfigFile", SendReceive.configPath);
            
            System.out.println("Remove " + msg.getChannel() + " Of the Broker\n");
            
            UpdateFile.removeQueue(SendReceive.openJMSConfigPath, msg.getChannel());
            LaunchOpenJMS.startupJMS(SendReceive.openJMSHOME);
        }
        /*List<Use> listUse */ globalInstance.getUse();
        for (int i = 0; i < globalInstance.getUse().size(); i++) {
            if (globalInstance.getUse().get(i).getServiceName().equals(currentUse.getServiceName())) {
                //Definir les paramatres en sortie du currentUse dans globalInstance
                for(int k=0;k<globalInstance.getUse().get(i).getOutputParameter().getItem().size();k++){
                    Item current = (Item) globalInstance.getUse().get(i).getOutputParameter().getItem().get(k);
                    if(SendReceive.isThatContain(current.getName(), msg.getOutParameter()) != null ){
                        String t = (String) SendReceive.isThatContain(current.getName(), msg.getOutParameter());
                        //--System.out.println("Hummm "+t.split(":").length);
                        if(t != null)
                            if(t.split(":").length == 2)
                            globalInstance.getUse().get(i).getOutputParameter().getItem().get(k).setContent(t.split(":")[1]);
                    }
                }
                
                /*Provide provide = globalInstance.getProvide();
                for(int j=0; j<provide.getItem().size(); j++){
                    provide.getItem().get(j).setContent(getValueItemID(globalInstance, provide.getItem().get(j).getRefItem()));
                }
                globalInstance.setProvide(provide);*/
                
            }
        }

        return globalInstance;

    }
    
    
    //ConnectionManager
    public static void receiveData(Msg msgTosend,Config config, Service service,Use currentUse, 
            String serviceFound,int i,String instanceFolder) throws IOException, JAXBException, FileNotFoundException, InterruptedException, JSONException{
        
        Msg receivedMsg = new Msg();
        Receiverr rec = new Receiverr();
        //--System.out.println("pp "+msgTosend.getOperationName());
        while(receivedMsg.getOperationName() == null){
            rec = new Receiverr();
            //--System.out.println("J'ecoute HOST "+config.getHost()+" Channel "+msgTosend.getChannel());
            receivedMsg = rec.receiveObjectMessage(config.getHost(), msgTosend.getChannel());

            //--System.out.println("pp0 "+msgTosend);
        }
        //--System.out.println("Diminue restoreContexte "+restoreContexte.size());
        if(!restoreContexte.isEmpty())
            restoreContexte.remove(restoreContexte.size()-1);
        //--System.out.println("Diminue restoreContexte "+restoreContexte.size());

//--
        System.out.println("\n>>>><<<<<<<<<<<<<<<<<<<<< Message Received1 >>>>>>>>>>>>>>>>>>>>");
        affiche(receivedMsg);
        
        service = ConnectionManager.defineOutputParam(service, currentUse, receivedMsg);
        RefineAndPersistenceManager.setServiceFile(service, "packageService", serviceFound);        
        service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);
        
        //System.out.println("Lool "+re);
        for(int b=0; b< receivedMsg.getOutParameter().size();b++){
            String x = (String)receivedMsg.getOutParameter().get(b);
            String name = x.split(":")[0];
            String content = x.split(":")[1];
            
            for(int n=0; n< currentUse.getOutputParameter().getItem().size();n++)
                if(name.equals(currentUse.getOutputParameter().getItem().get(n).getName()))
                    currentUse.getOutputParameter().getItem().get(n).setContent(content);
        }

        //Ecrire dans instanceFolder/Artefact.txt
        CopyPasteFile.writeAtTheEnd(instanceFolder+"/Artefact.txt", "Receive "+msgTosend.getOperationName()+"\n");
        packageArtefact.Service artefac = RefineAndPersistenceManager.getArtefactFile("packageArtefact", instanceFolder+"/Artefact.xml");
       //---System.out.println("***-* "+currentUse.getOutputParameter().getItem().get(0).getContent());
       //---System.out.println(currentUse.getServiceName()+" || "+service.getName());
       //---System.out.println(receivedMsg.getServiceSolved().getType()+" || "+receivedMsg.getServiceSolved().getServiceName());
       
       if(isArtefactContainUse(artefac,currentUse,service))
            artefac = remplaceCurrentByService(artefac,currentUse,service); 
       else{
           //---System.err.println("ICIIIIIIIIIIIIIIIIIII");
           Service s = tranformServiceObjectToService(receivedMsg.getServiceSolved());
           artefac = remplaceUseByService(artefac,s);
       }
        
        artefac = parcoursArtefactAndSetProvide(artefac,service);
        RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact", instanceFolder+"/Artefact.xml");
        
        if(i == service.getUse().size()-1){
            //--System.out.println("i=listUseSize "+serviceFound+"  Context Size "+restoreContexte.size());
            RefineAndPersistenceManager.treatementProvide(service, receivedMsg, serviceFound);
        }else{//Continuer pardon parceke l'autre est déjà bloqué
            
               for(int l=i+1; l< service.getUse().size(); l++){
                    //---System.out.println("THREAD ID "+Thread.currentThread().getId());
                    System.out.println("Continue with "+service.getUse().get(l).getServiceName()+" service");
                    if(service.getUse().get(l).getType().equals("simple")){

                        RefineAndPersistenceManager.treatmentSimpleService(service, service.getUse().get(l), serviceFound, receivedMsg);

                    }else{
                        SendReceive sr = new SendReceive();
                        sr.treatmentCompositeService(config, service,service.getUse().get(l)
                            ,receivedMsg,serviceFound,l);   
                    }         
                }
        }
        
        //--System.out.println("STOPPED ** "+Thread.currentThread().getId());
        Thread.currentThread().stop();
    }
    
}
