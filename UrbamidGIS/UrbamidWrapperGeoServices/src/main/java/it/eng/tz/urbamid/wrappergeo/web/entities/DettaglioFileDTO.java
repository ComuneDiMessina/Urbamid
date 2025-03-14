package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

public class DettaglioFileDTO implements Serializable {

	private static final long serialVersionUID = -3800603619656363349L;
	
	private boolean success;
	private String nome;
	private Long dimensione;
	private String tipo;
	private String dataCreazione;
	
	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
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


	public String getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Override
	public String toString() {
		return String.format("DettaglioFileDTO [nome=%s, dimensione=%s, tipo=%s, codiceComune=%s, dataCreazione=%s]",
				nome, dimensione, tipo, dataCreazione);
	}
	
}
