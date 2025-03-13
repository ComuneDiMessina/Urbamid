package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParticellaGeomCompleteDTO implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;
	
	private Long id;
	private String codiceCom;
	private String foglio;
	private String mappale;
	private String allegato;
	private String sviluppo;
	private String geomText;
	private BigDecimal area;
	private String intersectGeomText;
	private BigDecimal intersectArea;
	
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
	public String getGeomText() {
		return geomText;
	}
	public void setGeomText(String geomText) {
		this.geomText = geomText;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public String getIntersectGeomText() {
		return intersectGeomText;
	}
	public void setIntersectGeomText(String intersectGeomText) {
		this.intersectGeomText = intersectGeomText;
	}
	public BigDecimal getIntersectArea() {
		return intersectArea;
	}
	public void setIntersectArea(BigDecimal intersectArea) {
		this.intersectArea = intersectArea;
	}

	
}
