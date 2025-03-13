package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class ElencoNominativoProprietaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String foglio;
	private String numero;
	
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

}
