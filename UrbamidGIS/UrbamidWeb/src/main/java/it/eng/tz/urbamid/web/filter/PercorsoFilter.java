package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class PercorsoFilter implements Serializable {

	private static final long serialVersionUID = -3227200053795231551L;
	
	private String sigla;
	private String descrizione;
	private Boolean tipo;
	private String stato;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	
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
	public Boolean isTipo() {
		return tipo;
	}
	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
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
	public Integer getPageDraw() {
		return pageDraw;
	}
	public void setPageDraw(Integer pageDraw) {
		this.pageDraw = pageDraw;
	}
	
}
