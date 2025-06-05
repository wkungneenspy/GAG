package activeWorkspace.editeur;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import generated.Doc;
import classeUniverselle.Context;
import classeUniverselle.ManipSocket;
import classeUniverselle.ManipXML;
import classeUniverselle.Message;
import classeUniverselle.MultiThread;

/**
 * 
 * @author kengne
 *
 */
public class MainEditeur extends Expression{
	
	
	/**
	 * main method - build the interpreter
	 *  and then interpret a specific sequence 
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		//ICI C'est le site d'un Editeur, les messages qui arrivent ici sont de ce genre :
		
		//article = {date::Date,title::String,correspondingAuthor::author,coAuthor::author,abstract::String,text::PDF}
		//C'est a dire on souscrit pour le noeud SOUMISSION venant de l'Auteur
		
		//article = {date::Date,title::String,correspondingAuthor::author,coAuthor::author,abstract::String,text::PDF}
		//C'est a dire on souscrit pour le noeud SOUMISSION
		//ManipSocket manipSocket = new ManipSocket();
		
		
		System.out.println("J'ecoute sur le port : "+20001);
		/*Message message;
		
		message = manipSocket.serverSocket(Integer.parseInt("20001"));*/
		/*SecondThread second = new SecondThread(20002);
		SecondThread second2 = new SecondThread(20003);
		SecondThread second3 = new SecondThread(20004);
		SecondThread second4 = new SecondThread(20005);
		SecondThread second5 = new SecondThread(20006);
		SecondThread second6 = new SecondThread(20007);*/
		
		
		Expression tree = null;
				
		//if(message.getNom().equalsIgnoreCase("Soumission")){
			tree = initEspaceEditeur(); //Initialiser l'espace de l'editeur
		//}	
		
		//Juste pour initialiser, je vais entreprendre une reflexion pour changer cette facon.
		Context ctx = new Context(new Message("Data_Init","Nom_Init","Init_LocalPort","20001"));
		tree.interpret(ctx);
		
		
		//System.out.println("RECU >>>> "+message.getNom());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        System.out.println(ctx.getOutput().getNom());
        System.out.println(ctx.getOutput().getPortDistant());
        System.out.println(ctx.getOutput().getPortLocal());
        System.out.println(ctx.getOutput().getData());
        

	}

	@Override
	public boolean interpret(Context ctx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String portLocal() {
		// TODO Auto-generated method stub
		return null;
	}
}
