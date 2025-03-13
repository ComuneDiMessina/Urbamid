package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class TipologicaDTO implements Serializable {

	private static final long serialVersionUID = 7452377076755748018L;
	private Long id;
	private String codice;
	private String descrizione;
	private String informazione;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getInformazione() {
		return informazione;
	}
	public void setInformazione(String informazione) {
		this.informazione = informazione;
	}

	

}
