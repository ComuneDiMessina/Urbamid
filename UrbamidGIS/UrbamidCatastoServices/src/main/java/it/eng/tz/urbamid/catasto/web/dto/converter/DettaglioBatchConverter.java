package it.eng.tz.urbamid.catasto.web.dto.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobExecution;
import it.eng.tz.urbamid.catasto.util.DateUtils;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioBatchDTO;

@Component
public class DettaglioBatchConverter implements IConverter<BatchJobExecution, DettaglioBatchDTO>{

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public BatchJobExecution toModel(DettaglioBatchDTO dto) throws ConverterException {
		logger.error("Operazione di conversione non supportata");
		throw new UnsupportedOperationException("Operazione di conversione non supportata");
	}

	@Override
	public DettaglioBatchDTO toDto(BatchJobExecution model) throws ConverterException {
		DettaglioBatchDTO dto = new DettaglioBatchDTO();
		dto.setJobExecutionId( model.getJobExecutionId() != null ? model.getJobExecutionId() : null );
		dto.setJobInstanceId( model.getJobInstance() != null ? model.getJobInstance().getJobInstanceId() : null );
		dto.setDataAvvio( DateUtils.dateToString(model.getDataAvvio(), DateUtils.PATTERN_YYYY_MM_DD) );
		dto.setDataCreazione( DateUtils.dateToString(model.getDataCreazione(), DateUtils.PATTERN_YYYY_MM_DD));
		dto.setDataFine( DateUtils.dateToString(model.getDataFine(), DateUtils.PATTERN_YYYY_MM_DD));
		dto.setDataUltimoAggiornamento( DateUtils.dateToString(model.getDataUltimoAggiornamento(), DateUtils.PATTERN_YYYY_MM_DD));
		dto.setStato( model.getStato() );
		dto.setCodiceUscita( model.getCodiceUscita() );
		dto.setMessaggioUscita(this.getMessage(model.getCodiceUscita(), model.getMessaggioUscita()));
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
