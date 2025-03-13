package it.eng.tz.urbamid.administration.persistence.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "u_admin_menu_funzionalita")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class MenuFunzionalita extends AbstractModel {

	private static final long serialVersionUID = -426941643431805185L;

	@Column(name = "permesso", nullable = false, length = 50)
	private String permesso;
	
	@Column(name = "menu", nullable = false, length = 2048)
	private String menu;
	
	@Column(name = "locked", nullable = false)
	private Boolean locked;

	public MenuFunzionalita() {
	}

	public MenuFunzionalita(String permesso, String menu, Boolean locked ) {
		super();
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
