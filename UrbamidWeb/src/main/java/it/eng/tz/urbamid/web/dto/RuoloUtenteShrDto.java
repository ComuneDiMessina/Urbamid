package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class RuoloUtenteShrDto implements Serializable {

	private static final long serialVersionUID = 4246478149341743801L;
	

	private long id;
	
	private long idUtente;
	
	private long idRuolo;
	
	private String codRuolo;
	
	public RuoloUtenteShrDto() {				
	}

	public RuoloUtenteShrDto(long idUtente, long idRuolo) {
		super();
		this.idUtente = idUtente;
		this.idRuolo = idRuolo;
	}
	
	public RuoloUtenteShrDto(long idUtente, String codRuolo) {
		super();
		this.idUtente = idUtente;
		this.codRuolo = codRuolo;
	}

	public RuoloUtenteShrDto(long id, long idUtente, long idRuolo) {
		super();
		this.id = id;
		this.idUtente = idUtente;
		this.idRuolo = idRuolo;
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

	public String getCodeRuolo() {
		return codRuolo;
	}

	public void setCodRuolo(String codRuolo) {
		this.codRuolo = codRuolo;
	}

}
