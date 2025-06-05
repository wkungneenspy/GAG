package activeWorkspace.editeurAssocie;

import classeUniverselle.Context;

public class Accepte extends Expression{
	Context ctx = null;

	public Accepte(Context ctx) {
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
	
	
}
