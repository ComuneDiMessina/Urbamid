package it.eng.tz.urbamid.catasto.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.catasto.filter.ImmobileFilter;
import it.eng.tz.urbamid.catasto.filter.IntestatariFilter;
import it.eng.tz.urbamid.catasto.filter.ParticellaFilter;
import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.service.RicercaSoggettiService;
import it.eng.tz.urbamid.catasto.util.DateUtils;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioPtMultiploDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioSoggettiPTDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioSoggettiUIDTO;
import it.eng.tz.urbamid.catasto.web.dto.IdentificativiUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDirittoDTO;
import it.eng.tz.urbamid.catasto.web.dto.PagedResult;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.catasto.web.dto.PlanimetriaUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaIntestazioniDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaSoggettiDTO;
import it.eng.tz.urbamid.catasto.web.dto.SoggettiUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.catasto.web.response.ResponseData;

@RestController
@RequestMapping("/catasto/rest/api/ricercheController")
@Api(value = "urbamid catasto.ricerche", tags= {"Ricerche"})
public class RicercaSoggettiRESTController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(RicercaSoggettiRESTController.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private RicercaSoggettiService ricercaSoggettiService;

	@ApiOperation("Recupera tutti i soggetti in base ai filtri")
	@GetMapping("/ricercaPersoneFisiche")
	public ResponseData ricercaPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="sesso", required=false) String sesso,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="dataNascitaDa", required=false) String dataNascitaDa,
			@RequestParam(value="dataNascitaA", required=false) String dataNascitaA,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="note", required=false) String note,
			//@RequestParam("pagina") Integer pagina,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw) throws Exception {
		logger.debug("GET ricercaPersoneFisiche");
		ResponseData response = null;
		try {
			SoggettoFilter filter = new SoggettoFilter();
			filter.setNome(nome);
			filter.setCognome(cognome);
			filter.setSesso(sesso);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setDataNascitaDa(DateUtils.stringToDate(dataNascitaDa, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setDataNascitaA(DateUtils.stringToDate(dataNascitaA, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setProvincia(provincia);
			filter.setComune(comune);
			filter.setNote(note);
			filter.setPageIndex(page);
			filter.setPageSize(size);
			filter.setTipo("P");
			PagedResult<RicercaSoggettiDTO> listaSoggetti = ricercaSoggettiService.ricercaPersoneFisiche(filter);
			//List<RicercaSoggettiDTO> list = listaSoggetti.getContent().stream().collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(RicercaSoggettiDTO::getCodiceFiscale)))).stream().collect(Collectors.toList());
			response = new ResponseData(true, listaSoggetti.getContent(), (int)listaSoggetti.getTotalElements(), (int)listaSoggetti.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio in base all'id del soggetto")
	@GetMapping("/dettaglioSoggettiPT")
	public ResponseData dettaglioSoggettiPT(HttpServletRequest request, 
			@RequestParam(value="idSoggetto") Integer idSoggetto) throws Exception {
		logger.debug("GET ricercaPersoneFisiche");
		ResponseData response = null;
		try {
			List<DettaglioSoggettiPTDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioSoggettiPT(idSoggetto);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio in base all'id del soggetto")
	@GetMapping("/dettaglioSoggettiUIU")
	public ResponseData dettaglioSoggettiUIU(HttpServletRequest request, 
			@RequestParam(value="idSoggetto") Integer idSoggetto) throws Exception {
		logger.debug("GET dettaglioSoggettiUIU");
		ResponseData response = null;
		try {
			List<DettaglioSoggettiUIDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioSoggettiUIU(idSoggetto);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiUIU", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera tutti i soggetti in base ai filtri")
	@GetMapping("/ricercaSoggettiGiuridici")
	public ResponseData ricercaSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provinciaNascita", required=false) String provinciaNascita,
			@RequestParam(value="comuneNascita", required=false) String comuneNascita,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw) throws Exception {
		logger.debug("GET ricercaSoggettiGiuridici");
		ResponseData response = null;
		try {
			SoggettoFilter filter = new SoggettoFilter();
			filter.setDenominazione(denominazione);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setProvincia(provinciaNascita);
			filter.setComune(comuneNascita);
			filter.setPageIndex(page);
			filter.setPageSize(size);
			filter.setTipo("G");
			PagedResult<RicercaSoggettiDTO> listaSoggetti = ricercaSoggettiService.ricercaPersoneFisiche(filter);
			//List<RicercaSoggettiDTO> list = listaSoggetti.getContent().stream().collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(RicercaSoggettiDTO::getCodiceFiscale)))).stream().collect(Collectors.toList());
			response = new ResponseData(true, listaSoggetti.getContent(), (int)listaSoggetti.getTotalElements(), (int)listaSoggetti.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera tutti gli immobili di tipo UIU in base ai filtri")
	@GetMapping("/ricercaImmobiliUIU")
	public ResponseData ricercaImmobiliUIU(HttpServletRequest request, 
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="indirizzo", required=false) String indirizzo,
			@RequestParam(value="zona", required=false) String zona,
			@RequestParam(value="sezioneUrbana", required=false) String sezioneUrbana,
			@RequestParam(value="consistenza", required=false) String consistenza,
			@RequestParam(value="categoria", required=false) String categoria,
			@RequestParam(value="foglio", required=false) String foglio,
			@RequestParam(value="superficie", required=false) String superficie,
			@RequestParam(value="classe", required=false) String classe,
			@RequestParam(value="numero", required=false) String numero,
			@RequestParam(value="renditaLire", required=false) String renditaLire,
			@RequestParam(value="partita", required=false) String partita,
			@RequestParam(value="subalterno", required=false) String subalterno,
			@RequestParam(value="renditaEuro", required=false) String renditaEuro,
			@RequestParam(value="soppresso", required=false) boolean soppresso,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw) throws Exception {
		logger.debug("GET ricercaImmobiliUIU");
		ResponseData response = null;
		try {
			ImmobileFilter filter = new ImmobileFilter();
			//L'utilizzo di questi operatori ternari è per "forzare" il decimale, visto che a db tutti i valori terminano con .00
			//tutti i valori sono trattati come string durante l'esecuzione della query
			filter.setComune(comune);
			filter.setIndirizzo(indirizzo);
			filter.setZona(zona);
			filter.setSezioneUrbana(sezioneUrbana);
			filter.setConsistenza(consistenza != null ? consistenza + ".00" : null);
			filter.setCategoria(categoria);
			filter.setFoglio(foglio);
			filter.setNumero(numero);
			filter.setSuperficie(superficie != null ? superficie + ".00" : null);
			filter.setClasse(classe);
			filter.setPartita(partita);
			filter.setSubalterno(subalterno);
			filter.setRenditaLire(renditaLire);
			filter.setRenditaEuro(renditaEuro != null ? renditaEuro + ".00" : null);
			filter.setPageIndex(page);
			filter.setPageSize(size);
			filter.setSoppresso(soppresso);
			PagedResult<UnitaImmobiliareDTO> listaSoggetti = ricercaSoggettiService.ricercaImmobiliUIU(filter);
			response = new ResponseData(true, listaSoggetti.getContent(), (int)listaSoggetti.getTotalElements(), (int)listaSoggetti.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaImmobiliUIU", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettaglioImmobiliUIUIdentificativi")
	public ResponseData dettaglioImmobiliUIUIdentificativi(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettaglioImmobiliUIUIdentificativi");
		ResponseData response = null;
		try {
			List<IdentificativiUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioImmobiliUIUIdentificativi(idImmobile);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettaglioImmobiliUIUPersoneFisiche")
	public ResponseData dettaglioImmobiliUIUPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettaglioImmobiliUIUPersoneFisiche");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioImmobiliUIUPersoneFisiche(idImmobile);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettaglioImmobiliUIUSoggettiGiuridici")
	public ResponseData dettaglioImmobiliUIUSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettaglioImmobiliUIUSoggettiGiuridici");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioImmobiliUIUSoggettiGiuridici(idImmobile);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettaglioImmobiliUIUPlanimetrie")
	public ResponseData dettaglioImmobiliUIUPlanimetrie(HttpServletRequest request, 
			@RequestParam(value="foglio") Long foglio,
			@RequestParam(value="numero") Long numero,
			@RequestParam(value="subalterno") Long subalterno) throws Exception {
		logger.debug("GET dettaglioImmobiliUIUPlanimetrie");
		ResponseData response = null;
		try {
			List<PlanimetriaUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioImmobiliUIUPlanimetrie(foglio, numero, subalterno);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioImmobiliUIUPlanimetrie", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera l'immagine della planimetria")
	@PostMapping("/getUIUPlanimetria")
	public @ResponseBody ResponseData getUIUPlanimetria(HttpServletRequest request, 
			@RequestParam(value="nomeImage") String nomeImage) {
		logger.debug("POST getUIUPlanimetria");
		ResponseData response = null;
		try {
			File file = ricercaSoggettiService.getUIUPlanimetria(nomeImage);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getUIUPlanimetria", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettaglioParticellePTPersoneFisiche")
	public ResponseData dettaglioParticellePTPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettaglioParticellePTPersoneFisiche");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioParticellePTPersoneFisiche(idImmobile);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioParticellePTPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettaglioParticellePTSoggettiGiuridici")
	public ResponseData dettaglioParticellePTSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettaglioParticellePTSoggettiGiuridici");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettaglioParticellePTSoggettiGiuridici(idImmobile);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioParticellePTSoggettiGiuridici", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la geometria dell'immobile per la localizzazione su mappa")
	@GetMapping("/localizzaUIU")
	public ResponseData localizzaUIU(HttpServletRequest request, 
			@RequestParam(value="foglio") Long foglio,
			@RequestParam(value="numero") Long numero) throws Exception {
		logger.debug("GET localizzaUIU");
		ResponseData response = null;
		try {
			String dettaglio;
			dettaglio = ricercaSoggettiService.localizzaUIU(foglio, numero);
			response = new ResponseData(true, dettaglio, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera tutte le particelle in base ai filtri")
	@GetMapping("/ricercaParticellePT")
	public ResponseData ricercaParticellePT(HttpServletRequest request, 
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="sezione", required=false) String sezione,
			@RequestParam(value="foglio", required=false) String foglio,
			@RequestParam(value="numero", required=false) String numero,
			@RequestParam(value="subalterno", required=false) String subalterno,
			@RequestParam(value="partita", required=false) String partita,
			@RequestParam(value="redditoDominicaleEuro", required=false) String redditoDominicaleEuro,
			@RequestParam(value="redditoDominicaleLire", required=false) String redditoDominicaleLire,
			@RequestParam(value="redditoAgrarioEuro", required=false) String redditoAgrarioEuro,
			@RequestParam(value="redditoAgrarioLire", required=false) String redditoAgrarioLire,
			@RequestParam(value="superficie", required=false) String superficie,
			@RequestParam(value="soppresso", required=false) boolean soppresso,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw) throws Exception {
		logger.debug("GET ricercaParticellePT");
		ResponseData response = null;
		try {
			ParticellaFilter filter = new ParticellaFilter();
			filter.setComune(comune);
			filter.setSezione(sezione);
			filter.setFoglio(foglio);
			filter.setNumero(numero);
			filter.setSubalterno(subalterno);
			filter.setPartita(partita);
			filter.setRedditoDominicaleEuro(redditoDominicaleEuro);
			filter.setRedditoDominicaleLire(redditoDominicaleLire);
			filter.setRedditoAgrarioEuro(redditoAgrarioEuro);
			filter.setRedditoAgrarioLire(redditoAgrarioLire);
			filter.setSuperficie(superficie);
			filter.setPageIndex(page);
			filter.setPageSize(size);
			filter.setSoppresso(soppresso);
			PagedResult<ParticelleTerreniDTO> listaSoggetti = ricercaSoggettiService.ricercaParticellePT(filter);
			response = new ResponseData(true, listaSoggetti.getContent(), (int)listaSoggetti.getTotalElements(), (int)listaSoggetti.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaParticellePT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio degli identificativi uiu in base all'id immobile")
	@GetMapping("/dettagliParticelleUIUIdentificativi")
	public ResponseData dettagliParticelleUIUIdentificativi(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettagliParticelleUIUIdentificativi");
		ResponseData response = null;
		try {
			List<IdentificativiUiuDTO> dettaglio;
			dettaglio = ricercaSoggettiService.dettagliParticelleUIUIdentificativi(idImmobile);
			response = new ResponseData(true, dettaglio, dettaglio.size(), dettaglio.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettagliParticelleUIUIdentificativi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la geometria della particella per la localizzazione su mappa")
	@GetMapping("/localizzaPT")
	public ResponseData localizzaPT(HttpServletRequest request, 
			@RequestParam(value="foglio") Long foglio,
			@RequestParam(value="numero") Long numero) throws Exception {
		logger.debug("GET localizzaPT");
		ResponseData response = null;
		try {
			String dettaglio;
			dettaglio = ricercaSoggettiService.localizzaPT(foglio, numero);
			response = new ResponseData(true, dettaglio, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in localizzaPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio porzione, riserva e deduzione della particella in base all'id immobile")
	@GetMapping("/dettagliParticellePTMultiplo")
	public ResponseData dettagliParticellePTMultiplo(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET dettagliParticellePTMultiplo");
		ResponseData response = null;
		try {
			DettaglioPtMultiploDTO dettaglio;
			dettaglio = ricercaSoggettiService.dettagliParticellePTMultiplo(idImmobile);
			response = new ResponseData(true, dettaglio, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettagliParticellePTMultiplo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera tutti gli intestatari in base ai filtri")
	@GetMapping("/ricercaIntestazioniPersoneFisiche")
	public ResponseData ricercaIntestazioniPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuPf", required=false) boolean checkboxUiuPf,
			@RequestParam(value="checkboxPtPf", required=false) boolean checkboxPtPf,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw) throws Exception {
		logger.debug("GET ricercaIntestazioniPersoneFisiche");
		ResponseData response = null;
		try {
			IntestatariFilter filter = new IntestatariFilter();
			filter.setNome(nome);
			filter.setCognome(cognome);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setCheckboxPtPf(checkboxPtPf);
			filter.setCheckboxUiuPf(checkboxUiuPf);
			filter.setPageIndex(page);
			filter.setPageSize(size);
			PagedResult<RicercaIntestazioniDTO> listaSoggetti = ricercaSoggettiService.ricercaIntestazioniPersoneFisiche(filter);
			List<RicercaIntestazioniDTO> queryCount = ricercaSoggettiService.soggettiFisiciQueryCount(filter);
			response = new ResponseData(true, listaSoggetti.getContent(), queryCount.size(), queryCount.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaIntestazioniPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio generico della particella in base all'id immobile")
	@GetMapping("/informazioniParticellaByIdImmobile")
	public ResponseData informazioniParticellaByIdImmobile(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET informazioniParticellaByIdImmobile");
		ResponseData response = null;
		try {
			ParticelleTerreniDTO dettaglio;
			dettaglio = ricercaSoggettiService.informazioniParticellaByIdImmobile(idImmobile);
			response = new ResponseData(true, dettaglio, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in informazioniParticellaByIdImmobile", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il dettaglio generico di una unita immobiliare in base all'id immobile")
	@GetMapping("/informazioniUnitaImmobiliareByIdImmobile")
	public ResponseData informazioniUnitaImmobiliareByIdImmobile(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Long idImmobile) throws Exception {
		logger.debug("GET informazioniUnitaImmobiliareByIdImmobile");
		ResponseData response = null;
		try {
			UnitaImmobiliareDTO dettaglio;
			dettaglio = ricercaSoggettiService.informazioniUnitaImmobiliareByIdImmobile(idImmobile);
			response = new ResponseData(true, dettaglio, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in informazioniUnitaImmobiliareByIdImmobile", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera tutti gli intestatari in base ai filtri")
	@GetMapping("/ricercaIntestazioniSoggettiGiuridici")
	public ResponseData ricercaIntestazioniSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuSg", required=false) boolean checkboxUiuSg,
			@RequestParam(value="checkboxPtSg", required=false) boolean checkboxPtSg,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("draw") int draw) throws Exception {
		logger.debug("GET ricercaIntestazioniSoggettiGiuridici");
		ResponseData response = null;
		try {
			IntestatariFilter filter = new IntestatariFilter();
			filter.setDenominazione(denominazione);
			filter.setProvincia(provincia);
			filter.setComune(comune);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setCheckboxPtSg(checkboxPtSg);
			filter.setCheckboxUiuSg(checkboxUiuSg);
			filter.setPageIndex(page);
			filter.setPageSize(size);
			PagedResult<RicercaIntestazioniDTO> listaSoggetti = ricercaSoggettiService.ricercaIntestazioniSoggettiGiuridici(filter);
			List<RicercaIntestazioniDTO> queryCount = ricercaSoggettiService.soggettiGiuridiciQueryCount(filter);
			response = new ResponseData(true, listaSoggetti.getContent(), queryCount.size(), queryCount.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaIntestazioniPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera gli intestatari tranne quello corrente dato id immobile ed id soggetto")
	@GetMapping("/ricercaListaIntestatariTranneCorrenteConDiritto")
	public @ResponseBody ResponseData ricercaListaIntestatariTranneCorrenteConDiritto(HttpServletRequest request, 
			@ApiParam( value = "Id dell'immobile", required = false, example="1" )
			@RequestParam(name="idImmobile") Long idImmobile,
			@RequestParam(name="idSoggetto") Long idSoggetto) {
		logger.debug("GET ricercaListaIntestatariTranneCorrenteConDiritto");
		ResponseData response = null;
		try {
			List<ListaIntestatariDirittoDTO> listaIntestatari;
			listaIntestatari = ricercaSoggettiService.ricercaListaIntestatariTranneCorrenteConDiritto(idImmobile, idSoggetto);
			response = new ResponseData(true, listaIntestatari, listaIntestatari.size(), listaIntestatari.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaListaIntestatariTranneCorrenteConDiritto", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera gli intestatari tranne quello corrente dato id immobile ed id soggetto")
	@GetMapping("/getDataUltimoAggiornamento")
	public @ResponseBody ResponseData getDataUltimoAggiornamento(HttpServletRequest request) {
		logger.debug("GET getDataUltimoAggiornamento");
		ResponseData response = null;
		try {
			String dataAggiornamento;
			dataAggiornamento = ricercaSoggettiService.getDataUltimoAggiornamento();
			response = new ResponseData(true, dataAggiornamento, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getDataUltimoAggiornamento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
