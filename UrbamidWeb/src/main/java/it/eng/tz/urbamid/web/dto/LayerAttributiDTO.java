package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;
import java.util.List;

public class LayerAttributiDTO implements Serializable {

	private static final long serialVersionUID = 8480231812544132806L;

	private Long id;
	private String identificativo;
	private String nome;
	private String descrizione;
	private String note;
	private String stato;
	private List<LayerGeometrieDTO> geometrie;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public List<LayerGeometrieDTO> getGeometrie() {
		return geometrie;
	}
	public void setGeometrie(List<LayerGeometrieDTO> geometrie) {
		this.geometrie = geometrie;
	}
	
}
