package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.security.model.MappaBean;
import it.eng.tz.urbamid.security.model.MappaRicercaBean;
import it.eng.tz.urbamid.security.model.MappaToolBean;
import it.eng.tz.urbamid.security.model.PermessoMappaDto;
import it.eng.tz.urbamid.security.model.RicercaBean;
import it.eng.tz.urbamid.security.model.ToolBean;
import it.eng.tz.urbamid.web.dto.RuoloDto;

/**
 * @author Alessandro Paolillo
 */
@Service
public interface IMappaService {

	public MappaBean getMappa(String codeMappa) throws Exception;
	
	public List<MappaBean> getMappe() throws Exception;
	
	public List<MappaToolBean> getMapTools() throws Exception;
	
	public List<MappaRicercaBean> getMapRicerche() throws Exception;
	
	public List<RuoloDto> getAllRuoli() throws Exception;
	
	public MappaBean duplicaMappa( MappaBean mappaBean) throws Exception;
	
	public String saveOrUpdateMappa (MappaBean mappaBean) throws Exception;

	public List<MappaRicercaBean> getMapAttRicerche(Integer codeMappa) throws Exception;

	public List<MappaToolBean> getMapAttTools(Integer idMappa) throws Exception;

	public List<RicercaBean> insertMapRicerche(List<RicercaBean> ricerca) throws Exception;
		 
	public List<ToolBean> insertMapTool(List<ToolBean> tools) throws Exception;

	public List<PermessoMappaDto> insertMapPermessi(List<PermessoMappaDto> permessi) throws Exception;

	List<PermessoMappaDto> gettAllMapPermessi(Integer idMappa) throws Exception;

	List<PermessoMappaDto> getRuoloMappa(Integer idRuolo) throws Exception;
		
}