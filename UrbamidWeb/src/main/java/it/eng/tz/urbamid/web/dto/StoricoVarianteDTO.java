package it.eng.tz.urbamid.web.dto;

public class StoricoVarianteDTO {

	private Long idStorico;
	private VarianteDTO variante;
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
	public VarianteDTO getVariante() {
		return variante;
	}
	public void setVariante(VarianteDTO variante) {
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
