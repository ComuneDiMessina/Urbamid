package it.eng.tz.urbamid.webgis.dao.model;

import java.util.HashMap;

public class Layer implements java.io.Serializable {

	private static final long serialVersionUID = 3467989111284292050L;

	private HashMap<String,String> properties;
	private String wktGeometry;

	public Layer() {
	}

	public Layer(HashMap<String, String> properties, String wktGeometry) {
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
