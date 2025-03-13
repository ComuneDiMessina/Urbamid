package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class RequestReverseGeocodingDTO implements Serializable {

	private static final long serialVersionUID = 2118960901825990067L;

	private Double lat;
	private Double lon;
	private Double gradi;
	private String distance;
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getGradi() {
		return gradi;
	}
	public void setGradi(Double gradi) {
		this.gradi = gradi;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
}
