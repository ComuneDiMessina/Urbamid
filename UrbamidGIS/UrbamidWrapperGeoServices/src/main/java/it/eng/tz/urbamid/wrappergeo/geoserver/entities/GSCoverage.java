/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;


/**
 * Example GEOTIFF XML
 * <coverage>
	<name>test_coverage_sfdem</name>
	<nativeName>test_coverage_sfdem</nativeName>
	<namespace>
		<name>sf</name>
		<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8080/geoserver/rest/namespaces/sf.xml" type="application/xml"/>
	</namespace>
	<nativeCRS class="projected">
		PROJCS["NAD27 / UTM zone 13N", GEOGCS["NAD27", DATUM["North American Datum 1927", SPHEROID["Clarke 1866", 6378206.4, 294.9786982138982, AUTHORITY["EPSG","7008"]], TOWGS84[2.478, 149.752, 197.726, 0.526, -0.498, 0.501, 0.685], AUTHORITY["EPSG","6267"]], PRIMEM["Greenwich", 0.0, AUTHORITY["EPSG","8901"]], UNIT["degree", 0.017453292519943295], AXIS["Geodetic longitude", EAST], AXIS["Geodetic latitude", NORTH], AUTHORITY["EPSG","4267"]], PROJECTION["Transverse_Mercator"], PARAMETER["central_meridian", -105.0], PARAMETER["latitude_of_origin", 0.0], PARAMETER["scale_factor", 0.9996], PARAMETER["false_easting", 500000.0], PARAMETER["false_northing", 0.0], UNIT["m", 1.0], AXIS["Easting", EAST], AXIS["Northing", NORTH], AUTHORITY["EPSG","26713"]]
	</nativeCRS>
	<srs>EPSG:4326</srs>
	<nativeBoundingBox>
		<minx>589980.0</minx>
		<maxx>609000.0</maxx>
		<miny>4913700.0</miny>
		<maxy>4928010.0</maxy>
		<crs class="projected">EPSG:26713</crs>
	</nativeBoundingBox>
	<latLonBoundingBox>
		<minx>-103.87100615361031</minx>
		<maxx>-103.62932676908186</maxx>
		<miny>44.37021187004215</miny>
		<maxy>44.50162561960653</maxy>
		<crs>
			GEOGCS["WGS84(DD)", DATUM["WGS84", SPHEROID["WGS84", 6378137.0, 298.257223563]], PRIMEM["Greenwich", 0.0], UNIT["degree", 0.017453292519943295], AXIS["Geodetic longitude", EAST], AXIS["Geodetic latitude", NORTH]]
		</crs>
	</latLonBoundingBox>
	<projectionPolicy>REPROJECT_TO_DECLARED</projectionPolicy>
	<enabled>true</enabled>
	<store class="coverageStore">
		<name>sf:test_coverage_sfdem</name>
		<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://192.168.11.46:8080/geoserver/rest/workspaces/sf/coveragestores/test_coverage_sfdem.xml" type="application/xml"/>
	</store>
	<grid dimension="2">
		<range>
			<low>0 0</low>
			<high>634 477</high>
		</range>
		<transform>
			<scaleX>30.0</scaleX>
			<scaleY>-30.0</scaleY>
			<shearX>0.0</shearX>
			<shearY>0.0</shearY>
			<translateX>589995.0</translateX>
			<translateY>4927995.0</translateY>
		</transform>
		<crs>EPSG:26713</crs>
	</grid>
</coverage>
*
 * 
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *
 */
public class GSCoverage {
	
	private String name;
	private boolean enabled;
	private String nativeCRS;
	private String srs;
	private GSCoverageStore store;
	private String url;
	
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
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNativeCRS() {
		return nativeCRS;
	}
	public void setNativeCRS(String nativeCRS) {
		this.nativeCRS = nativeCRS;
	}
	
	public String getSrs() {
		return srs;
	}
	public void setSrs(String srs) {
		this.srs = srs;
	}
	
	public GSCoverageStore getStore() {
		return store;
	}
	public void setStore(GSCoverageStore store) {
		this.store = store;
	}
}
