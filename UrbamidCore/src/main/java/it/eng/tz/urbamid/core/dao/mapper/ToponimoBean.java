package it.eng.tz.urbamid.core.dao.mapper;

import java.io.Serializable;

public class ToponimoBean implements Serializable{


	private static final long serialVersionUID = -426941643431805185L;


	private Long id;
	private String codStrada;
	private String denominazione;
	private String sysRef;
	private Long latitudine;
	private Long longitudine;

	public ToponimoBean() {
	}

	public ToponimoBean(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodStrada() {
		return codStrada;
	}

	public void setCodStrada(String codStrada) {
		this.codStrada = codStrada;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getSysRef() {
		return sysRef;
	}

	public void setSysRef(String sysRef) {
		this.sysRef = sysRef;
	}

	public Long getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(Long latitudine) {
		this.latitudine = latitudine;
	}

	public Long getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(Long longitudine) {
		this.longitudine = longitudine;
	}


}
