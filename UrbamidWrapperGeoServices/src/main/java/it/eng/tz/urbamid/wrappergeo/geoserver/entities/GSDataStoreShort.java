/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSDataStoreShort {
	@JsonProperty(value = "@class")
	private String classs;
	private String name;
	private String href;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}

}
