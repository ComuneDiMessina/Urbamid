package it.eng.tz.urbamid.web.pageController;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.security.model.UMappa;
import it.eng.tz.urbamid.security.model.UMappaLayer;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.GroupMapDTO;
import it.eng.tz.urbamid.web.dto.LayerDaAggiungereDTO;
import it.eng.tz.urbamid.web.services.IUMappaLayerService;


@Controller(value = "Layer Controller")
@RequestMapping(value = "/layerController", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LayerCtrl extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(LayerCtrl.class.getName());
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	IUMappaLayerService ilayerService;
	
	@RequestMapping(value = "/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@RequestMapping(value="/getLayer", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getLayers(HttpServletRequest request) {
		logger.debug("POST getLayers");
		ResponseData response = null;
		 
		try {
		
			
		List<UMappaLayer> result=	 ilayerService.getLayers();
			
		if (result!=null && result.size()>0) {
			response = new ResponseData(true, result, result.size(), result.size(), null);
		} else
			response = new ResponseData(true, null, 0, 0, null);
		response.setMessage(SUCCESS);

		} catch (Exception e) {
			logger.error("Error in getLayers", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	
	@RequestMapping(value="/saveOrUpdate", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveOrUpdate(@RequestBody UMappaLayer entity,HttpServletRequest request) {
		logger.debug("POST saveOrUpdate");
		ResponseData response = null;
		 
		try {
 
			if(entity!=null){
 
			String result=ilayerService.saveOrUpdate(entity);
			response = new ResponseData(true, result, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("temaBean:::null");
			}
		 
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestBody UMappaLayer entity,HttpServletRequest request) {
		logger.debug("POST delete");
		ResponseData response = null;
		 
		try {
		 
			int res=ilayerService.delete(entity);
			if(res>0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("layer eliminati:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in delete", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	
	@RequestMapping(value="/group-layer-mappa", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getGroupLayerByMappa(@RequestBody UMappa entity,HttpServletRequest request) {
		logger.debug("POST getGroupLayerByMappa");
		ResponseData response = null;
		 
		try {
		 
			LinkedHashMap<String,List<UMappaLayer>> res=ilayerService.getGroupLayerByMappa(entity);
			if(res!=null) {
			response = new ResponseData(true, res,res.size(), res.size(), null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(true, res,0,0, null);
				response.setMessage("Nessun Gruppo Associata alla mappa");	
				
			}
		} catch (Exception e) {
			logger.error("Error in recupero gruppo associato alla mappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/updateGruppo", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData updateGruppo(@RequestBody UMappaLayer entity,HttpServletRequest request) {
		logger.debug("POST updateGruppo");
		ResponseData response = null;
		 
		try {
		 
			int res=ilayerService.updateGruppo(entity);
			if(res>0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("updateGruppo:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in updateGruppo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/deleteGruppo", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteGruppo(@RequestBody UMappaLayer entity,HttpServletRequest request) {
		logger.debug("POST deleteGruppo");
		ResponseData response = null;
		 
		try {
		 
			Integer[] res=ilayerService.deleteGruppo(entity);
			if(res.length>0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("deleteGruppo:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in deleteGruppo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/saveGrups", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveGrups(@RequestBody LinkedHashMap<String, List<UMappaLayer>> grups,HttpServletRequest request) {
		logger.debug("POST saveGrups");
		ResponseData response = null;
		 
		try {
		 
			int res=ilayerService.saveGrups(grups);
			if(res>=0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("saveGrups:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in saveGrups", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	@RequestMapping(value="/getGroupTableMap", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData getGroupTableMap(@RequestParam(name="idMappa", required=true) Integer idMappa, HttpServletRequest request) {
		logger.debug("POST getGroupTableMap");
		ResponseData response = null;
		 
		try { 
			List<GroupMapDTO> result = ilayerService.getGroupTableMap(idMappa);
			
			if(result != null) {
				response = new ResponseData(true, result, 0, 0, null);
				response.setMessage(SUCCESS);
			
			}else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage("getGroupTableMap:: 0");	
			
			}
		
		} catch (Exception e) {
			logger.error("Error in getGroupTableMap", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	@RequestMapping(value="/getLayerToTavola", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData layerDaAggiungere(@RequestParam(name="idMappa", required=true) Integer idMappa,
			@RequestParam(name="nomeTavola", required=true) String nomeTavola, HttpServletRequest request) {
		logger.debug("POST getLayerToTavola");
		ResponseData response = null;
		 
		try { 
			List<LayerDaAggiungereDTO> result = ilayerService.layerDaAggiungere(idMappa, nomeTavola);
			
			if(result != null) {
				response = new ResponseData(true, result, 0, 0, null);
				response.setMessage(SUCCESS);
			
			}else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage("getGroupTableMap:: 0");	
			
			}
		
		} catch (Exception e) {
			logger.error("Error in getGroupTableMap", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		
		return response;
	}

}
