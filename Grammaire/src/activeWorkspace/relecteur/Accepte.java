package activeWorkspace.relecteur;

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
		if(this.ctx.getInput().getData().equals(ctx.getInput().getData()))
			return true;
		return false;
	}
	
	
}
