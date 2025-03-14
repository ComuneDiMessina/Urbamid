/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *example 
 *<defaultStyle>
	<name>green</name>
	<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8080/geoserver/rest/styles/green.xml" type="application/xml"/>
  </defaultStyle>
 */
public class GSResource {
	private String name; 
	private String href;
	@JsonProperty(value = "@class")
	private String classs;
	
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
	public String getClasss() {
		return classs;
	}
	public void setClasss(String classs) {
		this.classs = classs;
	}
}
