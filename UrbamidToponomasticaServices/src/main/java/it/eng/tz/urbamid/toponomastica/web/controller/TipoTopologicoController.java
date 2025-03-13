package it.eng.tz.urbamid.toponomastica.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.toponomastica.service.TipoTopologicoService;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoTopologicoDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("toponomastica/tipoTopologico")
@Api(value = "urbamid toponomastica", tags={"TipoTopologico"})
public class TipoTopologicoController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(TipoTopologicoController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private TipoTopologicoService service;
	
	@ApiOperation("Il metodo si occupa del recupero dei tipi topologici")
	@RequestMapping(value = "/getTipiTopologici", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTipiTopologici(HttpServletRequest request) {
		
		logger.debug("POST getTipiTopologici");
		ResponseData response = null;
		
		try {
			
			List<TipoTopologicoDTO> lista = service.findAll();
			response = new ResponseData(true, lista, lista.size(), lista.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			
			logger.error("ERRORE getTipiTopologici: ", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
	}
	
}
