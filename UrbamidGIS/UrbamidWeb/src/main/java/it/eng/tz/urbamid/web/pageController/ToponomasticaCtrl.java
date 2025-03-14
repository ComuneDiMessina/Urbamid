package it.eng.tz.urbamid.web.pageController;

import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.AccessoDTO;
import it.eng.tz.urbamid.web.dto.CippoChilometricoDTO;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DocumentoStorageDTO;
import it.eng.tz.urbamid.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.web.dto.GiunzioneStradaleDTO;
import it.eng.tz.urbamid.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.web.dto.PercorsoDTO;
import it.eng.tz.urbamid.web.dto.SalvaToponimoStradaleDTO;
import it.eng.tz.urbamid.web.dto.ShapeResponseDTO;
import it.eng.tz.urbamid.web.dto.TipoFunzionaleDTO;
import it.eng.tz.urbamid.web.dto.TipoRisorsaDTO;
import it.eng.tz.urbamid.web.dto.TipoTopologicoDTO;
import it.eng.tz.urbamid.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.web.filter.DocumentoFilter;
import it.eng.tz.urbamid.web.filter.GiunzioneFilter;
import it.eng.tz.urbamid.web.filter.PercorsoFilter;
import it.eng.tz.urbamid.web.filter.ShapeFileFilter;
import it.eng.tz.urbamid.web.filter.ToponimoFilter;
import it.eng.tz.urbamid.web.services.IToponomasticaService;

@Controller(value = "Toponomastica Controller")
@RequestMapping(value = "/toponomasticaCtrl")
public class ToponomasticaCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ToponomasticaCtrl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String SUCCESS = "SUCCESS";
	private static final String ERROR = "ERROR";
	
	@Autowired
	private IToponomasticaService toponomasticaService;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaDocumenti", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO getDocumenti(HttpServletRequest request, @RequestBody DocumentoFilter filter) {
		logger.debug("GET ricercaDocumenti");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.getFile(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaDocumenti: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param file
	 * @param risorsa
	 * @param tipoRisorsa
	 * @return
	 */
	@PostMapping(value="/upload")
	public @ResponseBody ResponseData uploadDocumenti(HttpServletRequest request, @RequestBody MultipartFile file, 
			  @RequestParam(value = "risorsa", required = false) Long risorsa,
			  @RequestParam(value = "tipo", required = true) Long tipoRisorsa) {
		logger.debug("GET upload");
		ResponseData response = null;
		try {
			
			if(file.getSize() != 0) {
												
				TipoRisorsaDTO tipo = new TipoRisorsaDTO();
				tipo.setId(tipoRisorsa);
				DocumentoStorageDTO dto = new DocumentoStorageDTO();
				dto.setNomeDocumento(StringUtils.cleanPath(file.getOriginalFilename()));
				dto.setFile(Base64Utils.encode(file.getBytes()));
				dto.setIdRisorsa(risorsa);
				dto.setTipoRisorsa(tipo);
				toponomasticaService.upload(dto);
				response = new ResponseData(true, dto, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.debug("Errore in upload");
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.debug("Errore in upload: {}", e, e.getMessage());
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param fileName
	 * @param idRisorsa
	 * @param tipoRisorsa
	 * @param response
	 */
	@GetMapping(value="/download/{fileName:.+}")
	public void downloadDocumenti(HttpServletRequest request, @PathVariable String fileName,
																			@RequestParam(value = "risorsa", required = true) Long idRisorsa,
																			@RequestParam(value = "tipo", required = true) Long tipoRisorsa,
																			HttpServletResponse response) {
		logger.debug("GET download");
		DocumentoStorageDTO f = null;
		try {
						
			if(StringUtils.hasText(fileName)) {
				
				f = toponomasticaService.download(fileName, idRisorsa, tipoRisorsa);
				
				response.setContentType(request.getContentType());
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName +"\"");
				response.getOutputStream().write(Base64Utils.decode(f.getFile()));
				response.getOutputStream().flush();
			} else {
				
				logger.debug("Errore in download");
				response.setStatus(500);
			}
		} catch (Exception e) {
			
			logger.debug("Errore in download: {}", e, e.getMessage());
			response.setStatus(500);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/getShapeFiles", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO getShapeFiles(HttpServletRequest request, @RequestBody ShapeFileFilter filter) {
		
		logger.debug("GET getShapeFiles");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.getShapeFiles(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in getShapeFiles: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/importShape")
	public @ResponseBody ResponseData importShapeFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
		String idLog = "importShape";
		logger.debug(START, idLog);
		ResponseData response = null;
		try {
			
			if(file != null && file.getSize() != 0) {
				
				boolean fileCorretti = false;
				
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				
				if(extension.equalsIgnoreCase("zip"))
					fileCorretti = true;
				else
					fileCorretti = false;
						
				if(fileCorretti) {		
					
					toponomasticaService.importShapeFile(file);
					response = new ResponseData(true, null, 1, 1, null);
					response.setMessage(SUCCESS);
				} else {
					
					response = new ResponseData(false, null, 0, 0, null);
					response.setMessage(ERROR);	
				}
			} else {
				logger.debug("Errore in importShape");
				
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage(ERROR);
			}
			
		} catch (UrbamidServiceException ese) {
			
			logger.error(ERROR, idLog, "Non è stato possibile importare lo shapeFile!");
			response = new ResponseData(false, null,0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @param response
	 */
	@PostMapping(value = "/exportShape")
	public void exportShapeFile(HttpServletRequest request, @RequestBody ToponimoFilter filter, HttpServletResponse response) {
		String idLog = "POST exportShape";
		logger.debug(idLog);
		
		try {
			
			if(filter != null) {
				ShapeResponseDTO responseService = toponomasticaService.exportShapeFile(filter);
				
				if(responseService != null) {
					response.setContentType("application/zip");
					response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + responseService.getNameFile() +"\"");
					response.getOutputStream().write(Files.readAllBytes(responseService.getPathFile()));
					response.getOutputStream().flush();
					response.setStatus(200);
					
					logger.info(SUCCESS, idLog, "l'export dello shapeFile è stato effettuato correttamente!");
				} else {
					logger.error(ERROR, idLog, "Non è stato possibile exportare lo shapeFile!");
					response.setStatus(500);
					
				}
				
			} else {
				logger.error(ERROR, idLog, "Non è stato possibile exportare lo shapeFile!");
				response.setStatus(500);
			}
		} catch (Exception e) {
			
			logger.debug(ERROR, idLog, e.getMessage());
			response.setStatus(500);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping(value="/eliminaShapeFile")
	public @ResponseBody ResponseData eliminaShapeFile(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
		logger.debug("GET eliminaShapeFile");
		ResponseData response = null;
		try {
			
			if(id != null) {
				boolean result = toponomasticaService.deleteFileShape(id);
				
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.debug("Errore in eliminaShapeFile");
				
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
				
			}
					
		} catch (Exception e) {
			
			logger.debug("Errore in eliminaShapeFile: {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param idRisorsa
	 * @param tipoRisorsa
	 * @param nomeDocumento
	 * @return
	 */
	@GetMapping(value="/eliminaDocumenti")
	public @ResponseBody ResponseData eliminaStorage(HttpServletRequest request, @RequestParam(value = "risorsa", required = true) Long idRisorsa,
				  																 @RequestParam(value = "tipo", required = true) Long tipoRisorsa,
				  																 @RequestParam(value = "nome", required = true) String nomeDocumento) {
		logger.debug("GET eliminaDocumenti");
		ResponseData response = null;
		try {
			
			if(idRisorsa != null || tipoRisorsa != null || nomeDocumento != null) {
				
				DocumentoStorageDTO dto = toponomasticaService.eliminaStorage(idRisorsa, tipoRisorsa, nomeDocumento);
				response = new ResponseData(true, dto, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.debug("Errore in eliminaDocumenti");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.debug("Errore in eliminaDocumenti: {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getComuniByMessina")
	public @ResponseBody ResponseData getComuniByMessina(HttpServletRequest request) {
		String idLog = "getComuniByMessina";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<TipologicaDTO> dto = toponomasticaService.getComuniByMessina();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getComuniByMessina", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/findSiglaEstesaById")
	public @ResponseBody ResponseData findSiglaEstesaById(HttpServletRequest request, @RequestParam Long id) {
		String idLog = "findSiglaEstesaById";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<EstesaAmministrativaDTO> dto = toponomasticaService.findSiglaEstesaById(id);
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in findSiglaEstesaById", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getTipoTopologico")
	public @ResponseBody ResponseData getTipoTopologico(HttpServletRequest request) {
		String idLog = "getTipoTopologico";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<TipoTopologicoDTO> dto = toponomasticaService.getTipoTopologico();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getTipoTopologico", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getTipoFunzionale")
	public @ResponseBody ResponseData getTipoFunzionale(HttpServletRequest request) {
		String idLog = "getTipoFunzionale";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<TipoFunzionaleDTO> dto = toponomasticaService.getTipoFunzionale();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getTipoFunzionale", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param localita
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/insertOrUpdateLocalita", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdateLocalita(HttpServletRequest request, @RequestBody(required = true) LocalitaDTO localita) throws Exception {
		logger.debug("POST insertOrUpdateLocalita");
		ResponseData response = null;
		try {
			
			if(localita != null) {
				
				LocalitaDTO result = toponomasticaService.insertOrUpdateLocalita(localita);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				logger.debug("POST insertOrUpdateLocalita eseguita con successo. E' stata inserita la località con successo");
			} else {
				
				logger.error("Error in insertOrUpdateLocalita");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.error("POST insertOrUpdateLocalita non eseguita con successo. Non è stato possibile inseerire la località");
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in insertOrUpdateLocalita {}", ese, ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/cancellaLocalita", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaLocalita(HttpServletRequest request, @RequestParam(name="id") Long id) throws Exception {
		logger.debug("cancellaLocalita");
		ResponseData response = null;
		try {
			
			if(id != null) {
				
				Long result = toponomasticaService.countAccessoByLocalita(id);
				
				if(result == 0) {
					
					toponomasticaService.eliminaLocalita(id);
					response = new ResponseData(true, null, 0, 0, null);
					response.setMessage(SUCCESS);
					logger.debug("POST cancellaLocalita eseguita con successo. E' stata cancellata la località con id: {}", id);
				} else {
					
					logger.error("Error in cancellaLocalita");
					response = new ResponseData(false, null, 0, 0, null);
					response.setMessage(ERROR);
					logger.error("POST cancellaLocalita non eseguita con successo. Non è stata cancellata la località con id: {}", id);
				}
			} else {
				
				logger.error("Error in cancellaLocalita");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.error("POST cancellaLocalita non eseguita con successo. Non è stata cancellata la località con id: {}", id);
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in cancellaLocalita {}", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param salvaToponimoStradale
	 * @return
	 */
	@PostMapping(value = "/insertOrUpdateToponimo" )
	public @ResponseBody ResponseData insertOrUpdateToponimo(HttpServletRequest request, 
			@RequestBody(required = true) SalvaToponimoStradaleDTO salvaToponimoStradale) {
		logger.debug("POST insertOrUpdateToponimo");
		ResponseData response = null;
		try {
			
			if(salvaToponimoStradale.getToponimoStradale() != null) {
				
				ToponimoStradaleDTO result = toponomasticaService.insertOrUpdateToponimo(salvaToponimoStradale.getToponimoStradale());
				if (result!=null && (salvaToponimoStradale.getPublish()!=null && salvaToponimoStradale.getPublish()) ){
					
					toponomasticaService.pubblicaToponimoStradale(result.getId());
				} else if (result==null){
					
					logger.error("Error in insertOrUpdateToponimo");
					response = new ResponseData(false, null,0, 0, null);
					response.setMessage(ERROR);
				}
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in insertOrUpdateToponimo");
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in insertOrUpdateToponimo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/pubblicaToponimoStradale", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData pubblicaToponimoStradale(HttpServletRequest request,
			@RequestBody(required = true) Long id) throws Exception {
		logger.debug("POST pubblicaToponimoStradale");
		ResponseData response = null;
		try {
			
			if(id != null) {
					
				toponomasticaService.pubblicaToponimoStradale(id);
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage(SUCCESS);
				logger.debug("POST pubblicaToponimoStradale eseguita con successo. E' stato pubblicato il toponimo stradale con id {}.", id);
			} else {
			
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage(ERROR);
				logger.debug("POST pubblicaToponimoStradale non eseguita con successo. Il toponimo stradale con id {} non è stato pubblicato.", id);
			}
		} catch (UrbamidServiceException use) {
			
			logger.error("Error in pubblicaToponimoStradale: ", use);
			response = new ResponseData(false, null,0, 0, use.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param idPadre
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/isFigliPubblicati", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData isFigliPubblicati(HttpServletRequest request,
			@RequestParam(name="idPadre", required = true) Long idPadre) throws Exception {
		logger.debug("POST isFigliPubblicati");
		ResponseData response = null;
		try {
			
			if(idPadre != null) {
					
				Boolean result = toponomasticaService.isFigliPubblicati(idPadre);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				logger.debug("POST isFigliPubblicati eseguita con successo. Sono stati trovati dei figli con lo stato PUBBLICATO e idPadre: {}", idPadre);
			} else {
			
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage(ERROR);
				logger.debug("POST isFigliPubblicati non eseguita con successo. Non sono stati trovati dei figli con lo stato PUBBLICATO e idPadre: {}", idPadre);
			}
		} catch (UrbamidServiceException use) {
			
			logger.error("Error in isFigliPubblicati: ", use);
			response = new ResponseData(false, null,0, 0, use.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @param archivia
	 * @return
	 */
	@GetMapping(value="/cancellaToponimo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaToponimo(HttpServletRequest request, 
			@RequestParam(name="id") Long id,
			@RequestParam(name="archivia") Boolean archivia) {
		logger.debug("cancellaToponimo");
		ResponseData response = null;
		try {
			
			if(id != null) {
				
				Long result = toponomasticaService.countAccessoByToponimo(id);
				
				if(result  == 0) {
					toponomasticaService.eliminaToponimo(id, archivia);
					response = new ResponseData(true, null);
					response.setMessage(SUCCESS);
				} else {
					logger.error("Error in cancellaToponimo");
					response = new ResponseData(false, null);
					response.setMessage(ERROR);
				}
			} else {
				logger.error("Error in cancellaToponimo");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in cancellaToponimo {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * TODO: inserire commento
	 * @param request
	 * @param accesso
	 * @return
	 */
	@PostMapping(value = "/insertOrUpdateAccesso", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdateAccesso(HttpServletRequest request, @RequestBody(required = true) AccessoDTO accesso) {
		logger.debug("POST insertOrUpdateAccesso");
		ResponseData response = null;
		try {
			
			if(accesso != null) {
				
				AccessoDTO result = toponomasticaService.insertOrUpdateAccesso(accesso);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in insertOrUpdateAccesso");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in insertOrUpdateAccesso {}", e, e.getMessage());
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@GetMapping(value="/cancellaAccesso", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaAccesso(HttpServletRequest request, @RequestParam(name="id") Long id) {
		logger.debug("cancellaAccesso");
		ResponseData response = null;
		try {
			
			if(id != null) {
				
				toponomasticaService.eliminaAccesso(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in cancellaAccesso");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in cancellaAccesso {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @param toponimo
	 * @return
	 */
	@GetMapping(value="/cancellaAccessoByToponimo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaAccessoByToponimo(HttpServletRequest request, @RequestParam(name="id") Long id, @RequestParam(name = "toponimo") Long toponimo) {
		logger.debug("cancellaAccessoByToponimo");
		ResponseData response = null;
		try {
			
			if(id != null && toponimo != null) {
				
				toponomasticaService.eliminaAccessoByToponimo(id, toponimo);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in cancellaAccessoByToponimo");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in cancellaAccessoByToponimo {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param idAccesso
	 * @param idLocalita
	 * @return
	 */
	@GetMapping(value="/cancellaAccessoByLocalita", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaAccessoByLocalita(HttpServletRequest request, @RequestParam(name="id") Long idAccesso, @RequestParam(name = "localita") Long idLocalita) {
		logger.debug("cancellaAccessoByLocalita");
		ResponseData response = null;
		try {
			
			if(idAccesso != null && idLocalita != null) {
				
				toponomasticaService.eliminaAccessoByLocalita(idAccesso, idLocalita);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in cancellaAccessoByLocalita");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in cancellaAccessoByLocalita {}", e, e.getMessage());
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param estesaAmministrativa
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/insertOrUpdateEstesa", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdateEstesa(HttpServletRequest request, @RequestBody(required = true) EstesaAmministrativaDTO estesaAmministrativa) throws Exception {
		logger.debug("POST insertOrUpdateEstesa");
		ResponseData response = null;
		try {
			
			if(estesaAmministrativa != null) {
				
				EstesaAmministrativaDTO result = toponomasticaService.insertOrUpdateEstesa(estesaAmministrativa);
				
				response = new ResponseData(true, result, 0, 0, null);
				response.setMessage(SUCCESS);
				logger.debug("POST insertOrUpdateEstesa eseguita con successo. E' stata inserita o modificata l'estesa amministrativa");
			} else {
				
				logger.error("Error in insertOrUpdateEstesa");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.debug("POST insertOrUpdateEstesa non eseguita. Non è stato possibile inserire o modificare l'estesa amministrativa");
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in insertOrUpdateEstesa {}", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/cancellaEstesaAmministrativa", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaEstesaAmministrativa(HttpServletRequest request, @RequestParam(name="id") Long id) throws Exception {
		logger.debug("cancellaEstesaAmministrativa");
		ResponseData response = null;
		try {
			
			if(id != null) {
				
				Long result = toponomasticaService.countCippiByEstesa(id);
				if(result == 0) {
					
					toponomasticaService.eliminaEstesaAmministrativa(id);
					response = new ResponseData(true, null, 0, 0, null);
					response.setMessage(SUCCESS);
					logger.debug("POST cancellaEstesaAmministrativa eseguita con successo. E' stata cancellata l'estesa amministrativa con id: {}", id);
				} else {
					
					response = new ResponseData(false, null, 0, 0, null);
					response.setMessage(ERROR);
					logger.error("POST cancellaEstesaAmministrativa non eseguita con successo. Non è stato possibile cancellare l'estesa amministrativa con id: {}", id);
				}
			} else {
					
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.error("Error in cancellaEstesaAmministrativa");
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in cancellaEstesaAmministrativa {}", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaPercorso", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaPercorso(HttpServletRequest request, @RequestBody PercorsoFilter filter) {
		logger.debug("GET ricercaPercorso");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.ricercaPercorso(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaPercorso: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param percorso
	 * @return
	 */
	@PostMapping(value = "/insertOrUpdatePercorso", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdatePercorso(HttpServletRequest request, @RequestBody(required = true) PercorsoDTO percorso) {
		logger.debug("POST insertOrUpdatePercorso");
		ResponseData response = null;
		try {
			
			if(percorso != null) {
				
				PercorsoDTO result = toponomasticaService.insertOrUpdatePercorso(percorso);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in insertOrUpdatePercorso");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in insertOrUpdatePercorso {}", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@GetMapping(value="/cancellaPercorso", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaPercorso(HttpServletRequest request, @RequestParam(name="id") Long id) {
		logger.debug("cancellaPercorso");
		ResponseData response = null;
		try {
			
			if(id != null) {
					
				toponomasticaService.eliminaPercorso(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in cancellaPercorso");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in cancellaPercorso {}", e);
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param cippo
	 * @return
	 */
	@PostMapping(value = "/insertOrUpdateCippo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdateCippo(HttpServletRequest request, @RequestBody(required = true) CippoChilometricoDTO cippo) {
		logger.debug("POST insertOrUpdateCippo");
		ResponseData response = null;
		try {
			
			if(cippo != null) {
				
				CippoChilometricoDTO result = toponomasticaService.insertOrUpdateCippo(cippo);
				
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in insertOrUpdateCippo");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in insertOrUpdateCippo {}", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@GetMapping(value="/cancellaCippo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaCippo(HttpServletRequest request, @RequestParam(name="id") Long id) {
		logger.debug("cancellaCippo");
		ResponseData response = null;
		try {
			
			if(id != null) {
					
				toponomasticaService.eliminaCippo(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in cancellaCippo");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
		} catch (Exception e) {
			
			logger.error("Error in cancellaCippo {}", e);
			response = new ResponseData(false, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param idPadre
	 * @return
	 */
	@RequestMapping(value="/getToponimoFigli", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getToponimoFigli(HttpServletRequest request, @RequestParam(name="idPadre") Long idPadre) {
		logger.debug("POST getToponimoFigli");
		ResponseData response = null;
		try {
			List<ToponimoStradaleDTO> res = toponomasticaService.getToponimoFigli(idPadre);
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getToponimoFigli", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaGiunzioniStradali", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaGiunzioniStradali(HttpServletRequest request, @RequestBody GiunzioneFilter filter) {
		logger.debug("POST ricercaGiunzioniStradali");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.ricercaGiunzioneStradale(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaGiunzioniStradali: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param giunzione
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/insertOrUpdateGiunzione", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData insertOrUpdateGiunzione(HttpServletRequest request, @RequestBody(required = true) GiunzioneStradaleDTO giunzione) throws Exception {
		logger.debug("POST insertOrUpdateGiunzione");
		ResponseData response = null;
		try {
			
			if(giunzione != null) {
				
				GiunzioneStradaleDTO result = toponomasticaService.insertOrUpdateGiunzione(giunzione);
				
				response = new ResponseData(true, result, 0, 0, null);
				response.setMessage(SUCCESS);
				logger.debug("POST insertOrUpdateGiunzione eseguita con successo. E' stata inserita o modificata una giunzione stradale");
			} else {
				
				logger.error("Error in insertOrUpdateGiunzione");
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.debug("POST insertOrUpdateGiunzione non eseguita. Non è stato possibile inserire o modificare una giunzione stradale");
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in insertOrUpdateGiunzione {}", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/cancellaGiunzione", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cancellaGiunzioneStradale(HttpServletRequest request, @RequestParam(name="id") Long id) throws Exception {
		logger.debug("cancellaGiunzione");
		ResponseData response = null;
		try {
			
			if(id != null) {
				
				toponomasticaService.eliminaGiunzione(id);
				response = new ResponseData(true, null, 0, 0, null);
				response.setMessage(SUCCESS);
				logger.debug("POST cancellaGiunzione eseguita con successo. E' stata cancellata la giunzione stradale con id: {}", id);
			} else {
					
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.error("POST cancellaGiunzione non eseguita con successo. Non è stata cancellata la giunzione stradale con id: {}", id);
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in cancellaGiunzione {}", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param geom
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/findIntersections", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData findIntersections(HttpServletRequest request, @RequestParam(name = "geom", required = true) String geom) throws Exception {
		logger.debug("findIntersections");
		ResponseData response = null;
		try {
			
			if(geom != null) {
				
				 List<ToponimoStradaleDTO> result = toponomasticaService.findIntersections(geom);
				response = new ResponseData(true, result, result.size(), result.size(), null);
				response.setMessage(SUCCESS);
				logger.debug("POST findIntersections eseguita con successo. Sono stati intersecati dei toponimi stradali con questa geometria: {}", geom);
			} else {
					
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.error("POST findIntersections non eseguita con successo. Non è stato possibile intersecate i toponimi stradali con questa geometria: {}", geom);
			}
		} catch (UrbamidServiceException ese) {
			
			logger.error("Error in findIntersections {}", ese.getMessage());
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
}
