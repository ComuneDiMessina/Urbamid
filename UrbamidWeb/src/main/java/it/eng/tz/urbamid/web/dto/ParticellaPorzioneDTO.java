package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class ParticellaPorzioneDTO implements Serializable {

	private static final long serialVersionUID = -3676558956707472324L;

	private String classe;
	private Integer ettari;
	private Integer are;
	private Integer centiare;
	private Integer qualita;

	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Integer getEttari() {
		return ettari;
	}
	public void setEttari(Integer ettari) {
		this.ettari = ettari;
	}
	public Integer getAre() {
		return are;
	}
	public void setAre(Integer are) {
		this.are = are;
	}
	public Integer getCentiare() {
		return centiare;
	}
	public void setCentiare(Integer centiare) {
		this.centiare = centiare;
	}
	public Integer getQualita() {
		return qualita;
	}
	public void setQualita(Integer qualita) {
		this.qualita = qualita;
	}

}
