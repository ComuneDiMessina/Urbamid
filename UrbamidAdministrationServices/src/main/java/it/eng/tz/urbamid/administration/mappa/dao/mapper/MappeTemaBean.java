package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.urbamid.administration.mappa.dao.bean.AbstractBean;

public class MappeTemaBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = -426941643431805185L;

	private Boolean isNew;
	private Long id;
	private String code;
	private String title;
	private String description;
	private String tools;
	private String search;
	private String catalog;
	private String layer;
	
	private List<TemaBean> temiAssociati;
	
	public MappeTemaBean() {
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	public MappeTemaBean(Long id) {
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

	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public List<TemaBean> getTemiAssociati() {
		return temiAssociati;
	}

	public void setTemiAssociati(List<TemaBean> temiAssociati) {
		this.temiAssociati = temiAssociati;
	}
}