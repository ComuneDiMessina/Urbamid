package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "u_topo_tipo_toponimo")
public class TipoToponimo implements Serializable {

	private static final long serialVersionUID = 6202069034136431157L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descrizione", length = 100, nullable = false)
	private String descrizione;
	
	@OneToMany(mappedBy = "tipo", fetch = FetchType.LAZY)
	private Set<ToponimoStradale> toponimo;

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

	public Set<ToponimoStradale> getToponimo() {
		return toponimo;
	}

	public void setToponimo(Set<ToponimoStradale> toponimo) {
		this.toponimo = toponimo;
	}
	
}
