package it.eng.tz.urbamid.profilemanager.persistence.model;

import java.util.Date;
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

@Entity(name = "Utente")
@Table(name = "u_pm_utente")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class Utente extends AbstractModel {

	private static final long serialVersionUID = -7908683641720512798L;
	
	
	@Column(name = "username", nullable = false, length = 45, unique = true)
	private String username;
	
	@Column(name = "cognome", length = 45)
	private String cognome;
	
	@Column(name = "nome", length = 45)
	private String nome;
	
	@Column(name = "codfiscale", length = 16)
	private String codiceFiscale;
	
	@Column(name = "email", length = 45)
	private String email;
	
	@Column(name = "data_creazione")
	private Date dataCreazione;
	
	@Column(name = "data_conferma_reg")
	private Date dataRegistrazione;
	
	@Column(name = "note", length = 45)
	private String note;
	
	@Column(name = "abilitato", columnDefinition="boolean default false")
	private boolean abilitato;
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "u_pm_utente_ruolo",
	joinColumns = { @JoinColumn(name = "id_utente") },
	inverseJoinColumns = { @JoinColumn(name = "id_ruolo") })
	private Set<Ruolo> ruoli = new HashSet<Ruolo>();
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isAbilitato() {
		return abilitato;
	}
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	
	public Set<Ruolo> getRuoli() {
		return ruoli;
	}
	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((codiceFiscale == null) ? 0 : codiceFiscale.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Utente other = (Utente) obj;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (codiceFiscale == null) {
			if (other.codiceFiscale != null)
				return false;
		} else if (!codiceFiscale.equals(other.codiceFiscale))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
