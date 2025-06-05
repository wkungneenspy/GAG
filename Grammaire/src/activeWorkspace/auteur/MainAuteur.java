package activeWorkspace.auteur;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import generated.*;
import classeUniverselle.*;

public class MainAuteur {
	/**
	 * Une instance de la gestion
	 * @return
	 */
    
	//ICI C'est le site d'un Auteur, les messages qui arrivent ici sont de ce genre :
		//accuse_Reception = {date::Date,decision::Boolean,editeur::Editeur,text::String}
		//C'est a dire on souscrit pour le noeud ACCUSE
	/**
	 * main method - build the interpreter
	 *  and then interpret a specific sequence 
	 * @param args
	 */
	public static void main(String[] args) {
		ManipSocket manipSocket = new ManipSocket();
		ManipXML manipXML = new ManipXML();
		
		//1. L'Auteur envoie l'artefact Auteur.
		//1.1 Il parcourt son espace pour recuperer un artefact.
		Doc doc;
		try {
			doc = manipXML.xmltoJava("/home/willyk/NetBeansProjects/trang/espaceAuteur/artefact.xml");
			Artefact aaaa = manipXML.recupererUnArtifactComplet(doc.getArtefact());
			manipXML.parcoursDunArtefact(aaaa);
			
			Artefact art = manipXML.getArtefactComplet();//recupererUnArtifactComplet(doc.getArtefact());
			System.out.println("Artefact "+art.getAddress().getNom());
			
			Message msg = manipXML.construirArtifact(art);// Ceci c'est pour avoir les parametres comme les addresses (une reflexion doit etre mener pour changer cela)
			
			System.out.println("Adresse "+Integer.parseInt(msg.getPortDistant()));
                        manipSocket.clientSocket(manipXML.construirArtifact(art), "localhost", Integer.parseInt(msg.getPortDistant()));
		
		    
                        System.out.println("Le client attend "+10001);
			Message msgohh = manipSocket.serverSocket(10001);
			manipXML.javatoXml(doc, "/home/willyk/NetBeansProjects/trang/espaceAuteur/artefact.xml",manipXML.contruirMessage(msgohh));
                        
                        System.out.println(msgohh.getNom());
                        System.out.println(msgohh.getPortDistant());
                        System.out.println(msgohh.getPortLocal());
                        System.out.println(msgohh.getData()); 
		    
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	/**
	 * Ce service initialise l'espace de travail du peer
	 * @param m est l'artefact qui permet d'initialiser ce noeud
	 * Pour ce noeud, m est le message de soumission de l'article.
	 * @return
	 */
	public boolean startSite(Message m){
		//Appel du service qui initialise l'espace de travail d'un editeur.
		
		//Attendre l'accuse de reception.
		return false;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////******************************//////////////////////////////////
	//////////////                  SERVICE OFFERT PAR LE SITE DE L'AUTEUR                  /////////////
	public void Soumission(Message artefact){
		
	}
	
	public void Accuse(Message artefact){
		
	}
	
	public void Etat(Message artefact){
		
	}
	
	public void AccSModif(Message artefact){
		
	}
	
	public void correction(Message artefact){
		
	}
}
