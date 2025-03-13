package it.eng.tz.urbamid.profilemanager.web.dto;

import java.io.Serializable;
import java.util.List;

public class RuoloUtenteDto implements Serializable{

	private static final long serialVersionUID = 3809196557788417619L;
	protected Long id;
	protected String codice;
	protected String denominazione;
	protected String descrizione;
	protected boolean ruoloDefault;
	private List<PermessoUtenteDto> listaPermessi;
	
	
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
	public List<PermessoUtenteDto> getListaPermessi() {
		return listaPermessi;
	}
	public void setListaPermessi(List<PermessoUtenteDto> listaPermessi) {
		this.listaPermessi = listaPermessi;
	}
	public boolean isRuoloDefault() {
		return ruoloDefault;
	}
	public void setRuoloDefault(boolean ruoloDefault) {
		this.ruoloDefault = ruoloDefault;
	}
	
	
}
