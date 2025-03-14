package it.eng.tz.urbamid.catasto.persistence.model.batch;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_catasto_batch_job_execution_params")
public class BatchJobExecutionParam implements Serializable {

	private static final long serialVersionUID = 6793752383292206080L;
	
	@EmbeddedId
	private BatchJobExecutionParamId id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="job_execution_id", insertable=false, updatable=false)
    private BatchJobExecution jobExecution;
	
	@Column(name="type_cd", length=6, nullable=false)
	private String tipo;
	
	@Column(name="string_val", length=250)
	private String valoreStringa;
	
	@Column(name="date_val")
	@Temporal(TemporalType.TIMESTAMP)
	private Date valoreData;
	
	@Column(name="long_val")
	private Long valoreLong;
	
	@Column(name="double_val")
	private Double valoreDouble;
	
	@Column(name="identifying", length=1, nullable=false)
	private String flagIdentificazione;

	public BatchJobExecutionParam() {
		super();
	}

	public String getTipo() {
		return tipo;
	}

	public String getValoreStringa() {
		return valoreStringa;
	}

	public Date getValoreData() {
		return valoreData;
	}

	public Long getValoreLong() {
		return valoreLong;
	}

	public Double getValoreDouble() {
		return valoreDouble;
	}

	public String getFlagIdentificazione() {
		return flagIdentificazione;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setValoreStringa(String valoreStringa) {
		this.valoreStringa = valoreStringa;
	}

	public void setValoreData(Date valoreData) {
		this.valoreData = valoreData;
	}

	public void setValoreLong(Long valoreLong) {
		this.valoreLong = valoreLong;
	}

	public void setValoreDouble(Double valoreDouble) {
		this.valoreDouble = valoreDouble;
	}

	public void setFlagIdentificazione(String flagIdentificazione) {
		this.flagIdentificazione = flagIdentificazione;
	}

	public BatchJobExecution getJobExecution() {
		return jobExecution;
	}

	public void setJobExecution(BatchJobExecution jobExecution) {
		this.jobExecution = jobExecution;
	}

	public BatchJobExecutionParamId getId() {
		return id;
	}

	public void setId(BatchJobExecutionParamId id) {
		this.id = id;
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
		if (!(obj instanceof BatchJobExecutionParam)) {
			return false;
		}
		BatchJobExecutionParam other = (BatchJobExecutionParam) obj;
		if(this.id == null || other.getId() == null) {
			return false;
		}
		return Objects.equals(id, other.id);
	}

}
