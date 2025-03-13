/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.util.List;
import java.util.Map;

/**
 * Example SHAPEFILE XML
 * <dataStore>
    <name>sf</name>
    <enabled>true</enabled>
    <workspace>
        <name>sf</name>
        <atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate"
            href="http://localhost:8080/geoserver/rest/workspaces/sf.xml"
            type="application/xml"/>
    </workspace>
    <connectionParameters>
        <entry key="namespace">http://www.openplans.org/spearfish</entry>
        <entry key="url">file:data/sf</entry>
    </connectionParameters>
    <featureTypes>
        <atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate"
            href="http://localhost:8080/geoserver/rest/workspaces/sf/datastores/sf/featuretypes.xml"
            type="application/xml"/>
    </featureTypes>
</dataStore>
 * 
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSDataStore {
	
	public static final String TYPE_POSTGIS_JNDI = "PostGIS (JNDI)";
	
	private String name;
	private boolean enabled;
	private GSWorkspaceShort workspace;
	private String featureTypes;
	private GSConnectionParameters connectionParameters;
	
	//Sembra che per gli shape sia vuoto
	private String type;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getFeatureTypes() {
		return featureTypes;
	}
	public void setFeatureTypes(String featureTypes) {
		this.featureTypes = featureTypes;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GSConnectionParameters getConnectionParameters() {
		return connectionParameters;
	}
	public void setConnectionParameters(GSConnectionParameters connectionParameters) {
		this.connectionParameters = connectionParameters;
	}
	
	/*
	 * utility method
	 */
	public List<Map<String, String>> getConnectionParamEntries() {
		return connectionParameters.getEntry();
	}
	
}
