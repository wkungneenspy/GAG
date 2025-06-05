package activeWorkspace.editeur;

import classeUniverselle.Context;
import classeUniverselle.Message;

public class Etat extends Expression {
	private Expression exp = null;
	
	
	public Etat(Expression exp) {
		super();
		this.exp = exp;
	}


	@Override
	public boolean interpret(Context ctx) {
		ctx.setInput(new Message(ctx.getInput().getData()+"Etat ",ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));
		return exp.interpret(ctx);
	}
	
	public String portLocal() {
		return "20005";
	}
}
