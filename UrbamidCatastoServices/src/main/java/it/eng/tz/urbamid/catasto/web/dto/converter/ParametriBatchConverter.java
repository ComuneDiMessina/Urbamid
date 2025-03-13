package it.eng.tz.urbamid.catasto.web.dto.converter;


import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.catasto.configuration.BatchConfiguration;
import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobExecutionParam;
import it.eng.tz.urbamid.catasto.util.DateUtils;
import it.eng.tz.urbamid.catasto.web.dto.ParametriBatchDTO;

@Component
public class ParametriBatchConverter implements IConverter<Collection<BatchJobExecutionParam>, ParametriBatchDTO>{

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Collection<BatchJobExecutionParam> toModel(ParametriBatchDTO dto) throws ConverterException {
		logger.error("Operazione di conversione non supportata");
		throw new UnsupportedOperationException("Operazione di conversione non supportata");
	}

	@Override
	public ParametriBatchDTO toDto(Collection<BatchJobExecutionParam> models) throws ConverterException {
		ParametriBatchDTO parametri = new ParametriBatchDTO();
		for (BatchJobExecutionParam param : models) {
			if(param.getId() != null && StringUtils.hasText(param.getId().getKey())) {
				switch(param.getId().getKey()) {
				case BatchConfiguration.JOB_PARAMETER_UUID:
					parametri.setUuid(param.getValoreStringa());
					break;
				case BatchConfiguration.JOB_PARAMETER_DATA_AVVIO:
					parametri.setDataAvvio(DateUtils.dateToString(param.getValoreData(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS_SLASH));
					break;
				case BatchConfiguration.JOB_PARAMETER_LIVELLO_LOG:
					parametri.setLivelloLog(param.getValoreStringa());
					break;
				}
			}
		}
		return parametri;
	}

}
