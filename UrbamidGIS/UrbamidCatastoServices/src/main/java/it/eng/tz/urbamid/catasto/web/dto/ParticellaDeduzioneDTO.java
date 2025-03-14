package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class ParticellaDeduzioneDTO implements Serializable {

	private static final long serialVersionUID = -282366694403405810L;

	private String simboloDeduzione;

	public String getSimboloDeduzione() {
		return simboloDeduzione;
	}

	public void setSimboloDeduzione(String simboloDeduzione) {
		this.simboloDeduzione = simboloDeduzione;
	}

}
