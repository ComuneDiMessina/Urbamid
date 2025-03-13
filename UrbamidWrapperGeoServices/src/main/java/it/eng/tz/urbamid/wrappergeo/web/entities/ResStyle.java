package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyle;

public class ResStyle implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSStyle style;

	public ResStyle() {
	}

	public GSStyle getStyle() {
		return style;
	}

	public void setStyle(GSStyle style) {
		this.style = style;
	}
	
}