package it.eng.tz.urbamid.prg.web.dto;

import java.util.ArrayList;
import java.util.List;

import it.eng.tz.urbamid.prg.persistence.model.Variante;

public class DocumentoVarianteDTO {

	private Long idDocumento;
	private Variante variante;
	private String tipoDocumento;
	private String nomeDocumento;
	private String statoDocumento;
	private String pathDocumento;
	List<DocumentoIndiceDTO> listaIndici = new ArrayList<>();

	private String estensione;

	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Variante getVariante() {
		return variante;
	}
	public void setVariante(Variante variante) {
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
