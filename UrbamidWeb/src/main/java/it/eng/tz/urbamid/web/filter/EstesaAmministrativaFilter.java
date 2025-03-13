package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class EstesaAmministrativaFilter implements Serializable {

	private static final long serialVersionUID = 6931944388017995310L;
	
	private String sigla;
	private String descrizione;
	private String codice;
	private String stato;
	private Long classificaAmministrativa;
	private Long classificaFunzionale;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	private OrderFilter pageOrder;
	
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
	public Long getClassificaAmministrativa() {
		return classificaAmministrativa;
	}
	public void setClassificaAmministrativa(Long classificaAmministrativa) {
		this.classificaAmministrativa = classificaAmministrativa;
	}
	public Long getClassificaFunzionale() {
		return classificaFunzionale;
	}
	public void setClassificaFunzionale(Long classificaFunzionale) {
		this.classificaFunzionale = classificaFunzionale;
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
