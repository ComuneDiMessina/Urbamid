/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

/**
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *
 *Example:
 * <layer>
		<name>tasmania_state_boundaries</name>
		<path>/</path>
		<type>VECTOR</type>
		<defaultStyle>
			<name>green</name>
			<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8080/geoserver/rest/styles/green.xml" type="application/xml"/>
		</defaultStyle>
		<resource class="featureType">
			<name>tasmania_state_boundaries</name>
			<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8080/geoserver/rest/workspaces/topp/datastores/taz_shapes/featuretypes/tasmania_state_boundaries.xml" type="application/xml"/>
		</resource>
		<attribution>
			<logoWidth>0</logoWidth>
			<logoHeight>0</logoHeight>
		</attribution>
	</layer>
 */
public class GSLayer {
	
	private String name;
	private String type;
	private GSDefaultStyle defaultStyle;
	private GSResource resource;
	private Boolean queryable;
	private Boolean opaque;
	private GSAttribution attribution;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public GSDefaultStyle getDefaultStyle() {
		return defaultStyle;
	}
	public void setDefaultStyle(GSDefaultStyle defaultStyle) {
		this.defaultStyle = defaultStyle;
	}
	public GSResource getResource() {
		return resource;
	}
	public void setResource(GSResource resource) {
		this.resource = resource;
	}
	public Boolean getQueryable() {
		return queryable;
	}
	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}
	public Boolean getOpaque() {
		return opaque;
	}
	public void setOpaque(Boolean opaque) {
		this.opaque = opaque;
	}
	public GSAttribution getAttribution() {
		return attribution;
	}
	public void setAttribution(GSAttribution attribution) {
		this.attribution = attribution;
	}
	
	
	
}
