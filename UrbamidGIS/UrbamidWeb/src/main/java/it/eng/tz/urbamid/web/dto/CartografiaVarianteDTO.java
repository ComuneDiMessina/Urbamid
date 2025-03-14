package it.eng.tz.urbamid.web.dto;

public class CartografiaVarianteDTO {

	private Long idCartografia;
	private Long idVariante;
	private String nomeLayer;
	private String descrizioneLayer;
	private String dataImportazione;
	private String dataEsportazione;
	private String gruppoLayer;
	
	public Long getIdCartografia() {
		return idCartografia;
	}
	public void setIdCartografia(Long idCartografia) {
		this.idCartografia = idCartografia;
	}
	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}
	public String getNomeLayer() {
		return nomeLayer;
	}
	public void setNomeLayer(String nomeLayer) {
		this.nomeLayer = nomeLayer;
	}
	public String getDescrizioneLayer() {
		return descrizioneLayer;
	}
	public void setDescrizioneLayer(String descrizioneLayer) {
		this.descrizioneLayer = descrizioneLayer;
	}
	public String getDataImportazione() {
		return dataImportazione;
	}
	public void setDataImportazione(String dataImportazione) {
		this.dataImportazione = dataImportazione;
	}
	public String getDataEsportazione() {
		return dataEsportazione;
	}
	public void setDataEsportazione(String dataEsportazione) {
		this.dataEsportazione = dataEsportazione;
	}
	public String getGruppoLayer() {
		return gruppoLayer;
	}
	public void setGruppoLayer(String gruppoLayer) {
		this.gruppoLayer = gruppoLayer;
	}

}
