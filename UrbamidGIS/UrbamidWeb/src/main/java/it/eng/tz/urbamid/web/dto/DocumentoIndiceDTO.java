package it.eng.tz.urbamid.web.dto;

public class DocumentoIndiceDTO {

	private Long idIndice;
	private DocumentoVarianteDTO documento;
	private Integer articolo;
	private String elencoPagine;

	public Long getIdIndice() {
		return idIndice;
	}
	public void setIdIndice(Long idIndice) {
		this.idIndice = idIndice;
	}
	public DocumentoVarianteDTO getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoVarianteDTO documento) {
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
