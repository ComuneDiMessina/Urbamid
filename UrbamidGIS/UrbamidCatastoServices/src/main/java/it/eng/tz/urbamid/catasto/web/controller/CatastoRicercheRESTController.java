package it.eng.tz.urbamid.catasto.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
import it.eng.tz.urbamid.catasto.service.CatastoRicercheService;
import it.eng.tz.urbamid.catasto.web.dto.FoglioGeomDTO;
import it.eng.tz.urbamid.catasto.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomCompleteDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.catasto.web.response.ResponseData;

@RestController
@RequestMapping("/catasto/rest/api/catastoRicerche")
@Api(value = "urbamid catasto.ricerche", tags= {"CatastoRicerche"})
public class CatastoRicercheRESTController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(CatastoRicercheRESTController.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private CatastoRicercheService catastoRicercheService;
	
	@ApiOperation("Recupera le info di una particella dato numero di foglio e mappale")
	@RequestMapping(value="/findParticella", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticella(HttpServletRequest request, 
			@RequestParam(name="numFoglio") String numFoglio,
			@RequestParam(name="mappale") String mappale,
			@RequestParam(name="page") Integer page,
			@RequestParam(name="size") Integer size) {
		logger.debug("POST findParticella");
		ResponseData response = null;
		try {
			List<ParticellaGeomDTO> particelleList;
			particelleList = catastoRicercheService.findParticellaWithFoglioAndMappale(numFoglio, mappale, "mappale", "asc", page, size);
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticella", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@PostMapping("/findParticellaByGeom")
	public @ResponseBody ResponseData findParticellaByGeom(HttpServletRequest request, 
			@RequestBody(required = true) List<String> wktList) {
		logger.debug("POST findParticellaByGeom");
		ResponseData response = null;
		try {
			
			List<ParticellaGeomDTO> particelleList = new ArrayList<ParticellaGeomDTO>();
			for (String wktGeom : wktList) {
				particelleList.addAll( catastoRicercheService.findParticellaWithGeom(wktGeom, "mappale", "asc") );
			}
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			logger.error("Error in findParticella", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@PostMapping("/findParticellaByWkt")
	public @ResponseBody ResponseData findParticellaByWkt(HttpServletRequest request, 
			@RequestBody(required = true) List<String> wktList) {
		logger.debug("POST findParticellaByGeom");
		ResponseData response = null;
		try {
			
			List<ParticellaGeomDTO> particelleList = new ArrayList<ParticellaGeomDTO>();
			for (String wktGeom : wktList) {
				particelleList.addAll( catastoRicercheService.findParticellaByWkt(wktGeom) );
			}
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			logger.error("Error in findParticella", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@PostMapping("/findParticellaCompleteByWkt")
	public @ResponseBody ResponseData findParticellaCompleteByWkt(HttpServletRequest request, 
			@RequestBody(required = true) List<String> wktList) {
		logger.debug("POST findParticellaByGeom");
		ResponseData response = null;
		try {
			
			List<ParticellaGeomCompleteDTO> particelleList = new ArrayList<ParticellaGeomCompleteDTO>();
			for (String wktGeom : wktList) {
				particelleList.addAll( catastoRicercheService.findParticellaCompleteByWkt(wktGeom) );
			}
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			logger.error("Error in findParticella", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}
	
	@ApiOperation("Recupera le info di una particella data una geometria")
	@RequestMapping(value="/findParticellaByLayer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findParticellaByLayer(HttpServletRequest request, 
			@RequestParam(name="layerName") String layerName) {
		logger.debug("POST findParticellaByGeom");
		ResponseData response = null;
		try {
			List<GeometriaLayerDTO> particelleList;
			particelleList = catastoRicercheService.findParticellaWithLayer(layerName);
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findParticellaByLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
		
	}
	
	@ApiOperation("Recupera le info di un foglio dato numero il suo numero")
	@RequestMapping(value="/findFoglio", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findFoglio(HttpServletRequest request, 
			@RequestParam(name="numFoglio") String numFoglio, @RequestParam(name="page") Integer page, @RequestParam(name="size") Integer size) {
		logger.debug("POST findFoglio");
		ResponseData response = null;
		try {
			List<FoglioGeomDTO> foglioList;
			foglioList = catastoRicercheService.findFoglio(numFoglio, "allegato", "asc", page, size);
			response = new ResponseData(true, foglioList, foglioList.size(), foglioList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findFoglio", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera le info di un foglio dato numero il suo numero")
	@RequestMapping(value="/findFoglioByGeom", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData findFoglioByGeom(HttpServletRequest request, 
			@RequestParam(name="wktGeom") String wktGeom) {
		logger.debug("POST findFoglioByGeom");
		ResponseData response = null;
		try {
			List<FoglioGeomDTO> foglioList;
			foglioList = catastoRicercheService.findFoglioWithGeom(wktGeom, "allegato", "asc");
			response = new ResponseData(true, foglioList, foglioList.size(), foglioList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in findFoglioByGeom", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera le info di tipo PT di una o più particelle in base a quelle selezionate")
	@PostMapping("/ricercaSuParticellePT")
	public @ResponseBody ResponseData ricercaSuParticellePT(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaSuParticellePT");
		ResponseData response = null;
		try {
			List<ParticelleTerreniDTO> particelleList;
			particelleList = catastoRicercheService.ricercaSuParticellePT(lsParticelle, "allegato", "asc");
			response = new ResponseData(true, particelleList, particelleList.size(), particelleList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticellePT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera le info di tipo UI di una o più particelle in base a quelle selezionate")
	@PostMapping("/ricercaSuParticelleUI")
	public @ResponseBody ResponseData ricercaSuParticelleUI(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaSuParticelleUI");
		ResponseData response = null;
		try {
			List<UnitaImmobiliareDTO> unitaImmobiliariList;
			unitaImmobiliariList = catastoRicercheService.ricercaSuParticelleUI(lsParticelle, "allegato", "asc");
			response = new ResponseData(true, unitaImmobiliariList, unitaImmobiliariList.size(), unitaImmobiliariList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticelleUI", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera gli intestatari per il dato id immobile")
	@GetMapping("/ricercaListaIntestatari")
	public @ResponseBody ResponseData ricercaListaIntestatari(HttpServletRequest request, 
			@ApiParam( value = "Id dell'immobile", required = false, example="1" )
			@RequestParam(name="idImmobile") 
			Integer idImmobile) {
		logger.debug("GET ricercaListaIntestatari");
		ResponseData response = null;
		try {
			List<ListaIntestatariDTO> listaIntestatari;
			listaIntestatari = catastoRicercheService.ricercaListaIntestatari(idImmobile, "allegato", "asc");
			response = new ResponseData(true, listaIntestatari, listaIntestatari.size(), listaIntestatari.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticelleUI", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera le info di tutte le persone fisiche di una o più particelle in base a quelle selezionate")
	@PostMapping("/ricercaPersoneFisiche")
	public @ResponseBody ResponseData ricercaPersoneFisiche(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaPersoneFisiche");
		ResponseData response = null;
		try {
			List<ListaIntestatariDTO> listaPersoneFisiche;
			listaPersoneFisiche = catastoRicercheService.ricercaPersoneFisiche(lsParticelle, "allegato", "asc");
			response = new ResponseData(true, listaPersoneFisiche, listaPersoneFisiche.size(), listaPersoneFisiche.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera le info di tutti i soggetti giuridici di una o più particelle in base a quelle selezionate")
	@PostMapping("/ricercaSoggettiGiuridici")
	public @ResponseBody ResponseData ricercaSoggettiGiuridici(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST ricercaSoggettiGiuridici");
		ResponseData response = null;
		try {
			List<ListaIntestatariDTO> listaSoggettiGiuridici;
			listaSoggettiGiuridici = catastoRicercheService.ricercaSoggettiGiuridici(lsParticelle, "allegato", "asc");
			response = new ResponseData(true, listaSoggettiGiuridici, listaSoggettiGiuridici.size(), listaSoggettiGiuridici.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSoggettiGiuridici", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera le info di una particella dato numero di foglio e/o mappale")
	@GetMapping("/ricercaFoglioMappale")
	public @ResponseBody ResponseData ricercaFoglioMappale(HttpServletRequest request, 
			@RequestParam(name="foglio") String foglio,
			@RequestParam(name="mappale") String mappale) {
		logger.debug("GET ricercaFoglioMappale");
		ResponseData response = null;
		try {
			List<ParticellaGeomDTO> listaFoglioMappale;
			listaFoglioMappale = catastoRicercheService.ricercaFoglioMappale(foglio, mappale, "allegato", "asc");
			response = new ResponseData(true, listaFoglioMappale, listaFoglioMappale.size(), listaFoglioMappale.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaFoglioMappale", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
}
