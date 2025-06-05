/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import object.Msg;
import object.MsgSimple;
import object.TreatementServiceObject;
import org.json.JSONException;
import org.json.JSONObject;
import packageConfigFile.Config;
import packageSendReceivePrimitive.Receiverr;
import packageSendReceivePrimitive.SendReceive;
import static packageSendReceivePrimitive.SendReceive.affiche;
import static packageSendReceivePrimitive.SendReceive.configPath;
import static packageSendReceivePrimitive.SendReceive.getValueItemID;
import static packageSendReceivePrimitive.SendReceive.instance;
import static packageSendReceivePrimitive.SendReceive.instanceFolder;
import static packageSendReceivePrimitive.SendReceive.openJMSConfigPath;
import static packageSendReceivePrimitive.SendReceive.openJMSHOME;
import static packageSendReceivePrimitive.SendReceive.parcoursArtefactAndSetProvide;
import static packageSendReceivePrimitive.SendReceive.remplaceCurrentByService;
import static packageSendReceivePrimitive.SendReceive.restoreContexte;
import static packageSendReceivePrimitive.SendReceive.schemaExecution;
import static packageSendReceivePrimitive.SendReceive.tranformServiceToArtefact;
import packageSendReceivePrimitive.Senderr;
import packageService.Item;
import packageService.Provide;
import packageService.Service;
import packageService.Use;
import treatThread.DataReceivedMainQueue;
import utils.ContexteStore;
import utils.CopyPasteFile;
import utils.LaunchOpenJMS;
import utils.RestService;
import utils.UpdateFile;

/**
 *
 * @author willyk
 */
public class RefineAndPersistenceManager {
    
    //RefineArtefactAndPersitenceManager
    public static Service getServiceFile(String packag, String configFileURL) throws JAXBException {
        JAXBContext jc1 = JAXBContext.newInstance(packag);
        Unmarshaller unmarshaller1 = jc1.createUnmarshaller();
        Service serv = (Service) unmarshaller1.unmarshal(new File(configFileURL));

        return serv;
    }

    //RefineArtefactAndPersitenceManager
    public static void setServiceFile(Service serv, String packag, String configFileURL) throws JAXBException, FileNotFoundException {

        JAXBContext jc = JAXBContext.newInstance(packag);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

        marshaller.marshal(serv, new FileOutputStream(configFileURL));

    }

    //RefineArtefactAndPersitenceManager
    public static void setConfigFile(Config config, String packag, String configFileURL) throws JAXBException, FileNotFoundException {

        JAXBContext jc = JAXBContext.newInstance(packag);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

        marshaller.marshal(config, new FileOutputStream(configFileURL));

    }

    //RefineArtefactAndPersitenceManager
    public static Config getConfigFile(String packag, String configFileURL) throws JAXBException {
        JAXBContext jc1 = JAXBContext.newInstance(packag);
        Unmarshaller unmarshaller1 = jc1.createUnmarshaller();
        Config config = (Config) unmarshaller1.unmarshal(new File(configFileURL));

        return config;
    }

    //RefineArtefactAndPersitenceManager
    public static packageArtefact.Service getArtefactFile(String packag, String configFileURL) throws JAXBException,FileNotFoundException {
        JAXBContext jc1 = JAXBContext.newInstance(packag);
        Unmarshaller unmarshaller1 = jc1.createUnmarshaller();
        packageArtefact.Service serv = (packageArtefact.Service) unmarshaller1.unmarshal(new File(configFileURL));

        return serv;
    }

    //RefineArtefactAndPersitenceManager
    public static void setArtefactFile(packageArtefact.Service serv, String packag, String configFileURL) throws JAXBException, FileNotFoundException {

        JAXBContext jc = JAXBContext.newInstance(packag);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

        marshaller.marshal(serv, new FileOutputStream(configFileURL));

    }
    
    
            //RefineAndPersistenceManager
    public static void treatementProvide(Service service,Msg receivedMsg,String serviceFound) throws JAXBException, FileNotFoundException, InterruptedException, JSONException{
        
        
        Provide provide = service.getProvide();
        for(int j=0; j<provide.getItem().size(); j++){
            provide.getItem().get(j).setContent(getValueItemID(service, provide.getItem().get(j).getRefItem()));
        }
        service.setProvide(provide);
        provide = service.getProvide();
        
        if(!receivedMsg.getListPreviousHostQueue().isEmpty()){  
            //4. Envoyer le message
            Senderr send0 = new Senderr();
            //On contacte tjrs l'hote distant pour la 1ere fois par son mainQueue
            MsgSimple msgReal = receivedMsg.getListPreviousHostQueue().get(receivedMsg.getListPreviousHostQueue().size() - 1);
            
            //if(receivedMsg.isCloseConnection()){
                    receivedMsg.getListPreviousHostQueue().remove(receivedMsg.getListPreviousHostQueue().size() - 1);
                    //--System.out.println("SUPPPPPPPPPPPPPPPPPPPPPPP OOOOOOOh Composite");
            //}

            //Il faut travailler les outPut du message à envoyer c'est pas receivedMsg
            receivedMsg.setAck(msgReal.isAck());
            receivedMsg.setChannel(msgReal.getChannel());
            receivedMsg.setCloseConnection(true);
            receivedMsg.setHost(msgReal.getHost());
            //receivedMsg.setInParameter(msgReal.getInParameter());
            receivedMsg.setOperationName(msgReal.getOperationName());

            List<Object> out = new ArrayList<>();
            /*for(int k=0; k<msgReal.getOutParameter().size(); k++){
                String nameOutputMsgReal = (String) msgReal.getOutParameter().get(k);
                nameOutputMsgReal = nameOutputMsgReal.split(":")[0];

                if(isThatContain(nameOutputMsgReal, receivedMsg.getOutParameter()) != null )
                    out.add(isThatContain(nameOutputMsgReal, receivedMsg.getOutParameter()));
            }*/
            for(int k=0; k<provide.getItem().size(); k++){
                String name = (String) provide.getItem().get(k).getName();
                String content = (String) provide.getItem().get(k).getContent();
                
                out.add(name+":"+content);
            }

            receivedMsg.setOutParameter(out);
            
            TreatementServiceObject treat = new TreatementServiceObject();
            receivedMsg.setServiceSolved(treat.transformServiceToObject(service));
//
//Prendre dans le doc (service) les valeurs des outPuts
            send0.sendObjectMessage(receivedMsg.getHost(), receivedMsg.getChannel(), receivedMsg);
            System.out.println("\n>>>>>>>>>>>>>>>>>>> Message Sent ");
            affiche(receivedMsg);  
        }
        
        //--System.out.println("PASSSSSSSSSSSE");
        packageArtefact.Service artefac = RefineAndPersistenceManager.getArtefactFile("packageArtefact", instanceFolder+"/Artefact.xml");
        artefac = parcoursArtefactAndSetProvide(artefac,service);
        RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact", instanceFolder+"/Artefact.xml");
        
        setServiceFile(service, "packageService", serviceFound);
        System.out.println("\nEnd Of Treatement "+serviceFound+"\n");
        
        //System.exit(0);
        System.out.println("Contexte Size = "+restoreContexte.size());
        for(int p=restoreContexte.size()-1; p>=0; p--){
            try {
                //--System.out.println("Contextttt  "+restoreContexte.get(p).getServiceFound());
                ConnectionManager.receiveData(restoreContexte.get(p).getMsgTosend(), restoreContexte.get(p).getConfig(),
                        restoreContexte.get(p).getService(), restoreContexte.get(p).getCurrentUse(),
                        restoreContexte.get(p).getServiceFound(), restoreContexte.get(p).getI(),
                        restoreContexte.get(p).getInstanceFolder());
            } catch (IOException ex) {
                Logger.getLogger(SendReceive.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nooooooooooooooooooooooooooo");
            }
        }
    }
    
    
     //RefineAndPersistenceManager
    public static void treatmentSimpleService(Service service, Use currentUse, String serviceFound, Msg msg) 
            throws JAXBException, FileNotFoundException, IOException, JSONException{
        //Remplir les inputs
            service = ConnectionManager.defineInputParam(service, currentUse, msg);
            
            setServiceFile(service, "packageService", serviceFound);
            service = getServiceFile("packageService", serviceFound);
            
            //--System.out.println("ServiceName "+currentUse.getServiceName()+"  chemin2 "+serviceFound);
            
            //--System.out.println("THREAD ID "+Thread.currentThread().getId());
            for(int i=0; i< currentUse.getInputParameter().getItem().size();i++){
                if(currentUse.getInputParameter().getItem().get(i).getContent().equals("")){
                    System.out.println("Complete input** "+service.getUse().get(0).getInputParameter().getItem().get(i).getName());
                    //Completer l'input
                    String input = "";
                    while(input.equals("")){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        input = br.readLine();
                    }
                           
                    service.getUse().get(0).getInputParameter().getItem().get(i).setContent(input);
                }
            }
            
           setServiceFile(service, "packageService", serviceFound);
           service = getServiceFile("packageService", serviceFound);
           
           JSONObject result = new JSONObject();

           if(!currentUse.isManually()){
               //REST
                List<Object> key = new ArrayList<>();
                for (int j = 0; j < currentUse.getInputParameter().getItem().size(); j++) {
                    Item currentItem = currentUse.getInputParameter().getItem().get(j);

                    key.add(currentItem.getName());

                }

                List<Object> value = new ArrayList<>();
                for (int j = 0; j < currentUse.getInputParameter().getItem().size(); j++) {
                    Item currentItem = currentUse.getInputParameter().getItem().get(j);
                    if (currentItem.getContent().equals("")) {
                        value.add(getValueItemID(service, currentItem.getRefItem()));
                    } else {
                        value.add(currentItem.getContent());
                    }
                }

                result = RestService.sendPost(currentUse.getLocation(), key, value);

                //Récupérer le outputList et compléter le fichier.(Etendre l'artefact') this.
                service = ConnectionManager.defineOutputParam(service, currentUse, result);
           }else{
               //Manuel
                System.out.println("\n*************************************");
                System.out.println("\n****** Operation Name: "+currentUse.getServiceName());
                System.out.println("\n****** Input Parameter");
                //--System.out.println("\n serviceFounddd "+serviceFound);
                for(int l=0;l<currentUse.getInputParameter().getItem().size();l++){
                    String content = null;
                    if(currentUse.getInputParameter().getItem().get(l).getContent().equals(""))
                        content = getValueItemID(service, currentUse.getInputParameter().getItem().get(l).getRefItem());
                    else
                        content = currentUse.getInputParameter().getItem().get(l).getContent();

                    System.out.println("N° "+l+" = "+content);
                }
                System.out.println("\n------ Please, Complete field following");
                String out = "";
                for(int l=0;l<currentUse.getOutputParameter().getItem().size();l++){
                    System.out.println("Give Value to "+currentUse.getOutputParameter().getItem().get(l).getName());
                    
                    String param = "";
                    while(param.equals("")){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        param = br.readLine();
                    }

                    currentUse.getOutputParameter().getItem().get(l).setContent(param);

                    if(l == currentUse.getOutputParameter().getItem().size() -1 ){
                        out = out+currentUse.getOutputParameter().getItem().get(l).getName()
                                +":"+param;
                    }else{
                        out = out+currentUse.getOutputParameter().getItem().get(l).getName()
                                +":"+param+",";
                    }
                }
                out = "{"+out+"}";
                System.out.println("JSON Manuel "+out);
                result = new JSONObject(out);
                
                //this.
                service = ConnectionManager.defineOutputParam(service, currentUse, result);
            }
           
            Provide provide = service.getProvide();
            for(int j=0; j<provide.getItem().size(); j++){
                provide.getItem().get(j).setContent(getValueItemID(service, provide.getItem().get(j).getRefItem()));
            }
            service.setProvide(provide);

            if(msg != null)
                if(!msg.getListPreviousHostQueue().isEmpty()){  
                    //4. Envoyer le message
                    Senderr send0 = new Senderr();
                    //On contacte tjrs l'hote distant pour la 1ere fois par son mainQueue
                    MsgSimple msgReal = msg.getListPreviousHostQueue().get(msg.getListPreviousHostQueue().size() - 1);

                    //if(msg.isCloseConnection()){
                            msg.getListPreviousHostQueue().remove(msg.getListPreviousHostQueue().size() - 1);
                            //--System.out.println("SUPPPPPPPPPPPPPPPPPPPPPPP OOOOOOOh Simple");
                    //}
                            
                    //Il faut travailler les outPut du message à envoyer c'est pas receivedMsg
                    msg.setAck(msgReal.isAck());
                    msg.setChannel(msgReal.getChannel());
                    msg.setCloseConnection(true);
                    msg.setHost(msgReal.getHost());
                    msg.setInParameter(msgReal.getInParameter());
                    msg.setOperationName(msgReal.getOperationName());

                    List<Object> out = new ArrayList<>();
                    /*for(int k=0; k<msgReal.getOutParameter().size(); k++){
                        String nameOutputMsgReal = (String) msgReal.getOutParameter().get(k);
                        nameOutputMsgReal = nameOutputMsgReal.split(":")[0];

                        if(isThatContain(nameOutputMsgReal, result) != null )
                            out.add(isThatContain(nameOutputMsgReal, result));
                    }*/
                    for(int k=0; k<provide.getItem().size(); k++){
                        String name = (String) provide.getItem().get(k).getName();
                        String content = (String) provide.getItem().get(k).getContent();

                        out.add(name+":"+content);
                    }

                    msg.setOutParameter(out);
                    
                    TreatementServiceObject treat = new TreatementServiceObject();
                    msg.setServiceSolved(treat.transformServiceToObject(service));
         //les outPut de msg doivent être ceux de provide
        //Prendre dans le doc (service) les valeurs des outPuts
                    send0.sendObjectMessage(msgReal.getHost(), msgReal.getChannel(), msg);
                    System.out.println("\n>>>>>>>>>>>>>>>>>>> Message Sent <<<<<<<<<<<<<<<<<<<<<<<");
                    affiche(msg);  
                }//--
            //---System.out.println("OTOTOTOTTO "+service.getType());
            //CopyPasteFile.writeAtTheEnd(instanceFolder+"/Artefact.txt", "Receive "+msg.getOperationName()+"\n");
            try{    
                packageArtefact.Service artefac = getArtefactFile("packageArtefact", instanceFolder+"/Artefact.xml");
                artefac = remplaceCurrentByService(artefac,currentUse,service);
                artefac = parcoursArtefactAndSetProvide(artefac,service);
                setArtefactFile(artefac, "packageArtefact", instanceFolder+"/Artefact.xml");

                setServiceFile(service, "packageService", serviceFound);
                
            }catch(JAXBException | FileNotFoundException ex){
                
                CopyPasteFile.writeAtTheEnd(instanceFolder+"/Artefact.xml", "");
                System.out.println("Zuttt "+service.getType());
                setServiceFile(service, "packageService", instanceFolder+"/Artefact.xml");
                
                setServiceFile(service, "packageService", serviceFound);
            }
    }
    
    
    //ConnectionManager
    public void receiveData(Msg msgTosend,Config config, Service service,Use currentUse, 
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
        System.out.println("\n>>>><<<<<<<<<<<<<<<<<<<<< Message Received R>>>>>>>>>>>>>>>>>>>>");
        affiche(receivedMsg);
        
        
        service = ConnectionManager.defineOutputParam(service, currentUse, receivedMsg);
        RefineAndPersistenceManager.setServiceFile(service, "packageService", serviceFound);        
        service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);
        
        //Ecrire dans instanceFolder/Artefact.txt
        CopyPasteFile.writeAtTheEnd(instanceFolder+"/Artefact.txt", "Receive "+msgTosend.getOperationName()+"\n");
        packageArtefact.Service artefac = RefineAndPersistenceManager.getArtefactFile("packageArtefact", instanceFolder+"/Artefact.xml");
        artefac = remplaceCurrentByService(artefac,currentUse,service);
        artefac = parcoursArtefactAndSetProvide(artefac,service);
        RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact", instanceFolder+"/Artefact.xml");
        
        if(i == service.getUse().size()-1){
            //--System.out.println("i=listUseSize "+serviceFound+"  Context Size "+restoreContexte.size());
            RefineAndPersistenceManager.treatementProvide(service, receivedMsg, serviceFound);
        }else{//Continuer pardon parceke l'autre est déjà bloqué
            
               for(int l=i+1; l< service.getUse().size(); l++){
                    System.out.println("THREAD ID "+Thread.currentThread().getId());
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
