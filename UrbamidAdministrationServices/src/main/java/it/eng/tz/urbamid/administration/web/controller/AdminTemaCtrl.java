package it.eng.tz.urbamid.administration.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.service.ITemaService;
import it.eng.tz.urbamid.administration.web.response.ResponseData;

/**
 * @author Salvatore Mariniello
 */
@RestController
@RequestMapping("/administration/tema")
public class AdminTemaCtrl extends AbstractController  {

	private static final Logger logger = LoggerFactory.getLogger(AdminTemaCtrl.class.getName());
	
	@Autowired
	private ITemaService temaService;
	

	
	@ApiOperation("Recupera i temi.")
	@RequestMapping(value="/getTemi", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getMappe(HttpServletRequest request) {

		String idLog = "getTemi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<TemaBean> temaList = temaService.getTemi();
			
			if (temaList!=null && temaList.size()>0) {
				response = new ResponseData(true, temaList, temaList.size(), temaList.size(), null);
			} else
				response = new ResponseData(true, null, 0, 0, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getTemi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@ApiOperation("Salva o modifica il Tema passato")
	@RequestMapping(value="/salvaTema", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData salvaOrUpdate (@RequestBody TemaBean temaBean,HttpServletRequest request) {
		String result = null;
		ResponseData response = null;
		try{
  
			result = temaService.salvaOrUpdate(temaBean);
			 
			if(result!= null){
				response = new ResponseData(true, result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore salvaMappa; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	} 
	
	@ApiOperation("Elimina tutti i temi selezionati")
	@RequestMapping(value="/deleteSel", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deletes (@RequestBody List<Integer> ids,HttpServletRequest request) {
		int [] result = null;
		ResponseData response = null;
		try{
  
			result = temaService.deletes(ids);
			 
			if(result!= null){
				 
				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore salvaMappa; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	} 
	
	
	@ApiOperation("Elimina il tema selezionato")
	@RequestMapping(value="/delete", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delete (@RequestParam(name ="id",defaultValue = "0") Integer id,HttpServletRequest request) {
		int result;
		ResponseData response = null;
		try{
  
			result = temaService.delete(id);
			 
			if(result!= 0){
				response = new ResponseData(true, new Integer(result));
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore salvaMappa; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
	
	
	@ApiOperation("Recupero la lista di mappe associate al tema")
	@RequestMapping(value="/mappeToTema", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData mappeToTema (@RequestBody List<Integer> ids,HttpServletRequest request) {
		List<Integer> result=null;
		ResponseData response = null;
		try{
  
			result = temaService.mappeToTema(ids);
			 
			if(result!= null){
				response = new ResponseData(true, result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore nel recupero mappe associate al tema; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
	@ApiOperation("Recupero la lista di mappe associate al tema")
	@RequestMapping(value="/mappeToTemaid", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData mappeToTemaId (@RequestBody Long idTema,HttpServletRequest request) {
		List<Integer> result=null;
		ResponseData response = null;
		try{
  
			result = temaService.mappeToTema(idTema);
			 
			if(result!= null){
				response = new ResponseData(true, result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore nel recupero mappe associate al tema; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
}
