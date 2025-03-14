package it.eng.tz.urbamid.security.model;

import java.io.Serializable;
import java.util.Date;

public class AbstractBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7396924224134384489L;
	
	protected String utenteCreazione;
	protected Date dataCreazione;
	protected String utenteModifica;
	protected Date dataModifica;
	public String getUtenteCreazione() {
		return utenteCreazione;
	}
	public void setUtenteCreazione(String utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getUtenteModifica() {
		return utenteModifica;
	}
	public void setUtenteModifica(String utenteModifica) {
		this.utenteModifica = utenteModifica;
	}
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	
	
	
}
