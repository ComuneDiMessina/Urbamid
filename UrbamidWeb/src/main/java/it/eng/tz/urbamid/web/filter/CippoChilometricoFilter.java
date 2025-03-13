package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class CippoChilometricoFilter implements Serializable {

	private static final long serialVersionUID = -8879590564308289395L;
	
	private Long idEstesa;
	private String estesaAmministrativa;
	private String codice;
	private double misura;
	private String stato;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	private OrderFilter pageOrder;
	
	public Long getIdEstesa() {
		return idEstesa;
	}
	public void setIdEstesa(Long idEstesa) {
		this.idEstesa = idEstesa;
	}
	public String getEstesaAmministrativa() {
		return estesaAmministrativa;
	}
	public void setEstesaAmministrativa(String estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public double getMisura() {
		return misura;
	}
	public void setMisura(double misura) {
		this.misura = misura;
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
