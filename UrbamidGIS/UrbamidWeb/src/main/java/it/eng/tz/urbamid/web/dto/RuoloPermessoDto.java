package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class RuoloPermessoDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6899144885200165623L;

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
