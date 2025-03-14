package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureType;

public class ResFeatureType implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSFeatureType featureType;

	public ResFeatureType() {
	}

	public GSFeatureType getFeatureType() {
		return featureType;
	}

	public void setFeatureType(GSFeatureType featureType) {
		this.featureType = featureType;
	}

	
}