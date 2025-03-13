package it.eng.tz.urbamid.prg.persistence.model;

import java.io.Serializable;

public class AmbitoVarianteParticella implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9106458373726996786L;
	
	private Double area_intersect;
	
	public AmbitoVarianteParticella() {
		super();
	}

	public Double getArea_intersect() {
		return area_intersect;
	}

	public void setArea_intersect(Double area_intersect) {
		this.area_intersect = area_intersect;
	}

	
}
