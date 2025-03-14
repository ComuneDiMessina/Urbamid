package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;
import java.util.List;

public class DettaglioBatchDTO implements Serializable {

	private static final long serialVersionUID = -8839886202400563923L;
	
	private Long jobExecutionId;
	private Long jobInstanceId;
	private String dataCreazione;
	private String dataAvvio;
	private String dataFine;
	private String stato;
	private String codiceUscita;
	private String messaggioUscita;
	private String dataUltimoAggiornamento;
	
	private List<DettaglioStepBatchDTO> listaStep;
	private ParametriBatchDTO parametri;
	
	public DettaglioBatchDTO() {
		super();
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
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

	public List<DettaglioStepBatchDTO> getListaStep() {
		return listaStep;
	}

	public void setListaStep(List<DettaglioStepBatchDTO> listaStep) {
		this.listaStep = listaStep;
	}

	public ParametriBatchDTO getParametri() {
		return parametri;
	}

	public void setParametri(ParametriBatchDTO parametri) {
		this.parametri = parametri;
	}

	@Override
	public String toString() {
		return String.format(
				"DettaglioBatchDTO [jobExecutionId=%s, jobInstanceId=%s, dataCreazione=%s, dataAvvio=%s, dataFine=%s, stato=%s, codiceUscita=%s, messaggioUscita=%s, dataUltimoAggiornamento=%s]",
				jobExecutionId, jobInstanceId, dataCreazione, dataAvvio, dataFine, stato, codiceUscita, messaggioUscita,
				dataUltimoAggiornamento);
	}

}