package activeWorkspace.editeur;

import classeUniverselle.Context;
import classeUniverselle.Message;

public class Valide extends Expression{
	Context ctx = null;

	public Valide(Context ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public boolean interpret(Context ctx) {
		System.out.println("VALIDEEEEEEE ");
		//Preparer la reponse en modifiant eventuellement le Context
		//if(this.ctx.getInput().getNom().equals(ctx.getInput().getNom())){
			ctx.setOutput(new Message(ctx.getOutput().getData(),ctx.getOutput().getNom()+" Valide",ctx.getOutput().getPortLocal(),ctx.getOutput().getPortDistant()));
			ctx.setInput(new Message(ctx.getInput().getData(),ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));
			
			return true;
		//}
		//return false;
	}
	
	public String portLocal() {
		return "undefined";
	}
}
