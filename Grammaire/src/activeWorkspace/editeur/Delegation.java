package activeWorkspace.editeur;

import generated.Doc;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import classeUniverselle.Context;
import classeUniverselle.ManipSocket;
import classeUniverselle.ManipXML;
import classeUniverselle.Message;

public class Delegation extends Expression{
	private Expression exp = null;
	
	public Delegation(Expression exp) {
		super();
		this.exp = exp;
	}


	public boolean interpret(Context ctx) {
		/**
		 * Faire un traitement specifique ici (dans les sorties et entrees du context)
		 */
		boolean result = false;
		System.out.println("Delegation distant "+ctx.getInput().getPortDistant());
		if(ctx.getInput().getPortDistant().equals(portLocal())){
			System.out.println("Dedans Delegation");
			ctx.setOutput(new Message(ctx.getInput().getData(),"Delegation",portLocal(),ctx.getInput().getPortLocal()));
			ctx.setInput(new Message(ctx.getInput().getData(),ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));
		
			
			result =  exp.interpret(ctx);
		
			
			ManipXML manipXML = new ManipXML();
			ManipSocket manipSocket = new ManipSocket();
			
			Doc doc = null;
			try {
				doc = manipXML.xmltoJava("/home/willyk/NetBeansProjects/trang/espaceEditeur/artefact.xml");
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//doc.setArtefact(manipXML.contruirMessage(ctx.getOutput()));
			try {
				manipXML.javatoXml(doc, "/home/willyk/NetBeansProjects/trang/espaceEditeur/artefact.xml",manipXML.contruirMessage(ctx.getOutput()));
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
	        System.out.println("okoloooooool");
	        manipSocket.clientSocket(ctx.getOutput(), "localhost", 10001);
		}else{
			result =  exp.interpret(ctx);
		}
		return result;
	}
	
	public String portLocal() {
		return "20002";
	}
}
