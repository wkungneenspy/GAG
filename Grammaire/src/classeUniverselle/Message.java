package classeUniverselle;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String data;
	
	String nom;
	
	String portLocal;
	
	String portDistant;
	
	public Message() {
		super();
	}

	public Message(String data, String nom, String portLocal,
			String portDistant) {
		this.data = data;
		this.nom = nom;
		this.portLocal = portLocal;
		this.portDistant = portDistant;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPortLocal() {
		return portLocal;
	}

	public void setPortLocal(String portLocal) {
		this.portLocal = portLocal;
	}

	public String getPortDistant() {
		return portDistant;
	}

	public void setPortDistant(String portDistant) {
		this.portDistant = portDistant;
	}
}
