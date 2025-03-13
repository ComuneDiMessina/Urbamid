package it.eng.tz.urbamid.wrappergeo.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.wrappergeo.geoserver.service.GeoserverRicercheService;
import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerCompleteDTO;
import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.wrappergeo.web.response.ResponseData;

@RestController
@RequestMapping("/wrappergeo/")
@Api(value = "urbamid geoserver", tags= {"GeoserverWrapperGeo"})
public class RicercheGeoserverController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(RicercheGeoserverController.class.getName());
	
	public static final String SUCCESS 	= "Success";
	public static final String ERROR 	= "Error";
	
	@Autowired
	private GeoserverRicercheService geoserverRicercheService;
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@RequestMapping(value="/findGeometriaLayer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findGeometriaLayer(HttpServletRequest request,
			@RequestParam(name="layerName") String layerName) {
		logger.debug("POST findGeometriaLayer");
		ResponseData response = null;
		try {
			List<GeometriaLayerDTO> geometrie = geoserverRicercheService.findGeometriaLayer(layerName);
			response = new ResponseData(true, geometrie, geometrie.size(), geometrie.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findGeometriaLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@RequestMapping(value="/findGeometriaLayerByWkt", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findGeometriaLayerByWkt(HttpServletRequest request,
			@RequestParam(name="layerName") String layerName,
			@RequestParam(name="wktGeom") String wktGeom) {
		logger.debug("POST findGeometriaLayerByWkt");
		ResponseData response = null;
		try {
			GeometriaLayerCompleteDTO geometria = geoserverRicercheService.findGeometriaLayerByWkt(layerName, wktGeom);
			response = new ResponseData(true, geometria, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findGeometriaLayerByWkt", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}
}
