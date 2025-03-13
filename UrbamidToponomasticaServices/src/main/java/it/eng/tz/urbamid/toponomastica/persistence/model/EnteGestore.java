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
@Table(name = "u_topo_ente_gestore")
public class EnteGestore implements Serializable{

	private static final long serialVersionUID = -609073803377069265L;

	@Id
	@SequenceGenerator(name="u_topo_ente_gestore_id_seq", sequenceName="u_topo_ente_gestore_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_ente_gestore_id_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "codice", length = 100, nullable = false, unique = true)
	private String codice;
	
	@Column(name = "descrizione", length = 100, nullable = false)
	private String descrizione;
	
	@OneToMany(mappedBy = "enteGestore", fetch = FetchType.LAZY)
	private Set<EstesaAmministrativa> estesaAmministrativa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<EstesaAmministrativa> getEstesaAmministrativa() {
		return estesaAmministrativa;
	}

	public void setEstesaAmministrativa(Set<EstesaAmministrativa> estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
	}
}
