/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSNativeCRS {
	
	@JsonProperty(value = "@class")
	private String classs;
	@JsonProperty(value = "$")
	private String proj;
	public String getClasss() {
		return classs;
	}
	public void setClasss(String classs) {
		this.classs = classs;
	}
	public String getProj() {
		return proj;
	}
	public void setProj(String proj) {
		this.proj = proj;
	}
	
}
