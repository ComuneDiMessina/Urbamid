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
@Table(name="u_cat_fiduciali")
public class FiducialiGeom implements Serializable {

	private static final long serialVersionUID = -5040870838140810212L;

	@Id 
	@SequenceGenerator(name="u_cat_fiduciali_gid_seq", sequenceName="u_cat_fiduciali_gid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_cat_fiduciali_gid_seq")
	@Column(name = "gid", unique = true, nullable = false, updatable=false)
	private Long gid;

	@Column(name = "codice_com", length=50)
	private String codiceCom;

	@Column(name = "fg", length=5)
	private String fg;

	@Column(name = "mappale", length=10)
	private String mappale;

	@Column(name = "all", length=5)
	private String all;

	@Column(name = "sez", length=5)
	private String sez;

	@Column(name = "codice", length=50)
	private String codice;

	@Column(name = "simbolo", length=5)
	private String simbolo;

	@Column(name = "posx")
	private BigDecimal posx;
	
	@Column(name = "posy")
	private BigDecimal posy;
	
	@Column(name = "relposx")
	private BigDecimal relposx;
	
	@Column(name = "relposy")
	private BigDecimal relposy;

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

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getPosx() {
		return posx;
	}

	public void setPosx(BigDecimal posx) {
		this.posx = posx;
	}

	public BigDecimal getPosy() {
		return posy;
	}

	public void setPosy(BigDecimal posy) {
		this.posy = posy;
	}

	public BigDecimal getRelposx() {
		return relposx;
	}

	public void setRelposx(BigDecimal relposx) {
		this.relposx = relposx;
	}

	public BigDecimal getRelposy() {
		return relposy;
	}

	public void setRelposy(BigDecimal relposy) {
		this.relposy = relposy;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

}
