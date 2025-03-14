package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSLayerGroups implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<GSLayerGroup> layerGroup;

	public GSLayerGroups() {
	}

	public List<GSLayerGroup> getLayerGroup() {
		return layerGroup;
	}

	public void setLayerGroup(List<GSLayerGroup> layerGroup) {
		this.layerGroup = layerGroup;
	}

	
}