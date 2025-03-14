package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class TipoTopologicoDTO implements Serializable {

	private static final long serialVersionUID = 3929254390280485807L;

	private Long id;
	private String descrizione;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
