package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_prg_doc_indice_codici")
public class DocumentoIndiceCodici implements Serializable {

	private static final long serialVersionUID = -2003005641548220430L;

	@Id 
	@SequenceGenerator(name="u_prg_doc_indice_codici_id_seq", sequenceName="u_prg_doc_indice_codici_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_doc_indice_codici_id_seq")
	@Column(name = "id_codice", unique = true, nullable = false, updatable=false)
	private Long idCodice;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_indice", nullable=false, updatable=false )
	private DocumentoIndice indice;

	@Column(name = "codice", length=500)
	private String codice;

	@Column(name = "descrizione", length=500)
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getIdCodice() {
		return idCodice;
	}

	public void setIdCodice(Long idCodice) {
		this.idCodice = idCodice;
	}

	public DocumentoIndice getIndice() {
		return indice;
	}

	public void setIndice(DocumentoIndice indice) {
		this.indice = indice;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

}
