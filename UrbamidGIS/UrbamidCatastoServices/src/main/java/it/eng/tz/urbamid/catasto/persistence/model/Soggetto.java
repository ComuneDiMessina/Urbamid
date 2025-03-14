package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_soggetti")
public class Soggetto implements Serializable {

	private static final long serialVersionUID = -8006522139310696206L;

	@Id 
	@SequenceGenerator(name="soggetti_id_seq", sequenceName="soggetti_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="soggetti_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "id_soggetto")
	private int idSoggetto;

	@Column(name = "tipo_soggetto", length=1)
	private String tipoSoggetto;

	@Column(name = "cognome", length=50)
	private String cognome;

	@Column(name = "nome", length=50)
	private String nome;

	@Column(name = "sesso", length=1)
	private String sesso;

	@Column(name = "data_nascita")
	@Temporal(value = TemporalType.DATE)
	private Date dataNascita;

	@Column(name = "luogo_nascita", length=4)
	private String luogoNascita;

	@Column(name = "codice_fiscale", length=16)
	private String codiceFiscale;

	@Column(name = "denominazione", length=150)
	private String denominazione;

	@Column(name = "sede", length=4)
	private String sede;

	@Column(name = "indicazioni_supplementari", length=200)
	private String indicazioniSupplementari;

	public Soggetto () { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public int getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
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

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getIndicazioniSupplementari() {
		return indicazioniSupplementari;
	}

	public void setIndicazioniSupplementari(String indicazioniSupplementari) {
		this.indicazioniSupplementari = indicazioniSupplementari;
	}

}
