/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;


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
public class GSStyle {
	
	private String name;
	private String filename;
	private String format;
	private GSLanguageVersion languageVersion;
	private GSWorkspaceShort workspace;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	public GSLanguageVersion getLanguageVersion() {
		return languageVersion;
	}
	public void setLanguageVersion(GSLanguageVersion languageVersion) {
		this.languageVersion = languageVersion;
	}
	public GSWorkspaceShort getWorkspace() {
		return workspace;
	}
	public void setWorkspace(GSWorkspaceShort workspace) {
		this.workspace = workspace;
	}

}
