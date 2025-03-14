package it.eng.tz.urbamid.catasto.persistence.model.batch;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_catasto_batch_job_execution")
public class BatchJobExecution implements Serializable {

	private static final long serialVersionUID = -4121865472608672636L;

	@Id 
	@SequenceGenerator(name="catasto_batch_job_execution_seq", sequenceName="catasto_batch_job_execution_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="catasto_batch_job_execution_seq")
	@Column(name="job_execution_id", nullable=false, unique=true, updatable=false)
	private Long jobExecutionId;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "jobExecution", fetch=FetchType.LAZY)
	private BatchJobExecutionContext jobExecutionContext;
	
	@OneToMany(mappedBy="jobExecution", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<BatchJobExecutionParam> parametri;
	
	@Column(name="version")
	private Long versione;
	
	//@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "job_instance_id")
	private BatchJobInstance jobInstance;
	
	@Column(name="create_time", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCreazione;
	
	@Column(name="start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAvvio;
	
	@Column(name="end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFine;
	
	@Column(name="status", length=10)
	private String stato;
	
	@Column(name="exit_code")
	private String codiceUscita;
	
	@Column(name="exit_message")
	private String messaggioUscita;
	
	@Column(name="last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAggiornamento;
	
	@OneToMany(mappedBy="jobExecution", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
	private Set<BatchStepExecution> listaStep;

	public BatchJobExecution() {
		super();
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public Long getVersione() {
		return versione;
	}

	public BatchJobInstance getJobInstance() {
		return jobInstance;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public Date getDataAvvio() {
		return dataAvvio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public String getStato() {
		return stato;
	}

	public String getCodiceUscita() {
		return codiceUscita;
	}

	public String getMessaggioUscita() {
		return messaggioUscita;
	}

	public Date getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setVersione(Long versione) {
		this.versione = versione;
	}

	public void setJobInstance(BatchJobInstance jobInstance) {
		this.jobInstance = jobInstance;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public void setDataAvvio(Date dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setCodiceUscita(String codiceUscita) {
		this.codiceUscita = codiceUscita;
	}

	public void setMessaggioUscita(String messaggioUscita) {
		this.messaggioUscita = messaggioUscita;
	}

	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public Set<BatchStepExecution> getListaStep() {
		return listaStep;
	}

	public void setListaStep(Set<BatchStepExecution> listaStep) {
		this.listaStep = listaStep;
	}

	public BatchJobExecutionContext getJobExecutionContext() {
		return jobExecutionContext;
	}

	public Set<BatchJobExecutionParam> getParametri() {
		return parametri;
	}

	public void setJobExecutionContext(BatchJobExecutionContext jobExecutionContext) {
		this.jobExecutionContext = jobExecutionContext;
	}

	public void setParametri(Set<BatchJobExecutionParam> parametri) {
		this.parametri = parametri;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobExecutionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BatchJobExecution)) {
			return false;
		}
		BatchJobExecution other = (BatchJobExecution) obj;
		if(this.jobExecutionId == null || other.getJobExecutionId() == null) {
			return false;
		}
		return Objects.equals(jobExecutionId, other.jobExecutionId);
	}
	
}
