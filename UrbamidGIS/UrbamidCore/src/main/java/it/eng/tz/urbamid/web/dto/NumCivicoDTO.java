package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NumCivicoDTO implements Serializable {

	private static final long serialVersionUID = -3704053874125437542L;
	
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private String cs;
	private String num;
	private String sRef;
	private Double lat;
	private Double lon;
	
	@JsonIgnore
	private String separatore = "/"; 
	
	public NumCivicoDTO() {
		super();
		this.lat = 0.;
		this.lon = 0.0;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCs() {
		return cs;
	}



	public void setCs(String cs) {
		this.cs = cs;
	}



	public String getNum() {
		return num;
	}



	public void setNum(String num) {
		this.num = num;
	}



	public String getsRef() {
		return sRef;
	}



	public void setsRef(String sRef) {
		this.sRef = sRef;
	}



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
	
	



	public String getSeparatore() {
		return separatore;
	}


	public void setSeparatore(String separatore) {
		this.separatore = separatore;
	}


	@Override
	public String toString() {
		return String.format(
				"NumCivicoDTO [id=%s, cs=%s, num=%s, sRef=%s, lat=%s, lon=%s]",
				id, cs, num, sRef, lat, lon);
	}

}
