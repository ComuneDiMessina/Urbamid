package it.eng.tz.urbamid.prg.web.dto;

public class InserimentoIndiceDTO {

	private Integer articolo;
	private String elencoPagine;
	private Long idDocumento;

	public Integer getArticolo() {
		return articolo;
	}
	public void setArticolo(Integer articolo) {
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
