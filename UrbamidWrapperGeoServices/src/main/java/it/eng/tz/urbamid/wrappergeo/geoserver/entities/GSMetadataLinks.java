package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSMetadataLinks implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private String type;
	
	private String metadataType;
	
	private String content;
	
	public GSMetadataLinks() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMetadataType() {
		return metadataType;
	}

	public void setMetadataType(String metadataType) {
		this.metadataType = metadataType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}