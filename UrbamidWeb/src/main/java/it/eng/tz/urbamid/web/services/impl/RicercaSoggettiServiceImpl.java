package it.eng.tz.urbamid.web.services.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DettaglioPtMultiploDTO;
import it.eng.tz.urbamid.web.dto.DettaglioSoggettiPTDTO;
import it.eng.tz.urbamid.web.dto.DettaglioSoggettiUIDTO;
import it.eng.tz.urbamid.web.dto.IdentificativiUiuDTO;
import it.eng.tz.urbamid.web.dto.ListaIntestatariDirittoDTO;
import it.eng.tz.urbamid.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.web.dto.PlanimetriaUiuDTO;
import it.eng.tz.urbamid.web.dto.RicercaIntestazioniDTO;
import it.eng.tz.urbamid.web.dto.RicercaSoggettiDTO;
import it.eng.tz.urbamid.web.dto.SoggettiUiuDTO;
import it.eng.tz.urbamid.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.web.services.RicercaSoggetti;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class RicercaSoggettiServiceImpl implements RicercaSoggetti{

	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;

	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO ricercaPersoneFisiche(String nome, String cognome, String sesso,
			String codiceFiscale, String dataNascitaDa, String dataNascitaA, String provincia, String comune,
			String note, Integer page, Integer size, Integer draw) throws Exception {
		List<RicercaSoggettiDTO> lsSoggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_SOGGETTI_PF)+"?";
		if (nome != null && !nome.isEmpty() && !nome.equals("")) 
			url += "&nome=" + nome;
		if (cognome != null && !cognome.isEmpty() && !cognome.equals("")) 
			url += "&cognome=" + cognome;
		if (sesso != null && !sesso.isEmpty() && !sesso.equals("")) 
			url += "&sesso=" + sesso;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		if (dataNascitaDa != null && !dataNascitaDa.isEmpty() && !dataNascitaDa.equals("")) 
			url += "&dataNascitaDa=" + dataNascitaDa;
		if (dataNascitaA != null && !dataNascitaA.isEmpty() && !dataNascitaA.equals("")) 
			url += "&dataNascitaA=" + dataNascitaA;
		if (provincia != null && !provincia.isEmpty() && !provincia.equals("")) 
			url += "&provincia=" + provincia;
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (note != null && !note.isEmpty() && !note.equals("")) 
			url += "&note=" + note;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsSoggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RicercaSoggettiDTO>>() {});
			//response.setAaData(lsSoggetti);
		}
		//return lsSoggetti;
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsSoggetti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DettaglioSoggettiPTDTO> dettaglioSoggettiPT(Integer idSoggetto) throws Exception {
		List<DettaglioSoggettiPTDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_SOGGETTI_DETTAGLIO_PT)+"?idSoggetto="+idSoggetto;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DettaglioSoggettiPTDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DettaglioSoggettiUIDTO> dettaglioSoggettiUIU(Integer idSoggetto) throws Exception {
		List<DettaglioSoggettiUIDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_SOGGETTI_DETTAGLIO_UIU)+"?idSoggetto="+idSoggetto;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DettaglioSoggettiUIDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO ricercaSoggettiGiuridici(String denominazione, String provinciaNascita,
			String comuneNascita, String codiceFiscale, Integer page, Integer size, Integer draw) throws Exception {
		List<RicercaSoggettiDTO> lsSoggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_SOGGETTI_SG)+"?";
		if (denominazione != null && !denominazione.isEmpty() && !denominazione.equals("")) 
			url += "&denominazione=" + denominazione;
		if (provinciaNascita != null && !provinciaNascita.isEmpty() && !provinciaNascita.equals("")) 
			url += "&provinciaNascita=" + provinciaNascita;
		if (comuneNascita != null && !comuneNascita.isEmpty() && !comuneNascita.equals("")) 
			url += "&comuneNascita=" + comuneNascita;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsSoggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RicercaSoggettiDTO>>() {});
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsSoggetti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	public String exportPersoneFisiche(String nome, String cognome, String sesso, String codiceFiscale,
			String dataNascitaDa, String dataNascitaA, String provincia, String comune, String note) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_SOGGETTI_PF_EXPORT_XLS)+"?";
		if (nome != null && !nome.isEmpty() && !nome.equals("")) 
			url += "&nome=" + nome;
		if (cognome != null && !cognome.isEmpty() && !cognome.equals("")) 
			url += "&cognome=" + cognome;
		if (sesso != null && !sesso.isEmpty() && !sesso.equals("")) 
			url += "&sesso=" + sesso;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		if (dataNascitaDa != null && !dataNascitaDa.isEmpty() && !dataNascitaDa.equals("")) 
			url += "&dataNascitaDa=" + dataNascitaDa;
		if (dataNascitaA != null && !dataNascitaA.isEmpty() && !dataNascitaA.equals("")) 
			url += "&dataNascitaA=" + dataNascitaA;
		if (provincia != null && !provincia.isEmpty() && !provincia.equals("")) 
			url += "&provincia=" + provincia;
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (note != null && !note.isEmpty() && !note.equals("")) 
			url += "&note=" + note;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	public String exportSoggettiGiuridici(String denominazione, String provinciaNascita, String comuneNascita,
			String codiceFiscale) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_SOGGETTI_SG_EXPORT_XLS)+"?";
		if (denominazione != null && !denominazione.isEmpty() && !denominazione.equals("")) 
			url += "&denominazione=" + denominazione;
		if (provinciaNascita != null && !provinciaNascita.isEmpty() && !provinciaNascita.equals("")) 
			url += "&provinciaNascita=" + provinciaNascita;
		if (comuneNascita != null && !comuneNascita.isEmpty() && !comuneNascita.equals("")) 
			url += "&comuneNascita=" + comuneNascita;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO ricercaImmobiliUIU(String comune, String indirizzo, String zona, String sezioneUrbana,
			String consistenza, String categoria, String foglio, String superficie, String classe, String numero,
			String renditaLire, String partita, String subalterno, String renditaEuro, boolean soppresso,
			Integer page, Integer size,	Integer draw) throws Exception {
		List<UnitaImmobiliareDTO> lsSoggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU)+"?";
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (indirizzo != null && !indirizzo.isEmpty() && !indirizzo.equals("")) 
			url += "&indirizzo=" + indirizzo;
		if (zona != null && !zona.isEmpty() && !zona.equals("")) 
			url += "&zona=" + zona;
		if (sezioneUrbana != null && !sezioneUrbana.isEmpty() && !sezioneUrbana.equals("")) 
			url += "&sezioneUrbana=" + sezioneUrbana;
		if (consistenza != null && !consistenza.isEmpty() && !consistenza.equals("")) 
			url += "&consistenza=" + consistenza;
		if (categoria != null && !categoria.isEmpty() && !categoria.equals("")) 
			url += "&categoria=" + categoria;
		if (foglio != null && !foglio.isEmpty() && !foglio.equals("")) 
			url += "&foglio=" + foglio;
		if (superficie != null && !superficie.isEmpty() && !superficie.equals("")) 
			url += "&superficie=" + superficie;
		if (classe != null && !classe.isEmpty() && !classe.equals("")) 
			url += "&classe=" + classe;
		if (numero != null && !numero.isEmpty() && !numero.equals("")) 
			url += "&numero=" + numero;
		if (renditaLire != null && !renditaLire.isEmpty() && !renditaLire.equals("")) 
			url += "&renditaLire=" + renditaLire;
		if (partita != null && !partita.isEmpty() && !partita.equals("")) 
			url += "&partita=" + partita;
		if (subalterno != null && !subalterno.isEmpty() && !subalterno.equals("")) 
			url += "&subalterno=" + subalterno;
		if (renditaEuro != null && !renditaEuro.isEmpty() && !renditaEuro.equals("")) 
			url += "&renditaEuro=" + renditaEuro;
		url += "&soppresso=" + soppresso;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsSoggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<UnitaImmobiliareDTO>>() {});
			for (UnitaImmobiliareDTO unitaImmobiliareDTO : lsSoggetti) {
				unitaImmobiliareDTO.setRenditaEuroStr( df.format(unitaImmobiliareDTO.getRenditaEuro()) );
				unitaImmobiliareDTO.setRenditaLireStr( df.format(unitaImmobiliareDTO.getRenditaLire()) );
			}
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsSoggetti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<IdentificativiUiuDTO> dettaglioImmobiliUIUIdentificativi(Integer idImmobile) throws Exception {
		List<IdentificativiUiuDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_IDENTIFICATIVI)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<IdentificativiUiuDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<SoggettiUiuDTO> dettaglioImmobiliUIUPersoneFisiche(Integer idImmobile) throws Exception {
		List<SoggettiUiuDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_PF)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<SoggettiUiuDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	public List<SoggettiUiuDTO> dettaglioImmobiliUIUSoggettiGiuridici(Integer idImmobile) throws Exception {
		List<SoggettiUiuDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_SG)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<SoggettiUiuDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	public List<PlanimetriaUiuDTO> dettaglioImmobiliUIUPlanimetrie(Long foglio, Long numero, Long subalterno) throws Exception {
		List<PlanimetriaUiuDTO> planimetrie = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_PLANIMETRIE)+
				"?foglio="+foglio+"&numero="+numero;
		if (subalterno != null) 
			url += "&subalterno=" + subalterno;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			planimetrie = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PlanimetriaUiuDTO>>() {});		
		}
		return planimetrie;
	}
	
	@Override
	public String getUIUPlanimetrie(String nomeImage) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_IMAGE_PLANIMETRIE)+
				"?nomeImage="+nomeImage;	 
		ResponseData response = restService.restPostTable(url, null);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<SoggettiUiuDTO> dettaglioParticellePTPersoneFisiche(Integer idImmobile) throws Exception {
		List<SoggettiUiuDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT_PF)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<SoggettiUiuDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	public List<SoggettiUiuDTO> dettaglioParticellePTSoggettiGiuridici(Integer idImmobile) throws Exception {
		List<SoggettiUiuDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT_SG)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<SoggettiUiuDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	public String exportImmobiliUIU(String comune, String indirizzo, String zona, String sezioneUrbana,
			String consistenza, String categoria, String foglio, String superficie, String classe, String numero,
			String renditaLire, String partita, String subalterno, String renditaEuro, boolean soppresso)
			throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_EXPORT)+"?";
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (indirizzo != null && !indirizzo.isEmpty() && !indirizzo.equals("")) 
			url += "&indirizzo=" + indirizzo;
		if (zona != null && !zona.isEmpty() && !zona.equals("")) 
			url += "&zona=" + zona;
		if (sezioneUrbana != null && !sezioneUrbana.isEmpty() && !sezioneUrbana.equals("")) 
			url += "&sezioneUrbana=" + sezioneUrbana;
		if (consistenza != null && !consistenza.isEmpty() && !consistenza.equals("")) 
			url += "&consistenza=" + consistenza;
		if (categoria != null && !categoria.isEmpty() && !categoria.equals("")) 
			url += "&categoria=" + categoria;
		if (foglio != null && !foglio.isEmpty() && !foglio.equals("")) 
			url += "&foglio=" + foglio;
		if (superficie != null && !superficie.isEmpty() && !superficie.equals("")) 
			url += "&superficie=" + superficie;
		if (classe != null && !classe.isEmpty() && !classe.equals("")) 
			url += "&classe=" + classe;
		if (numero != null && !numero.isEmpty() && !numero.equals("")) 
			url += "&numero=" + numero;
		if (renditaLire != null && !renditaLire.isEmpty() && !renditaLire.equals("")) 
			url += "&renditaLire=" + renditaLire;
		if (partita != null && !partita.isEmpty() && !partita.equals("")) 
			url += "&partita=" + partita;
		if (subalterno != null && !subalterno.isEmpty() && !subalterno.equals("")) 
			url += "&subalterno=" + subalterno;
		if (renditaEuro != null && !renditaEuro.isEmpty() && !renditaEuro.equals("")) 
			url += "&renditaEuro=" + renditaEuro;
		url += "&soppresso=" + soppresso;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	public String localizzaUIU(Long foglio, Long numero) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_IMMOBILI_UIU_LOCALIZZA)+"?&foglio=" + foglio + "&numero=" + numero;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO ricercaParticellePT(String comune, String sezione, String foglio, String numero,
			String subalterno, String partita, String redditoDominicaleEuro, String redditoDominicaleLire,
			String redditoAgrarioEuro, String redditoAgrarioLire, String superficie, boolean soppresso, Integer page,
			Integer size, Integer draw) throws Exception {
		List<ParticelleTerreniDTO> lsSoggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT)+"?";
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (sezione != null && !sezione.isEmpty() && !sezione.equals("")) 
			url += "&sezione=" + sezione;
		if (foglio != null && !foglio.isEmpty() && !foglio.equals("")) 
			url += "&foglio=" + foglio;
		if (numero != null && !numero.isEmpty() && !numero.equals("")) 
			url += "&numero=" + numero;
		if (subalterno != null && !subalterno.isEmpty() && !subalterno.equals("")) 
			url += "&subalterno=" + subalterno;
		if (partita != null && !partita.isEmpty() && !partita.equals("")) 
			url += "&catepartitagoria=" + partita;
		if (redditoDominicaleEuro != null) 
			url += "&redditoDominicaleEuro=" + redditoDominicaleEuro;
		if (redditoDominicaleLire != null) 
			url += "&redditoDominicaleLire=" + redditoDominicaleLire;
		if (redditoAgrarioEuro != null) 
			url += "&redditoAgrarioEuro=" + redditoAgrarioEuro;
		if (redditoAgrarioLire != null) 
			url += "&redditoAgrarioLire=" + redditoAgrarioLire;
		if (superficie != null) 
			url += "&superficie=" + superficie;
		url += "&soppresso=" + soppresso;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsSoggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticelleTerreniDTO>>() {});
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsSoggetti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<IdentificativiUiuDTO> dettagliParticelleUIUIdentificativi(Integer idImmobile) throws Exception {
		List<IdentificativiUiuDTO> soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT_IDENTIFICATIVI)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<IdentificativiUiuDTO>>() {});		
		}
		return soggetti;
	}

	@Override
	public String localizzaPT(Long foglio, Long numero) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT_LOCALIZZA)+"?&foglio=" + foglio + "&numero=" + numero;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DettaglioPtMultiploDTO dettagliParticellePTMultiplo(Integer idImmobile) throws Exception {
		DettaglioPtMultiploDTO soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT_DETTAGLIO)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<DettaglioPtMultiploDTO>() {});		
		}
		return soggetti;
	}

	@Override
	public String exportParticellePT(String comune, String sezione, String foglio, String numero, String subalterno,
			String partita, String redditoDominicaleEuro, String redditoDominicaleLire, String redditoAgrarioEuro,
			String redditoAgrarioLire, String superficie, boolean soppresso) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_PARTICELLE_PT_EXPORT)+"?";
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (sezione != null && !sezione.isEmpty() && !sezione.equals("")) 
			url += "&sezione=" + sezione;
		if (foglio != null && !foglio.isEmpty() && !foglio.equals("")) 
			url += "&foglio=" + foglio;
		if (numero != null && !numero.isEmpty() && !numero.equals("")) 
			url += "&numero=" + numero;
		if (subalterno != null && !subalterno.isEmpty() && !subalterno.equals("")) 
			url += "&subalterno=" + subalterno;
		if (partita != null && !partita.isEmpty() && !partita.equals("")) 
			url += "&catepartitagoria=" + partita;
		if (redditoDominicaleEuro != null) 
			url += "&redditoDominicaleEuro=" + redditoDominicaleEuro;
		if (redditoDominicaleLire != null) 
			url += "&redditoDominicaleLire=" + redditoDominicaleLire;
		if (redditoAgrarioEuro != null) 
			url += "&redditoAgrarioEuro=" + redditoAgrarioEuro;
		if (redditoAgrarioLire != null) 
			url += "&redditoAgrarioLire=" + redditoAgrarioLire;
		if (superficie != null) 
			url += "&superficie=" + superficie;
		url += "&soppresso=" + soppresso;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO ricercaIntestazioniPersoneFisiche(String nome, String cognome, String codiceFiscale,
			boolean checkboxUiuPf, boolean checkboxPtPf, Integer page, Integer size, Integer draw) throws Exception {
		List<RicercaIntestazioniDTO> lsSoggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_INTESTAZIONI_PF)+"?";
		if (nome != null && !nome.isEmpty() && !nome.equals("")) 
			url += "&nome=" + nome;
		if (cognome != null && !cognome.isEmpty() && !cognome.equals("")) 
			url += "&cognome=" + cognome;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		url += "&checkboxUiuPf=" + checkboxUiuPf;
		url += "&checkboxPtPf=" + checkboxPtPf;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsSoggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RicercaIntestazioniDTO>>() {});
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsSoggetti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public ParticelleTerreniDTO informazioniParticellaByIdImmobile(Integer idImmobile) throws Exception {
		ParticelleTerreniDTO soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_INTESTAZIONI_PF_INFO_PT)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<ParticelleTerreniDTO>() {});		
		}
		return soggetti;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public UnitaImmobiliareDTO informazioniUnitaImmobiliareByIdImmobile(Integer idImmobile) throws Exception {
		UnitaImmobiliareDTO soggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_INTESTAZIONI_PF_INFO_UIU)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			soggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<UnitaImmobiliareDTO>() {});		
		}
		return soggetti;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportIntestazioniPF(String nome, String cognome, String codiceFiscale, boolean checkboxUiuPf,
			boolean checkboxPtPf) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_INTESTAZIONI_PF_EXPORT)+"?";
		if (nome != null && !nome.isEmpty() && !nome.equals("")) 
			url += "&nome=" + nome;
		if (cognome != null && !cognome.isEmpty() && !cognome.equals("")) 
			url += "&cognome=" + cognome;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		url += "&checkboxUiuPf=" + checkboxUiuPf;
		url += "&checkboxPtPf=" + checkboxPtPf;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO ricercaIntestazioniSoggettiGiuridici(String denominazione, String provincia, String comune,
			String codiceFiscale, boolean checkboxUiuSg, boolean checkboxPtSg, Integer page, Integer size, Integer draw)
			throws Exception {
		List<RicercaIntestazioniDTO> lsSoggetti = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_INTESTAZIONI_SG)+"?";
		if (denominazione != null && !denominazione.isEmpty() && !denominazione.equals("")) 
			url += "&denominazione=" + denominazione;
		if (provincia != null && !provincia.isEmpty() && !provincia.equals("")) 
			url += "&provincia=" + provincia;
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		url += "&checkboxUiuSg=" + checkboxUiuSg;
		url += "&checkboxPtSg=" + checkboxPtSg;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsSoggetti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<RicercaIntestazioniDTO>>() {});
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsSoggetti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportIntestazioniSG(String denominazione, String provincia, String comune, String codiceFiscale,
			boolean checkboxUiuSg, boolean checkboxPtSg) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_INTESTAZIONI_SG_EXPORT)+"?";
		if (denominazione != null && !denominazione.isEmpty() && !denominazione.equals("")) 
			url += "&denominazione=" + denominazione;
		if (provincia != null && !provincia.isEmpty() && !provincia.equals("")) 
			url += "&provincia=" + provincia;
		if (comune != null && !comune.isEmpty() && !comune.equals("")) 
			url += "&comune=" + comune;
		if (codiceFiscale != null && !codiceFiscale.isEmpty() && !codiceFiscale.equals("")) 
			url += "&codiceFiscale=" + codiceFiscale;
		url += "&checkboxUiuSg=" + checkboxUiuSg;
		url += "&checkboxPtSg=" + checkboxPtSg;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDirittoDTO> ricercaListaIntestatariTranneCorrenteConDiritto(Long idImmobile, Long idSoggetto) throws Exception {
		List<ListaIntestatariDirittoDTO> listaTitolari = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_LISTA_INTESTATARI_DIRITTO)+"?idImmobile="+idImmobile+"&idSoggetto="+idSoggetto;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			listaTitolari = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ListaIntestatariDirittoDTO>>() {});		
		}
		return listaTitolari;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String getDataUltimoAggiornamento() throws Exception {
		String dataAggiornamento = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_RICERCHE_DATA_AGGIORNAMENTO);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			dataAggiornamento = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return dataAggiornamento;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportVisuraCatastaleFabbricati(Long idImmobile) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_VISURA_CATASTALE_FABBRICATI_ATTUALE) + "?idImmobile=" + idImmobile;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportVisuraCatastaleTerreni(Long idImmobile) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_VISURA_CATASTALE_TERRENI_ATTUALE) + "?idImmobile=" + idImmobile;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportVisuraCatastaleStoricaTerreni(Long idImmobile) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_VISURA_CATASTALE_TERRENI_STORICA) + "?idImmobile=" + idImmobile;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportVisuraCatastaleStoricaFabbricati(Long idImmobile) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_VISURA_CATASTALE_FABBRICATI_STORICA) + "?idImmobile=" + idImmobile;
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

}
