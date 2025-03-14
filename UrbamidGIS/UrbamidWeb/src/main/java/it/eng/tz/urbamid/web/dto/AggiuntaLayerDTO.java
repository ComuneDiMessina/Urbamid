package it.eng.tz.urbamid.web.dto;

public class AggiuntaLayerDTO {

	private Long idGruppo;
	private String layerName;
	private String layerTitle;
	private String hrefDetail;
	private String sorgente;
	private String nomeColonnaLayer;
	
	public Long getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}
	public String getLayerName() {
		return layerName;
	}
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	public String getLayerTitle() {
		return layerTitle;
	}
	public void setLayerTitle(String layerTitle) {
		this.layerTitle = layerTitle;
	}
	public String getSorgente() {
		return sorgente;
	}
	public String getHrefDetail() {
		return hrefDetail;
	}
	public void setHrefDetail(String hrefDetail) {
		this.hrefDetail = hrefDetail;
	}
	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}
	public String getNomeColonnaLayer() {
		return nomeColonnaLayer;
	}
	public void setNomeColonnaLayer(String nomeColonnaLayer) {
		this.nomeColonnaLayer = nomeColonnaLayer;
	}
	

}
