package it.eng.tz.urbamid.catasto.filter;

import java.io.Serializable;

public class ImmobileFilter implements Serializable {

	private static final long serialVersionUID = 5427512710401634435L;

	private String comune;
	private String indirizzo;
	private String zona;
	private String sezioneUrbana;
	private String consistenza;
	private String categoria;
	private String foglio;
	private String superficie;
	private String classe;
	private String numero;
	private String renditaLire;
	private String partita;
	private String subalterno;
	private String renditaEuro;
	private Integer pageIndex;
	private Integer pageSize;
	private boolean soppresso;

	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getSezioneUrbana() {
		return sezioneUrbana;
	}
	public void setSezioneUrbana(String sezioneUrbana) {
		this.sezioneUrbana = sezioneUrbana;
	}
	public String getConsistenza() {
		return consistenza;
	}
	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getSuperficie() {
		return superficie;
	}
	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getRenditaLire() {
		return renditaLire;
	}
	public void setRenditaLire(String renditaLire) {
		this.renditaLire = renditaLire;
	}
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getRenditaEuro() {
		return renditaEuro;
	}
	public void setRenditaEuro(String renditaEuro) {
		this.renditaEuro = renditaEuro;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isSoppresso() {
		return soppresso;
	}
	public void setSoppresso(boolean soppresso) {
		this.soppresso = soppresso;
	}

	

}
