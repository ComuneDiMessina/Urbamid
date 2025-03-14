package it.eng.tz.urbamid.administration.mappa.dao.model;

public class PermessoLayer {

	private long id_layer;
	private String nome_layer;
	private long id_ruolo;
	private boolean abilita_visualizzazione;
	private boolean abilita_modifica;
	
	public long getId_layer() {
		return id_layer;
	}
	public void setId_layer(long id_layer) {
		this.id_layer = id_layer;
	}
	public String getNome_layer() {
		return nome_layer;
	}
	public void setNome_layer(String nome_layer) {
		this.nome_layer = nome_layer;
	}
	public long getId_ruolo() {
		return id_ruolo;
	}
	public void setId_ruolo(long id_ruolo) {
		this.id_ruolo = id_ruolo;
	}
	public boolean isAbilita_visualizzazione() {
		return abilita_visualizzazione;
	}
	public void setAbilita_visualizzazione(boolean abilita_visualizzazione) {
		this.abilita_visualizzazione = abilita_visualizzazione;
	}
	public boolean isAbilita_modifica() {
		return abilita_modifica;
	}
	public void setAbilita_modifica(boolean abilita_modifica) {
		this.abilita_modifica = abilita_modifica;
	}
	
	
	
}
