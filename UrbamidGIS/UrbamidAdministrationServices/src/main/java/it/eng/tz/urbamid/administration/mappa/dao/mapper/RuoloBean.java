package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.io.Serializable;

public class RuoloBean implements Serializable {
	
	private static final long serialVersionUID = -504207861512282087L;
	
	private Long id;
	private String codice;
	private String denominazione;
	private String descrizione;
	
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
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
