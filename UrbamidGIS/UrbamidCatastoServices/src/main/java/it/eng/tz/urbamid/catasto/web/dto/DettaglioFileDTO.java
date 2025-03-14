package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class DettaglioFileDTO implements Serializable {

	private static final long serialVersionUID = -3800603619656363349L;
	
	private String nome;
	private Long dimensione;
	private String tipo;
	private String codiceComune;
	private String dataCreazione;
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getCodiceComune() {
		return codiceComune;
	}


	public void setCodiceComune(String codiceComune) {
		this.codiceComune = codiceComune;
	}


	public String getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public DettaglioFileDTO() {
		super();
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Long getDimensione() {
		return dimensione;
	}


	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}


	@Override
	public String toString() {
		return String.format("DettaglioFileDTO [nome=%s, dimensione=%s, tipo=%s, codiceComune=%s, dataCreazione=%s]",
				nome, dimensione, tipo, codiceComune, dataCreazione);
	}
	
}
