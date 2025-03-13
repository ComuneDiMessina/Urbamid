/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.util.List;
import java.util.Map;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSConnectionParameters {
	private List<Map<String, String>> entry;

	public List<Map<String, String>> getEntry() {
		return entry;
	}

	public void setEntry(List<Map<String, String>> entry) {
		this.entry = entry;
	}
}
