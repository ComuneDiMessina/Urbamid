package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;


public class MenuFunzionalitaDto implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Long id;
	private String permesso;
	private String menu;
	private Boolean locked;

	public MenuFunzionalitaDto() {
	}

	public MenuFunzionalitaDto(Long id, String permesso, String menu, Boolean locked) {
		super();
		this.id = id;
		this.permesso = permesso;
		this.menu = menu;
		this.locked = locked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermesso() {
		return permesso;
	}

	public void setPermesso(String permesso) {
		this.permesso = permesso;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	
}