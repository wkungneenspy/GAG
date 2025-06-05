/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willyk
 */
public class LaunchOpenJMS extends Thread{
    //static String  openJMSHOME = "/home/willyk/dev/openjms-0.7.7-beta-1";
    
    public static void startupJMS(final String openJMSHOME){
        //String[] cmd = {  "/bin/sh", "-c", openJMSHOME+"/bin/startup.sh" };
        String[] cmd = {"cmd", "/c", openJMSHOME+"/bin/startup.bat"};
        System.out.println("--------Start startup JMS");
        //new Thread() {
            //@Override public void run() {
                try {
                    Process p = Runtime.getRuntime().exec("cmd /c "+openJMSHOME+"/bin/startup.bat");
     System.out.println("1");     
                    /* Lancement du thread de récupération de la sortie standard */
                    new RecuperationSorties(p.getInputStream()).start();
     System.out.println("2");               
                    /* Lancement du thread de récupération de la sortie en erreur */
                    new RecuperationSorties(p.getErrorStream()).start();
     System.out.println("3");               
                    LaunchOpenJMS.sleep(10000);
     System.out.println("4");                 
                    p.getOutputStream().close();
     System.out.println("5");                 
                    //p.getInputStream().close();
     System.out.println("6");  
                } catch (IOException ex) {
                    System.err.println("77777777");
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    System.err.println("88888888");
                    Logger.getLogger(LaunchOpenJMS.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
          //  }
        //}.run();
        //Process p = Runtime.getRuntime().exec(cmd);
        //int exitValue = p.waitFor();
        
        System.out.println("--------End startup JMS \n");
    }
    
    
    public static void shutdownJMS(final String openJMSHOME){
        //String[] cmd = {  "/bin/sh", "-c", openJMSHOME+"/bin/shutdown.sh" };
        String[] cmd = {"cmd.exe", "/c", openJMSHOME+"/bin/shutdown.bat"};
        System.out.println("\n----Start shutdown JMS");
        new Thread() {
            @Override public void run() {
                try {
                    Process p = Runtime.getRuntime().exec("cmd /c "+openJMSHOME+"/bin/shutdown.bat");
                    
                    /* Lancement du thread de récupération de la sortie standard */
                    new RecuperationSorties(p.getInputStream()).start();
                    
                    /* Lancement du thread de récupération de la sortie en erreur */
                    new RecuperationSorties(p.getErrorStream()).start();
                    
                    LaunchOpenJMS.sleep(10000);
                    p.getOutputStream().close();
                    p.getInputStream().close();
                } catch (IOException ex) {
                    
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    Logger.getLogger(LaunchOpenJMS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.run();
        //willy Process p = Runtime.getRuntime().exec(cmd);
        
        /* Lancement du thread de récupération de la sortie standard */
        //willynew RecuperationSorties(p.getInputStream()).start();
        
        /* Lancement du thread de récupération de la sortie en erreur */
        //willynew RecuperationSorties(p.getErrorStream()).start();
        
        
        //int exitValue = p.waitFor();
        //willy LaunchOpenJMS.sleep(10000);
        //willy p.getOutputStream().close();
        //willy p.getInputStream().close();
        System.out.println("----End shutdown JMS \n");
    }
    
    
    public static void main(String arg[]){
        
        LaunchOpenJMS.startupJMS("C:/gag/openjms-0.7.7-beta-1");
        
//        String[] cmd = {  "", "-c", "lsof -i:3035" };
//        try {
//            System.out.println("\n---- Start shutdown JMS");
//            Process p = Runtime.getRuntime().exec("lsof -i:3035");
//            
//            System.out.println("---\n "+RecuperationSorties.nothingValue());
//            /* Lancement du thread de récupération de la sortie standard */
//            new RecuperationSorties(p.getInputStream()).start();
//            
//            /* Lancement du thread de récupération de la sortie en erreur */
//            new RecuperationSorties(p.getErrorStream()).start();
//
//            
//            //int exitValue = p.waitFor(); 
//            LaunchOpenJMS.sleep(10000);
//            p.getOutputStream().close();
//            p.getInputStream().close();
//            System.out.println("\n---- End shutdown JMS \n");
//            
//        } catch (IOException | InterruptedException e) {
//            
//            System.out.println(" "+e.getMessage());
//            
//        }
    }
    
}
