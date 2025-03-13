package it.eng.tz.urbamid.toponomastica.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "geo_strade")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "gid")) })
public class Toponimo extends AbstractModel {

	private static final long serialVersionUID = 3467989111284292050L;

	@Column(name = "objectid", nullable = false, length = 200)
	private Integer codStrada;
	
	@Column(name = "toponimo", nullable = true, length = 200)
	private String denominazione;
	
	@Column(name = "sysref")
	private String sysref;
	
	@Column(name = "latitudine")
	private Double latitudine;
	
	@Column(name = "longitudine")
	private Double longitudine;

	public Toponimo() {
	}

	public Toponimo(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodStrada() {
		return codStrada;
	}

	public void setCodStrada(Integer codStrada) {
		this.codStrada = codStrada;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getSysref() {
		return sysref;
	}

	public void setSysref(String sysref) {
		this.sysref = sysref;
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

