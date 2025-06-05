package com.monitor.client.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class Repertoire {
	
	private static Logger log;
	private static String logPrefix="MIS-LOG ";
	
	public static void addMessageerreur(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
	
	public static void addMessagefatal(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
	
	public static void addMessageinfo(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
	
	
	/**
	 * Convert a string to an integer
	 * 
	 * @param valeur the string value to convert
	 * @return the int value of valeur if possible, 0 otherwise
	 */
	public static int parseStringToInteger(String valeur){
		try{
			return Integer.parseInt(valeur);
		}
		catch(Exception e){
			return 0;
		}
	}
	
	public static Long currentrythme;
	public static Long currentmusic;
	
	public static String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * Log un message de niveau DEBUG
	 * @param message message a logger
	 * @param classe classe ayant �mis le log
	 */
	public static void logDebug(String message, Class<?> classe){
		log=Logger.getLogger(classe);
		log.debug(logPrefix+message);	
	}
}
