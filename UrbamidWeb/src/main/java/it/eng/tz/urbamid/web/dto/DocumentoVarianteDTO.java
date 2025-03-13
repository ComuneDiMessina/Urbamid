package it.eng.tz.urbamid.web.dto;

import java.util.List;

public class DocumentoVarianteDTO {

	private Long idDocumento;
	private VarianteDTO variante;
	private String tipoDocumento;
	private String nomeDocumento;
	private String statoDocumento;
	private String pathDocumento;
	List<DocumentoIndiceDTO> listaIndici;

	private String estensione;

	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public VarianteDTO getVariante() {
		return variante;
	}
	public void setVariante(VarianteDTO variante) {
		this.variante = variante;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public String getStatoDocumento() {
		return statoDocumento;
	}
	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}
	public String getPathDocumento() {
		return pathDocumento;
	}
	public void setPathDocumento(String pathDocumento) {
		this.pathDocumento = pathDocumento;
	}
	public List<DocumentoIndiceDTO> getListaIndici() {
		return listaIndici;
	}
	public void setListaIndici(List<DocumentoIndiceDTO> listaIndici) {
		this.listaIndici = listaIndici;
	}
	public String getEstensione() {
		return estensione;
	}
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

}
