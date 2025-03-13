package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSPublishablesLG implements Serializable {

	private static final long serialVersionUID = 6849125317317229847L;

	private GSPublished published;

	public GSPublished getPublished() {
		return published;
	}

	public void setPublished(GSPublished published) {
		this.published = published;
	}
	
}
