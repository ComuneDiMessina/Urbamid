package it.eng.tz.urbamid.prg.web.dto;

import java.util.List;

public class InterrogazionePianoDTO {

	private VarianteDTO variante;
	
	private String protocollo;
	private String dataRichiesta;
	private String nomeDitta;
	
	private String codicePrimoVersamento;
	private String dataPrimoVersamento;
	private String importoPrimoVersamento;
	
	private String codiceSecondoVersamento;
	private String dataSecondoVersamento;
	private String importoSecondoVersamento;

	private List<GeometriaCduDTO> entity;
	
	private boolean indiciVariante;
	
	private String codiceModelloF23;
	private String dataModelloF23;

	private String riferimentoMappaCatastale;
	private String riferimentoVisuraCatastale;
	private String dataVisuraCatastale;
	
	private String dipartimento;
	private String servizio;
	private String emailReferente;
	private String responsabileServizio;
	private String responsabilePianoTerritoriale;
	private String dirigenteDipartimento;

	public VarianteDTO getVariante() {
		return variante;
	}

	public void setVariante(VarianteDTO variante) {
		this.variante = variante;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getNomeDitta() {
		return nomeDitta;
	}

	public void setNomeDitta(String nomeDitta) {
		this.nomeDitta = nomeDitta;
	}

	public String getCodicePrimoVersamento() {
		return codicePrimoVersamento;
	}

	public void setCodicePrimoVersamento(String codicePrimoVersamento) {
		this.codicePrimoVersamento = codicePrimoVersamento;
	}

	public String getDataPrimoVersamento() {
		return dataPrimoVersamento;
	}

	public void setDataPrimoVersamento(String dataPrimoVersamento) {
		this.dataPrimoVersamento = dataPrimoVersamento;
	}

	public String getImportoPrimoVersamento() {
		return importoPrimoVersamento;
	}

	public void setImportoPrimoVersamento(String importoPrimoVersamento) {
		this.importoPrimoVersamento = importoPrimoVersamento;
	}

	public String getCodiceSecondoVersamento() {
		return codiceSecondoVersamento;
	}

	public void setCodiceSecondoVersamento(String codiceSecondoVersamento) {
		this.codiceSecondoVersamento = codiceSecondoVersamento;
	}

	public String getDataSecondoVersamento() {
		return dataSecondoVersamento;
	}

	public void setDataSecondoVersamento(String dataSecondoVersamento) {
		this.dataSecondoVersamento = dataSecondoVersamento;
	}

	public String getImportoSecondoVersamento() {
		return importoSecondoVersamento;
	}

	public void setImportoSecondoVersamento(String importoSecondoVersamento) {
		this.importoSecondoVersamento = importoSecondoVersamento;
	}

	public List<GeometriaCduDTO> getEntity() {
		return entity;
	}

	public void setEntity(List<GeometriaCduDTO> entity) {
		this.entity = entity;
	}

	public boolean isIndiciVariante() {
		return indiciVariante;
	}

	public void setIndiciVariante(boolean indiciVariante) {
		this.indiciVariante = indiciVariante;
	}

	public String getCodiceModelloF23() {
		return codiceModelloF23;
	}

	public void setCodiceModelloF23(String codiceModelloF23) {
		this.codiceModelloF23 = codiceModelloF23;
	}

	public String getDataModelloF23() {
		return dataModelloF23;
	}

	public void setDataModelloF23(String dataModelloF23) {
		this.dataModelloF23 = dataModelloF23;
	}

	public String getRiferimentoMappaCatastale() {
		return riferimentoMappaCatastale;
	}

	public void setRiferimentoMappaCatastale(String riferimentoMappaCatastale) {
		this.riferimentoMappaCatastale = riferimentoMappaCatastale;
	}

	public String getRiferimentoVisuraCatastale() {
		return riferimentoVisuraCatastale;
	}

	public void setRiferimentoVisuraCatastale(String riferimentoVisuraCatastale) {
		this.riferimentoVisuraCatastale = riferimentoVisuraCatastale;
	}

	public String getDataVisuraCatastale() {
		return dataVisuraCatastale;
	}

	public void setDataVisuraCatastale(String dataVisuraCatastale) {
		this.dataVisuraCatastale = dataVisuraCatastale;
	}

	public String getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}

	public String getEmailReferente() {
		return emailReferente;
	}

	public void setEmailReferente(String emailReferente) {
		this.emailReferente = emailReferente;
	}

	public String getResponsabileServizio() {
		return responsabileServizio;
	}

	public void setResponsabileServizio(String responsabileServizio) {
		this.responsabileServizio = responsabileServizio;
	}

	public String getResponsabilePianoTerritoriale() {
		return responsabilePianoTerritoriale;
	}

	public void setResponsabilePianoTerritoriale(String responsabilePianoTerritoriale) {
		this.responsabilePianoTerritoriale = responsabilePianoTerritoriale;
	}

	public String getDirigenteDipartimento() {
		return dirigenteDipartimento;
	}

	public void setDirigenteDipartimento(String dirigenteDipartimento) {
		this.dirigenteDipartimento = dirigenteDipartimento;
	}
}
