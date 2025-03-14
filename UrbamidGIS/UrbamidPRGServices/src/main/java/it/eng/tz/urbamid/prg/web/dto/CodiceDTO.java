package it.eng.tz.urbamid.prg.web.dto;

import java.util.List;

public class CodiceDTO {

	private Long idIndice;
	private List<String> layers;
	
	public Long getIdIndice() {
		return idIndice;
	}
	public void setIdIndice(Long idIndice) {
		this.idIndice = idIndice;
	}
	public List<String> getLayers() {
		return layers;
	}
	public void setLayers(List<String> layers) {
		this.layers = layers;
	}

}
