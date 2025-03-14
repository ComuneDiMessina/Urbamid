package it.eng.tz.urbamid.catasto.filter;

import java.io.Serializable;
import java.util.Date;

public class SoggettoFilter implements Serializable {

	private static final long serialVersionUID = 5952858562687126856L;

	private String nome;
	private String cognome;
	private String sesso;
	private String codiceFiscale;
	private Date dataNascitaDa;
	private Date dataNascitaA;
	private String provincia;
	private String comune;
	private String note;
	private String denominazione;
	private Integer pageIndex;
	private Integer pageSize;

	private String tipo;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Date getDataNascitaDa() {
		return dataNascitaDa;
	}
	public void setDataNascitaDa(Date dataNascitaDa) {
		this.dataNascitaDa = dataNascitaDa;
	}
	public Date getDataNascitaA() {
		return dataNascitaA;
	}
	public void setDataNascitaA(Date dataNascitaA) {
		this.dataNascitaA = dataNascitaA;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
