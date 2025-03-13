package it.eng.tz.urbamid.web.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.GeometriaLayerCompletoDTO;
import it.eng.tz.urbamid.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.web.services.WebGisServices;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class WebGisServicesImpl implements WebGisServices{
	
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@Override
	public List<GeometriaLayerCompletoDTO> findGeometryLayerByWkt(String layerName, String wktGeom) throws Exception {
		List<GeometriaLayerCompletoDTO> geometrie = new ArrayList<GeometriaLayerCompletoDTO>();
		
		List<String> wktList = this.splitWkt(wktGeom);
		for (String wkt : wktList) {
			String url = env.getProperty(IConstants.WS_WRAPPER_ENDPOINT)+ env.getProperty(IConstants.WS_WRAPPER_GEOMETRY_LAYER_BYWKT) + 
							"?wktGeom="+wkt+"&layerName="+layerName;	 
			ResponseData response = restService.restPostTable(url, null);
			if(response!=null && response.isSuccess()) {
				geometrie.add( new ObjectMapper().convertValue(response.getAaData(), new TypeReference<GeometriaLayerCompletoDTO>(){}) );
			}
		}
		return geometrie;
	}

	@Override
	public List<GeometriaLayerDTO> findGeometryLayer(String layerName) throws Exception {
		List<GeometriaLayerDTO> geometrie = new ArrayList<GeometriaLayerDTO>();
		
		String url = env.getProperty(IConstants.WS_WRAPPER_ENDPOINT)+ env.getProperty(IConstants.WS_WRAPPER_GEOMETRY_LAYER) + "?layerName="+layerName;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			geometrie.addAll(   new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<GeometriaLayerDTO>>() {})  );
		}
		return geometrie;
	}
	
	private List<String> splitWkt(String wkt) {
		List<String> result = new ArrayList<String>();
		if (wkt.contains("|")) {
			String[] arr = wkt.split("\\|");
			for ( int i=0; i<arr.length; i++ ){
				result.add( arr[i].replace("|", "") );
			}
		} else 
			result.add(wkt);
		
		return result;
	}

	@Override
	public boolean addLayer(Long idLayer, String nomeLayer, String identificativo) throws Exception {
		boolean result = false;
		
		String url = env.getProperty(IConstants.WS_WRAPPER_ENDPOINT) + env.getProperty(IConstants.WS_WRAPPER_ADD_LAYER) + "?idLayer=" + idLayer + "&nomeLayer=" + nomeLayer + "&identificativo=" + identificativo; 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess())
			result = true;
		else
			throw new UrbamidServiceException("Errore nella creazione/modifica del layer da geoserver");
		
		return result;
	}
	
	@Override
	public boolean deleteLayer(String identificativo) throws Exception {
		boolean result = false;
		
		String url = env.getProperty(IConstants.WS_WRAPPER_ENDPOINT) + env.getProperty(IConstants.WS_WRAPPER_DELETE_LAYER) + "?workspaces=urbamid&layer=" + "u_admin_edtl_".concat(identificativo.toLowerCase()).concat(".json"); 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess())
			result = true;
		else 
			throw new UrbamidServiceException("Errore nella cancellazione del layer da geoserver");
		
		return result;
	}
}
