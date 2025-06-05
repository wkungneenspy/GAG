package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import classeUniverselle.Message;


import server.Server;
/** Le processus client se connecte au site fourni dans la commande
 *   d'appel en premier argument et utilise le port distant 8080.
 */
public class ClientSocket {

   public static void main(String[] args) throws Exception {
        ClientSocket client =new ClientSocket();
        Message m = new Message("data1", "nom", "portLocal", "portDistant");
        client.clientSocket(m,"127.0.0.1",8080);
        
        Server s = new Server();
        s.serverSocket(8081);
   }
   
   public void clientSocket(Message message,String address,int port){
	   boolean bool = true;
	   while(bool){
			  try { 
			   Socket socket  = new Socket(address, port);
			   
		       ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		        
		        
		        outStream.writeObject(message);
			   
		       socket.close();
		       bool = false;
		       } catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					/*e.printStackTrace();
					System.out.println("Pas dispo");*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					/*e.printStackTrace();
					System.out.println("Pas dispooo");*/
				}
         }
   }
}