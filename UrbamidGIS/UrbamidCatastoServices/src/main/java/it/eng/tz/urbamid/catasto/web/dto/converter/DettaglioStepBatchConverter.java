package it.eng.tz.urbamid.catasto.web.dto.converter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchStepExecution;
import it.eng.tz.urbamid.catasto.util.DateUtils;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioStepBatchDTO;

@Component
public class DettaglioStepBatchConverter implements IConverter<BatchStepExecution, DettaglioStepBatchDTO> {

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public BatchStepExecution toModel(DettaglioStepBatchDTO dto) {
		logger.error("Operazione di conversione non supportata");
		throw new UnsupportedOperationException("Operazione di conversione non supportata");
	}

	@Override
	public DettaglioStepBatchDTO toDto(BatchStepExecution model) {
		DettaglioStepBatchDTO dto = new DettaglioStepBatchDTO();
		dto.setStepExecutionId(model.getStepExecutionId());
		dto.setCodiceUscita(model.getCodiceUscita());
		dto.setDataAvvio(DateUtils.dateToString(model.getDataAvvio(), DateUtils.PATTERN_YYYY_MM_DD));
		dto.setDataFine(DateUtils.dateToString(model.getDataFine(), DateUtils.PATTERN_YYYY_MM_DD));
		dto.setDataUltimoAggiornamento(DateUtils.dateToString(model.getDataUltimoAggiornamento(), DateUtils.PATTERN_YYYY_MM_DD));
		dto.setMessaggioUscita(this.getMessage(model.getCodiceUscita(), model.getMessaggioUscita()));
		dto.setNomeStep(model.getNomeStep());
		dto.setStato(model.getStato());
		return dto;
	}
	
	private String getMessage(String codiceUscita, String messaggioUscita) {
		if (ExitStatus.FAILED.getExitCode().equals(codiceUscita)) {
			String[] appo = messaggioUscita.split(System.lineSeparator());
			return appo[0];
		} else {
			return messaggioUscita;
		}
	}

}
