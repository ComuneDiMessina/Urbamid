package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;
import java.util.Set;

public class TipoLocalitaDTO implements Serializable {

	private static final long serialVersionUID = 78201633343934267L;

	private Long id;
	private String descrizione;
	private Set<LocalitaDTO> localita;
	
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
	public Set<LocalitaDTO> getLocalita() {
		return localita;
	}
	public void setLocalita(Set<LocalitaDTO> localita) {
		this.localita = localita;
	}
	
}
