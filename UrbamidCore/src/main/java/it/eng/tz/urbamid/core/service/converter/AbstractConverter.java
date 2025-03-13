package it.eng.tz.urbamid.core.service.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public abstract class AbstractConverter<MODEL, DTO> {
	
	public abstract MODEL toModel(DTO dto);
	
	public abstract DTO toDTO(MODEL model);
	
	/**
	 * Converte un oggetto iterabile di MODEL nella corrispondente lista di DTO
	 * @param models è la lista iterabile di MODEL da convertire
	 * @return la lista dei DTO convertiti
	 * @throws ConverterException
	 */
	public List<DTO> toDTO( Iterable<MODEL> models ) {
		List<DTO> dtos = Collections.emptyList();
		if( models != null ){
			dtos = new ArrayList<DTO>();
			for (MODEL model : models ) {
				dtos.add( this.toDTO( model ) );
			}
		}
		return dtos;
	}

	/**
	 * Converte un oggetto iterabile di DTO nella corrispondente lista di MODEL
	 * @param dtos è la lista iterabile di DTO da convertire
	 * @return la lista dei MODEL convertiti
	 * @throws ConverterException
	 */
	public List<MODEL> toModel( Iterable<DTO> dtos ) throws Exception{
		List<MODEL> models = Collections.emptyList();
		if( dtos != null ){
			models = new ArrayList<MODEL>( );
			for (DTO dto : dtos) {
				models.add( this.toModel(dto) );
			}
		}
		return models;
	}		

}