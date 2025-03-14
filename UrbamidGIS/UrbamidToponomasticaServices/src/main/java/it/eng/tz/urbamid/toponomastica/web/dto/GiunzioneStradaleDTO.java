package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;

public class GiunzioneStradaleDTO implements Serializable {

	private static final long serialVersionUID = -3741854190131599419L;

	private Long id;
	private String descrizione;
	private TipoTopologicoDTO tipoTopologico;
	private TipoFunzionaleDTO tipoFunzionale;
	private boolean limiteAmministrativo;
	private boolean inizioFineStrada;
	private String geom;
	private String note;
	private String stato;
	private Boolean isCircle;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public TipoTopologicoDTO getTipoTopologico() {
		return tipoTopologico;
	}
	public void setTipoTopologico(TipoTopologicoDTO tipoTopologico) {
		this.tipoTopologico = tipoTopologico;
	}
	public TipoFunzionaleDTO getTipoFunzionale() {
		return tipoFunzionale;
	}
	public void setTipoFunzionale(TipoFunzionaleDTO tipoFunzionale) {
		this.tipoFunzionale = tipoFunzionale;
	}
	public boolean isLimiteAmministrativo() {
		return limiteAmministrativo;
	}
	public void setLimiteAmministrativo(boolean limiteAmministrativo) {
		this.limiteAmministrativo = limiteAmministrativo;
	}
	public boolean isInizioFineStrada() {
		return inizioFineStrada;
	}
	public void setInizioFineStrada(boolean inizioFineStrada) {
		this.inizioFineStrada = inizioFineStrada;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
