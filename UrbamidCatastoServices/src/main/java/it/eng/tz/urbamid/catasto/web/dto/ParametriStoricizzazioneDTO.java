package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class ParametriStoricizzazioneDTO implements Serializable {
	
	private static final long serialVersionUID = 4377292386918706538L;

	@NotNull
	private Date dataInizioValidita;
	
	@NotNull
	private Date dataFineValidita;

	public ParametriStoricizzazioneDTO() {
		super();
	}
	
	public ParametriStoricizzazioneDTO(Date dataInizioValidita, Date dataFineValidita) {
		super();
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	@Override
	public String toString() {
		return String.format("ParametriStoricizzazioneDTO [dataInizioValidita=%s, dataFineValidita=%s]",
				dataInizioValidita, dataFineValidita);
	}
	
}
