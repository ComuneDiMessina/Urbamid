package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Set;

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

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "u_topo_accesso")
public class Accesso implements Serializable {

	private static final long serialVersionUID = -7734839658161359049L;

	@Id
	@SequenceGenerator(name="u_topo_accesso_id_seq", sequenceName="u_topo_accesso_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_accesso_id_seq")
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toponimo_stradale", nullable = true)
	private ToponimoStradale toponimo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "localita", nullable = true)
	private Localita localita;
	
	@Column(name = "numero", length = 100, nullable = true )
	private int numero;
	
	@Column(name = "esponente", length = 7, nullable = true)
	private String esponente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo", nullable = true)
	private TipoAccesso tipo;
	
	@Column(name = "passo_carrabile", nullable = true)
	private boolean passoCarrabile;
	
	@Column(name = "principale", nullable = true)
	private boolean principale;
	
	@Column(name = "metodo", length = 100, nullable = true)
	private String metodo;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;
	
	@Column(name = "stato", nullable = true)
	private String stato;
	
	@OneToMany(mappedBy = "accesso", fetch = FetchType.LAZY)
	private Set<GeocodingReverseGeocoding> geocoding;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ToponimoStradale getToponimo() {
		return toponimo;
	}

	public void setToponimo(ToponimoStradale toponimo) {
		this.toponimo = toponimo;
	}

	public Localita getLocalita() {
		return localita;
	}

	public void setLocalita(Localita localita) {
		this.localita = localita;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEsponente() {
		return esponente;
	}

	public void setEsponente(String esponente) {
		this.esponente = esponente;
	}

	public TipoAccesso getTipo() {
		return tipo;
	}

	public void setTipo(TipoAccesso tipo) {
		this.tipo = tipo;
	}

	public boolean isPassoCarrabile() {
		return passoCarrabile;
	}

	public void setPassoCarrabile(boolean passoCarrabile) {
		this.passoCarrabile = passoCarrabile;
	}

	public boolean isPrincipale() {
		return principale;
	}

	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
