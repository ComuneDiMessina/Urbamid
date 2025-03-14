package it.eng.tz.urbamid.web.services;

import java.util.List;

import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.web.dto.LayerAttributiDTO;
import it.eng.tz.urbamid.web.dto.LayerGeometrieDTO;
import it.eng.tz.urbamid.web.dto.MenuFunzionalitaDto;
import it.eng.tz.urbamid.web.filter.LayersFilter;

public interface AdministrationServices {

	public List<FunzionalitaDto> getFunzionalita(List<String> authorities) throws Exception;
	
	public MenuFunzionalitaDto getMenuFunzionalita(String authority) throws Exception;
	
	public LayerAttributiDTO insertOrUpdateLayer(LayerAttributiDTO attributi) throws Exception;
	
	public DataTableDTO findAllLayers(LayersFilter filter) throws Exception;

	public List<LayerGeometrieDTO> findAllGeometry(Long idLayer) throws Exception;
	
	public LayerAttributiDTO eliminaLayer(LayerAttributiDTO attributi) throws Exception;

	public LayerAttributiDTO eliminaGeometria(Long idGeometria) throws Exception;
	
	public int countLayerByIdentificativo(String identificativo) throws Exception;

	
}
