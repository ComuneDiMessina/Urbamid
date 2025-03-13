package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;


public class FunzionalitaDto implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Long id;
	private String permesso;
	private String codice;
	private String denominazione;
	private String linkUrl;
	private String imgUrl;

	public FunzionalitaDto() {
	}

	public FunzionalitaDto(Long id, String permesso, String codice) {
		super();
		this.id = id;
		this.permesso = permesso;
		this.codice = codice;
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

	
}