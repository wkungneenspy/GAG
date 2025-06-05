/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openjms;

import Manager.RefineAndPersistenceManager;
import packageSendReceivePrimitive.ReceiveSend;
import packageSendReceivePrimitive.SendReceive;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import packageConfigFile.Config;

import org.json.JSONException;
import configFile.GagconfigFile;
import java.io.File;
import org.apache.commons.io.FileUtils;
import utils.FileOfFolder;
import utils.LConfigLoader;

/**
 *
 * @author willykk
 */
public class Start {

    public static GagconfigFile gagconfigFile;

    {
        try {
            gagconfigFile = LConfigLoader.getConfigFromJSON(GagconfigFile.class, "C:/gag/GagconfigFile.json");
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GagconfigFile loadConfigFile(String GagconfigFilePath) throws Exception {
        GagconfigFile gagconfigFile = null;
        try {
            return gagconfigFile = LConfigLoader.getConfigFromJSON(GagconfigFile.class, GagconfigFilePath);

        } catch (Exception ex) {

            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);

            throw new Exception(ex);
        }
    }

    //static String instanceFolder;
    public void start(String GagconfigFilePath) {
        //1. Charger le fichier qui contient les repertoires
        GagconfigFile gagconfigFile = null;
        try {
            gagconfigFile = LConfigLoader.getConfigFromJSON(GagconfigFile.class, GagconfigFilePath);

        } catch (Exception ex) {

            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);

        }

        //2. Demander à l'utilisateur de choisir s'il lance le truc en
        //2.1 mode ecoute ou en
        //2.2 mode envoie
        System.out.println("-----------------------------------------------");
        System.out.println("--> Choose Operation");
        System.out.println("--> Type 1 to launch sent mode Or");
        System.out.println("--> Type 2 to launch receive mode");
        System.out.println("-----------------------------------------------");
        int mode = 0;
        while (mode != 1 && mode != 2) {
            //Choix d'un service
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                mode = Integer.parseInt(FileOfFolder.lireValeur(new File(gagconfigFile.getMutexFile() + "toto.txt"))); //Integer.parseInt(br.readLine());
                System.out.println("MODE " + mode);
                if (mode != 1 && mode != 2) {
                    System.out.println("Please type 1 or 2");
                    System.out.println("Begin");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Please type 1 or 2");
                System.out.println("Begin");
            }
        }

        if (mode == 1) {
            try {
                SendReceive sr = new SendReceive(gagconfigFile.getConfigPath(), gagconfigFile.getOpenJMSConfigPath(),
                        gagconfigFile.getSchemaExecution(), gagconfigFile.getInstanceFolder(),
                        gagconfigFile.getOpenJMSHOME());
                sr.sendReceive(null);
            } catch (JAXBException | IOException | JSONException | InterruptedException ex) {

                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);

                System.out.println("Cette erreur s'est produite");

                System.err.println(ex.getMessage());
            }
        } else {
            try {
                //mode reception
                ReceiveSend rs = new ReceiveSend(gagconfigFile.getConfigPath(),
                        gagconfigFile.getSchemaExecution(), gagconfigFile.getInstanceFolder(), gagconfigFile.getOpenJMSHOME());

                SendReceive sr = new SendReceive(gagconfigFile.getConfigPath(), gagconfigFile.getOpenJMSConfigPath(),
                        gagconfigFile.getSchemaExecution(), gagconfigFile.getInstanceFolder(),
                        gagconfigFile.getOpenJMSHOME());

                Config config = RefineAndPersistenceManager.getConfigFile("packageConfigFile", gagconfigFile.getConfigPath());

                rs.receiveSend(config, sr);

            } catch (JAXBException | IOException | JSONException | InterruptedException ex) {

                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);

                System.out.println("Cette erreur s'est produite");

                System.err.println(ex.getMessage());
            }
        }
    }
}
