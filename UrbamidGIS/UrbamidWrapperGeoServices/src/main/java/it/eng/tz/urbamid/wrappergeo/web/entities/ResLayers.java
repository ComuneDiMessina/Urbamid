package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayers;

public class ResLayers implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSLayers layers;

	public ResLayers() {
	}

	public GSLayers getLayers() {
		return layers;
	}

	public void setLayers(GSLayers layers) {
		this.layers = layers;
	}
	
}