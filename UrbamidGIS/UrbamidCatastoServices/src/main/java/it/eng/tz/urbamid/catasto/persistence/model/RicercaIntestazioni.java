package it.eng.tz.urbamid.catasto.persistence.model;

public interface RicercaIntestazioni {

	String getCodiceFiscale();
	String getCognome();
	String getNome();
	Long getIdSoggetto();
	String getTipo();
	String getSezione();
	Long getFoglio();
	String getNumero();
	String getSubalterno();
	String getCodComune();
	Long getIdImmobile();

	String getDenominazione();

}
