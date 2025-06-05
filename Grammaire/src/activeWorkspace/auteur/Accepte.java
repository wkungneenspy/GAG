package activeWorkspace.auteur;

import classeUniverselle.*;

public class Accepte extends Expression {
	Context ctx = null;

	public Accepte(Context ctx) {
		super();
		this.ctx = ctx;
	}
	
	public boolean interpret(Context ctx) {
		System.out.println(this.ctx.getInput().getNom()+" >>> "+ctx.getInput().getNom());
		System.out.println(this.ctx.getInput().getData()+" >>> "+ctx.getInput().getData());/*
		System.out.println(this.ctx.getInput().getNom()+" >>> "+ctx.getInput().getNom());
		System.out.println(this.ctx.getInput().getNom()+" >>> "+ctx.getInput().getNom());*/
		if(ctx.getInput().getNom().equals(this.ctx.getInput().getNom()))
			return true;
		return false;
	}
	
	public String portLocal() {
		return "undefined";
	}
}
