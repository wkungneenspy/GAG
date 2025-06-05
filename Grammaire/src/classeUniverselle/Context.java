package classeUniverselle;

/**
 * Specifie les donnees deja traitee et ceux qui reste a traiter
 * @author kengne
 *
 */
public class Context {
	Message input = new Message();
	
	Message output = new Message(); // Contient les calculs courants et  a la fin le resultat
	
	public Context() {
		super();
	}
	
	public Context(Message input) {
		super();
		this.input = input;
	}

	public Context(Message input, Message output) {
		super();
		this.input = input;
		this.output = output;
	}


	public Message getInput() {
		return input;
	}

	public void setInput(Message input) {
		this.input = input;
	}

	public Message getOutput() {
		return output;
	}

	public void setOutput(Message output) {
		this.output = output;
	}

	
}
