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
@Table(name = "u_topo_estesa_amministrativa")
public class EstesaAmministrativa implements Serializable {

	private static final long serialVersionUID = -4078032362475980810L;

	@Id
	@SequenceGenerator(name="u_topo_estesa_amministrativa_id_seq", sequenceName="u_topo_estesa_amministrativa_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_topo_estesa_amministrativa_id_seq")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "sigla", length = 100, nullable = false)
	private String sigla;
	
	@Column(name = "descrizione", length = 100, nullable = false)
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classifica_amministrativa", nullable = true)
	private ClassificaAmministrativa classificaAmministrativa;
	
	@Column(name = "codice", length = 100, nullable = true)
	private String codice;
	
	@Column(name = "estensione", length = 100, nullable = true)
	private String estensione;
	
	@Column(name = "tronco", length = 250, nullable = true)
	private String tronco;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patrimonialita", nullable = true)
	private Patrimonialita patrimonialita;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ente_gestore", nullable = true)
	private EnteGestore enteGestore;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classifica_funzionale", nullable = true)
	private ClassificaFunzionale classificaFunzionale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provincia", nullable = false)
	private Province provincia; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comune", nullable = false)
	private Comuni comune;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;
	
	@Column(name = "geom", nullable = true)
	@ColumnTransformer(read = "ST_AsText(geom)")
	private String geom;
	
	@Column(name = "stato", nullable = true)
	private String stato;
	
	@Column(name = "is_circle", nullable = true)
	private Boolean isCircle;
	
	@OneToMany(mappedBy = "estesaAmministrativa", fetch = FetchType.LAZY)
	private Set<CippoChilometrico> cippoChilometrico;
	
	@OneToMany(mappedBy = "estesaAmministrativa", fetch = FetchType.LAZY)
	private Set<Percorso> percorso;

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

	public ClassificaAmministrativa getClassificaAmministrativa() {
		return classificaAmministrativa;
	}

	public void setClassificaAmministrativa(ClassificaAmministrativa classificaAmministrativa) {
		this.classificaAmministrativa = classificaAmministrativa;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getEstensione() {
		return estensione;
	}

	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

	public String getTronco() {
		return tronco;
	}

	public void setTronco(String tronco) {
		this.tronco = tronco;
	}

	public Patrimonialita getPatrimonialita() {
		return patrimonialita;
	}

	public void setPatrimonialita(Patrimonialita patrimonialita) {
		this.patrimonialita = patrimonialita;
	}

	public EnteGestore getEnteGestore() {
		return enteGestore;
	}

	public void setEnteGestore(EnteGestore enteGestore) {
		this.enteGestore = enteGestore;
	}

	public ClassificaFunzionale getClassificaFunzionale() {
		return classificaFunzionale;
	}

	public void setClassificaFunzionale(ClassificaFunzionale classificaFunzionale) {
		this.classificaFunzionale = classificaFunzionale;
	}

	public Province getProvincia() {
		return provincia;
	}

	public void setProvincia(Province provincia) {
		this.provincia = provincia;
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

	public Boolean getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(Boolean isCircle) {
		this.isCircle = isCircle;
	}
	
}
