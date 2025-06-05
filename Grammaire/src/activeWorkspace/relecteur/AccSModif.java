package activeWorkspace.relecteur;

import classeUniverselle.Context;

public class AccSModif extends Expression{
	private Expression exp =  null;
	
	public AccSModif(Expression exp) {
		super();
		this.exp = exp;
	}

	@Override
	public boolean interpret(Context ctx) {
		
		return exp.interpret(ctx);
	}

	
}
