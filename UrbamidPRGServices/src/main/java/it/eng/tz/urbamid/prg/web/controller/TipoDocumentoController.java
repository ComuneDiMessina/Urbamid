package it.eng.tz.urbamid.prg.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.prg.filter.TipoDocumentoFilter;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.service.TipoDocumentoService;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoVarianteDTO;
import it.eng.tz.urbamid.prg.web.response.ResponseData;


@RestController
@RequestMapping("/prg/rest/api/tipodocumento")
@Api(value = "urbamid codice", tags= {"Tipo Documento" })
public class TipoDocumentoController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(VarianteRESTController.class.getName());

	private static final String SUCCESS = "Success";
	private static final String ERROR = "Error";

	@Autowired
	private TipoDocumentoService service;

	@ApiOperation("Recupera tutti i tipi di documento in base ai filtri di ricerca")
	@GetMapping("/getTipoDocumenti")
	public @ResponseBody ResponseData getDocumentiDataTable(HttpServletRequest request, @RequestParam(value = "codice", required = false) String codice,
																			   @RequestParam(value = "descrizione", required = false) String descrizione,
																			   @RequestParam(value = "descrizioneCDU", required = false) String descrizioneCDU,
																			   @RequestParam(value = "note", required = false) String note, 
																			   @RequestParam("page") int page,
																			   @RequestParam("size") int size, 
																			   @RequestParam("draw") int draw) {

		ResponseData response = null;

		logger.debug("getTipoDocumenti");

		try {

			TipoDocumentoFilter filter = new TipoDocumentoFilter();

			filter.setCodice(codice);
			filter.setDescrizione(descrizione);
			filter.setDescrizioneCDU(descrizioneCDU);
			filter.setNote(note);
			filter.setPageIndex(page);
			filter.setPageSize(size);

			PagedResult<TipoDocumentoDTO> listaDocumenti = service.getDocumenti(filter);

			response = new ResponseData(true, listaDocumenti.getContent(), (int) listaDocumenti.getTotalElements(), (int) listaDocumenti.getTotalElements(), null);

			response.setMessage(SUCCESS);

		} catch (Exception e) {

			logger.debug("Errore in getTipoDocumenti {}", e);

			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);

		}

		return response;

	}
	
	@ApiOperation("Recupera tutti i tipi di documento")
	@RequestMapping(value = "/findAllTipoDocumento", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTipoDocumenti(HttpServletRequest request) {
		
		logger.debug("findTipoDocumenti");
		ResponseData response = null;

		try {

			List<TipoDocumento> result = service.findTipoDocumenti();
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);

		} catch (Exception e) {

			logger.debug("Errore in findTipoDocumenti {}", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);

		}

		return response;
		
	}
	
	@ApiOperation("Recupera il nome della variante in base al tipo di documento")
	@RequestMapping(value = "/varianteByTipoDocumento", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData varianteByTipoDocumento(HttpServletRequest request, 
			@ApiParam(value = "Tipo di documento", required = true)
			@RequestParam String tipoDocumento) {
		
		logger.debug("varianteByTipoDocumento");
		ResponseData response = null;

		try {

			List<TipoDocumentoVarianteDTO> result = service.varianteByTipoDocumento(tipoDocumento);
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);

		} catch (Exception e) {

			logger.debug("Errore in varianteByTipoDocumento {}", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);

		}

		return response;
		
	}

	@ApiOperation("Inserisce o modifica un tipo documento")
	@RequestMapping(value = "/insertOrUpdateTipoDocumento", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertDocumento(HttpServletRequest request, 
			@ApiParam( value = "Tipo di documento", required = true)
			@RequestBody TipoDocumentoDTO documento) {

		logger.debug("insertTipoDocumento");

		ResponseData response = null;

		try {
				
			service.insertOrUpdateDocumento(documento);
				
			response = new ResponseData(true, documento, 1, 1, null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {

			logger.debug("Errore in insertOrUpdateTipoDocumento {}", e);

			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);

		}

		return response;

	}
	
	@ApiOperation("Conta quanti tipi documenti ci sono")
	@GetMapping("/countTipoDocumento")
	public @ResponseBody ResponseData countTipoDocumento(HttpServletRequest request, 
			@ApiParam( value = "codice del tipo di documento", required = true)
			@RequestParam String codice) {

		logger.debug("countTipoDocumento");
		ResponseData response = null;

		try {

			int result = service.countCodiceDocumento(codice);
			response = new ResponseData(true, result);
			response.setMessage(SUCCESS);

		} catch (Exception e) {

			logger.debug("Errore in countTipoDocumento {}", e);
			response = new ResponseData(false, null);
			response.setMessage(ERROR);

		}

		return response;

	}

	@ApiOperation("Cancella un tipo di documento")
	@GetMapping("/cancellaTipoDocumento")
	public @ResponseBody ResponseData cancellaDocumento(HttpServletRequest request, 
			@ApiParam( value = "Indentificativo del tipo di documento", required = false)
			@RequestParam Long id) {

		logger.debug("cancellaDocumento");
		ResponseData response = null;

		try {

			service.deleteDocumento(id);
			response = new ResponseData(true, null);
			response.setMessage(SUCCESS);

		} catch (Exception e) {

			logger.debug("Errore in cancellaDocumento {}", e);
			response = new ResponseData(false, null);
			response.setMessage(ERROR);

		}

		return response;

	}

	@ApiOperation("Recupera i documenti associati alla variante")
	@GetMapping("/cercaTipiDocumentoMancanti")
	public @ResponseBody ResponseData cercaTipiDocumentoMancanti(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaDocumentiByIdVariante");
		ResponseData response = null;
		try {
			List<TipoDocumento> result = service.cercaTipiDocumentoMancanti(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaTipiDocumentoMancanti", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
