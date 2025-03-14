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
import it.eng.tz.urbamid.toponomastica.service.TipoFunzionaleService;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("toponomastica/tipoFunzionale")
@Api(value = "urbamid toponomastica", tags={"TipoFunzionale"})
public class TipoFunzionaleController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(TipoFunzionaleController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private TipoFunzionaleService service;
	
	@ApiOperation("Il metodo si occupa del recupero dei tipi funzionali")
	@RequestMapping(value = "/getTipiFunzionali", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTipiFunzionali(HttpServletRequest request) {
		
		logger.debug("POST getTipiFunzionali");
		ResponseData response = null;
		
		try {
			
			List<TipoFunzionaleDTO> lista = service.findAll();
			response = new ResponseData(true, lista, lista.size(), lista.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			
			logger.error("ERRORE getTipiFunzionali: ", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
	}
	
}
