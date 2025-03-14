package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "u_topo_toponimo_geocoding")
public class GeocodingReverseGeocoding implements Serializable {

	private static final long serialVersionUID = 9211383804840713079L;

	@Id
	@SequenceGenerator(name = "u_topo_toponimo_geocoding_gid_seq", sequenceName = "u_topo_toponimo_geocoding_gid_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_topo_toponimo_geocoding_gid_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "comune_label", length = 10, nullable = true)
	private String comuneLabel;
	
	@Column(name = "denominazione_ufficiale", length = 100, nullable = false)
	private String denominazioneUfficiale;
	
	@Column(name = "shape_leng", length = 50, nullable = false)
	private String shapeLeng;
	
	@Column(name = "cap", length = 10, nullable = true)
	private String cap;
	
	@Column(name = "compendi", length = 254, nullable = true)
	private String compendi;
	
	@Column(name = "precdenomi", length = 50, nullable = true)
	private String precdenomi;
	
	@Column(name = "quartiere", length = 12, nullable = true)
	private String quartiere;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_comune", nullable = false)
	private Comuni comune;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "codice", length = 100, nullable = true)
	private String codice;
	
	@Column(name = "id_padre", nullable = true)
	private Long idPadre;
	
	@Column(name = "stato", nullable = true)
	private String stato;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "data_fine_validita", nullable = true)
	private Date dataFineValidita;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_toponimo", nullable = true)
	private ToponimoStradale toponimo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_accesso", nullable = true)
	private Accesso accesso;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComuneLabel() {
		return comuneLabel;
	}
	public void setComuneLabel(String comuneLabel) {
		this.comuneLabel = comuneLabel;
	}
	public String getDenominazioneUfficiale() {
		return denominazioneUfficiale;
	}
	public void setDenominazioneUfficiale(String denominazioneUfficiale) {
		this.denominazioneUfficiale = denominazioneUfficiale;
	}
	public String getShapeLeng() {
		return shapeLeng;
	}
	public void setShapeLeng(String shapeLeng) {
		this.shapeLeng = shapeLeng;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCompendi() {
		return compendi;
	}
	public void setCompendi(String compendi) {
		this.compendi = compendi;
	}
	public String getPrecdenomi() {
		return precdenomi;
	}
	public void setPrecdenomi(String precdenomi) {
		this.precdenomi = precdenomi;
	}
	public String getQuartiere() {
		return quartiere;
	}
	public void setQuartiere(String quartiere) {
		this.quartiere = quartiere;
	}
	public Comuni getComune() {
		return comune;
	}
	public void setComune(Comuni comune) {
		this.comune = comune;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Long getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public ToponimoStradale getToponimo() {
		return toponimo;
	}
	public void setToponimo(ToponimoStradale toponimo) {
		this.toponimo = toponimo;
	}
	public Accesso getAccesso() {
		return accesso;
	}
	public void setAccesso(Accesso accesso) {
		this.accesso = accesso;
	}
	
}
