package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParticellaCompleteGeom implements Serializable {

	private static final long serialVersionUID = 3613532055234295657L;
	
	private Long gid;
	private String codiceCom;
	private String foglio;
	private String mappale;
	private String allegato;
	private String sviluppo;
	private BigDecimal htxt;
	private BigDecimal rtxt;
	private BigDecimal xtxt;
	private BigDecimal ytxt;
	private String geomText;
	private BigDecimal area;
	private String intersectGeomText;
	private BigDecimal intersectArea;
	
	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
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

	public BigDecimal getHtxt() {
		return htxt;
	}

	public void setHtxt(BigDecimal htxt) {
		this.htxt = htxt;
	}

	public BigDecimal getRtxt() {
		return rtxt;
	}

	public void setRtxt(BigDecimal rtxt) {
		this.rtxt = rtxt;
	}

	public BigDecimal getXtxt() {
		return xtxt;
	}

	public void setXtxt(BigDecimal xtxt) {
		this.xtxt = xtxt;
	}

	public BigDecimal getYtxt() {
		return ytxt;
	}

	public void setYtxt(BigDecimal ytxt) {
		this.ytxt = ytxt;
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

	public BigDecimal getIntersectArea() {
		return intersectArea;
	}

	public void setIntersectArea(BigDecimal intersectArea) {
		this.intersectArea = intersectArea;
	}

	public String getIntersectGeomText() {
		return intersectGeomText;
	}

	public void setIntersectGeomText(String intersectGeomText) {
		this.intersectGeomText = intersectGeomText;
	}
}
