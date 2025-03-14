package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSLayers implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<GSLayerShort> layer;

	public GSLayers() {
	}

	public List<GSLayerShort> getLayer() {
		return layer;
	}

	public void setLayer(List<GSLayerShort> layer) {
		this.layer = layer;
	}

	
}