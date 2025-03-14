package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class RicercaIntestazioniDTO implements Serializable {

	private static final long serialVersionUID = -5773879259571196593L;

	private String codiceFiscale;
	private String cognome;
	private String nome;
	private Long idSoggetto;
	private String tipo;
	private String sezione;
	private String foglio;
	private String numero;
	private String subalterno;
	private String codComune;
	private Long idImmobile;

	private String denominazione;
	private String ute;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getCodComune() {
		return codComune;
	}
	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}
	public Long getIdImmobile() {
		return idImmobile;
	}
	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}
	public String getUte() {
		return ute;
	}
	public void setUte(String ute) {
		this.ute = ute;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
}
