package it.eng.tz.urbamid.security.model;

import java.io.Serializable;

public class MyRole implements Serializable{
	
	private static final long serialVersionUID = 3809196557788417619L;
	protected Long id;
	protected String codice;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}

}
