package it.eng.tz.urbamid.administration.web.controller;

import java.util.LinkedHashMap;
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
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;
import it.eng.tz.urbamid.administration.mappa.service.IUMappaLayerService;
import it.eng.tz.urbamid.administration.web.response.ResponseData;

/**
 * @author Salvatore Mariniello
 */
@RestController
@RequestMapping("/administration/layer")
public class AdminLayerCtrl extends AbstractController  {

	private static final Logger logger = LoggerFactory.getLogger(AdminLayerCtrl.class.getName());


	@Autowired
	IUMappaLayerService ilayerMappService;

	//MS
	@ApiOperation("Salva o modifica i Layers")
	@RequestMapping(value="/saveOrUpdate", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveOrUpdateLayer (@RequestBody UMappaLayer entity,HttpServletRequest request) {
		String result = null;
		ResponseData response = null;
		try{

			result = ilayerMappService.saveOrUpdate(entity);

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
	@ApiOperation("Recupera i Gruppi e layer associati alla mappa.")
	@RequestMapping(value="/group-layer-mappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getGroupLayerByMappa(@RequestBody UMappa entity, HttpServletRequest request) {

		String idLog = "getGroupLayerByMappa";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			LinkedHashMap<String,List<UMappaLayer>> gruppiList = ilayerMappService.getGroupLayerByMappa(entity);
			response = new ResponseData(true, gruppiList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getGroupLayerByMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	//MS
	@ApiOperation("Elimina il layer associato")
	@RequestMapping(value="/delete", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delete (@RequestBody UMappaLayer entity,HttpServletRequest request) {
		int result;
		ResponseData response = null;
		try{

			result = ilayerMappService.delete(entity);

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
	@ApiOperation("Modifica il nome Gruppo")
	@RequestMapping(value="/updateGruppo", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData updateGruppo (@RequestBody UMappaLayer entity,HttpServletRequest request) {
		int result;
		ResponseData response = null;
		try{

			result = ilayerMappService.updateGruppo(entity);

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
	@ApiOperation("Elimina il nome Gruppo")
	@RequestMapping(value="/deleteGruppo", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteGruppo (@RequestBody UMappaLayer entity,HttpServletRequest request) {
		int [] result;
		ResponseData response = null;
		try{

			result = ilayerMappService.deleteGruppo(entity);

			if(result!=null){

				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore deleteGruppo; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}

	@ApiOperation("Recupera i permessi di un determinato layer")
	@RequestMapping(value="/getAllLayerPermessi", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllLayerPermessi(HttpServletRequest request, @RequestParam(name="idMappa", required=true) Integer idMappa, @RequestParam(name="nomeLayer", required = true) String nomeLayer) {

		String idLog = "getAllLayerPermessi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaPermessoBean> permessiList = ilayerMappService.getLayerPermessi(idMappa, nomeLayer);
			response = new ResponseData(true, permessiList, permessiList.size(), permessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllLayerPermessi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	@RequestMapping(value="/countPermessiLayer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData countPermessiLayer(HttpServletRequest request, @RequestParam(name="nomeLayer", required=true) String nomeLayer) {

		String idLog = "countPermessiLayer";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<UMappaLayer> permessiList = ilayerMappService.countPermessiLayer(nomeLayer);
			response = new ResponseData(true, permessiList, permessiList.size(), permessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllLayerPermessi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}

	@ApiOperation("Salva i permessi")
	@RequestMapping(value="/insertPermessi", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData savePermessi (@RequestBody List<MappaPermessoBean> permesso) {
		ResponseData response = null;
		try{

			ilayerMappService.insertPermesso(permesso);			
			response = new ResponseData(true, permesso);
			response.setMessage("success");
		} catch (Exception e) {
			String message = "Errore savePermessi; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
	@ApiOperation("Visualizza le tavole delle altre mappe")
	@RequestMapping(value="/getTavoleGroup", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getGroupTableMap (@RequestParam(name="idMappa", required=true) Integer idMappa) {
		ResponseData response = null;
		try{
			response = new ResponseData(true, ilayerMappService.getGroupTableMap(idMappa));
			response.setMessage("success");
		} catch (Exception e) {
			String message = "Errore getGroupTableMap; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}
	
	@ApiOperation("Recupera i layer dalla tavola")
	@RequestMapping(value="/getLayerToTavola", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData layerDaAggiungere (@RequestParam(name="idMappa", required=true) Integer idMappa, 
			@RequestParam(name="nomeTavola", required=true) String nomeTavola) {
		ResponseData response = null;
		try{
			response = new ResponseData(true, ilayerMappService.layerDaAggiungere(idMappa, nomeTavola));
			response.setMessage("success");
		} catch (Exception e) {
			String message = "Errore layerDaAggiungere; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}

	//MS
	@ApiOperation("Elimina - Salva Gruppi e Layer associati")
	@RequestMapping(value="/saveGrups", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveGrups (@RequestBody LinkedHashMap<String, List<UMappaLayer>> grups,HttpServletRequest request) {
		int result;
		ResponseData response = null;
		try{

			result = ilayerMappService.saveGrups(grups);

			if(result>0){

				response = new ResponseData(true,result);
				response.setMessage("success");
			}else{
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			String message = "Errore saveGrups; "+e.getMessage();
			response = new ResponseData(false, null);
			response.setMessage(message);
			logger.error(message, e); 
		}
		return response;
	}		

}
