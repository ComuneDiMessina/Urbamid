package it.eng.tz.urbamid.web.dto;

public class DettaglioSoggettiPTDTO {

	private Long id;
	private Long idImmobile;
	private String sezione;
	private String foglio;
	private String numero;
	private String subalterno;
	private String comune;
	private String provincia;
	private String diritto;
	private String partita;
	private String uteSoggetto;
	private String geometry;

	private boolean singoloProprietario;

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
	public String getUteSoggetto() {
		return uteSoggetto;
	}
	public void setUteSoggetto(String uteSoggetto) {
		this.uteSoggetto = uteSoggetto;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
