package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroup;

public class ResLayerGroup implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSLayerGroup layerGroup;

	public ResLayerGroup() {
	}

	public ResLayerGroup(GSLayerGroup layerGroup) {
		super();
		this.layerGroup = layerGroup;
	}

	public GSLayerGroup getLayerGroup() {
		return layerGroup;
	}

	public void setLayerGroup(GSLayerGroup layerGroup) {
		this.layerGroup = layerGroup;
	}
	
}