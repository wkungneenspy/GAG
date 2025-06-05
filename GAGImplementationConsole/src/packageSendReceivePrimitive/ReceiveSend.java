/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packageSendReceivePrimitive;

import Manager.RefineAndPersistenceManager;
import packageConfigFile.Config;
import packageService.Service;
import packageService.Use;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.xml.bind.JAXBException;
import utils.CopyPasteFile;
import utils.LaunchOpenJMS;
import object.Msg;
import utils.RecuperationSorties;
import org.json.JSONException;
import static packageSendReceivePrimitive.SendReceive.tranformServiceToArtefact;

/**
 *
 * @author willyk
 */
public class ReceiveSend {
    String configPath;
    String schemaExecution; 
    String instance;
    String openJMSHOME;

    public ReceiveSend(String configPath, String schemaExecution, String instance, String openJMSHOME) {
        this.configPath = configPath;
        this.schemaExecution = schemaExecution;
        this.instance = instance;
        
        this.openJMSHOME = openJMSHOME;
    }
    
    public void receiveSend(Config config, SendReceive sr) throws IOException, JAXBException, FileNotFoundException, JSONException, InterruptedException{
        if(!RecuperationSorties.nothingValue()){ //Broker JMS pas lancé
            LaunchOpenJMS.startupJMS(openJMSHOME);
        }
        
       // 0. Lancé à partir de l'attente d'une réponse
        
       // 1. C'est lancé à partir de 0.
            Msg receivedMsg = new Msg();

            //---System.out.println("THREAD ID "+Thread.currentThread().getId());
            Receiverr rec = new Receiverr();
            //--System.out.println("pp "+receivedMsg.getOperationName());
            while(receivedMsg.getOperationName() == null){
                rec = new Receiverr();
                //--System.out.println("HOSSSSSSSSSSSST   "+config.getHost());
                receivedMsg = rec.receiveObjectMessage(config.getHost(), "mainQueue");

                //--System.out.println("pp0 "+receivedMsg);
            }

            System.out.println("\n>>>>>>>>>>>>>>>>>>> MainQueue Received Message");
            sr.affiche(receivedMsg);

            if(sr.instanceFolder == null){
                //CopyPasteFile.writeAtTheEnd(sr.instanceFolder+"/Artefact.xml", "");
                sr.instanceFolder = instance+"/scenario"+0+"/";
            }
        
        //--System.err.println("dddddddddddddddddddddddddd instanceFolder  "+sr.instanceFolder);
        
        List<String> serviceFoundList = sr.findMatchingService(receivedMsg.getOperationName(), schemaExecution, sr.instanceFolder); //0 doit être remplacé par une instance
        int operationChoisie = 0;
        if(serviceFoundList.isEmpty()){
            System.out.println("No service match");
            return;
        }else if(serviceFoundList.size() > 1){
            //Choix d'un service parmi ceux trouver.
            //--System.out.println("Voici les parametres en entrée");
            for(int  l=0;l<receivedMsg.getInParameter().size();l++){
                String item = (String)receivedMsg.getInParameter().get(l);
                //--System.out.println(l+" Name Param = "+item.split(":")[0]+" Content = "+item.split(":")[1]);
            }
            System.out.println("\n=======================================");
            System.out.println("============ Choose Number (it's choose of service) ");
            
            for(int i=0;i<serviceFoundList.size();i++){
                System.out.println("============ "+i+"  to "+sr.displayService("packageService", schemaExecution+"/"+serviceFoundList.get(i).split("::")[2])+"  "+serviceFoundList.get(i).split("::")[2]);
            }
            
            String input = "";
            while(input.equals("")){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
                if(!input.equals("")){
                    try{
                        operationChoisie = Integer.parseInt(input);
                        if(operationChoisie >= serviceFoundList.size()){
                            System.out.println("The number must be smaller than "+serviceFoundList.size());
                            input = "";
                        }
                            
                    }catch(Exception ex){
                        System.out.println("Type Number Please");
                        input = "";
                    }
                }
            }

        }
        
        CopyPasteFile.copyFile(serviceFoundList.get(operationChoisie).split("::")[0], serviceFoundList.get(operationChoisie).split("::")[1], serviceFoundList.get(operationChoisie).split("::")[2]);
        String serviceFound = serviceFoundList.get(operationChoisie).split("::")[3];

        System.out.println("\n ----- Service Choosen  = "+sr.displayService("packageService", schemaExecution+"/"+serviceFoundList.get(operationChoisie).split("::")[2]));

        Service service = RefineAndPersistenceManager.getServiceFile("packageService", serviceFound);
        service = sr.defineContexte(service,receivedMsg,config);
        
        //---System.out.println("HostConfig "+config.getHost());
        
        if(receivedMsg.getListPreviousHostQueue() != null){
            //---System.out.println("HostMsg "+receivedMsg.getListPreviousHostQueue().get(receivedMsg.getListPreviousHostQueue().size()-1).getHost());
            
            if(!config.getHost().equals(receivedMsg.getListPreviousHostQueue().get(receivedMsg.getListPreviousHostQueue().size()-1).getHost())){

                //sr.instanceFolder = instance+"/scenario"+0+"/";
                packageArtefact.Service artefac = tranformServiceToArtefact(service);
                RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact", sr.instanceFolder+"/Artefact.xml");
            }
        }
        
        

        RefineAndPersistenceManager.setServiceFile(service, "packageService", serviceFound);
        Service serv = service;
        
        
        if(service.getType().equals("simple")){
            //Prendre le seul element et consommer
            Use currentUse = service.getUse().get(0);
            
            RefineAndPersistenceManager.treatmentSimpleService(service, currentUse, serviceFound, receivedMsg);

        }else{ //Composite
        
            for(int i=0; i< service.getUse().size(); i++){
                
                if(service.getUse().get(i).getType().equals("simple")){
                
                    RefineAndPersistenceManager.treatmentSimpleService(service, service.getUse().get(i), serviceFound, receivedMsg);
                    
                }else{
                    sr.treatmentCompositeService(config, service,service.getUse().get(i)
                        ,receivedMsg,serviceFound,i);   
                }
                
            }
        }
        
        //--System.out.println("STOPPED -- "+Thread.currentThread().getId());
        Thread.currentThread().stop();
    }
    
    
    /*public static void main(String[] arg){
        try {
            ReceiveSend rs = new ReceiveSend();
            
            SendReceive sr = new SendReceive();
            
            Config config = sr.getConfigFile("configFile", rs.configPath);
            
            rs.receiveSend(config,sr);
            
        } catch (JAXBException | IOException | JSONException | InterruptedException ex) {
            Logger.getLogger(ReceiveSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
}
