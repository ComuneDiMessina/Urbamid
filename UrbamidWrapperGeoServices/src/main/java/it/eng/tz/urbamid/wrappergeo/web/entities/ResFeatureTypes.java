package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureTypes;

public class ResFeatureTypes implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSFeatureTypes featureTypes;

	public ResFeatureTypes() {
	}

	public GSFeatureTypes getFeatureTypes() {
		return featureTypes;
	}

	public void setFeatureTypes(GSFeatureTypes featureTypes) {
		this.featureTypes = featureTypes;
	}

	
}