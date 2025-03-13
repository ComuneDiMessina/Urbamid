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
@Table(name="u_cat_unita_immobiliare")
public class UnitaImmobiliare implements Serializable {

	private static final long serialVersionUID = 2851726248331266859L;

	@Id 
	@SequenceGenerator(name="unita_immobiliare_seq", sequenceName="unita_immobiliare_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="unita_immobiliare_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "id_immobile")
	private Long idImmobile;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "progressivo")
	private Integer progressivo;

	@Column(name = "zona", length=10)
	private String zona;

	@Column(name = "categoria", length=10)
	private String categoria;

	@Column(name = "classe", length=10)
	private String classe;

	@Column(name = "consistenza")
	private Double consistenza;

	@Column(name = "superficie")
	private Double superficie;

	@Column(name = "rendita_lire")
	private Double renditaLire;

	@Column(name = "rendita_euro")
	private Double renditaEuro;

	@Column(name = "lotto", length=2)
	private String lotto;

	@Column(name = "edificio", length=2)
	private String edificio;

	@Column(name = "scala", length=2)
	private String scala;

	@Column(name = "interno_1", length=3)
	private String interno1;

	@Column(name = "interno_2", length=3)
	private String interno2;

	@Column(name = "piano_1", length=4)
	private String piano1;

	@Column(name = "piano_2", length=4)
	private String piano2;

	@Column(name = "piano_3", length=4)
	private String piano3;

	@Column(name = "piano_4", length=4)
	private String piano4;

	@Column(name = "data_efficacia_gen")
	@Temporal(value = TemporalType.DATE)
	private Date dataEfficaciaGen;

	@Column(name = "data_registrazione_gen")
	@Temporal(value = TemporalType.DATE)
	private Date dataRegistrazioneGen;

	@Column(name = "tipo_nota_gen", length=1)
	private String tipoNotaGen;

	@Column(name = "num_nota_gen", length=6)
	private String numNotaGen;

	@Column(name = "progr_nota_gen", length=3)
	private String progrNotaGen;

	@Column(name = "anno_nota_gen")
	private Integer annoNotaGen;

	@Column(name = "data_efficacia_concl")
	@Temporal(value = TemporalType.DATE)
	private Date dataEfficaciaConcl;

	@Column(name = "data_registrazione_concl")
	@Temporal(value = TemporalType.DATE)
	private Date dataRegistrazioneConcl;

	@Column(name = "tipo_nota_concl", length=1)
	private String tipoNotaConcl;

	@Column(name = "num_nota_concl", length=6)
	private String numNotaConcl;

	@Column(name = "progr_nota_concl", length=3)
	private String progrNotaConcl;

	@Column(name = "anno_nota_concl")
	private Integer annoNotaConcl;

	@Column(name = "partita", length=7)
	private String partita;

	@Column(name = "annotazione", length=200)
	private String annotazione;

	@Column(name = "id_mutaz_in")
	private Long idMutazIn;

	@Column(name = "id_mutaz_fin")
	private Long idMutazFin;

	@Column(name = "prot_notifica", length=18)
	private String protNotifica;

	@Column(name = "data_notifica")
	@Temporal(value = TemporalType.DATE)
	private Date dataNotifica;

	@Column(name = "codice_causale_atto_gen", length=3)
	private String codiceCausaleAttoGen;

	@Column(name = "descrizione_atto_gen", length=100)
	private String descrizioneAttoGen;

	@Column(name = "codice_causale_atto_concl", length=3)
	private String codiceCausaleAttoConcl;

	@Column(name = "descrizione_atto_concl", length=100)
	private String descrizioneAttoConcl;

	@Column(name = "flag_classamento", length=1)
	private String flagClassamento;

	public UnitaImmobiliare () { }

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

	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Double getConsistenza() {
		return consistenza;
	}

	public void setConsistenza(Double consistenza) {
		this.consistenza = consistenza;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}

	public Double getRenditaLire() {
		return renditaLire;
	}

	public void setRenditaLire(Double renditaLire) {
		this.renditaLire = renditaLire;
	}

	public Double getRenditaEuro() {
		return renditaEuro;
	}

	public void setRenditaEuro(Double renditaEuro) {
		this.renditaEuro = renditaEuro;
	}

	public String getLotto() {
		return lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getPiano1() {
		return piano1;
	}

	public void setPiano1(String piano1) {
		this.piano1 = piano1;
	}

	public String getPiano2() {
		return piano2;
	}

	public void setPiano2(String piano2) {
		this.piano2 = piano2;
	}

	public String getPiano3() {
		return piano3;
	}

	public void setPiano3(String piano3) {
		this.piano3 = piano3;
	}

	public String getPiano4() {
		return piano4;
	}

	public void setPiano4(String piano4) {
		this.piano4 = piano4;
	}

	public Date getDataEfficaciaGen() {
		return dataEfficaciaGen;
	}

	public void setDataEfficaciaGen(Date dataEfficaciaGen) {
		this.dataEfficaciaGen = dataEfficaciaGen;
	}

	public Date getDataRegistrazioneGen() {
		return dataRegistrazioneGen;
	}

	public void setDataRegistrazioneGen(Date dataRegistrazioneGen) {
		this.dataRegistrazioneGen = dataRegistrazioneGen;
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

	public Integer getAnnoNotaGen() {
		return annoNotaGen;
	}

	public void setAnnoNotaGen(Integer annoNotaGen) {
		this.annoNotaGen = annoNotaGen;
	}

	public Date getDataEfficaciaConcl() {
		return dataEfficaciaConcl;
	}

	public void setDataEfficaciaConcl(Date dataEfficaciaConcl) {
		this.dataEfficaciaConcl = dataEfficaciaConcl;
	}

	public Date getDataRegistrazioneConcl() {
		return dataRegistrazioneConcl;
	}

	public void setDataRegistrazioneConcl(Date dataRegistrazioneConcl) {
		this.dataRegistrazioneConcl = dataRegistrazioneConcl;
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

	public Integer getAnnoNotaConcl() {
		return annoNotaConcl;
	}

	public void setAnnoNotaConcl(Integer annoNotaConcl) {
		this.annoNotaConcl = annoNotaConcl;
	}

	public String getPartita() {
		return partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public String getAnnotazione() {
		return annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
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

	public String getProtNotifica() {
		return protNotifica;
	}

	public void setProtNotifica(String protNotifica) {
		this.protNotifica = protNotifica;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
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

	public String getFlagClassamento() {
		return flagClassamento;
	}

	public void setFlagClassamento(String flagClassamento) {
		this.flagClassamento = flagClassamento;
	}

	public String getInterno1() {
		return interno1;
	}

	public void setInterno1(String interno1) {
		this.interno1 = interno1;
	}

	public String getInterno2() {
		return interno2;
	}

	public void setInterno2(String interno2) {
		this.interno2 = interno2;
	}

}
