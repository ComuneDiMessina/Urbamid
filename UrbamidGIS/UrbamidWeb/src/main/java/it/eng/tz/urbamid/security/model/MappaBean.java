package it.eng.tz.urbamid.security.model;

import java.io.Serializable;

public class MappaBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Boolean isNew;
	private Long id;
	private String code;
	private String title;
	private String description;
	private String catalog;
	private String layer;
	private Boolean showCatalog;
	private Integer zoom;
	
	public MappaBean() {
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	public MappaBean(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	public Boolean getShowCatalog() {
		return showCatalog;
	}

	public void setShowCatalog(Boolean showCatalog) {
		this.showCatalog = showCatalog;
	}
}