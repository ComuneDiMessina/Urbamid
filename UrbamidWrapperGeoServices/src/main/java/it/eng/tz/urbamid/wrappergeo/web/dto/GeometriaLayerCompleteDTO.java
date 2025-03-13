package it.eng.tz.urbamid.wrappergeo.web.dto;

import java.io.Serializable;

public class GeometriaLayerCompleteDTO implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;
	
	private Long id;
	private String nameColId;
	private Double distance;
	private String geometry;
	
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

	public String getGeometry() {
		return geometry;
	}
	
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
}
