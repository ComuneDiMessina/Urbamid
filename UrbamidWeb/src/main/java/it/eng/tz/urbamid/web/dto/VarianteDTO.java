package it.eng.tz.urbamid.web.dto;

import java.util.ArrayList;
import java.util.List;

public class VarianteDTO {

	private Long idVariante;
	private String nome;
	private String descrizione;
	private String codComune;
	private String dataDelAdoz;
	private String numDelAdoz;
	private String orgDelAdoz;
	private String noteDelAdoz;
	private String dataDelAppr;
	private String numDelAppr;
	private String orgDelAppr;
	private String noteDelAppr;
	private String stato;

	List<StoricoVarianteDTO> listaStorico = new ArrayList<>();
	List<DocumentoVarianteDTO> listaDocumento = new ArrayList<>();

	private String username;
	private boolean ambitoInserito;
	
	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
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
	public String getCodComune() {
		return codComune;
	}
	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}
	public String getDataDelAdoz() {
		return dataDelAdoz;
	}
	public void setDataDelAdoz(String dataDelAdoz) {
		this.dataDelAdoz = dataDelAdoz;
	}
	public String getNumDelAdoz() {
		return numDelAdoz;
	}
	public void setNumDelAdoz(String numDelAdoz) {
		this.numDelAdoz = numDelAdoz;
	}
	public String getOrgDelAdoz() {
		return orgDelAdoz;
	}
	public void setOrgDelAdoz(String orgDelAdoz) {
		this.orgDelAdoz = orgDelAdoz;
	}
	public String getNoteDelAdoz() {
		return noteDelAdoz;
	}
	public void setNoteDelAdoz(String noteDelAdoz) {
		this.noteDelAdoz = noteDelAdoz;
	}
	public String getDataDelAppr() {
		return dataDelAppr;
	}
	public void setDataDelAppr(String dataDelAppr) {
		this.dataDelAppr = dataDelAppr;
	}
	public String getNumDelAppr() {
		return numDelAppr;
	}
	public void setNumDelAppr(String numDelAppr) {
		this.numDelAppr = numDelAppr;
	}
	public String getOrgDelAppr() {
		return orgDelAppr;
	}
	public void setOrgDelAppr(String orgDelAppr) {
		this.orgDelAppr = orgDelAppr;
	}
	public String getNoteDelAppr() {
		return noteDelAppr;
	}
	public void setNoteDelAppr(String noteDelAppr) {
		this.noteDelAppr = noteDelAppr;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public List<StoricoVarianteDTO> getListaStorico() {
		return listaStorico;
	}
	public void setListaStorico(List<StoricoVarianteDTO> listaStorico) {
		this.listaStorico = listaStorico;
	}
	public List<DocumentoVarianteDTO> getListaDocumento() {
		return listaDocumento;
	}
	public void setListaDocumento(List<DocumentoVarianteDTO> listaDocumento) {
		this.listaDocumento = listaDocumento;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAmbitoInserito() {
		return ambitoInserito;
	}
	public void setAmbitoInserito(boolean ambitoInserito) {
		this.ambitoInserito = ambitoInserito;
	}

}
