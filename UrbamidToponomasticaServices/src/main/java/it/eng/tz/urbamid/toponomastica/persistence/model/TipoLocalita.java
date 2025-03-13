package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "u_topo_tipo_localita")
public class TipoLocalita implements Serializable {

	private static final long serialVersionUID = 1990699248023358472L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descrizione", length = 100, nullable = false)
	private String descrizione;
	
	@OneToMany(mappedBy = "tipo", fetch=FetchType.LAZY)
	private List<Localita> localita;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<Localita> getLocalita() {
		return localita;
	}

	public void setLocalita(List<Localita> localita) {
		this.localita = localita;
	}
	
}
