package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

public class FeatureGeomDTO implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;
	
//	@JsonIgnore
	private Long id;
//	@JsonIgnore
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
