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
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.service.EnteGestoreService;
import it.eng.tz.urbamid.toponomastica.web.dto.EnteGestoreDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

/**
* @author Luca Tricarico
*/
@RestController
@RequestMapping("/toponomastica/enteGestore")
@Api(value = "urbamid toponomastica", tags= {"EnteGestore"})
public class EnteGestoreController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(EnteGestoreController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private EnteGestoreService service;
	
	/**
	 * Metodo che recupera gli enti gestore dalla tabella u_topo_ente_gestore
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero degli enti gestore")
	@RequestMapping(value="/getEnteGestore", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getEnteGestore(HttpServletRequest request) throws Exception {
		
		logger.debug("GET getEnteGestore");
		ResponseData response = null;
		
		try {
			
			List<EnteGestoreDTO> listaEnteGestore;
			
			listaEnteGestore = service.findAll();
			response = new ResponseData(true, listaEnteGestore, listaEnteGestore.size(), listaEnteGestore.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getEnteGestore: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}

	
}
