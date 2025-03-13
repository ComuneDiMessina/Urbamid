package it.eng.tz.urbamid.profilemanager.web.response;

import java.io.Serializable;
import java.util.List;

public class BaseResponse<T> implements Serializable{
	
	private static final long serialVersionUID = -7690786130689073927L;
	private String codiceEsito;
	private String descrizioneEsito;
	private int numeroOggettiRestituiti;
	private T payload;
	
	
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
	
	
	public int getNumeroOggettiRestituiti() {
		return numeroOggettiRestituiti;
	}
	public void setNumeroOggettiRestituiti(int numeroOggettiRestituiti) {
		this.numeroOggettiRestituiti = numeroOggettiRestituiti;
	}
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}
	
	public Integer getPayloadSize(Object p) {
		Integer size = new Integer(0);
		if(p!=null) {
			size = (p instanceof List ? ((List<?>)p).size() : 1);
		}
		return size;
	}
	
	
}
