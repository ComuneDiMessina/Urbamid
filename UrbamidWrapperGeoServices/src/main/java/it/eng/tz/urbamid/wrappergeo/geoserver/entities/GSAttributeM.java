package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GSAttributeM implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private String name; 			//": "the_geom",
	private Integer minOccurs; 		//"minOccurs": 0,
	private Integer maxOccurs; 		//"maxOccurs": 1,
	private Boolean nillable; 		//"nillable": true,
	@JsonProperty(value = "binding")
	private String binding; 		//"binding": "com.vividsolutions.jts.geom.Point"
	private String length;
    	
	public GSAttributeM() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinOccurs() {
		return minOccurs;
	}

	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}

	public Integer getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(Integer maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public Boolean getNillable() {
		return nillable;
	}

	public void setNillable(Boolean nillable) {
		this.nillable = nillable;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}


}