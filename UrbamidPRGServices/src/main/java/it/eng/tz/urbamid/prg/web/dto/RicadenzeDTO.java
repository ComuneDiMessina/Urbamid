package it.eng.tz.urbamid.prg.web.dto;

public class RicadenzeDTO {

	private Integer numero;
	private String foglio;
	private String particelle;
	private String ricadenza;
	private String proprieta;
	
	public RicadenzeDTO(Integer numero, String foglio, String particelle, String ricadenza) {
		super();
		this.numero = numero;
		this.foglio = foglio;
		this.particelle = particelle;
		this.ricadenza = ricadenza;
	}
	
	public RicadenzeDTO(Integer numero, String foglio, String particelle, String ricadenza, String proprieta) {
		super();
		this.numero = numero;
		this.foglio = foglio;
		this.particelle = particelle;
		this.ricadenza = ricadenza;
		this.proprieta = proprieta;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public String getFoglio() {
		return foglio;
	}
	
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	
	public String getParticelle() {
		return particelle;
	}
	
	public void setParticelle(String particelle) {
		this.particelle = particelle;
	}
	
	public String getRicadenza() {
		return ricadenza;
	}
	
	public void setRicadenza(String ricadenza) {
		this.ricadenza = ricadenza;
	}

	public String getProprieta() {
		return proprieta;
	}

	public void setProprieta(String proprieta) {
		this.proprieta = proprieta;
	}
}
