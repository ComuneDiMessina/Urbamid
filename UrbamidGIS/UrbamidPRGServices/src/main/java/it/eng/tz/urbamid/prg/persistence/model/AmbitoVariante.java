package it.eng.tz.urbamid.prg.persistence.model;

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

@Entity
@Table(name="u_prg_ambito_variante")
public class AmbitoVariante implements Serializable {

	private static final long serialVersionUID = 3746282202580185126L;

	@Id 
	@SequenceGenerator(name="u_prg_ambito_variante_id_seq", sequenceName="u_prg_ambito_variante_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_ambito_variante_id_seq")
	@Column(name = "id_ambito", unique = true, nullable = false, updatable=false)
	private Long idAmbito;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_variante", nullable=false, updatable=false )
	private Variante variante;

	@Column(name = "geom")
	private String geom;

	public Long getIdAmbito() {
		return idAmbito;
	}

	public void setIdAmbito(Long idAmbito) {
		this.idAmbito = idAmbito;
	}

	public Variante getVariante() {
		return variante;
	}

	public void setVariante(Variante variante) {
		this.variante = variante;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

}
