package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.MappaBean;
import it.eng.tz.urbamid.security.model.MappaRicercaBean;
import it.eng.tz.urbamid.security.model.MappaToolBean;
import it.eng.tz.urbamid.security.model.PermessoMappaDto;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.model.RicercaBean;
import it.eng.tz.urbamid.security.model.ToolBean;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.RuoloDto;
import it.eng.tz.urbamid.web.util.IConstants;

/**
 * Service per la gestione delle mappe
 * @author Alessandro Paolillo
 */
@Service
public class MappaService implements IMappaService {

	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@Override
	public MappaBean getMappa(String codeMappa) throws Exception {
		MappaBean result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_GETMAPPA)
				+"?codeMappa="+codeMappa;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<MappaBean>() {});		
		}
		return result;
	}
	
	@Override
	public List<MappaBean> getMappe() throws Exception {
		List<MappaBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_GETMAPPE);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<MappaBean>>() {});
		}
		return result;
	}
	
	@Override
	public List<MappaToolBean> getMapTools() throws Exception {
		List<MappaToolBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_TOOLS);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<MappaToolBean>>() {});
		}
		return result;
	}
	
	@Override
	public List<MappaToolBean> getMapAttTools(Integer idMappa) throws Exception {
		List<MappaToolBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_ATTTOOL) + "?idMappa="+ idMappa;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<MappaToolBean>>() {});
		}
		return result;
	}
	
	@Override
	public List<MappaRicercaBean> getMapRicerche() throws Exception {
		List<MappaRicercaBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_ALLRICERCHE);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<MappaRicercaBean>>() {});
		}
		return result;
	}
	
	@Override
	public List<RuoloDto> getAllRuoli() throws Exception {
		List<RuoloDto> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT) + env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_GETALLRUOLI);
		ResponseData response = restService.restPostTable(url, null);
		if(response != null & response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RuoloDto>>() {});
		}
		return result;
	}

	@Override
	public List<ToolBean> insertMapTool(List<ToolBean> tools) throws Exception {
		List<ToolBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_INSERTJOINTOOL);	 
		ResponseData response = restService.restPostTable(url, tools);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ToolBean>>(){});
		}
		return result;
	}
	
	@Override
	public List<RicercaBean> insertMapRicerche(List<RicercaBean> ricerca) throws Exception {
		List<RicercaBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_INSERTJOINRICERCA);	 
		ResponseData response = restService.restPostTable(url, ricerca);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RicercaBean>>(){});
		}
		return result;
	}
	
	@Override
	public List<PermessoMappaDto> insertMapPermessi(List<PermessoMappaDto> permessi) throws Exception {
		List<PermessoMappaDto> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_INSERTPERMESSI);	 
		ResponseData response = restService.restPostTable(url, permessi);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PermessoMappaDto>>(){});
		}
		return result;
	}
	
	@Override
	public List<PermessoMappaDto> gettAllMapPermessi(Integer idMappa) throws Exception {
		List<PermessoMappaDto> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_GETALLMAPPERMESSI) + "?idMappa= " + idMappa;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PermessoMappaDto>>(){});
		}
		return result;
	}
	
	@Override
	public List<PermessoMappaDto> getRuoloMappa(Integer idRuolo) throws Exception {
		List<PermessoMappaDto> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_GETRUOLOMAPPA) + "?idRuolo= " + idRuolo;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PermessoMappaDto>>(){});
		}
		return result;
	}
	
	@Override
	public List<MappaRicercaBean> getMapAttRicerche(Integer idMappa) throws Exception {
		List<MappaRicercaBean> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_ATTRICERCHE)+"?idMappa=" + idMappa;
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<MappaRicercaBean>>() {});
		}
		return result;
	}
	
	@Override
	public MappaBean duplicaMappa( MappaBean mappaBean) throws Exception {
		MappaBean result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_DUPLICAMAPPA);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<MappaBean>() {});		
		}
		return result;
	}
	
	@Override
	public String saveOrUpdateMappa (MappaBean mappaBean) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_SALVAMAPPA);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}

}
