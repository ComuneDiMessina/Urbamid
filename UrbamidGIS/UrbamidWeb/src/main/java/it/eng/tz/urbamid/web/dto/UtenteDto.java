package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class UtenteDto implements Serializable {

	private static final long serialVersionUID = 4246478149341743801L;
	
	private Long id;
	
	private String username;
	
	private String codiceFiscale;
	
	private String nome;
	
	private String cognome;

	private String note;
	
	private String email;
	
	private Boolean abilitato;
	
	public UtenteDto() {				
	}

	public UtenteDto(String username, String codiceFiscale, String nome, String cognome, String note, String email,
			Boolean abilitato) {
		super();
		this.username = username;
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.note = note;
		this.email = email;
		this.abilitato = abilitato;
	}

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	

}
