package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_codici_qualita")
public class CodiciQualita implements Serializable {

	private static final long serialVersionUID = -4938121053957426884L;

	@Id
	@Column(name = "codice")
	private Long codice;

	@Column(name = "descrizione", length=100)
	private String descrizione;

	public Long getCodice() {
		return codice;
	}

	public void setCodice(Long codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
