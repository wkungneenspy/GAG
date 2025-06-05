package activeWorkspace.editeur;

import classeUniverselle.Context;
import classeUniverselle.Message;

public class Refuse extends Expression{
	Context ctx = null;

	public Refuse(Context ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public boolean interpret(Context ctx) {
		//Preparer la reponse en modifiant eventuellement le Context
		if(this.ctx.getInput().getNom().equals(ctx.getInput().getNom())){
			ctx.setOutput(new Message("C'est Article ca !! Poor English","Refuse",ctx.getOutput().getPortLocal(),ctx.getOutput().getPortDistant()));
			ctx.setInput(new Message(ctx.getInput().getData(),ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));
			
			return true;
		}
		return false;
	}
	
	public String portLocal() {
		return "undefined";
	}
}
