package it.eng.tz.urbamid.administration.mappa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RuoloBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.ToolBean;

/**
 * @author Alessandro Paolillo
 */
@Service
public interface IMappaService {

	public MappaBean getMappa(String codeMappa) throws Exception;
	
	public List<MappaBean> getMappe() throws Exception;
	
	public List<MappaToolBean> getMapTools() throws Exception;
	
	public List<MappaRicercaBean> getMapAllRicerche() throws Exception;
	
	public void duplicaMappa( MappaBean mappaBean) throws Exception;
	
	public String saveOrUpdateMappa (MappaBean mappaBean) throws Exception;
	
	public List<MappaRicercaBean> getMapRicerche(Integer idMappa) throws Exception;
	
	public List<MappaToolBean> getMapTool(Integer idMappa) throws Exception;
		
	public List<RuoloBean> getAllRuoli() throws Exception;

	public void insertJoinRicerca(List<RicercaBean> ricerca) throws Exception;
		
	public void insertJoinTool(List<ToolBean> tools) throws Exception;
	
	public List<MappaPermessoBean> getMapPermessi(Integer idMappa) throws Exception;
	
	public void insertOrUpdatePermesso (List<MappaPermessoBean> permesso) throws Exception;

	public List<MappaPermessoBean> getRuoloMappa(Integer idRuolo) throws Exception;

	
}