package activeWorkspace.editeur;

import java.io.FileNotFoundException;
import java.io.IOException;

import generated.Doc;

import javax.xml.bind.JAXBException;

import classeUniverselle.Context;
import classeUniverselle.ManipSocket;
import classeUniverselle.ManipXML;
import classeUniverselle.Message;
import classeUniverselle.MultiThread;

public class Soumission extends Expression{
	private Expression exp = null;
	private Expression expDel = null;

	
	
	public Soumission() {
		super();
		
	}

	public Soumission(Expression exp) {
		super();
		this.exp = exp;
	}

	public Soumission(Expression exp, Expression expDel) {
		super();
		this.exp = exp;
		this.expDel = expDel;
	}

	public boolean interpret(Context ctx) {
		/**
		 * Faire un traitement specifique ici (dans les sorties et entrees du context)
		 */
		boolean result = false;
		ManipSocket manipSocket = new ManipSocket();
		Message message = new Message();
		
		if(ctx.getInput().getPortDistant().equals(portLocal())){
			try {
				message = manipSocket.serverSocket(Integer.parseInt("20001"));
				
				MultiThread second = new MultiThread(20002);
				MultiThread second2 = new MultiThread(20003);
				MultiThread second3 = new MultiThread(20004);
				MultiThread second4 = new MultiThread(20005);
				MultiThread second5 = new MultiThread(20006);
				MultiThread second6 = new MultiThread(20007);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Porrrrrt "+ portLocal());
			ctx.setOutput(new Message(message.getData(),"Accuse_Reception",portLocal(),message.getPortLocal()));
			ctx.setInput(new Message(message.getData(),message.getNom(),message.getPortLocal(),message.getPortDistant()));
		
			
			if(this.expDel != null){
	    		//Ici preparer le traitement de deux cas (Valide et Delegation)
				System.out.println("Soumission Delegation est definie");
				result =  exp.interpret(ctx) && expDel.interpret(ctx);
		    }else{
		    	//Ici c'est le cas Refuse
		    	System.out.println("----> "+exp);
		    	System.out.println(ctx.getOutput().getNom()+" Context "+ctx.getOutput().getData());
		    	result =  exp.interpret(ctx);
		    }
			
			
			ManipXML manipXML = new ManipXML();
			//ManipSocket manipSocket = new ManipSocket();
			
			Doc doc = new Doc();
			doc.setArtefact(manipXML.contruirMessage(ctx.getOutput()));
			try {
				manipXML.javatoXml(doc, "/home/willyk/NetBeansProjects/trang/espaceEditeur/artefact.xml");
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
	        
			System.out.println("Soumission >>> "+Integer.parseInt(ctx.getOutput().getPortDistant()));
	        manipSocket.clientSocket(ctx.getOutput(), "localhost", Integer.parseInt(ctx.getOutput().getPortDistant()));
		}else{
			if(this.expDel != null){
	    		//Ici preparer le traitement de deux cas (Valide et Delegation)
				System.out.println("Soumission Delegation est definie");
				result =  exp.interpret(ctx) && expDel.interpret(ctx);
		    }else{
		    	//Ici c'est le cas Refuse
		    	System.out.println("----> "+exp);
		    	System.out.println(ctx.getOutput().getNom()+" Context "+ctx.getOutput().getData());
		    	result =  exp.interpret(ctx);
		    }
		}
		return result;
	}
	
	public String portLocal() {
		return "20001";
	}
}
