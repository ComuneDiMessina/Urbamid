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
@Table(name = "u_topo_dug")
public class Dug implements Serializable {

	private static final long serialVersionUID = 771472139831879531L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "dug", length = 100)
	private String dug;
	
	@OneToMany(mappedBy = "classe", fetch = FetchType.LAZY)
	private Set<ToponimoStradale> toponimo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDug() {
		return dug;
	}

	public void setDug(String dug) {
		this.dug = dug;
	}

	public Set<ToponimoStradale> getToponimo() {
		return toponimo;
	}

	public void setToponimo(Set<ToponimoStradale> toponimo) {
		this.toponimo = toponimo;
	}
	
}
