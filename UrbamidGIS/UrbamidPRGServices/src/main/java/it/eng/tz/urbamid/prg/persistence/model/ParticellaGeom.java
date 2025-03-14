package it.eng.tz.urbamid.prg.persistence.model;

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
@Table(name="u_cat_particelle")
//@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "gid")) })
public class ParticellaGeom implements Serializable {

	private static final long serialVersionUID = 3613532055234295657L;
	
	@Id 
	@SequenceGenerator(name="u_cat_particelle_gid_seq", sequenceName="u_cat_particelle_gid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_cat_particelle_gid_seq")
	@Column(name = "gid", unique = true, nullable = false, updatable=false)
	private Long gid;

	@Column(name = "codice_com", length=50)
	private String codiceCom;
	
	@Column(name = "foglio", length=5)
	private String foglio;
	
	@Column(name = "mappale", length=6)
	private String mappale;
	
	@Column(name = "allegato", length=5)
	private String allegato;
	
	@Column(name = "sviluppo", length=5)
	private String sviluppo;
	
	@Column(name = "htxt")
	private BigDecimal htxt;
	
	@Column(name = "rtxt")
	private BigDecimal rtxt;
	
	@Column(name = "xtxt")
	private BigDecimal xtxt;
	
	@Column(name = "ytxt")
	private BigDecimal ytxt;
	
	@Column(name = "geom")
	private String geom;
	
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

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}
	
	
	
}
