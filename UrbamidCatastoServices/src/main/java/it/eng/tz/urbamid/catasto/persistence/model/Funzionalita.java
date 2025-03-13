package it.eng.tz.urbamid.catasto.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Funzionalita.findAll", query = "select f from Funzionalita f") })
@Table(name = "u_funzionalita")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })

public class Funzionalita extends AbstractModel {

	private static final long serialVersionUID = -426941643431805185L;

	@Column(name = "permesso", nullable = false, length = 50)
	private String permesso;
	
	@Column(name = "codice", nullable = false, length = 50)
	private String codice;
	
	@Column(name = "denominazione", nullable = false, length = 100)
	private String denominazione;
	
	
	@Column(name = "link_url", nullable = false, length = 500)
	private String linkUrl;
	
	@Column(name = "img_url", nullable = false, length = 500)
	private String imgUrl;
	
	@Column(name = "mappa", nullable = false, length = 100)
	private String mappa;
	
	@Column(name = "ordine", nullable = false, length = 100)
	private String ordine;

	public Funzionalita() {
	}

	public Funzionalita(String permesso, String codice, String denominazione ) {
		super();
		this.permesso = permesso;
		this.codice = codice;
		this.denominazione = denominazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermesso() {
		return permesso;
	}

	public void setPermesso(String permesso) {
		this.permesso = permesso;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMappa() {
		return mappa;
	}

	public void setMappa(String mappa) {
		this.mappa = mappa;
	}

	
}
