package it.eng.tz.urbamid.administration.web.controller;


import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RuoloBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaMappeBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.ToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
import it.eng.tz.urbamid.administration.mappa.service.IMappaService;
import it.eng.tz.urbamid.administration.mappa.service.IUMappaService;
import it.eng.tz.urbamid.administration.web.response.ResponseData;

/**
 * @author Alessandro Paolillo
 */
@RestController
@RequestMapping("/administration/mappa")
public class AdminMappaCtrl extends AbstractController  {

	private static final Logger logger = LoggerFactory.getLogger(AdminMappaCtrl.class.getName());

	@Autowired
	private IMappaService webGisService;

	
	@Autowired
	IUMappaService imappaService;
 
	@ApiOperation("Recupera le mappe dato il codice della mappa.")
	@RequestMapping(value="/getMappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData  getMappa(HttpServletRequest request, @ApiParam(value="Codice della mappa", required=true) @RequestParam(name="codeMappa", required=true) String codeMappa) {

		String idLog = "getMappa";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			MappaBean mappaBean = webGisService.getMappa(codeMappa);
			response = new ResponseData(true, mappaBean, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	@ApiOperation("Recupera le mappe dato il codice della mappa.")
	@RequestMapping(value="/getMappe", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getMappe(HttpServletRequest request) {

		String idLog = "getMappe";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaBean> mappaList = webGisService.getMappe();

			if (mappaList!=null && mappaList.size()>0) {
				response = new ResponseData(true, mappaList, mappaList.size(), mappaList.size(), null);
			} else
				response = new ResponseData(true, null, 0, 0, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMappe", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	@ApiOperation("Recupera i tools delle mappe.")
	@RequestMapping(value="/getMapTools", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getMapTools(HttpServletRequest request) {

		String idLog = "getMapTools";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaToolBean> mappaToolList = webGisService.getMapTools();
			response = new ResponseData(true, mappaToolList, mappaToolList.size(), mappaToolList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapTools", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	@ApiOperation("Recupera tutte le ricerche delle mappe.")
	@RequestMapping(value="/getMapAllRicerche", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllMapRicerche(HttpServletRequest request) {

		String idLog = "getAllMapRicerche";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaRicercaBean> mappaRicercaList = webGisService.getMapAllRicerche();
			response = new ResponseData(true, mappaRicercaList, mappaRicercaList.size(), mappaRicercaList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllMapRicerche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@ApiOperation("Recupera tutti i ruoli")
	@RequestMapping(value="/getAllRuoli", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllRuoli(HttpServletRequest request) {

		String idLog = "getAllRuoli";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<RuoloBean> ruoliList = webGisService.getAllRuoli();
			response = new ResponseData(true, ruoliList, ruoliList.size(), ruoliList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllRuoli", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@ApiOperation("Recupera tutte le mappe di un ruolo")
	@RequestMapping(value="/getRuoloMappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getRuoloMappa(HttpServletRequest request, @RequestParam(name="idRuolo", required=true) Integer idRuolo) {

		String idLog = "getAllMapPermessi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaPermessoBean> permessiList = webGisService.getRuoloMappa(idRuolo);
			response = new ResponseData(true, permessiList, permessiList.size(), permessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllMapPermessi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@ApiOperation("Recupera tutti i permessi di una mappa")
	@RequestMapping(value="/getAllMapPermessi", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllMapPermessi(HttpServletRequest request, @RequestParam(name="idMappa", required=true) Integer idMappa) {

		String idLog = "getAllMapPermessi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaPermessoBean> permessiList = webGisService.getMapPermessi(idMappa);
			response = new ResponseData(true, permessiList, permessiList.size(), permessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllMapPermessi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	@ApiOperation("Recupera le ricerche delle mappe.")
	@RequestMapping(value="/getMapAttRicerche", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getMapAttRicerche(HttpServletRequest request, @RequestParam(name="idMappa", required=true) Integer idMappa) {

		String idLog = "getMapRicerche";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaRicercaBean> mappaRicercaList = webGisService.getMapRicerche(idMappa);
			response = new ResponseData(true, mappaRicercaList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	
	
	@ApiOperation("Recupera le ricerche delle mappe.")
	@RequestMapping(value="/getMapAttTool", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getMapAttTool(HttpServletRequest request, @RequestParam(name="idMappa", required=true) Integer idMappa) {

		String idLog = "getMapAttTool";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaToolBean> mappaToolList = webGisService.getMapTool(idMappa);
			response = new ResponseData(true, mappaToolList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}


	@ApiOperation("Duplica le mappe passata con codeMappa")
	@RequestMapping(value="/duplicaMappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData duplicaMappa(HttpServletRequest request, @ApiParam(value="Codice della mappa", required=true) @RequestParam(name = "codeMappa") String codeMappa) {

		String idLog = "duplicaMappa";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			MappaBean mappaBean = webGisService.getMappa(codeMappa);
			webGisService.duplicaMappa(mappaBean);
			mappaBean = webGisService.getMappa(codeMappa+" COPIA");
			response = new ResponseData(true, mappaBean, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in duplicaMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@ApiOperation("Inserisci i riferimenti nella tabella di JOIN dei tool")
	@RequestMapping(value="/insertJoinTool", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData inserteJoinTool (@RequestBody List<ToolBean> tools) {
		
		ResponseData response = null;

		try{

			webGisService.insertJoinTool(tools);
			
			response = new ResponseData(true, tools); 
			response.setMessage("success");

		} catch (Exception e) {
			String message = "Errore deleteJoinRicerca; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}

		return response;
	}
	
	@ApiOperation("Inserisco i riferimenti nella tabella di JOIN della ricerca")
	@RequestMapping(value="/insertJoinRicerca", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData inserteJoinRicerca (@RequestBody List<RicercaBean> ricerca) {
		
		ResponseData response = null;

		try{
			
			webGisService.insertJoinRicerca(ricerca);
			response = new ResponseData(true, ricerca);
			response.setMessage("success");
		
		} catch (Exception e) {
			String message = "Errore deleteJoinRicerca; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}

		return response;
	}
	
	@ApiOperation("Salva o modifica i permessi")
	@RequestMapping(value="/insertPermessi", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveOrUpdatePermessi (@RequestBody List<MappaPermessoBean> permesso) {
		ResponseData response = null;
		try{
  
			webGisService.insertOrUpdatePermesso(permesso);
			response = new ResponseData(true, permesso);
			response.setMessage("success");
			
		} catch (Exception e) {
			String message = "Errore saveOrUpdate; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	} 

	@ApiOperation("Salva la mappa passata con come json stringfy dell'oggetto MappaBean")
	@RequestMapping(value="/salvaMappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData salvaMappa (HttpServletRequest request) {
		String result = null;
		ResponseData response = null;
		try{

			String mappa = null; 
			if(request.getParameter("mappa")!= null){
				mappa = request.getParameter("mappa");
			}

			MappaBean mObj = null;
			mObj = new ObjectMapper().readValue(mappa, MappaBean.class);
			result = webGisService.saveOrUpdateMappa(mObj);
			/**Gestione response**/
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
	
	//MS
	@ApiOperation("Recupera la lista delle mappe.")
	@RequestMapping(value="/allmappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getUMappe(HttpServletRequest request) {

		String idLog = "allmappa";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<UMappa> mappeList = imappaService.getMappe();
			response = new ResponseData(true, mappeList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in allmappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	//MS
	@ApiOperation("Salva o modifica la mappa")
	@RequestMapping(value="/saveOrUpdate", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveOrUpdate (@RequestBody UMappa entity,HttpServletRequest request) {
		String result = null;
		ResponseData response = null;
		try{
  
			result = imappaService.saveOrUpdate(entity);
			 
			if(result!= null){
				response = new ResponseData(true, result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore saveOrUpdate; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	} 
	
	//MS
	@ApiOperation("Elimina tutti le mappe selezionati")
	@RequestMapping(value="/deleteSel", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deletes (@RequestBody List<Long> ids,HttpServletRequest request) {
		int [] result = null;
		ResponseData response = null;
		try{
  
			result = imappaService.deletes(ids);
			 
			if(result!= null){
				 
				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore deleteSel; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	//MS
	@ApiOperation("Associa temi alla mappa")
	@RequestMapping(value="/associaTemiToMap", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData associaTemiToMap (@RequestBody UMappa entity,HttpServletRequest request) {
		int [] result = null;
		ResponseData response = null;
		try{
  
			result = imappaService.associaTemaToMappa(entity);
			 
			if(result!= null){
				 
				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore associaTemiToMap; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	//MS
	@ApiOperation("Associa temi alla mappa")
	@RequestMapping(value="/deleteTemiToMap", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteTemiToMap (@RequestBody UMappa entity,HttpServletRequest request) {
		int [] result = null;
		ResponseData response = null;
		try{
  
			result = imappaService.deleteTemaToMappa(entity);
			 
			if(result!= null){
				 
				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore deleteTemiToMap; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
	//MS
	@ApiOperation("Recupera i Temi associati alla mappa.")
	@RequestMapping(value="/temiToMappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTemiToMappa(@RequestBody UMappa entity, HttpServletRequest request) {

		String idLog = "getTemiToMappa";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<TemaBean> mappaRicercaList = imappaService.temaToMappa(entity);
			response = new ResponseData(true, mappaRicercaList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getTemiToMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
 
	/**
	 * Recupero delle mappe con relativi temi associati
	 * @param entity
	 * @param request
	 * @return
	 * @author PAOLILLO
	 */
	@ApiOperation("Recupera tutti i temi e le mappe associate")
	@RequestMapping(value="/getAllTemaMappe", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllTemaMappe(HttpServletRequest request) {

		String idLog = "getAllTemaMappe";
		logger.info("Start {}", idLog);
		List<TemaMappeBean> tmBeanList = new ArrayList<TemaMappeBean>(); 
		ResponseData response = null;
		try {
			
			tmBeanList = imappaService.getAllTemaMappe();
			if(tmBeanList!=null ) {
				response = new ResponseData(true, tmBeanList, tmBeanList.size(), tmBeanList.size(), null);
				response.setMessage("success");
			} else
				response = new ResponseData(true, null, 0, 0, null);
			
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllTemaMappe", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Recupero delle mappe con relativi temi associati
	 * @param entity
	 * @param request
	 * @return
	 * @author PAOLILLO
	 */
	@ApiOperation("Recupera tutti i temi e le mappe associate")
	@RequestMapping(value="/getAllTemaMappeByRoles", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllTemaMappeByRoles(HttpServletRequest request, @RequestBody List<Long> roles) {

		String idLog = "getAllTemaMappeByRoles";
		logger.info("Start {}", idLog);
		List<TemaMappeBean> tmBeanList = new ArrayList<TemaMappeBean>(); 
		ResponseData response = null;
		try {
			//List<Long> roles = new ArrayList<Long>();
			//roles.add(1L);
			tmBeanList = imappaService.getAllTemaMappeByRoles(roles);
			if(tmBeanList!=null ) {
				response = new ResponseData(true, tmBeanList, tmBeanList.size(), tmBeanList.size(), null);
				response.setMessage("success");
			} else
				response = new ResponseData(true, null, 0, 0, null);
			
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllTemaMappe", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	//MS
	@ApiOperation("Duplica la mappa")
	@RequestMapping(value="/duplica", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData duplica (@RequestBody UMappa entity,HttpServletRequest request) {
		int result;
		ResponseData response = null;
		try{
  
			result = imappaService.duplica(entity);
			 
			if(result>0){
				 
				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore delete; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
	//MS
		@ApiOperation("Modifica il valore zoom e il valore show_category la mappa")
		@RequestMapping(value="/updateZoomShowCat", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
		public @ResponseBody ResponseData updateZoomShowCat (@RequestBody UMappa entity,HttpServletRequest request) {
			int result;
			ResponseData response = null;
			try{
	  
				result = imappaService.updateZoomAndShowCat(entity);
				 
				if(result>0){
					 
					response = new ResponseData(true,result);
					response.setMessage("success");
				}else{
					response = new ResponseData(false, null);
					response.setMessage("error");
				}
			} catch (Exception e) {
				String message = "Errore delete; "+e.getMessage();
				response = new ResponseData(false, null);
				response.setMessage(message);
				logger.error(message, e); 
			}
			return response;
		}
	
	
}
