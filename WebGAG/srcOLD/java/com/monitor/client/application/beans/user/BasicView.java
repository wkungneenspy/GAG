/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monitor.client.application.beans.user;

import Manager.RefineAndPersistenceManager;
import configFile.GagconfigFile;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBException;
import openjms.Start;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import packageArtefact.Contexte;
import packageArtefact.Service;
import packageArtefact.Item;
import packageArtefact.Provide;
import packageArtefact.Use;
import utils.FileOfFolder;
import utils.RecuperationSorties;

@ManagedBean(name = "treeBasicView")
@ViewScoped
public class BasicView implements Serializable {

    private TreeNode root;

    private TreeNode selectedNode;

    private TreeNode rootScenario;

    private TreeNode selectedNodeScenario;

    private Service artefact;

    private Service artefactScenario;

    private List<Item> inputService;

    private Item selectedInputService;

    private List<Item> outputService;

    private Item selectedOutputService;

    private Service noeudTrouve;

    private List<String> scenarioFolder;

    private String selectedScenario;

    private List<packageService.Service> availableServices;

    private packageService.Service selectedAvailableService;

    private boolean isClosed;

    private boolean isManual;

    private boolean isInputDefine;

    private boolean isOutputDefine;

    private boolean isUse;

    private String serviceName;

    private List<packageService.Item> contexteService;

    private List<packageService.Item> provideService;

    private packageService.Item selectedServ;

    private List<String> param = new ArrayList<String>();

    private List<String> value = new ArrayList<String>();

    private String instanceInProgressPath = null;

    @ManagedProperty("#{dataListView}")
    private DataListView dataListView;

    //1. une fonction qui me dit si un service est manuel ou non
    private Service findServiceInArtefact(Service arte, String serviceName)
            throws JAXBException, IOException {

        for (Use u : arte.getUse()) {

            if (u.getServiceName().equals(serviceName)) {

                System.out.println("J'ai trouvé leeeeeeee");

                Service s = new Service();
                s.setName(u.getServiceName());

                Provide p = new Provide();
                p.setItem(u.getOutputParameter().getItem());
                s.setProvide(p);

                s.setType(u.getType());

                Contexte c = new Contexte();
                c.setItem(u.getInputParameter().getItem());

                List<Object> cc = new ArrayList<Object>();
                cc.add(c);
                s.setServiceOrContexte(cc);

                isManual = isManually(u);
                System.out.println("===> isManual dans recursivité " + isManual);

                noeudTrouve = s;
                return noeudTrouve;
            }
        }

        if (arte.getName().equals(serviceName)) {
            System.out.println("J'ai trouvé " + arte.getName());
            noeudTrouve = arte;
            return noeudTrouve;
        }

        for (int i = 0; i < arte.getServiceOrContexte().size(); i++) {
            Service s = null;
            try {
                s = (Service) arte.getServiceOrContexte().get(i);

                //Juste pour le rafinement côté interface(peut être fait ailleur)
                for (int k = 0; k < s.getProvide().getItem().size(); k++) {
                    if (!param.contains(s.getProvide().getItem().get(k).getName())) {
                        if (!s.getProvide().getItem().get(k).getContent().equals("")) {
                            System.out.println(s.getProvide().getItem().get(k).getName() + " ADDDDDD " + s.getProvide().getItem().get(k).getContent());
                            param.add(s.getProvide().getItem().get(k).getName());
                            value.add(s.getProvide().getItem().get(k).getContent());
                        }
                    }
                }
                //Fin 

                if (s.getName().equals(serviceName)) {
                    System.out.println("J'ai trouvé " + s.getName());
                    noeudTrouve = s;
                    return noeudTrouve;
                } else {
                    findServiceInArtefact(s, serviceName);
                }
            } catch (ClassCastException ex) {
                System.out.println(s + " ==> " + ex.getMessage());
                //gestion des rafinement sur le use
                Contexte c = ((Contexte) arte.getServiceOrContexte().get(i));
                for (int k = 0; k < c.getItem().size(); k++) {
                    if (!param.contains(c.getItem().get(k).getName())) {
                        if (!c.getItem().get(k).getContent().equals("")) {
                            System.out.println(c.getItem().get(k).getName() + " ADDDDDD " + c.getItem().get(k).getContent());
                            param.add(c.getItem().get(k).getName());
                            value.add(c.getItem().get(k).getContent());
                        }
                    }
                }

                //fin de cette gestion
            }

        }
        return null;
    }

    private TreeNode artefactToTree(TreeNode root, Service arte) {

        TreeNode node0 = null;

        //System.out.println("==> ceci " + arte.getName() + " a Use = " + arte.getUse().size());
        for (Use u : arte.getUse()) {
            try {
                //System.out.println(u.getServiceName() + " son isManual = " + isManually(u.getServiceName()));
                node0 = new DefaultTreeNode(new ServiceNode(u.getServiceName(), u.getInputParameter().getItem(), u.getOutputParameter().getItem(), false, true, isManually(u)), root);
            } catch (JAXBException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < arte.getServiceOrContexte().size(); i++) {
            try {
//                for(Use u : arte.getUse())
//                    node0 = new DefaultTreeNode(u.getServiceName(), root);

                Service s = (Service) arte.getServiceOrContexte().get(i);
                //il faut définir le 2eme paramètres
                System.out.println("Je suis " + s.getName() + " J'ai Use ? " + isThatTreeIsResolved(s));

                Contexte c = new Contexte();
                for (int a = 0; a < s.getServiceOrContexte().size(); a++) {
                    try {
                        c = (Contexte) s.getServiceOrContexte().get(a);
                        break;
                    } catch (Exception e) {

                    }
                }

                if (!s.getName().equals(((ServiceNode) root.getData()).getName())) {
                    System.out.println("[[[[[[[[[[[[]]]]]]]]]]]]  " + s.getProvide().getItem().size());
                    node0 = new DefaultTreeNode(new ServiceNode(s.getName(),
                            c.getItem(), s.getProvide().getItem(),
                            isThatTreeIsResolved(s), false, false), root);

                    artefactToTree(node0, s);
                }
            } catch (ClassCastException ex) {

            }

        }

        root.setExpanded(true);
        return root;
    }

    /**
     *
     * @param arte
     * @return
     */
    private boolean isThatTreeIsResolved(Service arte) {

        boolean isFound = true;
        //System.out.println("==> ceci " + arte.getName() + " a Use = " + arte.getUse().size());
        for (Use u : arte.getUse()) {
            isFound = false;
        }

        for (int i = 0; i < arte.getServiceOrContexte().size(); i++) {
            try {
                Service s = (Service) arte.getServiceOrContexte().get(i);

                System.out.println("j'appelle " + s.getName());
                for (Use u : arte.getUse()) {
                    return false;
                }

                isFound = isThatTreeIsResolved(s);
                if (!isFound) {
                    return false;
                }
            } catch (ClassCastException ex) {

            }

        }

        return isFound;
    }

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

            role = gagconfigFile.getRole();

            root = new DefaultTreeNode();

            if (instanceInProgressPath == null) {

                if (!FileUtils.readFileToString(new File(gagconfigFile.getMutexFile() + "instance.txt")).equals("")) {
                    instanceInProgressPath = FileUtils.readFileToString(new File(gagconfigFile.getMutexFile() + "instance.txt"));
                }
            }

            if (instanceInProgressPath != null && !instanceInProgressPath.equals("")) {
                artefact = RefineAndPersistenceManager.getArtefactFile("packageArtefact", instanceInProgressPath + "Artefact.xml");

                System.out.println("=====> ROOT IS RESOLVED  " + isThatTreeIsResolved(artefact));

                Contexte c = new Contexte();
                for (int a = 0; a < artefact.getServiceOrContexte().size(); a++) {
                    try {
                        c = (Contexte) artefact.getServiceOrContexte().get(a);
                        break;
                    } catch (Exception e) {

                    }
                }
                root = new DefaultTreeNode(new ServiceNode(artefact.getName(), c.getItem(), artefact.getProvide().getItem(), isThatTreeIsResolved(artefact), false, false), null);

                artefactToTree(root, artefact);

                root.setExpanded(true);
            }

            //Parcour des repertoires
            File rootFolder = new File(gagconfigFile.getInstanceFolder());
            File[] folders = rootFolder.listFiles((FileFilter) FileFilterUtils.directoryFileFilter());

            Arrays.sort(folders, new Comparator<File>() {
                public int compare(File a, File b) {
                    // do your comparison here returning -1 if a is before b, 0 if same, 1 if a is after b
                    return a.getName().compareTo(b.getName());
                }
            });
            scenarioFolder = new ArrayList<String>();

            for (File f : folders) {
                System.out.println("===> " + f.getName());
                scenarioFolder.add(f.getName());
            }

                //Fin Parcours
    //            root = new DefaultTreeNode("Root", null);
            //            TreeNode node0 = new DefaultTreeNode("Node 0", root);
            //            TreeNode node1 = new DefaultTreeNode("Node 1", root);
            //            
            //            TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
            //            TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);
            //            
            //            TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);
            //            
            //            node1.getChildren().add(new DefaultTreeNode("Node 1.1"));
            //            node00.getChildren().add(new DefaultTreeNode("Node 0.0.0"));
            //            node00.getChildren().add(new DefaultTreeNode("Node 0.0.1"));
            //            node01.getChildren().add(new DefaultTreeNode("Node 0.1.0"));
            //            node10.getChildren().add(new DefaultTreeNode("Node 1.0.0"));
            //            root.getChildren().add(new DefaultTreeNode("Node 2"));
        } catch (JAXBException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void generateForm() {
        System.out.println("Dans Generate form " + availableServices.size());
        if (availableServices != null) {

            contexteService = new ArrayList<packageService.Item>();

            provideService = new ArrayList<packageService.Item>();

            if (selectedAvailableService != null) {

                serviceName = selectedAvailableService.getName().split("::")[1].split("\\(")[0];
                System.out.println("serviceName " + serviceName);
                //identifier le numéro du service.
                try {
                    if (availableServices.size() > 1) {
                        FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), selectedAvailableService.getName().split("::")[0].split("S")[1]);
                        FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");

                        //Tu dors un peu... Tu refresh artefact... et enfin la variable contexteService et provideService
                        Thread.sleep(10000);

                    }

                    //appel le rafinement voir... et ecrire sur le disque
                    //packageArtefact.Service artefac = RefineAndPersistenceManager.getArtefactFile("packageArtefact", instanceInProgressPath+"Artefact.xml");
                    //artefac = parcoursArtefactAndSetProvide(artefac, service);
                    //parcours les uses et rafiner
//ss
                    // RefineAndPersistenceManager.setArtefactFile(artefac, "packageArtefact",  instanceInProgressPath+"Artefact.xml");
                    System.out.println("Avant Init ");
                    //refresh artefact
                    init();
                    System.out.println("Apres Init " + artefact.getName());

                    param = new ArrayList<String>();
                    value = new ArrayList<String>();
                    isOutputDefine = false;
                    isInputDefine = false;

                    //Ajouter les param eventuellement défini
                    if (!paramAdefenir.equals("")) {
                        param.add(paramAdefenir);
                        value.add(valeurAdefinir);
                    }

                    findServiceInArtefact(artefact, serviceName);
                    Service s = noeudTrouve;

                    System.out.println("Service trouvéééé " + s.getName());
                    for (int i = 0; i < s.getProvide().getItem().size(); i++) {
                        packageService.Item p = new packageService.Item();

                        p.setContent(s.getProvide().getItem().get(i).getContent());
                        p.setId(s.getProvide().getItem().get(i).getId());
                        p.setName(s.getProvide().getItem().get(i).getName());
                        p.setRefItem(s.getProvide().getItem().get(i).getRefItem());

                        if (p.getContent() == null || p.getContent().equals("")) {
                            System.out.println("TAILLE DE PARAM " + param.size());
                            System.out.println("TAILLE DE VVALL " + value.size());
                            for (int k = 0; k < param.size(); k++) {
                                System.out.println(p.getName() + " Compare avec " + param.get(k));
                                if (param.get(k).equals(p.getName())) {
                                    p.setContent(value.get(k));
                                    System.out.println("AAAAA DEEEFIIINIII " + value.get(k));
                                }
                            }
                        }

                        if ((p.getContent() == null || p.getContent().equals("")) && isManually(s)) {
                            isOutputDefine = true;
                        }

                        provideService.add(p);
                    }

                    System.out.println("provideService = " + provideService + " isOutputDefine = " + isOutputDefine + " isManual = " + isManually(s) + " serviceName = " + s.getName());
                    for (int i = 0; i < s.getServiceOrContexte().size(); i++) {
                        try {
                            for (Item it : ((Contexte) s.getServiceOrContexte().get(i)).getItem()) {

                                packageService.Item itpack = new packageService.Item();
                                itpack.setContent(it.getContent());

                                itpack.setId(it.getId());
                                itpack.setName(it.getName());
                                itpack.setRefItem(it.getRefItem());

                                if (itpack.getContent() == null || itpack.getContent().equals("")) {
                                    System.out.println("TAILLEI DE PARAM " + param.size());
                                    System.out.println("TAILLEI DE VVALL " + value.size());
                                    for (int k = 0; k < param.size(); k++) {
                                        System.out.println(itpack.getName() + " CompareI avec " + param.get(k));
                                        if (param.get(k).equals(itpack.getName())) {
                                            itpack.setContent(value.get(k));
                                            System.out.println("AAAAA DEEEFIIINIII " + value.get(k));
                                        }
                                    }
                                }

                                if (itpack.getContent() == null || itpack.getContent().equals("")) {
                                    isInputDefine = true;
                                }

                                contexteService.add(itpack);
                            }

                            System.out.println("isInputDefine " + isInputDefine + " =hummmContexte >" + contexteService.size());

                        } catch (ClassCastException ex) {
                            System.out.println("ERRRRRR " + ex.getMessage());

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                } catch (JAXBException ex) {
                    Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }

                System.out.println("-----------> isUse " + isUse + " contexteService " + selectedAvailableService.getContexte().getItem());

                //contexteService = selectedAvailableService.getContexte().getItem();
                //provideService = selectedAvailableService.getProvide().getItem();
            }

        }
    }

    public void serviceDispoAuNoeud() {
        System.out.println("selectedNode " + selectedNode);
        if (selectedNode != null) {
            try {
                isClosed = ((ServiceNode) selectedNode.getData()).isIsResolved();

                availableServices = findMatchingService(((ServiceNode) selectedNode.getData()).getName(), gagconfigFile.getSchemaExecution());
                // now il faut bien formatter pour l'affichage

                if (!((ServiceNode) selectedNode.getData()).isUse) {
                    //packageService.Service service = new packageService.Service();
                    List<packageService.Service> aux = new ArrayList<packageService.Service>();
                    ServiceNode servNode = ((ServiceNode) selectedNode.getData());
                    //lorsqu'il y a plusieur elements dans result, il faut choisir le bon
                    for (packageService.Service s : availableServices) {

                        boolean t = false;

                        if (servNode.getName().equals(s.getName())) {
                            //verifier les paramètres en entrée
                            if (servNode.getInputs().size()
                                    == s.getContexte().getItem().size()) {
                                for (Item i : servNode.getInputs()) {
                                    t = isBelong(i.getName(), s.getContexte().getItem());
                                    if (!t) {
                                        break;
                                    }
                                }
                            }

                            if (!t) {
                                continue;
                            }

                            //verifier les paramètres en sortie
                            if (servNode.getOutputs().size()
                                    == s.getProvide().getItem().size()) {
                                for (Item i : servNode.getOutputs()) {
                                    t = isBelong(i.getName(), s.getProvide().getItem());
                                    if (!t) {
                                        break;
                                    }
                                }
                            }

                            if (t) {
                                aux.add(s);
                                break;
                            }
                        }
                    }
                    availableServices = aux;
                }

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

            } catch (IOException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    boolean defineInput;
    String paramAdefenir = "";
    String valeurAdefinir = "";
    boolean isCorrectService;
    String serviceAchoisir;

    public void displaySelectedSingle() {

        try {
            String x = FileUtils.readFileToString(new File(gagconfigFile.getMutexFile() + "serviceAchoisir.txt"));

            serviceAchoisir = x;

            if (!((ServiceNode) selectedNode.getData()).getName().trim().equals(x.trim())) {
                System.out.println("Not match " + ((ServiceNode) selectedNode.getData()).getName().trim() + "  " + x.trim());
                isCorrectService = false;
                //return;
            } else {
                isCorrectService = true;
            }

            System.out.println("MMMMMMMMMAtch " + ((ServiceNode) selectedNode.getData()).getName().trim() + "  " + x.trim());

        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        defineInput = false;
        String val = "";

        try {
            //1. s'il y a des trucs à donner, alors formulaire pour donner et dormir un peu
            val = FileUtils.readFileToString(new File(gagconfigFile.getMutexFile() + "besoin.txt"));
            //FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"besoin.txt"), "");
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        System.out.println("val val val " + val);
        if (val.length() > 0) {
            defineInput = true;
            paramAdefenir = val;
        }
        //Sinon on fait le reste.
        //init();
        System.out.println("selectedNode " + selectedNode);
        if (selectedNode != null) {
            serviceDispoAuNoeud();

            try {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
                FacesContext.getCurrentInstance().addMessage(null, message);

                inputService = new ArrayList<Item>();
                System.out.println(artefact.getName() + " appel avec  " + ((ServiceNode) selectedNode.getData()).getName());

                Service s = (Service) findServiceInArtefact(artefact, ((ServiceNode) selectedNode.getData()).getName());

                isClosed = ((ServiceNode) selectedNode.getData()).isIsResolved();
                isManual = ((ServiceNode) selectedNode.getData()).isIsManual();
                isInputDefine = false;
                isUse = ((ServiceNode) selectedNode.getData()).isIsUse();

                for (int i = 0; i < noeudTrouve.getServiceOrContexte().size(); i++) {
                    try {
                        for (Item it : ((Contexte) noeudTrouve.getServiceOrContexte().get(i)).getItem()) {
                            if (it.getContent() == null || it.getContent().equals("")) {
                                isInputDefine = true;
                            }
                            inputService.add(it);
                        }

                        System.out.println("=hummm >" + inputService.size());

                    } catch (ClassCastException ex) {
                        System.out.println("ERRRRRR " + ex.getMessage());

                    }
                }

                System.out.println(noeudTrouve + " SSSSSSSS ceci a été trouvé " + s + " Ismanual = " + isManual + " isUse = " + isUse + " isInputDefine = " + isInputDefine);

//                if (!isManual) {
//                } else {
//                    for (int i = 0; i < noeudTrouve.getServiceOrContexte().size(); i++) {
//                        try {
//                            for (Item it : ((Contexte) noeudTrouve.getServiceOrContexte().get(i)).getItem()) {
//                                inputServiceManual.add(it);
//                            }
//
//                            System.out.println("=hummm >" + inputServiceManual.size());
//
//                        } catch (ClassCastException ex) {
//                            System.out.println("ERRRRRR " + ex.getMessage());
//
//                        }
//                    }
//                }
                System.out.println("FOURNUUUUU " + noeudTrouve.getProvide().getItem().size());
                outputService = new ArrayList<Item>();
                for (Item i : noeudTrouve.getProvide().getItem()) {
                    outputService.add(i);
                }

                //item.setName("nameeeeeee "+selectedNode.getData().toString());
                System.out.println(((ServiceNode) selectedNode.getData()).getName() + " ==>> " + inputService.size());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    String nameScenario;
    List<Item> inputScenario = new ArrayList<Item>();
    List<Item> outputScenario = new ArrayList<Item>();

    public void displaySelectedNodeScenario() {
        System.out.println("====> " + ((ServiceNode) selectedNodeScenario.getData()));
        //0. name
        nameScenario = ((ServiceNode) selectedNodeScenario.getData()).getName();
        //2. input
        inputScenario = ((ServiceNode) selectedNodeScenario.getData()).getInputs();
        //3. output
        outputScenario = ((ServiceNode) selectedNodeScenario.getData()).getOutputs();
        //4. contraint
    }

    public String getNameScenario() {
        return nameScenario;
    }

    public void setNameScenario(String nameScenario) {
        this.nameScenario = nameScenario;
    }

    public List<Item> getInputScenario() {
        return inputScenario;
    }

    public void setInputScenario(List<Item> inputScenario) {
        this.inputScenario = inputScenario;
    }

    public List<Item> getOutputScenario() {
        return outputScenario;
    }

    public void setOutputScenario(List<Item> outputScenario) {
        this.outputScenario = outputScenario;
    }

    public int getAvailableServicesSize() {
        //(treeBasicView.getAvailableServicesSize < 2) or
        return availableServices.size();
    }

    public String getServiceAchoisir() {
        return serviceAchoisir;
    }

    public void setServiceAchoisir(String serviceAchoisir) {
        this.serviceAchoisir = serviceAchoisir;
    }

    public boolean isIsCorrectService() {
        return isCorrectService;
    }

    public void setIsCorrectService(boolean isCorrectService) {
        this.isCorrectService = isCorrectService;
    }

    public TreeNode getRoot() {
        return root;
    }

    public Service getArtefact() {
        return artefact;
    }

    public void setArtefact(Service artefact) {
        this.artefact = artefact;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<Item> getInputService() {
        return inputService;
    }

    public void setInputService(List<Item> inputService) {
        this.inputService = inputService;
    }

    public Item getSelectedInputService() {
        return selectedInputService;
    }

    public void setSelectedInputService(Item selectedInputService) {
        this.selectedInputService = selectedInputService;
    }

    public Service getNoeudTrouve() {
        return noeudTrouve;
    }

    public void setNoeudTrouve(Service noeudTrouve) {
        this.noeudTrouve = noeudTrouve;
    }

    public List<Item> getOutputService() {
        return outputService;
    }

    public void setOutputService(List<Item> outputService) {
        this.outputService = outputService;
    }

    public Item getSelectedOutputService() {
        return selectedOutputService;
    }

    public void setSelectedOutputService(Item selectedOutputService) {
        this.selectedOutputService = selectedOutputService;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public boolean isIsManual() {
        return isManual;
    }

    public void setIsManual(boolean isManual) {
        this.isManual = isManual;
    }

    public boolean isIsInputDefine() {
        return isInputDefine;
    }

    public void setIsInputDefine(boolean isInputDefine) {
        this.isInputDefine = isInputDefine;
    }

    public List<packageService.Service> getAvailableServices() {
        return availableServices;
    }

    public void setAvailableServices(List<packageService.Service> availableServices) {
        this.availableServices = availableServices;
    }

    public packageService.Service getSelectedAvailableService() {
        return selectedAvailableService;
    }

    public void setSelectedAvailableService(packageService.Service selectedAvailableService) {
        this.selectedAvailableService = selectedAvailableService;
    }

    public boolean isIsUse() {
        return isUse;
    }

    public void setIsUse(boolean isUse) {
        this.isUse = isUse;
    }

    public List<packageService.Item> getContexteService() {
        return contexteService;
    }

    public void setContexteService(List<packageService.Item> contexteService) {
        this.contexteService = contexteService;
    }

    public List<packageService.Item> getProvideService() {
        return provideService;
    }

    public void setProvideService(List<packageService.Item> provideService) {
        this.provideService = provideService;
    }

    public packageService.Item getSelectedServ() {
        return selectedServ;
    }

    public void setSelectedServ(packageService.Item selectedServ) {
        this.selectedServ = selectedServ;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void chooseService() {

        //    Arrangez les paramètres en entrée et générer le formulaire
        //            S2:: submitDemand (infoMission) <infoReserve> --> checkDemand (infoMission) <infoReserve>
    }

    public void defineInputValeur() {
        try {
            if (valeurAdefinir != null && !valeurAdefinir.equals("")) {
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), valeurAdefinir);
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");
                defineInput = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void defineValeur() {

//////        System.out.println("====> ");
//////        for (packageService.Item i : contexteService) {
//////            try {
//////                System.out.println(i.getName() + " " + i.getContent());
//////                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile()+"toto.txt"), i.getContent());
//////            } //envoyer la valeur dans le fichier toto.
//////            catch (IOException ex) {
//////                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
//////            }
//////        }
        for (packageService.Item i : provideService) {
            try {
                System.out.println(i.getName() + " " + i.getContent());
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "toto.txt"), i.getContent());
                FileUtils.writeStringToFile(new File(gagconfigFile.getMutexFile() + "besoin.txt"), "");
            } //envoyer la valeur dans le fichier toto.
            catch (IOException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void quelValeur() {

        for (Item i : inputService) {
            System.out.println(i.getName() + " " + i.getContent());
        }

        System.out.println("===> ICIIIIIIIIIIIIIIIIII");

        System.out.println("noeudTrouve " + noeudTrouve);
        System.out.println(selectedNode);
        System.out.println(noeudTrouve.getName());
        System.out.println(noeudTrouve.getName() + "  " + ((ServiceNode) selectedNode.getData()).getName());

    }

    public List<packageService.Service> findMatchingService(String operationChoisie, String schemaExecution) throws IOException, JAXBException {
        ArrayList<String> allFileOfAW = (ArrayList<String>) FileOfFolder.scanDir(schemaExecution);
        String serviceFound = null;
        List<String> result = new ArrayList<String>();
        List<packageService.Service> service = new ArrayList<packageService.Service>();
        for (int j = 0; j < allFileOfAW.size(); j++) {
            //--System.out.println(" "+allFileOfAW.get(j));
            if (allFileOfAW.get(j).contains("~")) {
                continue;
            }
            packageService.Service serv = RefineAndPersistenceManager.getServiceFile("packageService", allFileOfAW.get(j));
            if (serv.getName().trim().equals(operationChoisie.trim())) {
                //CopyPasteFile.copyFile(allFileOfAW.get(j), instanceFolder,allFileOfAW.get(j).split("/")[allFileOfAW.get(j).split("/").length-1]);
                serviceFound = /*instanceFolder+*/ allFileOfAW.get(j).split("/")[allFileOfAW.get(j).split("/").length - 1];
                //allFileOfAW.get(j);
                //--System.out.println("8888 "+instanceFolder);
                //this.instanceFolder = instanceFolder;
                //service = serv;

                service.add(serv);

                result.add(allFileOfAW.get(j) + "::" + "instanceFolder" + "::" + allFileOfAW.get(j).split("/")[allFileOfAW.get(j).split("/").length - 1] + "::" + serviceFound);
            }
        }

        return service;

    }

    private boolean isBelong(String s, List<packageService.Item> l) {
        for (packageService.Item i : l) {
            if (i.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isManually(Use us) throws JAXBException, IOException {
        List<packageService.Service> result = findMatchingService(us.getServiceName(), gagconfigFile.getSchemaExecution());

        packageService.Service service = new packageService.Service();

        //lorsqu'il y a plusieur elements dans result, il faut choisir le bon
        for (packageService.Service s : result) {

            boolean t = false;

            if (us.getServiceName().equals(s.getName())) {
                //verifier les paramètres en entrée
                if (us.getInputParameter().getItem().size()
                        == s.getContexte().getItem().size()) {
                    for (Item i : us.getInputParameter().getItem()) {
                        t = isBelong(i.getName(), s.getContexte().getItem());
                        if (!t) {
                            break;
                        }
                    }
                }

                if (!t) {
                    continue;
                }

                //verifier les paramètres en sortie
                if (us.getOutputParameter().getItem().size()
                        == s.getProvide().getItem().size()) {
                    for (Item i : us.getOutputParameter().getItem()) {
                        t = isBelong(i.getName(), s.getProvide().getItem());
                        if (!t) {
                            break;
                        }
                    }
                }

                if (t) {
                    service = s;
                    break;
                }
            }
        }

        for (packageService.Use u : service.getUse()) {
            if (u.getType().equals("simple")) {
                if (u.getServiceName().equals(us.getServiceName())) {
                    return u.isManually().booleanValue();
                }
            }
        }

        return false;
    }

    //Cette fonction ne marche pas bien
    private boolean isManually(Service us) throws JAXBException, IOException {
        List<packageService.Service> result = findMatchingService(us.getName(), gagconfigFile.getSchemaExecution());

        packageService.Service service = new packageService.Service();
        System.out.println("Je recherche " + us.getName());
        System.out.println("List service trouvé " + result.size());

        if (result.size() == 1) {
            service = result.get(0);
        }

        //lorsqu'il y a plusieur elements dans result, il faut choisir le bon
        for (packageService.Service s : result) {

            boolean t = false;

            if (us.getName().equals(s.getName())) {
                //verifier les paramètres en entrée
                Contexte c = null;
                for (int i = 0; i < us.getServiceOrContexte().size(); i++) {

                    try {
                        c = (Contexte) us.getServiceOrContexte().get(i);

                        break;
                    } catch (ClassCastException ex) {
                        System.out.println(s + " ==> " + ex.getMessage());
                    }

                }

                if (c.getItem().size()
                        == s.getContexte().getItem().size()) {
                    for (Item i : c.getItem()) {
                        t = isBelong(i.getName(), s.getContexte().getItem());
                        if (!t) {
                            break;
                        }
                    }
                }

                if (!t) {
                    continue;
                }

                //verifier les paramètres en sortie
                if (us.getProvide().getItem().size()
                        == s.getProvide().getItem().size()) {
                    for (Item i : us.getProvide().getItem()) {
                        t = isBelong(i.getName(), s.getProvide().getItem());
                        if (!t) {
                            break;
                        }
                    }
                }

                if (t) {
                    service = s;
                    break;
                }
            }
        }

        System.out.println("||||||| serviceChoisi " + service.getName());
        for (packageService.Use u : service.getUse()) {
            if (u.getType().equals("simple")) {
                System.out.println("||||||| Type Simple");
                if (u.getServiceName().equals(us.getName())) {
                    System.out.println("||||||| isManualllll " + u.isManually().booleanValue());
                    return u.isManually().booleanValue();
                }
            }
        }

        return false;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        init();

        System.out.println("onNodeExpand() " + event + " event " + event.getTreeNode().isExpanded());
        event.getTreeNode().setExpanded(true);
    }

    public void nodeExpand(NodeExpandEvent event) {
        event.getTreeNode().setExpanded(true);
    }

    public void nodeCollapse(NodeCollapseEvent event) {
        event.getTreeNode().setExpanded(false);
    }

    public boolean isIsOutputDefine() {
        return isOutputDefine;
    }

    public void setIsOutputDefine(boolean isOutputDefine) {
        this.isOutputDefine = isOutputDefine;
    }

    public boolean isDefineInput() {
        return defineInput;
    }

    public void setDefineInput(boolean defineInput) {
        this.defineInput = defineInput;
    }

    public String getParamAdefenir() {
        return paramAdefenir;
    }

    public void setParamAdefenir(String paramAdefenir) {
        this.paramAdefenir = paramAdefenir;
    }

    public String getValeurAdefinir() {
        return valeurAdefinir;
    }

    public void setValeurAdefinir(String valeurAdefinir) {
        this.valeurAdefinir = valeurAdefinir;
    }

    public List<String> getScenarioFolder() {
        return scenarioFolder;
    }

    public void setScenarioFolder(List<String> scenarioFolder) {
        this.scenarioFolder = scenarioFolder;
    }

    public String getSelectedScenario() {
        return selectedScenario;
    }

    public void setSelectedScenario(String selectedScenario) {
        this.selectedScenario = selectedScenario;
    }

    public List<String> getParam() {
        return param;
    }

    public void setParam(List<String> param) {
        this.param = param;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public Service getArtefactScenario() {
        return artefactScenario;
    }

    public void setArtefactScenario(Service artefactScenario) {
        this.artefactScenario = artefactScenario;
    }

    public boolean isThatbackendWorks() {
        try {
            String r = FileUtils.readFileToString(new File(gagconfigFile.getMutexFile() + "work.txt"));

            if (r.equals("")) {
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public String getInstanceInProgressPath() {
        return instanceInProgressPath;
    }

    public void setInstanceInProgressPath(String instanceInProgressPath) {
        this.instanceInProgressPath = instanceInProgressPath;
    }

    public static void main(String arg[]) {

        //BasicView.launchBackend("");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static GagconfigFile getGagconfigFile() {
        return gagconfigFile;
    }

    public static void setGagconfigFile(GagconfigFile gagconfigFile) {
        BasicView.gagconfigFile = gagconfigFile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIsConnect() {
        return isConnect;
    }

    public void setIsConnect(boolean isConnect) {
        this.isConnect = isConnect;
    }

    String login;
    String password;
    String role;
    boolean isConnect;

    public String connexion() {

        isConnect = true;

        if (login.equals(gagconfigFile.getLoginParam().split("###")[0])
                && password.equals(gagconfigFile.getLoginParam().split("###")[1])) {
            return "choixAction2";
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login and/or password Incorrect!", "Incorrect Parameter"));
        return null;
    }

    public String disconnect() {
        isConnect = false;
        return "login";
    }

    public DataListView getDataListView() {
        return dataListView;
    }

    public void setDataListView(DataListView dataListView) {
        this.dataListView = dataListView;
    }

    public void obtainSelectedScenario(String scenario) {
        System.out.println(scenario + " SCENARIOOOOO " + selectedScenario);
        if (scenario != null) {
            try {
                rootScenario = new DefaultTreeNode();

                artefactScenario = RefineAndPersistenceManager.getArtefactFile("packageArtefact", gagconfigFile.getInstanceFolder() + "/" + scenario + "/Artefact.xml");

                System.out.println("=====> ROOT IS RESOLVED  " + isThatTreeIsResolved(artefactScenario));

                Contexte c = new Contexte();
                for (int a = 0; a < artefactScenario.getServiceOrContexte().size(); a++) {
                    try {
                        c = (Contexte) artefactScenario.getServiceOrContexte().get(a);
                        break;
                    } catch (Exception e) {

                    }
                }
                System.out.println("{{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}  " + artefactScenario.getProvide().getItem().size());
                rootScenario = new DefaultTreeNode(new ServiceNode(artefactScenario.getName(), c.getItem(), artefactScenario.getProvide().getItem(), isThatTreeIsResolved(artefactScenario), false, false), null);

                artefactToTree(rootScenario, artefactScenario);

                rootScenario.setExpanded(true);
            } catch (JAXBException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }

        }
    }

    public TreeNode getRootScenario() {
        return rootScenario;
    }

    public void setRootScenario(TreeNode rootScenario) {
        this.rootScenario = rootScenario;
    }

    public TreeNode getSelectedNodeScenario() {
        return selectedNodeScenario;
    }

    public void setSelectedNodeScenario(TreeNode selectedNodeScenario) {
        this.selectedNodeScenario = selectedNodeScenario;
    }

}
