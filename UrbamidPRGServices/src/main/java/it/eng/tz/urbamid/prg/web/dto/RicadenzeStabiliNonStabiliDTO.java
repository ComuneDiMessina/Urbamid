package it.eng.tz.urbamid.prg.web.dto;

import java.util.List;

public class RicadenzeStabiliNonStabiliDTO {

	private Integer numero;
	private String foglio;
	private String particelle;
	private List<String> stabili;
	private List<String> nonStabili;
	
	public RicadenzeStabiliNonStabiliDTO(Integer numero, String foglio, String particelle, List<String> stabili, List<String> nonStabili) {
		super();
		this.numero = numero;
		this.foglio = foglio;
		this.stabili = stabili;
		this.nonStabili = nonStabili;
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

	public List<String> getStabili() {
		return stabili;
	}

	public void setStabili(List<String> stabili) {
		this.stabili = stabili;
	}

	public List<String> getNonStabili() {
		return nonStabili;
	}

	public void setNonStabili(List<String> nonStabili) {
		this.nonStabili = nonStabili;
	}
	
}
