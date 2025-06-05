package com.monitor.client.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;


import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

//import com.Monitor.businesscomponent.utils.IEJBReferencesBus;
//import com.Monitor.dao.entities.Equipement;
//import com.Monitor.dao.entities.Historique;
//import com.Monitor.dao.entities.Info_Equipement;
//import com.Monitor.dao.entities.Platform;
//import com.Monitor.dao.entities.ComposePlatform;
//import com.Monitor.service.services_Supervisor.Iservices_SupervisorWS;
//import com.exception.businesscomponent.Exception.AucunHistoriqueException;
//import com.exception.businesscomponent.Exception.AucunServerException;
//import com.exception.businesscomponent.Exception.AucunePlatformException;
//import com.exception.businesscomponent.Exception.CompteExistantException;
//import com.exception.businesscomponent.Exception.CompteInexistantException;
//import com.exception.businesscomponent.Exception.PlatformExistanteException;
//import com.exception.businesscomponent.Exception.PlatformInexistantException;
//import com.exception.businesscomponent.Exception.ServerExistantException;
//import com.monitor.client.application.beans.diagramme.Info;
//import com.monitor.client.application.beans.diagramme.Info_EquipementBean;
//import com.monitor.client.application.beans.equipement.EquipementBean;
//import com.monitor.client.application.beans.historique.HistoriqueBean;
//import com.monitor.client.application.beans.platform.PlatformBean;



@ManagedBean(name="bridge", eager=true)
@ApplicationScoped
public class Bridge implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
//	@EJB(beanName="services_Supervisor",mappedName="services_SupervisorWS",lookup="services_SupervisorWS")
//	private Iservices_SupervisorWS monitor;
//	
//	@EJB(beanName="EJBRefbus",mappedName="EJBReferencesBus",lookup="EJBReferencesBus")
//	private IEJBReferencesBus busRemote;
//	
//	public Boolean connectUser(String login, String password) throws CompteInexistantException {
//		return busRemote.getAuthentificationManagerBus().authentification(login, password);
//	}
//	
//	public Boolean definirSeuil(String login, int seuil) throws IndexOutOfBoundsException{
//		return busRemote.getAuthentificationManagerBus().definirSeuil(login, seuil);
//	}
//	
//	public Boolean definirSeuilmin(String login, int seuilmin) throws IndexOutOfBoundsException{
//		return busRemote.getAuthentificationManagerBus().definirSeuilmin(login, seuilmin);
//	}
// 
//	public Boolean definirSeuilsnmp(String login, int seuilsnmp) throws IndexOutOfBoundsException{
//		return busRemote.getAuthentificationManagerBus().definirSeuilsnmp(login, seuilsnmp);
//	}
//	
//	public Boolean definirSeuilsnmpmin(String login, int seuilsnmpmin) throws IndexOutOfBoundsException{
//		return busRemote.getAuthentificationManagerBus().definirSeuilsnmpmin(login, seuilsnmpmin);
//	}
//	
//	public int getSeuil(){
//		return busRemote.getAuthentificationManagerBus().getSeuil();
//	}
//	
//	public int getSeuilSNMPmin(){
//		return busRemote.getAuthentificationManagerBus().getSeuil();
//	}
//	
//	public int getSeuilSNMP(){
//		return busRemote.getAuthentificationManagerBus().getSeuil();
//	}
//	
//	public int getSeuilmin(){
//		return busRemote.getAuthentificationManagerBus().getSeuil();
//	}
//	
//	public List<Pourcentage> pourcentageUseBandwidth() throws Exception{
//		return busRemote.getAuthentificationManagerBus().pourcentageUseBandwidth();
//	}
//	
//	public List<PourcentageSNMP> pourcentageUseBandwidthSNMP() throws Exception{
//		return busRemote.getAuthentificationManagerBus().pourcentageUseBandwidthSNMP();
//	}
//	
//	public List<PourcentageOut> pourcentageUseBandwidthOut() throws Exception{
//		return busRemote.getAuthentificationManagerBus().pourcentageUseBandwidthOut();
//	}
//	
//	public List<PourcentageSNMPOut> pourcentageUseBandwidthSNMPOut() throws Exception{
//		return busRemote.getAuthentificationManagerBus().pourcentageUseBandwidthSNMPOut();
//	}
//	
//	public Boolean set_check_interval(String folder,int n) throws IOException{
//	    return busRemote.getAuthentificationManagerBus().set_check_intervall(folder,n);	
//	}
//	
//	public double savePourcentageBandwidth() throws Exception{
//	    return busRemote.getAuthentificationManagerBus().save_pourcentageUseBandwidth();	
//	}
//	
//	public double savePourcentageBandwidthSNMP() throws Exception{
//	    return busRemote.getAuthentificationManagerBus().save_pourcentageUseBandwidthSNMP();	
//	}
//	
//	
//	public double savePourcentageBandwidthOut() throws Exception{
//	    return busRemote.getAuthentificationManagerBus().save_pourcentageUseBandwidthOut();	
//	}
//	
//	public double savePourcentageBandwidthSNMPOut() throws Exception{
//	    return busRemote.getAuthentificationManagerBus().save_pourcentageUseBandwidthSNMPOut();	
//	}
//	
//	public int get_check_intervall(String folder) throws IOException{
//		return busRemote.getAuthentificationManagerBus().get_check_intervall(folder);
//	}
//	
//	public Boolean configure(String inputFile,String ip,String user, String password) {
//		return busRemote.getAuthentificationManagerBus().configure(inputFile, ip, user, password);
//	}
}
