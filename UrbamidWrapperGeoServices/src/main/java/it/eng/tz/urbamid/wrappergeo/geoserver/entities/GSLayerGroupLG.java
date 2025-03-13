package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSLayerGroupLG implements Serializable {

	private static final long serialVersionUID = -6299926513017338050L;

	private String name;
	private String mode;
	private String title;
	private GSWorkspaceLG workspace;
	private GSPublishablesLG publishables;
	private GSStyles styles;
	private GSBounds bounds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public GSWorkspaceLG getWorkspace() {
		return workspace;
	}
	public void setWorkspace(GSWorkspaceLG workspace) {
		this.workspace = workspace;
	}
	public GSPublishablesLG getPublishables() {
		return publishables;
	}
	public void setPublishables(GSPublishablesLG publishables) {
		this.publishables = publishables;
	}
	public GSStyles getStyles() {
		return styles;
	}
	public void setStyles(GSStyles styles) {
		this.styles = styles;
	}
	public GSBounds getBounds() {
		return bounds;
	}
	public void setBounds(GSBounds bounds) {
		this.bounds = bounds;
	}
	
}
