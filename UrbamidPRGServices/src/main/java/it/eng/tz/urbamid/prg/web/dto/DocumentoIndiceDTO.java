package it.eng.tz.urbamid.prg.web.dto;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoVariante;

public class DocumentoIndiceDTO {

	private Long idIndice;
	private DocumentoVariante documento;
	private Integer articolo;
	private String elencoPagine;

	public Long getIdIndice() {
		return idIndice;
	}
	public void setIdIndice(Long idIndice) {
		this.idIndice = idIndice;
	}
	public DocumentoVariante getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoVariante documento) {
		this.documento = documento;
	}
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

}
