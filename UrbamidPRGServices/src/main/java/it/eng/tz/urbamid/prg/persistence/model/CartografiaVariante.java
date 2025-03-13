package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="u_prg_cartografia_variante")
public class CartografiaVariante implements Serializable {

	private static final long serialVersionUID = 8224125637119609698L;

	@Id 
	@SequenceGenerator(name="u_prg_cartografia_variante_id_seq", sequenceName="u_prg_cartografia_variante_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="u_prg_cartografia_variante_id_seq")
	@Column(name = "id_cartografia", unique = true, nullable = false, updatable=false)
	private Long idCartografia;

	@ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="id_variante", nullable=false, updatable=false )
	private Variante variante;

	@Column(name = "nome_layer", nullable = false, length=1000)
	private String nomeLayer;

	@Column(name = "descrizione_layer", nullable = false, length=1000)
	private String descrizioneLayer;
	
	@Column(name = "gruppo_layer", nullable = false, length = 250)
	private String gruppoLayer;

	@Column(name = "sorgente", nullable = false, length = 250)
	private String sorgente;

	@Column(name = "nome_colonna", nullable = false, length = 100)
	private String nomeColonna;

	public String getDescrizioneLayer() {
		return descrizioneLayer;
	}

	public void setDescrizioneLayer(String descrizioneLayer) {
		this.descrizioneLayer = descrizioneLayer;
	}

	public Long getIdCartografia() {
		return idCartografia;
	}

	public void setIdCartografia(Long idCartografia) {
		this.idCartografia = idCartografia;
	}

	public Variante getVariante() {
		return variante;
	}

	public void setVariante(Variante variante) {
		this.variante = variante;
	}

	public String getNomeLayer() {
		return nomeLayer;
	}

	public void setNomeLayer(String nomeLayer) {
		this.nomeLayer = nomeLayer;
	}

	public String getGruppoLayer() {
		return gruppoLayer;
	}

	public void setGruppoLayer(String gruppoLayer) {
		this.gruppoLayer = gruppoLayer;
	}

	public String getSorgente() {
		return sorgente;
	}

	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}
	public String getNomeColonna() {
		return nomeColonna;
	}

	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}

}
