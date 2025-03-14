package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_titolarita")
public class Titolarita implements Serializable {

	private static final long serialVersionUID = -7608623235763774578L;

	@Id 
	@SequenceGenerator(name="titolarita_id_seq", sequenceName="titolarita_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="titolarita_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "id_soggetto")
	private int idSoggetto;

	@Column(name = "tipo_soggetto", length=1)
	private String tipoSoggetto;

	@Column(name = "id_immobile")
	private Long idImmobile;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "codice_diritto", length=3)
	private String codiceDiritto;

	@Column(name = "titolo_non_codificato", length=200)
	private String titoloNonCodificato;

	@Column(name = "quota_numeratore", length=9)
	private String quotaNumeratore;

	@Column(name = "quota_denominatore", length=9)
	private String quotaDenominatore;

	@Column(name = "regime", length=1)
	private String regime;

	@Column(name = "id_soggetto_riferimento")
	private Long idSoggettoRiferimento;

	@Column(name = "data_validita_gen")
	@Temporal(value = TemporalType.DATE)
	private Date dataValiditaGen;

	@Column(name = "tipo_nota_gen", length=1)
	private String tipoNotaGen;

	@Column(name = "num_nota_gen", length=6)
	private String numNotaGen;

	@Column(name = "progr_nota_gen", length=3)
	private String progrNotaGen;

	@Column(name = "anno_nota_gen")
	private int annoNotaGen;

	@Column(name = "data_registrazione_gen")
	@Temporal(value = TemporalType.DATE)
	private Date dataRegistrazioneGen;

	@Column(name = "partita", length=7)
	private String partita;

	@Column(name = "data_validita_concl")
	@Temporal(value = TemporalType.DATE)
	private Date dataValiditaConcl;

	@Column(name = "tipo_nota_concl", length=1)
	private String tipoNotaConcl;

	@Column(name = "num_nota_concl", length=6)
	private String numNotaConcl;

	@Column(name = "progr_nota_concl", length=3)
	private String progrNotaConcl;

	@Column(name = "anno_nota_concl")
	private int annoNotaConcl;

	@Column(name = "data_registrazione_concl")
	@Temporal(value = TemporalType.DATE)
	private Date dataRegistrazioneConcl;

	@Column(name = "id_mutaz_in")
	private Long idMutazIn;

	@Column(name = "id_mutaz_fin")
	private Long idMutazFin;

	@Column(name = "id_titolarita")
	private Long idTitolarita;

	@Column(name = "codice_causale_atto_gen", length=3)
	private String codiceCausaleAttoGen;

	@Column(name = "descrizione_atto_gen", length=100)
	private String descrizioneAttoGen;

	@Column(name = "codice_causale_atto_concl", length=3)
	private String codiceCausaleAttoConcl;

	@Column(name = "descrizione_atto_concl", length=100)
	private String descrizioneAttoConcl;

	public Titolarita () { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public int getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public Long getIdImmobile() {
		return idImmobile;
	}

	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

	public String getCodiceDiritto() {
		return codiceDiritto;
	}

	public void setCodiceDiritto(String codiceDiritto) {
		this.codiceDiritto = codiceDiritto;
	}

	public String getTitoloNonCodificato() {
		return titoloNonCodificato;
	}

	public void setTitoloNonCodificato(String titoloNonCodificato) {
		this.titoloNonCodificato = titoloNonCodificato;
	}

	public String getQuotaNumeratore() {
		return quotaNumeratore;
	}

	public void setQuotaNumeratore(String quotaNumeratore) {
		this.quotaNumeratore = quotaNumeratore;
	}

	public String getQuotaDenominatore() {
		return quotaDenominatore;
	}

	public void setQuotaDenominatore(String quotaDenominatore) {
		this.quotaDenominatore = quotaDenominatore;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public Long getIdSoggettoRiferimento() {
		return idSoggettoRiferimento;
	}

	public void setIdSoggettoRiferimento(Long idSoggettoRiferimento) {
		this.idSoggettoRiferimento = idSoggettoRiferimento;
	}

	public Date getDataValiditaGen() {
		return dataValiditaGen;
	}

	public void setDataValiditaGen(Date dataValiditaGen) {
		this.dataValiditaGen = dataValiditaGen;
	}

	public String getTipoNotaGen() {
		return tipoNotaGen;
	}

	public void setTipoNotaGen(String tipoNotaGen) {
		this.tipoNotaGen = tipoNotaGen;
	}

	public String getNumNotaGen() {
		return numNotaGen;
	}

	public void setNumNotaGen(String numNotaGen) {
		this.numNotaGen = numNotaGen;
	}

	public String getProgrNotaGen() {
		return progrNotaGen;
	}

	public void setProgrNotaGen(String progrNotaGen) {
		this.progrNotaGen = progrNotaGen;
	}

	public int getAnnoNotaGen() {
		return annoNotaGen;
	}

	public void setAnnoNotaGen(int annoNotaGen) {
		this.annoNotaGen = annoNotaGen;
	}

	public Date getDataRegistrazioneGen() {
		return dataRegistrazioneGen;
	}

	public void setDataRegistrazioneGen(Date dataRegistrazioneGen) {
		this.dataRegistrazioneGen = dataRegistrazioneGen;
	}

	public String getPartita() {
		return partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public Date getDataValiditaConcl() {
		return dataValiditaConcl;
	}

	public void setDataValiditaConcl(Date dataValiditaConcl) {
		this.dataValiditaConcl = dataValiditaConcl;
	}

	public String getTipoNotaConcl() {
		return tipoNotaConcl;
	}

	public void setTipoNotaConcl(String tipoNotaConcl) {
		this.tipoNotaConcl = tipoNotaConcl;
	}

	public String getNumNotaConcl() {
		return numNotaConcl;
	}

	public void setNumNotaConcl(String numNotaConcl) {
		this.numNotaConcl = numNotaConcl;
	}

	public String getProgrNotaConcl() {
		return progrNotaConcl;
	}

	public void setProgrNotaConcl(String progrNotaConcl) {
		this.progrNotaConcl = progrNotaConcl;
	}

	public int getAnnoNotaConcl() {
		return annoNotaConcl;
	}

	public void setAnnoNotaConcl(int annoNotaConcl) {
		this.annoNotaConcl = annoNotaConcl;
	}

	public Date getDataRegistrazioneConcl() {
		return dataRegistrazioneConcl;
	}

	public void setDataRegistrazioneConcl(Date dataRegistrazioneConcl) {
		this.dataRegistrazioneConcl = dataRegistrazioneConcl;
	}

	public Long getIdMutazIn() {
		return idMutazIn;
	}

	public void setIdMutazIn(Long idMutazIn) {
		this.idMutazIn = idMutazIn;
	}

	public Long getIdMutazFin() {
		return idMutazFin;
	}

	public void setIdMutazFin(Long idMutazFin) {
		this.idMutazFin = idMutazFin;
	}

	public Long getIdTitolarita() {
		return idTitolarita;
	}

	public void setIdTitolarita(Long idTitolarita) {
		this.idTitolarita = idTitolarita;
	}

	public String getCodiceCausaleAttoGen() {
		return codiceCausaleAttoGen;
	}

	public void setCodiceCausaleAttoGen(String codiceCausaleAttoGen) {
		this.codiceCausaleAttoGen = codiceCausaleAttoGen;
	}

	public String getDescrizioneAttoGen() {
		return descrizioneAttoGen;
	}

	public void setDescrizioneAttoGen(String descrizioneAttoGen) {
		this.descrizioneAttoGen = descrizioneAttoGen;
	}

	public String getCodiceCausaleAttoConcl() {
		return codiceCausaleAttoConcl;
	}

	public void setCodiceCausaleAttoConcl(String codiceCausaleAttoConcl) {
		this.codiceCausaleAttoConcl = codiceCausaleAttoConcl;
	}

	public String getDescrizioneAttoConcl() {
		return descrizioneAttoConcl;
	}

	public void setDescrizioneAttoConcl(String descrizioneAttoConcl) {
		this.descrizioneAttoConcl = descrizioneAttoConcl;
	}
	
}
