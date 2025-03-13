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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.catasto.filter.ImmobileFilter;
import it.eng.tz.urbamid.catasto.filter.IntestatariFilter;
import it.eng.tz.urbamid.catasto.filter.ParticellaFilter;
import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.service.ExportService;
import it.eng.tz.urbamid.catasto.util.DateUtils;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.catasto.web.response.ResponseData;

@RestController
@RequestMapping("/catasto/rest/api/export")
@Api(value = "urbamid catasto.export", tags= {"CatastoExport"})
public class ExportRESTController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ExportRESTController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private ExportService exportService;

	@ApiOperation("Esporta tutti e 4 i tab visualizzati a video in formato .xls")
	@PostMapping("/exportXls")
	public @ResponseBody ResponseData exportXls(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST exportXls");
		ResponseData response = null;
		try {
			File file = exportService.exportXls(lsParticelle);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Esporta il tab soggetti a video in formato .xls")
	@PostMapping("/exportSoggettiXls")
	public @ResponseBody ResponseData exportSoggettiXls(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST exportXlsSoggetti");
		ResponseData response = null;
		try {
			File file = exportService.exportXlsSoggetti(lsParticelle);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXlsSoggetti", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Esporta il tab intestazioni a video in formato .xls")
	@PostMapping("/exportIntestazioniXls")
	public @ResponseBody ResponseData exportIntestazioniXls(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST exportIntestazioniXls");
		ResponseData response = null;
		try {
			File file = exportService.exportXlsIntestazioni(lsParticelle);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportIntestazioniXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Esporta il tab UIU a video in formato .xls")
	@PostMapping("/exportUiuXls")
	public @ResponseBody ResponseData exportUiuXls(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST exportXls");
		ResponseData response = null;
		try {
			File file = exportService.exportXlsUiu(lsParticelle);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Esporta il tab terreni a video in formato .xls")
	@PostMapping("/exportTerreniXls")
	public @ResponseBody ResponseData exportTerreniXls(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle) {
		logger.debug("POST exportTerreniXls");
		ResponseData response = null;
		try {
			File file = exportService.exportXlsTerreni(lsParticelle);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportTerreniXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Esporta i risultati della ricerca soggetti in formato .xls")
	@GetMapping("/exportPersoneFisiche")
	public @ResponseBody ResponseData exportPersoneFisiche(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="sesso", required=false) String sesso,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="dataNascitaDa", required=false) String dataNascitaDa,
			@RequestParam(value="dataNascitaA", required=false) String dataNascitaA,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="note", required=false) String note) {
		logger.debug("GET exportPersoneFisiche");
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
			filter.setTipo("P");
			File file = exportService.exportPersoneFisiche(filter);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i risultati della ricerca soggetti in formato .xls")
	@GetMapping("/exportSoggettiGiuridici")
	public @ResponseBody ResponseData exportSoggettiGiuridici(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provinciaNascita", required=false) String provinciaNascita,
			@RequestParam(value="comuneNascita", required=false) String comuneNascita,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale) {
		logger.debug("GET exportPersoneFisiche");
		ResponseData response = null;
		try {
			SoggettoFilter filter = new SoggettoFilter();
			filter.setDenominazione(denominazione);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setProvincia(provinciaNascita);
			filter.setComune(comuneNascita);
			filter.setTipo("G");
			File file = exportService.exportSoggettiGiuridici(filter);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i risultati della ricerca uiu in formato .xls")
	@GetMapping("/exportImmobiliUIU")
	public @ResponseBody ResponseData exportImmobiliUIU(HttpServletRequest request, 
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
			@RequestParam(value="soppresso", required=false) boolean soppresso) {
		logger.debug("GET exportImmobiliUIU");
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
			filter.setSoppresso(soppresso);
			File file = exportService.exportImmobiliUIU(filter);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i risultati della ricerca pt in formato .xls")
	@GetMapping("/exportParticellePT")
	public @ResponseBody ResponseData exportParticellePT(HttpServletRequest request, 
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
			@RequestParam(value="soppresso", required=false) boolean soppresso) {
		logger.debug("GET exportParticellePT");
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
			filter.setSoppresso(soppresso);
			File file = exportService.exportParticellePT(filter);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportParticellePT", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i risultati della ricerca intestzioni persone fisiche a in formato .xls")
	@GetMapping("/exportIntestazioniPF")
	public @ResponseBody ResponseData exportIntestazioniPF(HttpServletRequest request, 
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuPf", required=false) boolean checkboxUiuPf,
			@RequestParam(value="checkboxPtPf", required=false) boolean checkboxPtPf) {
		logger.debug("GET exportIntestazioniPF");
		ResponseData response = null;
		try {
			IntestatariFilter filter = new IntestatariFilter();
			filter.setNome(nome);
			filter.setCognome(cognome);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setCheckboxPtPf(checkboxPtPf);
			filter.setCheckboxUiuPf(checkboxUiuPf);
			File file = exportService.exportIntestazioniPF(filter);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportIntestazioniPF", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i risultati della ricerca intestzioni persone fisiche a in formato .xls")
	@GetMapping("/exportIntestazioniSG")
	public @ResponseBody ResponseData exportIntestazioniSG(HttpServletRequest request, 
			@RequestParam(value="denominazione", required=false) String denominazione,
			@RequestParam(value="provincia", required=false) String provincia,
			@RequestParam(value="comune", required=false) String comune,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="checkboxUiuSg", required=false) boolean checkboxUiuSg,
			@RequestParam(value="checkboxPtSg", required=false) boolean checkboxPtSg) {
		logger.debug("GET exportIntestazioniPF");
		ResponseData response = null;
		try {
			IntestatariFilter filter = new IntestatariFilter();
			filter.setDenominazione(denominazione);
			filter.setProvincia(provincia);
			filter.setComune(comune);
			filter.setCodiceFiscale(codiceFiscale);
			filter.setCheckboxPtSg(checkboxPtSg);
			filter.setCheckboxUiuSg(checkboxUiuSg);
			File file = exportService.exportIntestazioniSG(filter);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportIntestazioniPF", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i fabbricati raggruppati per nominativo in formato .pdf")
	@PostMapping("/exportFabbricatiPerNominativo")
	public @ResponseBody ResponseData exportFabbricatiPerNominativo(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione) {
		logger.debug("POST expoexportFabbricatiPerNominativortXls");
		ResponseData response = null;
		try {
			File file = exportService.exportFabbricatiPerNominativo(lsParticelle, titolo, tipologiaEstrazione);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i terreni raggruppati per nominativo in formato .pdf")
	@PostMapping("/exportTerreniPerNominativo")
	public @ResponseBody ResponseData exportTerreniPerNominativo(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione) {
		logger.debug("POST exportTerreniPerNominativo");
		ResponseData response = null;
		try {
			File file = exportService.exportTerreniPerNominativo(lsParticelle, titolo, tipologiaEstrazione);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i fabbricati raggruppati per particelle in formato .pdf")
	@PostMapping("/exportFabbricatiPerParticella")
	public @ResponseBody ResponseData exportFabbricatiPerParticella(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione) {
		logger.debug("POST exportFabbricatiPerParticella");
		ResponseData response = null;
		try {
			File file = exportService.exportFabbricatiPerParticella(lsParticelle, titolo, tipologiaEstrazione);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta i terreni raggruppati per particelle in formato .pdf")
	@PostMapping("/exportTerreniPerParticella")
	public @ResponseBody ResponseData exportTerreniPerParticella(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione) {
		logger.debug("POST exportTerreniPerParticella");
		ResponseData response = null;
		try {
			File file = exportService.exportTerreniPerParticella(lsParticelle, titolo, tipologiaEstrazione);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportXls", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta tutti i report .pdf in uno zip")
	@PostMapping("/exportPdf")
	public @ResponseBody ResponseData exportPdf(HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo,
			@RequestParam String tipologiaEstrazione) {
		logger.debug("POST exportPdf");
		ResponseData response = null;
		try {
			File file = exportService.exportPdf(lsParticelle, titolo, tipologiaEstrazione);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportPdf", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta la visura catastale in formato .pdf dato l'id dell'immobile")
	@GetMapping("/exportVisuraCatastaleFabbricati")
	public @ResponseBody ResponseData exportVisuraCatastaleFabbricati(HttpServletRequest request, 
			@RequestParam Long idImmobile) {
		logger.debug("POST exportVisuraCatastaleFabbricati");
		ResponseData response = null;
		try {
			File file = exportService.exportVisuraCatastaleFabbricati(idImmobile);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleFabbricati", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta la visura catastale in formato .pdf dato l'id dell'immobile")
	@GetMapping("/exportVisuraCatastaleTerreni")
	public @ResponseBody ResponseData exportVisuraCatastaleTerreni(HttpServletRequest request, 
			@RequestParam Long idImmobile) {
		logger.debug("POST exportVisuraCatastaleTerreni");
		ResponseData response = null;
		try {
			File file = exportService.exportVisuraCatastaleTerreni(idImmobile);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleTerreni", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta la visura catastale storica del terreno in formato .pdf dato l'id dell'immobile")
	@GetMapping("/exportVisuraCatastaleStoricaTerreni")
	public @ResponseBody ResponseData exportVisuraCatastaleStoricaTerreni(HttpServletRequest request, 
			@RequestParam Long idImmobile) {
		logger.debug("POST exportVisuraCatastaleStoricaTerreni");
		ResponseData response = null;
		try {
			File file = exportService.exportVisuraCatastaleStoricaTerreni(idImmobile);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleStoricaTerreni", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta la visura catastale storica del fabbricato in formato .pdf dato l'id dell'immobile")
	@GetMapping("/exportVisuraCatastaleStoricaFabbricati")
	public @ResponseBody ResponseData exportVisuraCatastaleStoricaFabbricati(HttpServletRequest request, 
			@RequestParam Long idImmobile) {
		logger.debug("POST exportVisuraCatastaleStoricaFabbricati");
		ResponseData response = null;
		try {
			File file = exportService.exportVisuraCatastaleStoricaFabbricati(idImmobile);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in exportVisuraCatastaleStoricaFabbricati", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
