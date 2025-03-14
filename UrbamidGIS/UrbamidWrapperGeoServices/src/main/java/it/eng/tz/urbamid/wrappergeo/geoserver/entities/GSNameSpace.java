/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

/**
 * Example Namespace "namespace": { "name": "tiger", "href":
 * "http://localhost:8080/geoserver/rest/namespaces/tiger.json" }
 * 
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSNameSpace {
	private String name;
	private String href;

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
}
