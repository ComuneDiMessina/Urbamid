package it.eng.tz.urbamid.prg.web.dto;

import it.eng.tz.urbamid.prg.persistence.model.Variante;

public class StoricoVarianteDTO {

	private Long idStorico;
	private Variante variante;
	private String azione;
	private String dataAzione;
	private String descrizioneAzione;
	private String noteAzione;

	public Long getIdStorico() {
		return idStorico;
	}
	public void setIdStorico(Long idStorico) {
		this.idStorico = idStorico;
	}
	public Variante getVariante() {
		return variante;
	}
	public void setVariante(Variante variante) {
		this.variante = variante;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getDataAzione() {
		return dataAzione;
	}
	public void setDataAzione(String dataAzione) {
		this.dataAzione = dataAzione;
	}
	public String getDescrizioneAzione() {
		return descrizioneAzione;
	}
	public void setDescrizioneAzione(String descrizioneAzione) {
		this.descrizioneAzione = descrizioneAzione;
	}
	public String getNoteAzione() {
		return noteAzione;
	}
	public void setNoteAzione(String noteAzione) {
		this.noteAzione = noteAzione;
	}

}
