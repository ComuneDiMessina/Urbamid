package it.eng.tz.urbamid.prg.web.dto;

import java.io.Serializable;

public class CartografiaVarianteLayerDTO implements Serializable {

	private static final long serialVersionUID = 4372045838175491715L;
	
	private Long idVariante;
	private String nomeVariante;
	private String nomeLayer;
	
	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}
	public String getNomeVariante() {
		return nomeVariante;
	}
	public void setNomeVariante(String nomeVariante) {
		this.nomeVariante = nomeVariante;
	}
	public String getNomeLayer() {
		return nomeLayer;
	}
	public void setNomeLayer(String nomeLayer) {
		this.nomeLayer = nomeLayer;
	}
	
}
