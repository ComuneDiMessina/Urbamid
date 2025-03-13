package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;
import java.util.Date;

public class UnitaImmobiliareDTO implements Serializable {

	private static final long serialVersionUID = -1595260950421104004L;

	private Long id;
	private String comune;
	private String sezione;
	private Long idImmobile;
	private String tipoImmobile;
	private Integer progressivo;
	private String zona;
	private String categoria;
	private String classe;
	private Double consistenza;
	private Double superficie;
	private Double renditaLire;
	private Double renditaEuro;
	private String lotto;
	private String edificio;
	private String scala;
	private String interno1;
	private String interno2;
	private String piano1;
	private String piano2;
	private String piano3;
	private String piano4;
	private Date dataEfficaciaGen;
	private Date dataRegistrazioneGen;
	private String tipoNotaGen;
	private String numNotaGen;
	private String progrNotaGen;
	private Integer annoNotaGen;
	private Date dataEfficaciaConcl;
	private Date dataRegistrazioneConcl;
	private String tipoNotaConcl;
	private String numNotaConcl;
	private String progrNotaConcl;
	private Integer annoNotaConcl;
	private String partita;
	private String annotazione;
	private Long idMutazIn;
	private Long idMutazFin;
	private String protNotifica;
	private Date dataNotifica;
	private String codiceCausaleAttoGen;
	private String descrizioneAttoGen;
	private String codiceCausaleAttoConcl;
	private String descrizioneAttoConcl;

	private String indirizzo;
	private String civico1;
	private String civico2;
	private String civico3;
	private String foglio;
	private String numero;
	private String subalterno;

	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCivico1() {
		return civico1;
	}
	public void setCivico1(String civico1) {
		this.civico1 = civico1;
	}
	public String getCivico2() {
		return civico2;
	}
	public void setCivico2(String civico2) {
		this.civico2 = civico2;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
	public String getCivico3() {
		return civico3;
	}
	public void setCivico3(String civico3) {
		this.civico3 = civico3;
	}
	private String flagClassamento;

}
