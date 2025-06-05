package classeUniverselle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ManipSocket {

	   @SuppressWarnings("resource")
	   public Message serverSocket(int port) throws IOException, ClassNotFoundException{
		   ServerSocket serverSocket = new ServerSocket(port);
	       //System.out.println("Je suis le server j'ecoute sur "+port);
	       
	       Socket soc = null;
	       
	       soc = serverSocket.accept();
	       ObjectInputStream inStream = new ObjectInputStream(soc.getInputStream()); 
	       Message obj = (Message) inStream.readObject();
	       
	       
	       //System.out.println("Voci ce que j'ai recu \n"+m.getData());
	       soc.close();
	       
	       return obj;
	   }
	   
	   public void clientSocket(Message msg,String address,int port){
		   boolean bool = true;
		   while(bool){
			  try { 
			   Socket socket  = new Socket(address, port);
			   
		       ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		        
		        outStream.writeObject(msg);
			   
		       socket.close();
		       bool = false;
		       } catch (UnknownHostException e) {

				} catch (IOException e) {
					
				}
	         }
	   }
}
