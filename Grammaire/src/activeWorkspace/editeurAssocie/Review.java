package activeWorkspace.editeurAssocie;

import classeUniverselle.Context;

public class Review extends Expression {
    private Expression exp = null;
     
	public Review(Expression exp) {
		super();
		this.exp = exp;
	}

	@Override
	public boolean interpret(Context ctx) {
		// TODO Auto-generated method stub
		return exp.interpret(ctx);
	}

}
