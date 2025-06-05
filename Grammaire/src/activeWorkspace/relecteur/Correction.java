package activeWorkspace.relecteur;

import classeUniverselle.Context;

public class Correction extends Expression{
	private Expression exp =  null;
	
	public Correction(Expression exp) {
		super();
		this.exp = exp;
	}

	@Override
	public boolean interpret(Context ctx) {
		
		return exp.interpret(ctx);
	}

	
}
