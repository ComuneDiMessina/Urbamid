package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSStyles implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<GSStyleShort> style;
	
	public GSStyles() {
	}

	public List<GSStyleShort> getStyle() {
		return style;
	}

	public void setStyle(List<GSStyleShort> style) {
		this.style = style;
	}

}