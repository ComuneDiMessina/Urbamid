package it.eng.tz.urbamid.administration.mappa.dao.model;

import java.io.Serializable;

public class MappaPermesso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9106458373726996786L;
	private Long id_rsrs_prms;
	private Long id_risorsa;
	private Long id_tipo_risorsa;
	private Long id_ruolo;
	private Boolean abilita_visualizzazione;
	private Boolean abilita_modifica;
	private Boolean abilita_permesso1;
	private Boolean abilita_permesso2;
	private Boolean abilita_permesso3;
	
	public MappaPermesso() {
		super();
	}
	public Long getId_rsrs_prms() {
		return id_rsrs_prms;
	}
	public void setId_rsrs_prms(Long id_rsrs_prms) {
		this.id_rsrs_prms = id_rsrs_prms;
	}
	public Long getId_risorsa() {
		return id_risorsa;
	}
	public void setId_risorsa(Long id_risorsa) {
		this.id_risorsa = id_risorsa;
	}
	public Long getId_tipo_risorsa() {
		return id_tipo_risorsa;
	}
	public void setId_tipo_risorsa(Long id_tipo_risorsa) {
		this.id_tipo_risorsa = id_tipo_risorsa;
	}
	public Long getId_ruolo() {
		return id_ruolo;
	}
	public void setId_ruolo(Long id_ruolo) {
		this.id_ruolo = id_ruolo;
	}
	public Boolean getAbilita_visualizzazione() {
		return abilita_visualizzazione;
	}
	public void setAbilita_visualizzazione(Boolean abilita_visualizzazione) {
		this.abilita_visualizzazione = abilita_visualizzazione;
	}
	public Boolean getAbilita_modifica() {
		return abilita_modifica;
	}
	public void setAbilita_modifica(Boolean abilita_modifica) {
		this.abilita_modifica = abilita_modifica;
	}
	public Boolean getAbilita_permesso1() {
		return abilita_permesso1;
	}
	public void setAbilita_permesso1(Boolean abilita_permesso1) {
		this.abilita_permesso1 = abilita_permesso1;
	}
	public Boolean getAbilita_permesso2() {
		return abilita_permesso2;
	}
	public void setAbilita_permesso2(Boolean abilita_permesso2) {
		this.abilita_permesso2 = abilita_permesso2;
	}
	public Boolean getAbilita_permesso3() {
		return abilita_permesso3;
	}
	public void setAbilita_permesso3(Boolean abilita_permesso3) {
		this.abilita_permesso3 = abilita_permesso3;
	}
	
	
}
