package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class PatrimonialitaDTO implements Serializable {

	private static final long serialVersionUID = -9216385409625621694L;

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
