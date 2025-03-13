package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

public class FeatureType implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;
	
	private String name;
	
	private String title;

	private String nativeCRS;
	
	private String srs;
	
	private boolean enabled;

	public FeatureType() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNativeCRS() {
		return nativeCRS;
	}

	public void setNativeCRS(String nativeCRS) {
		this.nativeCRS = nativeCRS;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	} 
}