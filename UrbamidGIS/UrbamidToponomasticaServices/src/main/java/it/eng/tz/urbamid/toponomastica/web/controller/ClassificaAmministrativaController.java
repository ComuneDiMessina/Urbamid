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
import it.eng.tz.urbamid.toponomastica.service.ClassificaAmministrativaService;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

/**
* @author Luca Tricarico
*/
@RestController
@RequestMapping("/toponomastica/classificaAmministrativa")
@Api(value = "urbamid toponomastica", tags= {"ClassificaAmministrativa"})
public class ClassificaAmministrativaController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassificaAmministrativaController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private ClassificaAmministrativaService service;
	
	/**
	 * Metodo che recupera le classifiche amministrative dalla tabella u_topo_classifica_amministrativa
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero delle classifiche amministrative")
	@RequestMapping(value="/getClassificaAmministrativa", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getClassificaAmministrativa(HttpServletRequest request) throws Exception {
		
		logger.debug("GET getClassificaAmministrativa");
		ResponseData response = null;
		
		try {
			
			List<ClassificaAmministrativaDTO> listaClassificaAmministrativa;
			
			listaClassificaAmministrativa = service.findAll();
			response = new ResponseData(true, listaClassificaAmministrativa, listaClassificaAmministrativa.size(), listaClassificaAmministrativa.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getClassificaAmministrativa: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}

}
