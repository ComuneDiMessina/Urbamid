/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSWorkspace extends GSWorkspaceShort {
	private String dataStores;
	private String coverageStores;
	private String wmsStores;
	
	public String getDataStores() {
		return dataStores;
	}
	public void setDataStores(String dataStores) {
		this.dataStores = dataStores;
	}
	public String getCoverageStores() {
		return coverageStores;
	}
	public void setCoverageStores(String coverageStores) {
		this.coverageStores = coverageStores;
	}
	public String getWmsStores() {
		return wmsStores;
	}
	public void setWmsStores(String wmsStores) {
		this.wmsStores = wmsStores;
	}

}
