package it.eng.tz.urbamid.web.dto;

import java.util.List;

public class CatalogoGruppoDTO {

	private Long id;
	private String nomeGruppo;
	private List<CatalogoLayerDTO> listCatalogoLayer;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeGruppo() {
		return nomeGruppo;
	}
	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}
	public List<CatalogoLayerDTO> getListCatalogoLayer() {
		return listCatalogoLayer;
	}
	public void setListCatalogoLayer(List<CatalogoLayerDTO> listCatalogoLayer) {
		this.listCatalogoLayer = listCatalogoLayer;
	}

}
