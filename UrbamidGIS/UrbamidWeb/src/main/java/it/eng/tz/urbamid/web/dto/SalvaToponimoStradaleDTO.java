package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;


public class SalvaToponimoStradaleDTO implements Serializable {
	
	private static final long serialVersionUID = 2434638278853060273L;
	
	private ToponimoStradaleDTO toponimoStradale;
	private Boolean publish;
	public ToponimoStradaleDTO getToponimoStradale() {
		return toponimoStradale;
	}
	public void setToponimoStradale(ToponimoStradaleDTO toponimoStradalse) {
		this.toponimoStradale = toponimoStradalse;
	}
	public Boolean getPublish() {
		return publish;
	}
	public void setPublish(Boolean publish) {
		this.publish = publish;
	}
	
}
