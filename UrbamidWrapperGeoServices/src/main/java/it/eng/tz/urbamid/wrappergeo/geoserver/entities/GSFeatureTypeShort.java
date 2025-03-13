/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

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
public class GSFeatureTypeShort {
	
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
