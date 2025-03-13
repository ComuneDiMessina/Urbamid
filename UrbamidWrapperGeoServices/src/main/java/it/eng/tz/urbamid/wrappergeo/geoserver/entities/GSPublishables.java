package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSPublishables implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<GSPublished> published;
	
	public List<GSPublished> getPublished() {
		return published;
	}

	public void setPublished(List<GSPublished> published) {
		this.published = published;
	}
}