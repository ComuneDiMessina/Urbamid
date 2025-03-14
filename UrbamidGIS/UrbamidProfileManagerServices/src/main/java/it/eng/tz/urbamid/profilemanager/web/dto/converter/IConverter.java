package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.List;

public interface IConverter<MODEL, DTO>{
	
	public static final  String DEFAULT_FIELD_VALUE = "nd";
	public static final  String DEFAULT_FIELD_VALUE_NULL = "-1";
	
	
	/**
	 * Converte un DTO nel corrispondente model di hibernate.
	 * @param dto è l'oggetto da convertire
	 * @return il modello di hibernate convertito
	 * @throws Exception
	 */
	public MODEL toModel(DTO dto) throws Exception;
	
	/**
	 * Converte un model di hibernatae nel corrispondente DTO.
	 * @param model è il model di hibernate
	 * @return il dto corrispondente al model di hibernate da covertire.
	 * @throws Exception
	 */
	public DTO toDto(MODEL model) throws Exception;

	/**
	 * Converte una lista di model di hibernatae nella corrispondente lista di DTO.
	 * @param model è la lsita di model di hibernate
	 * @return la lista di dto corrispondente.
	 * @throws Exception
	 */
	public List<DTO> toDto(List<MODEL> models) throws Exception;
	

}
