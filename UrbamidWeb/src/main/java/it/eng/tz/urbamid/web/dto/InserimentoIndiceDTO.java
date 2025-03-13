package it.eng.tz.urbamid.web.dto;

public class InserimentoIndiceDTO {

	private String articolo;
	private String elencoPagine;
	private Long idDocumento;

	public String getArticolo() {
		return articolo;
	}
	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}
	public String getElencoPagine() {
		return elencoPagine;
	}
	public void setElencoPagine(String elencoPagine) {
		this.elencoPagine = elencoPagine;
	}
	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

}
