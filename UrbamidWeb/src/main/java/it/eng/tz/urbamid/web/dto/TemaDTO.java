package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class TemaDTO  implements Serializable {

	private static final long serialVersionUID = 6909410779348653080L;
	private Long id;
	private Long idPadre;
	private String nome;
	private String descrizione;
	private Long ordinamento;
	private String dataCreazione;
	public TemaDTO() {
		 
	}
	
	
	public TemaDTO(Long id, String nome, String descrizione, Long ordinamento) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.ordinamento = ordinamento;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
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
	public Long getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}
	
	public String getDataCreazione() {
		return dataCreazione;
	}
	
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
}
