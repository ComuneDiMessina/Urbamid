package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;
import java.util.List;

public class GSResponseSRS implements Serializable{

	private static final long serialVersionUID = -2802118518706684480L;

	private List<String> string;
	
	public GSResponseSRS() {
	}

	public List<String> getString() {
		return string;
	}

	public void setString(List<String> string) {
		this.string = string;
	}
}