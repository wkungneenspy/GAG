package activeWorkspace.editeurAssocie;

import classeUniverselle.Context;

public class Oui extends Expression{
	Context ctx = null;

	public Oui(Context ctx) {
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
