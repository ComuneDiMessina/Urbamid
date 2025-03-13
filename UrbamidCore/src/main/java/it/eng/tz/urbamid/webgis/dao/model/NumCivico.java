package it.eng.tz.urbamid.webgis.dao.model;

public class NumCivico implements java.io.Serializable {

	private static final long serialVersionUID = 3467989111284292050L;

	private Long id;
	private String codStrada;
	private Integer numero;
	private String esponente;
	private String sysRef;
	private Double latitudine;
	private Double longitudine;

	public NumCivico() {
	}

	public NumCivico(Long id) {
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getEsponente() {
		return esponente;
	}

	public void setEsponente(String esponente) {
		this.esponente = esponente;
	}

	public String getSysRef() {
		return sysRef;
	}

	public void setSysRef(String sysRef) {
		this.sysRef = sysRef;
	}

	public Double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(Double latitudine) {
		this.latitudine = latitudine;
	}

	public Double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(Double longitudine) {
		this.longitudine = longitudine;
	}



	
}
