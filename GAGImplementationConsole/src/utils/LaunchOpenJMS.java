/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.IOException;

/**
 *
 * @author willyk
 */
public class LaunchOpenJMS extends Thread{
    //static String  openJMSHOME = "/home/willyk/dev/openjms-0.7.7-beta-1";
    
    public static void startupJMS(String openJMSHOME){
        String[] cmd = {  "/bin/sh", "-c", openJMSHOME+"/bin/startup.sh" };
        try {
            System.out.println("--------Start startup JMS");
            Process p = Runtime.getRuntime().exec(cmd);
            
            /* Lancement du thread de récupération de la sortie standard */
            new RecuperationSorties(p.getInputStream()).start();
 
            /* Lancement du thread de récupération de la sortie en erreur */
            new RecuperationSorties(p.getErrorStream()).start();
            
            //int exitValue = p.waitFor(); 
            LaunchOpenJMS.sleep(10000);
            p.getOutputStream().close();
            p.getInputStream().close();
            System.out.println("--------End startup JMS \n");
        } catch (IOException | InterruptedException e) {
            System.out.println(" "+e.getMessage());
        }
    }
    
    
    public static void shutdownJMS(String openJMSHOME){
        String[] cmd = {  "/bin/sh", "-c", openJMSHOME+"/bin/shutdown.sh" };
        try {
            System.out.println("\n----Start shutdown JMS");
            Process p = Runtime.getRuntime().exec(cmd);
            
            /* Lancement du thread de récupération de la sortie standard */
            new RecuperationSorties(p.getInputStream()).start();
            
            /* Lancement du thread de récupération de la sortie en erreur */
            new RecuperationSorties(p.getErrorStream()).start();

            
            //int exitValue = p.waitFor(); 
            LaunchOpenJMS.sleep(10000);
            p.getOutputStream().close();
            p.getInputStream().close();
            System.out.println("----End shutdown JMS \n");
            
        } catch (IOException | InterruptedException e) {
            
            System.out.println(" "+e.getMessage());
            
        }
    }
    
    
    public static void main(String arg[]){
        String[] cmd = {  "", "-c", "lsof -i:3035" };
        try {
            System.out.println("\n---- Start shutdown JMS");
            Process p = Runtime.getRuntime().exec("lsof -i:3035");
            
            System.out.println("---\n "+RecuperationSorties.nothingValue());
            /* Lancement du thread de récupération de la sortie standard */
            new RecuperationSorties(p.getInputStream()).start();
            
            /* Lancement du thread de récupération de la sortie en erreur */
            new RecuperationSorties(p.getErrorStream()).start();

            
            //int exitValue = p.waitFor(); 
            LaunchOpenJMS.sleep(10000);
            p.getOutputStream().close();
            p.getInputStream().close();
            System.out.println("\n---- End shutdown JMS \n");
            
        } catch (IOException | InterruptedException e) {
            
            System.out.println(" "+e.getMessage());
            
        }
    }
    
}
