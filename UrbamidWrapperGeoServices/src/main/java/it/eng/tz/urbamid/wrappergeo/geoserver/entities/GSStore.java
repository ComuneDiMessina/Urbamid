package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class GSStore implements Serializable {

	private static final long serialVersionUID = -7203313321135395266L;

	@SerializedName(value = "@class")
	private String clas;
	
	private String name;
	
	private String href;

	public String getClas() {
		return clas;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}
