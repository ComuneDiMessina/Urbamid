package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class PercorsoDTO implements Serializable {

	private static final long serialVersionUID = 2538466212136001558L;
	
	private Long id;
	private String sigla;
	private String descrizione;
	private ToponimoStradaleDTO toponimo;
	private EstesaAmministrativaDTO estesaAmministrativa;
	private String note;
	private String geom;
	private String stato;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public ToponimoStradaleDTO getToponimo() {
		return toponimo;
	}
	public void setToponimo(ToponimoStradaleDTO toponimo) {
		this.toponimo = toponimo;
	}
	public EstesaAmministrativaDTO getEstesaAmministrativa() {
		return estesaAmministrativa;
	}
	public void setEstesaAmministrativa(EstesaAmministrativaDTO estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
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
