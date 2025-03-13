package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSAttributes implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<GSAttribute> attribute;
	
	public GSAttributes() {
	}

	public List<GSAttribute> getAttribute() {
		return attribute;
	}

	public void setAttribute(List<GSAttribute> attribute) {
		this.attribute = attribute;
	}

}