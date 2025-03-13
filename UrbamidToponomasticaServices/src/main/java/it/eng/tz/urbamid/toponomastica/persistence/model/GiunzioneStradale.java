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
@Table(name = "u_topo_giunzioni_stradali")
public class GiunzioneStradale implements Serializable {

	private static final long serialVersionUID = -5737614954006234490L;

	@Id
	@SequenceGenerator(name="u_topo_giunzioni_stradali_id_seq", sequenceName="u_topo_giunzioni_stradali_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_giunzioni_stradali_id_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "descrizione", length = 100, nullable = true)
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "tipo_topologico", nullable = true)
	private TipoTopologico tipoTopologico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_funzionale", nullable = true)
	private TipoFunzionale tipoFunzionale;
	
	@Column(name = "limite_amministrativo", nullable = true)
	private boolean limiteAmministrativo;
	
	@Column(name = "inizio_fine_strada", nullable = true)
	private boolean inizioFineStrada;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "stato", nullable = true)
	private String stato;
	
	@Column(name = "is_circle", nullable = true)
	private Boolean isCircle;

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

	public TipoTopologico getTipoTopologico() {
		return tipoTopologico;
	}

	public void setTipoTopologico(TipoTopologico tipoTopologico) {
		this.tipoTopologico = tipoTopologico;
	}

	public TipoFunzionale getTipoFunzionale() {
		return tipoFunzionale;
	}

	public void setTipoFunzionale(TipoFunzionale tipoFunzionale) {
		this.tipoFunzionale = tipoFunzionale;
	}

	public boolean isLimiteAmministrativo() {
		return limiteAmministrativo;
	}

	public void setLimiteAmministrativo(boolean limiteAmministrativo) {
		this.limiteAmministrativo = limiteAmministrativo;
	}

	public boolean isInizioFineStrada() {
		return inizioFineStrada;
	}

	public void setInizioFineStrada(boolean inizioFineStrada) {
		this.inizioFineStrada = inizioFineStrada;
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

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Boolean getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(Boolean isCircle) {
		this.isCircle = isCircle;
	}
	
}
