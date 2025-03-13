package it.eng.tz.urbamid.toponomastica.web.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ToponimoDto implements Serializable {

private static final long serialVersionUID = -3704053874125437542L;
	
	private Long id;
	private String codStrada;
	private String denom;
	private String sRef;
	private Double lat;
	private Double lon;
	
	@JsonIgnore
	private List<NumCivicoDto> numCivici;
	
	public ToponimoDto() {
		super();
		this.lat = 0.0;
		this.lon = 0.0;
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCodStrada() {
		return codStrada;
	}



	public void setCodStrada(String codStrada) {
		this.codStrada = codStrada;
	}



	public String getDenom() {
		return denom;
	}



	public void setDenom(String denom) {
		this.denom = denom;
	}



	public String getsRef() {
		return sRef;
	}



	public void setsRef(String sRef) {
		this.sRef = sRef;
	}



	public Double getLat() {
		return lat;
	}



	public void setLat(Double lat) {
		this.lat = lat;
	}



	public Double getLon() {
		return lon;
	}



	public void setLon(Double lon) {
		this.lon = lon;
	}



	public List<NumCivicoDto> getNumCivici() {
		return numCivici;
	}



	public void setNumCivici(List<NumCivicoDto> numCivici) {
		this.numCivici = numCivici;
	}



	@Override
	public String toString() {
		return String.format(
				"ToponimoDto [id=%s, codStrada=%s, denom=%s, sRef=%s, lat=%s, lon=%s]",
				id, codStrada, denom, sRef, lat, lon);
	}

}
