package it.eng.tz.urbamid.administration.mappa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.Tema;
import it.eng.tz.urbamid.administration.web.dto.converter.IConverter;

@Component
public class TemaConverter implements IConverter<Tema, TemaBean>{
	
	private static final Logger logger = LoggerFactory.getLogger(TemaConverter.class.getName());
	
	@Override
	public Tema toModel(TemaBean model) throws Exception{
		Tema dto = new Tema();
		
		dto.setId(model.getId());
		dto.setIdPadre(model.getIdPadre()); 
		dto.setNome(model.getNome());
		dto.setDescrizione(model.getDescrizione());
		dto.setOrdinamento(model.getOrdinamento());
		dto.setDataCreazione(model.getDataCreazione());
		dto.setDataModifica(model.getDataModifica());
		dto.setUtenteCreazione(model.getUtenteCreazione());
		dto.setUtenteModifica(model.getUtenteModifica());

		return dto;
	}

	@Override
	public TemaBean toDto(Tema model) throws Exception{
		TemaBean dto = new TemaBean();
		dto.setIsNew(false);
		dto.setId(model.getId());
		dto.setIdPadre(model.getIdPadre()); 
		dto.setNome(model.getNome());
		dto.setDescrizione(model.getDescrizione());
		dto.setOrdinamento(model.getOrdinamento());
		dto.setDataCreazione(model.getDataCreazione());
		dto.setDataModifica(model.getDataModifica());
		dto.setUtenteCreazione(model.getUtenteCreazione());
		dto.setUtenteModifica(model.getUtenteModifica());
		 
		return dto;
	}
	
	@Override
	public List<TemaBean> toDto(List<Tema> models) throws Exception {
		List<TemaBean> result = new ArrayList<TemaBean>();
		try 
		{
			for (Tema model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Mappa --> MappaBean" + e, e);
		}

		return result;
	}
	

}
