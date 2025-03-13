package it.eng.tz.urbamid.wrappergeo.persistence.model;

import java.io.Serializable;

public class GeometriaLayer implements Serializable {

	private static final long serialVersionUID = 3613532055234295657L;
	
	private Long id;
	private String geom;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}
	
	
	
}
