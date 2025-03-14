package it.eng.tz.urbamid.catasto.persistence.model.batch;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_catasto_batch_step_execution")
public class BatchStepExecution implements Serializable {

	private static final long serialVersionUID = 4207406390337134964L;
	
	@Id 
	@SequenceGenerator(name="catasto_batch_step_execution_seq", sequenceName="catasto_batch_step_execution_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="catasto_batch_step_execution_seq")
	@Column(name="step_execution_id", nullable=false, unique=true, updatable=false)
	private Long stepExecutionId;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "stepExecution", fetch=FetchType.LAZY)
    private BatchStepExecutionContext stepExecutionContext;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="job_execution_id", nullable=false, updatable=false)
	private BatchJobExecution jobExecution;
	
	@Column(name="version", nullable=false)
	private Long versione;
	
	@Column(name="step_name", length=100, nullable=false)
	private String nomeStep;
	
	@Column(name="start_time", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAvvio;
	
	@Column(name="end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFine;
	
	@Column(name="status", length=10)
	private String stato;
	
//	commit_count bigint,
//	read_count bigint,
//	filter_count bigint,
//	write_count bigint,
//	read_skip_count bigint,
//	write_skip_count bigint,
//	process_skip_count bigint,
//	rollback_count bigint,
	
	@Column(name="exit_code")
	private String codiceUscita;
	
	@Column(name="exit_message")
	private String messaggioUscita;
	
	@Column(name="last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAggiornamento;
	
	public BatchStepExecution() {
		super();
	}

	public Long getStepExecutionId() {
		return stepExecutionId;
	}

	public BatchJobExecution getJobExecution() {
		return jobExecution;
	}

	public Long getVersione() {
		return versione;
	}

	public String getNomeStep() {
		return nomeStep;
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

	public void setStepExecutionId(Long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public void setJobExecution(BatchJobExecution jobExecution) {
		this.jobExecution = jobExecution;
	}

	public void setVersione(Long versione) {
		this.versione = versione;
	}

	public void setNomeStep(String nomeStep) {
		this.nomeStep = nomeStep;
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

	public BatchStepExecutionContext getStepExecutionContext() {
		return stepExecutionContext;
	}

	public void setStepExecutionContext(BatchStepExecutionContext stepExecutionContext) {
		this.stepExecutionContext = stepExecutionContext;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stepExecutionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BatchStepExecution)) {
			return false;
		}
		BatchStepExecution other = (BatchStepExecution) obj;
		if(this.stepExecutionId == null || other.getStepExecutionId() == null) {
			return false;
		}
		return Objects.equals(stepExecutionId, other.stepExecutionId);
	}
	
}