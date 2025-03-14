package it.eng.tz.urbamid.wrappergeo.persistence.model;

import java.io.Serializable;

public class GeometriaCompleteLayer implements Serializable {

	private static final long serialVersionUID = 3613532055234295657L;
	
	private Long id;
	private String nameColId;
	private Double distance;
	private String geom;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameColId() {
		return nameColId;
	}

	public void setNameColId(String nameColId) {
		this.nameColId = nameColId;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}
	
	
	
}
