package it.eng.tz.urbamid.prg.web.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CertificatoUrbanisticoInput {

	private String protocollo;
	private String dataIstanza;
	private String ditta;
	
	private String importoVersamento1;
	private String dataVersamento1;
	private String codiceVersamento1;
	
	private String nomeVariante;
	private String dataVariante;
	private String numeroVariante;
	
	private String importoVersamento2;
	private String dataVersamento2;
	private String codiceVersamento2;
	
	private JRBeanCollectionDataSource listaRicadenze;
	
	private String listaStabili;
	private String listaNonStabili;
	private String pianoPaesaggistico;
	
	private JRBeanCollectionDataSource listaIntersezioniAggiuntive;
	
	private String listaInApf;
	private String listaInApfAltriAnni;
	private String listaFuoriApfParticelle;
	private String listaFuoriApfAnni;

	private boolean indiciVariante;
	private JRBeanCollectionDataSource listaDocumenti;

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
	
	private String pathImage;
	
	private JRBeanCollectionDataSource listaVincoliPiano;

	public Map<String, Object> getDataSources() {
		
       Map<String,Object> dataSources = new HashMap<>();
       dataSources.put("protocollo", protocollo);
       dataSources.put("dataIstanza", dataIstanza);
       dataSources.put("ditta", ditta);
       
   	   dataSources.put("nomeVariante", nomeVariante);
   	   dataSources.put("dataVariante", dataVariante);
   	   dataSources.put("numeroVariante", numeroVariante);
    
       dataSources.put("importoVersamento1", importoVersamento1);
       dataSources.put("dataVersamento1", dataVersamento1);
       dataSources.put("codiceVersamento1", codiceVersamento1);
       
       dataSources.put("importoVersamento2", importoVersamento2);
       dataSources.put("dataVersamento2", dataVersamento2);
       dataSources.put("codiceVersamento2", codiceVersamento2);
       
       dataSources.put("codiceModelloF23", codiceModelloF23);
       dataSources.put("dataModelloF23", dataModelloF23);
       
       dataSources.put("listaRicadenze",listaRicadenze);
       
       dataSources.put("riferimentoMappaCatastale", riferimentoMappaCatastale);
       dataSources.put("riferimentoVisuraCatastale", riferimentoVisuraCatastale);
       dataSources.put("dataVisuraCatastale", dataVisuraCatastale);
       
       dataSources.put("listaStabili", listaStabili);
       dataSources.put("listaNonStabili", listaNonStabili);
       dataSources.put("pianoPaesaggistico", pianoPaesaggistico);
       
       dataSources.put("listaIntersezioniAggiuntive",listaIntersezioniAggiuntive);

       dataSources.put("listaInApf",listaInApf);
       dataSources.put("listaInApfAltriAnni",listaInApfAltriAnni);
       dataSources.put("listaFuoriApfParticelle",listaFuoriApfParticelle);
       dataSources.put("listaFuoriApfAnni",listaFuoriApfAnni);

       dataSources.put("indiciVariante",indiciVariante);
       dataSources.put("listaDocumenti",listaDocumenti);
       
       dataSources.put("listaVincoliPiano",listaVincoliPiano);
       
	   dataSources.put("dipartimento",dipartimento);
	   dataSources.put("servizio",servizio);
	   dataSources.put("emailReferente",emailReferente);
	   dataSources.put("responsabileServizio",responsabileServizio);
	   dataSources.put("responsabilePianoTerritoriale",responsabilePianoTerritoriale);
	   dataSources.put("dirigenteDipartimento",dirigenteDipartimento);
	   
	   dataSources.put("pathImage",pathImage);
       return dataSources;
	}

	public JRBeanCollectionDataSource getListaRicadenze() {
		return listaRicadenze;
	}

	public void setListaRicadenze(JRBeanCollectionDataSource listaRicadenze) {
		this.listaRicadenze = listaRicadenze;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getDataIstanza() {
		return dataIstanza;
	}

	public void setDataIstanza(String dataIstanza) {
		this.dataIstanza = dataIstanza;
	}

	public String getDitta() {
		return ditta;
	}

	public void setDitta(String ditta) {
		this.ditta = ditta;
	}

	public String getNomeVariante() {
		return nomeVariante;
	}

	public void setNomeVariante(String nomeVariante) {
		this.nomeVariante = nomeVariante;
	}

	public String getDataVariante() {
		return dataVariante;
	}

	public void setDataVariante(String dataVariante) {
		this.dataVariante = dataVariante;
	}

	public String getNumeroVariante() {
		return numeroVariante;
	}

	public void setNumeroVariante(String numeroVariante) {
		this.numeroVariante = numeroVariante;
	}

	public String getImportoVersamento1() {
		return importoVersamento1;
	}

	public void setImportoVersamento1(String importoVersamento1) {
		this.importoVersamento1 = importoVersamento1;
	}

	public String getDataVersamento1() {
		return dataVersamento1;
	}

	public void setDataVersamento1(String dataVersamento1) {
		this.dataVersamento1 = dataVersamento1;
	}

	public String getCodiceVersamento1() {
		return codiceVersamento1;
	}

	public void setCodiceVersamento1(String codiceVersamento1) {
		this.codiceVersamento1 = codiceVersamento1;
	}

	public String getImportoVersamento2() {
		return importoVersamento2;
	}

	public void setImportoVersamento2(String importoVersamento2) {
		this.importoVersamento2 = importoVersamento2;
	}

	public String getDataVersamento2() {
		return dataVersamento2;
	}

	public void setDataVersamento2(String dataVersamento2) {
		this.dataVersamento2 = dataVersamento2;
	}

	public String getCodiceVersamento2() {
		return codiceVersamento2;
	}

	public void setCodiceVersamento2(String codiceVersamento2) {
		this.codiceVersamento2 = codiceVersamento2;
	}

	public String getListaStabili() {
		return listaStabili;
	}

	public void setListaStabili(String listaStabili) {
		this.listaStabili = listaStabili;
	}

	public String getListaNonStabili() {
		return listaNonStabili;
	}

	public void setListaNonStabili(String listaNonStabili) {
		this.listaNonStabili = listaNonStabili;
	}


	public String getPianoPaesaggistico() {
		return pianoPaesaggistico;
	}

	public void setPianoPaesaggistico(String pianoPaesaggistico) {
		this.pianoPaesaggistico = pianoPaesaggistico;
	}

	public JRBeanCollectionDataSource getListaIntersezioniAggiuntive() {
		return listaIntersezioniAggiuntive;
	}

	public void setListaIntersezioniAggiuntive(JRBeanCollectionDataSource listaIntersezioniAggiuntive) {
		this.listaIntersezioniAggiuntive = listaIntersezioniAggiuntive;
	}

	public String getListaInApf() {
		return listaInApf;
	}

	public void setListaInApf(String listaInApf) {
		this.listaInApf = listaInApf;
	}

	public String getListaInApfAltriAnni() {
		return listaInApfAltriAnni;
	}

	public void setListaInApfAltriAnni(String listaInApfAltriAnni) {
		this.listaInApfAltriAnni = listaInApfAltriAnni;
	}

	public String getListaFuoriApfParticelle() {
		return listaFuoriApfParticelle;
	}

	public void setListaFuoriApfParticelle(String listaFuoriApfParticelle) {
		this.listaFuoriApfParticelle = listaFuoriApfParticelle;
	}

	public String getListaFuoriApfAnni() {
		return listaFuoriApfAnni;
	}

	public void setListaFuoriApfAnni(String listaFuoriApfAnni) {
		this.listaFuoriApfAnni = listaFuoriApfAnni;
	}

	public boolean isIndiciVariante() {
		return indiciVariante;
	}

	public void setIndiciVariante(boolean indiciVariante) {
		this.indiciVariante = indiciVariante;
	}

	public JRBeanCollectionDataSource getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(JRBeanCollectionDataSource listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
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

	public JRBeanCollectionDataSource getListaVincoliPiano() {
		return listaVincoliPiano;
	}

	public void setListaVincoliPiano(JRBeanCollectionDataSource listaVincoliPiano) {
		this.listaVincoliPiano = listaVincoliPiano;
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

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

}
