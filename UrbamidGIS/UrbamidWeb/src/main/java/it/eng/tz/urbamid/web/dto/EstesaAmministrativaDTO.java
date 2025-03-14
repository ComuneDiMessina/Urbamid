package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class EstesaAmministrativaDTO implements Serializable {

	private static final long serialVersionUID = -1870045936591673388L;
	
	private Long id;
	private String sigla;
	private String descrizione;
	private ClassificaAmministrativaDTO classificaAmministrativa;
	private String codice;
	private String estensione;
	private String tronco;
	private PatrimonialitaDTO patrimonialita;
	private EnteGestoreDTO enteGestore;
	private ClassificaFunzionaleDTO classificaFunzionale;
	private ProvinciaDTO provincia; 
	private ComuneDTO comune;
	private String note;
	private String geom;
	private String stato;
	private Boolean isCircle;
	
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
	public ClassificaAmministrativaDTO getClassificaAmministrativa() {
		return classificaAmministrativa;
	}
	public void setClassificaAmministrativa(ClassificaAmministrativaDTO classificaAmministrativa) {
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
	public PatrimonialitaDTO getPatrimonialita() {
		return patrimonialita;
	}
	public void setPatrimonialita(PatrimonialitaDTO patrimonialita) {
		this.patrimonialita = patrimonialita;
	}
	public EnteGestoreDTO getEnteGestore() {
		return enteGestore;
	}
	public void setEnteGestore(EnteGestoreDTO enteGestore) {
		this.enteGestore = enteGestore;
	}
	public ClassificaFunzionaleDTO getClassificaFunzionale() {
		return classificaFunzionale;
	}
	public void setClassificaFunzionale(ClassificaFunzionaleDTO classificaFunzionale) {
		this.classificaFunzionale = classificaFunzionale;
	}
	public ProvinciaDTO getProvincia() {
		return provincia;
	}
	public void setProvincia(ProvinciaDTO provincia) {
		this.provincia = provincia;
	}
	public ComuneDTO getComune() {
		return comune;
	}
	public void setComune(ComuneDTO comune) {
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
