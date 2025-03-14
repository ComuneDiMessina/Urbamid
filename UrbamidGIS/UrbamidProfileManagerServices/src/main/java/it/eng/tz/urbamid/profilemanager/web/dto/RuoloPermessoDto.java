package it.eng.tz.urbamid.profilemanager.web.dto;

import java.io.Serializable;

public class RuoloPermessoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long idRuolo;
	private long idPermesso;
	
	public RuoloPermessoDto() {
		super();
	}
	public RuoloPermessoDto(long id, long idRuolo, long idPermesso) {
		super();
		this.id = id;
		this.idRuolo = idRuolo;
		this.idPermesso = idPermesso;
	}
	public RuoloPermessoDto(long idRuolo, long idPermesso) {
		super();
		this.idRuolo = idRuolo;
		this.idPermesso = idPermesso;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdRuolo() {
		return idRuolo;
	}
	public void setIdRuolo(long idRuolo) {
		this.idRuolo = idRuolo;
	}
	public long getIdPermesso() {
		return idPermesso;
	}
	public void setIdPermesso(long idPermesso) {
		this.idPermesso = idPermesso;
	}
	
	
}
