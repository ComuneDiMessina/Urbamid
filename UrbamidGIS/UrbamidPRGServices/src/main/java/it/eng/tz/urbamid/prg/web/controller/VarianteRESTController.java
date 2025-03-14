package it.eng.tz.urbamid.prg.web.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.prg.filter.VarianteFilter;
import it.eng.tz.urbamid.prg.persistence.model.Variante;
import it.eng.tz.urbamid.prg.service.VarianteService;
import it.eng.tz.urbamid.prg.util.DateUtils;
import it.eng.tz.urbamid.prg.web.dto.AmbitoRicercaDTO;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteListDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.CodiceDTO;
import it.eng.tz.urbamid.prg.web.dto.CodiceZtoDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceCodiciDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.ImportIndiceByteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoDocumentoByteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoIndiceDTO;
import it.eng.tz.urbamid.prg.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.VarianteDTO;
import it.eng.tz.urbamid.prg.web.response.ResponseData;

@RestController
@RequestMapping("/prg/rest/api/variante")
@Api(value = "urbamid codice", tags= {"Variante"})
public class VarianteRESTController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(VarianteRESTController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private VarianteService varianteService;

	@ApiOperation("Recupera la lista delle categorie catastali")
	@GetMapping("/getVarianti")
	public @ResponseBody ResponseData getVarianti(HttpServletRequest request,
			@RequestParam(value="ente", required=false) String ente,
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="descrizione", required=false) String descrizione,
			@RequestParam(value="dataAdozioneDal", required=false) String dataAdozioneDal,
			@RequestParam(value="dataAdozioneAl", required=false) String dataAdozioneAl,
			@RequestParam(value="dataApprovazioneDal", required=false) String dataApprovazioneDal,
			@RequestParam(value="dataApporvazioneAl", required=false) String dataApporvazioneAl,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw,
			@RequestParam("numColonna") String numColonna,
			@RequestParam("dir") String dir) {
		logger.debug("GET getVarianti");
		ResponseData response = null;
		try {
			VarianteFilter filter = new VarianteFilter();
			filter.setEnte(ente);
			filter.setNome(nome);
			filter.setDescrizione(descrizione);
			filter.setDataAdozioneDal(DateUtils.stringToDate(dataAdozioneDal, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setDataAdozioneAl(DateUtils.stringToDate(dataAdozioneAl, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setDataApprovazioneDal(DateUtils.stringToDate(dataApprovazioneDal, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setDataApprovazioneAl(DateUtils.stringToDate(dataApporvazioneAl, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setPageIndex(page);
			filter.setPageSize(size);
			filter.setNumColonna(numColonna);
			filter.setDir(dir);
			PagedResult<VarianteDTO> listaVarianti = varianteService.getVarianti(filter);
			response = new ResponseData(true, listaVarianti.getContent(), (int)listaVarianti.getTotalElements(), (int)listaVarianti.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getVarianti", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera tutte le varianti ordinate per data data_del_appr")
	@PostMapping("/findAllOrderByDate")
	public @ResponseBody ResponseData findAllOrderByDate(HttpServletRequest request) {
		
		logger.debug("POST findAllOrderByDate");
		ResponseData response = null;
		try {
			
			List<VarianteDTO> result = varianteService.findAllOrderByDate();
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Error in findAllOrderByDate", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	
	@ApiOperation("Crea o modifica la variante")
	@PostMapping("/creaOrSalvaVariante")
	public @ResponseBody ResponseData creaOrSalvaVariante(HttpServletRequest request, 
			@RequestBody(required = true) VarianteDTO variante) {
		logger.debug("POST creaOrSalvaVariante");
		ResponseData response = null;
		try {
			Variante result = varianteService.creaOrSalvaVariante(variante);
			if (result.getStato().equals("NUOVO")) {
				varianteService.aggiornaStoricoNuovo(variante);
			}
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in creaOrSalvaVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Cancella la variante ed il suo storico")
	@GetMapping("/cancellaVariante")
	public @ResponseBody ResponseData cancellaVariante(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cancellaVariante");
		ResponseData response = null;
		try {
			VarianteDTO result = varianteService.cancellaVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera i documenti associati alla variante")
	@GetMapping("/cercaDocumentiByIdVariante")
	public @ResponseBody ResponseData cercaDocumentiByIdVariante(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaDocumentiByIdVariante");
		ResponseData response = null;
		try {
			List<DocumentoVarianteDTO> result = varianteService.cercaDocumentiByIdVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaDocumentiByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Crea o modifica un documento")
	@PostMapping("/creaOrSalvaDocumento")
	public @ResponseBody ResponseData creaOrSalvaDocumento(HttpServletRequest request, 
			@RequestBody(required = true) InserimentoDocumentoByteDTO documento) {
		logger.debug("POST creaOrSalvaDocumento");
		ResponseData response = null;
		try {
			InserimentoDocumentoByteDTO result = varianteService.creaOrSalvaDocumento(documento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in creaOrSalvaDocumento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Cancella il documento associato alla variante")
	@GetMapping("/cancellaDocumento")
	public @ResponseBody ResponseData cancellaDocumento(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idDocumento") Long idDocumento) {
		logger.debug("GET cancellaVariante");
		ResponseData response = null;
		try {
			DocumentoVarianteDTO result = varianteService.cancellaDocumento(idDocumento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaDocumento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta il documento associato alla variante dato l'id del documento")
	@GetMapping("/downloadDocumento")
	public @ResponseBody ResponseData downloadDocumento(HttpServletRequest request, 
			@RequestParam Long idDocumento) {
		logger.debug("POST downloadDocumento");
		ResponseData response = null;
		try {
			File file = varianteService.downloadDocumento(idDocumento);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in downloadDocumento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva l'ambito della variante sul tracciato disegnato su mappa")
	@PostMapping("/salvaAmbitoVarianteTracciato")
	public @ResponseBody ResponseData salvaAmbitoVarianteTracciato(HttpServletRequest request, 
			@RequestBody(required = true) AmbitoVarianteDTO data) {
		logger.debug("POST salvaAmbitoVarianteTracciato");
		ResponseData response = null;
		try {
			AmbitoVarianteDTO result = varianteService.salvaAmbitoVarianteTracciato(data);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteTracciato", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva l'ambito della variante sulla selezione delle particelle")
	@PostMapping("/salvaAmbitoVarianteSelezione")
	public @ResponseBody ResponseData salvaAmbitoVarianteSelezione(HttpServletRequest request, 
			@RequestBody(required = true) AmbitoVarianteListDTO data) {
		logger.debug("POST salvaAmbitoVarianteSelezione");
		ResponseData response = null;
		try {
			AmbitoVarianteListDTO result = varianteService.salvaAmbitoVarianteSelezione(data);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteTracciato", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva l'ambito della variante sulla selezione di un layer")
	@PostMapping("/salvaAmbitoVarianteSelezioneLayer")
	public @ResponseBody ResponseData salvaAmbitoVarianteSelezioneLayer(HttpServletRequest request, 
			@RequestBody(required = true) AmbitoVarianteListDTO data) {
		logger.debug("POST salvaAmbitoVarianteSelezione");
		ResponseData response = null;
		try {
			AmbitoVarianteListDTO result = varianteService.salvaAmbitoVarianteSelezioneLayer(data);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteTracciato", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera le cartografie associati alla variante")
	@GetMapping("/cercaCartografieByIdVariante")
	public @ResponseBody ResponseData cercaCartografieByIdVariante(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaCartografieByIdVariante");
		ResponseData response = null;
		try {
			List<CartografiaVarianteDTO> result = varianteService.cercaCartografieByIdVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCartografieByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva la cartografia associandola alla variante")
	@PostMapping("/salvaCartografia")
	public @ResponseBody ResponseData salvaCartografia(HttpServletRequest request, 
			@RequestBody(required = true) CartografiaDTO variante) {
		logger.debug("POST salvaCartografia");
		ResponseData response = null;
		try {
			CartografiaDTO result = varianteService.salvaCartografia(variante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in creaOrSalvaVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva l'ambito della variante in base alla ricerca effettuata")
	@PostMapping("/salvaAmbitoVarianteRicerca")
	public @ResponseBody ResponseData salvaAmbitoVarianteRicerca(HttpServletRequest request, 
			@RequestBody(required = true) List<AmbitoRicercaDTO> lsParticelle) {
		logger.debug("POST salvaAmbitoVarianteRicerca");
		ResponseData response = null;
		try {
			List<AmbitoRicercaDTO> particelleList = varianteService.salvaAmbitoVarianteRicerca(lsParticelle);
			response = new ResponseData(true, particelleList, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaAmbitoVarianteRicerca", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera gli indici associati al documento")
	@GetMapping("/cercaIndiciByIdDocumento")
	public @ResponseBody ResponseData cercaIndiciByIdDocumento(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idDocumento") Long idDocumento) {
		logger.debug("GET cercaIndiciByIdDocumento");
		ResponseData response = null;
		try {
			List<DocumentoIndiceDTO> result = varianteService.cercaIndiciByIdDocumento(idDocumento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaDocumentiByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera i codici associati agli indici")
	@GetMapping("/cercaCodiciByIdIndice")
	public @ResponseBody ResponseData cercaCodiciByIdIndice(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idIndice") Long idIndice) {
		logger.debug("GET cercaCodiciByIdIndice");
		ResponseData response = null;
		try {
			List<DocumentoIndiceCodiciDTO> result = varianteService.cercaCodiciByIdIndice(idIndice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCodiciByIdIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera i codici e la descrizione della ZTO")
	@PostMapping("/cercaCodiciZto")
	public @ResponseBody ResponseData cercaCodiciZto(HttpServletRequest request) {
		logger.debug("GET cercaCodiciZto");
		ResponseData response = null;
		try {
			List<CodiceZtoDTO> result = varianteService.cercaCodiceZto();
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCodiciZto", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva l'indice")
	@PostMapping("/salvaIndice")
	public @ResponseBody ResponseData salvaIndice(HttpServletRequest request, 
			@RequestBody(required = true) InserimentoIndiceDTO indice) {
		logger.debug("POST salvaIndice");
		ResponseData response = null;
		try {
			InserimentoIndiceDTO result = varianteService.salvaIndice(indice);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in indice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Cancella l'indice associato al documento")
	@GetMapping("/cancellaIndice")
	public @ResponseBody ResponseData cancellaIndice(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idIndice") Long idIndice) {
		logger.debug("GET cancellaIndice");
		ResponseData response = null;
		try {
			DocumentoIndiceDTO result = varianteService.cancellaIndice(idIndice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Importa l'indice")
	@PostMapping("/importaIndice")
	public @ResponseBody ResponseData importaIndice(HttpServletRequest request, 
			@RequestBody(required = true) ImportIndiceByteDTO documento) {
		logger.debug("POST importaIndice");
		ResponseData response = null;
		try {
			ImportIndiceByteDTO result = varianteService.importaIndice(documento);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in importaIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta l'indice in base all'id del documento")
	@GetMapping("/esportaIndice")
	public @ResponseBody ResponseData esportaIndice(HttpServletRequest request, 
			@RequestParam Long idDocumento) {
		logger.debug("POST esportaIndice");
		ResponseData response = null;
		try {
			File file = varianteService.esportaIndice(idDocumento);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in esportaIndice", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera l'ambito associato alla variante")
	@GetMapping("/cercaAmbitoByIdVariante")
	public @ResponseBody ResponseData cercaAmbitoByIdVariante(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="idVariante") Long idVariante) {
		logger.debug("GET cercaAmbitoByIdVariante");
		ResponseData response = null;
		try {
			List<String> result = varianteService.cercaAmbitoByIdVariante(idVariante);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaAmbitoByIdVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva la cartografia associandola alla variante")
	@PostMapping("/salvaCodici")
	public @ResponseBody ResponseData salvaCodici(HttpServletRequest request, 
			@RequestBody(required = true) CodiceDTO codice) {
		logger.debug("POST salvaCodici");
		ResponseData response = null;
		try {
			CodiceDTO result = varianteService.salvaCodici(codice);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaCodici", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Esporta il documento CDU associato alla variante")
	@PostMapping("/downloadCdu")
	public @ResponseBody ResponseData downloadCdu(HttpServletRequest request, 
			@RequestBody(required = true) InterrogazionePianoDTO interrogazionePianoDTO) {
		
		logger.debug("POST downloadCdu");
		ResponseData response = null;
		try {
			File file = varianteService.downloadCdu(interrogazionePianoDTO);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in downloadCdu", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva la cartografia associandola alla variante")
	@PostMapping("/cancellaCartografia")
	public @ResponseBody ResponseData cancellaCartografia(HttpServletRequest request, 
			@RequestBody(required = true) CartografiaDTO cartografia) {
		logger.debug("POST cancellaCartografia");
		ResponseData response = null;
		try {
			CartografiaDTO result = varianteService.cancellaCartografia(cartografia);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaCartografia", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il protocollo se esiste")
	@GetMapping("/cercaProtocollo")
	public @ResponseBody ResponseData cercaProtocollo(HttpServletRequest request, 
			@ApiParam( value = "Id della variante", required = false, example="1" )
			@RequestParam(name="protocollo") String protocollo) {
		logger.debug("GET cercaProtocollo");
		ResponseData response = null;
		try {
			String result = varianteService.cercaProtocollo(protocollo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaProtocollo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
