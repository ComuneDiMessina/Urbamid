package it.eng.tz.urbamid.toponomastica.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.EstesaAmministrativaFilter;
import it.eng.tz.urbamid.toponomastica.service.EstesaAmministrativaService;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

/**
* @author Luca Tricarico
*/

@RestController
@RequestMapping("/toponomastica/estesaAmministrativa")
@Api(value = "urbamid toponomastica", tags= {"EstesaAmministrativa"})
public class EstesaAmministrativaController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(EstesaAmministrativaController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private EstesaAmministrativaService service;
	
	/**
	 * Metodo che recupera in maniera paginata la tabella u_topo_estesa_amministrativa passandogli {@link filter} come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero delle estese amministrative con i filtri di ricerca")
	@RequestMapping(value = "/getEstesaAmministrativa", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getEstesaAmministrativa(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody EstesaAmministrativaFilter filter) throws ConverterException {
		
		logger.debug("POST getEstesaAmministrativa");
		ResponseData response = null;
		
		try {
			
			PagedResult<EstesaAmministrativaDTO> listaEstesaAmministrativa = service.getEstesaAmministrativa(filter);
			
			response = new ResponseData(true, listaEstesaAmministrativa.getContent(), (int) listaEstesaAmministrativa.getTotalElements(), (int) listaEstesaAmministrativa.getTotalElements(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getEstesaAmministrativa", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo ricerca l'estesa amministrativa dalla tabella u_topo_estesa_amministrativa, passandogli come input {@link descrizione}
	 * @param request
	 * @param descrizione, è la descrizione dell'estesa amministrativa
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa di ricercare l'estesa amministrativa")
	@RequestMapping(value="/estesaAmministrativaAutocomplete", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData findAll(HttpServletRequest request, 
			@ApiParam(value = "Descrizione dell'estesa amministrativa", required = true)
			@RequestParam(name = "DESCRIZIONE", required = true) String descrizione) throws Exception {
		
		logger.debug("POST getEsteseAmministrative");
		ResponseData response = null;
		
		try {
			
			List<EstesaAmministrativaDTO> lista;
			
			lista = service.findAll(descrizione);
			response = new ResponseData(true, lista, lista.size(), lista.size(), null);
			
			response.setMessage(SUCCESS);

		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getEsteseAmministrative: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo ricerca la sigla dalla tabella u_topo_estesa_amministrativa, passandogli come input {@link id}
	 * @param request
	 * @param id, è l'indentificativo dell'estesa amministrativa
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa di ricercare la sigla dell'estesa amministrativa")
	@RequestMapping(value="/findSiglaById", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData findSiglaById(HttpServletRequest request, 
			@ApiParam(value = "Identificativo dell'estesa amministrativa", required = true)
			@RequestParam(name = "id", required = true) Long id) throws Exception {
		
		logger.debug("POST findSiglaById");
		ResponseData response = null;
		
		try {
			
			List<EstesaAmministrativaDTO> lista;
			
			lista = service.findSigla(id);
			response = new ResponseData(true, lista, lista.size(), lista.size(), null);
			
			response.setMessage(SUCCESS);

		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE findSiglaById: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_estesa_amministrativa passandogli l'oggetto {@link estesa}
	 * @param request
	 * @param estesa, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare l'estesa amministrativa")
	@PostMapping("/insertOrUpdate")
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, 
			@ApiParam(value = "Estesa amministrativa", required = true)
			@RequestBody(required = true) EstesaAmministrativaDTO estesa) {
		
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		
		try {
			
			if(estesa != null) {
				
				EstesaAmministrativaDTO result = service.insertOrUpdate(estesa);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE insertOrUpdate");
				response = new ResponseData(false, null, 0, 0, "ERRORE insertOrUpdate");
				response.setMessage(ERROR);
				
			}
		
		} catch (Exception e) {
			
			logger.error("ERRORE insertOrUpdate", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		
		}
		
		return response;
	}
	
	/**
	 * Il metodo elimina l'estesa amministrativa dalla tabella u_topo_estesa_amministrativa passandogli {@link id} 
	 * @param request
	 * @param id, identificativo dell'estesa amministrativa
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione dell'estesa amministrativa")
	@RequestMapping(value = "/eliminaEstesa", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaToponimo(HttpServletRequest request, 
			@ApiParam(value = "Identificativo dell'estesa amministrativa", required = true)
			@RequestParam(name="id", required = true) Long id) {
		
		logger.debug("POST eliminaEstesa");
		ResponseData response = null;
		
		try {

			if(id != null) {
				
				service.deleteById(id);
				
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
				
				
			} else {
				
				logger.error("ERRORE eliminaEstesa");
				
				response = new ResponseData(false, null, 0, 0, "ERRORE eliminaEstesa");
				response.setMessage(ERROR);
				
			}
			
		} catch (Exception e) {
			
			logger.error("ERRORE eliminaEstesa: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	
}
