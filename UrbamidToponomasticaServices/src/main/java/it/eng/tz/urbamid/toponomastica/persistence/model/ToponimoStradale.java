package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "u_topo_toponimo_stradale")
public class ToponimoStradale implements Serializable {

	private static final long serialVersionUID = -5452756193798865555L;

	@Id
	@SequenceGenerator(name="u_topo_toponimo_stradale_id_seq", sequenceName="u_topo_toponimo_stradale_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_toponimo_stradale_id_seq")
	@Column(name = "id")
	private Long id; 
	
	@Column(name = "comune_label", length = 10, nullable = true)
	private String comuneLabel;
	
	@Column(name = "denominazione", length = 200, nullable = false)
	private String denominazione;
	
	@Column(name = "denominazione_ufficiale", length = 100, nullable = false)
	@ColumnTransformer(read  = "UPPER(denominazione_ufficiale)")
	private String denominazioneUfficiale;
	
	@Column(name = "classe_label", length = 50, nullable = true)
	private String classeLabel;
	
	@Column(name = "shape_leng", nullable = true)
	private String shapeLeng;
	
	@Column(name = "cap", length = 10, nullable = true)
	private String cap;
	
	@Column(name = "compendi", length = 254, nullable = true)
	private String compendi;
	
	@Column(name = "precdenomi", length = 50, nullable = true)
	private String precdenomi;
	
	@Column(name = "quartiere", length = 50, nullable = true)
	private String quartiere;

	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;
	
	@Column(name = "numero_delibera", length = 150, nullable = true)
	private String numeroDelibera;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "data_delibera", nullable = true)
	private Date dataDelibera;	
	
	@Column(name = "codice_autorizzazione", length = 150, nullable = true)
	private String codiceAutorizzazione;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "data_autorizzazione", nullable = true)
	private Date dataAutorizzazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_comune", nullable = false)
	private Comuni comune;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_classe", nullable = false)
	private Dug classe; 
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "stato", nullable = true)
	private String stato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo", nullable = false)
	private TipoToponimo tipo;
	
	@Column(name = "codice", length = 100, nullable = true)
	private String codice;
	
	@OneToMany(mappedBy = "toponimo", fetch = FetchType.LAZY)
	private Set<Accesso> accesso;
	
	@OneToMany(mappedBy = "toponimo", fetch = FetchType.LAZY)
	private Set<Percorso> percorso;
	
	@OneToMany(mappedBy = "toponimo", fetch = FetchType.LAZY)
	private Set<GeocodingReverseGeocoding> geocoding;
	
	@Column(name = "id_padre", nullable = true)
	private Long idPadre;
	
	@Column(name = "is_circle", nullable = false)
	private Boolean isCircle;
	
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
	
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDenominazioneUfficiale() {
		return denominazioneUfficiale;
	}

	public void setDenominazioneUfficiale(String denominazioneUfficiale) {
		this.denominazioneUfficiale = denominazioneUfficiale;
	}

	public String getClasseLabel() {
		return classeLabel;
	}

	public void setClasseLabel(String classeLabel) {
		this.classeLabel = classeLabel;
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

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public String getNumeroDelibera() {
		return numeroDelibera;
	}

	public void setNumeroDelibera(String numeroDelibera) {
		this.numeroDelibera = numeroDelibera;
	}

	public Date getDataDelibera() {
		return dataDelibera;
	}

	public void setDataDelibera(Date dataDelibera) {
		this.dataDelibera = dataDelibera;
	}

	public String getCodiceAutorizzazione() {
		return codiceAutorizzazione;
	}

	public void setCodiceAutorizzazione(String codiceAutorizzazione) {
		this.codiceAutorizzazione = codiceAutorizzazione;
	}

	public Date getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Comuni getComune() {
		return comune;
	}

	public void setComune(Comuni comune) {
		this.comune = comune;
	}

	public Dug getClasse() {
		return classe;
	}

	public void setClasse(Dug classe) {
		this.classe = classe;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TipoToponimo getTipo() {
		return tipo;
	}

	public void setTipo(TipoToponimo tipo) {
		this.tipo = tipo;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Set<Accesso> getAccesso() {
		return accesso;
	}

	public void setAccesso(Set<Accesso> accesso) {
		this.accesso = accesso;
	}

	public Set<Percorso> getPercorso() {
		return percorso;
	}

	public void setPercorso(Set<Percorso> percorso) {
		this.percorso = percorso;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	public Boolean getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(Boolean isCircle) {
		this.isCircle = isCircle;
	}

}
