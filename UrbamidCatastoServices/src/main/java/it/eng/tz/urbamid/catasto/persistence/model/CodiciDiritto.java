package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_codici_diritto")
public class CodiciDiritto implements Serializable {

	private static final long serialVersionUID = -4938121053957426884L;

	@Id
	@Column(name = "codice", length=5)
	private String codice;

	@Column(name = "descrizione", length=100)
	private String descrizione;

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

}
