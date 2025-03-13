package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "u_prg_tipo_documento")
public class TipoDocumento implements Serializable {


	private static final long serialVersionUID = -4057190550521575163L;

	@Id
	@SequenceGenerator(name = "u_prg_documento_id_seq", sequenceName = "u_prg_documento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_prg_documento_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "codice", length = 250, nullable = false, updatable = false)
	private String codice;
	
	@Column(name = "descrizione", length = 2000, nullable = false)
	private String descrizione;
	
	@Column(name = "descrizione_cdu", length = 2000, nullable = true)
	private String descrizioneCDU;
	
	@Column(name = "note", length = 2000, nullable = true)
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneCDU() {
		return descrizioneCDU;
	}

	public void setDescrizioneCDU(String descrizioneCDU) {
		this.descrizioneCDU = descrizioneCDU;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
