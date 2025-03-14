package it.eng.tz.urbamid.wrappergeo.web.entities;

import java.io.Serializable;

import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyles;

public class ResStyles implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private GSStyles styles;

	public ResStyles() {
	}

	public GSStyles getStyles() {
		return styles;
	}

	public void setStyles(GSStyles styles) {
		this.styles = styles;
	}
	
}