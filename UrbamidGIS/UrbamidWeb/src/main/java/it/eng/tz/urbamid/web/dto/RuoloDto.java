package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class RuoloDto implements Serializable {

	private static final long serialVersionUID = 4246478149341743801L;
	
	private Long id;
	
	private String codice;
	
	private String denominazione;
	
	private String descrizione;
	
	private boolean ruoloDefault;
		
	public RuoloDto() {				
	}

	public RuoloDto(String codice, String denominazione, String descrizione) {
		super();
		this.codice = codice;
		this.denominazione = denominazione;
		this.descrizione = descrizione;
	}

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

	public boolean isRuoloDefault() {
		return ruoloDefault;
	}

	public void setRuoloDefault(boolean ruoloDefault) {
		this.ruoloDefault = ruoloDefault;
	}
}
