package it.eng.tz.urbamid.prg.filter;

import java.io.Serializable;
import java.util.Date;

public class VarianteFilter implements Serializable {

	private static final long serialVersionUID = 8856067891410453888L;

	private String ente;
	private String nome;
	private String descrizione;
	private Date dataAdozioneDal;
	private Date dataAdozioneAl;
	private Date dataApprovazioneDal;
	private Date dataApprovazioneAl;
	private Integer pageIndex;
	private Integer pageSize;
	private String numColonna;
	private String dir;

	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
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
	public Date getDataAdozioneDal() {
		return dataAdozioneDal;
	}
	public void setDataAdozioneDal(Date dataAdozioneDal) {
		this.dataAdozioneDal = dataAdozioneDal;
	}
	public Date getDataAdozioneAl() {
		return dataAdozioneAl;
	}
	public void setDataAdozioneAl(Date dataAdozioneAl) {
		this.dataAdozioneAl = dataAdozioneAl;
	}
	public Date getDataApprovazioneDal() {
		return dataApprovazioneDal;
	}
	public void setDataApprovazioneDal(Date dataApprovazioneDal) {
		this.dataApprovazioneDal = dataApprovazioneDal;
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
	public Date getDataApprovazioneAl() {
		return dataApprovazioneAl;
	}
	public void setDataApprovazioneAl(Date dataApprovazioneAl) {
		this.dataApprovazioneAl = dataApprovazioneAl;
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
