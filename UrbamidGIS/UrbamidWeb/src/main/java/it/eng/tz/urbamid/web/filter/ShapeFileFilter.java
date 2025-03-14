package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class ShapeFileFilter implements Serializable {
	
	private static final long serialVersionUID = -8785012143303224383L;
	
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	private OrderFilter pageOrder;
	
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
