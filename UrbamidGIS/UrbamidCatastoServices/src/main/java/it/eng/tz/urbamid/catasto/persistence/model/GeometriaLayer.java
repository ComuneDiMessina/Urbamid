package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

public class GeometriaLayer implements Serializable {

	private static final long serialVersionUID = 3613532055234295657L;
	
	private Long gid;

	private String geom;
	
	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}
	
	
	
}
