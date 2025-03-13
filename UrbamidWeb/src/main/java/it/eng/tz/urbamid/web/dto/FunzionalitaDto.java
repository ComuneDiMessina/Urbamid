package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;


public class FunzionalitaDto implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Long id;
	private String permesso;
	private String codice;
	private String denominazione;
	private String linkUrl;
	private String imgUrl;
	private String mappa;
	private String ordine;
	private Boolean locked;

	public FunzionalitaDto() {
	}

	public FunzionalitaDto(Long id, String permesso, String codice, String mappa) {
		super();
		this.id = id;
		this.permesso = permesso;
		this.codice = codice;
		this.mappa = mappa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermesso() {
		return permesso;
	}

	public void setPermesso(String permesso) {
		this.permesso = permesso;
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMappa() {
		return mappa;
	}

	public void setMappa(String mappa) {
		this.mappa = mappa;
	}

	public String getOrdine() {
		return ordine;
	}

	public void setOrdine(String ordine) {
		this.ordine = ordine;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	
}