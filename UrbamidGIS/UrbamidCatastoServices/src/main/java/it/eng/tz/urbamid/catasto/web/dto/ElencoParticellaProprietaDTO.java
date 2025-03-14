package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class ElencoParticellaProprietaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cognome;
	private String citta;
	private String dataNascita;
	private String codiceFiscale;
	private String diritto;
	private String tipoSoggetto;

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
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDiritto() {
		return diritto;
	}
	public void setDiritto(String diritto) {
		this.diritto = diritto;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

}
