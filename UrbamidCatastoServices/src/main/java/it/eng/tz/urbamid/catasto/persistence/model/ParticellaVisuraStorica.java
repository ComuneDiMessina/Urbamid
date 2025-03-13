package it.eng.tz.urbamid.catasto.persistence.model;

public interface ParticellaVisuraStorica {

	String getCodComune();
	String getSezione();
	String getClasse();
	String getTipoNotaGen();
	String getNumeroNotaGen();
	String getProgressivoNotaGen();
	String getTipoNotaConcl();
	String getNumeroNotaConcl();
	String getProgressivoNotaConcl();
	String getPartita();
	String getFoglio();
	String getNumero();
	String getSubalterno();
	String getEdificabilita();
	String getTipoImmobile();
	String getFlagReddito();
	String getFlagPorzione();
	String getFlagDeduzioni();
	String getAnnotazione();
	String getCodiceCausaleAttoGen();
	String getDescrizioneAttoGen();
	String getCodiceCausaleAttoConcl();
	String getDescrizioneAttoConcl();
	Long getIdImmobile();
	Integer getProgressivo();
	Integer getAnnoNotaGen();
	Integer getAnnoNotaConcl();
	Long getIdMutazIn();
	Long getIdMutazFin();
	Integer getQualita();
	Integer getEttari();
	Integer getAre();
	Double getRedditoDominicaleLire();
	Double getRedditoAgrarioLire();
	Double getRedditoDominicaleEuro();
	Double getRedditoAgrarioEuro();
	Integer getCentiare();
	String getDataEfficaciaGen();
	String getDataRegistrazioneGen();
	String getDataEfficaciaConcl();
	String getDataRegistrazioneConcl();
	Integer getDenominatore();

}
