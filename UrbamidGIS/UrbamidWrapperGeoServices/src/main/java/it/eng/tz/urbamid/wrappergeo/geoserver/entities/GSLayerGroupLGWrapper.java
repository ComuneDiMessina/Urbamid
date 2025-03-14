package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSLayerGroupLGWrapper implements Serializable {

	private static final long serialVersionUID = 6808169827306878038L;
	
	private GSLayerGroupLG layerGroup;

	public GSLayerGroupLG getLayerGroup() {
		return layerGroup;
	}

	public void setLayerGroup(GSLayerGroupLG layerGroup) {
		this.layerGroup = layerGroup;
	}
	
}
