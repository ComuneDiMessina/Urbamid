package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class ToponimoDugDTO implements Serializable {

	private static final long serialVersionUID = -2603998916244579474L;
	
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
