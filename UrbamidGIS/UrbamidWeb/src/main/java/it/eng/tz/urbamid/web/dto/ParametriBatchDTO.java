package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class ParametriBatchDTO implements Serializable {

	private static final long serialVersionUID = -889903012347991887L;
	
	private String uuid;
	private String dataAvvio;
	private String livelloLog;
	
	public ParametriBatchDTO() {
		super();
	}
	
	public ParametriBatchDTO(String uuid, String dataAvvio, String livelloLog) {
		super();
		this.uuid = uuid;
		this.dataAvvio = dataAvvio;
		this.livelloLog = livelloLog;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(String dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public String getLivelloLog() {
		return livelloLog;
	}

	public void setLivelloLog(String livelloLog) {
		this.livelloLog = livelloLog;
	}

	@Override
	public String toString() {
		return String.format("ParametriBatchDTO [uuid=%s, dataAvvio=%s, livelloLog=%s]", uuid, dataAvvio, livelloLog);
	}

}