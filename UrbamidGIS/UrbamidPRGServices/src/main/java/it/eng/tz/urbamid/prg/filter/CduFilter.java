package it.eng.tz.urbamid.prg.filter;

import java.io.Serializable;
import java.util.Date;

public class CduFilter implements Serializable {

	private static final long serialVersionUID = -3626545384923764119L;

	private String protocollo;
	private Date dataCreazione;
	private Integer pageIndex;
	private Integer pageSize;
	private String numColonna;
	private String dir;

	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
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
	public String getNumColonna() {
		return numColonna;
	}
	public void setNumColonna(String numColonna) {
		this.numColonna = numColonna;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

}
