package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.model.TemaBean;
import it.eng.tz.urbamid.security.model.TemaMappeBean;
import it.eng.tz.urbamid.security.model.UMappa;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class UMappaService implements IUMappaService {
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@Override
	public List<UMappa> getMappe() throws Exception {
	List<UMappa> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+  env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_ALL_MAPPA);
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<UMappa>>() {});
		}
		
		return result;
	}

	@Override
	public String saveOrUpdate(UMappa umappa) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+  env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_SAVE_OR_UPDATE);	 
		ResponseData response = restService.restPostTable(url, umappa);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}

	@Override
	public Integer[] deletes(List<Long> ids) throws Exception {
		Integer[] result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+  env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_DELETE_SEL);	 
		ResponseData response = restService.restPostTable(url, ids);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer[]>() {});		
		}
		return result;
	}

	@Override
	public int delete(UMappa entity) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_DELETE);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}

	@Override
	public List<TemaBean> temaToMappa(UMappa entity) throws Exception {
		List<TemaBean> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_TEMI_TO_MAP);
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TemaBean>>() {});
		}
		
		return result;
	}

	@Override
	public List<TemaBean> temaToMappa(List<Long> ids) throws Exception {
		List<TemaBean> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_TEMI_TO_MAP_ALL);
		ResponseData response = restService.restPostTable(url, ids);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TemaBean>>() {});
		}
		
		return result;
	}

	@Override
	public Integer[] associaTemaToMappa(UMappa entity) throws Exception {
		Integer[] result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+  env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_ASSOCIA_TEMI_TO_MAP);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer[]>() {});		
		}
		return result;
	}

	@Override
	public Integer[] deleteTemaToMappa(UMappa entity) throws Exception {
		Integer[] result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_DELETE_TEMI_TO_MAP); 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer[]>() {});		
		}
		return result;
	}

	@Override
	public List<TemaMappeBean> getAllTemaMappe()throws Exception {
		List<TemaMappeBean> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_GETALLTEMAMAPPE);
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TemaMappeBean>>() {});
		}
		
		return result;
	}
	
	@Override
	public List<TemaMappeBean> getAllTemaMappebyRoles(List<Long> roles)throws Exception {
		List<TemaMappeBean> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_GETALLTEMAMAPPEROLES);
		ResponseData response = restService.restPostTable(url, roles);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TemaMappeBean>>() {});
		}
		
		return result;
	}
	
	@Override
	public int duplica(UMappa entity) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_DUPLICA);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}
	
	@Override
	public int updateZoomAndShowCat(UMappa entity) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPE_UPDATE_ZOOM);	 
		ResponseData response = restService.restPostTable(url, entity);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}
}
