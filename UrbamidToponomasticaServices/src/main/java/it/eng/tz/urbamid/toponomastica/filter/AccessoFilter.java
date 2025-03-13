package it.eng.tz.urbamid.toponomastica.filter;

import java.io.Serializable;

public class AccessoFilter implements Serializable {

	private static final long serialVersionUID = -8505821811082296865L;

	private String toponimo;
	private Long idToponimo;
	private Long localita;
	private Long tipo;
	private String stato;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	private OrderFilter pageOrder;
	
	public String getToponimo() {
		return toponimo;
	}
	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
	public Long getIdToponimo() {
		return idToponimo;
	}
	public void setIdToponimo(Long idToponimo) {
		this.idToponimo = idToponimo;
	}
	public Long getLocalita() {
		return localita;
	}
	public void setLocalita(Long localita) {
		this.localita = localita;
	}
	public Long getTipo() {
		return tipo;
	}
	public void setTipo(Long tipo) {
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
	public OrderFilter getPageOrder() {
		return pageOrder;
	}
	public void setPageOrder(OrderFilter pageOrder) {
		this.pageOrder = pageOrder;
	}
	
}
