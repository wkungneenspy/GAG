package activeWorkspace.editeurAssocie;

import classeUniverselle.Context;

public class Delegation extends Expression{
    private Expression exp = null;
    private Expression expReview = null;
    
	public Delegation(Expression exp) {
		super();
		this.exp = exp;
	}
	public Delegation(Expression exp, Expression expReview) {
		super();
		this.exp = exp;
		this.expReview = expReview;
	}

	@Override
	public boolean interpret(Context ctx) {
		if(expReview != null)
			return exp.interpret(ctx) && expReview.interpret(ctx);
		else
			return exp.interpret(ctx);
	}

}
