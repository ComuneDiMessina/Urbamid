/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

/**
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *
 *Example:
 *<truncateLayer><layerName>workspace:layername</layerName></truncateLayer>
 */
public class GSTruncateLayer {
	
	private String layerName;

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	
	
}
