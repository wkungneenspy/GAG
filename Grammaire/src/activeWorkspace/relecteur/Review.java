package activeWorkspace.relecteur;

import classeUniverselle.Context;

public class Review extends Expression{
	private Expression exp = null;
	private Expression expEtat = null;
	
	public Review(Expression exp) {
		super();
		this.exp = exp;
	}

	public Review(Expression exp, Expression expEtat) {
		super();
		this.exp = exp;
		this.expEtat = expEtat;
	}
	
	@Override
	public boolean interpret(Context ctx) {
		if(expEtat != null)
			return exp.interpret(ctx) && expEtat.interpret(ctx);
		else
			return exp.interpret(ctx);
	}

}
