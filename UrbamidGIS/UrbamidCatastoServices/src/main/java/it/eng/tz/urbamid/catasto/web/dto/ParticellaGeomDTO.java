package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class ParticellaGeomDTO implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;
	
//	@JsonIgnore
	private Long id;
//	@JsonIgnore
	private String codiceCom;
	private String foglio;
	private String mappale;
	private String allegato;
	private String sviluppo;
	private String geometry;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodiceCom() {
		return codiceCom;
	}
	public void setCodiceCom(String codiceCom) {
		this.codiceCom = codiceCom;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getMappale() {
		return mappale;
	}
	public void setMappale(String mappale) {
		this.mappale = mappale;
	}
	public String getAllegato() {
		return allegato;
	}
	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}
	public String getSviluppo() {
		return sviluppo;
	}
	public void setSviluppo(String sviluppo) {
		this.sviluppo = sviluppo;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	
}
