package it.eng.tz.urbamid.catasto.persistence.model;

public interface UnitaImmobiliareJoin {

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
	String getDiritto();

}
