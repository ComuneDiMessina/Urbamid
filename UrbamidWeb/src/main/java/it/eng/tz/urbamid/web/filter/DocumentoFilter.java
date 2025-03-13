package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class DocumentoFilter implements Serializable{

	private static final long serialVersionUID = -3129632110729980953L;
	
	private Long tipoRisorsa;
	private Long idRisorsa;
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	
	public Long getTipoRisorsa() {
		return tipoRisorsa;
	}
	public void setTipoRisorsa(Long tipoRisorsa) {
		this.tipoRisorsa = tipoRisorsa;
	}
	public Long getIdRisorsa() {
		return idRisorsa;
	}
	public void setIdRisorsa(Long idRisorsa) {
		this.idRisorsa = idRisorsa;
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
