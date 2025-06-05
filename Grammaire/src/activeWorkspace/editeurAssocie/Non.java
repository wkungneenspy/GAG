package activeWorkspace.editeurAssocie;

import classeUniverselle.Context;

public class Non extends Expression{
	private Expression exp = null;
	
	public Non(Expression exp) {
		super();
		this.exp = exp;
	}


	public boolean interpret(Context ctx) {
		/**
		 * Faire un traitement specifique ici (dans les sorties et entrees du context)
		 */
		//ctx.setInput(new Message(ctx.getInput().getData()+" Sou ",ctx.getInput().getDecision(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));//traitons le noeud
	    
	    return exp.interpret(ctx);
	    
	}
}
