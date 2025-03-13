package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;
import java.util.Date;

public class GeocodingReverseGeocodingDTO implements Serializable {

	private static final long serialVersionUID = -3767560462667358715L;

	private Long id;
	private String comuneLabel;
	private String denominazioneUfficiale;
	private String shapeLeng;
	private String cap;
	private String compendi;
	private String precdenomi;
	private String quartiere;
	private ComuniDto comune;
	private String note;
	private String codice;
	private Long idPadre;
	private String stato;
	private Date dataFineValidita;
	private String geom;
	private ToponimoStradaleDTO toponimo;
	private AccessoDTO accesso;
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
	public ComuniDto getComune() {
		return comune;
	}
	public void setComune(ComuniDto comune) {
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
	public ToponimoStradaleDTO getToponimo() {
		return toponimo;
	}
	public void setToponimo(ToponimoStradaleDTO toponimo) {
		this.toponimo = toponimo;
	}
	public AccessoDTO getAccesso() {
		return accesso;
	}
	public void setAccesso(AccessoDTO accesso) {
		this.accesso = accesso;
	}
	
}
