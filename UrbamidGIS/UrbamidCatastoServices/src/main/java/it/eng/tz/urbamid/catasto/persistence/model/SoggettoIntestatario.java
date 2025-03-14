package it.eng.tz.urbamid.catasto.persistence.model;

import java.util.Date;

public interface SoggettoIntestatario {

	String getNome();
	String getCognome();
	String getCodice_fiscale();
	String getDenominazione();
	String getTipo_soggetto();
	
	Long getId_soggetto();
	String getSesso();
	Date getData_nascita();
	String getNote();

	String getDiritto();
	Date getDataValiditaGen();

}
