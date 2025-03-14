package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class TipoAccessoDTO implements Serializable {

	private static final long serialVersionUID = 6733948705999305064L;
	
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
