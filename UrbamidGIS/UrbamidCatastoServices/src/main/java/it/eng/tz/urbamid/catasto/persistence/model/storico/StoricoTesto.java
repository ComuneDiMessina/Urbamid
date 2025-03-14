package it.eng.tz.urbamid.catasto.persistence.model.storico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_testi_storico", schema="storico")
public class StoricoTesto implements Serializable {

	private static final long serialVersionUID = 7968521045547858907L;

	@Id 
	@Column(name = "gid", unique = true, nullable = false, updatable=false)
	private Long gid;
	
	@Column(name = "versione")
	private Integer versione;
	
	@Column(name = "data_inizio_validita")
	@Temporal(TemporalType.DATE)
	private Date dataInizioValidita;
	
	@Column(name = "data_fine_validita")
	@Temporal(TemporalType.DATE)
	private Date dataFineValidita;

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

	public StoricoTesto() {
		super();
	}

	public Long getGid() {
		return gid;
	}

	public Integer getVersione() {
		return versione;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public String getCodiceCom() {
		return codiceCom;
	}

	public String getFg() {
		return fg;
	}

	public String getMappale() {
		return mappale;
	}

	public String getAll() {
		return all;
	}

	public String getSez() {
		return sez;
	}

	public String getTesto() {
		return testo;
	}

	public BigDecimal getHtxt() {
		return htxt;
	}

	public BigDecimal getRtxt() {
		return rtxt;
	}

	public BigDecimal getXtxt() {
		return xtxt;
	}

	public BigDecimal getYtxt() {
		return ytxt;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public void setVersione(Integer versione) {
		this.versione = versione;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public void setCodiceCom(String codiceCom) {
		this.codiceCom = codiceCom;
	}

	public void setFg(String fg) {
		this.fg = fg;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public void setHtxt(BigDecimal htxt) {
		this.htxt = htxt;
	}

	public void setRtxt(BigDecimal rtxt) {
		this.rtxt = rtxt;
	}

	public void setXtxt(BigDecimal xtxt) {
		this.xtxt = xtxt;
	}

	public void setYtxt(BigDecimal ytxt) {
		this.ytxt = ytxt;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StoricoTesto)) {
			return false;
		}
		StoricoTesto other = (StoricoTesto) obj;
		return Objects.equals(gid, other.gid);
	}
	
}
