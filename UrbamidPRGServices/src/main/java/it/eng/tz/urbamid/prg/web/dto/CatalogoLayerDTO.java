package it.eng.tz.urbamid.prg.web.dto;

public class CatalogoLayerDTO {

	private Long id;
	private Long idGruppo;
	private String idLayer;
	private String nomeLayer;
	private String hrefDetail;
	private String sorgente;
	private String nomeColonna;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}
	public String getIdLayer() {
		return idLayer;
	}
	public void setIdLayer(String idLayer) {
		this.idLayer = idLayer;
	}
	public String getNomeLayer() {
		return nomeLayer;
	}
	public void setNomeLayer(String nomeLayer) {
		this.nomeLayer = nomeLayer;
	}
	public String getHrefDetail() {
		return hrefDetail;
	}
	public void setHrefDetail(String hrefDetail) {
		this.hrefDetail = hrefDetail;
	}
	public String getSorgente() {
		return sorgente;
	}
	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}
	public String getNomeColonna() {
		return nomeColonna;
	}
	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}
	

}
