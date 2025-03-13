package it.eng.tz.urbamid.toponomastica.filter;

import java.io.Serializable;

public class ToponimoDugFilter implements Serializable {

	private static final long serialVersionUID = 9146420259968946674L;
	
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
