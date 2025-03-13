package it.eng.tz.urbamid.toponomastica.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "geo_numcivico")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class NumCivico extends AbstractModel {
	
	private static final long serialVersionUID = -8548887952999193571L;
	
	
	@Column(name = "codstrada")
	private String codStrada;
	
	@Column(name = "numero")
	private Integer numero;
	
	@Column(name = "esponente")
	private String esponente;
	
	@Column(name = "sysref")
	private String sysRef;
	
	@Column(name = "latitudine")
	private Double latitudine;
	
	@Column(name = "longitudine")
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
