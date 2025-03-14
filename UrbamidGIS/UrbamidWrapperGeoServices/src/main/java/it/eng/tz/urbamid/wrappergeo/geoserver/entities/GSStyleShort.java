/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Example Style XML
 * <style>
	<name>poly_landmarks</name>
	<format>sld</format>
	<languageVersion>
		<version>1.0.0</version>
	</languageVersion>
	<filename>poly_landmarks.sld</filename>
  </style>
*
 * 
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GSStyleShort {
	
	
	private String name;
//	private String link;
	private String href;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getLink() {
//		return link;
//	}
//	public void setLink(String link) {
//		this.link = link;
//	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
}
