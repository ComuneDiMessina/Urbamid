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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DettaglioPtMultiploDTO;
import it.eng.tz.urbamid.web.dto.DettaglioSoggettiPTDTO;
import it.eng.tz.urbamid.web.dto.DettaglioSoggettiUIDTO;
import it.eng.tz.urbamid.web.dto.IdentificativiUiuDTO;
import it.eng.tz.urbamid.web.dto.ListaIntestatariDirittoDTO;
import it.eng.tz.urbamid.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.web.dto.PlanimetriaUiuDTO;
import it.eng.tz.urbamid.web.dto.SoggettiUiuDTO;
import it.eng.tz.urbamid.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.web.services.RicercaSoggetti;

@Controller(value = "Ricerche Controller")
@RequestMapping("/ricercheController")
public class RicercaSoggettiCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CatastoCtrl.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private RicercaSoggetti ricercheService;

	@GetMapping(value="/ricercaPersoneFisiche", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="sesso", required=false) String sesso,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="dataNascitaDa", required=false) String dataNascitaDa,
			@RequestParam(value="dataNascitaA", required=false) String dataNascitaA,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="note", required=false) String note,
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaPersoneFisiche");
		DataTableDTO response = null;
		try {
			response = ricercheService.ricercaPersoneFisiche(nome, cognome, sesso, codiceFiscale, dataNascitaDa, dataNascitaA, provincia, comune, note, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in ricercaPersoneFisiche", e);
		}
		return response;
	}

	@GetMapping(value="/dettaglioSoggettiPT", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioSoggettiPT(HttpServletRequest request, 
			@RequestParam(value="idSoggetto") Integer idSoggetto) {
		logger.debug("GET dettaglioSoggettiPT");
		ResponseData response = null;
		try {
			List<DettaglioSoggettiPTDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioSoggettiPT(idSoggetto);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/dettaglioSoggettiUIU", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioSoggettiUIU(HttpServletRequest request, 
			@RequestParam(value="idSoggetto") Integer idSoggetto) {
		logger.debug("GET dettaglioSoggettiUIU");
		ResponseData response = null;
		try {
			List<DettaglioSoggettiUIDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioSoggettiUIU(idSoggetto);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiUIU", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/ricercaSoggettiGiuridici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provinciaNascita", required=false) String provinciaNascita,
			@RequestParam(value="comuneNascita", required=false) String comuneNascita,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaPersoneFisiche");
		DataTableDTO response = null;
		try {
			response = ricercheService.ricercaSoggettiGiuridici(denominazione, provinciaNascita, comuneNascita, codiceFiscale, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in ricercaSoggettiGiuridici", e);
		}
		return response;
	}

	@GetMapping(value="/exportPersoneFisiche", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="sesso", required=false) String sesso,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="dataNascitaDa", required=false) String dataNascitaDa,
			@RequestParam(value="dataNascitaA", required=false) String dataNascitaA,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="note", required=false) String note,
			@RequestParam(value="pageIndex", required=false, defaultValue="0") Integer pageIndex,
			@RequestParam(value="pageSize", required=false, defaultValue="10") Integer pageSize,
			HttpServletResponse response) {
		logger.debug("GET exportPersoneFisiche");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xls");
			printWriter.write(ricercheService.exportPersoneFisiche(nome, cognome, sesso, codiceFiscale, dataNascitaDa, dataNascitaA, provincia, comune, note));
		} catch (Exception e) {
			logger.error("Error in exportPersoneFisiche", e);
		}
	}

	@GetMapping(value="/exportSoggettiGiuridici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provinciaNascita", required=false) String provinciaNascita,
			@RequestParam(value="comuneNascita", required=false) String comuneNascita,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			HttpServletResponse response) {
		logger.debug("GET exportSoggettiGiuridici");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xls");
			printWriter.write(ricercheService.exportSoggettiGiuridici(denominazione, provinciaNascita, comuneNascita, codiceFiscale));
		} catch (Exception e) {
			logger.error("Error in exportSoggettiGiuridici", e);
		}
	}

	@GetMapping(value="/ricercaImmobiliUIU", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaImmobiliUIU(HttpServletRequest request, 
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
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaImmobiliUIU");
		DataTableDTO response = null;
		try {
			response = ricercheService.ricercaImmobiliUIU(comune, indirizzo, zona, sezioneUrbana, consistenza, categoria, foglio, superficie, classe, numero, renditaLire, partita, subalterno, renditaEuro, soppresso, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in ricercaImmobiliUIU", e);
		}
		return response;
	}

	@GetMapping(value="/dettaglioImmobiliUIUIdentificativi", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioImmobiliUIUIdentificativi(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET dettaglioImmobiliUIUIdentificativi");
		ResponseData response = null;
		try {
			List<IdentificativiUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioImmobiliUIUIdentificativi(idImmobile);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/dettaglioImmobiliUIUPersoneFisiche", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioImmobiliUIUPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET dettaglioImmobiliUIUPersoneFisiche");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioImmobiliUIUPersoneFisiche(idImmobile);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/dettaglioImmobiliUIUSoggettiGiuridici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioImmobiliUIUSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET dettaglioImmobiliUIUSoggettiGiuridici");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioImmobiliUIUSoggettiGiuridici(idImmobile);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/dettaglioImmobiliUIUPlanimetrie", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioImmobiliUIUPlanimetrie(HttpServletRequest request, 
			@RequestParam(value="foglio") Long foglio,
			@RequestParam(value="numero") Long numero,
			@RequestParam(value="subalterno", required=false) Long subalterno) {
		logger.debug("GET dettaglioImmobiliUIUPlanimetrie");
		ResponseData response = null;
		try {
			List<PlanimetriaUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioImmobiliUIUPlanimetrie(foglio, numero, subalterno);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioImmobiliUIUPlanimetrie", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/getUIUPlanimetrie", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void getUIUPlanimetrie(HttpServletRequest request, 
			@RequestParam(value="nomeImage") String nomeImage,
			HttpServletResponse response) {
		
		logger.debug("POST getUIUPlanimetrie");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+nomeImage+".tif");
			printWriter.write(ricercheService.getUIUPlanimetrie(nomeImage));
		} catch (Exception e) {
			logger.error("Error in getUIUPlanimetrie", e);
		}
	}

	@GetMapping(value="/dettaglioParticellePTPersoneFisiche", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioParticellePTPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET dettaglioParticellePTPersoneFisiche");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioParticellePTPersoneFisiche(idImmobile);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioParticellePTPersoneFisiche", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/dettaglioParticellePTSoggettiGiuridici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettaglioParticellePTSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET dettaglioParticellePTSoggettiGiuridici");
		ResponseData response = null;
		try {
			List<SoggettiUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettaglioParticellePTSoggettiGiuridici(idImmobile);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioParticellePTSoggettiGiuridici", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/exportImmobiliUIU", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportImmobiliUIU(HttpServletRequest request, 
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
			HttpServletResponse response) {
		logger.debug("GET exportSoggettiGiuridici");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xls");
			printWriter.write(ricercheService.exportImmobiliUIU(comune, indirizzo, zona, sezioneUrbana, consistenza, categoria, foglio, superficie, classe, numero, renditaLire, partita, subalterno, renditaEuro, soppresso));
		} catch (Exception e) {
			logger.error("Error in exportSoggettiGiuridici", e);
		}
	}

	@GetMapping(value="/localizzaUIU", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData localizzaUIU(HttpServletRequest request, 
			@RequestParam(value="foglio") Long foglio,
			@RequestParam(value="numero") Long numero) {
		logger.debug("GET localizzaUIU");
		ResponseData response = null;
		try {
			String geometria;
			geometria = ricercheService.localizzaUIU(foglio, numero);
			response = new ResponseData(true, geometria, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in localizzaUIU", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/ricercaParticellePT", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaParticellePT(HttpServletRequest request, 
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
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaParticellePT");
		DataTableDTO response = null;
		try {
			response = ricercheService.ricercaParticellePT(comune, sezione, foglio, numero, subalterno, partita, redditoDominicaleEuro, redditoDominicaleLire, redditoAgrarioEuro, redditoAgrarioLire, superficie, soppresso, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in ricercaParticellePT", e);
		}
		return response;
	}

	@GetMapping(value="/dettagliParticelleUIUIdentificativi", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettagliParticelleUIUIdentificativi(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET dettagliParticelleUIUIdentificativi");
		ResponseData response = null;
		try {
			List<IdentificativiUiuDTO> listaRisultati;
			listaRisultati = ricercheService.dettagliParticelleUIUIdentificativi(idImmobile);
			response = new ResponseData(true, listaRisultati, listaRisultati.size(), listaRisultati.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in dettaglioSoggettiPT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/localizzaPT", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData localizzaPT(HttpServletRequest request, 
			@RequestParam(value="foglio") Long foglio,
			@RequestParam(value="numero") Long numero) {
		logger.debug("GET localizzaPT");
		ResponseData response = null;
		try {
			String geometria;
			geometria = ricercheService.localizzaPT(foglio, numero);
			response = new ResponseData(true, geometria, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in localizzaUIU", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/dettagliParticellePTMultiplo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData dettagliParticellePTMultiplo(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET localizzaPT");
		ResponseData response = null;
		try {
			DettaglioPtMultiploDTO listaRisultati;
			listaRisultati = ricercheService.dettagliParticellePTMultiplo(idImmobile);
			response = new ResponseData(true, listaRisultati, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in localizzaUIU", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/exportParticellePT", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportParticellePT(HttpServletRequest request, 
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
			HttpServletResponse response) {
		logger.debug("GET exportParticellePT");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xls");
			printWriter.write(ricercheService.exportParticellePT(comune, sezione, foglio, numero, subalterno, partita, redditoDominicaleEuro, redditoDominicaleLire, redditoAgrarioEuro, redditoAgrarioLire, superficie, soppresso));
		} catch (Exception e) {
			logger.error("Error in exportParticellePT", e);
		}
	}

	@GetMapping(value="/ricercaIntestazioniPersoneFisiche", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaIntestazioniPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuPf", required=false) boolean checkboxUiuPf,
			@RequestParam(value="checkboxPtPf", required=false) boolean checkboxPtPf,
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaIntestazioniPersoneFisiche");
		DataTableDTO response = null;
		try {
			response = ricercheService.ricercaIntestazioniPersoneFisiche(nome, cognome, codiceFiscale, checkboxUiuPf, checkboxPtPf, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in ricercaParticellePT", e);
		}
		return response;
	}

	@GetMapping(value="/informazioniParticellaByIdImmobile", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData informazioniParticellaByIdImmobile(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET informazioniParticellaByIdImmobile");
		ResponseData response = null;
		try {
			ParticelleTerreniDTO listaRisultati;
			listaRisultati = ricercheService.informazioniParticellaByIdImmobile(idImmobile);
			response = new ResponseData(true, listaRisultati, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in informazioniParticellaByIdImmobile", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/informazioniUnitaImmobiliareByIdImmobile", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData informazioniUnitaImmobiliareByIdImmobile(HttpServletRequest request, 
			@RequestParam(value="idImmobile") Integer idImmobile) {
		logger.debug("GET informazioniUnitaImmobiliareByIdImmobile");
		ResponseData response = null;
		try {
			UnitaImmobiliareDTO listaRisultati;
			listaRisultati = ricercheService.informazioniUnitaImmobiliareByIdImmobile(idImmobile);
			response = new ResponseData(true, listaRisultati, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in informazioniUnitaImmobiliareByIdImmobile", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/exportIntestazioniPF", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportIntestazioniPF(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuPf", required=false) boolean checkboxUiuPf,
			@RequestParam(value="checkboxPtPf", required=false) boolean checkboxPtPf,
			HttpServletResponse response) {
		logger.debug("GET exportIntestazioniPF");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xls");
			printWriter.write(ricercheService.exportIntestazioniPF(nome, cognome, codiceFiscale, checkboxUiuPf, checkboxPtPf));
		} catch (Exception e) {
			logger.error("Error in exportParticellePT", e);
		}
	}

	@GetMapping(value="/ricercaIntestazioniSoggettiGiuridici", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaIntestazioniSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuSg", required=false) boolean checkboxUiuSg,
			@RequestParam(value="checkboxPtSg", required=false) boolean checkboxPtSg,
			@RequestParam("start") int page,
			@RequestParam("length") int size,
			@RequestParam("draw") int draw) {
		logger.debug("GET ricercaIntestazioniPersoneFisiche");
		DataTableDTO response = null;
		try {
			response = ricercheService.ricercaIntestazioniSoggettiGiuridici(denominazione, provincia, comune, codiceFiscale, checkboxUiuSg, checkboxPtSg, page, size, draw);
		} catch (Exception e) {
			logger.error("Error in ricercaParticellePT", e);
		}
		return response;
	}

	@GetMapping(value="/exportIntestazioniSG", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportIntestazioniSG(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuSg", required=false) boolean checkboxUiuSg,
			@RequestParam(value="checkboxPtSg", required=false) boolean checkboxPtSg,
			HttpServletResponse response) {
		logger.debug("GET exportIntestazioniSG");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.xls");
			printWriter.write(ricercheService.exportIntestazioniSG(denominazione, provincia, comune, codiceFiscale, checkboxUiuSg, checkboxPtSg));
		} catch (Exception e) {
			logger.error("Error in exportParticellePT", e);
		}
	}

	@GetMapping(value="/ricercaListaIntestatariTranneCorrenteConDiritto", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData ricercaListaIntestatariTranneCorrenteConDiritto(HttpServletRequest request, 
			@RequestParam(name="idImmobile") Long idImmobile,
			@RequestParam(name="idSoggetto") Long idSoggetto) {
		logger.debug("GET ricercaListaIntestatariTranneCorrenteConDiritto");
		ResponseData response = null;
		try {
			List<ListaIntestatariDirittoDTO> listaIntestatari;
			listaIntestatari = ricercheService.ricercaListaIntestatariTranneCorrenteConDiritto(idImmobile, idSoggetto);
			response = new ResponseData(true, listaIntestatari, listaIntestatari.size(), listaIntestatari.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in ricercaSuParticelleUI", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/getDataUltimoAggiornamento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getDataUltimoAggiornamento(HttpServletRequest request) {
		logger.debug("GET getDataUltimoAggiornamento");
		ResponseData response = null;
		try {
			String dataAggiornamento;
			dataAggiornamento = ricercheService.getDataUltimoAggiornamento();
			response = new ResponseData(true, dataAggiornamento, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getDataUltimoAggiornamento", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@GetMapping(value="/exportVisuraCatastaleFabbricati", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportVisuraCatastaleFabbricati(HttpServletRequest request,
			@RequestParam(name="idImmobile") Long idImmobile, HttpServletResponse response) {
		logger.debug("POST exportTerreniPerParticella");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(ricercheService.exportVisuraCatastaleFabbricati(idImmobile));
		} catch (Exception e) {
			logger.error("Error in exportTerreniPerParticella", e);
		}
	}

	@GetMapping(value="/exportVisuraCatastaleTerreni", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportVisuraCatastaleTerreni(HttpServletRequest request,
			@RequestParam(name="idImmobile") Long idImmobile, HttpServletResponse response) {
		logger.debug("POST exportVisuraCatastaleTerreni");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(ricercheService.exportVisuraCatastaleTerreni(idImmobile));
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleTerreni", e);
		}
	}

	@GetMapping(value="/exportVisuraCatastaleStoricaTerreni", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportVisuraCatastaleStoricaTerreni(HttpServletRequest request,
			@RequestParam(name="idImmobile") Long idImmobile, HttpServletResponse response) {
		logger.debug("POST exportVisuraCatastaleStoricaTerreni");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(ricercheService.exportVisuraCatastaleStoricaTerreni(idImmobile));
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleStoricaTerreni", e);
		}
	}

	@GetMapping(value="/exportVisuraCatastaleStoricaFabbricati", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void exportVisuraCatastaleStoricaFabbricati(HttpServletRequest request,
			@RequestParam(name="idImmobile") Long idImmobile, HttpServletResponse response) {
		logger.debug("POST exportVisuraCatastaleStoricaFabbricati");
		try {
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(ricercheService.exportVisuraCatastaleStoricaFabbricati(idImmobile));
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleStoricaFabbricati", e);
		}
	}

}
