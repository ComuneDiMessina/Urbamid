package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class TipoDocumentoVarianteDTO implements Serializable {

	private static final long serialVersionUID = 2272599916996822555L;

	private Long idDocumento;
	private String tipoDocumento;
	private String nomeVariante;
	
	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNomeVariante() {
		return nomeVariante;
	}
	public void setNomeVariante(String nomeVariante) {
		this.nomeVariante = nomeVariante;
	}
	
}
