package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_categorie_catastali")
public class CategorieCatastali implements Serializable {

	private static final long serialVersionUID = -4938121053957426884L;

	@Id
	@Column(name = "codice", length=10)
	private String codice;

	@Column(name = "descrizione", length=500)
	private String descrizione;

	@Column(name = "informazione", length=500)
	private String informazione;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getInformazione() {
		return informazione;
	}

	public void setInformazione(String informazione) {
		this.informazione = informazione;
	}

}
