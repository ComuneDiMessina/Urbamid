package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Set;

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
@Table(name = "u_topo_tipo_accesso")
public class TipoAccesso implements Serializable{

	private static final long serialVersionUID = -825692594661913606L;

	@Id
	@SequenceGenerator(name="u_topo_tipo_accesso_id_seq", sequenceName="u_topo_tipo_accesso_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_tipo_accesso_id_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descrizione", length = 100, nullable = true)
	private String descrizione;
	
	@OneToMany(mappedBy = "tipo", fetch = FetchType.LAZY)
	private Set<Accesso> accesso;

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

	public Set<Accesso> getAccesso() {
		return accesso;
	}

	public void setAccesso(Set<Accesso> accesso) {
		this.accesso = accesso;
	}
	
}
