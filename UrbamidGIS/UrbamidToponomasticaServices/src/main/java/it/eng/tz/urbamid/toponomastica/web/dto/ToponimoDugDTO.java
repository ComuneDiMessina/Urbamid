package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class ToponimoDugDTO implements Serializable {

	private static final long serialVersionUID = -2505751156911937049L;
	
	private Long id;
	private String toponimo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToponimo() {
		return toponimo;
	}
	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
	
}
