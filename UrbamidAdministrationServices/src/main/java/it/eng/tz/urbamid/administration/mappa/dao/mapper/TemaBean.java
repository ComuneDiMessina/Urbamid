package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.io.Serializable;
import java.util.Date;

import it.eng.tz.urbamid.administration.mappa.dao.bean.AbstractBean;

public class TemaBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Boolean isNew;
	private Long id;
	private Long idPadre;
	private String nome;
	private String descrizione;
	private Long ordinamento;
	private Date dataCreazione;
	private Date dataModifica;
	private String utenteCreazione;
	private String utenteModifica;
	
	public TemaBean() {
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	public TemaBean(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(String utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

	public String getUtenteModifica() {
		return utenteModifica;
	}

	public void setUtenteModifica(String utenteModifica) {
		this.utenteModifica = utenteModifica;
	}




}