package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSKeyword implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private String chiave;
	
	private String valore;
	
	public GSKeyword() {
	}

	public GSKeyword(String chiave, String valore) {
		super();
		this.chiave = chiave;
		this.valore = valore;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}