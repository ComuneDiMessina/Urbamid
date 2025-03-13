package it.eng.tz.urbamid.catasto.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_cat_indirizzi_unita_immobiliari")
public class IndirizzoUnitaImmobiliare implements Serializable {

	private static final long serialVersionUID = 2558429687358958786L;

	@Id 
	@SequenceGenerator(name="indirizzo_unita_immobiliare_id_seq", sequenceName="indirizzo_unita_immobiliare_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="indirizzo_unita_immobiliare_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "id_immobile")
	private Long idImmobile;

	@Column(name = "progressivo")
	private int progressivo;

	@Column(name = "indirizzo", length=50)
	private String indirizzo;

	@Column(name = "civico_1", length=6)
	private String civico1;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "civico_2", length=6)
	private String civico2;

	@Column(name = "civico_3", length=6)
	private String civico3;

	@Column(name = "civico_4", length=6)
	private String civico4;

	@Column(name = "toponimo", length=3)
	private String toponimo;

	@Column(name = "cod_strada", length=5)
	private String codStrada;

	public IndirizzoUnitaImmobiliare () { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public Long getIdImmobile() {
		return idImmobile;
	}

	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}

	public int getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico1() {
		return civico1;
	}

	public void setCivico1(String civico1) {
		this.civico1 = civico1;
	}

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

	public String getCivico2() {
		return civico2;
	}

	public void setCivico2(String civico2) {
		this.civico2 = civico2;
	}

	public String getCivico3() {
		return civico3;
	}

	public void setCivico3(String civico3) {
		this.civico3 = civico3;
	}

	public String getCivico4() {
		return civico4;
	}

	public void setCivico4(String civico4) {
		this.civico4 = civico4;
	}

	public String getToponimo() {
		return toponimo;
	}

	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
}
