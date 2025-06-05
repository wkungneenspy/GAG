/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package object;

import packageService.Contexte;
import packageService.InputParameter;
import packageService.Item;
import packageService.OutputParameter;
import packageService.Provide;
import packageService.Service;
import packageService.Use;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author willyk
 */
public class TreatementServiceObject {
    
    public ServiceObject transformServiceToObject(Service serv){
        ServiceObject servObject = new ServiceObject();
        
        servObject.setServiceName(serv.getName());
        servObject.setType(serv.getType());
        
        //Traiter le contexte
        List<ItemObject> listItemContext = new ArrayList<>();
        for(int i=0; i<serv.getContexte().getItem().size();i++){
            ItemObject itemObject = new ItemObject();
            
            itemObject.setContent(serv.getContexte().getItem().get(i).getContent());
            itemObject.setId(serv.getContexte().getItem().get(i).getId());
            itemObject.setName(serv.getContexte().getItem().get(i).getName());
            itemObject.setRefItem(serv.getContexte().getItem().get(i).getRefItem());
            
            listItemContext.add(itemObject);
        }
        servObject.setListContext(listItemContext);
                
        List<UseObject> listUse = new ArrayList<>();
        for(int i=0; i<serv.getUse().size();i++){
            UseObject useObject = new UseObject();
            
            List<ItemObject> listInput = new ArrayList<>();
            for(int j=0; j<serv.getUse().get(i).getInputParameter().getItem().size();j++){
                ItemObject itemObject = new ItemObject();

                itemObject.setContent(serv.getUse().get(i).getInputParameter().getItem().get(j).getContent());
                itemObject.setId(serv.getUse().get(i).getInputParameter().getItem().get(j).getId());
                itemObject.setName(serv.getUse().get(i).getInputParameter().getItem().get(j).getName());
                itemObject.setRefItem(serv.getUse().get(i).getInputParameter().getItem().get(j).getRefItem());

                listInput.add(itemObject);
            }
            useObject.setInputParam(listInput);
            
            List<ItemObject> listOutput = new ArrayList<>();
            for(int j=0; j<serv.getUse().get(i).getOutputParameter().getItem().size();j++){
                ItemObject itemObject = new ItemObject();

                itemObject.setContent(serv.getUse().get(i).getOutputParameter().getItem().get(j).getContent());
                itemObject.setId(serv.getUse().get(i).getOutputParameter().getItem().get(j).getId());
                itemObject.setName(serv.getUse().get(i).getOutputParameter().getItem().get(j).getName());
                itemObject.setRefItem(serv.getUse().get(i).getOutputParameter().getItem().get(j).getRefItem());

                listOutput.add(itemObject);
            }
            useObject.setOutputParam(listOutput);
        }
        servObject.setListUse(listUse);
        
        //List des Provides
        List<ItemObject> listItemProvide = new ArrayList<>();
        for(int i=0; i<serv.getProvide().getItem().size();i++){
            ItemObject itemObject = new ItemObject();
            
            itemObject.setContent(serv.getProvide().getItem().get(i).getContent());
            itemObject.setId(serv.getProvide().getItem().get(i).getId());
            itemObject.setName(serv.getProvide().getItem().get(i).getName());
            itemObject.setRefItem(serv.getProvide().getItem().get(i).getRefItem());
            
            listItemProvide.add(itemObject);
        }
        servObject.setListProvid(listItemProvide);
        
        return servObject;
    }
    
    
    public Service transformObjectToService(ServiceObject servObject){
        Service serv = new Service();
        
        serv.setName(serv.getName());
        serv.setType(serv.getType());
        
        Contexte c = new Contexte();
        for(int i=0;i<servObject.getListContext().size();i++){
            Item item = new Item();
            item.setContent(servObject.getListContext().get(i).getContent());
            item.setId(servObject.getListContext().get(i).getId());
            item.setName(servObject.getListContext().get(i).getName());
            //item.setRefItem(null);
            
            c.getItem().add(item);
        }
        serv.setContexte(c);
        
        
        Provide p = new Provide();
        for(int i=0;i<servObject.getListProvid().size();i++){
            Item item = new Item();
            item.setContent(servObject.getListProvid().get(i).getContent());
            item.setId(servObject.getListProvid().get(i).getId());
            item.setName(servObject.getListProvid().get(i).getName());
            item.setRefItem(servObject.getListProvid().get(i).getRefItem());
            
            p.getItem().add(item);
        }
        serv.setProvide(p);
        
        
        for(int i=0;i<servObject.getListUse().size();i++){
            Use use = new Use();
            
            use.setLocation(servObject.getListUse().get(i).getLocation());
            
            if(servObject.getListUse().get(i).getType().equals("simple"))
                use.setManually(servObject.getListUse().get(i).isManually());
            
            use.setOrder(servObject.getListUse().get(i).isOrder());
            use.setServiceName(servObject.getListUse().get(i).getServiceName());
            use.setType(servObject.getListUse().get(i).getType());
            
            InputParameter ip = new InputParameter();
            for(int l=0;l<servObject.getListUse().get(i).getInputParam().size();l++){
                Item item = new Item();
                item.setContent(servObject.getListUse().get(i).getInputParam().get(l).getContent());
                item.setId(servObject.getListUse().get(i).getInputParam().get(l).getId());
                item.setName(servObject.getListUse().get(i).getInputParam().get(l).getName());
                item.setRefItem(servObject.getListUse().get(i).getInputParam().get(l).getRefItem());
                
                ip.getItem().add(item);
            }
            use.setInputParameter(ip);
        
            
            OutputParameter op = new OutputParameter();
            for(int l=0;l<servObject.getListUse().get(i).getOutputParam().size();l++){
                Item item = new Item();
                item.setContent(servObject.getListUse().get(i).getOutputParam().get(l).getContent());
                item.setId(servObject.getListUse().get(i).getOutputParam().get(l).getId());
                item.setName(servObject.getListUse().get(i).getOutputParam().get(l).getName());
                //item.setRefItem(servObject.getListUse().get(i).getOutputParam().get(l).getRefItem());
                
                op.getItem().add(item);
            }
            use.setOutputParameter(op);

            serv.getUse().add(use);
        }
        
        return serv;
    }
    
    public static void main(String arg[]){
        
    }
}
