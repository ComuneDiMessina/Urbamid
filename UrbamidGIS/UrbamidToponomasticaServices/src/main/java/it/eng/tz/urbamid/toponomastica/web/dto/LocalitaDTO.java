package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class LocalitaDTO implements Serializable {

	private static final long serialVersionUID = -8981974616178910296L;

	private Long id;
	private String descrizione;
	private ComuniDto comune;
	private String frazione;
	private TipoLocalitaDTO tipo;
	private String geom;
	private String note;
	private String stato;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public ComuniDto getComune() {
		return comune;
	}
	public void setComune(ComuniDto comune) {
		this.comune = comune;
	}
	public String getFrazione() {
		return frazione;
	}
	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}
	public TipoLocalitaDTO getTipo() {
		return tipo;
	}
	public void setTipo(TipoLocalitaDTO tipo) {
		this.tipo = tipo;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
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
}
