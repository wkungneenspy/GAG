/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <b>
 * RecuperationSorties est la classe qui permet de récupérer   
 * les sorties d'une commande lancée avec Runtime et de les   
 * rediriger vers la console  
 * </b>  
 * <p>  
 * Cette classe est caractérisée par les informations suivantes :  
 * <ul>  
 * <li>Le flux à rediriger</li>  
 * </ul>  
 * </p>  
 * <p>  
 * Cette classe définit un thread, on peut lancer la récupération  
 * avec la méthode start().  
 * </p>  
 *   
 * @author Julien  
 * @version 1.0   
 */
public class RecuperationSorties extends Thread {  

	/**  Le flux à rediriger  */
	private InputStream flux;

	/**
	 * <b>Constructeur de RecuperationSorties</b>
	 * @param flux
	 *  Le flux à rediriger
	 */
	public RecuperationSorties(InputStream flux){
		this.flux = flux;
	}
	
	@Override
	public void run(){
		try {    
			InputStreamReader reader = new InputStreamReader(flux);
			BufferedReader br = new BufferedReader(reader);
			String ligne=null;
			while ( (ligne = br.readLine()) != null){
				System.out.println(ligne);
			}
		}
		catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
        
         public static boolean nothingValue(){
            
            boolean result = false;
            
            try {  
                Process p = Runtime.getRuntime().exec("netstat -ano | findstr 3035"/*"lsof -i:3035"*/);
                
                InputStreamReader reader = new InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(reader);
                String ligne=null;
                while ( (ligne = br.readLine()) != null){
                        result = true;
                }
            }
            catch (IOException ioe){
                    ioe.printStackTrace();
            }

            return result;
        }
} 
