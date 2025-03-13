package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.eng.tz.urbamid.catasto.exception.ConverterException;

public interface IConverter<MODEL, DTO>{
	
	final String DEFAULT_FIELD_VALUE = "nd";
	final String DEFAULT_FIELD_VALUE_NULL = "-1";
	
	/**
	 * Converte un DTO nel corrispondente model.
	 * 
	 * @param dto è l'oggetto da convertire
	 * @return il modello convertito.
	 * 
	 * @throws Exception
	 */
	public MODEL toModel(DTO dto) throws ConverterException;
	
	/**
	 * Converte un model nel corrispondente DTO.
	 * 
	 * @param model è il model
	 * @return il dto corrispondente.
	 * 
	 * @throws Exception
	 */
	public DTO toDto(MODEL model) throws ConverterException;

	/**
	 * Converte una lista di model nella corrispondente lista di DTO.
	 * Questa è l'implementazione di default che può essere sovrascritta nelle sottoclassi.
	 * 
	 * @param model è la lista di model
	 * @return la lista di dto corrispondente.
	 * 
	 * @throws Exception
	 */
	default List<DTO> toDto(List<MODEL> models) throws ConverterException {
		List<DTO> dtos = Collections.emptyList();
		if( models != null && !models.isEmpty()){
			dtos = new ArrayList<>(models.size());
			for (MODEL model : models) {
				dtos.add(this.toDto(model));
			}
		}
		return dtos;
	}
	
}
