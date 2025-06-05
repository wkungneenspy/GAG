package activeWorkspace.editeur;

import classeUniverselle.Context;

public class Modification extends Expression{
	Context ctx = null;

	public Modification(Context ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public boolean interpret(Context ctx) {
		//Preparer la reponse en modifiant eventuellement le Context
		if(this.ctx.getInput().getNom().equals(ctx.getInput().getNom()))
			return true;
		return false;
	}
	
	public String portLocal() {
		return "undefined";
	}
}
