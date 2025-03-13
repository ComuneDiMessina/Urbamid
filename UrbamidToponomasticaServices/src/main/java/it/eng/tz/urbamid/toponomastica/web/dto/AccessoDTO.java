package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class AccessoDTO implements Serializable {

	private static final long serialVersionUID = 7404315950288585782L;
	
	private Long id;
	private ToponimoStradaleDTO toponimo;
	private LocalitaDTO localita;
	private int numero;
	private String esponente;
	private TipoAccessoDTO tipo;
	private boolean passoCarrabile;
	private boolean principale;
	private String metodo;
	private String note;
	private String geom;
	private String stato;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ToponimoStradaleDTO getToponimo() {
		return toponimo;
	}
	public void setToponimo(ToponimoStradaleDTO toponimo) {
		this.toponimo = toponimo;
	}
	public LocalitaDTO getLocalita() {
		return localita;
	}
	public void setLocalita(LocalitaDTO localita) {
		this.localita = localita;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getEsponente() {
		return esponente;
	}
	public void setEsponente(String esponente) {
		this.esponente = esponente;
	}
	public TipoAccessoDTO getTipo() {
		return tipo;
	}
	public void setTipo(TipoAccessoDTO tipo) {
		this.tipo = tipo;
	}
	public boolean isPassoCarrabile() {
		return passoCarrabile;
	}
	public void setPassoCarrabile(boolean passoCarrabile) {
		this.passoCarrabile = passoCarrabile;
	}
	public boolean isPrincipale() {
		return principale;
	}
	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
