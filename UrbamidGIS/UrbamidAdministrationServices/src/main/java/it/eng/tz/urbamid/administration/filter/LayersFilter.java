package it.eng.tz.urbamid.administration.filter;

import java.io.Serializable;

public class LayersFilter implements Serializable {

	private static final long serialVersionUID = 8360839834636574505L;
	
	private String nome;
	private String descrizione;
	private String stato;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	private OrderFilter pageOrder;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
	public OrderFilter getPageOrder() {
		return pageOrder;
	}
	public void setPageOrder(OrderFilter pageOrder) {
		this.pageOrder = pageOrder;
	}
	
}
