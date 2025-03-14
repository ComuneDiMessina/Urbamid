package it.eng.tz.urbamid.catasto.persistence.model;

public class IntestazioniExport {

	private Long idptuiu;
	private Long idsoggetto;
	private String tipo;
	private String persona;
	private String diritto;

	public Long getIdptuiu() {
		return idptuiu;
	}

	public void setIdptuiu(Long idptuiu) {
		this.idptuiu = idptuiu;
	}

	public Long getIdsoggetto() {
		return idsoggetto;
	}

	public void setIdsoggetto(Long idsoggetto) {
		this.idsoggetto = idsoggetto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public String getDiritto() {
		return diritto;
	}

	public void setDiritto(String diritto) {
		this.diritto = diritto;
	}

	public IntestazioniExport convert(Intestazioni item) {
		IntestazioniExport ie = new IntestazioniExport();
		ie.setIdptuiu(item.getIdPtUiu());
		ie.setIdsoggetto(item.getIdSoggetto());
		ie.setTipo(item.getTipo());
		ie.setPersona(item.getPersona());
		ie.setDiritto(item.getDiritto());
		return ie;
	}

}
