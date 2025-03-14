package it.eng.tz.urbamid.prg.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Permesso.findAll", query = "select o from Permesso o") })
@Table(name = "u_pm_permesso")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class Permesso extends AbstractModel {

	private static final long serialVersionUID = -7908683641720512798L;

	@Column(name = "codice", nullable = false, length = 45)
	protected String codice;
	@Column(name = "denominazione", nullable = false, length = 45)
	protected String denominazione;
	@Column(name = "descrizione", length = 255)
	protected String descrizione;
	
	
	public Permesso() {				
	}
	
	
	public Permesso(String codice, String denominazione, String descrizione) {				
		this.codice = codice;
		this.denominazione = denominazione;
		this.descrizione = descrizione;
	}
	
	

	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
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
		Permesso other = (Permesso) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (denominazione == null) {
			if (other.denominazione != null)
				return false;
		} else if (!denominazione.equals(other.denominazione))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		return true;
	}
	
	@Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName() + "@" + Integer.toHexString(hashCode()))
              .append('[')
        	  .append("id=").append(getId())
        	  .append(",").append("codice=").append(getCodice())
        	  .append(",").append("denominazione=").append(getDenominazione())
        	  .append(",").append("descrizione=").append(getDescrizione())
              .append(']');
        return buffer.toString();
    }
}
