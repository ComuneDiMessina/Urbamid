package it.eng.tz.urbamid.web.pageController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.GeometriaLayerCompletoDTO;
import it.eng.tz.urbamid.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.web.services.WebGisServices;

/**
 * @author Alessandro Paolillo
 */
@Controller(value = "WebGis Controller")
@RequestMapping("/webGisController")
public class WebGisCtrl extends AbstractController  {

	private static final Logger logger = LoggerFactory.getLogger(WebGisCtrl.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private WebGisServices webGisServices;
	
	@RequestMapping(value = "/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@RequestMapping(value="/findGeometryLayerByWkt", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findGeometryLayerByWkt(HttpServletRequest request) {
		logger.debug("POST findGeometryLayer");
		ResponseData response = null;
		String wktGeom = null; 
		if(request.getParameter("wktGeom")!= null){
			wktGeom = request.getParameter("wktGeom");
		}
		String layerName = null; 
		if(request.getParameter("layerName")!= null){
			layerName = request.getParameter("layerName");
		}
		try {
			List<GeometriaLayerCompletoDTO> res = webGisServices.findGeometryLayerByWkt( layerName, wktGeom );
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findGeometryLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findGeometryLayer", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findGeometryLayer(HttpServletRequest request) {
		
		logger.debug("POST findGeometryLayer");
		ResponseData response = null;
		String wktGeom = null; 
		if(request.getParameter("wktGeom")!= null){
			
			wktGeom = request.getParameter("wktGeom");
		}
		String layerName = null; 
		if(request.getParameter("layerName")!= null){
			
			layerName = request.getParameter("layerName");
		}
		try {
			
			List<GeometriaLayerDTO> res = webGisServices.findGeometryLayer( layerName );
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Error in findGeometryLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
}
