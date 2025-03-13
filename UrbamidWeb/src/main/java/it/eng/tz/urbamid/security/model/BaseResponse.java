package it.eng.tz.urbamid.security.model;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.urbamid.web.util.IConstants;

public class BaseResponse<T> implements Serializable{
	
	private static final long serialVersionUID = -7690786130689073927L;
	private String codiceEsito;
	private String descrizioneEsito;
	private int numOggettiTotali;
	private int numOggettiRestituiti;
	private T payload;
	
	
	
	/**
	 * 
	 * Il metodo permette di impostare la response in caso di esito positivo
	 * 
	 * @return {@link BaseResponse}
	 */
	public BaseResponse setOK(){
		return this.setOK(null);
	}
	
	/**
	 * 
	 * Il metodo permette di impostare la response in caso di esito positivo
	 * 
	 * @param {@link Object} dati da ritornare dalla response
	 * 
	 * @return {@link BaseResponse}
	 */
	public BaseResponse setOK(Object payload){
		this.numOggettiTotali = this.getPayloadSize(payload);
		this.numOggettiRestituiti = this.getPayloadSize(payload);
		this.codiceEsito = IConstants.WSConstant.WS_OK.getCodice();
		this.descrizioneEsito = (payload instanceof IConstants.WSConstant ? ((IConstants.WSConstant)payload).getDescrizione() :IConstants.WSConstant.WS_OK.getDescrizione());
		return this;
	}
	
	
	/**
	 * 
	 * Il metodo permette di impostare la response in caso di esito negativo
	 * 
	 * @param {@link Object} dati da ritornare dalla response 
	 * @param {@link WSRestUtil.RVEAscWSConstant} esitoOperazione

	 * @return {@link BaseResponse}
	 */
	public BaseResponse setKO(Object payload, IConstants.WSConstant esitoOperazione){
		return this.setKO(payload, esitoOperazione.getCodice(), esitoOperazione.getDescrizione());
	}
	
	/**
	 * 
	 * Il metodo permette di impostare la response in caso di esito negativo
	 * 
	 * @param {@link Object} dati da ritornare dalla response 
	 * @param {@link String} codiceErrore, msgErrore

	 * @return {@link BaseResponse}
	 */
	public BaseResponse setKO(Object payload, String codiceErrore, String msgErrore){
		this.numOggettiTotali = this.getPayloadSize(payload);
		this.numOggettiRestituiti = this.getPayloadSize(payload);
		this.codiceEsito = codiceErrore;
		this.descrizioneEsito = msgErrore;
		return this;
	}
	
	
	private Integer getPayloadSize(Object p) {
		Integer size = new Integer(0);
		if(p!=null) {
			size = (p instanceof List ? ((List<?>)p).size() : 1);
		}
		return size;
	}
	
	
	public String getCodiceEsito() {
		return codiceEsito;
	}
	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
		
	}
	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}
	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}
	
	

	public int getNumOggettiTotali() {
		return numOggettiTotali;
	}

	public void setNumOggettiTotali(int numOggettiTotali) {
		this.numOggettiTotali = numOggettiTotali;
	}

	public int getNumOggettiRestituiti() {
		return numOggettiRestituiti;
	}

	public void setNumOggettiRestituiti(int numOggettiRestituiti) {
		this.numOggettiRestituiti = numOggettiRestituiti;
	}

	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}
}
