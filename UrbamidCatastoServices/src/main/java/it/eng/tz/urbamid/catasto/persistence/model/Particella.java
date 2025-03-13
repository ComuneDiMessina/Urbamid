package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_particelle_catastali")
public class Particella implements Serializable {

	private static final long serialVersionUID = -5858114647172664412L;

	@Id 
	@SequenceGenerator(name="particella_id_seq", sequenceName="particella_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="particella_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "classe", length=2)
	private String classe;

	@Column(name = "tipo_nota_gen", length=1)
	private String tipoNotaGen;

	@Column(name = "numero_nota_gen", length=6)
	private String numeroNotaGen;

	@Column(name = "progressivo_nota_gen", length=3)
	private String progressivoNotaGen;

	@Column(name = "tipo_nota_concl", length=1)
	private String tipoNotaConcl;

	@Column(name = "numero_nota_concl", length=6)
	private String numeroNotaConcl;

	@Column(name = "progressivo_nota_concl", length=3)
	private String progressivoNotaConcl;

	@Column(name = "partita", length=7)
	private String partita;

	@Column(name = "foglio")
	private String foglio;

	@Column(name = "numero")
	private String numero;

	@Column(name = "subalterno", length=4)
	private String subalterno;

	@Column(name = "edificabilita", length=1)
	private String edificabilita;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "flag_reddito", length=1)
	private String flagReddito;

	@Column(name = "flag_porzione", length=1)
	private String flagPorzione;

	@Column(name = "flag_deduzioni", length=1)
	private String flagDeduzioni;

	@Column(name = "annotazione", length=200)
	private String annotazione;

	@Column(name = "codice_causale_atto_gen", length=3)
	private String codiceCausaleAttoGen;

	@Column(name = "descrizione_atto_gen", length=100)
	private String descrizioneAttoGen;

	@Column(name = "codice_causale_atto_concl", length=3)
	private String codiceCausaleAttoConcl;

	@Column(name = "descrizione_atto_concl", length=100)
	private String descrizioneAttoConcl;

	@Column(name = "id_immobile")
	private Long idImmobile;

	@Column(name = "progressivo")
	private Integer progressivo;

	@Column(name = "anno_nota_gen")
	private Integer annoNotaGen;

	@Column(name = "anno_nota_concl")
	private Integer annoNotaConcl;

	@Column(name = "id_mutaz_in")
	private Long idMutazIn;

	@Column(name = "id_mutaz_fin")
	private Long idMutazFin;

	@Column(name = "qualita")
	private Integer qualita;

	@Column(name = "ettari")
	private Integer ettari;

	@Column(name = "are")
	private Integer are;

	@Column(name = "reddito_dominicale_lire")
	private Double redditoDominicaleLire;

	@Column(name = "reddito_agrario_lire")
	private Double redditoAgrarioLire;

	@Column(name = "reddito_dominicale_euro")
	private Double redditoDominicaleEuro;

	@Column(name = "reddito_agrario_euro")
	private Double redditoAgrarioEuro;

	@Column(name = "centiare")
	private Integer centiare;

	@Column(name = "data_efficacia_gen", length=8)
	private String dataEfficaciaGen;

	@Column(name = "data_registrazione_gen", length=8)
	private String dataRegistrazioneGen;

	@Column(name = "data_efficacia_concl", length=8)
	private String dataEfficaciaConcl;

	@Column(name = "data_registrazione_concl", length=8)
	private String dataRegistrazioneConcl;

	@Column(name = "denominatore", length=4)
	private Integer denominatore;

	public Particella () { }

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

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getTipoNotaGen() {
		return tipoNotaGen;
	}

	public void setTipoNotaGen(String tipoNotaGen) {
		this.tipoNotaGen = tipoNotaGen;
	}

	public String getNumeroNotaGen() {
		return numeroNotaGen;
	}

	public void setNumeroNotaGen(String numeroNotaGen) {
		this.numeroNotaGen = numeroNotaGen;
	}

	public String getProgressivoNotaGen() {
		return progressivoNotaGen;
	}

	public void setProgressivoNotaGen(String progressivoNotaGen) {
		this.progressivoNotaGen = progressivoNotaGen;
	}

	public String getTipoNotaConcl() {
		return tipoNotaConcl;
	}

	public void setTipoNotaConcl(String tipoNotaConcl) {
		this.tipoNotaConcl = tipoNotaConcl;
	}

	public String getNumeroNotaConcl() {
		return numeroNotaConcl;
	}

	public void setNumeroNotaConcl(String numeroNotaConcl) {
		this.numeroNotaConcl = numeroNotaConcl;
	}

	public String getProgressivoNotaConcl() {
		return progressivoNotaConcl;
	}

	public void setProgressivoNotaConcl(String progressivoNotaConcl) {
		this.progressivoNotaConcl = progressivoNotaConcl;
	}

	public String getPartita() {
		return partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getEdificabilita() {
		return edificabilita;
	}

	public void setEdificabilita(String edificabilita) {
		this.edificabilita = edificabilita;
	}

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

	public String getFlagReddito() {
		return flagReddito;
	}

	public void setFlagReddito(String flagReddito) {
		this.flagReddito = flagReddito;
	}

	public String getFlagPorzione() {
		return flagPorzione;
	}

	public void setFlagPorzione(String flagPorzione) {
		this.flagPorzione = flagPorzione;
	}

	public String getFlagDeduzioni() {
		return flagDeduzioni;
	}

	public void setFlagDeduzioni(String flagDeduzioni) {
		this.flagDeduzioni = flagDeduzioni;
	}

	public String getAnnotazione() {
		return annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
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

	public Long getIdImmobile() {
		return idImmobile;
	}

	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}

	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	public Integer getAnnoNotaGen() {
		return annoNotaGen;
	}

	public void setAnnoNotaGen(Integer annoNotaGen) {
		this.annoNotaGen = annoNotaGen;
	}

	public Integer getAnnoNotaConcl() {
		return annoNotaConcl;
	}

	public void setAnnoNotaConcl(Integer annoNotaConcl) {
		this.annoNotaConcl = annoNotaConcl;
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

	public Integer getQualita() {
		return qualita;
	}

	public void setQualita(Integer qualita) {
		this.qualita = qualita;
	}

	public Integer getEttari() {
		return ettari;
	}

	public void setEttari(Integer ettari) {
		this.ettari = ettari;
	}

	public Integer getAre() {
		return are;
	}

	public void setAre(Integer are) {
		this.are = are;
	}

	public Double getRedditoDominicaleLire() {
		return redditoDominicaleLire;
	}

	public void setRedditoDominicaleLire(Double redditoDominicaleLire) {
		this.redditoDominicaleLire = redditoDominicaleLire;
	}

	public Double getRedditoAgrarioLire() {
		return redditoAgrarioLire;
	}

	public void setRedditoAgrarioLire(Double redditoAgrarioLire) {
		this.redditoAgrarioLire = redditoAgrarioLire;
	}

	public Double getRedditoDominicaleEuro() {
		return redditoDominicaleEuro;
	}

	public void setRedditoDominicaleEuro(Double redditoDominicaleEuro) {
		this.redditoDominicaleEuro = redditoDominicaleEuro;
	}

	public Double getRedditoAgrarioEuro() {
		return redditoAgrarioEuro;
	}

	public void setRedditoAgrarioEuro(Double redditoAgrarioEuro) {
		this.redditoAgrarioEuro = redditoAgrarioEuro;
	}

	public Integer getCentiare() {
		return centiare;
	}

	public void setCentiare(Integer centiare) {
		this.centiare = centiare;
	}

	public String getDataEfficaciaGen() {
		return dataEfficaciaGen;
	}

	public void setDataEfficaciaGen(String dataEfficaciaGen) {
		this.dataEfficaciaGen = dataEfficaciaGen;
	}

	public String getDataRegistrazioneGen() {
		return dataRegistrazioneGen;
	}

	public void setDataRegistrazioneGen(String dataRegistrazioneGen) {
		this.dataRegistrazioneGen = dataRegistrazioneGen;
	}

	public String getDataEfficaciaConcl() {
		return dataEfficaciaConcl;
	}

	public void setDataEfficaciaConcl(String dataEfficaciaConcl) {
		this.dataEfficaciaConcl = dataEfficaciaConcl;
	}

	public String getDataRegistrazioneConcl() {
		return dataRegistrazioneConcl;
	}

	public void setDataRegistrazioneConcl(String dataRegistrazioneConcl) {
		this.dataRegistrazioneConcl = dataRegistrazioneConcl;
	}

	public Integer getDenominatore() {
		return denominatore;
	}

	public void setDenominatore(Integer denominatore) {
		this.denominatore = denominatore;
	}
}
