package it.eng.tz.urbamid.security.model;

import java.io.Serializable;
import java.util.Date;

public class UMappa implements Serializable{

	private static final long serialVersionUID = 1L;
	private Boolean isNew;
	//COLUMN id
    private Long id;
    //COLUMN codice
    private String codice;
    //COLUMN nome
    private String nome;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN stato
    private String stato;
    //COLUMN mappa_predefinita
    private boolean mappaPredefinita;
    //COLUMN data_creazione
    private Date dataCreazione;
    //COLUMN data_modifica
    private Date dataModifica;
    //COLUMN utente_creazione
    private String utenteCreazione;
    //COLUMN utente_modifica
    private String utenteModifica;

    private Object[] idsTemi;
    
    private Object[] search;
    private Object layer;
    private Object tools; 
    
    //COLUMN show_catalog
    private Boolean showCatalog;
    //COLUMN zoom
    private int zoom;
    
    public Boolean getIsNew() {
		return isNew;
	}
    
    public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
    
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public boolean getMappaPredefinita(){
        return this.mappaPredefinita;
    }

    public void setMappaPredefinita(boolean mappaPredefinita){
        this.mappaPredefinita = mappaPredefinita;
    }

    public Date getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public Date getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(Date dataModifica){
        this.dataModifica = dataModifica;
    }

    public String getUtenteCreazione(){
        return this.utenteCreazione;
    }

    public void setUtenteCreazione(String utenteCreazione){
        this.utenteCreazione = utenteCreazione;
    }

    public String getUtenteModifica(){
        return this.utenteModifica;
    }

    public void setUtenteModifica(String utenteModifica){
        this.utenteModifica = utenteModifica;
    }

    public Object[] getIdsTemi() {
		return idsTemi;
	}
    
    public void setIdsTemi(Object[] idsTemi) {
		this.idsTemi = idsTemi;
	}

	public Object[] getSearch() {
		return search;
	}

	public void setSearch(Object[] search) {
		this.search = search;
	}

	public Object getLayer() {
		return layer;
	}

	public void setLayer(Object layer) {
		this.layer = layer;
	}

	public Object getTools() {
		return tools;
	}

	public void setTools(Object tools) {
		this.tools = tools;
	}
    
	public Boolean getShowCatalog() {
		return showCatalog;
	}
	
	public void setShowCatalog(Boolean showCatalog) {
		this.showCatalog = showCatalog;
	}
	
	public int getZoom() {
		return zoom;
	}
	
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}