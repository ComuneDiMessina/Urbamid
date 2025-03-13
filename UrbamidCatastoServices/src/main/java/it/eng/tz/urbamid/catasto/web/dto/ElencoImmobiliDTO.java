package it.eng.tz.urbamid.catasto.web.dto;

public class ElencoImmobiliDTO {

	private String data;
	private Integer indiceUiu;
	private String sezione;
	private String zona;
	private String categoria;
	private String classe;
	private Double consistenza;
	private Double superficie;
	private Double renditaEuro;
	private String derivazione;
	private String indirizzo;
	private String note;

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Integer getIndiceUiu() {
		return indiceUiu;
	}
	public void setIndiceUiu(Integer indiceUiu) {
		this.indiceUiu = indiceUiu;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
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
	public Double getConsistenza() {
		return consistenza;
	}
	public void setConsistenza(Double consistenza) {
		this.consistenza = consistenza;
	}
	public Double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	public Double getRenditaEuro() {
		return renditaEuro;
	}
	public void setRenditaEuro(Double renditaEuro) {
		this.renditaEuro = renditaEuro;
	}
	public String getDerivazione() {
		return derivazione;
	}
	public void setDerivazione(String derivazione) {
		this.derivazione = derivazione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
