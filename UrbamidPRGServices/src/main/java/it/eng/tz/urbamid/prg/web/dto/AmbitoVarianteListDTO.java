package it.eng.tz.urbamid.prg.web.dto;

import java.util.List;

public class AmbitoVarianteListDTO {

	private Long idVariante;
	private List<String> wktGeom;

	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}
	public List<String> getWktGeom() {
		return wktGeom;
	}
	public void setWktGeom(List<String> wktGeom) {
		this.wktGeom = wktGeom;
	}

}
