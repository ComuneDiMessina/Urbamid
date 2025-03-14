package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class GeometriaToponomasticaDTO implements Serializable {

	private static final long serialVersionUID = -85941157165084278L;
	
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
