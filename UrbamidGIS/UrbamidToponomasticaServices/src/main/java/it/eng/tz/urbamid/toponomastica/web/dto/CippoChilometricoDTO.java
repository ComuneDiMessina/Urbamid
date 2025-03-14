package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class CippoChilometricoDTO implements Serializable {

	private static final long serialVersionUID = -5760473795542000006L;
	
	private Long id;
	private EstesaAmministrativaDTO estesaAmministrativa;
	private String codice;
	private Double misura;
	private String note;
	private String geom;
	private String stato;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EstesaAmministrativaDTO getEstesaAmministrativa() {
		return estesaAmministrativa;
	}
	public void setEstesaAmministrativa(EstesaAmministrativaDTO estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Double getMisura() {
		return misura;
	}
	public void setMisura(Double misura) {
		this.misura = misura;
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
