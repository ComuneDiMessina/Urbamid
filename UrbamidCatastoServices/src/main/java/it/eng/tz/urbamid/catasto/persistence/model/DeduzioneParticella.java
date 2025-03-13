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
@Table(name="u_cat_deduzioni_particella")
public class DeduzioneParticella implements Serializable {

	private static final long serialVersionUID = 7232115429676150516L;

	@Id 
	@SequenceGenerator(name="deduzioni_particella_id_seq", sequenceName="deduzioni_particella_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="deduzioni_particella_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "progressivo", length=3)
	private String progressivo;

	@Column(name = "simbolo_deduzioni", length=6)
	private String simboloDeduzioni;

	@Column(name = "id_immobile")
	private Long idImmobile;

	public DeduzioneParticella () { }

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

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

	public String getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}

	public String getSimboloDeduzioni() {
		return simboloDeduzioni;
	}

	public void setSimboloDeduzioni(String simboloDeduzioni) {
		this.simboloDeduzioni = simboloDeduzioni;
	}

	public Long getIdImmobile() {
		return idImmobile;
	}

	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}

}
