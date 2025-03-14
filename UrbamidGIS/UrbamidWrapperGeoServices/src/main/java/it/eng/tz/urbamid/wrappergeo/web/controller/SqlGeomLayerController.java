package it.eng.tz.urbamid.wrappergeo.web.controller;

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

import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.wrappergeo.service.SqlGeomLayerService;
import it.eng.tz.urbamid.wrappergeo.web.entities.FeatureGeomDTO;
import it.eng.tz.urbamid.wrappergeo.web.response.ResponseData;

@RestController
@RequestMapping("/rest/api/findFeature")
public class SqlGeomLayerController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(SqlGeomLayerController.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private SqlGeomLayerService sqlGeomLayerService;
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@RequestMapping(value="/findFeatureLayerByGeom", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findFeatureLayerByGeom(HttpServletRequest request,
			@RequestParam(name="tableName") String tableName,
			@RequestParam(name="wktGeom") String wktGeom) {
		logger.debug("POST findFeatureLayerByGeom");
		ResponseData response = null;
		try {
			FeatureGeomDTO feature;
			feature = sqlGeomLayerService.findFeatureLayerWithGeom(tableName, wktGeom);
			response = new ResponseData(true, feature, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findFeatureLayerByGeom", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}	
}
