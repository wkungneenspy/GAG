package classeUniverselle;

import java.io.IOException;
import java.io.Serializable;

import activeWorkspace.editeur.Expression;

public class MultiThread extends Thread implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected int port;

    	/** D�marrage de cinq threads bas�s sur un m�me objet */
    	public MultiThread (int port) {
    		this.port = port;
    		this.start();       
  		/*C'est l� que s'�tablit le lien entre l'objet Runnable et le thread */ 
	
    	}
    	
		public int getPort() {
			return port;
		}
		
		public void setPort(int port) {
			this.port = port;
		}

		public void run() {
			try {
				
				ManipSocket manipSocket = new ManipSocket();
				System.out.println("Ecoute sur "+port);
				Message msg = manipSocket.serverSocket(this.port);
				System.out.println(msg.getNom());
		        System.out.println(msg.getPortDistant());
		        System.out.println(msg.getPortLocal());
		        System.out.println(msg.getData());
		        
		        
		        Expression tree = null;
				
				//if(message.getNom().equalsIgnoreCase("Soumission")){
					tree = tree.initEspaceEditeur(); //Initialiser l'espace de l'editeur
				//}	
					
				Context ctx = new Context(new Message(msg.getData(),msg.getNom(),msg.getPortLocal(),msg.getPortDistant()));
				tree.interpret(ctx);
				
				
		        /*msg.setData("TOTO");
		        manipSocket.clientSocket(msg, "localhost", 10001);*/
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}
	    }    

}
