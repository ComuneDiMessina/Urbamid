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
import it.eng.tz.urbamid.toponomastica.service.ClassificaFunzionaleService;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

/**
 * @author Luca Tricarico
 */

@RestController
@RequestMapping("/toponomastica/classificaFunzionale")
@Api(value = "urbamid toponomastica", tags= {"ClassificaFunzionale"})
public class ClassificaFunzionaleController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ClassificaFunzionaleController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private ClassificaFunzionaleService service;
	
	
	/**
	 * Metodo che recupera le classifiche funzionale dalla tabella u_topo_classifica_funzionale
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero delle classifiche funzionali")
	@RequestMapping(value="/getClassificaFunzionale", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getClassificaFunzionale(HttpServletRequest request) throws Exception {
		
		logger.debug("GET getClassificaFunzionale");
		ResponseData response = null;
		
		try {
			
			List<ClassificaFunzionaleDTO> listaClassificaFunzionale;
			
			listaClassificaFunzionale = service.findAll();
			response = new ResponseData(true, listaClassificaFunzionale, listaClassificaFunzionale.size(), listaClassificaFunzionale.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("ERRORE getClassificaFunzionale: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);	
		}
		
		return response;
		
	}
	
}
