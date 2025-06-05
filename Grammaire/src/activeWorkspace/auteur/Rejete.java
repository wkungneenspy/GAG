package activeWorkspace.auteur;

import classeUniverselle.Context;

public class Rejete extends Expression {
	Context ctx = null;

	public Rejete(Context ctx) {
		super();
		this.ctx = ctx;
	}

	public boolean interpret(Context ctx) {
		if(ctx.getInput().getNom().equals(this.ctx.getInput().getNom()))
			return true;
		return false;
	}
	
	public String portLocal() {
		return "undefined";
	}
}
