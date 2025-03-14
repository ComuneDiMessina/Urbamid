package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
@Table(name="u_cat_aggiornamenti", schema="public")
public class AggiornamentoCatasto implements Serializable {

	private static final long serialVersionUID = -3496031057625232607L;
	
	@Id 
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	@SequenceGenerator(name="u_cat_aggiornamenti_id_seq", sequenceName="u_cat_aggiornamenti_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_cat_aggiornamenti_id_seq")
	private Long id;
	
	@Column(name="comune")
	private String comune;
	
	@Column(name="data_richiesta")
	@Temporal(TemporalType.DATE)
	private Date dataRichiesta;
	
	@Column(name="data_elaborazione")
	@Temporal(TemporalType.DATE)
	private Date dataElaborazione;
	
	@Column(name="tipo_estrazione")
	private String tipoEstrazione;
	
	@Column(name="numero_record")
	private String numeroRecord;
	
	@Column(name="data_inizio_validita")
	@Temporal(TemporalType.DATE)
	private Date dataInizioValidita;
	
	@Column(name="data_fine_validita")
	@Temporal(TemporalType.DATE)
	private Date dataFineValidita;
	
	@Column(name="data_creazione")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCreazione;
	
	public AggiornamentoCatasto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getComune() {
		return comune;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public Date getDataElaborazione() {
		return dataElaborazione;
	}

	public String getTipoEstrazione() {
		return tipoEstrazione;
	}

	public String getNumeroRecord() {
		return numeroRecord;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public void setDataElaborazione(Date dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}

	public void setTipoEstrazione(String tipoEstrazione) {
		this.tipoEstrazione = tipoEstrazione;
	}

	public void setNumeroRecord(String numeroRecord) {
		this.numeroRecord = numeroRecord;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AggiornamentoCatasto)) {
			return false;
		}
		AggiornamentoCatasto other = (AggiornamentoCatasto) obj;
		return Objects.equals(id, other.id);
	}
	
}
