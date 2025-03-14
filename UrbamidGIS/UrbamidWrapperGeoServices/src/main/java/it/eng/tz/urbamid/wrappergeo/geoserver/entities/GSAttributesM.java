package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSAttributesM implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<GSAttributeM> attribute;
	
	public GSAttributesM() {
	}

	public List<GSAttributeM> getAttribute() {
		return attribute;
	}

	public void setAttribute(List<GSAttributeM> attribute) {
		this.attribute = attribute;
	}

}