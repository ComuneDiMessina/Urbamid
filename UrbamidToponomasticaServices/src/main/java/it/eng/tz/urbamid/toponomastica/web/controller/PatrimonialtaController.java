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
import it.eng.tz.urbamid.toponomastica.service.PatrimonialitaService;
import it.eng.tz.urbamid.toponomastica.web.dto.PatrimonialitaDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/patrimonialita")
@Api(value = "urbamid toponomastica", tags= {"Patrimonialita"})
public class PatrimonialtaController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(TipoLocalitaController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private PatrimonialitaService service;
	
	/**
	 * Metodo che recupera le patrimonialità dalla tabella u_topo_patrimonialita
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Metodo si occupa del recupero della patrimonialità")
	@RequestMapping(value="/getPatrimonialita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getPatrimonialita(HttpServletRequest request) throws Exception {
		
		logger.debug("GET getPatrimonialita");
		ResponseData response = null;
		
		try {
			
			List<PatrimonialitaDTO> listaPatrimonialita;
			
			listaPatrimonialita = service.findAll();
			response = new ResponseData(true, listaPatrimonialita, listaPatrimonialita.size(), listaPatrimonialita.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Errore: getPatrimonialita", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
