package it.eng.tz.urbamid.web.pageController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.MappaBean;
import it.eng.tz.urbamid.security.model.MappaRicercaBean;
import it.eng.tz.urbamid.security.model.MappaToolBean;
import it.eng.tz.urbamid.security.model.MyRole;
import it.eng.tz.urbamid.security.model.MyUser;
import it.eng.tz.urbamid.security.model.PermessoMappaDto;
import it.eng.tz.urbamid.security.model.RicercaBean;
import it.eng.tz.urbamid.security.model.TemaBean;
import it.eng.tz.urbamid.security.model.TemaMappeBean;
import it.eng.tz.urbamid.security.model.ToolBean;
import it.eng.tz.urbamid.security.model.UMappa;
import it.eng.tz.urbamid.security.model.UMappaLayer;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.RuoloDto;
import it.eng.tz.urbamid.web.services.IMappaService;
import it.eng.tz.urbamid.web.services.IUMappaLayerService;
import it.eng.tz.urbamid.web.services.IUMappaService;

/**
 * @author Alessandro Paolillo
 */
@Controller(value = "Mappa Controller")
@RequestMapping(value = "/mappaController", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class MappaCtrl extends AbstractController  {

	private static final Logger logger = LoggerFactory.getLogger(MappaCtrl.class.getName());
	
	@Autowired
	private IMappaService webGisService;
	
	@Autowired
	IUMappaService imappaService;
	
	@Autowired
	IUMappaLayerService ilayerService;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	@GetMapping("/getMappa")
	public @ResponseBody ResponseData getMappa(HttpServletRequest request, @RequestParam(name = "codeMappa") String codeMappa) {

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
	
	@GetMapping("/getMapTools")
	public @ResponseBody ResponseData getMapTools(HttpServletRequest request) {

		String idLog = "getMapTools";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaToolBean> mappaToolList = webGisService.getMapTools();
			response = new ResponseData(true, mappaToolList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapTools", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getAllRuoli")
	public @ResponseBody ResponseData getAllRuoli(HttpServletRequest request) {

		String idLog = "getAllRuoli";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<RuoloDto> ruoliList = webGisService.getAllRuoli();
			response = new ResponseData(true, ruoliList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapTools", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getMapAttTools")
	public @ResponseBody ResponseData getMapAttTools(HttpServletRequest request, @RequestParam(name = "idMappa") Integer idMappa) {

		String idLog = "getMapAttTools";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaToolBean> mappaToolList = webGisService.getMapAttTools(idMappa);
			response = new ResponseData(true, mappaToolList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapTools", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getMapAllRicerche")
	public @ResponseBody ResponseData getMapRicerche(HttpServletRequest request) {

		String idLog = "getMapRicerche";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaRicercaBean> mappaRicercaList = webGisService.getMapRicerche();
			response = new ResponseData(true, mappaRicercaList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getMapAttRicerche")
	public @ResponseBody ResponseData getMapAttRicerche(HttpServletRequest request, @RequestParam(name = "idMappa") Integer idMappa) {

		String idLog = "getMapRicerche";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<MappaRicercaBean> mappaRicercaList = webGisService.getMapAttRicerche(idMappa);
			response = new ResponseData(true, mappaRicercaList, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@RequestMapping(value="/insertRicerche", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertRicerche(HttpServletRequest request, @RequestBody List<RicercaBean> ricerca) {

		String idLog = "insertRicerche";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		
		try {
			List<RicercaBean> result = webGisService.insertMapRicerche(ricerca);
			
			if(result != null) {
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage("success");
			} else {
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
			
			
		} catch (Exception e) {
			logger.error("Error in duplicaMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@RequestMapping(value="/insertToolJoin", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertToolJoin(HttpServletRequest request, @RequestBody List<ToolBean> tools) {
		
		String idLog = "insertToolJoin";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		
		try {
			
			List<ToolBean> result = webGisService.insertMapTool(tools);
			
			if(result != null ) {
				response = new ResponseData(true, result);
				response.setMessage("success");
			} else {
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
			
		} catch (Exception e) {
			logger.error("Errore in deelteToolJoin", e);
			response = new ResponseData(false, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getAllMapPermessi")
	public @ResponseBody ResponseData getAllMapPermessi(HttpServletRequest request, @RequestParam(name = "idMappa") Integer idMappa) {

		String idLog = "getAllMapPermessi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<PermessoMappaDto> mappaPermessiList = webGisService.gettAllMapPermessi(idMappa);
			response = new ResponseData(true, mappaPermessiList, mappaPermessiList.size(), mappaPermessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getRuoloMappa")
	public @ResponseBody ResponseData getRuoloMappa(HttpServletRequest request, @RequestParam(name = "idRuolo") Integer idRuolo) {

		String idLog = "getAllMapPermessi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<PermessoMappaDto> mappaPermessiList = webGisService.getRuoloMappa(idRuolo);
			
			if(mappaPermessiList.size() != 0) {
				response = new ResponseData(true, mappaPermessiList, mappaPermessiList.size(), mappaPermessiList.size(), null);
				response.setMessage("success");
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@RequestMapping(value="/insertPermessi", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertPermessi(HttpServletRequest request, @RequestBody List<PermessoMappaDto> permessi) {
		
		String idLog = "insertToolJoin";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		
		try {
			
			List<PermessoMappaDto> result = webGisService.insertMapPermessi(permessi);
			
			if(result != null ) {
				response = new ResponseData(true, result);
				response.setMessage("success");
			} else {
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
			
		} catch (Exception e) {
			logger.error("Errore in insertPermessi", e);
			response = new ResponseData(false, null);
			response.setMessage("error");
		}
		
		return response;
	}
	
	@RequestMapping(value="/duplicaMappa", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData duplicaMappa(HttpServletRequest request, @RequestParam(name = "codeMappa") String codeMappa) {

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
	
	@RequestMapping(value = "/salvaMappa", method = RequestMethod.POST)
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
	
	@RequestMapping(value="/getAllMapp", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllMapp(HttpServletRequest request) {

		String idLog = "getAllMapp";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<UMappa> mappaList = imappaService.getMappe();//webGisService.getMappe();
			
			if (mappaList!=null && mappaList.size()>0) {
				response = new ResponseData(true, mappaList, mappaList.size(), mappaList.size(), null);
			} else
				response = new ResponseData(true, null, 0, 0, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getAllMapp", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	//MS
	@RequestMapping(value="/saveOrUpdate", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveOrUpdate(@RequestBody UMappa entity,HttpServletRequest request) {
		logger.debug("POST saveOrUpdate");
		ResponseData response = null;
		 
		try {
 
			if(entity!=null){
 
			String result=imappaService.saveOrUpdate(entity);
			response = new ResponseData(true, result, 0, 0, null);
			response.setMessage("success");
			}else {
				
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("UMappa:::null");
			}
		 
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	//MS
	@RequestMapping(value="/temiToMappa", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTemiToMappa(@RequestBody UMappa entity, HttpServletRequest request) {

		String idLog = "getTemiToMappa";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<TemaBean> temaList = imappaService.temaToMappa(entity);
			
			if (temaList!=null && temaList.size()>0) {
				response = new ResponseData(true, temaList, temaList.size(), temaList.size(), null);
			} else
				response = new ResponseData(true, null, 0, 0, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getTemiToMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Recupero di tutte le mappe con temi associati
	 * @param request
	 * @return
	 * @author PAOLILLO
	 */
	@RequestMapping(value="/getAllTemaMappe", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAllTemaMappe(HttpServletRequest request) {

		String idLog = "getAllTemaMappe";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			
			List<TemaMappeBean> temaMappeList = null;
			
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUser) 
				{
					MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if (user.getRuoli() != null && user.getRuoli().size() > 0 ) 
					{
						List<Long> idRoles = new ArrayList<Long>();
						for (MyRole role : user.getRuoli() ) 
						{
							idRoles.add(role.getId());
						}
						
						temaMappeList = imappaService.getAllTemaMappebyRoles(idRoles);
					}
				}			
			}
			else 
			{
				temaMappeList = imappaService.getAllTemaMappe();
			}
			
			
			
			if (temaMappeList!=null && temaMappeList.size()>0) {
				response = new ResponseData(true, temaMappeList, temaMappeList.size(), temaMappeList.size(), null);
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
	@RequestMapping(value="/deleteTemiToMap", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteTemiToMap(@RequestBody UMappa entity,HttpServletRequest request) {
		logger.debug("POST deleteTemiToMap");
		ResponseData response = null;
		 
		try {
		 
			Integer[] res=imappaService.deleteTemaToMappa(entity);
			if(res.length>0) {
			response = new ResponseData(true, res, res.length, res.length, null);
			response.setMessage("success");
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("deleteTemiToMap eliminati:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in deleteTemiToMap", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	//MS
	@RequestMapping(value="/associaTemiToMap", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData associaTemiToMap(@RequestBody UMappa entity,HttpServletRequest request) {
		logger.debug("POST associaTemiToMap");
		ResponseData response = null;
		 
		try {
		 
			Integer[] res=imappaService.associaTemaToMappa(entity);
			if(res.length>0) {
			response = new ResponseData(true, res, res.length, res.length, null);
			response.setMessage("success");
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("associaTemiToMap associati:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in associaTemiToMap", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	//MS
	@RequestMapping(value="/deleteSel", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deletes(@RequestBody List<Long> ids,HttpServletRequest request) {
		logger.debug("POST deleteSel");
		ResponseData response = null;
		 
		try {
		 
			Integer[] res=imappaService.deletes(ids);
			if(res.length>0) {
			response = new ResponseData(true, res, res.length, res.length, null);
			response.setMessage("success");
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("deleteSel eliminati:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in deleteSel", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@RequestMapping(value="/duplica", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData duplica(@RequestBody UMappa entity,HttpServletRequest request) {
		logger.debug("POST duplica");
		ResponseData response = null;
		 
		try {
		 
			int res=imappaService.duplica(entity);
			if(res>0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage("success");
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("mappa duplica:: 0");	
			}
		} catch (Exception e) {
			logger.error("Error in duplica", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping(value="/getAllLayerPermessi", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getAllLayerPermessi(HttpServletRequest request, @RequestParam(name = "idMappa") Integer idMappa, @RequestParam(name = "nomeLayer") String nomeLayer) {

		String idLog = "getAllLayerPermessi";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<PermessoMappaDto> mappaPermessiList = ilayerService.getAllLayerPermessi(idMappa, nomeLayer);
			response = new ResponseData(true, mappaPermessiList, mappaPermessiList.size(), mappaPermessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMapRicerche", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping(value="/countPermessiLayer", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getAllLayerPermessi(HttpServletRequest request, @RequestParam(name = "nomeLayer") String nomeLayer) {

		String idLog = "countPermessiLayer";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			List<UMappaLayer> mappaPermessiList = ilayerService.countPermessiLayer(nomeLayer);
			response = new ResponseData(true, mappaPermessiList, mappaPermessiList.size(), mappaPermessiList.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in countPermessiLayer", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@RequestMapping(value="/insertPermessiLayer", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertPermessiLayer(HttpServletRequest request, @RequestBody List<PermessoMappaDto> permessi) {
		
		String idLog = "insertPermessiLayer";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		
		try {
			
			List<PermessoMappaDto> result = ilayerService.insertPermessi(permessi);
			
			if(result != null ) {
				response = new ResponseData(true, result);
				response.setMessage("success");
			} else {
				response = new ResponseData(false, null);
				response.setMessage("error");
			}
			
		} catch (Exception e) {
			logger.error("Errore in insertPermessi", e);
			response = new ResponseData(false, null);
			response.setMessage("error");
		}
		
		return response;
	}
	
	
	@RequestMapping(value="/updateZoomShowCat", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData updateZoomShowCat(@RequestBody UMappa entity,HttpServletRequest request) {
		logger.debug("POST updateZoomShowCat");
		ResponseData response = null;
		 
		try {
		 
			int res=imappaService.updateZoomAndShowCat(entity);
			if(res>0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage("success");
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("mappa duplica:: 0");	
			}
		} catch (Exception e) {
			logger.error("Error in updateZoomShowCat", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
}
