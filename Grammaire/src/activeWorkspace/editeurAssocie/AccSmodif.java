package activeWorkspace.editeurAssocie;

import classeUniverselle.*;

public class AccSmodif extends Expression{
	private Expression exp = null;
	private Expression expCorrect = null;


	public AccSmodif(Expression exp, Expression expCorrect) {
		super();
		this.exp = exp;
		this.expCorrect = expCorrect;
	}

	public boolean interpret(Context ctx) {
		/**
		 * Faire un traitement specifique ici (dans les sorties et entrees du context)
		 */
		ctx.setInput(new Message(ctx.getInput().getData()+" Sou ",ctx.getInput().getData(),ctx.getInput().getPortLocal(),ctx.getInput().getPortDistant()));//traitons le noeud
	    if(this.expCorrect != null){
	    	//Ici preparer le traitement de deux cas (Valide et Delegation)
	    	return exp.interpret(ctx) && expCorrect.interpret(ctx);
	    }else{
	    	//Ici c'est le cas Refuse
	    	return exp.interpret(ctx);
	    }
	}
}
