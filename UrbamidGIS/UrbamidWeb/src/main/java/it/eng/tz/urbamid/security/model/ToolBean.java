package it.eng.tz.urbamid.security.model;

import java.io.Serializable;

public class ToolBean implements Serializable {

	private static final long serialVersionUID = 839251909773915667L;
	
	private Long id_mappa_tool;
	private Long id_mappa;
	private Long id_maptool;
	
	public ToolBean() {
		super();
	}
	public Long getId_mappa_tool() {
		return id_mappa_tool;
	}
	public void setId_mappa_tool(Long id_mappa_tool) {
		this.id_mappa_tool = id_mappa_tool;
	}
	public Long getId_mappa() {
		return id_mappa;
	}
	public void setId_mappa(Long id_mappa) {
		this.id_mappa = id_mappa;
	}
	public Long getId_maptool() {
		return id_maptool;
	}
	public void setId_maptool(Long id_maptool) {
		this.id_maptool = id_maptool;
	}	
}
