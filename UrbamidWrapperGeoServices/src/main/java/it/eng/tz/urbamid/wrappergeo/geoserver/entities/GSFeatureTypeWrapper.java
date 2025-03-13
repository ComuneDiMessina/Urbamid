/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSFeatureTypeWrapper {

	private GSFeatureType featureType;

	public GSFeatureTypeWrapper(GSFeatureType ft) {
		this.featureType = ft;
	}
	
	public GSFeatureType getFeatureType() {
		return featureType;
	}

	public void setFeatureType(GSFeatureType featureType) {
		this.featureType = featureType;
	}
}
