package it.eng.tz.urbamid.catasto.persistence.model;

public class UnitaImmobiliareUrbanaExport {

	private Long identificativo;
	private String comune;
	private String ute;
	private String foglio;
	private String numero;
	private String subalterno;
	private String stadio;
	private String sezione;
	private String categoria;
	private String classe;
	private String zona;
	private Integer denominatore;
	private String partita;
	private Double superficie;
	private Double renditaeuro;

	public Long getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getUte() {
		return ute;
	}
	public void setUte(String ute) {
		this.ute = ute;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getStadio() {
		return stadio;
	}
	public void setStadio(String stadio) {
		this.stadio = stadio;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public Integer getDenominatore() {
		return denominatore;
	}
	public void setDenominatore(Integer denominatore) {
		this.denominatore = denominatore;
	}
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public Double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	public Double getRenditaeuro() {
		return renditaeuro;
	}
	public void setRenditaeuro(Double renditaeuro) {
		this.renditaeuro = renditaeuro;
	}

	public UnitaImmobiliareUrbanaExport convert(UnitaImmobiliareUrbana item) {
		UnitaImmobiliareUrbanaExport uiu = new UnitaImmobiliareUrbanaExport();
		uiu.setIdentificativo(item.getIdentificativo());
		uiu.setComune(item.getComune());
		uiu.setUte(item.getUte());
		uiu.setFoglio(item.getFoglio());
		uiu.setNumero(item.getNumero());
		uiu.setSubalterno(item.getSubalterno());
		uiu.setStadio(item.getStadio());
		uiu.setSezione(item.getSezione());
		uiu.setCategoria(item.getCategoria());
		uiu.setClasse(item.getClasse());
		uiu.setZona(item.getZona());
		uiu.setDenominatore(item.getDenominatore());
		uiu.setPartita(item.getPartita());
		uiu.setSuperficie(item.getSuperficie());
		uiu.setRenditaeuro(item.getRenditaEuro());
		return uiu;
	}

}
