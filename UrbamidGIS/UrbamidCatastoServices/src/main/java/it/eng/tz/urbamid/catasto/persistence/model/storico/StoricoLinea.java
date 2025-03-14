package it.eng.tz.urbamid.catasto.persistence.model.storico;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_linee_storico", schema="storico")
public class StoricoLinea implements Serializable {
	
	private static final long serialVersionUID = -8121746587071940865L;

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

	@Column(name = "cod_linea", length=5)
	private String codLinea;

	@Column(name = "geom")
	private String geometry;

	public StoricoLinea() {
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

	public String getCodLinea() {
		return codLinea;
	}

	public void setCodLinea(String codLinea) {
		this.codLinea = codLinea;
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
		if (!(obj instanceof StoricoLinea)) {
			return false;
		}
		StoricoLinea other = (StoricoLinea) obj;
		return Objects.equals(gid, other.gid);
	}
	
}
