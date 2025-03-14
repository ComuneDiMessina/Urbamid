package it.eng.tz.urbamid.prg.web.dto;

import java.io.Serializable;

public class ParticellaCustomDTO implements Serializable {


	private static final long serialVersionUID = 5070379730543868083L;

	private Long gid;
	private String codice_com;
	private String  foglio;
	private String  mappale;
	private String  allegato;
	private String  sviluppo;
	private Double htxt;
	private Double rtxt;
	private Double xtxt;
	private Double ytxt;
	private String[] bbox;

	public ParticellaCustomDTO() {
	}
	
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public String getCodice_com() {
		return codice_com;
	}
	public void setCodice_com(String codice_com) {
		this.codice_com = codice_com;
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
	public Double getHtxt() {
		return htxt;
	}
	public void setHtxt(Double htxt) {
		this.htxt = htxt;
	}
	public Double getRtxt() {
		return rtxt;
	}
	public void setRtxt(Double rtxt) {
		this.rtxt = rtxt;
	}
	public Double getXtxt() {
		return xtxt;
	}
	public void setXtxt(Double xtxt) {
		this.xtxt = xtxt;
	}
	public Double getYtxt() {
		return ytxt;
	}
	public void setYtxt(Double ytxt) {
		this.ytxt = ytxt;
	}
	public String[] getBbox() {
		return bbox;
	}
	public void setBbox(String[] bbox) {
		this.bbox = bbox;
	}

}
