package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.util.StringUtils;
import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.util.DateUtils;
import it.eng.tz.urbamid.catasto.util.IConstants;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioFileDTO;

@Component
public class DettaglioFileConverter implements IConverter<File, DettaglioFileDTO>{

	private static final String CODICE_BELFIORE_PATTERN = "[a-zA-Z]{1}[0-9]{3}_.*\\.zip$";
	
	@Override
	public File toModel(DettaglioFileDTO dettaglio) throws ConverterException {
		throw new UnsupportedOperationException("Operazione di conversione da <<DettaglioFileDTO>> a <<File>> non supportata");
	}

	@Override
	public DettaglioFileDTO toDto(File file) throws ConverterException {
		DettaglioFileDTO dettaglio = new DettaglioFileDTO();
		if(file.length() > 0) {
			dettaglio.setDimensione(file.length() / 1024);
		}
		dettaglio.setNome(file.getName());
		dettaglio.setDataCreazione(
				DateUtils.dateToString(
						new Date(file.lastModified()), 
						DateUtils.PATTERN_DD_MM_YYYY_SLASH));
		if(iniziaConCodiceBelfiore(file.getName())) {
			dettaglio.setCodiceComune(file.getName().substring(0, 4));
		}
		dettaglio.setTipo(IConstants.TIPO_APPLICATION_ZIP);
		return dettaglio;
	}
	
	protected boolean iniziaConCodiceBelfiore(String nomeFile) {
		return (StringUtils.isNotBlank(nomeFile)) ? nomeFile.matches(CODICE_BELFIORE_PATTERN) : Boolean.FALSE;
	}

}