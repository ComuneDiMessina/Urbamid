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
import it.eng.tz.urbamid.toponomastica.service.TipoLocalitaService;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/tipoLocalita")
@Api(value = "urbamid toponomastica", tags= {"TipoLocalita"})
public class TipoLocalitaController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(TipoLocalitaController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private TipoLocalitaService tipoLocalitaService;
	
	/**
	 * Metodo che recupera i tipi di località dalla tabella u_topo_tipo_localita
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero dei tipi di localita")
	@RequestMapping(value="/getTipoLocalita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTipoLocalita(HttpServletRequest request) throws Exception {
		
		logger.debug("GET getTipoLocalita");
		ResponseData response = null;
		
		try {
			
			List<TipoLocalitaDTO> listaTipoLocalita;
			
			listaTipoLocalita = tipoLocalitaService.findAll();
			response = new ResponseData(true, listaTipoLocalita, listaTipoLocalita.size(), listaTipoLocalita.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getTipoLocalita: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
