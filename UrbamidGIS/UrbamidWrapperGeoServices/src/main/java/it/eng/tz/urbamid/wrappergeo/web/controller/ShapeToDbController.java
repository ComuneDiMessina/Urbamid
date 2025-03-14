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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.wrappergeo.service.ConvertShapefileService;
import it.eng.tz.urbamid.wrappergeo.web.response.ResponseData;

@RestController
@RequestMapping("/wrappergeo/rest/api/shapeToDb")
@Api(value = "urbamid converter", tags= {"ConverterWrapperGeo"})
public class ShapeToDbController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(ShapeToDbController.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private ConvertShapefileService convertShapefileService;
	
	@ApiOperation("Converte uno shape file presente al path in tabella su db")
	@RequestMapping(value="/converter", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData converter(HttpServletRequest request,
			@ApiParam(value="Nome dello shape file da covertire", required=true)
			@RequestParam(name="nameShapeFile", required=true) String nameShapeFile) {
		
		logger.debug("POST converter");
		ResponseData response = null;
		try {
			
			String logImporta = convertShapefileService.importaShapefile(nameShapeFile);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("POST REQUEST eseguita con successo. Convertito correttamente lo shapeFile {}.", nameShapeFile);
		} catch (Exception e) {
			
			logger.error("Error in converter", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
		
	}	
}
