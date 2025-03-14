package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class GeometriaLayerDTO implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;
	
	private Long id;
	private String geometry;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeometry() {
		return geometry;
	}
	
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
}
