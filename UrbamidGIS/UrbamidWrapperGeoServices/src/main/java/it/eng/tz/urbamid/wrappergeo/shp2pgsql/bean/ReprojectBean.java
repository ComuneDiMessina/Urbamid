package it.eng.tz.urbamid.wrappergeo.shp2pgsql.bean;

import java.io.Serializable;

public class ReprojectBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7837794761210027186L;

	private Long gid;
	
	private String wkt;

	public ReprojectBean() {
		super();
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}
}
