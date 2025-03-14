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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.DettaglioBatchDTO;
import it.eng.tz.urbamid.web.dto.FoglioGeomDTO;
import it.eng.tz.urbamid.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.web.dto.ParticellaGeomCompleteDTO;
import it.eng.tz.urbamid.web.dto.ParticellaGeomDTO;
import it.eng.tz.urbamid.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.web.services.CatastoBatchManagementService;
import it.eng.tz.urbamid.web.services.CatastoServices;

/**
 * @author Alessandro Paolillo
 */
@Controller(value = "Catasto Controller")
@RequestMapping("/catastoController")
public class CatastoCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CatastoCtrl.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private CatastoServices catastoServices;
	
	/**
	 * Service
	 */
	@Autowired
	private CatastoBatchManagementService service;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/findParticella", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticella(HttpServletRequest request) {
		logger.debug("POST findParticella");
		ResponseData response = null;
		String numFoglio	= request.getParameter("numFoglio");
		String mappale	= request.getParameter("mappale");
		try {
			List<ParticellaGeomDTO> res = catastoServices.findParticella(numFoglio, mappale);
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticella", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findParticellaByGeom", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticellaByGeom(HttpServletRequest request) {
		logger.debug("POST findParticellaByGeom");
		ResponseData response = null;
		String wktGeom = null; 
		if(request.getParameter("wktGeom")!= null){
			wktGeom = request.getParameter("wktGeom");
		}
		try {
			List<ParticellaGeomDTO> res = catastoServices.findParticellaByGeom( wktGeom );
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticellaByGeom", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findParticellaByWkt", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticellaByWkt(HttpServletRequest request) {
		logger.debug("POST findParticellaByWkt");
		ResponseData response = null;
		String wktGeom = null; 
		if(request.getParameter("wktGeom")!= null){
			wktGeom = request.getParameter("wktGeom");
		}
		try {
			List<ParticellaGeomDTO> res = catastoServices.findParticellaByWkt( wktGeom );
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticellaByWkt", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findParticellaCompleteByWkt", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticellaCompleteByWkt(HttpServletRequest request) {
		logger.debug("POST findParticellaCompleteByWkt");
		ResponseData response = null;
		String wktGeom = null; 
		if(request.getParameter("wktGeom")!= null){
			wktGeom = request.getParameter("wktGeom");
		}
		try {
			List<ParticellaGeomCompleteDTO> res = catastoServices.findParticellaCompleteByWkt( wktGeom );
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticellaCompleteByWkt", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findParticellaByLayer", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticellaByLayer(HttpServletRequest request) {
		logger.debug("POST findParticellaByLayer");
		ResponseData response = null;
		String layerName = null; 
		if(request.getParameter("layerName")!= null){
			layerName = request.getParameter("layerName");
		}
		try {
			List<ParticellaGeomDTO> res = catastoServices.findParticellaByLayer( layerName );
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticellaByLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findFoglio", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findFoglio(HttpServletRequest request) {
		logger.debug("POST findFoglio");
		ResponseData response = null;
		String numFoglio	= request.getParameter("numFoglio");
		try {
			List<FoglioGeomDTO> res = catastoServices.findFoglio(numFoglio);
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findFoglio", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/findFoglioByGeom", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findFoglioByGeom(HttpServletRequest request) {
		logger.debug("POST findFoglioByGeom");
		ResponseData response = null;
		String wktGeom = null; 
		if(request.getParameter("wktGeom")!= null){
			wktGeom = request.getParameter("wktGeom");
		}
		try {
			List<FoglioGeomDTO> res = catastoServices.findFoglioByGeom(wktGeom);
			response = new ResponseData(true, res, res.size(), res.size(), null);
			
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findFoglioByGeom", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@PostMapping(value="/ricercaSuParticellePT", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaSuParticellePT(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaSuParticellePT");
		ResponseData response = null;
		try {
			List<ParticelleTerreniDTO> particelleList;
			particelleList = catastoServices.ricercaSuParticellePT(lsParticelle);
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticellePT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@PostMapping(value="/ricercaSuParticelleUI", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaSuParticelleUI(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaSuParticelleUI");
		ResponseData response = null;
		try {
			List<UnitaImmobiliareDTO> res = catastoServices.ricercaSuParticelleUI(lsParticelle);
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticelleUI", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/ricercaListaIntestatari", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaListaIntestatari(HttpServletRequest request, 
			@RequestParam(name="idImmobile") Integer idImmobile) {
		logger.debug("GET ricercaListaIntestatari");
		ResponseData response = null;
		try {
			List<ListaIntestatariDTO> listaIntestatari;
			listaIntestatari = catastoServices.ricercaListaIntestatari(idImmobile);
			response = new ResponseData(true, listaIntestatari, listaIntestatari.size(), listaIntestatari.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticelleUI", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/ricercaPersoneFisiche", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaPersoneFisiche(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaPersoneFisiche");
		ResponseData response = null;
		try {
			List<ListaIntestatariDTO> listaPersoneFisiche;
			listaPersoneFisiche = catastoServices.ricercaPersoneFisiche(lsParticelle);
			response = new ResponseData(true, listaPersoneFisiche, listaPersoneFisiche.size(), listaPersoneFisiche.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/ricercaSoggettiGiuridici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaSoggettiGiuridici(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaSoggettiGiuridici");
		ResponseData response = null;
		try {
			List<ListaIntestatariDTO> listaSoggettiGiuridici;
			listaSoggettiGiuridici = catastoServices.ricercaSoggettiGiuridici(lsParticelle);
			response = new ResponseData(true, listaSoggettiGiuridici, listaSoggettiGiuridici.size(), listaSoggettiGiuridici.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSoggettiGiuridici", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/ricercaFoglioMappale", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaFoglioMappale(HttpServletRequest request, 
			@RequestParam(name="foglio") String foglio,
			@RequestParam(name="mappale") String mappale) {
		logger.debug("GET ricercaFoglioMappale");
		ResponseData response = null;
		try {
			List<ParticellaGeomDTO> listaFoglioMappale;
			listaFoglioMappale = catastoServices.ricercaFoglioMappale(foglio, mappale);
			response = new ResponseData(true, listaFoglioMappale, listaFoglioMappale.size(), listaFoglioMappale.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaFoglioMappale", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/categorieCatastali", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData categorieCatastali(HttpServletRequest request ) {
		logger.debug("GET categorieCatastali");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaCategorieCatastali;
			listaCategorieCatastali = catastoServices.categorieCatastali();
			response = new ResponseData(true, listaCategorieCatastali, listaCategorieCatastali.size(), listaCategorieCatastali.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in categorieCatastali", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/codiciDiritto", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData codiciDiritto(HttpServletRequest request ) {
		logger.debug("GET codiciDiritto");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaCodiciDiritto;
			listaCodiciDiritto = catastoServices.codiciDiritto();
			response = new ResponseData(true, listaCodiciDiritto, listaCodiciDiritto.size(), listaCodiciDiritto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in codiciDiritto", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/codiciQualita", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData codiciQualita(HttpServletRequest request ) {
		logger.debug("GET codiciQualita");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaCodiciQualita;
			listaCodiciQualita = catastoServices.codiciQualita();
			response = new ResponseData(true, listaCodiciQualita, listaCodiciQualita.size(), listaCodiciQualita.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in codiciQualita", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@PostMapping(value="/exportXls", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportXls(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			HttpServletResponse response) {
		logger.debug("POST exportXls");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.zip");
			printWriter.write(catastoServices.exportXls(lsParticelle));
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
		}
	}
	
	@PostMapping(value="/exportSoggettiXls", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportSoggettiXls(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			HttpServletResponse response) {
		logger.debug("POST exportSoggettiXls");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Soggetti.zip");
			printWriter.write(catastoServices.exportXlsSoggetti(lsParticelle));
		} catch (Exception e) {
			logger.error("Error in exportSoggettiXls", e);
		}
	}
	
	@PostMapping(value="/exportTerreniXls", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportTerreniXls(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			HttpServletResponse response) {
		logger.debug("POST exportTerreniXls");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ParticellaTerreni.zip");
			printWriter.write(catastoServices.exportXlsTerreni(lsParticelle));
		} catch (Exception e) {
			logger.error("Error in exportTerreniXls", e);
		}
	}
	
	@PostMapping(value="/exportUiuXls", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportUiuXls(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			HttpServletResponse response) {
		logger.debug("POST exportTerreniXls");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=UnitaImmobiliareUrbana.xls");
			printWriter.write(catastoServices.exportXlsUiu(lsParticelle));
		} catch (Exception e) {
			logger.error("Error in exportUiuXls", e);
		}
	}
	
	@PostMapping(value="/exportIntestazioniXls", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportIntestazioniXls(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			HttpServletResponse response) {
		logger.debug("POST exportIntestazioniXls");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Intestazioni.xls");
			printWriter.write(catastoServices.exportXlsIntestazioni(lsParticelle));
		} catch (Exception e) {
			logger.error("Error in exportIntestazioniXls", e);
		}
	}


	@PostMapping(value="/exportPdf", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportPdf(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione,
			HttpServletResponse response) {
		logger.debug("POST exportPdf");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.zip");
			printWriter.write(catastoServices.exportPdf(lsParticelle, titolo, tipologiaEstrazione));
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
		}
	}
	
	@PostMapping(value="/exportShape", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportShape(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo
			,HttpServletResponse response
			) {
		logger.debug("POST exportShape");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+titolo+".zip");
			printWriter.write(catastoServices.exportShape(lsParticelle, titolo));
		} catch (Exception e) {
			logger.error("Error in exportShape", e);
		}
	}

	@PostMapping(value="/exportFabbricatiPerNominativo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportFabbricatiPerNominativo(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione,
			HttpServletResponse response) {
		logger.debug("POST exportPdf");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(catastoServices.exportFabbricatiPerNominativo(lsParticelle, titolo, tipologiaEstrazione));
		} catch (Exception e) {
			logger.error("Error in exportFabbricatiPerNominativo", e);
		}
	}

	@PostMapping(value="/exportTerreniPerNominativo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportTerreniPerNominativo(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione,
			HttpServletResponse response) {
		logger.debug("POST exportTerreniPerNominativo");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(catastoServices.exportTerreniPerNominativo(lsParticelle, titolo, tipologiaEstrazione));
		} catch (Exception e) {
			logger.error("Error in exportTerreniPerNominativo", e);
		}
	}

	@PostMapping(value="/exportFabbricatiPerParticella", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportFabbricatiPerParticella(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione,
			HttpServletResponse response) {
		logger.debug("POST exportFabbricatiPerParticella");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(catastoServices.exportFabbricatiPerParticella(lsParticelle, titolo, tipologiaEstrazione));
		} catch (Exception e) {
			logger.error("Error in exportFabbricatiPerParticella", e);
		}
	}

	@PostMapping(value="/exportTerreniPerParticella", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportTerreniPerParticella(HttpServletRequest request,
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione,
			HttpServletResponse response) {
		logger.debug("POST exportTerreniPerParticella");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(catastoServices.exportTerreniPerParticella(lsParticelle, titolo, tipologiaEstrazione));
		} catch (Exception e) {
			logger.error("Error in exportTerreniPerParticella", e);
		}
	}

	@GetMapping(value="/getComuni", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getComuni(HttpServletRequest request ) {
		logger.debug("GET codiciQualita");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaComuni;
			listaComuni = catastoServices.getComuni();
			response = new ResponseData(true, listaComuni, listaComuni.size(), listaComuni.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getComuni", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/getProvince", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getProvince(HttpServletRequest request ) {
		logger.debug("GET getProvince");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaProvince;
			listaProvince = catastoServices.getProvince();
			response = new ResponseData(true, listaProvince, listaProvince.size(), listaProvince.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getProvince", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/getComuniByProvincia", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getComuniByProvincia(HttpServletRequest request,
			@RequestParam(name="idProvincia") Long idProvincia) {
		logger.debug("GET getComuniByProvincia");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaComuni;
			listaComuni = catastoServices.getComuniByProvincia(idProvincia);
			response = new ResponseData(true, listaComuni, listaComuni.size(), listaComuni.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getComuniByProvincia", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/getExecutionJob", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData avviaRecuperoProcessoBatch(HttpServletRequest request ) {
		
		logger.debug("GET getExecutionJob");
		ResponseData response = null;
		try {
			
			DettaglioBatchDTO dettaglio = this.service.avviaRecuperoProcessoBatch();
			if (dettaglio!=null) {
				
				response = new ResponseData(true, dettaglio, 0, 0, null);
				response.setMessage(SUCCESS);
			} else {
				
				response = new ResponseData(true, null, 0, 0, null);
				response.setMessage(SUCCESS);	
			}
		} catch (Exception e) {
			
			logger.error("Error in avviaRecuperoProcessoBatch", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
