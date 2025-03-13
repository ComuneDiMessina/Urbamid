package it.eng.tz.urbamid.webgis.dao.mapper;

import java.io.Serializable;
import java.util.HashMap;

public class LayerBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private HashMap<String,String> properties;
	private String wktGeometry;
	
	public LayerBean() {
	}

	public LayerBean(HashMap<String, String> properties, String wktGeometry) {
		super();
		this.properties = properties;
		this.wktGeometry = wktGeometry;
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public String getWktGeometry() {
		return wktGeometry;
	}

	public void setWktGeometry(String wktGeometry) {
		this.wktGeometry = wktGeometry;
	}
}