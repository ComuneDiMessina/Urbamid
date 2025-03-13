package it.eng.tz.urbamid.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.BaseResponse;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.web.dto.LayerAttributiDTO;
import it.eng.tz.urbamid.web.dto.LayerGeometrieDTO;
import it.eng.tz.urbamid.web.dto.MenuFunzionalitaDto;
import it.eng.tz.urbamid.web.dto.RequestLayerAttributiDTO;
import it.eng.tz.urbamid.web.filter.LayersFilter;
import it.eng.tz.urbamid.web.services.AdministrationServices;
import it.eng.tz.urbamid.web.services.WebGisServices;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class AdministrationServicesImpl implements AdministrationServices{

	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@Autowired
	protected WebGisServices wrapperGeo;
	
	
	@Override
	public List<FunzionalitaDto> getFunzionalita(List<String> authorities) throws Exception {
		List<FunzionalitaDto> funzionalita = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_FUNZIONALITA);	 
		BaseResponse<?> response = restService.restPost(url, authorities);
		
		if(response!=null && StringUtils.hasText(response.getCodiceEsito()) && response.getCodiceEsito().equals("OK")) {
			funzionalita = new ObjectMapper().convertValue(response.getPayload(), new TypeReference<List<FunzionalitaDto>>() {});		
		}
		
		return funzionalita;
	}

	@Override
	public MenuFunzionalitaDto getMenuFunzionalita(String authority) throws Exception {
		MenuFunzionalitaDto menuFunzionalita = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_FUNZIONALITA_MENU)
						+"?authority="+authority;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			menuFunzionalita = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<MenuFunzionalitaDto>() {});		
		}
		return menuFunzionalita;
	}

	@Override
	public LayerAttributiDTO insertOrUpdateLayer(LayerAttributiDTO attributi) throws Exception {
		LayerAttributiDTO result = null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_INSERT_OR_UPDATE);	 
		ResponseData response = restService.restPostTable(url, attributi);
		
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<LayerAttributiDTO>() {});
			
			/** INSERISCO IL LAYER SU GEOSERVER SOLO SE HA LO STATO SU PUBBLICATP **/
			if(attributi.getStato().indexOf("PUBBLICATO") != -1)
				wrapperGeo.addLayer(attributi.getId(), attributi.getNome(), attributi.getIdentificativo());		
			else if(attributi.getStato().indexOf("BOZZA") != -1) /** SE LO STATO RITORNA A BOZZA, ELIMINO IL LAYER **/
				wrapperGeo.deleteLayer(attributi.getIdentificativo());
			
		} else {
			throw new UrbamidServiceException("Errore nell'inserimento dei layer");
			
		}
		
		return result;
	}

	@Override
	public DataTableDTO findAllLayers(LayersFilter filter) throws Exception {
		
		List<RequestLayerAttributiDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT) + env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_FINDALL_LAYERS);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RequestLayerAttributiDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public List<LayerGeometrieDTO> findAllGeometry(Long idLayer) throws Exception {
		
		List<LayerGeometrieDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT) + env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_FINDALL_GEOMETRY) + "?idLayer=" + idLayer;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess())
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<LayerGeometrieDTO>>() {});
		else
			throw new UrbamidServiceException("Errore nel recuperare le geometrie del layer");
			
		return lista;
	}
	
	@Override
	public LayerAttributiDTO eliminaLayer(LayerAttributiDTO attributi) throws Exception {
		LayerAttributiDTO result = null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_DELETE_LAYER) + "?idLayer=" + attributi.getId();	 
		ResponseData response = restService.restPostTable(url, null);
		
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<LayerAttributiDTO>() {});
			
			/** ELIMINO IL LAYER DA GEOSERVER **/
			if(attributi.getStato().indexOf("PUBBLICATO") != -1)
				wrapperGeo.deleteLayer(attributi.getIdentificativo());
		
		} else {
			throw new UrbamidServiceException("Errore nella cancellazione del layer");
		
		}
		
		return result;
	}
	
	@Override
	public LayerAttributiDTO eliminaGeometria(Long idGeometria) throws Exception {
		LayerAttributiDTO result = null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_DELETE_GEOMETRY) + "?idGeometria=" + idGeometria;	 
		ResponseData response = restService.restPostTable(url, null);
		
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<LayerAttributiDTO>() {});
		} else {
			throw new UrbamidServiceException("Errore nella cancellazione della geometria!");
		}
		
		return result;
	}

	@Override
	public int countLayerByIdentificativo(String identificativo) throws Exception {
		int result = 0;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_COUNT_BY_IDENTIFICATIVO) + "?identificativo=" + identificativo;	 
		ResponseData response = restService.restPostTable(url, null);
		
		if(response!=null && response.isSuccess())
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});
		else
			throw new UrbamidServiceException("Errore nel contare i layer!");
		
		return result;
	}
}
