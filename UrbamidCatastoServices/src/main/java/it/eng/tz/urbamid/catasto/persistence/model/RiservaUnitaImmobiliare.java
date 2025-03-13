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
@Table(name="u_cat_riserve_unita_immobiliare")
public class RiservaUnitaImmobiliare implements Serializable {

	private static final long serialVersionUID = 5839489214625187928L;

	@Id 
	@SequenceGenerator(name="riserve_unita_immobiliare_id_seq", sequenceName="riserve_unita_immobiliare_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="riserve_unita_immobiliare_id_seq")
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

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "codice_riserva", length=1)
	private String codiceRiserva;

	@Column(name = "partita_iscrizione_riserva", length=7)
	private String partitaIscrizioneRiserva;

	public RiservaUnitaImmobiliare () { }

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

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

	public String getCodiceRiserva() {
		return codiceRiserva;
	}

	public void setCodiceRiserva(String codiceRiserva) {
		this.codiceRiserva = codiceRiserva;
	}

	public String getPartitaIscrizioneRiserva() {
		return partitaIscrizioneRiserva;
	}

	public void setPartitaIscrizioneRiserva(String partitaIscrizioneRiserva) {
		this.partitaIscrizioneRiserva = partitaIscrizioneRiserva;
	}

}
