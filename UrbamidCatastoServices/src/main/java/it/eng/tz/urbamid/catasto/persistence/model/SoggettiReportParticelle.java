package it.eng.tz.urbamid.catasto.persistence.model;

import java.util.Date;

public interface SoggettiReportParticelle {

	String getTipoSoggetto();
	String getCognome();
	String getNome();
	String getLuogoNascita();
	Date getDataNascita();
	String getCodiceFiscale();
	String getDenominazione();
	String getFoglio();
	String getNumero();
	//String getSubalterno();
	String getDescrizione();

}
