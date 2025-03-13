package it.eng.tz.urbamid.profilemanager.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "RuoloUtente")
@Table(name = "\"u_pm_utente_ruolo\"")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class RuoloUtente extends AbstractModel {

	private static final long serialVersionUID = -7908683641720512798L;

	@Column(name = "id_ruolo", nullable = false)
	protected Long idRuolo;
	@Column(name = "id_utente", nullable = false)
	protected Long idUtente;
	
	public RuoloUtente() {
		super();
	}

	public RuoloUtente(Long idRuolo, Long idUtente) {
		super();
		this.idRuolo = idRuolo;
		this.idUtente = idUtente;
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idRuolo == null) ? 0 : idRuolo.hashCode());
		result = prime * result + ((idUtente == null) ? 0 : idUtente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuoloUtente other = (RuoloUtente) obj;
		if (idRuolo == null) {
			if (other.idRuolo != null)
				return false;
		} else if (!idRuolo.equals(other.idRuolo))
			return false;
		if (idUtente == null) {
			if (other.idUtente != null)
				return false;
		} else if (!idUtente.equals(other.idUtente))
			return false;
		return true;
	}

	
}
