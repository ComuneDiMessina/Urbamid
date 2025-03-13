/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

/**
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *example 
 *<defaultStyle>
	<name>green</name>
	<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8080/geoserver/rest/styles/green.xml" type="application/xml"/>
  </defaultStyle>
 */
public class GSDefaultStyle {
	private String name; 
	private String href;
	private String workspace;
	
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
	public String getWorkspace() {
		return workspace;
	}
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}
	

}
