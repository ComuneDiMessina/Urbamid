package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroups;

public class ResLayerGroups implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSLayerGroups layerGroups;

	public ResLayerGroups() {
	}

	public GSLayerGroups getLayerGroups() {
		return layerGroups;
	}

	public void setLayerGroups(GSLayerGroups layerGroups) {
		this.layerGroups = layerGroups;
	}
	
}