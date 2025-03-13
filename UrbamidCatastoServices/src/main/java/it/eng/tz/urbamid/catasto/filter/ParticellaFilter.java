package it.eng.tz.urbamid.catasto.filter;

import java.io.Serializable;

public class ParticellaFilter implements Serializable {

	private static final long serialVersionUID = -4749694807259236058L;

	private String comune;
	private String sezione;
	private String foglio;
	private String numero;
	private String subalterno;
	private String partita;
	private String redditoDominicaleEuro;
	private String redditoDominicaleLire;
	private String redditoAgrarioEuro;
	private String redditoAgrarioLire;
	private String superficie;
	private boolean soppresso;

	private Integer pageIndex;
	private Integer pageSize;

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
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
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
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public String getRedditoDominicaleEuro() {
		return redditoDominicaleEuro;
	}
	public void setRedditoDominicaleEuro(String redditoDominicaleEuro) {
		this.redditoDominicaleEuro = redditoDominicaleEuro;
	}
	public String getRedditoDominicaleLire() {
		return redditoDominicaleLire;
	}
	public void setRedditoDominicaleLire(String redditoDominicaleLire) {
		this.redditoDominicaleLire = redditoDominicaleLire;
	}
	public String getRedditoAgrarioEuro() {
		return redditoAgrarioEuro;
	}
	public void setRedditoAgrarioEuro(String redditoAgrarioEuro) {
		this.redditoAgrarioEuro = redditoAgrarioEuro;
	}
	public String getRedditoAgrarioLire() {
		return redditoAgrarioLire;
	}
	public void setRedditoAgrarioLire(String redditoAgrarioLire) {
		this.redditoAgrarioLire = redditoAgrarioLire;
	}
	public String getSuperficie() {
		return superficie;
	}
	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}
	public boolean isSoppresso() {
		return soppresso;
	}
	public void setSoppresso(boolean soppresso) {
		this.soppresso = soppresso;
	}

}
