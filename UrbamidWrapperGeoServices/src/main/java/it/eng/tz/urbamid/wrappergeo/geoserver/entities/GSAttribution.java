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
public class GSAttribution {
	private String logoWidth;
	
	private String logoHeight;

	public String getLogoWidth() {
		return logoWidth;
	}

	public void setLogoWidth(String logoWidth) {
		this.logoWidth = logoWidth;
	}

	public String getLogoHeight() {
		return logoHeight;
	}

	public void setLogoHeight(String logoHeight) {
		this.logoHeight = logoHeight;
	}
}