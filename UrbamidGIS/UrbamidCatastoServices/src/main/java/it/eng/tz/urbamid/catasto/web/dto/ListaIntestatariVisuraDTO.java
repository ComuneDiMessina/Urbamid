package it.eng.tz.urbamid.catasto.web.dto;

public class ListaIntestatariVisuraDTO {

	private Integer indiceIntestatario;
	private String datiAnagrafici;
	private String codiceFiscale;
	private String diritto;

	public String getDatiAnagrafici() {
		return datiAnagrafici;
	}
	public void setDatiAnagrafici(String datiAnagrafici) {
		this.datiAnagrafici = datiAnagrafici;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDiritto() {
		return diritto;
	}
	public void setDiritto(String diritto) {
		this.diritto = diritto;
	}
	public Integer getIndiceIntestatario() {
		return indiceIntestatario;
	}
	public void setIndiceIntestatario(Integer indiceIntestatario) {
		this.indiceIntestatario = indiceIntestatario;
	}

}
