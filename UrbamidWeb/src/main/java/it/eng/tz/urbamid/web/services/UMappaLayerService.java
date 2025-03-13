package it.eng.tz.urbamid.web.services;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.PermessoMappaDto;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.model.UMappa;
import it.eng.tz.urbamid.security.model.UMappaLayer;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.GroupMapDTO;
import it.eng.tz.urbamid.web.dto.LayerDaAggiungereDTO;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class UMappaLayerService implements IUMappaLayerService {

	@Autowired
	private Environment env;

	@Autowired
	protected RestService restService;



	@Override
	public String saveOrUpdate(UMappaLayer entity) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+  env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_SAVE_OR_UPDATE);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}

	@Override
	public Integer[] deletes(List<Integer> ids) throws Exception {
		Integer[] result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_DELETE_SEL);	 
		ResponseData response = restService.restPostTable(url, ids);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer[]>() {});		
		}
		return result;
	}

	@Override
	public int delete(UMappaLayer entity) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_DELETE);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}



	@Override
	public List<UMappaLayer> getLayers() throws Exception {

		List<UMappaLayer> result=null;

		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_LAYER_ALL);
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<UMappaLayer>>() {});
		}

		return result;
	}

	@Override
	public List<PermessoMappaDto> getAllLayerPermessi(Integer idMappa, String nomeLayer) throws Exception {
		List<PermessoMappaDto> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_GET_ALL_PERMESSI) + "?idMappa=" + idMappa + "&nomeLayer=" + nomeLayer;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PermessoMappaDto>>(){});
		}
		return result;
	}

	@Override
	public List<UMappaLayer> countPermessiLayer(String nomeLayer) throws Exception {
		List<UMappaLayer> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_COUNT_PERMESSI) + "?nomeLayer=" + nomeLayer ;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<UMappaLayer>>(){});
		}
		return result;
	}

	@Override
	public LinkedHashMap<String, List<UMappaLayer>> getGroupLayerByMappa(UMappa entity) throws Exception {
		LinkedHashMap<String,List<UMappaLayer>> result=null;

		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_GROUP_LAYER_MAPPA);
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<LinkedHashMap<String,List<UMappaLayer>>>() {});
		}

		return result;


	}

	@Override
	public int updateGruppo(UMappaLayer entity) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_UPDATE_GROUP);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}

	@Override
	public List<PermessoMappaDto> insertPermessi(List<PermessoMappaDto> permessi) throws Exception {
		List<PermessoMappaDto> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_INSERT_PERMESSI);	 
		ResponseData response = restService.restPostTable(url, permessi);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PermessoMappaDto>>(){});
		}
		return result;
	}

	@Override
	public Integer[] deleteGruppo(UMappaLayer entity) throws Exception {
		Integer[] result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+env.getProperty(IConstants.WS_ADMINISTRATION_LAYER_DELETE_GROUP);
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer[]>() {});		
		}
		return result;
	}

	@Override
	public int saveGrups(LinkedHashMap<String, List<UMappaLayer>> grups) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+"layer/saveGrups";	 
		ResponseData response = restService.restPostTable(url, grups);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}

	@Override
	public List<GroupMapDTO> getGroupTableMap(Integer idMappa) throws Exception {
		List<GroupMapDTO> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+env.getProperty(IConstants.WS_ADMINISTRATION_GROUP_TO_MAP) + "?idMappa=" + idMappa;
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<GroupMapDTO>>() {});		
		}
		return result;
	}
	
	@Override
	public List<LayerDaAggiungereDTO> layerDaAggiungere(final Integer idMappa, final String nomeTavola) throws Exception {
		List<LayerDaAggiungereDTO> result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+env.getProperty(IConstants.WS_ADMINISTRATION_ADD_LAYER) + "?idMappa=" + idMappa + "&nomeTavola=" + nomeTavola;
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<LayerDaAggiungereDTO>>() {});		
		}
		return result;
	}

}
