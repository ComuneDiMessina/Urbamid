package it.eng.tz.urbamid.profilemanager.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "RuoloPermesso")
@Table(name = "\"u_pm_ruolo_permesso\"")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class RuoloPermesso extends AbstractModel {

	private static final long serialVersionUID = -7908683641720512798L;

	@Column(name = "id_ruolo", nullable = false)
	protected Long idRuolo;
	@Column(name = "id_permesso", nullable = false)
	protected Long idPermesso;
	
	public RuoloPermesso() {				
	}

	public RuoloPermesso(Long idRuolo, Long idPermesso) {
		super();
		this.idRuolo = idRuolo;
		this.idPermesso = idPermesso;
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public Long getIdPermesso() {
		return idPermesso;
	}

	public void setIdPermesso(Long idPermesso) {
		this.idPermesso = idPermesso;
	}
	
}
