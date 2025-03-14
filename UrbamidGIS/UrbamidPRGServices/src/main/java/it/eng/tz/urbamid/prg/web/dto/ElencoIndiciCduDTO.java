package it.eng.tz.urbamid.prg.web.dto;

import java.io.Serializable;

public class ElencoIndiciCduDTO implements Serializable {

	private String codice;
	private String articoloElenco;

	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getArticoloElenco() {
		return articoloElenco;
	}
	public void setArticoloElenco(String articoloElenco) {
		this.articoloElenco = articoloElenco;
	}

}
