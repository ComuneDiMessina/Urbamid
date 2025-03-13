package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_province")
public class Province implements Serializable {

	private static final long serialVersionUID = -7387381819715896357L;

	@Id
	@Column(name = "id_provincia")
	private Long idProvincia;

	@Column(name = "codice_istat", length=3)
	private String codiceIstat;

	@Column(name = "sigla", length=2)
	private String sigla;

	@Column(name = "denominazione", length=50)
	private String denominazione;

	@Column(name = "vl_inizio")
	@Temporal(value = TemporalType.DATE)
	private Date vlInizio;

	@Column(name = "vl_fine")
	@Temporal(value = TemporalType.DATE)
	private Date vlFine;

	@Column(name = "du_cat_agg")
	@Temporal(value = TemporalType.DATE)
	private Date duCatAgg;

	@Column(name = "id_regione")
	private Long idRegione;

	public Province () { }

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getCodiceIstat() {
		return codiceIstat;
	}

	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getVlInizio() {
		return vlInizio;
	}

	public void setVlInizio(Date vlInizio) {
		this.vlInizio = vlInizio;
	}

	public Date getVlFine() {
		return vlFine;
	}

	public void setVlFine(Date vlFine) {
		this.vlFine = vlFine;
	}

	public Date getDuCatAgg() {
		return duCatAgg;
	}

	public void setDuCatAgg(Date duCatAgg) {
		this.duCatAgg = duCatAgg;
	}

	public Long getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}

}
