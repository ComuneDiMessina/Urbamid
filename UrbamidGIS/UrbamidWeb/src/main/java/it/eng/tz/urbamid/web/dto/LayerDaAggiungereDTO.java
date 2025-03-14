package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class LayerDaAggiungereDTO implements Serializable {

	private static final long serialVersionUID = -3389758861108349305L;

	private String nomeLayer;
	private String titoloLayer;
	private String trasparenza;
	private Integer posizione;
	
	public String getNomeLayer() {
		return nomeLayer;
	}
	public void setNomeLayer(String nomeLayer) {
		this.nomeLayer = nomeLayer;
	}
	public String getTitoloLayer() {
		return titoloLayer;
	}
	public void setTitoloLayer(String titoloLayer) {
		this.titoloLayer = titoloLayer;
	}
	public String getTrasparenza() {
		return trasparenza;
	}
	public void setTrasparenza(String trasparenza) {
		this.trasparenza = trasparenza;
	}
	public Integer getPosizione() {
		return posizione;
	}
	public void setPosizione(Integer posizione) {
		this.posizione = posizione;
	}
	
}
