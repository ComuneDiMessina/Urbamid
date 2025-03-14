/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.util.List;
import java.util.Map;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSDataStorePostgisJNDI extends GSDataStore {

	public GSDataStorePostgisJNDI(String name, boolean enabled, GSWorkspaceShort workspace, List<Map<String, String>> connectionParameterEntries) {
		super();
		setName(name);
		setType(GSDataStore.TYPE_POSTGIS_JNDI);
		setEnabled(enabled);
		setWorkspace(workspace);
		
		GSConnectionParameters connP = new GSConnectionParameters();
		connP.setEntry(connectionParameterEntries);
		setConnectionParameters(connP);
		
	}
}
