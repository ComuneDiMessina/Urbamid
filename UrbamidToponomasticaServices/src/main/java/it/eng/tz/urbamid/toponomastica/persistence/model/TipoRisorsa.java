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
@Table(name = "u_topo_tipo_risorsa")
public class TipoRisorsa implements Serializable {

	private static final long serialVersionUID = 3561656745359474603L;

	@Id
	@Column(name = "id", updatable = false)
	private Long id;
	
	@Column(name = "codice", nullable = true, length = 50)
	private String codice;
	
	@Column(name = "descrizione", nullable = false, length = 100)
	private String descrizione;
	
	@OneToMany(mappedBy = "tipo", fetch = FetchType.LAZY)
	private Set<Documento> path;

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
	
}
