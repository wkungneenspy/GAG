/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packageSendReceivePrimitive;

import Manager.ConnectionManager;
import Manager.MessageManager;
import Manager.RefineAndPersistenceManager;
import treatThread.DataReceivedMainQueue;

import utils.ContexteStore;

import packageConfigFile.Config;
import packageService.Item;
import packageService.Service;
import packageService.Use;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import utils.CopyPasteFile;
import utils.LaunchOpenJMS;

import object.Msg;
import object.ServiceObject;

import utils.UpdateFile;

import utils.FileOfFolder;

import org.json.JSONException;
import org.json.JSONObject;
import packageService.Contexte;
import packageService.InputParameter;
import packageService.OutputParameter;
import packageService.Provide;


/**
 *
 * @author willyk
 */
public class SendReceive {
    
    public static List<ContexteStore> restoreContexte = new ArrayList<>();
    
    public static String configPath; //= "/home/willyk/config.xml"
    public static String openJMSConfigPath; // = "/home/willyk/dev/openjms-0.7.7-beta-1/config/openjms.xml";
    public static String schemaExecution;
    public static String instance;
    public static String openJMSHOME;
    
    public static String instanceFolder = null;

    public SendReceive(String configPath, String openJMSConfigPath, String schemaExecution, String instance, String openJMSHOME) {
        this.configPath = configPath;
        this.openJMSConfigPath = openJMSConfigPath;
        this.schemaExecution = schemaExecution;
        this.instance = instance;
        this.openJMSHOME = openJMSHOME;
    }

    public SendReceive() {
    }
   
    public void sendReceive(Msg msgRecu) throws JAXBException, IOException, JSONException, FileNotFoundException, InterruptedException{
        //Chargement du fichier de configuration
        Config config = RefineAndPersistenceManager.getConfigFile("packageConfigFile", configPath);
        
        
       //1. Quand tu start à partir de rien
                System.out.println("\nStart Instance Treatment\n");

                List<String> serviceFoundList = null;

                while(serviceFoundList == null){
                    //Choix d'un service
                    System.out.println("\n--> Choose Operation");
                    String operationChoisie = "";
                    while(operationChoisie.equals("")){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        operationChoisie = br.readLine();
                    }


                    //Parcourir les services disponibles à la recherche du service approprié
                    //Pour l'instant ayant le même nom. Il y a un repertoire pour les schémas d'execution
                     this.instanceFolder = instance+"/scenario"+config.getInstanceNumber()+"/";
                     System.err.println("Instance Folder  "+this.instanceFolder);
                     serviceFoundList = findMatchingService(operationChoisie, schemaExecution, this.instanceFolder);
                     if(serviceFoundList.isEmpty()){
                         System.out.println("Aucun service ne correspond, recommencez");
                         serviceFoundList = null;
                     }
                }
                int nbConn = config.getInstanceNumber().intValue()+1;
                config.setInstanceNumber(BigInteger.valueOf(nbConn));
                RefineAndPersistenceManager.setConfigFile(config, "packageConfigFile", configPath);
                config = RefineAndPersistenceManager.getConfigFile("packageConfigFile", configPath);  

        
        
        
        int numChoisi = 0; 

        if(serviceFoundList.size() > 1){
            //Choix d'un service parmi ceux trouver.
            System.out.println("\n=======================================");
            System.out.println("============ Choisir un numero (C'est le choix d'un service spécifique) ");
            
            for(int i=0;i<serviceFoundList.size();i++){
                System.out.println("============ "+i+"  pour "+displayService("packageService", schemaExecution+"/"+serviceFoundList.get(i).split("::")[2]));
            }
            
            String operationChoisie = "";
            while(operationChoisie.equals("")){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                operationChoisie = br.readLine();
                if(!operationChoisie.equals("")){
                    try{
                        numChoisi = Integer.parseInt(operationChoisie);
                        if(numChoisi >= serviceFoundList.size()){
                            System.out.println("The number must be smaller than "+serviceFoundList.size());
                            operationChoisie = "";
                        }
                            
                    }catch(Exception ex){
                        System.out.println("Type Number Please");
                        operationChoisie = "";
                    }
                }
            }
        }

        CopyPasteFile.copyFile(serviceFoundList.get(numChoisi).split("::")[0], serviceFoundList.get(numChoisi).split("::")[1], serviceFoundList.get(numChoisi).split("::")[2]);
        String serviceFound = serviceFoundList.get(numChoisi).split("::")[3];

        System.out.println("Service Found :\n -------------> "+displayService("packageService", schemaExecution+"/"+serviceFoundList.get(numChoisi).split("::")[2]));
        Service service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);

                        
        //Completer le contexte        
        service = defineContexte(service,msgRecu,config);
        
        RefineAndPersistenceManager.setServiceFile(service, "packageService", serviceFound);
        service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);
        
        if(service.getType().equals("simple")){
            //Prendre le seul element et consommer
            Use currentUse = service.getUse().get(0);
            
            RefineAndPersistenceManager.treatmentSimpleService(service, currentUse, serviceFound, msgRecu);

        }else{ //Composite
        
            for(int i=0; i< service.getUse().size(); i++){
                
                if(service.getUse().get(i).getType().equals("simple")){
                
                    RefineAndPersistenceManager.treatmentSimpleService(service, service.getUse().get(i), serviceFound, msgRecu);
                    
                }else{
                    treatmentCompositeService(config, service,service.getUse().get(i)
                        ,msgRecu,serviceFound,i);   
                }         
            }
        }
        
        //--System.out.println("STOPPED "+Thread.currentThread().getId());
        Thread.currentThread().stop();
    }
    
    public String displayService(String packageService, String urlFile) throws JAXBException{
        Service serv = RefineAndPersistenceManager.getServiceFile(packageService, urlFile);
        String serviceName = serv.getName();
        
        String inputService = " INPUT (";
            for(int i=0;i<serv.getContexte().getItem().size();i++){
                if(i != (serv.getContexte().getItem().size() - 1))
                    inputService = inputService + serv.getContexte().getItem().get(i).getName()+",";
                else
                    inputService = inputService + serv.getContexte().getItem().get(i).getName();
            }
        inputService = inputService + ")";
    
        String outputService = " OUTPUT (";
            for(int i=0;i<serv.getProvide().getItem().size();i++){
                if(i != (serv.getProvide().getItem().size() - 1))
                    outputService = outputService + serv.getProvide().getItem().get(i).getName()+",";
                else
                    outputService = outputService + serv.getProvide().getItem().get(i).getName();
            }
        outputService = outputService + ")";
        
        return serviceName+inputService+outputService;
    }
    
    /**
     * @author Willy KENGNE
     * @param serv
     * @param itemID
     * @return
     */
    public static String getValueItemID(Service serv, String itemID) {
        //Parcourir le Contexte
        List<Item> listItem = serv.getContexte().getItem();
        for (int i = 0; i < listItem.size(); i++) {
            if (listItem.get(i).getId().equals(itemID)) {
                return listItem.get(i).getContent();
            }
        }

        //Parcourir les Use
        List<Use> listUsedServices = serv.getUse();
        for (int i = 0; i < listUsedServices.size(); i++) {
            //Pour les input Parameter
            List<Item> listItemInput = serv.getUse().get(i).getInputParameter().getItem();
            for (int j = 0; j < listItemInput.size(); j++) {
                if (listItemInput.get(j).getId().equals(itemID)) {
                    return listItemInput.get(j).getContent();
                }
            }

            List<Item> listItemOutput = serv.getUse().get(i).getOutputParameter().getItem();
            for (int j = 0; j < listItemOutput.size(); j++) {
                if (listItemOutput.get(j).getId().equals(itemID)) {
                    return listItemOutput.get(j).getContent();
                }
            }
        }

        return null;
    }
    
    public static String isThatContain(String name, JSONObject listOuput){
        try {
            //---System.out.println("name = "+name+"  list "+listOuput);
            Object o = listOuput.get(name);
            
            return name+":"+(String) o;
            
                
        } catch (JSONException ex) {
            
            Logger.getLogger(SendReceive.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
    
    public static String isThatContain(String name, List<Object> listOuput){
        
        for(int i=0;i<listOuput.size();i++){
            String current = (String) listOuput.get(i);
            if(current.split(":")[0].equals(name))
                return current;
        }
        return null;
    }
    
   
   
    public static void affiche(Msg receivedMsg) {
        System.out.println("\n>>>>>>>>>>>>>>>>>>> Host = " + receivedMsg.getHost());
        System.out.println(">>>>>>>>>>>>>>>>>>> Queue = " + receivedMsg.getChannel());
        System.out.println(">>>>>>>>>>>>>>>>>>> OpName = " + receivedMsg.getOperationName());
        if(receivedMsg.getListPreviousHostQueue() != null)    
            for (int i = 0; i < receivedMsg.getListPreviousHostQueue().size(); i++) {
                System.out.println(">>>>>>>>>>>>>>>>>>> Previous " + i + " " + receivedMsg.getListPreviousHostQueue().get(i));
            }
        
        if(receivedMsg.getInParameter() != null) 
            for (int i = 0; i < receivedMsg.getInParameter().size(); i++) {
                System.out.println(">>>>>>>>>>>>>>>>>>> Input " + i + " " + receivedMsg.getInParameter().get(i));
            }
        
        if(receivedMsg.getOutParameter() != null) 
            for (int i = 0; i < receivedMsg.getOutParameter().size(); i++) {
                System.out.println(">>>>>>>>>>>>>>>>>>> Output " + i + " " + receivedMsg.getOutParameter().get(i));
            }
        
        if(receivedMsg.getServiceSolved() != null){
            System.out.println(">>>>>>>>>>>>>>>>>>> Solved Service Name  " + receivedMsg.getServiceSolved().getServiceName());
        }
    }
    
    public List<String> findMatchingService(String operationChoisie,String schemaExecution, String instanceFolder) throws IOException, JAXBException{
        ArrayList<String> allFileOfAW =(ArrayList<String>) FileOfFolder.scanDir(schemaExecution);
        String serviceFound = null;
        List<String> result = new ArrayList<String>();
        Service service = new Service();
        for(int j=0;j<allFileOfAW.size();j++){
            //--System.out.println(" "+allFileOfAW.get(j));
            if(allFileOfAW.get(j).contains("~"))
                continue;
            Service serv = RefineAndPersistenceManager.getServiceFile("packageService", allFileOfAW.get(j));
            if(serv.getName().equals(operationChoisie)){
                //CopyPasteFile.copyFile(allFileOfAW.get(j), instanceFolder,allFileOfAW.get(j).split("/")[allFileOfAW.get(j).split("/").length-1]);
                serviceFound = instanceFolder+allFileOfAW.get(j).split("/")[allFileOfAW.get(j).split("/").length-1]; 
                    //allFileOfAW.get(j);
                //--System.out.println("8888 "+instanceFolder);
                this.instanceFolder = instanceFolder;
                service = serv;
                
                result.add(allFileOfAW.get(j)+"::"+instanceFolder+"::"+allFileOfAW.get(j).split("/")[allFileOfAW.get(j).split("/").length-1]+"::"+serviceFound);
            }
        }
        
        return result;
    }
    
    
    /*public static void main(String[] arg){
        try {
            SendReceive sr = new SendReceive();
            
            Config config = sr.getConfigFile("configFile", sr.configPath);
            
            sr.sendReceive(null);
            
        } catch (JAXBException | IOException | JSONException | InterruptedException ex) {
            Logger.getLogger(ReceiveSend.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }*/

    
    
    
    public Service defineContexte(Service service, Msg msgRecu, Config config){
        
        if(msgRecu != null){
            for(int l=0; l<service.getContexte().getItem().size();l++){
                for(int k=0; k<msgRecu.getInParameter().size();k++){
                    if(service.getContexte().getItem().get(l).getContent().equals("")){
                        String param = (String) msgRecu.getInParameter().get(k);
                        String paramName = param.split(":")[0];
                        if(service.getContexte().getItem().get(l).getName()
                                .equals(paramName)){
                            service.getContexte().getItem().get(l).setContent(param.split(":")[1]);
                        }
                    }
                }
            }
        }
        
        boolean isAddress = false;
        
        for(int l=0; l<service.getContexte().getItem().size();l++){
            try {
                if(service.getContexte().getItem().get(l).getContent().equals("")){
                    System.out.println("Give Value to "+service.getContexte().getItem().get(l).getName()+":");
                    
                    String s = "";
                    while(s.equals("")){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        s = br.readLine();
                    }
                    
                    /*System.out.println(service.getContexte().getItem().get(l).getName().contains("@")+
                            " --------- "+service.getContexte().getItem().get(l).getName());*/
                    if(service.getContexte().getItem().get(l).getName().contains("@"))
                        isAddress = isAddress || true;

                    service.getContexte().getItem().get(l).setContent(s);

                    service.getContexte().getItem().set(l, service.getContexte().getItem().get(l));
                }
            }catch (IOException e){
                System.out.println(e);
            }
        }
        
        //Define local address
         for(int l=0; l<service.getUse().size();l++){
                
                if(service.getUse().get(l).getLocation()
                        .equals("localhost")){
                    service.getUse().get(l).setLocation(config.getHost());
                }
        }
        
        //définir les addresses 
        if(isAddress)
            service = defineAdress(service);
        
        return service;
    }
    
    public static packageService.Service tranformServiceObjectToService(ServiceObject servObj){
    
        packageService.Service serv = new Service();
        //definir les elements du contexte
        List<Item> listItem = new ArrayList<>();
        for(int i=0;i<servObj.getListContext().size();i++){
            Item item = new Item();
            item.setContent(servObj.getListContext().get(i).getContent());
            item.setId(servObj.getListContext().get(i).getId());
            item.setName(servObj.getListContext().get(i).getName());
            item.setRefItem(servObj.getListContext().get(i).getRefItem());
            listItem.add(item);
        }
        packageService.Contexte contexte = new Contexte();
        contexte.getItem().addAll(listItem);
        serv.setContexte(contexte);
        
        //Definir le nom
        serv.setName(servObj.getServiceName());
        
        //Definir Provide
        listItem = new ArrayList<>();
        for(int i=0;i<servObj.getListProvid().size();i++){
            Item item = new Item();
            item.setContent(servObj.getListProvid().get(i).getContent());
            item.setId(servObj.getListProvid().get(i).getId());
            item.setName(servObj.getListProvid().get(i).getName());
            item.setRefItem(servObj.getListProvid().get(i).getRefItem());
            listItem.add(item);
        }
        packageService.Provide provide = new Provide();
        provide.getItem().addAll(listItem);
        serv.setProvide(provide);
        
        //Definir le type
        serv.setType(servObj.getType());
        
        List<Use> listUses = new ArrayList<>();
        for(int i=0;i<servObj.getListUse().size();i++){
            Use use = new Use();
            
            InputParameter input = new InputParameter();
            for(int k=0;k<servObj.getListUse().get(i).getInputParam().size();k++){
                Item item = new Item();
                item.setContent(servObj.getListUse().get(i).getInputParam().get(k).getContent());
                item.setId(servObj.getListUse().get(i).getInputParam().get(k).getId());
                item.setName(servObj.getListUse().get(i).getInputParam().get(k).getName());
                item.setRefItem(servObj.getListUse().get(i).getInputParam().get(k).getRefItem());
                
                input.getItem().add(item);
            }
            use.setInputParameter(input);
            
            use.setLocation(servObj.getListUse().get(i).getLocation());
    
            if(servObj.getListUse().get(i).getType().equals("simple"))
                use.setManually(servObj.getListUse().get(i).isManually());
            
            use.setOrder(servObj.getListUse().get(i).isOrder());
            
            OutputParameter output = new OutputParameter();
            for(int k=0;k<servObj.getListUse().get(i).getOutputParam().size();k++){
                Item item = new Item();
                item.setContent(servObj.getListUse().get(i).getOutputParam().get(k).getContent());
                item.setId(servObj.getListUse().get(i).getOutputParam().get(k).getId());
                item.setName(servObj.getListUse().get(i).getOutputParam().get(k).getName());
                //item.setRefItem(servObj.getListUse().get(i).getOutputParam().get(k).getRefItem());
                
                output.getItem().add(item);
            }
            use.setOutputParameter(output);
            
            use.setServiceName(servObj.getListUse().get(i).getServiceName());
            
            use.setType(servObj.getListUse().get(i).getType());
            
            
            
            listUses.add(use);
        }
        serv.getUse().addAll(listUses);
        
        return serv;
    }
    
    public static packageArtefact.Service tranformServiceToArtefact(Service serv){
        packageArtefact.Service artefac = new packageArtefact.Service();
        artefac.setName(serv.getName());
        artefac.setType(serv.getType());
        
        packageArtefact.Provide p = new packageArtefact.Provide();
        for(int i=0;i<serv.getProvide().getItem().size();i++){
            packageArtefact.Item item = new packageArtefact.Item();
            item.setContent(serv.getProvide().getItem().get(i).getContent());
            item.setId(serv.getProvide().getItem().get(i).getId());
            item.setName(serv.getProvide().getItem().get(i).getName());
            item.setRefItem(serv.getProvide().getItem().get(i).getRefItem());
            
            p.getItem().add(item);
        }
        artefac.setProvide(p);
        
        packageArtefact.Contexte c = new packageArtefact.Contexte();
        for(int i=0;i<serv.getContexte().getItem().size();i++){
            packageArtefact.Item item = new packageArtefact.Item();
            item.setContent(serv.getContexte().getItem().get(i).getContent());
            item.setId(serv.getContexte().getItem().get(i).getId());
            item.setName(serv.getContexte().getItem().get(i).getName());
            //item.setRefItem(serv.getContexte().getItem().get(i).getRefItem());
            
            c.getItem().add(item);
        }
        artefac.getServiceOrContexte().add(c);
        
        
        
        
        for(int i=0;i<serv.getUse().size();i++){
            packageArtefact.Use use = new packageArtefact.Use();
            
            use.setLocation(serv.getUse().get(i).getLocation());
            
            if(serv.getUse().get(i).getType().equals("simple"))
                use.setManually(serv.getUse().get(i).isManually());
            
            use.setOrder(serv.getUse().get(i).isOrder());
            use.setServiceName(serv.getUse().get(i).getServiceName());
            use.setType(serv.getUse().get(i).getType());
            
            packageArtefact.InputParameter ip = new packageArtefact.InputParameter();
            for(int l=0;l<serv.getUse().get(i).getInputParameter().getItem().size();l++){
                packageArtefact.Item item = new packageArtefact.Item();
                item.setContent(serv.getUse().get(i).getInputParameter().getItem().get(l).getContent());
                item.setId(serv.getUse().get(i).getInputParameter().getItem().get(l).getId());
                item.setName(serv.getUse().get(i).getInputParameter().getItem().get(l).getName());
                item.setRefItem(serv.getUse().get(i).getInputParameter().getItem().get(l).getRefItem());
                
                ip.getItem().add(item);
            }
            use.setInputParameter(ip);
        
            
            packageArtefact.OutputParameter op = new packageArtefact.OutputParameter();
            for(int l=0;l<serv.getUse().get(i).getOutputParameter().getItem().size();l++){
                packageArtefact.Item item = new packageArtefact.Item();
                item.setContent(serv.getUse().get(i).getOutputParameter().getItem().get(l).getContent());
                item.setId(serv.getUse().get(i).getOutputParameter().getItem().get(l).getId());
                item.setName(serv.getUse().get(i).getOutputParameter().getItem().get(l).getName());
                //item.setRefItem(servObject.getListUse().get(i).getOutputParam().get(l).getRefItem());
                
                op.getItem().add(item);
            }
            use.setOutputParameter(op);

            artefac.getUse().add(use);
        }
        
        
        return artefac;
    }
    
    public static packageArtefact.Service parcoursArtefactAndSetProvide(packageArtefact.Service artefac, Service serv){
        
            for(int i=0; i< artefac.getProvide().getItem().size();i++){
                //--System.out.println("Artefact_Provide: Content "+artefac.getProvide().getItem().get(i).getContent()+" Name "+artefac.getProvide().getItem().get(i).getName());
                for(int j=0; j< serv.getProvide().getItem().size();j++){
                    //System.out.println("Service_Provide: Content "+artefac.getProvide().getItem().get(j).getContent()+" Name "+artefac.getProvide().getItem().get(i).getName());
                    if(artefac.getProvide().getItem().get(i).getName()
                        .equals(serv.getProvide().getItem().get(j).getName())){
                        
                        if(artefac.getProvide().getItem().get(i).getContent() == null || 
                                artefac.getProvide().getItem().get(i).getContent().equals("")){
                            
                            packageArtefact.Item item = new packageArtefact.Item();
                            
                            //--System.out.println("Contennnnnt "+serv.getProvide().getItem().get(j).getContent());
                            item.setContent(serv.getProvide().getItem().get(j).getContent());
                            
                            item.setId(artefac.getProvide().getItem().get(i).getId());
                            item.setName(artefac.getProvide().getItem().get(i).getName());
                            item.setRefItem(artefac.getProvide().getItem().get(i).getRefItem());

                            artefac.getProvide().getItem().set(i, item);
                            
                        }
                    }
                }
            }
            //System.out.println("\n\n");
        
            for(int i=0; i< artefac.getServiceOrContexte().size();i++){
                try{
                    parcoursArtefactAndSetProvide((packageArtefact.Service)artefac.getServiceOrContexte().get(i), serv);
                }catch(Exception ex){
                
                }
            }
            
            
        return artefac;
    }
    
    
    
    public static boolean isArtefactContainUse(packageArtefact.Service artefac,Use use, Service serv){
        
        for(int i=0; i< artefac.getUse().size();i++){
            //Si tu trouves tu breaks
            if(artefac.getUse().get(i).getServiceName().equals(serv.getName())){
                return true;
            }
        }

        //parcourir récursivement les services internes
        for(int i=0; i< artefac.getServiceOrContexte().size();i++){
            try{
                remplaceCurrentByService((packageArtefact.Service)artefac.getServiceOrContexte().get(i),use,serv);
            }catch(Exception ex){
                
            }
        }
        
        //Si Aucun remplacement n'a été effectué.
        
        return false;
    }
    
    
    public static packageArtefact.Service remplaceUseByService(packageArtefact.Service artefac, Service serv){
        
        for(int i=0; i< artefac.getUse().size();i++){
            //Si tu trouves tu breaks
            if(artefac.getUse().get(i).getServiceName().equals(serv.getName())){
                artefac.getUse().remove(i);
                artefac.getServiceOrContexte().add(tranformServiceToArtefact(serv));
                //---System.err.println("OUIIIIIIIIIIIIIIII "+serv.getName());
                return artefac;
            }
        }

        //parcourir récursivement les services internes
        for(int i=0; i< artefac.getServiceOrContexte().size();i++){
            try{
                remplaceUseByService((packageArtefact.Service)artefac.getServiceOrContexte().get(i),serv);
            }catch(Exception ex){
                
            }
        }
        
        //Si Aucun remplacement n'a été effectué.
        
        return artefac;
    }
    
    public static packageArtefact.Service remplaceCurrentByService(packageArtefact.Service artefac,Use use, Service serv){
        
        for(int i=0; i< artefac.getUse().size();i++){
            //Si tu trouves tu breaks
            if(artefac.getUse().get(i).getServiceName().equals(serv.getName())){
                artefac.getUse().remove(i);
                artefac.getServiceOrContexte().add(tranformServiceToArtefact(serv));
                
                return artefac;
            }
        }

        //parcourir récursivement les services internes
        for(int i=0; i< artefac.getServiceOrContexte().size();i++){
            try{
                remplaceCurrentByService((packageArtefact.Service)artefac.getServiceOrContexte().get(i),use,serv);
            }catch(Exception ex){
                
            }
        }
        
        //Si Aucun remplacement n'a été effectué.
        
        return artefac;
    }
    
        //RefineAndPersistenceManager
    public void treatmentCompositeService(Config config, Service service, Use currentUse
                    , Msg msgRecu, String serviceFound, int i) throws JAXBException, FileNotFoundException, IOException, InterruptedException, JSONException{
        //--System.out.println(" i = "+i+" serviceFound = "+serviceFound);
        System.out.println("\nService composite\n");
        //1. recupérer un numéro de queue libre
        int nb = UpdateFile.queueNumber(openJMSConfigPath, "queue" + config.getNumberOfConnection().intValue());
        //Ajoute un nouveau numéro pour avoir plutard de nouvelles destinations
        config.setNumberOfConnection(BigInteger.valueOf(nb));
        RefineAndPersistenceManager.setConfigFile(config, "packageConfigFile", configPath);

            //2. Ajouter la queue créee dans le Broker JMS
        //Ajouter une variable globale pour le rep d'openJMS
        //Verifier que la queue n'existe pas dans le fichier de config de JMS
        LaunchOpenJMS.shutdownJMS(openJMSHOME);
        System.out.println("----- Added Queue" + nb + " Of The Broker\n");
        UpdateFile.addNewQueue(openJMSConfigPath, "queue" + nb);
        LaunchOpenJMS.startupJMS(openJMSHOME);

        //Définir les input des services...
        service = ConnectionManager.defineInputParam(service, currentUse, msgRecu);
      
        RefineAndPersistenceManager.setServiceFile(service, "packageService", serviceFound);
        service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);

        //Recherche de l'indice dans Service du CurrenUse
        int indice = 0;
        for(int k=0; k< service.getUse().size();k++){
            if(currentUse.getServiceName().equals(service.getUse().get(k).getServiceName()))
                indice = k;
        }
        
         
        //System.out.println("ServiceName "+currentUse.getServiceName()+"  chemin "+serviceFound);
        for(int k=0; k< currentUse.getInputParameter().getItem().size();k++){
            System.out.println(currentUse.getInputParameter().getItem().get(k).getName()+" content = "+currentUse.getInputParameter().getItem().get(k).getContent());
                if(currentUse.getInputParameter().getItem().get(k).getContent() == null){
                    System.out.println("Complete input-- "+service.getUse().get(indice).getInputParameter().getItem().get(k).getName());

                    //Completer l'input
                    String input = "";
                    while(input.equals("")){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        input = br.readLine();
                    }

                    service.getUse().get(indice).getInputParameter().getItem().get(k).setContent(input);
                    currentUse.getInputParameter().getItem().get(k).setContent(input);
            
                    
                }else if(currentUse.getInputParameter().getItem().get(k).getContent().equals("")){
                    System.out.println("Complete input-- "+service.getUse().get(indice).getInputParameter().getItem().get(k).getName());

                    //Completer l'input
                    String input = "";
                    while(input.equals("")){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        input = br.readLine();
                    }

                    service.getUse().get(indice).getInputParameter().getItem().get(k).setContent(input);
                    currentUse.getInputParameter().getItem().get(k).setContent(input);
                
                }
        }
        
        RefineAndPersistenceManager.setServiceFile(service, "packageService", serviceFound);
        service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);

        
        //3. Fabriquer le message à envoyer
        Msg msgTosend = MessageManager.constructMessageToSend(service, currentUse, config, msgRecu);
        
                
        //4. Envoyer le message
        Senderr send = new Senderr();
       // Faut pas tjrs send
        //On contacte tjrs l'hote distant pour la 1ere fois par son mainQueue
        send.sendObjectMessage(currentUse.getLocation(), "mainQueue", msgTosend);
        //Ecrire dans instanceFolder/Artefact.txt
        CopyPasteFile.writeAtTheEnd(instanceFolder+"/Artefact.txt", "Call "+msgTosend.getOperationName()+"\n");
        
        //init artefact
        if(msgRecu == null){//Créer le fichier Artefact
            packageArtefact.Service artefac = tranformServiceToArtefact(service);
            RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact", instanceFolder+"/Artefact.xml");
        }else{//Remplacer le currentUse par le Service
            packageArtefact.Service artefac = RefineAndPersistenceManager.getArtefactFile("packageArtefact", instanceFolder+"/Artefact.xml");
            artefac = remplaceCurrentByService(artefac,currentUse,service);
            artefac = parcoursArtefactAndSetProvide(artefac,service);
            RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact", instanceFolder+"/Artefact.xml");
        }
        
        System.out.println("<<<<<<<<<<<<<<<<<<<< \nMessage Sent");
                affiche(msgTosend);

        //5. Attendre la réponse sur la queue envoyée dans le msg précedent
        //Voir comment créer des Threads indépedants pour traiter les msgs.
        Thread.sleep(10000);
        System.out.println("\nLaunching Thread to listen another connections");
        DataReceivedMainQueue t = new DataReceivedMainQueue(config, "mainQueue",this, configPath, schemaExecution, instance,openJMSHOME);
        t.start();

        Thread.sleep(15000);

        /*DataReceived dataRecei = new DataReceived(msgTosend, config, service, currentUse,
                                                          serviceFound, i,instanceFolder);
        dataRecei.start();*/
        
        //---System.err.println("CurrentLocation "+currentUse.getLocation());
        //---System.err.println("ConfigHost "+config.getHost());

        ///****if(currentUse.getLocation().equals(config.getHost())){
            //--System.out.println("Config "+config.getHost()+"  currentUseLocation "+currentUse.getLocation()
                                //--+"  Channel "+currentUse.getServiceName());
            
            ContexteStore c = new ContexteStore();
            c.setConfig(config);
            c.setCurrentUse(currentUse);
            c.setI(i);
            c.setInstanceFolder(instanceFolder);
            c.setMsgTosend(msgTosend);
            c.setService(service);
            c.setServiceFound(serviceFound);

            restoreContexte.add(c);
        ///****}
        
        ConnectionManager.receiveData(msgTosend,config, service, currentUse, 
            serviceFound,i,instanceFolder);
    }
    
    
    public Service defineAdress(Service service){
        List<String> listAddress = new ArrayList<>();
        
        for(int l=0; l<service.getContexte().getItem().size();l++){
            if(!service.getContexte().getItem().get(l).getContent().equals("")){
                String paramName = (String) service.getContexte().getItem().get(l).getName();
                String paramContent = (String) service.getContexte().getItem().get(l).getContent();
                
                listAddress.add(paramName+":"+paramContent);
                
                //---System.out.println("Adreesss  "+paramName+":"+paramContent);
            }
        }
        
        for(int l=0; l<service.getUse().size();l++){
            
            for(int i=0; i<listAddress.size();i++){
                
                if(service.getUse().get(l).getLocation()
                        .equals(listAddress.get(i).split(":")[0])){
                    service.getUse().get(l).setLocation(listAddress.get(i).split(":")[1]);
                }
            }
        }
        
        return service;
    }

}
