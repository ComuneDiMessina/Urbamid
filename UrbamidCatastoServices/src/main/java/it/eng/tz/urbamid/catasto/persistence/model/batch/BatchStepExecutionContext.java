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
@Table(name="u_cat_catasto_batch_step_execution_context")
public class BatchStepExecutionContext implements Serializable {

	private static final long serialVersionUID = 1487785879616909463L;

	@Id
	@Column(name="step_execution_id", nullable=false, unique=true, updatable=false)
	private Long stepExecutionId;
	
    @MapsId 
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "step_execution_id")
    private BatchStepExecution stepExecution;
	
	@Column(name="short_context", nullable=false)
	private String shortContext;
	
	@Column(name="serialized_context")
	private String serializedContext;
	
	public BatchStepExecutionContext() {
		super();
	}

	public Long getStepExecutionId() {
		return stepExecutionId;
	}

	public BatchStepExecution getStepExecution() {
		return stepExecution;
	}

	public String getShortContext() {
		return shortContext;
	}

	public String getSerializedContext() {
		return serializedContext;
	}

	public void setStepExecutionId(Long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public void setStepExecution(BatchStepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public void setShortContext(String shortContext) {
		this.shortContext = shortContext;
	}

	public void setSerializedContext(String serializedContext) {
		this.serializedContext = serializedContext;
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
		if (!(obj instanceof BatchStepExecutionContext)) {
			return false;
		}
		BatchStepExecutionContext other = (BatchStepExecutionContext) obj;
		if(this.stepExecutionId == null || other.getStepExecutionId() == null) {
			return false;
		}
		return Objects.equals(stepExecutionId, other.stepExecutionId);
	}
	
}