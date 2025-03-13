package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_utilita_unita_immobiliare")
public class UtilitaUnitaImmobiliare implements Serializable {

	private static final long serialVersionUID = 3851906502770180531L;

	@Id 
	@SequenceGenerator(name="utilita_unita_immobiliare_id_seq", sequenceName="utilita_unita_immobiliare_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="utilita_unita_immobiliare_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "id_immobile")
	private Long idImmobile;

	@Column(name = "progressivo")
	private int progressivo;

	@Column(name = "sezione_urbana", length=3)
	private String sezioneUrbana;

	@Column(name = "foglio", length=4)
	private String foglio;

	@Column(name = "numero", length=5)
	private String numero;

	@Column(name = "denominatore")
	private int denominatore;

	@Column(name = "subalterno", length=4)
	private String subalterno;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	public UtilitaUnitaImmobiliare () { }

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

	public Long getIdImmobile() {
		return idImmobile;
	}

	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}

	public int getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}

	public String getSezioneUrbana() {
		return sezioneUrbana;
	}

	public void setSezioneUrbana(String sezioneUrbana) {
		this.sezioneUrbana = sezioneUrbana;
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

	public int getDenominatore() {
		return denominatore;
	}

	public void setDenominatore(int denominatore) {
		this.denominatore = denominatore;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

}
