package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSBounds implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private String minx;
	
	private String maxx;
	
	private String miny;
	
	private String maxy;
	
	private String crs;
	
	public GSBounds() {
	}

	public String getMinx() {
		return minx;
	}

	public void setMinx(String minx) {
		this.minx = minx;
	}

	public String getMaxx() {
		return maxx;
	}

	public void setMaxx(String maxx) {
		this.maxx = maxx;
	}

	public String getMiny() {
		return miny;
	}

	public void setMiny(String miny) {
		this.miny = miny;
	}

	public String getMaxy() {
		return maxy;
	}

	public void setMaxy(String maxy) {
		this.maxy = maxy;
	}

	public String getCrs() {
		return crs;
	}

	public void setCrs(String crs) {
		this.crs = crs;
	}


}