package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_testi")
public class TestiGeom implements Serializable {

	private static final long serialVersionUID = 7968521045547858907L;

	@Id 
	@SequenceGenerator(name="u_cat_testi_gid_seq", sequenceName="u_cat_testi_gid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_cat_testi_gid_seq")
	@Column(name = "gid", unique = true, nullable = false, updatable=false)
	private Long gid;

	@Column(name = "codice_com", length=50)
	private String codiceCom;

	@Column(name = "fg", length=5)
	private String fg;

	@Column(name = "mappale", length=6)
	private String mappale;

	@Column(name = "all", length=5)
	private String all;

	@Column(name = "sez", length=5)
	private String sez;

	@Column(name = "testo", length=50)
	private String testo;

	@Column(name = "htxt")
	private BigDecimal htxt;
	
	@Column(name = "rtxt")
	private BigDecimal rtxt;
	
	@Column(name = "xtxt")
	private BigDecimal xtxt;
	
	@Column(name = "ytxt")
	private BigDecimal ytxt;
	
	@Column(name = "geom")
	private String geometry;

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

	public String getFg() {
		return fg;
	}

	public void setFg(String fg) {
		this.fg = fg;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
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

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

}
