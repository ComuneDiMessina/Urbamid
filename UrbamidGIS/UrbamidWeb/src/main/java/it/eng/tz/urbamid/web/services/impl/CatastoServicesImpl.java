package it.eng.tz.urbamid.web.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.FoglioGeomDTO;
import it.eng.tz.urbamid.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.web.dto.ParticellaGeomCompleteDTO;
import it.eng.tz.urbamid.web.dto.ParticellaGeomDTO;
import it.eng.tz.urbamid.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.web.services.CatastoServices;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class CatastoServicesImpl implements CatastoServices{
	
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@Override
	public List<ParticellaGeomDTO> findParticella(String numFoglio, String mappale) throws Exception {
		List<ParticellaGeomDTO> particella = null;
		Integer page = Integer.valueOf(env.getProperty("urbamid.ricerche.page"));
		Integer size = Integer.valueOf(env.getProperty("urbamid.ricerche.size"));
		
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA)+"?numFoglio="+numFoglio+"&mappale="+mappale+"&page="+page+"&size="+size;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			particella = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticellaGeomDTO>>() {});		
		}
		return particella;
	}
	
	@Override
	public List<ParticellaGeomDTO> findParticellaByGeom(String wktGeom) throws Exception {
		List<ParticellaGeomDTO> particella = new ArrayList<ParticellaGeomDTO>();
		
		List<String> wktList = this.splitWkt(wktGeom);
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_BYGEOM);
		ResponseData response = restService.restPostTable(url, wktList);
		if(response!=null && response.isSuccess()) {
			particella.addAll(  new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticellaGeomDTO>>() {}) );		
		}
		
		return particella;
	}
	
	@Override
	public List<ParticellaGeomDTO> findParticellaByWkt(String wktGeom) throws Exception {
		List<ParticellaGeomDTO> particella = new ArrayList<ParticellaGeomDTO>();
		
		List<String> wktList = this.splitWkt(wktGeom);
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_BYWKT);
		ResponseData response = restService.restPostTable(url, wktList);
		if(response!=null && response.isSuccess()) {
			particella.addAll(  new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticellaGeomDTO>>() {}) );		
		}
		
		return particella;
	}
	
	@Override
	public List<ParticellaGeomCompleteDTO> findParticellaCompleteByWkt(String wktGeom) throws Exception {
		List<ParticellaGeomCompleteDTO> particella = new ArrayList<ParticellaGeomCompleteDTO>();
		
		List<String> wktList = this.splitWkt(wktGeom);
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_COMPLETE_BYWKT);
		ResponseData response = restService.restPostTable(url, wktList);
		if(response!=null && response.isSuccess()) {
			particella.addAll(  new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticellaGeomCompleteDTO>>() {}) );		
		}
		
		return particella;
	}
	
	@Override
	public List<ParticellaGeomDTO> findParticellaByLayer(String layerName) throws Exception {
		List<ParticellaGeomDTO> particella = new ArrayList<ParticellaGeomDTO>();
		
			String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_BYLAYER) + "?layerName="+layerName;	 
			ResponseData response = restService.restPostTable(url, null);
			if(response!=null && response.isSuccess()) {
				particella.addAll(  new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticellaGeomDTO>>() {}) );		
			}
		return particella;
	}
	
	@Override
	public List<FoglioGeomDTO> findFoglio(String numFoglio) throws Exception {
		List<FoglioGeomDTO> foglio = null;
		Integer page = Integer.valueOf(env.getProperty("urbamid.ricerche.page"));
		Integer size = Integer.valueOf(env.getProperty("urbamid.ricerche.size"));
		
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_FOGLIO)+"?numFoglio="+numFoglio+"&page="+page+"&size="+size;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			foglio = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<FoglioGeomDTO>>() {});		
		}
		return foglio;
	}
	
	@Override
	public List<FoglioGeomDTO> findFoglioByGeom(String wktGeom) throws Exception {
		List<FoglioGeomDTO> foglio = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_FOGLIO_BYGEOM)
				+"?wktGeom="+wktGeom;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			foglio = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<FoglioGeomDTO>>() {});		
		}
		return foglio;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ParticelleTerreniDTO> ricercaSuParticellePT(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		List<ParticelleTerreniDTO> particelle = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_PT);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		if(response!=null && response.isSuccess()) {
			particelle = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticelleTerreniDTO>>() {});		
		}
		return particelle;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<UnitaImmobiliareDTO> ricercaSuParticelleUI(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		List<UnitaImmobiliareDTO> unitaImmobiliari = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_UI);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		if(response!=null && response.isSuccess()) {
			unitaImmobiliari = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<UnitaImmobiliareDTO>>() {});		
		}
		return unitaImmobiliari;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDTO> ricercaListaIntestatari(Integer idImmobile) throws Exception {
		List<ListaIntestatariDTO> unitaImmobiliari = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_LI)+"?idImmobile="+idImmobile;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			unitaImmobiliari = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ListaIntestatariDTO>>() {});		
		}
		return unitaImmobiliari;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDTO> ricercaPersoneFisiche(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		List<ListaIntestatariDTO> intestatari = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_PF);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		if(response!=null && response.isSuccess()) {
			intestatari = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ListaIntestatariDTO>>() {});		
		}
		return intestatari;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDTO> ricercaSoggettiGiuridici(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		List<ListaIntestatariDTO> soggettiGiuridici = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_PARTICELLA_SG);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		if(response!=null && response.isSuccess()) {
			soggettiGiuridici = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ListaIntestatariDTO>>() {});		
		}
		return soggettiGiuridici;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ParticellaGeomDTO> ricercaFoglioMappale(String numFoglio, String mappale) throws Exception {
		List<ParticellaGeomDTO> particelle = null;
		if(!mappale.chars().allMatch(Character::isDigit))
			mappale = mappale.toUpperCase();

		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_FOGLIO_MAPPALE)
				+ "?foglio="+numFoglio+"&mappale="+mappale;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			particelle = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ParticellaGeomDTO>>() {});		
		}
		return particelle;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> categorieCatastali() throws Exception {
		List<TipologicaDTO> particelle = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_CODICI_CATEGORIE_CATASTALI);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			particelle = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		return particelle;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> codiciDiritto() throws Exception {
		List<TipologicaDTO> particelle = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_CODICI_CODICI_DIRITTO);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			particelle = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		return particelle;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> codiciQualita() throws Exception {
		List<TipologicaDTO> particelle = null;
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_CODICI_CODICI_QUALITA);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			particelle = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		return particelle;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportXls(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_XLS);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportXlsSoggetti(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_SOGGETTI_XLS);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportXlsTerreni(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_TERRENI_XLS);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportXlsUiu(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_UIU_XLS);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportXlsIntestazioni(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_INTESTAZIONI_XLS);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}
	
	private List<String> splitWkt(String wkt) {
		List<String> result = new ArrayList<String>();
		if (wkt.contains("|")) {
			String[] arr = wkt.split("\\|");
			for ( int i=0; i<arr.length; i++ ){
				result.add( arr[i].replace("|", "") );
			}
		} else 
			result.add(wkt);
		
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportPdf(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_PDF) + "?titolo=" + titolo + "&tipologiaEstrazione=" + tipologiaEstrazione;
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportShape(List<ParticellaCustomDTO> lsParticelle, String titolo) {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_SHAPE) + "?titolo="+titolo;
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
		
//		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_SHAPE);	 
//		ResponseData response = restService.restPostTable(url, lsParticelle);
//		if(response!=null && response.isSuccess()) {
//			System.out.println("Ok il file");
////			particelle = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
//		}
//		return "";
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportFabbricatiPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_PDF_FABBRICATI_NOMINATIVO) + "?titolo=" + titolo + "&tipologiaEstrazione=" + tipologiaEstrazione;
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportTerreniPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_PDF_TERRENI_NOMINATIVO) + "?titolo=" + titolo + "&tipologiaEstrazione=" + tipologiaEstrazione;
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportFabbricatiPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_PDF_FABBRICATI_PARTICELLA) + "?titolo=" + titolo + "&tipologiaEstrazione=" + tipologiaEstrazione;
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String exportTerreniPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) {
		String url = env.getProperty(IConstants.WS_CATASTO_ENDPOINT)+ env.getProperty(IConstants.WS_CATASTO_EXPORT_PDF_TERRENI_PARTICELLA) + "?titolo=" + titolo + "&tipologiaEstrazione=" + tipologiaEstrazione;
		ResponseData response = restService.restPostTable(url, lsParticelle);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getComuni() throws Exception {
		List<TipologicaDTO> comuni = null;
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT)+ env.getProperty(IConstants.WS_TOPONOMASTICA_COMUNI);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			comuni = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		return comuni;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getProvince() throws Exception {
		List<TipologicaDTO> province = null;
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT)+ env.getProperty(IConstants.WS_TOPONOMASTICA_PROVINCE);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			province = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		return province;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getComuniByProvincia(Long idProvincia) throws Exception {
		List<TipologicaDTO> comuni = null;
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT)+ env.getProperty(IConstants.WS_TOPONOMASTICA_COMUNI_BY_PROVINCIA) + "?idProvincia=" + idProvincia;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			comuni = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		return comuni;
	}
}
