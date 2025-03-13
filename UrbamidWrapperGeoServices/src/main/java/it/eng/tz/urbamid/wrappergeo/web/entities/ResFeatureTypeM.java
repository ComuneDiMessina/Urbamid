package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureTypeM;

public class ResFeatureTypeM implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSFeatureTypeM featureType;

	public ResFeatureTypeM() {
	}

	public GSFeatureTypeM getFeatureType() {
		return featureType;
	}

	public void setFeatureType(GSFeatureTypeM featureType) {
		this.featureType = featureType;
	}

	
}