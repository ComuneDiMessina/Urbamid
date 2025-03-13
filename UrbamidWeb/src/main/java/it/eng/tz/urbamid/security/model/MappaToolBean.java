package it.eng.tz.urbamid.security.model;

import java.io.Serializable;

public class MappaToolBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Long id;
	private String name;
	private String title;
	
	public MappaToolBean() {
	}

	public MappaToolBean(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}