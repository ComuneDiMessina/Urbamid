package it.eng.tz.urbamid.administration.mappa.dao.model;

import java.io.Serializable;

public class Ricerca implements Serializable {

	private static final long serialVersionUID = 7871359323377459766L;
	
	private Long id_mappa_ricerca;
	private Long id_mappa;
	private Long id_mapricerca;
	
	public Ricerca() {
		super();
	}

	public Long getId_mappa_ricerca() {
		return id_mappa_ricerca;
	}

	public void setId_mappa_ricerca(Long id_mappa_ricerca) {
		this.id_mappa_ricerca = id_mappa_ricerca;
	}

	public Long getId_mappa() {
		return id_mappa;
	}

	public void setId_mappa(Long id_mappa) {
		this.id_mappa = id_mappa;
	}

	public Long getId_mapricerca() {
		return id_mapricerca;
	}

	public void setId_mapricerca(Long id_mapricerca) {
		this.id_mapricerca = id_mapricerca;
	}
	
}
