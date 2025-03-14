package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSResFeatureTypeWrapperDB implements Serializable {

	private static final long serialVersionUID = 7413529617284971432L;

	private GSResFeatureTypeDB featureType;

	public GSResFeatureTypeDB getFeatureType() {
		return featureType;
	}

	public void setFeatureType(GSResFeatureTypeDB featureType) {
		this.featureType = featureType;
	}
	
	
	
}
