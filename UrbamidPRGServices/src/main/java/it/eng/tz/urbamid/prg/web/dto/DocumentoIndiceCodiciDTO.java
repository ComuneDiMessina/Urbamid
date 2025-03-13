package it.eng.tz.urbamid.prg.web.dto;

public class DocumentoIndiceCodiciDTO {

	private Long idCodice;
	private String codice;
	private String descrizione;

	public Long getIdCodice() {
		return idCodice;
	}
	public void setIdCodice(Long idCodice) {
		this.idCodice = idCodice;
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

}
