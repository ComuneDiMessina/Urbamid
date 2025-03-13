package it.eng.tz.urbamid.toponomastica.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="u_cat_comuni")
public class Comuni implements Serializable {

	private static final long serialVersionUID = -5569768343744951888L;

	@Id
	@Column(name = "id_comune", length=5)
	private Long idComune;

	@Column(name = "sigla_provincia", length=2)
	private String siglaProvincia;

	@Column(name = "codice_istat", length=6)
	private String codiceIstat;

	@Column(name = "codice_mf", length=4)
	private String codiceMf;

	@Column(name = "denominazione", length=50)
	private String denominazione;

	@Column(name = "vl_inizio")
	@Temporal(value = TemporalType.DATE)
	private Date vlInizio;

	@Column(name = "vl_fine")
	@Temporal(value = TemporalType.DATE)
	private Date vlFine;

	@Column(name = "du_cat_agg")
	@Temporal(value = TemporalType.DATE)
	private Date duCatAgg;

	@Column(name = "id_provincia")
	private Long idProvincia;

	@OneToMany(mappedBy = "comune", fetch=FetchType.LAZY)
	private Set<Localita> localita;
	
	@OneToMany(mappedBy = "comune", fetch = FetchType.LAZY)
	private Set<ToponimoStradale> toponimo; 
	
	@OneToMany(mappedBy = "comune", fetch=FetchType.LAZY)
	private Set<EstesaAmministrativa> estesaAmministrativa;
	
	public Comuni () { }

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public String getCodiceIstat() {
		return codiceIstat;
	}

	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}

	public String getCodiceMf() {
		return codiceMf;
	}

	public void setCodiceMf(String codiceMf) {
		this.codiceMf = codiceMf;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getVlInizio() {
		return vlInizio;
	}

	public void setVlInizio(Date vlInizio) {
		this.vlInizio = vlInizio;
	}

	public Date getVlFine() {
		return vlFine;
	}

	public void setVlFine(Date vlFine) {
		this.vlFine = vlFine;
	}

	public Date getDuCatAgg() {
		return duCatAgg;
	}

	public void setDuCatAgg(Date duCatAgg) {
		this.duCatAgg = duCatAgg;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Set<Localita> getLocalita() {
		return localita;
	}

	public void setLocalita(Set<Localita> localita) {
		this.localita = localita;
	}

	public Set<ToponimoStradale> getToponimo() {
		return toponimo;
	}

	public void setToponimo(Set<ToponimoStradale> toponimo) {
		this.toponimo = toponimo;
	}

	public Set<EstesaAmministrativa> getEstesaAmministrativa() {
		return estesaAmministrativa;
	}

	public void setEstesaAmministrativa(Set<EstesaAmministrativa> estesaAmministrativa) {
		this.estesaAmministrativa = estesaAmministrativa;
	}

}
