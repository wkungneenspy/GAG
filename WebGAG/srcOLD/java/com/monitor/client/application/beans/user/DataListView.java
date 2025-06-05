/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monitor.client.application.beans.user;

import Manager.RefineAndPersistenceManager;
import static com.monitor.client.application.beans.user.BasicView.gagconfigFile;
import configFile.GagconfigFile;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBException;
import openjms.Start;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import packageService.Contexte;
import packageService.Item;
import packageService.Provide;
import packageService.Service;
import packageService.Use;
import utils.LConfigLoader;
import utils.RecuperationSorties;

@ManagedBean
@ViewScoped
public class DataListView implements Serializable {

    private List<Service> services1;

    private Service selectedService;
    
    public static GagconfigFile getGagconfigFile() {
        return gagconfigFile;
    }

    public static void setGagconfigFile(GagconfigFile gagconfigFile) {
        DataListView.gagconfigFile = gagconfigFile;
    }
    
    public String getItem(List<Item> listItem) {

        String listI = "";

        for (Item i : listItem) {

            listI = i.getName() + "," + listI;

        }

        return listI.split(",")[0];
    }

    public String getUse(List<Use> listUse) {

        String result = "";

        for (Use i : listUse) {
            String listI = i.getServiceName();

            listI = listI + " (" + getItem(i.getInputParameter().getItem()) + ") <" + getItem(i.getOutputParameter().getItem()) + "> ";

            result = result + listI;
        }

        return result;
    }

    //sudo ps -ef | grep GAGImplementation.jar
    public static GagconfigFile gagconfigFile;

    {
        Start start = new Start();
        try {
            gagconfigFile = start.loadConfigFile("C:\\gag\\GagconfigFile.json");
        } catch (Exception ex) {
            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @PostConstruct
    public void init() {
        try {
            ///isGrey = true;
            
            services1 = new ArrayList<Service>();
            //Allez chercher les services sur le disque et remplir services1
            GagconfigFile gagConfigFile = new GagconfigFile();
            gagConfigFile.getInstanceFolder();

            File actual = new File(gagconfigFile.getSchemaExecution());
            int i = 1;
            for (File f : actual.listFiles()) {
                if (!f.getAbsolutePath().contains("~")) {
                    packageService.Service service = RefineAndPersistenceManager.getServiceFile("packageService", f.getAbsolutePath());

                    Service s = new Service();
                    Contexte c = service.getContexte();
                    Provide p = service.getProvide();

                    if (service.getType().equals("simple")) {
                        s.setName(/*"S" + (i++) + ":: " +*/service.getName() + " (" + getItem(c.getItem()) + ")" + " <" + getItem(p.getItem()) + "> -->");
                    } else {
                        s.setName(/*"S" + (i++) + ":: " +*/service.getName() + " (" + getItem(c.getItem()) + ")" + " <" + getItem(p.getItem()) + ">  --> " + getUse(service.getUse()));
                    }

                    s.setContexte(c);
                    s.setProvide(p);
                    s.setType(s.getType());

                    if (services1.isEmpty()) {
                        services1.add(s);
                    } else {
                        boolean alreadyInsert = false;
                        for (int k = 0; k < services1.size(); k++) {
                            if (contain(s.getName(), services1.get(k).getName().split("\\(")[0].trim())) {
                                //inserer avant
                                List<Service> gauche = services1.subList(0, k);
                                List<Service> droite = services1.subList(k, services1.size());
                                services1 = new ArrayList<Service>();
                                services1.addAll(gauche);
                                services1.add(s);
                                services1.addAll(droite);
                                alreadyInsert = true;
                                break;
                            }
                        }
                        if (!alreadyInsert) {
                            services1.add(s);
                        }
                    }

                }
            }

            for (int k = 0; k < services1.size(); k++) {
                services1.get(k).setName("S" + (k) + ":: " + services1.get(k).getName());
                services1.set(k, services1.get(k));
            }

        } catch (JAXBException ex) {
            Logger.getLogger(DataListView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    boolean contain(String s1, String s2) {
        return s1.contains(s2);
    }

    public List<Service> getServices1() {
        return services1;
    }

    public void setServices1(List<Service> services1) {
        this.services1 = services1;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public void onRowDbselectEvent(SelectEvent event) {
//        FacesMessage msg = new FacesMessage("Document DB Selected", ((EvenementBean) event.getObject()).getIdEven() + "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);

        System.out.println("Selected " + selectedService);
    }

    public void onNodeSelectListener(NodeSelectEvent e) {
        if (e.isContextMenu()) {
            // right click has been fired
            System.out.println("Selected Droiit " + e.getTreeNode().getData());
        } else {
            // left click has been fired
            System.out.println("Selected Gauche " + e.getTreeNode().getData());
        }
    }

    private boolean isManyService;
    private boolean isNotSimple;
    //private boolean isGrey;

    private List<packageService.Service> availableServices;

    private packageService.Service selectedAvailableService;

    private String serviceChoisie;

    public void serviceDispoAuNoeud() {
        System.out.println("selectedService " + selectedService);
        isManyService = false;
        isNotSimple = false;
        if (selectedService != null) {
            try {

                serviceChoisie = selectedService.getName().split("::")[1].split("\\(")[0].trim();

                BasicView basicView = new BasicView();

                availableServices = basicView.findMatchingService(selectedService.getName().split("::")[1].split("\\(")[0], gagconfigFile.getSchemaExecution());
                // now il faut bien formatter pour l'affichage

                System.out.println(selectedService.getName().split("::")[1].split("\\(")[0] + " availableServices  availableServices  " + availableServices.size());
//////////                if (!((ServiceNode) selectedService.getData()).isUse) {
//////////                    //packageService.Service service = new packageService.Service();
//////////                    List<packageService.Service> aux = new ArrayList<packageService.Service>();
//////////                    ServiceNode servNode = ((ServiceNode) selectedService.getData());
//////////                    //lorsqu'il y a plusieur elements dans result, il faut choisir le bon
//////////                    for (packageService.Service s : availableServices) {
//////////
//////////                        boolean t = false;
//////////
//////////                        if (servNode.getName().equals(s.getName())) {
//////////                            //verifier les paramètres en entrée
//////////                            if (servNode.getInputs().size()
//////////                                    == s.getContexte().getItem().size()) {
//////////                                for (Item i : servNode.getInputs()) {
//////////                                    t = isBelong(i.getName(), s.getContexte().getItem());
//////////                                    if (!t) {
//////////                                        break;
//////////                                    }
//////////                                }
//////////                            }
//////////
//////////                            if (!t) {
//////////                                continue;
//////////                            }
//////////
//////////                            //verifier les paramètres en sortie
//////////                            if (servNode.getOutputs().size()
//////////                                    == s.getProvide().getItem().size()) {
//////////                                for (Item i : servNode.getOutputs()) {
//////////                                    t = isBelong(i.getName(), s.getProvide().getItem());
//////////                                    if (!t) {
//////////                                        break;
//////////                                    }
//////////                                }
//////////                            }
//////////
//////////                            if (t) {
//////////                                aux.add(s);
//////////                                break;
//////////                            }
//////////                        }
//////////                    }
//////////                    availableServices = aux;
//////////                }

                DataListView dlv = new DataListView();
                List<packageService.Service> services = new ArrayList<packageService.Service>();

                int i = 0;
                for (packageService.Service service : availableServices) {
                    packageService.Service s = new packageService.Service();
                    packageService.Contexte c = service.getContexte();
                    packageService.Provide p = service.getProvide();

                    if (service.getType().equals("simple")) {
                        s.setName("S" + (i++) + ":: " + service.getName() + " (" + dlv.getItem(c.getItem()) + ")" + " <" + dlv.getItem(p.getItem()) + "> -->");
                    } else {
                        s.setName("S" + (i++) + ":: " + service.getName() + " (" + dlv.getItem(c.getItem()) + ")" + " <" + dlv.getItem(p.getItem()) + ">  --> " + dlv.getUse(service.getUse()));
                    }

                    s.setContexte(c);
                    s.setProvide(p);
                    s.setType(service.getType());
                    services.add(s);
                }

                availableServices = services;

                System.out.println("availableServices22222222 " + availableServices.size());
                if (availableServices.size() > 1) {
                    isManyService = true;
                }

                if (availableServices.size() == 1) {
                    if (availableServices.get(0).getType().equalsIgnoreCase("simple")) {
                        isNotSimple = true;
                    }
                }

                System.out.println("isManySrvice " + isManyService);

            } catch (IOException ex) {
                Logger.getLogger(BasicView.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(BasicView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void generateForm() {
        isManyService = false;
        System.out.println("Dans Generate form " + availableServices.size());
        if (availableServices != null) {

            if (selectedAvailableService != null) {

                String serviceName = selectedAvailableService.getName().split("::")[1].split("\\(")[0].trim();
                System.out.println("serviceName " + serviceName + " TYPEEEEEE " + selectedAvailableService.getType());
                isNotSimple = false;
                //identifier le numéro du service.
                try {
                    if (!selectedAvailableService.getType().equalsIgnoreCase("simple")) {
                        if (availableServices.size() > 1) {
                            System.out.println("ecrire le service choisie " + serviceChoisie);

                            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), serviceChoisie);
                            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");
                            Thread.sleep(4000);

                            System.out.println("ecrire le choixxxx " + selectedAvailableService.getName().split("::")[0].split("S")[1].trim());

                            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), selectedAvailableService.getName().split("::")[0].split("S")[1].trim());
                            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");

                            //Tu dors un peu... Tu refresh artefact... et enfin la variable contexteService et provideService
                            Thread.sleep(2000);

                        }
                    } else {
                        isNotSimple = true;
                    }

                    System.out.println("selectedAvailableService " + selectedAvailableService.getContexte().getItem().size());

//                            for (Item it : selectedAvailableService.getContexte().getItem()) {
//
//                                packageService.Item itpack = new packageService.Item();
//                                itpack.setContent(it.getContent());
//
//                                itpack.setId(it.getId());
//                                itpack.setName(it.getName());
//                                itpack.setRefItem(it.getRefItem());
//                            }
                } catch (IOException ex) {
                    Logger.getLogger(BasicView.class
                            .getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BasicView.class
                            .getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }

        }
    }

    public void chooseServicee() {

        serviceDispoAuNoeud();

    }

    public void defineInputValeurMoreService() {
        try {
            //Le nom a déjà été envoyé lors de la selection du service
            if (availableServices.size() == 1) {
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), selectedAvailableService.getName().split("::")[1].split("\\(")[0].trim());
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");

                Thread.sleep(3000);
            }

            for (Item i : selectedAvailableService.getContexte().getItem()) {
                try {
                    System.out.println("==> " + i.getName());
                    System.out.println("==> " + i.getContent());

                    FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), i.getContent());
                    FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");

                    Thread.sleep(3000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(DataListView.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DataListView.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            //selectedAvailableService.getName().split("::")[1].split("\\(")[0].trim()
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "serviceEnCours.txt"), selectedAvailableService.getName().split("::")[1].split("\\(")[0].trim());

        } catch (IOException ex) {
            Logger.getLogger(DataListView.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DataListView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void defineInputValeur() {
        try {
            //récupérer toutes les informations sur le service selectionné
            //1. si c'est un service simple alors ne rien déclencher
            //2. s'il y a plusieurs, alors selectionner le bon

            //ooo
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), selectedService.getName().split("::")[1].split("\\(")[0].trim());
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");

            Thread.sleep(4000);

            for (Item i : selectedService.getContexte().getItem()) {
                System.out.println("==> " + i.getName());
                System.out.println("==> " + i.getContent());

                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), i.getContent());
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");

                Thread.sleep(4000);
            }

            //selectedAvailableService.getName().split("::")[1].split("\\(")[0].trim()
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "serviceEnCours.txt"), selectedService.getName().split("::")[1].split("\\(")[0].trim());
            
            //isGrey = true;
            
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DataListView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isIsManyService() {
        return isManyService;
    }

    public void setIsManyService(boolean isManyService) {
        this.isManyService = isManyService;
    }

    public List<Service> getAvailableServices() {
        return availableServices;
    }

    public void setAvailableServices(List<Service> availableServices) {
        this.availableServices = availableServices;
    }

    public Service getSelectedAvailableService() {
        return selectedAvailableService;
    }

    public void setSelectedAvailableService(Service selectedAvailableService) {
        this.selectedAvailableService = selectedAvailableService;
    }

    public String getServiceChoisie() {
        return serviceChoisie;
    }

    public void setServiceChoisie(String serviceChoisie) {
        this.serviceChoisie = serviceChoisie;
    }

    public boolean isIsNotSimple() {
        return isNotSimple;
    }

    public void setIsNotSimple(boolean isNotSimple) {
        this.isNotSimple = isNotSimple;
    }

    public String definirModeLancement() {
        try {
            if (!FileUtils.readFileToString(new File(gagconfigFile.getMutexFile()+"backendRun.txt")).equals("yes")) {
                launchBackend("");

                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"toto.txt"), "1");
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"besoin.txt"), "");
     
                //isGrey = false;
                
                return "choixAction2";
            }
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void launchBackend(String openJMSHOME) {

       // String[] cmd = {"/bin/sh", "-c", gagconfigFile.getMutexFile()+"launchBackend.sh"};
       String[] cmd = {"cmd.exe", "/c", gagconfigFile.getMutexFile()+"launchBackend.bat"};
        try {
            System.out.println("--------Start backend JMS");
            Process p = Runtime.getRuntime().exec(cmd);

            /* Lancement du thread de récupération de la sortie standard */
            new RecuperationSorties(p.getInputStream()).start();

            /* Lancement du thread de récupération de la sortie en erreur */
            new RecuperationSorties(p.getErrorStream()).start();
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"backendRun.txt"), "yes");
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"toto.txt"), "");
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"work.txt"), "");
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"besoin.txt"), "");
            FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"instance.txt"), "");

            //int exitValue = p.waitFor(); 
            Thread.sleep(10000);
            p.getOutputStream().close();
            p.getInputStream().close();

            System.out.println("--------End Start Backend JMS \n");
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] ag) {
        try {
            Start st = new Start();
            GagconfigFile gagconfigFile = st.loadConfigFile("C:\\gag\\GagconfigFile.json"/*"/home/willyk/willyk/dev/version13/ActivesWorkSpace/GagconfigFile.json"*/);
            System.out.println(gagconfigFile.getConfigPath());
            System.out.println(gagconfigFile.getInstanceFolder());
            System.out.println(gagconfigFile.getMutexFile());
            System.out.println(gagconfigFile.getOpenJMSConfigPath());

        } catch (Exception ex) {
            Logger.getLogger(DataListView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
