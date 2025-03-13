package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_prg_doc_variante")
public class DocumentoVariante implements Serializable {

	private static final long serialVersionUID = -4697490641180010885L;

	@Id 
	@SequenceGenerator(name="u_prg_doc_variante_id_seq", sequenceName="u_prg_doc_variante_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_doc_variante_id_seq")
	@Column(name = "id_documento", unique = true, nullable = false, updatable=false)
	private Long idDocumento;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_variante", nullable=false, updatable=false )
	private Variante variante;

	@Column(name = "tipo_documento", nullable = false, length=5)
	private String tipoDocumento;

	@Column(name = "nome_documento", nullable = false, length=100)
	private String nomeDocumento;

	@Column(name = "stato_documento", nullable = false, length=1)
	private String statoDocumento;

	@Column(name = "path_documento", nullable = false, length=500)
	private String pathDocumento;

	@Column(name = "estensione", nullable = false, length=50)
	private String estensione;

	@OneToMany(
	        mappedBy = "documento", 
	        fetch=FetchType.LAZY
	)
	private List<DocumentoIndice> documentoIndice;

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Variante getVariante() {
		return variante;
	}

	public void setVariante(Variante variante) {
		this.variante = variante;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public String getPathDocumento() {
		return pathDocumento;
	}

	public void setPathDocumento(String pathDocumento) {
		this.pathDocumento = pathDocumento;
	}

	public String getEstensione() {
		return estensione;
	}

	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

	public List<DocumentoIndice> getDocumentoIndice() {
		return documentoIndice;
	}

	public void setDocumentoIndice(List<DocumentoIndice> documentoIndice) {
		this.documentoIndice = documentoIndice;
	}

}
