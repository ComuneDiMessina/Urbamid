package it.eng.tz.urbamid.catasto.persistence.model;

import java.util.Date;

public interface Soggetti {

	Long getIdentificativo();
	String getUte();
	String getPersonaFisica();
	String getSesso();
	String getNome();
	String getCognome();
	String getCodiceFiscale();
	Date getDataNascita();
	String getLuogoNascita();
	String getIdbelfioreNascita();
	String getAnnotazioni();
	String getDenominazione();

}
