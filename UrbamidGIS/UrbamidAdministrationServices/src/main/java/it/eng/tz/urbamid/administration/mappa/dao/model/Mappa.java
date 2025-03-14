package it.eng.tz.urbamid.administration.mappa.dao.model;

public class Mappa implements java.io.Serializable {

	private static final long serialVersionUID = 3467989111284292050L;

	private Long id;
	private String code;
	private String title;
	private String description;
	private String catalog;
	private Boolean showCatalog;
	private Integer zoom;

	public Mappa() {
	}

	public Mappa(Long id) {
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

	public Boolean getShowCatalog() {
		return showCatalog;
	}

	public void setShowCatalog(Boolean showCatalog) {
		this.showCatalog = showCatalog;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
}
