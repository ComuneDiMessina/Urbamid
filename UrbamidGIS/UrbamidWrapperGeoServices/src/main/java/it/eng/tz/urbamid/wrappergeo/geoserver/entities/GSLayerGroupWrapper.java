package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class GSLayerGroupWrapper implements Serializable {

	private static final long serialVersionUID = -5068770262812563388L;
	
	@SerializedName(value = "layerGroup")
	private GSLayerGroup layerGroup;

	public GSLayerGroupWrapper(GSLayerGroup layerGroup) {
		this.layerGroup = layerGroup;
	}

	public GSLayerGroup getLayerGroup() {
		return layerGroup;
	}

	public void setLayerGroup(GSLayerGroup layerGroup) {
		this.layerGroup = layerGroup;
	}
	
}
