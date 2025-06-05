/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Manager;

import java.util.ArrayList;
import java.util.List;
import object.Msg;
import object.MsgSimple;
import packageConfigFile.Config;
import packageSendReceivePrimitive.SendReceive;
import packageService.Item;
import packageService.Service;
import packageService.Use;

/**
 *
 * @author willyk
 */
public class MessageManager {
    
    //MessageManager
    public static Msg constructMessageToSend(Service globalInstance, Use currentUse, Config config, Msg msgRecu) {

        Msg message = new Msg();
        message.setChannel("queue" + (config.getNumberOfConnection().intValue()));//être dynamique et suivant un certain ordre
        message.setCloseConnection(false);
        message.setHost(config.getHost()); //Hote actuel, prendre cela dans un fichier de config
        message.setOperationName(currentUse.getServiceName());

        List<MsgSimple> listPreviousHost = new ArrayList<>();
        if (msgRecu != null) {
            listPreviousHost = msgRecu.getListPreviousHostQueue();
        }
        
        //Vérifier que tous les paramètres sont disponibles.
        List<Object> listInParam = new ArrayList<>();
        for (int j = 0; j < currentUse.getInputParameter().getItem().size(); j++) {
            Item currentItem = currentUse.getInputParameter().getItem().get(j);
            if (currentItem.getContent().equals("")) {
                listInParam.add(currentItem.getName() + ":" + SendReceive.getValueItemID(globalInstance, currentItem.getRefItem()));
            } else {
                listInParam.add(currentItem.getName() + ":" + currentItem.getContent());
            }
        }
        message.setInParameter(listInParam);
        
        List<Object> listOutParam = new ArrayList<>();
        for (int j = 0; j < currentUse.getOutputParameter().getItem().size(); j++) {
            Item currentItem = currentUse.getOutputParameter().getItem().get(j);
            if (currentItem.getContent().equals("")) {
                listOutParam.add(currentItem.getName() + ":" + SendReceive.getValueItemID(globalInstance, currentItem.getId()));
            } else {
                listOutParam.add(currentItem.getName() + ":" + currentItem.getContent());
            }
        }
        message.setOutParameter(listOutParam);
        
        
        
        //Construire l'element à ajouter dans listPreviousHost
        MsgSimple msgSimple = new MsgSimple();
        msgSimple.setChannel("queue" + (config.getNumberOfConnection().intValue()));
        msgSimple.setCloseConnection(false);
        msgSimple.setHost(config.getHost());
        msgSimple.setOperationName(currentUse.getServiceName());
        msgSimple.setOutParameter(listOutParam);
        msgSimple.setInParameter(listInParam);
        
        listPreviousHost.add(msgSimple);
        message.setListPreviousHostQueue(listPreviousHost);
        
        return message;
    }
    
}
