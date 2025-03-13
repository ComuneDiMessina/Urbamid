package it.eng.tz.urbamid.prg.filter;

import java.io.Serializable;

public class TipoDocumentoFilter implements Serializable {

	private static final long serialVersionUID = 7033802991443772920L;

	private Long id;
	private String codice;
	private String descrizione;
	private String descrizioneCDU;
	private String note;
	private Integer pageIndex;
	private Integer pageSize;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneCDU() {
		return descrizioneCDU;
	}
	public void setDescrizioneCDU(String descrizioneCDU) {
		this.descrizioneCDU = descrizioneCDU;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
