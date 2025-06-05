package activeWorkspace.auteur;

import classeUniverselle.Context;

public abstract class Expression {
	
	public abstract boolean interpret(Context ctx);
	
	//permet d'obtenir des informations extentionnelles
	//Le portLocal est d'une information intentionnelle
	public abstract String portLocal();
}
