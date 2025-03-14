package it.eng.tz.urbamid.catasto.web.dto;

public class DettaglioSoggettiUIDTO {

	private Integer uiu;
	private Long idImmobile;
	private String categoria;
	private String sezione;
	private String foglio;
	private String numero;
	private String subalterno;
	private String indirizzo;
	private String comune;
	private String provincia;
	private String diritto;
	private String partita;
	private Double renditaLire;
	private Double renditaEuro;
	private String geometry;

	private boolean singoloProprietario;

	public Integer getUiu() {
		return uiu;
	}
	public void setUiu(Integer uiu) {
		this.uiu = uiu;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
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
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDiritto() {
		return diritto;
	}
	public void setDiritto(String diritto) {
		this.diritto = diritto;
	}
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public Double getRenditaLire() {
		return renditaLire;
	}
	public void setRenditaLire(Double renditaLire) {
		this.renditaLire = renditaLire;
	}
	public Double getRenditaEuro() {
		return renditaEuro;
	}
	public void setRenditaEuro(Double renditaEuro) {
		this.renditaEuro = renditaEuro;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	public Long getIdImmobile() {
		return idImmobile;
	}
	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}
	public boolean isSingoloProprietario() {
		return singoloProprietario;
	}
	public void setSingoloProprietario(boolean singoloProprietario) {
		this.singoloProprietario = singoloProprietario;
	}

}
