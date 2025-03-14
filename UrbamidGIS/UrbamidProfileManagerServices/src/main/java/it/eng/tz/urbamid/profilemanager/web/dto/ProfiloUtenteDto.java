package it.eng.tz.urbamid.profilemanager.web.dto;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.urbamid.profilemanager.util.CommonUtil;

public class ProfiloUtenteDto implements Serializable{

	private static final long serialVersionUID = 755520576812559471L;
	
	private Long id;
	private String username;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String email;
	private String note;
	private boolean abilitato;
	private List<RuoloUtenteDto> ruoli;
	private List<String> permessi;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isAbilitato() {
		return abilitato;
	}
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	public List<RuoloUtenteDto> getRuoli() {
		return ruoli;
	}
	public void setRuoli(List<RuoloUtenteDto> ruoli) {
		this.ruoli = ruoli;
	}
	public List<String> getPermessi() {
		return permessi;
	}
	public void setPermessi(List<String> permessi) {
		this.permessi = permessi;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("id=").append(getId())
        	  .append(",")
        	  .append("username=").append(getUsername())
        	  .append(",")
        	  .append("Ruoli={");
		if(CommonUtil.listIsNotEmpty(getRuoli())) {
			getRuoli().forEach(ruolo->buffer.append(ruolo.toString()).append(","));
		}else {
			buffer.append("isEmpty");
		}
		buffer.append("}")
			  .append(",")
			  .append("Ruoli={");
		if(CommonUtil.listIsNotEmpty(getPermessi())) {
			getPermessi().forEach(permesso->buffer.append(permesso).append(","));
		}else {
			buffer.append("isEmpty");
		}
		buffer.append("}");
        buffer.append(']');
        return buffer.toString();
    }
	
}
