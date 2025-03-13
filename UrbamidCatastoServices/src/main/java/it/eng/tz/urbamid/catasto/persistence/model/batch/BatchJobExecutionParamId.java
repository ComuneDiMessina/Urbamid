package it.eng.tz.urbamid.catasto.persistence.model.batch;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatchJobExecutionParamId implements Serializable {

	private static final long serialVersionUID = 6434125773508006350L;
	
	@Column(name="job_execution_id", nullable=false, updatable=false, unique=true)
	private Long jobExecutionId;
	
	@Column(name="key_name", length=100, nullable=false)
	private String key;
	
	public BatchJobExecutionParamId() {
		super();
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public String getKey() {
		return key;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(jobExecutionId, key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BatchJobExecutionParamId)) {
			return false;
		}
		BatchJobExecutionParamId other = (BatchJobExecutionParamId) obj;
		if(this.jobExecutionId == null || this.key == null 
				|| other.getJobExecutionId() == null || other.getKey() == null) {
			return false;
		}
		return Objects.equals(jobExecutionId, other.jobExecutionId) && Objects.equals(key, other.key);
	}

}
