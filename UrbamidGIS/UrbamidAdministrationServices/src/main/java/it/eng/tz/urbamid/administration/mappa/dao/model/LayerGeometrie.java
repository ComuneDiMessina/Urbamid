package it.eng.tz.urbamid.administration.mappa.dao.model;

import java.io.Serializable;

public class LayerGeometrie implements Serializable {

	private static final long serialVersionUID = 1723003627638786072L;
	
	private Long id;
	private Long idLayer;
	private String geom;
	private String nome;
	private String descrizione;
	private String tipo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdLayer() {
		return idLayer;
	}
	public void setIdLayer(Long idLayer) {
		this.idLayer = idLayer;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
