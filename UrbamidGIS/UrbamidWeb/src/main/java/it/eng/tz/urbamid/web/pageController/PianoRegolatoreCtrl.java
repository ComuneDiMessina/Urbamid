package it.eng.tz.urbamid.web.pageController;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.AggiuntaLayerDTO;
import it.eng.tz.urbamid.web.dto.AmbitoRicercaDTO;
import it.eng.tz.urbamid.web.dto.AmbitoVarianteDTO;
import it.eng.tz.urbamid.web.dto.AmbitoVarianteListDTO;
import it.eng.tz.urbamid.web.dto.CartografiaDTO;
import it.eng.tz.urbamid.web.dto.CartografiaVarianteDTO;
import it.eng.tz.urbamid.web.dto.CartografiaVarianteLayerDTO;
import it.eng.tz.urbamid.web.dto.CatalogoGruppoDTO;
import it.eng.tz.urbamid.web.dto.CodiceDTO;
import it.eng.tz.urbamid.web.dto.CodiceZtoDTO;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DocumentoIndiceCodiciDTO;
import it.eng.tz.urbamid.web.dto.DocumentoIndiceDTO;
import it.eng.tz.urbamid.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.web.dto.ImportIndiceDTO;
import it.eng.tz.urbamid.web.dto.InserimentoDocumentoDTO;
import it.eng.tz.urbamid.web.dto.InserimentoIndiceDTO;
import it.eng.tz.urbamid.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.web.dto.TipoDocumentoVarianteDTO;
import it.eng.tz.urbamid.web.dto.VarianteDTO;
import it.eng.tz.urbamid.web.services.PianoRegolatoreService;

@Controller(value = "Piano Regolatore Controller")
@RequestMapping("/prgController")
public class PianoRegolatoreCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(PianoRegolatoreCtrl.class.getName());

	@Autowired
	private PianoRegolatoreService prgService;
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@GetMapping(value="/ricercaVarianti", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaVarianti(HttpServletRequest request, 
			@RequestParam(value="ente", required=false) String ente,
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="descrizione", required=false) String descrizione,
			@RequestParam(value="dataAdozioneDal", required=false) String dataAdozioneDal,
			@RequestParam(value="dataAdozioneAl", required=false) String dataAdozioneAl,
			@RequestParam(value="dataApprovazioneDal", required=false) String dataApprovazioneDal,
			@RequestParam(value="dataApporvazioneAl", required=false) String dataApporvazioneAl,
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaVarianti");
		DataTableDTO response = null;
		try {
			String numColonna = request.getParameter("order[0][column]");
			String dirColonna = request.getParameter("order[0][dir]");
			response = prgService.ricercaVarianti(nome, ente, descrizione, dataAdozioneDal, dataAdozioneAl, dataApprovazioneDal, dataApporvazioneAl, page, size, draw, numColonna, dirColonna);
		} catch (Exception e) {
			logger.error("Error in ricercaVarianti", e);
		}
		return response;
	}
	
	@PostMapping(value="/getAllVariantiByDate", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getAllVariantiByDate(HttpServletRequest request) {
		logger.debug("POST getAllVariantiByDate");
		ResponseData response = null;
		try {
			
			List<VarianteDTO> result = prgService.getAllVariantiByDate( );
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Error in getAllVariantiByDate", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@PostMapping(value="/creaOrSalvaVariante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData creaOrSalvaVariante(HttpServletRequest request, 
			@RequestBody(required = true) VarianteDTO variante) {
		logger.debug("POST creaOrSalvaVariante");
		ResponseData response = null;
		try {
			VarianteDTO result = prgService.creaOrSalvaVariante(variante, getCurrentUser());
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in creaOrSalvaVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cancellaVariante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaVariante(HttpServletRequest request, 
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cancellaVariante");
		ResponseData response = null;
		try {
			VarianteDTO result = prgService.cancellaVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaDocumentiByIdVariante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaDocumentiByIdVariante(HttpServletRequest request, 
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaDocumentiByIdVariante");
		ResponseData response = null;
		try {
			List<DocumentoVarianteDTO> result = prgService.cercaDocumentiByIdVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaDocumentiByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/creaOrSalvaDocumento")
	public @ResponseBody ResponseData creaOrSalvaDocumento(HttpServletRequest request, 
			@ModelAttribute InserimentoDocumentoDTO documento) {
		logger.debug("POST creaOrSalvaDocumento");
		ResponseData response = null;
		try {
			InserimentoDocumentoDTO result = prgService.creaOrSalvaDocumento(documento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in creaOrSalvaDocumento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/ricercaTipoDocumenti", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaDocumenti(HttpServletRequest request, @RequestParam(value = "codice", required = false) String codice, 
																				   @RequestParam(value = "descrizione", required = false) String descrizione, 
																				   @RequestParam(value = "descrizioneCDU", required = false) String descrizioneCDU, 
																				   @RequestParam(value = "note", required = false) String note,
																				   @RequestParam("start") int page,
																				   @RequestParam("length") int size,
																				   @RequestParam("draw") int draw) {
		logger.debug("GET ricercaTipoDocumenti");
		DataTableDTO response = null;
		try {
			
			response = prgService.ricercaDocumenti(codice, descrizione, descrizioneCDU, note, page, size, draw);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaTipoDocumenti: {}", e, e.getMessage());
		}
		return response;
	}
	
	@PostMapping(value="/insertOrUpdateTipoDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdateTipoDocumento(HttpServletRequest request, @RequestBody(required = true) TipoDocumentoDTO documento) {
		logger.debug("POST insertOrUpdateTipoDocumento");
		ResponseData response = null;
		try {
			
			if(documento.getId() != null) {
				TipoDocumentoDTO result = prgService.insertOrUpdateTipoDocumento(documento);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				if(prgService.countTipoDocumento(documento.getCodice().toUpperCase()) != 0) {
					response = new ResponseData(false, null, 0, 0, null);
					response.setMessage(ERROR);
				} else {
					TipoDocumentoDTO result = prgService.insertOrUpdateTipoDocumento(documento);
					response = new ResponseData(true, result, 1, 1, null);
					response.setMessage(SUCCESS);
				}
				
			}
			
		} catch (Exception e) {
			logger.error("Error in insertOrUpdateTipoDocumento {}", e, e.getMessage());
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/findAllTipoDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData findAllTipoDocumento(HttpServletRequest request) {
		logger.debug("findAllTipoDocumento");
		ResponseData response = null;
		try {
			List<TipoDocumentoDTO> result = prgService.findAllTipoDocumento();
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findAllTipoDocumento {}", e, e.getMessage());
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/varianteByTipoDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData varianteByTipoDocumento(HttpServletRequest request, @RequestParam String tipoDocumento) {
		logger.debug("varianteByTipoDocumento");
		ResponseData response = null;
		try {
			List<TipoDocumentoVarianteDTO> result = prgService.varianteByTipoDocumento(tipoDocumento);
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in varianteByTipoDocumento {}", e, e.getMessage());
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/cancellaTipoDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaTipoDocumento(HttpServletRequest request, @RequestParam(name="id") Long id) {
		logger.debug("cancellaTipoDocumento");
		ResponseData response = null;
		try {
			prgService.cancellaTipoDocumento(id);
			response = new ResponseData(true, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaTipoDocumento {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cancellaDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaDocumento(HttpServletRequest request, 
			@RequestParam(name="idDocumento") Long idDocumento) {
		logger.debug("GET cancellaDocumento");
		ResponseData response = null;
		try {
			DocumentoVarianteDTO result = prgService.cancellaDocumento(idDocumento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaDocumento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/downloadDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void downloadDocumento(HttpServletRequest request,
			@RequestParam(name="idDocumento") Long idDocumento, HttpServletResponse response) {
		logger.debug("POST downloadDocumento");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(prgService.downloadDocumento(idDocumento));
		} catch (Exception e) {
			logger.error("Error in downloadDocumento", e);
		}
	}

	@PostMapping(value="/salvaAmbitoVarianteTracciato", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaAmbitoVarianteTracciato(HttpServletRequest request, @RequestBody(required = true) AmbitoVarianteDTO data) {
		logger.debug("POST salvaAmbitoVarianteTracciato");
		ResponseData response = null;
		try {
			AmbitoVarianteDTO result = prgService.salvaAmbitoVarianteTracciato(data);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteTracciato", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaAmbitoVarianteSelezione", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaAmbitoVarianteSelezione(HttpServletRequest request, @RequestBody(required = true) AmbitoVarianteListDTO data) {
		logger.debug("POST salvaAmbitoVarianteSelezione");
		ResponseData response = null;
		try {
			AmbitoVarianteListDTO result = prgService.salvaAmbitoVarianteSelezione(data);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteSelezione", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaAmbitoVarianteSelezioneLayer", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaAmbitoVarianteSelezioneLayer(HttpServletRequest request, @RequestBody(required = true) AmbitoVarianteListDTO data) {
		logger.debug("POST salvaAmbitoVarianteSelezioneLayer");
		ResponseData response = null;
		try {
			AmbitoVarianteListDTO result = prgService.salvaAmbitoVarianteSelezioneLayer(data);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteSelezioneLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaCartografieByIdVariante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaCartografieByIdVariante(HttpServletRequest request, 
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaCartografieByIdVariante");
		ResponseData response = null;
		try {
			List<CartografiaVarianteDTO> result = prgService.cercaCartografieByIdVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCartografieByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaCartografia", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaCartografia(HttpServletRequest request, 
			@RequestBody(required = true) CartografiaDTO variante) {
		logger.debug("POST salvaCartografia");
		ResponseData response = null;
		try {
			CartografiaDTO result = prgService.salvaCartografia(variante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaCartografia", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaAmbitoVarianteRicerca", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaAmbitoVarianteRicerca(HttpServletRequest request, 
			@RequestBody(required = true) List<AmbitoRicercaDTO> lsParticelle) {
		logger.debug("POST salvaAmbitoVarianteRicerca");
		ResponseData response = null;
		try {
			List<AmbitoRicercaDTO> result;
			result = prgService.salvaAmbitoVarianteRicerca(lsParticelle);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteRicerca", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaTipiDocumentoMancanti", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaTipiDocumentoMancanti(HttpServletRequest request, 
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaTipiDocumentoMancanti");
		ResponseData response = null;
		try {
			List<TipoDocumentoDTO> result = prgService.cercaTipiDocumentoMancanti(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaTipiDocumentoMancanti", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaIndiciByIdDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaIndiciByIdDocumento(HttpServletRequest request, 
			@RequestParam(name="idDocumento") Long idDocumento) {
		logger.debug("GET cercaIndiciByIdDocumento");
		ResponseData response = null;
		try {
			List<DocumentoIndiceDTO> result = prgService.cercaIndiciByIdDocumento(idDocumento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaIndiciByIdDocumento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaCodiciByIdIndice", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaCodiciByIdIndice(HttpServletRequest request, 
			@RequestParam(name="idIndice") Long idIndice) {
		logger.debug("GET cercaCodiciByIdIndice");
		ResponseData response = null;
		try {
			List<DocumentoIndiceCodiciDTO> result = prgService.cercaCodiciByIdIndice(idIndice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCodiciByIdIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/cercaCodiciZto", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaCodiciZto(HttpServletRequest request) {
		logger.debug("GET cercaCodiciZto");
		ResponseData response = null;
		try {
			List<CodiceZtoDTO> result = prgService.ricercaCodiceZto();
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCodiciZto", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaIndice")
	public @ResponseBody ResponseData salvaIndice(HttpServletRequest request, 
			@RequestBody(required = true) InserimentoIndiceDTO indice) {
		logger.debug("POST salvaIndice");
		ResponseData response = null;
		try {
			InserimentoIndiceDTO result = prgService.salvaIndice(indice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cancellaIndice", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaIndice(HttpServletRequest request, 
			@RequestParam(name="idIndice") Long idIndice) {
		logger.debug("GET cancellaIndice");
		ResponseData response = null;
		try {
			DocumentoIndiceDTO result = prgService.cancellaIndice(idIndice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/importaIndice")
	public @ResponseBody ResponseData importaIndice(HttpServletRequest request, 
			@ModelAttribute ImportIndiceDTO documento) {
		logger.debug("POST importaIndice");
		ResponseData response = null;
		try {
			ImportIndiceDTO result = prgService.importaIndice(documento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in importaIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/esportaIndice", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void esportaIndice(HttpServletRequest request,
			@RequestParam(name="idDocumento") Long idDocumento, HttpServletResponse response) {
		logger.debug("POST esportaIndice");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(prgService.esportaIndice(idDocumento));
		} catch (Exception e) {
			logger.error("Error in esportaIndice", e);
		}
	}

	@GetMapping(value="/cercaAmbitoByIdVariante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaAmbitoByIdVariante(HttpServletRequest request, 
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaAmbitoByIdVariante");
		ResponseData response = null;
		try {
			List<String> result = prgService.cercaAmbitoByIdVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaAmbitoByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaCodici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaCodici(HttpServletRequest request, 
			@RequestBody(required = true) CodiceDTO codice) {
		logger.debug("POST salvaCodici");
		ResponseData response = null;
		try {
			CodiceDTO result = prgService.salvaCodici(codice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaCodici", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/salvaNuovoGruppo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaNuovoGruppo(HttpServletRequest request, 
			@RequestParam(name="nomeGruppo") String nomeGruppo) {
		logger.debug("GET salvaNuovoGruppo");
		ResponseData response = null;
		try {
			String result = prgService.salvaNuovoGruppo(nomeGruppo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaNuovoGruppo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/reperimentoCatalogoVariante", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData reperimentoCatalogoVariante(HttpServletRequest request) {
		logger.debug("GET reperimentoCatalogoVariante");
		ResponseData response = null;
		try {
			List<CatalogoGruppoDTO> result = prgService.reperimentoCatalogoVariante();
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in reperimentoCatalogoVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/salvaLayer", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaLayer(HttpServletRequest request, 
			@RequestBody(required = true) AggiuntaLayerDTO layer) {
		logger.debug("POST salvaLayer");
		ResponseData response = null;
		try {
			AggiuntaLayerDTO result = prgService.salvaLayer(layer);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cancellaLayer", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaLayer(HttpServletRequest request, 
			@RequestParam(name="idLayer") Long idLayer) {
		logger.debug("GET cancellaLayer");
		ResponseData response = null;
		try {
			String result = prgService.cancellaLayer(idLayer);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cancellaGruppo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaGruppo(HttpServletRequest request, 
			@RequestParam(name="idGruppo") Long idGruppo) {
		logger.debug("GET cancellaGruppo");
		ResponseData response = null;
		try {
			String result = prgService.cancellaGruppo(idGruppo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaGruppo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/varianteByNomeLayer", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData varianteByNomeLayer(HttpServletRequest request, 
			@RequestParam(name="nomeLayer") String nomeLayer) throws Exception {
		logger.debug("GET varianteByNomeLayer");
		ResponseData response = null;
		try {
			List<CartografiaVarianteLayerDTO> result = prgService.varianteByNomeLayer(nomeLayer);
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (UrbamidServiceException ese) {
			logger.error("Error in varianteByNomeLayer", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/downloadCduAnagrafica", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void downloadCduAnagrafica(HttpServletRequest request,
			@RequestParam(name="protocollo") String protocollo, HttpServletResponse response) {
		logger.debug("POST downloadCduAnagrafica");
		try {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(prgService.downloadCduAnagrafica(protocollo));
		} catch (Exception e) {
			
			logger.error("Error in downloadCduAnagrafica", e);
		}
	}
	
	@PostMapping(value="/cancellaCartografia", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaCartografia(HttpServletRequest request, 
			@RequestBody(required = true) CartografiaDTO cartografia) {
		logger.debug("POST cancellaCartografia");
		ResponseData response = null;
		try {
			CartografiaDTO result = prgService.cancellaCartografia(cartografia);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaCartografia", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaProtocollo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaProtocollo(HttpServletRequest request, 
			@RequestParam(name="protocollo") String protocollo) {
		logger.debug("GET cercaProtocollo");
		ResponseData response = null;
		try {
			String result = prgService.cercaProtocollo(protocollo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaProtocollo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/cercaCdu", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO cercaCdu(HttpServletRequest request,
			@RequestParam(value="protocollo", required=false) String protocollo,
			@RequestParam(value="dataCreazione", required=false) String dataCreazione,
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET cercaCdu");
		DataTableDTO response = null;
		try {
			response = prgService.cercaCdu(protocollo, dataCreazione, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in cercaCdu", e);
		}
		return response;
	}

	
	@GetMapping(value="/reperimentoColonneLayer", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData reperimentoColonneLayer(HttpServletRequest request,
			@RequestParam(name="nomeLayer") String nomeLayer) {
		logger.debug("GET reperimentoColonneLayer");
		ResponseData response = null;
		try {
			List<String> result = prgService.reperimentoColonneLayer(nomeLayer);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in reperimentoColonneLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}