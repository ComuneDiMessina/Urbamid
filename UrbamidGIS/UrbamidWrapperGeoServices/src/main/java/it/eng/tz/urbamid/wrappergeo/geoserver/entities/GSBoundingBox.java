/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.math.BigDecimal;

/* Example
"latLonBoundingBox": {
    "minx": -74.0118315772888,
    "maxx": -74.00857344353275,
    "miny": 40.70754683896324,
    "maxy": 40.711945649065406,
    "crs": "EPSG:4326"
  },
 * 
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GSBoundingBox {
	
	private BigDecimal minx;
	private BigDecimal maxx;
	private BigDecimal miny;
	private BigDecimal maxy;
	private String crs;
	
	public BigDecimal getMinx() {
		return minx;
	}
	public void setMinx(BigDecimal minx) {
		this.minx = minx;
	}
	public BigDecimal getMaxx() {
		return maxx;
	}
	public void setMaxx(BigDecimal maxx) {
		this.maxx = maxx;
	}
	public BigDecimal getMiny() {
		return miny;
	}
	public void setMiny(BigDecimal miny) {
		this.miny = miny;
	}
	public BigDecimal getMaxy() {
		return maxy;
	}
	public void setMaxy(BigDecimal maxy) {
		this.maxy = maxy;
	}
	public String getCrs() {
		return crs;
	}
	public void setCrs(String crs) {
		this.crs = crs;
	}
	
}
