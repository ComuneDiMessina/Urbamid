package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;
import java.util.Date;

public class ToponimoStradaleDTO implements Serializable {

	private static final long serialVersionUID = 1330275107708652192L;

	private Long id;
	private String comuneLabel;
	private String denominazione;
	private String denominazioneUfficiale;
	private String classeLabel;
	private String shapeLeng;
	private String cap;
	private String compendi;
	private String precdenomi;
	private String quartiere;
	private String geom;
	private String numeroDelibera;
	private Date dataDelibera;
	private String codiceAutorizzazione;
	private Date dataAutorizzazione;	
	private ComuniDto comune;
	private DugDTO classe;
	private String note;
	private TipoToponimoDTO tipo;
	private String codice;
	private String stato;
	private Long idPadre;
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
	public ComuniDto getComune() {
		return comune;
	}
	public void setComune(ComuniDto comune) {
		this.comune = comune;
	}
	public DugDTO getClasse() {
		return classe;
	}
	public void setClasse(DugDTO classe) {
		this.classe = classe;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public TipoToponimoDTO getTipo() {
		return tipo;
	}
	public void setTipo(TipoToponimoDTO tipo) {
		this.tipo = tipo;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
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
