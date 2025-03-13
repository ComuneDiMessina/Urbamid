package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSFeatureTypeDBWrapper implements Serializable {

	private static final long serialVersionUID = 2990559842402537940L;

	private GSResFeatureTypeDB featureType;

	public GSFeatureTypeDBWrapper(GSResFeatureTypeDB featureType) {
		super();
		this.featureType = featureType;
	}

	public GSResFeatureTypeDB getFeatureType() {
		return featureType;
	}

	public void setFeatureType(GSResFeatureTypeDB featureType) {
		this.featureType = featureType;
	}
	
}
