/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;


/**
 * Example GEOTIFF XML
 * <coverageStore>
	<name>ctrr</name>
	<description>ctrr</description>
	<type>GeoTIFF</type>
	<enabled>true</enabled>
	<workspace>
		<name>test</name>
			<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8093/geoserver/rest/workspaces/test.xml" type="application/xml"/>
	</workspace>
	<url>
		file:///opt/data_test/ctr_test/result/merged_resolved.tiff
	</url>
	<coverages>
		<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8093/geoserver/rest/workspaces/test/coveragestores/ctrr/coverages.xml" type="application/xml"/>
	</coverages>
</coverageStore>
*
 * 
 * @author XXX
 *
 */
public class GSCoverageStore {
	
	public static final String TYPE_GEOTIFF = "GeoTIFF";
	
	private String name;
	private String description;
	private String type;
	private boolean enabled;
	private GSWorkspaceShort workspace;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public GSWorkspaceShort getWorkspace() {
		return workspace;
	}
	public void setWorkspace(GSWorkspaceShort workspace) {
		this.workspace = workspace;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
