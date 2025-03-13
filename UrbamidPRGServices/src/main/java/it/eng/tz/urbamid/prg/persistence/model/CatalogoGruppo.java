package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_prg_catalogo_gruppo")
public class CatalogoGruppo implements Serializable {

	private static final long serialVersionUID = 9182933110509780803L;

	@Id 
	@SequenceGenerator(name="u_prg_catalogo_gruppo_id_seq", sequenceName="u_prg_catalogo_gruppo_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_catalogo_gruppo_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "nome_gruppo", length=1000, unique = true, nullable = false)
	private String nomeGruppo;

	@OneToMany(
	        mappedBy = "catalogoGruppo", 
	        fetch=FetchType.LAZY
	)
	private List<CatalogoLayer> catalogoLayer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeGruppo() {
		return nomeGruppo;
	}

	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}

	public List<CatalogoLayer> getCatalogoLayer() {
		return catalogoLayer;
	}

	public void setCatalogoLayer(List<CatalogoLayer> catalogoLayer) {
		this.catalogoLayer = catalogoLayer;
	}

}
