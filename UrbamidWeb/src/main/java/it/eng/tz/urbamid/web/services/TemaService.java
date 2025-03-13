package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.model.TemaBean;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class TemaService implements ITemaService{
	
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;

	@Override
	public List<TemaBean> getTemi() throws Exception {
		
		List<TemaBean> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_TEMA_GETTEMI);
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TemaBean>>() {});
		}
		
		return result;
	}
	
	
	@Override
	public String saveOrUpdate(TemaBean temaBean) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_SALVA_TEMA);	 
		ResponseData response = restService.restPostTable(url, temaBean);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}
	
	@Override
	public Integer[] deletes(List<Integer> ids) throws Exception {
		Integer[] result = null;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_ELIMINA_TEMA_ALL);	 
		ResponseData response = restService.restPostTable(url, ids);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer[]>() {});		
		}
		return result;
	}
	
	@Override
	public int delete(Integer id) throws Exception {
		int result=0;
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_ELIMINA_TEMA);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		}
		return result;
	}
	
	@Override
	public List<Integer> getMappeToTema(Long idTema) throws Exception {
		List<Integer> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_TO_TEMA);
		ResponseData response = restService.restPostTable(url, idTema);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<Integer>>() {});
		}
		
		return result;
	}
	
	@Override
	public List<Integer> getMappeToTema(List<Integer> ids) throws Exception {
		List<Integer> result=null;
		
		String url = env.getProperty(IConstants.WS_ADMINISTRATION_ENDPOINT)+ env.getProperty(IConstants.WS_ADMINISTRATION_MAPPA_TO_TEMA);
		ResponseData response = restService.restPostTable(url, ids);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<Integer>>() {});
		}
		
		return result;
	}
}
