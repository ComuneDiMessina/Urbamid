package it.eng.tz.urbamid.prg.web.dto;

import java.util.List;

public class CartografiaDTO {

	private Long idVariante;
	private List<String> layers;

	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}
	public List<String> getLayers() {
		return layers;
	}
	public void setLayers(List<String> layers) {
		this.layers = layers;
	}

}
