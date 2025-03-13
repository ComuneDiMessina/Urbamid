package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_prg_storico_variante")
public class StoricoVariante implements Serializable {

	private static final long serialVersionUID = 4630194466064379404L;

	@Id 
	@SequenceGenerator(name="u_prg_storico_variante_id_seq", sequenceName="u_prg_storico_variante_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_storico_variante_id_seq")
	@Column(name = "id_storico", unique = true, nullable = false, updatable=false)
	private Long idStorico;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_variante", nullable=false, updatable=false )
	private Variante variante;

	@Column(name = "azione", length=20, nullable = false)
	private String azione;

	@Column(name = "data_azione", nullable = false)
	private Timestamp dataAzione;

	@Column(name = "descrizione_azione", length=200)
	private String descrizioneAzione;

	@Column(name = "note_azione", length=500)
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

	public Timestamp getDataAzione() {
		return dataAzione;
	}

	public void setDataAzione(Timestamp dataAzione) {
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
