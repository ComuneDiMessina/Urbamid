package it.eng.tz.urbamid.toponomastica.web.controller;

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
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.service.DugService;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/dug")
@Api(value = "urbamid toponomastica", tags= {"Dug"})
public class DugController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(DugController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private DugService dugService;
	
	/**
	 * Metodo che ricerca il toponimo stradale dalla tabella u_topo_dug, passandogli {@link dug}
	 * @param request
	 * @param dug, è la descrizione del DUG
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di ricercare i DUG")
	@RequestMapping(value="/getDug", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getDug(HttpServletRequest request, 
			@ApiParam(value = "Descrizione del DUG", required = true)
			@RequestParam(name = "DUG") String dug) throws Exception {
		
		logger.debug("POST getDug");
		ResponseData response = null;

		try {
			
			List<DugDTO> listaDUG;
			
			listaDUG = dugService.findAll(dug.toUpperCase());
			response = new ResponseData(true, listaDUG, listaDUG.size(), listaDUG.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getDug: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
