package it.eng.tz.urbamid.prg.web.dto;

public class TipoDocumentoDTO {

	private Long id;
	private String codice;
	private String descrizione;
	private String descrizioneCDU;
	private String note;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneCDU() {
		return descrizioneCDU;
	}
	public void setDescrizioneCDU(String descrizioneCDU) {
		this.descrizioneCDU = descrizioneCDU;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
