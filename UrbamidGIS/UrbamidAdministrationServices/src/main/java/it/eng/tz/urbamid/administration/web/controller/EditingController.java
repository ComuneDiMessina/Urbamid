package it.eng.tz.urbamid.administration.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.administration.filter.LayersFilter;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerAttributiBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerAttributi;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerGeometrie;
import it.eng.tz.urbamid.administration.mappa.service.IEditingLayerService;
import it.eng.tz.urbamid.administration.web.dto.PagedResult;
import it.eng.tz.urbamid.administration.web.response.ResponseData;

@RestController
@RequestMapping("/administration/editing")
public class EditingController extends AbstractController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SUCCESS = "Success";
	private static final String ERROR = "Error";
	
	@Autowired
	private IEditingLayerService service;
	
	/**
	 * Il metodo effettua la ricerca dei layer passandogli un oggetto filtro
	 * @param rquest
	 * @param layers
	 * @return
	 */
	@ApiOperation("Il metodo si occupa della visualizzazione e della ricerca dei layer")
	@RequestMapping(value = "/findAll", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	public @ResponseBody ResponseData findAll(HttpServletRequest rquest, 
			@ApiParam(value = "Filtri ricerca", required = true)
			@RequestBody(required = true) LayersFilter filter) {
		
		String idLog = "POST findAll";
		ResponseData response = null;
		
		try {
			
			if(filter != null) {
				
				PagedResult<LayerAttributi> lista = service.findAllLayers(filter);
				
				logger.info(SUCCESS);
				
				response = new ResponseData(true, lista.getContent(), (int) lista.getTotalElements(), (int) lista.getTotalElements(), null);
				response.setMessage(SUCCESS);	
			} else {
				
				logger.error("ERROR {}", idLog);
				
				response = new ResponseData(false, null, 0, 0, "Errore nella visualizzazione dei layers!");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE findAll: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua la ricerca dei layer passandogli un oggetto filtro
	 * @param rquest
	 * @param layers
	 * @return
	 */
	@ApiOperation("Il metodo si occupa della visualizzazione e della ricerca dei layer")
	@RequestMapping(value = "/findAllGeometry", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	public @ResponseBody ResponseData findAllGeometry(HttpServletRequest rquest, 
			@ApiParam(value = "Identificativo del layer", required = true)
			@RequestParam Long idLayer) {
		
		String idLog = "POST findAllGeometry";
		ResponseData response = null;
		
		try {
			
			if(idLayer != null) {
				
				List<LayerGeometrie> result = service.findAllGeometry(idLayer);
				
				logger.info(SUCCESS);
				
				response = new ResponseData(true, result, result.size(), result.size(), null);
				response.setMessage(SUCCESS);	
			} else {
				
				logger.error("ERROR {}", idLog);
				
				response = new ResponseData(false, null, 0, 0, "Errore nella visualizzazione delle geometrie del layer!");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE findAllGeometry: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua l'inserimento dei layer creati e salva i dati nelle tabelle u_admin_edtl_layers (attributi) e u_admin_edtl_layers_attr (geometrie)
	 * @param rquest
	 * @param layers
	 * @return
	 */
	@ApiOperation("Il metodo si occupa dell'inserimento del layer creato")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest rquest, 
			@ApiParam(value = "Livello", required = true)
			@RequestBody(required = true) LayerAttributiBean layers) {
		
		String idLog = "POST insertOrUpdate";
		ResponseData response = null;
		
		try {
			
			if(layers != null) {
				
				LayerAttributiBean result = service.insertEditingLayer(layers);
				
				logger.info(SUCCESS);
				
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);	
			} else {
				
				logger.error("ERROR {}", idLog);
				
				response = new ResponseData(false, null, 0, 0, "Errore nell'inserimento!");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE insertOrUpdate: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua la cancellazione del later dalle tabelle u_admin_edtl_layers (attributi) e u_admin_edtl_layers_attr (geometrie)
	 * @param rquest
	 * @param layers
	 * @return
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione del layer")
	@RequestMapping(value = "/deleteLayer", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	public @ResponseBody ResponseData deleteEditingLayer(HttpServletRequest rquest, 
			@ApiParam(value = "ID livello", required = true)
			@RequestParam(value = "idLayer") Long idLayer) {
		
		String idLog = "POST deleteEditingLayer";
		ResponseData response = null;
		
		try {
			
			if(idLayer != null) {
				
				service.deleteEditingLayer(idLayer);
				
				logger.info(SUCCESS);
				
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage(SUCCESS);	
			} else {
				
				logger.error("ERROR {}", idLog);
				
				response = new ResponseData(false, null, 0, 0, "Errore nell'eliminazione!");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE deleteEditingLayer: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua la cancellazione della geometria del layer dalla tabella u_admin_edtl_layers_attr
	 * @param rquest
	 * @param layers
	 * @return
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione della geometria del layer")
	@RequestMapping(value = "/deleteGeometria", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	public @ResponseBody ResponseData deleteEditingGeometria(HttpServletRequest rquest, 
			@ApiParam(value = "Identificativo geometria", required = true)
			@RequestParam(value = "idGeometria") Long idGeometria) {
		
		String idLog = "POST deleteEditingGeometria";
		ResponseData response = null;
		
		try {
			
			if(idGeometria != null) {
				
				service.deleteEditingGeometria(idGeometria);
				
				logger.info(SUCCESS);
				
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage(SUCCESS);	
			} else {
				
				logger.error("ERROR {}", idLog);
				
				response = new ResponseData(false, null, 0, 0, "Errore nell'eliminazione della geometria!");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE deleteEditingGeometria: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	@ApiOperation("Il metodo si occupa del conteggio dei layer in base all'identificativo")
	@RequestMapping(value = "/countLayerByIdentificativo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST )
	public @ResponseBody ResponseData countLayerByIdentificativo(HttpServletRequest rquest, 
			@ApiParam(value = "Identificativo layer", required = true)
			@RequestParam(value = "identificativo") String identificativo) {
		
		String idLog = "POST countLayerByIdentificativo";
		ResponseData response = null;
		
		try {
			
			if(identificativo != null) {
				
				int result = service.countLayerByIdentificativo(identificativo);
				
				logger.info(SUCCESS);
				
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);	
			} else {
				
				logger.error("ERROR {}", idLog);
				
				response = new ResponseData(false, null, 0, 0, "Errore nel conteggio dei layer!");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE countLayerByIdentificativo: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
}
