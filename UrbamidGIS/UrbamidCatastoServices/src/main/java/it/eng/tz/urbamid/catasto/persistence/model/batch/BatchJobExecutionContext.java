package it.eng.tz.urbamid.catasto.persistence.model.batch;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_catasto_batch_job_execution_context")
public class BatchJobExecutionContext implements Serializable {

	private static final long serialVersionUID = -7743380157362334520L;
	
	@Id
	@Column(name="job_execution_id")
	private Long jobExecutionId;
	
    @MapsId 
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "job_execution_id")
    private BatchJobExecution jobExecution;
	
	@Column(name="short_context", nullable=false)
	private String shortContext;
	
	@Column(name="serialized_context")
	private String serializedContext;
	
	public BatchJobExecutionContext() {
		super();
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public String getShortContext() {
		return shortContext;
	}

	public String getSerializedContext() {
		return serializedContext;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setShortContext(String shortContext) {
		this.shortContext = shortContext;
	}

	public void setSerializedContext(String serializedContext) {
		this.serializedContext = serializedContext;
	}

	public BatchJobExecution getJobExecution() {
		return jobExecution;
	}

	public void setJobExecution(BatchJobExecution jobExecution) {
		this.jobExecution = jobExecution;
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
		if (!(obj instanceof BatchJobExecutionContext)) {
			return false;
		}
		BatchJobExecutionContext other = (BatchJobExecutionContext) obj;
		if(this.jobExecutionId == null || other.getJobExecutionId() == null) {
			return false;
		}
		return Objects.equals(jobExecutionId, other.jobExecutionId);
	}
	
}
