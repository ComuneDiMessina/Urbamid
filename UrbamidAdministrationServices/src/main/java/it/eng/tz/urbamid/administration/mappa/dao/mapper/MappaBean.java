package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.io.Serializable;

import it.eng.tz.urbamid.administration.mappa.dao.bean.AbstractBean;

public class MappaBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Boolean isNew;
	private Long id;
	private String code;
	private String title;
	private String description;
	private String tools;
	private String search;
	private String catalog;
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