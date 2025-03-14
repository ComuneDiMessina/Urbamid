package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSWorkspaceLG implements Serializable {

	private static final long serialVersionUID = 642210949455914304L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
