package it.eng.tz.urbamid.profilemanager.web.dto;

import java.io.Serializable;

public class RuoloUtenteShtDto implements Serializable {

	private static final long serialVersionUID = 4246478149341743801L;
	

	private long id;
	
	private long idUtente;
	
	private long idRuolo;
	
	private String codRuolo;
	
	public RuoloUtenteShtDto() {				
	}

	public RuoloUtenteShtDto(long idUtente) {
		super();
		this.idUtente = idUtente;
	}

	public RuoloUtenteShtDto(long idUtente, long idRuolo) {
		super();
		this.idUtente = idUtente;
		this.idRuolo = idRuolo;
	}

	public RuoloUtenteShtDto(long idUtente, String codRuolo) {
		super();
		this.idUtente = idUtente;
		this.codRuolo = codRuolo;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getCodRuolo() {
		return codRuolo;
	}

	public void setCodRuolo(String codRuolo) {
		this.codRuolo = codRuolo;
	}

}
