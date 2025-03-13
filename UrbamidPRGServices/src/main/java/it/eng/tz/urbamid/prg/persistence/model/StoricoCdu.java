package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_prg_storico_cdu")
public class StoricoCdu implements Serializable {

	private static final long serialVersionUID = -6003844123318548486L;

	@Id 
	@SequenceGenerator(name="u_prg_storico_cdu_id_seq", sequenceName="u_prg_storico_cdu_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_storico_cdu_id_seq")
	@Column(name = "id_storico_cdu", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "foglio")
	private String foglio;

	@Column(name = "numero")
	private String numero;

	@Column(name = "data_creazione", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date dataCreazione;

	@Column(name = "protocollo")
	private String protocollo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

}
