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
@Table(name="u_cat_fabbricati_storico", schema="storico")
public class StoricoFabbricato implements Serializable {

	private static final long serialVersionUID = -7377605234318737545L;

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
	
	@Column(name = "foglio", length=5)
	private String foglio;
	
	@Column(name = "mappale", length=7)
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
	private String geometry;

	public StoricoFabbricato() {
		super();
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public Integer getVersione() {
		return versione;
	}

	public void setVersione(Integer versione) {
		this.versione = versione;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
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

	public String getGeometry() {
		return geometry;
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
		if (!(obj instanceof StoricoFabbricato)) {
			return false;
		}
		StoricoFabbricato other = (StoricoFabbricato) obj;
		return Objects.equals(gid, other.gid);
	}
	
}
