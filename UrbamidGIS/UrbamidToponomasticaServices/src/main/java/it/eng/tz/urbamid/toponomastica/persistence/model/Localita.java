package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "u_topo_localita")
public class Localita implements Serializable {

	private static final long serialVersionUID = 507288291329470839L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descrizione", length = 100, nullable = false)
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name="comune")
	private Comuni comune;
	
	@Column(name = "frazione", length = 150, nullable = true)
	private String frazione;
	
	@ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name="tipo")
	private TipoLocalita tipo;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;

	@Column(name = "stato", nullable = true)
	private String stato;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@OneToMany(mappedBy = "localita", fetch = FetchType.LAZY)
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

	public Comuni getComune() {
		return comune;
	}

	public void setComune(Comuni comune) {
		this.comune = comune;
	}

	public String getFrazione() {
		return frazione;
	}

	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}

	public TipoLocalita getTipo() {
		return tipo;
	}

	public void setTipo(TipoLocalita tipo) {
		this.tipo = tipo;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<Accesso> getAccesso() {
		return accesso;
	}

	public void setAccesso(Set<Accesso> accesso) {
		this.accesso = accesso;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
