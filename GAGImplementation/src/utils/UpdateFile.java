/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willyk
 */
public class UpdateFile {
    
    public static int queueNumber(String configFile, String queueName){
        
        int queueNumber = Integer.parseInt(queueName.split("queue")[1]);
        
        try {
            Scanner sc = new Scanner(new File(configFile));
            while (sc.hasNextLine())
            {
                
                String s = sc.nextLine();
                if(s.contains(queueName)){
                    queueNumber = queueNumber + 1;
                    queueName = "queue"+queueNumber;
                }
                    
                //System.out.println(s);
            }
            sc.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return queueNumber;
    }
    
    public static void addNewQueue(String configFile, String queueName){
                    
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(configFile));
                String line = "";
                String result = "";
                while( (line = in.readLine()) != null ){
                    //System.out.println(line);
                    if(!result.equals("")){
                        result = result+"\n"+line;
                        if(line.contains("</AdministeredTopic>")){
                            line = in.readLine();
                            result = result+"\n"+line;
                            result = result+"\n"+"    <AdministeredQueue name=\""+queueName+"\" />";
                        }
                    }else
                        result=line;
                }
                
                
                BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
                out.write(result, 0, result.length());
                out.flush();
                out.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }
    
    public static void removeQueue(String configFile, String queueName){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(configFile));
            String line = "";
            String result = "";
                while( (line = in.readLine()) != null ){
                    //--System.out.println(line);
                    if(!result.equals("")){
                        if(!line.contains(queueName)){
                            result = result+"\n"+line;
                        }
                    }else
                        result=line;
                }
             
            
            BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
            out.write(result, 0, result.length());
            out.flush();
            out.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(UpdateFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String arg[]){
        //UpdateFile.addNewQueue("/home/willykk/Fichier.xml", "queue3");
        
        //String[] cmd = {  "/bin/sh", "-c", "/home/willykk/dev/openjms-0.7.7-beta-1/bin/shutdown.sh" };
         String[] cmd = {"cmd", "/c", "C:\\gag\\openjms-0.7.7-beta-1\\"+"\\bin\\startup.bat"};
        try {
            Process p = Runtime.getRuntime().exec("cmd /c C:\\gag\\openjms-0.7.7-beta-1\\bin\\startup.bat");
            Thread.sleep(10000);
            //new RecuperationSorties(p.getInputStream()).start();

            /* Lancement du thread de récupération de la sortie en erreur */
            //new RecuperationSorties(p.getErrorStream()).start();
            p.getOutputStream().close();
            p.getInputStream().close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
