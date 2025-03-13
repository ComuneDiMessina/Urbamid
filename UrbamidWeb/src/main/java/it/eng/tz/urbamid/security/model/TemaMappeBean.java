package it.eng.tz.urbamid.security.model;

import java.io.Serializable;

public class TemaMappeBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Long idTema;
	private String nomeTema;
	private String stato;
	private Long idMappa;
	private String nomeMappa;
	private String codeMappa;
	private Long ordinamento;
	
	public TemaMappeBean() {
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	public String getNomeTema() {
		return nomeTema;
	}

	public void setNomeTema(String nomeTema) {
		this.nomeTema = nomeTema;
	}

	public Long getIdMappa() {
		return idMappa;
	}

	public void setIdMappa(Long idMappa) {
		this.idMappa = idMappa;
	}

	public String getNomeMappa() {
		return nomeMappa;
	}

	public void setNomeMappa(String nomeMappa) {
		this.nomeMappa = nomeMappa;
	}

	public String getCodeMappa() {
		return codeMappa;
	}

	public void setCodeMappa(String codeMappa) {
		this.codeMappa = codeMappa;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Long getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}

}