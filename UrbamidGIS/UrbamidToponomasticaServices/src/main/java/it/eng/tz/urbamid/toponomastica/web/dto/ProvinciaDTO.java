package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;
import java.util.Date;

public class ProvinciaDTO implements Serializable {

	private static final long serialVersionUID = -9180576605843533422L;

	private Long idProvincia;
	private String codiceIstat;
	private String sigla;
	private String denominazione;
	private Date vlInizio;
	private Date vlFine;
	private Date duCatAgg;
	private Long idRegione;
	
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
