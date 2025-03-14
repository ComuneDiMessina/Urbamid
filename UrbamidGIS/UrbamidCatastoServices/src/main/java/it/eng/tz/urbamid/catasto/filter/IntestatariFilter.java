package it.eng.tz.urbamid.catasto.filter;

import java.io.Serializable;

public class IntestatariFilter implements Serializable {

	private static final long serialVersionUID = 1183462240776193575L;

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private boolean checkboxUiuPf;
	private boolean checkboxPtPf;

	private String denominazione;
	private String comune;
	private String provincia;
	private boolean checkboxUiuSg;
	private boolean checkboxPtSg;
	
	private Integer pageIndex;
	private Integer pageSize;

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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public boolean isCheckboxUiuPf() {
		return checkboxUiuPf;
	}
	public void setCheckboxUiuPf(boolean checkboxUiuPf) {
		this.checkboxUiuPf = checkboxUiuPf;
	}
	public boolean isCheckboxPtPf() {
		return checkboxPtPf;
	}
	public void setCheckboxPtPf(boolean checkboxPtPf) {
		this.checkboxPtPf = checkboxPtPf;
	}
	public boolean isCheckboxPtSg() {
		return checkboxPtSg;
	}
	public void setCheckboxPtSg(boolean checkboxPtSg) {
		this.checkboxPtSg = checkboxPtSg;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public boolean isCheckboxUiuSg() {
		return checkboxUiuSg;
	}
	public void setCheckboxUiuSg(boolean checkboxUiuSg) {
		this.checkboxUiuSg = checkboxUiuSg;
	}
}
