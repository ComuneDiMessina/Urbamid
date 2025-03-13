package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayer;

public class ResLayer implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSLayer layer;

	public ResLayer() {
	}

	public GSLayer getLayer() {
		return layer;
	}

	public void setLayer(GSLayer layer) {
		this.layer = layer;
	}
	
}