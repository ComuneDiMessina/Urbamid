package it.eng.tz.urbamid.catasto.persistence.model;

import java.util.Date;

public interface UnitaImmobiliareJoinRicerca {

	Long getId();
	Long getIdImmobile();
	String getCodComune();
	String getZona();
	String getCategoria();
	String getClasse();
	String getIndirizzo();
	String getCivico1();
	String getCivico2();
	String getSezione();
	String getFoglio();
	String getNumero();
	String getSubalterno();
	Double getConsistenza();
	Double getSuperficie();
	Double getRenditaLire();
	Double getRenditaEuro();
	String getPartita();
	
	String getLotto();
	String getEdificio();
	String getScala();
	String getInterno();
	String getPiano();
	String getAnnotazione();
	String getCivico3();

	String getDescrizioneAttoGen();
	Date getDataEfficaciaGen();

}
