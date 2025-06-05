package server;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;

import classeUniverselle.Message;
import client.ClientSocket;

public class Server{

   public static void main(String[] args) throws Exception {
        Server server =new Server();
        server.serverSocket(8080);
        
        ClientSocket client = new ClientSocket();
        Message m = new Message("dataServer", "nomServer", "portLocalServer", "portDistantServer");
        client.clientSocket(m,"127.0.0.1",8081);
   }
   
   @SuppressWarnings("resource")
   public Message serverSocket(int port) throws IOException, ClassNotFoundException{
	   ServerSocket serverSocket = new ServerSocket(port);
       System.out.println("Je suis le server j'ecoute sur "+port);
       
       Socket soc = null;
       
       //while (true) { 
        soc = serverSocket.accept();

        ObjectInputStream inStream = new ObjectInputStream(soc.getInputStream());
        
        
        Message m = (Message) inStream.readObject();
        System.out.println("Voci ce que j'ai recu \n"+m.getData());
       soc.close();
       return m;
   }
}