package it.eng.tz.urbamid.catasto.persistence.model;

public interface Particellaterreni {

	Long getIdentificativo();
	String getComune();
	String getUte();
	String getFoglio();
	String getNumero();
	String getSubalterno();
	String getStadio();
	String getDenominatore();
	String getPartita();
	String getQualita();
	String getClasse();
	String getSezione();
	Integer getEttari();
	Integer getAre();
	Integer getCentiare();
	Double getSuperficie();
	Double getSuperficieGeometrica();
	Double getSuperficieEsproprio();
	Double getRedditoDominicaleEuro();
	Double getRedditoAgrarioEuro();

}
