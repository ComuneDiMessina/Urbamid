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
@Table(name="u_cat_porzioni_particella")
public class PorzioneParticella implements Serializable {

	private static final long serialVersionUID = 2115921940309400601L;

	@Id 
	@SequenceGenerator(name="porzione_particella_id_seq", sequenceName="porzione_particella_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="porzione_particella_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "sezione", length=1)
	private String sezione;

	@Column(name = "tipo_immobile", length=1)
	private String tipoImmobile;

	@Column(name = "progressivo", length=3)
	private String progressivo;

	@Column(name = "identificativo_porzione", length=2)
	private String identificativoPorzione;

	@Column(name = "classe", length=2)
	private String classe;

	@Column(name = "id_immobile")
	private Long idImmobile;

	@Column(name = "qualita")
	private Integer qualita;

	@Column(name = "reddito_dominicale_euro", length=20)
	private String redditoDominicaleEuro;

	@Column(name = "reddito_agrario_euro", length=20)
	private String redditoAgrarioEuro;

	@Column(name = "ettari")
	private Integer ettari;

	@Column(name = "are")
	private Integer are;

	@Column(name = "centiare")
	private Integer centiare;

	public PorzioneParticella () { }

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

	public String getTipoImmobile() {
		return tipoImmobile;
	}

	public void setTipoImmobile(String tipoImmobile) {
		this.tipoImmobile = tipoImmobile;
	}

	public String getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}

	public String getIdentificativoPorzione() {
		return identificativoPorzione;
	}

	public void setIdentificativoPorzione(String identificativoPorzione) {
		this.identificativoPorzione = identificativoPorzione;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Long getIdImmobile() {
		return idImmobile;
	}

	public void setIdImmobile(Long idImmobile) {
		this.idImmobile = idImmobile;
	}

	public int getQualita() {
		return qualita;
	}

	public void setQualita(int qualita) {
		this.qualita = qualita;
	}

	public String getRedditoDominicaleEuro() {
		return redditoDominicaleEuro;
	}

	public void setRedditoDominicaleEuro(String redditoDominicaleEuro) {
		this.redditoDominicaleEuro = redditoDominicaleEuro;
	}

	public String getRedditoAgrarioEuro() {
		return redditoAgrarioEuro;
	}

	public void setRedditoAgrarioEuro(String redditoAgrarioEuro) {
		this.redditoAgrarioEuro = redditoAgrarioEuro;
	}

	public int getEttari() {
		return ettari;
	}

	public void setEttari(int ettari) {
		this.ettari = ettari;
	}

	public int getAre() {
		return are;
	}

	public void setAre(int are) {
		this.are = are;
	}

	public Integer getCentiare() {
		return centiare;
	}

	public void setCentiare(Integer centiare) {
		this.centiare = centiare;
	}

}
