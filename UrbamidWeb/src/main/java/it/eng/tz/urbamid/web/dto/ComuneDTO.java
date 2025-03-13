package it.eng.tz.urbamid.web.dto;

import java.util.Date;
import java.util.Set;

public class ComuneDTO {

	private Long idComune;
	private String siglaProvincia;
	private String codiceIstat;
	private String codiceMf;
	private String denominazione;
	private Date vlInizio;
	private Date vlFine;
	private Date duCatAgg;
	private Long idProvincia;
	private Set<LocalitaDTO> localita;
	public Long getIdComune() {
		return idComune;
	}
	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getCodiceIstat() {
		return codiceIstat;
	}
	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}
	public String getCodiceMf() {
		return codiceMf;
	}
	public void setCodiceMf(String codiceMf) {
		this.codiceMf = codiceMf;
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
	public Long getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}
	public Set<LocalitaDTO> getLocalita() {
		return localita;
	}
	public void setLocalita(Set<LocalitaDTO> localita) {
		this.localita = localita;
	}
	
}
