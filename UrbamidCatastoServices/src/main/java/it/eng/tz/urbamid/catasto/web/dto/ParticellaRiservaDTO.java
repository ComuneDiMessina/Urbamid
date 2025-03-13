package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class ParticellaRiservaDTO implements Serializable {

	private static final long serialVersionUID = -8963061948061077760L;

	private String riserva;
	private String partitaIscrizione;

	public String getRiserva() {
		return riserva;
	}
	public void setRiserva(String riserva) {
		this.riserva = riserva;
	}
	public String getPartitaIscrizione() {
		return partitaIscrizione;
	}
	public void setPartitaIscrizione(String partitaIscrizione) {
		this.partitaIscrizione = partitaIscrizione;
	}

}
