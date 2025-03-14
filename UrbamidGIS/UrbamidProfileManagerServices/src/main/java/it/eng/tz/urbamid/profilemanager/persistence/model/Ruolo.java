package it.eng.tz.urbamid.profilemanager.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "Ruolo")
@Table(name = "\"u_pm_ruolo\"")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class Ruolo extends AbstractModel {

	private static final long serialVersionUID = -7908683641720512798L;

	@Column(name = "codice", nullable = false, length = 45)
	protected String codice;
	@Column(name = "denominazione", nullable = false, length = 45)
	protected String denominazione;
	@Column(name = "descrizione", length = 255)
	protected String descrizione;
	@Column(name = "ruolo_default", columnDefinition="boolean default false")
	protected boolean ruoloDefault;
	
	@ManyToMany(mappedBy = "ruoli")
	private Set<Utente> utenti = new HashSet<>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "u_pm_ruolo_permesso",
	joinColumns = { @JoinColumn(name = "id_ruolo") },
	inverseJoinColumns = { @JoinColumn(name = "id_permesso") })
	private Set<Permesso> permessi = new HashSet<Permesso>();
	
	
	public Ruolo() {				
	}
	

	public Ruolo(String codice, String descrizione) {				
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
	public Set<Permesso> getPermessi() {
		return permessi;
	}
	public void setPermessi(Set<Permesso> permessi) {
		this.permessi = permessi;
	}
	
	
	public Set<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(Set<Utente> utenti) {
		this.utenti = utenti;
	}


	public boolean isRuoloDefault() {
		return ruoloDefault;
	}


	public void setRuoloDefault(boolean ruoloDefault) {
		this.ruoloDefault = ruoloDefault;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((permessi == null) ? 0 : permessi.hashCode());
		result = prime * result + (ruoloDefault ? 1231 : 1237);
		result = prime * result + ((utenti == null) ? 0 : utenti.hashCode());
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
		Ruolo other = (Ruolo) obj;
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
		if (permessi == null) {
			if (other.permessi != null)
				return false;
		} else if (!permessi.equals(other.permessi))
			return false;
		if (ruoloDefault != other.ruoloDefault)
			return false;
		if (utenti == null) {
			if (other.utenti != null)
				return false;
		} else if (!utenti.equals(other.utenti))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Ruolo [codice=" + codice + ", denominazione=" + denominazione + ", descrizione=" + descrizione
				+ ", ruoloDefault=" + ruoloDefault + ", utenti=" + utenti + ", permessi=" + permessi + "]";
	}
	
}
