package activeWorkspace.auteur;

import classeUniverselle.Context;
import classeUniverselle.Message;

public class Accuse extends Expression{
	private Expression exp = null;
	private Expression expEtat = null;
	
	
	public Accuse(Expression exp) {
		super();
		this.exp = exp;
	}

	public Accuse(Expression exp, Expression expEtat) {
		super();
		this.exp = exp;
		this.expEtat = expEtat;
	}

	public boolean interpret(Context ctx) {
		System.out.println("Etattooo "+this.exp == null);
		System.out.println("Etattooo "+this.expEtat == null);
		ctx.setInput(new Message(ctx.getInput().getData()+"Accu ",ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));
		if(this.expEtat != null){
			System.out.println(">>> ");
			return exp.interpret(ctx) || expEtat.interpret(ctx);
		}else{
			System.out.println("lool");
			return exp.interpret(ctx);
		}
	}
	
	public String portLocal() {
		return "102";
	}
}
