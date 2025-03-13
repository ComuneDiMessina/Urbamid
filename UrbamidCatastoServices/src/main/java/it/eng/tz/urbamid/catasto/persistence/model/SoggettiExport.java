package it.eng.tz.urbamid.catasto.persistence.model;

import java.text.SimpleDateFormat;

public class SoggettiExport {

	private Long identificativo;
	private String ute;
	private String personafisica;
	private String sesso;
	private String nome;
	private String cognome;
	private String codicefiscale;
	private String datanascita;
	private String luogonascita;
	private String idbelfiorenascita;
	private String annotazioni;

	public Long getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}
	public String getUte() {
		return ute;
	}
	public void setUte(String ute) {
		this.ute = ute;
	}
	public String getPersonafisica() {
		return personafisica;
	}
	public void setPersonafisica(String personafisica) {
		this.personafisica = personafisica;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCodicefiscale() {
		return codicefiscale;
	}
	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}
	public String getDatanascita() {
		return datanascita;
	}
	public void setDatanascita(String datanascita) {
		this.datanascita = datanascita;
	}
	public String getLuogonascita() {
		return luogonascita;
	}
	public void setLuogonascita(String luogonascita) {
		this.luogonascita = luogonascita;
	}
	public String getIdbelfiorenascita() {
		return idbelfiorenascita;
	}
	public void setIdbelfiorenascita(String idbelfiorenascita) {
		this.idbelfiorenascita = idbelfiorenascita;
	}
	public String getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public SoggettiExport convert(Soggetti item) {
		SoggettiExport s = new SoggettiExport();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		s.setIdentificativo(item.getIdentificativo());
		s.setUte(item.getUte());
		s.setPersonafisica(item.getPersonaFisica());
		if (item.getPersonaFisica().equalsIgnoreCase("P")) {
			if(item.getSesso() != null)
				s.setSesso(item.getSesso().equals("1") ? "M" : "F");
			
			s.setNome(item.getNome());
			s.setCognome(item.getCognome());
		} else {
			s.setNome(item.getDenominazione());
		}
		s.setCodicefiscale(item.getCodiceFiscale());
		s.setDatanascita(item.getDataNascita() != null ? sdf.format(item.getDataNascita()) : null);
		s.setLuogonascita(item.getLuogoNascita());
		s.setIdbelfiorenascita(item.getIdbelfioreNascita());
		s.setAnnotazioni(item.getAnnotazioni());
		
		return s;
	}

}
