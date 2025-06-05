package activeWorkspace.editeur;

import classeUniverselle.Context;
import classeUniverselle.Message;

public abstract class Expression {

	public abstract boolean interpret(Context ctx);
	
	public abstract String portLocal();
	
	/**
	 * Initialisation d'un Espace de l'editeur
	 * @return
	 */
   public static Expression initEspaceEditeur() 
    {
        Expression oui = new Oui(new Context(new Message("Data_Oui","Decision_Oui","Port1_Oui","Port2_Oui")));
        
        Expression accepte = new Accepte(new Context(new Message("Data_Accepte","Decision_Accepte","Port1_Accepte","Port2_Accepte")));
        
        Expression etat = new Accuse(accepte);
        
        Expression accuse = new Accuse(oui,etat);
        
        Expression delegation = new Delegation(accuse);
        
        Expression valide = new Valide(new Context(new Message("Data_Oui","Decision_Oui","Port1_Oui","Port2_Oui")));
        
        Expression soumission = new Soumission(valide,delegation);
        
        /*Expression non = new Non(new Context(new Message("Data_Non","Decision_Non","Port1_Non","Port2_Non")));
        
        Expression accuse1 = new Accuse(non);
	
        Expression soumission1 = new Soumission(accuse1);*/
        
        return soumission;
    }

}
