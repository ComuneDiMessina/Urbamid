package it.eng.tz.urbamid.prg.web.dto;

import java.io.Serializable;

public class CodiceZtoDTO implements Serializable {

	private static final long serialVersionUID = 4304465256897795290L;
	
	private String codice;
	private String descrizione;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
