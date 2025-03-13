package it.eng.tz.urbamid.toponomastica.persistence.model;

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

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "u_topo_percorso")
public class Percorso implements Serializable {

	private static final long serialVersionUID = 2902760934985138917L;

	@Id
	@SequenceGenerator(name="u_topo_percorso_id_seq", sequenceName="u_topo_percorso_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_percorso_id_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "sigla", length = 100, nullable = false)
	private String sigla;
	
	@Column(name = "descrizione", length = 150, nullable = true)
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toponimo_stradale", nullable = true)
	private ToponimoStradale toponimo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estesa_amministrativa", nullable = true)
	private EstesaAmministrativa estesaAmministrativa;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "stato", nullable = true)
	private String stato;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public ToponimoStradale getToponimo() {
		return toponimo;
	}

	public void setToponimo(ToponimoStradale toponimo) {
		this.toponimo = toponimo;
	}

	public EstesaAmministrativa getEstesaAmministrativa() {
		return estesaAmministrativa;
	}

	public void setEstesaAmministrativa(EstesaAmministrativa estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
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
