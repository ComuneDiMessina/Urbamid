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
import it.eng.tz.urbamid.toponomastica.service.TipoAccessoService;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoAccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/tipoAccesso")
@Api(value = "urbamid toponomastica", tags= {"TipoAccesso"})
public class TipoAccessoController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(TipoAccessoController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private TipoAccessoService service;
	
	/**
	 * Metodo che recupera i tipi di accesso dalla tabella u_topo_tipo_accesso
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero dei tipi di accesso")
	@RequestMapping(value = "/getTipoAccesso", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTipoAccesso(HttpServletRequest request) throws Exception {
		
		logger.debug("GET getTipoAccesso");
		ResponseData response = null;
		
		try {
			
			List<TipoAccessoDTO> listaTipoAccesso;
			
			listaTipoAccesso = service.findAll();
			response = new ResponseData(true, listaTipoAccesso, listaTipoAccesso.size(), listaTipoAccesso.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getTipoAccesso: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
