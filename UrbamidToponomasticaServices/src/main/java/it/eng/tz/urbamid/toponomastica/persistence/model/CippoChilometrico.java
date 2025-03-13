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
@Table(name = "u_topo_cippo_chilometrico")
public class CippoChilometrico implements Serializable {

	private static final long serialVersionUID = -5850943859651079304L;

	@Id
	@SequenceGenerator(name="u_topo_cippo_chilometrico_id_seq", sequenceName="u_topo_cippo_chilometrico_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_cippo_chilometrico_id_seq")
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estesa_amministrativa", nullable = true)
	private EstesaAmministrativa estesaAmministrativa;
	
	@Column(name = "codice", nullable = false)
	private String codice;
	
	@Column(name = "misura", nullable = true)
	private Double misura;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;
	
	@Column(name = "stato", nullable = true)
	private String stato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstesaAmministrativa getEstesaAmministrativa() {
		return estesaAmministrativa;
	}

	public void setEstesaAmministrativa(EstesaAmministrativa estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
	}
	
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Double getMisura() {
		return misura;
	}

	public void setMisura(Double misura) {
		this.misura = misura;
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
