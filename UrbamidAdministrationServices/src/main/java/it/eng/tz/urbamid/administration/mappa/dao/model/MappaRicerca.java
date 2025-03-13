package it.eng.tz.urbamid.administration.mappa.dao.model;

public class MappaRicerca implements java.io.Serializable {

	private static final long serialVersionUID = 3467989111284292050L;

	private Long id;
	private String name;
	private String title;
	private String idHandle;

	public MappaRicerca() {
	}

	public MappaRicerca(Long id) {
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

	public String getIdHandle() {
		return idHandle;
	}

	public void setIdHandle(String idHandle) {
		this.idHandle = idHandle;
	}
}
