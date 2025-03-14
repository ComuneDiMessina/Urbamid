package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class ToponimoFilter implements Serializable {

	private static final long serialVersionUID = 7249109958985381440L;

	private String stato;
	private String classe;
	private String denominazioneUfficiale;
	private String comune;
	private String dataDeliberaDal;
	private String dataDeliberaAl;
	private String dataAutorizzazioneDal;
	private String dataAutorizzazioneAl;
	private String numeroDelibera;
	private String codiceAutorizzazione;
	private Integer numCivicoDxDa;
	private Integer numCivicoDxA;
	private Integer numCivicoSxDa;
	private Integer numCivicoSxA; 
	private Integer pageIndex;
	private Integer pageSize;
	private Integer pageDraw;
	private OrderFilter pageOrder;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getDenominazioneUfficiale() {
		return denominazioneUfficiale;
	}
	public void setDenominazioneUfficiale(String denominazioneUfficiale) {
		this.denominazioneUfficiale = denominazioneUfficiale;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getDataDeliberaDal() {
		return dataDeliberaDal;
	}
	public void setDataDeliberaDal(String dataDeliberaDal) {
		this.dataDeliberaDal = dataDeliberaDal;
	}
	public String getDataDeliberaAl() {
		return dataDeliberaAl;
	}
	public void setDataDeliberaAl(String dataDeliberaAl) {
		this.dataDeliberaAl = dataDeliberaAl;
	}
	public String getDataAutorizzazioneDal() {
		return dataAutorizzazioneDal;
	}
	public void setDataAutorizzazioneDal(String dataAutorizzazioneDal) {
		this.dataAutorizzazioneDal = dataAutorizzazioneDal;
	}
	public String getDataAutorizzazioneAl() {
		return dataAutorizzazioneAl;
	}
	public void setDataAutorizzazioneAl(String dataAutorizzazioneAl) {
		this.dataAutorizzazioneAl = dataAutorizzazioneAl;
	}
	public String getNumeroDelibera() {
		return numeroDelibera;
	}
	public void setNumeroDelibera(String numeroDelibera) {
		this.numeroDelibera = numeroDelibera;
	}
	public String getCodiceAutorizzazione() {
		return codiceAutorizzazione;
	}
	public void setCodiceAutorizzazione(String codiceAutorizzazione) {
		this.codiceAutorizzazione = codiceAutorizzazione;
	}
	public Integer getNumCivicoDxDa() {
		return numCivicoDxDa;
	}
	public void setNumCivicoDxDa(Integer numCivicoDxDa) {
		this.numCivicoDxDa = numCivicoDxDa;
	}
	public Integer getNumCivicoDxA() {
		return numCivicoDxA;
	}
	public void setNumCivicoDxA(Integer numCivicoDxA) {
		this.numCivicoDxA = numCivicoDxA;
	}
	public Integer getNumCivicoSxDa() {
		return numCivicoSxDa;
	}
	public void setNumCivicoSxDa(Integer numCivicoSxDa) {
		this.numCivicoSxDa = numCivicoSxDa;
	}
	public Integer getNumCivicoSxA() {
		return numCivicoSxA;
	}
	public void setNumCivicoSxA(Integer numCivicoSxA) {
		this.numCivicoSxA = numCivicoSxA;
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
