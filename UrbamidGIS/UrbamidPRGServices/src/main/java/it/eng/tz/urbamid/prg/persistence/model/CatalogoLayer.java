
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
@Table(name="u_prg_catalogo_layer")
public class CatalogoLayer implements Serializable {

	private static final long serialVersionUID = -3984789396150903992L;

	@Id 
	@SequenceGenerator(name="u_prg_catalogo_layer_id_seq", sequenceName="u_prg_catalogo_layer_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_catalogo_layer_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_gruppo", nullable=false, updatable=false )
	private CatalogoGruppo catalogoGruppo;

	@Column(name = "id_layer", length=1000, unique = true, nullable = false)
	private String idLayer;

	@Column(name = "nome_layer", length=1000, unique = true, nullable = false)
	private String nomeLayer;
	
	@Column(name = "sorgente", length = 100, unique = false, nullable = false)
	private String sorgente;
	
	@Column(name = "hrefdetail", length = 350, unique = false, nullable = false)
	private String hrefDetail;

	@Column(name = "nome_colonna", length = 100, unique = false, nullable = false)
	private String nomeColonna;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CatalogoGruppo getCatalogoGruppo() {
		return catalogoGruppo;
	}

	public void setCatalogoGruppo(CatalogoGruppo catalogoGruppo) {
		this.catalogoGruppo = catalogoGruppo;
	}

	public String getIdLayer() {
		return idLayer;
	}

	public void setIdLayer(String idLayer) {
		this.idLayer = idLayer;
	}

	public String getNomeLayer() {
		return nomeLayer;
	}

	public void setNomeLayer(String nomeLayer) {
		this.nomeLayer = nomeLayer;
	}

	public String getSorgente() {
		return sorgente;
	}

	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}

	public String getHrefDetail() {
		return hrefDetail;
	}

	public void setHrefDetail(String hrefDetail) {
		this.hrefDetail = hrefDetail;
	}

	public String getNomeColonna() {
		return nomeColonna;
	}

	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}

}
