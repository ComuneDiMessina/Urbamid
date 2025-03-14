package it.eng.tz.urbamid.catasto.web.dto;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Modello per i dati del dettaglio di uno degli step dell'esecuzione di un batch job.", value="DettaglioStepBatch")
public class DettaglioStepBatchDTO implements Serializable {

	private static final long serialVersionUID = 2158807913490740213L;
	
	private Long stepExecutionId;
	private String nomeStep;
	private String dataAvvio;
	private String dataFine;
	private String stato;
	private String codiceUscita;
	private String messaggioUscita;
	private String dataUltimoAggiornamento;
	
	public DettaglioStepBatchDTO() {
		super();
	}

	public Long getStepExecutionId() {
		return stepExecutionId;
	}

	public void setStepExecutionId(Long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public String getNomeStep() {
		return nomeStep;
	}

	public void setNomeStep(String nomeStep) {
		this.nomeStep = nomeStep;
	}

	public String getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(String dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getCodiceUscita() {
		return codiceUscita;
	}

	public void setCodiceUscita(String codiceUscita) {
		this.codiceUscita = codiceUscita;
	}

	public String getMessaggioUscita() {
		return messaggioUscita;
	}

	public void setMessaggioUscita(String messaggioUscita) {
		this.messaggioUscita = messaggioUscita;
	}

	public String getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(String dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	@Override
	public String toString() {
		return String.format(
				"DettaglioStepBatchDTO [stepExecutionId=%s, nomeStep=%s, dataAvvio=%s, dataFine=%s, stato=%s, codiceUscita=%s, messaggioUscita=%s, dataUltimoAggiornamento=%s]",
				stepExecutionId, nomeStep, dataAvvio, dataFine, stato, codiceUscita, messaggioUscita,
				dataUltimoAggiornamento);
	}
	
}
