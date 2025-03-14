/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Example Feature Type
 * {
  "name": "poi",
  "nativeName": "poi",
  "namespace": {
    "name": "tiger",
    "href": "http://localhost:8080/geoserver/rest/namespaces/tiger.json"
  },
  "title": "Manhattan (NY) points of interest",
  "abstract": "Points of interest in New York, New York (on Manhattan). One of the attributes contains the name of a file with a picture of the point of interest.",
  "keywords": {
    "string": [
      "poi",
      "Manhattan",
      "DS_poi",
      "points_of_interest",
      "sampleKeyword\\@language=ab\\;",
      "area of effect\\@language=bg\\;\\@vocabulary=technical\\;",
      "Привет\\@language=ru\\;\\@vocabulary=friendly\\;"
    ]
  },
  "metadataLinks": {
    "metadataLink": [
      {
        "type": "text/plain",
        "metadataType": "FGDC",
        "content": "www.google.com"
      }
    ]
  },
  "dataLinks": {
    "org.geoserver.catalog.impl.DataLinkInfoImpl": [
      {
        "type": "text/plain",
        "content": "http://www.google.com"
      }
    ]
  },
  "nativeCRS": "GEOGCS[\"WGS 84\", \n  DATUM[\"World Geodetic System 1984\", \n    SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], \n    AUTHORITY[\"EPSG\",\"6326\"]], \n  PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], \n  UNIT[\"degree\", 0.017453292519943295], \n  AXIS[\"Geodetic longitude\", EAST], \n  AXIS[\"Geodetic latitude\", NORTH], \n  AUTHORITY[\"EPSG\",\"4326\"]]",
  "srs": "EPSG:4326",
  "nativeBoundingBox": {
    "minx": -74.0118315772888,
    "maxx": -74.00153046439813,
    "miny": 40.70754683896324,
    "maxy": 40.719885123828675,
    "crs": "EPSG:4326"
  },
  "latLonBoundingBox": {
    "minx": -74.0118315772888,
    "maxx": -74.00857344353275,
    "miny": 40.70754683896324,
    "maxy": 40.711945649065406,
    "crs": "EPSG:4326"
  },
  "projectionPolicy": "REPROJECT_TO_DECLARED",
  "enabled": true,
  "metadata": {
    "entry": [
      {
        "@key": "kml.regionateStrategy",
        "$": "external-sorting"
      },
      {
        "@key": "kml.regionateFeatureLimit",
        "$": "15"
      },
      {
        "@key": "cacheAgeMax",
        "$": "3000"
      },
      {
        "@key": "cachingEnabled",
        "$": "true"
      },
      {
        "@key": "kml.regionateAttribute",
        "$": "NAME"
      },
      {
        "@key": "indexingEnabled",
        "$": "false"
      },
      {
        "@key": "dirName",
        "$": "DS_poi_poi"
      }
    ]
  },
  "store": {
    "@class": "dataStore",
    "name": "tiger:nyc",
    "href": "http://localhost:8080/geoserver/rest/workspaces/tiger/datastores/nyc.json"
  },
  "cqlFilter": "INCLUDE",
  "maxFeatures": 100,
  "numDecimals": 6,
  "responseSRS": {
    "string": [
      4326
    ]
  },
  "overridingServiceSRS": true,
  "skipNumberMatched": true,
  "circularArcPresent": true,
  "linearizationTolerance": 10,
  "attributes": {
    "attribute": [
      {
        "name": "the_geom",
        "minOccurs": 0,
        "maxOccurs": 1,
        "nillable": true,
        "binding": "com.vividsolutions.jts.geom.Point"
      },
      {},
      {},
      {}
    ]
  }
}
 * 
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
@XmlRootElement(name="featureType")
public class GSFeatureTypeM {
	
	public static final String PROJ_POLICY_REPROJECT_NATIVE_TO_DECLARED = "REPROJECT_TO_DECLARED";
	public static final String PROJ_POLICY_REPROJECT_FORCE_DECLARED = "FORCE_DECLARED";
	public static final String PROJ_POLICY_KEEP_NATIVE = "NONE";
	
	//Name of the feature
	private String name;
	
	//Name of the feature in the data store (eg table in postgis)
	private String nativeName;
	private GSNameSpace namespace;
	private String title;
	
	//EPSG or WKT String
	private GSNativeCRS nativeCRS;
	//EPSG or WKT String
	private String srs;
	
	//Non obbligatori, calcolati automaticamente da BBOX
	private GSBoundingBox nativeBoundingBox;
	private GSBoundingBox latLonBoundingBox;
	
	private String projectionPolicy;
	
	private boolean enabled;

	private GSDataStoreShort store;
	
	private GSKeywords keywords;
	
	private GSResponseSRS responseSRS;
	
	private GSAttributesM attributes;	
	
	private boolean advertised;
	
	private boolean serviceConfiguration;
	
	private Integer maxFeatures;
	
	private Integer numDecimals;
	
	private boolean padWithZeros;
	
	private boolean forcedDecimal;
	
	private boolean overridingServiceSRS;
	
	private boolean skipNumberMatched;
	
	private boolean circularArcPresent;
	
	private boolean encodeMeasures;
	
	@JsonProperty(value = "abstract")
	private String abs;
	
	public String getName() {
		return name;
	}
	
	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNativeName() {
		return nativeName;
	}
	
	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}
	
	public GSNameSpace getNamespace() {
		return namespace;
	}
	
	public void setNamespace(GSNameSpace namespace) {
		this.namespace = namespace;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public GSNativeCRS getNativeCRS() {
		return nativeCRS;
	}
	
	public void setNativeCRS(GSNativeCRS nativeCRS) {
		this.nativeCRS = nativeCRS;
	}
	
	public String getSrs() {
		return srs;
	}
	
	public void setSrs(String srs) {
		this.srs = srs;
	}
	
	public GSBoundingBox getNativeBoundingBox() {
		return nativeBoundingBox;
	}
	
	public void setNativeBoundingBox(GSBoundingBox nativeBoundingBox) {
		this.nativeBoundingBox = nativeBoundingBox;
	}
	
	public GSBoundingBox getLatLonBoundingBox() {
		return latLonBoundingBox;
	}
	
	public void setLatLonBoundingBox(GSBoundingBox latLonBoundingBox) {
		this.latLonBoundingBox = latLonBoundingBox;
	}
	
	public String getProjectionPolicy() {
		return projectionPolicy;
	}
	
	public void setProjectionPolicy(String projectionPolicy) {
		this.projectionPolicy = projectionPolicy;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public GSDataStoreShort getStore() {
		return store;
	}
	
	public void setStore(GSDataStoreShort store) {
		this.store = store;
	}
	
	public GSKeywords getKeywords() {
		return keywords;
	}
	
	public void setKeywords(GSKeywords keywords) {
		this.keywords = keywords;
	}
	
	public GSResponseSRS getResponseSRS() {
		return responseSRS;
	}
	
	public void setResponseSRS(GSResponseSRS responseSRS) {
		this.responseSRS = responseSRS;
	}
	
	public GSAttributesM getAttributes() {
		return attributes;
	}
	
	public void setAttributes(GSAttributesM attributes) {
		this.attributes = attributes;
	}
	
	public boolean isAdvertised() {
		return advertised;
	}
	
	public void setAdvertised(boolean advertised) {
		this.advertised = advertised;
	}
	
	public boolean isServiceConfiguration() {
		return serviceConfiguration;
	}
	
	public void setServiceConfiguration(boolean serviceConfiguration) {
		this.serviceConfiguration = serviceConfiguration;
	}
	
	public Integer getMaxFeatures() {
		return maxFeatures;
	}
	
	public void setMaxFeatures(Integer maxFeatures) {
		this.maxFeatures = maxFeatures;
	}
	
	public Integer getNumDecimals() {
		return numDecimals;
	}
	
	public void setNumDecimals(Integer numDecimals) {
		this.numDecimals = numDecimals;
	}
	
	public boolean isPadWithZeros() {
		return padWithZeros;
	}
	
	public void setPadWithZeros(boolean padWithZeros) {
		this.padWithZeros = padWithZeros;
	}
	
	public boolean isForcedDecimal() {
		return forcedDecimal;
	}
	
	public void setForcedDecimal(boolean forcedDecimal) {
		this.forcedDecimal = forcedDecimal;
	}
	
	public boolean isOverridingServiceSRS() {
		return overridingServiceSRS;
	}
	
	public void setOverridingServiceSRS(boolean overridingServiceSRS) {
		this.overridingServiceSRS = overridingServiceSRS;
	}
	
	public boolean isSkipNumberMatched() {
		return skipNumberMatched;
	}
	
	public void setSkipNumberMatched(boolean skipNumberMatched) {
		this.skipNumberMatched = skipNumberMatched;
	}
	
	public boolean isCircularArcPresent() {
		return circularArcPresent;
	}
	
	public void setCircularArcPresent(boolean circularArcPresent) {
		this.circularArcPresent = circularArcPresent;
	}
	
	public boolean isEncodeMeasures() {
		return encodeMeasures;
	}
	
	public void setEncodeMeasures(boolean encodeMeasures) {
		this.encodeMeasures = encodeMeasures;
	}
	
	
}
