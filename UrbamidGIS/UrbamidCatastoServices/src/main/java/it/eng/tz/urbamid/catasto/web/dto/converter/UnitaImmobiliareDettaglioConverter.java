package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobileJoinDettaglio;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.SoggettiUiuDTO;

@Component
public class UnitaImmobiliareDettaglioConverter extends AbstractConverter<UnitaImmobileJoinDettaglio, SoggettiUiuDTO> {

	private static final Logger logger = LoggerFactory.getLogger(UnitaImmobiliareDettaglioConverter.class.getName());

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat df_effcacia_pt = new SimpleDateFormat("ddMMyyyy");
	SimpleDateFormat df_effcacia_ui = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat df_registrazione = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public UnitaImmobileJoinDettaglio toModel(SoggettiUiuDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	public SoggettiUiuDTO toDTOPT(UnitaImmobileJoinDettaglio model, Map<String, Object> parameters)
			throws ConverterException {
		SoggettiUiuDTO dto = new SoggettiUiuDTO();
		try {
		if (model.getTipoSoggetto().equals("P")) {
			dto.setNominativo(model.getCognome() + " " + model.getNome());
		} else {
			dto.setNominativo(model.getDenominazione());
		}
		dto.setDiritto(model.getCodiceDiritto());
		dto.setDataEfficacia(formatter.format(df_effcacia_pt.parse(model.getDataEfficaciaGen())));
		dto.setDataRegistrazione(formatter.format(df_registrazione.parse(model.getDataRegistrazioneGen())));
		dto.setTipoAtto(model.getTipoNotaGen());
		dto.setNumeroAtto(model.getNumNotaGen());
		dto.setProgressivoAtto(model.getProgrNotaGen());
		dto.setAnnoAtto(String.valueOf(model.getAnnoNotaGen()));
		dto.setDescrizioneAtto(model.getDescrizioneAttoGen());
		} catch (Exception e) {
			logger.error("Errore in UnitaImmobiliareDettaglioConverter" + " >>>>>>>>>>>> " + e, e);
		}
		return dto;
	}

	public List<SoggettiUiuDTO> toDTOPT(List<UnitaImmobileJoinDettaglio> models) throws ConverterException {
		List<SoggettiUiuDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaImmobileJoinDettaglio model : models) {
				result.add(this.toDTOPT(model, null));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}


	public SoggettiUiuDTO toDTOUI(UnitaImmobileJoinDettaglio model, Map<String, Object> parameters)
			throws ConverterException {
		SoggettiUiuDTO dto = new SoggettiUiuDTO();
		try {
		if (model.getTipoSoggetto().equals("P")) {
			dto.setNominativo(model.getCognome() + " " + model.getNome());
		} else {
			dto.setNominativo(model.getDenominazione());
		}
		dto.setDiritto(model.getCodiceDiritto());
		dto.setDataEfficacia(formatter.format(df_effcacia_ui.parse(model.getDataEfficaciaGen())));
		dto.setDataRegistrazione(formatter.format(df_registrazione.parse(model.getDataRegistrazioneGen())));
		dto.setTipoAtto(model.getTipoNotaGen());
		dto.setNumeroAtto(model.getNumNotaGen());
		dto.setProgressivoAtto(model.getProgrNotaGen());
		dto.setAnnoAtto(String.valueOf(model.getAnnoNotaGen()));
		dto.setDescrizioneAtto(model.getDescrizioneAttoGen());
		} catch (Exception e) {
			logger.error("Errore in UnitaImmobiliareDettaglioConverter" + " >>>>>>>>>>>> " + e, e);
		}
		return dto;
	}

	public List<SoggettiUiuDTO> toDTOUI(List<UnitaImmobileJoinDettaglio> models) throws ConverterException {
		List<SoggettiUiuDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaImmobileJoinDettaglio model : models) {
				result.add(this.toDTOUI(model, null));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	@Override
	public SoggettiUiuDTO toDTO(UnitaImmobileJoinDettaglio model, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

}
