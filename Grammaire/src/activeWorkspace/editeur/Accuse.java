package activeWorkspace.editeur;

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
		/**
		 * Faire un traitement specifique ici (dans les sorties et entrees du context)
		 */
		if(ctx.getInput().getPortDistant().equals(portLocal())){
			ctx.setOutput(new Message(ctx.getInput().getData(),"Accuse_Reception",portLocal(),ctx.getInput().getPortLocal()));
			ctx.setInput(new Message(ctx.getInput().getData(),ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));
		}
		//ctx.setInput(new Message(ctx.getInput().getData()+" Sou ",ctx.getInput().getNom(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));//traitons le noeud
	    if(this.expEtat != null){
	    	//Ici preparer le traitement de deux cas (Valide et Delegation)
	    	return exp.interpret(ctx) && expEtat.interpret(ctx);
	    }else{
	    	//Ici c'est le cas Refuse
	    	return exp.interpret(ctx);
	    }
	}
	
	public String portLocal() {
		return "20003";
	}
}
