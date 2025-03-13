package it.eng.tz.urbamid.catasto.persistence.model.batch;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( name="u_cat_catasto_batch_job_instance",
		uniqueConstraints=@UniqueConstraint(columnNames= {"job_name","job_key"}))
public class BatchJobInstance implements Serializable {

	private static final long serialVersionUID = 2352246931087506268L;

	@Id 
	@SequenceGenerator(name="catasto_batch_job_seq", sequenceName="catasto_batch_job_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="catasto_batch_job_seq")
	@Column(name="job_instance_id", nullable=false, unique=true, updatable=false)
	private Long jobInstanceId;
	
	@Column(name="version")
	private Long versione;
	
	@Column(name="job_name", length=100, nullable=false)
	private String nomeJob;
	
	@Column(name="job_key", length=32, nullable=false)
	private String key;
	
	public BatchJobInstance() {
		super();
	}

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public Long getVersione() {
		return versione;
	}

	public String getNomeJob() {
		return nomeJob;
	}

	public String getKey() {
		return key;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public void setVersione(Long versione) {
		this.versione = versione;
	}

	public void setNomeJob(String nomeJob) {
		this.nomeJob = nomeJob;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobInstanceId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BatchJobInstance)) {
			return false;
		}
		BatchJobInstance other = (BatchJobInstance) obj;
		if(this.jobInstanceId == null || other.getJobInstanceId() == null ) {
			return false;
		}
		return Objects.equals(jobInstanceId, other.jobInstanceId);
	}
	
}
