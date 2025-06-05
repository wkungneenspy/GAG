package activeWorkspace.auteur;

import classeUniverselle.Context;
import classeUniverselle.Message;

public class Soumission extends Expression{
	private Expression exp = null;
	
	public Soumission(Expression exp) {
		super();
		this.exp = exp;
	}

	public boolean interpret(Context ctx) {
		//ctx.setOutput(this.exp); 
		
		ctx.setInput(new Message(ctx.getInput().getData()+" Sou ",ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));//traitons le noeud
	    
		return exp.interpret(ctx);
	}
	
	public String portLocal() {
		return "101";
	}
}