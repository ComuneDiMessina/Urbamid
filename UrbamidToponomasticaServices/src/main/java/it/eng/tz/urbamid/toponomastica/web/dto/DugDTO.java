package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class DugDTO implements Serializable {

	private static final long serialVersionUID = -5371229542134748829L;
	
	private Long id;
	private String dug;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDug() {
		return dug;
	}
	public void setDug(String dug) {
		this.dug = dug;
	}
	
	
}
