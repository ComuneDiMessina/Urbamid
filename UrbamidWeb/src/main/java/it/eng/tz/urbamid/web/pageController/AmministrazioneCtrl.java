package it.eng.tz.urbamid.web.pageController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.web.dto.LayerAttributiDTO;
import it.eng.tz.urbamid.web.dto.LayerGeometrieDTO;
import it.eng.tz.urbamid.web.dto.MenuFunzionalitaDto;
import it.eng.tz.urbamid.web.filter.LayersFilter;
import it.eng.tz.urbamid.web.services.AdministrationServices;

/**
 * @author Alessandro Paolillo
 */
@Controller(value = "AmministrazioneController")
@RequestMapping(value = "/adminController", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AmministrazioneCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(AmministrazioneCtrl.class.getName());
	
	@Autowired
	private AdministrationServices administrationServices;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	@GetMapping("/getFunzionalita")
	public @ResponseBody ResponseData getFunzionalita(HttpServletRequest request) {

		String idLog = "getFunzionalita";
		logger.info("Start {}", idLog);
		List<String> authorities = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) 
        {
        	authorities = new ArrayList<String>();
        	for (GrantedAuthority auth : authentication.getAuthorities()) {
        		authorities.add(auth.getAuthority());
            }
        }
        if (!authorities.isEmpty()) {
			for(String authority : authorities) {logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>authority:"+authority);}
		}else logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>authorities is empty");
		ResponseData response = null;
		try {
			List<FunzionalitaDto> res = administrationServices.getFunzionalita(authorities);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getRuoloFunzionalita", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@GetMapping("/getMenuFunzionalita")
	public @ResponseBody ResponseData getMenuFunzionalita(HttpServletRequest request) {

		String idLog = "getMenuFunzionalita";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			
			String authority = new String(); 
			if(request.getParameter("authority")!= null){
				authority = request.getParameter("authority");
			}
			
			MenuFunzionalitaDto res = administrationServices.getMenuFunzionalita(authority);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMenuFunzionalita", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	@PostMapping("/insertOrUpdate")
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, @RequestBody LayerAttributiDTO dto) {

		String idLog = "insertOrUpdate";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			
			if(dto != null) {
				
				administrationServices.insertOrUpdateLayer(dto);
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage("success");
			} else {
				logger.error("Error in insertOrUpdate");
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("error");
				
			}
			
			
		} catch (Exception e) {
			logger.error("Error in insertOrUpdate", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage("error");
		}
		return response;
	}
	
	@PostMapping("/countLayerByIdentificativo")
	public @ResponseBody ResponseData countLayerByIdentificativo(HttpServletRequest request, @RequestParam String identificativo) {

		String idLog = "countLayerByIdentificativo";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			
			if(identificativo != null) {
				
				int result = administrationServices.countLayerByIdentificativo(identificativo);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage("success");
			} else {
				logger.error("Error in countLayerByIdentificativo");
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("error");
				
			}
			
			
		} catch (Exception e) {
			logger.error("Error in countLayerByIdentificativo", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage("error");
		}
		return response;
		
	}
	
	@PostMapping("/findAllLayers")
	public @ResponseBody DataTableDTO findAllLayer(HttpServletRequest request, @RequestBody LayersFilter filter) {

		String idLog = "insertOrUpdate";
		logger.info("Start {}", idLog);
		DataTableDTO response = null;
		try {
			
			if(filter != null) {
				
				response = administrationServices.findAllLayers(filter);
			} else {
				logger.error("Error in findAllLayer");
				
			}
			
			
		} catch (Exception e) {
			logger.error("Error in findAllLayer", e);
			
		}
		
		return response;
	}
	
	@GetMapping("/findAllGeometry")
	public @ResponseBody ResponseData findAllGeometry(HttpServletRequest request, @RequestParam Long idLayer) {

		String idLog = "findAllGeometry";
		logger.info("Start {}", idLog);
		
		ResponseData response = null;
		try {
			
			if(idLayer != null) {
				
				List<LayerGeometrieDTO> result = administrationServices.findAllGeometry(idLayer);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage("success");
			} else {
				logger.error("Error in findAllGeometry");
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("error");
			
			}
			
			
		} catch (Exception e) {
			logger.error("Error in findAllGeometry", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage("error");
		
		}
		
		return response;
	}
	
	@PostMapping("/eliminaLayer")
	public @ResponseBody ResponseData eliminaLayer(HttpServletRequest request, @RequestBody LayerAttributiDTO dto) {

		String idLog = "eliminaLayer";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			
			if(dto != null) {
				
				administrationServices.eliminaLayer(dto);
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage("success");
			} else {
				logger.error("Error in eliminaLayer");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage("error");
				
			}
			
			
		} catch (Exception e) {
			logger.error("Error in eliminaLayer", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage("error");
			
		}
		
		return response;
	}
	
	@PostMapping("/eliminaGeometria")
	public @ResponseBody ResponseData eliminaGeometria(HttpServletRequest request, @RequestParam Long idGeometria) {

		String idLog = "eliminaGeometria";
		logger.info("Start {}", idLog);
		
		ResponseData response = null;
		try {
			
			if(idGeometria != null) {
				
				administrationServices.eliminaGeometria(idGeometria);
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage("success");
			} else {
				logger.error("Error in eliminaGeometria");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage("error");
				
			}
			
			
		} catch (Exception e) {
			logger.error("Error in eliminaGeometria", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage("error");
			
		}
		
		return response;
	}
	
}
