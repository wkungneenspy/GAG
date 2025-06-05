package activeWorkspace.auteur;

import classeUniverselle.Context;

public class Oui extends Expression {
	Context ctx = null;

	public Oui(Context ctx) {
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
