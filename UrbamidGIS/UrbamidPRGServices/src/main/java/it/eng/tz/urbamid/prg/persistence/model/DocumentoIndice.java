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
@Table(name="u_prg_doc_indice")
public class DocumentoIndice implements Serializable {

	private static final long serialVersionUID = 8938870105042650991L;

	@Id 
	@SequenceGenerator(name="u_prg_doc_indice_id_seq", sequenceName="u_prg_doc_indice_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_doc_indice_id_seq")
	@Column(name = "id_indice", unique = true, nullable = false, updatable=false)
	private Long idIndice;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_documento", nullable=false, updatable=false )
	private DocumentoVariante documento;

	@Column(name = "articolo", length=10)
	private Integer articolo;

	@Column(name = "elenco_pagine", length=10)
	private String elencoPagine;

	@OneToMany(
	        mappedBy = "indice", 
	        fetch=FetchType.LAZY
	)
	private List<DocumentoIndiceCodici> indiceCodici;

	public Long getIdIndice() {
		return idIndice;
	}

	public void setIdIndice(Long idIndice) {
		this.idIndice = idIndice;
	}

	public DocumentoVariante getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoVariante documento) {
		this.documento = documento;
	}

	public Integer getArticolo() {
		return articolo;
	}

	public void setArticolo(Integer articolo) {
		this.articolo = articolo;
	}

	public String getElencoPagine() {
		return elencoPagine;
	}

	public void setElencoPagine(String elencoPagine) {
		this.elencoPagine = elencoPagine;
	}

	public List<DocumentoIndiceCodici> getIndiceCodici() {
		return indiceCodici;
	}

	public void setIndiceCodici(List<DocumentoIndiceCodici> indiceCodici) {
		this.indiceCodici = indiceCodici;
	}

}
