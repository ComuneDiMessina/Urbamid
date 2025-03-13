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
@Table(name="u_cat_planimetrie_image")
public class PlanimetriaImage implements Serializable {

	private static final long serialVersionUID = -5858114647172664412L;

	@Id 
	@SequenceGenerator(name="u_cat_planimetrie_id_seq", sequenceName="u_cat_planimetrie_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_cat_planimetrie_id_seq")
	@Column(name = "id", unique = true, nullable = false, updatable=false)
	private Long id;

	@Column(name = "cod_comune", length=4)
	private String codComune;

	@Column(name = "id_pla_immagine_1", length=1)
	private String idPlaImmagine1;

	@Column(name = "id_pla_immagine_2", length=2)
	private String idPlaImmagine2;

	@Column(name = "id_pla_immagine_3", length=1)
	private String idPlaImmagine3;

	@Column(name = "nome_image", length=6)
	private String nomeImage;

	@Column(name = "scale_from", length=3)
	private String scaleFrom;

	@Column(name = "scale_to", length=1)
	private String scaleTo;

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

	public String getIdPlaImmagine1() {
		return idPlaImmagine1;
	}

	public void setIdPlaImmagine1(String idPlaImmagine1) {
		this.idPlaImmagine1 = idPlaImmagine1;
	}

	public String getIdPlaImmagine2() {
		return idPlaImmagine2;
	}

	public void setIdPlaImmagine2(String idPlaImmagine2) {
		this.idPlaImmagine2 = idPlaImmagine2;
	}

	public String getIdPlaImmagine3() {
		return idPlaImmagine3;
	}

	public void setIdPlaImmagine3(String idPlaImmagine3) {
		this.idPlaImmagine3 = idPlaImmagine3;
	}

	public String getNomeImage() {
		return nomeImage;
	}

	public void setNomeImage(String nomeImage) {
		this.nomeImage = nomeImage;
	}

	public String getScaleFrom() {
		return scaleFrom;
	}

	public void setScaleFrom(String scaleFrom) {
		this.scaleFrom = scaleFrom;
	}

	public String getScaleTo() {
		return scaleTo;
	}

	public void setScaleTo(String scaleTo) {
		this.scaleTo = scaleTo;
	}

	
}
