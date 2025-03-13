package it.eng.tz.urbamid.prg.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.prg.persistence.dao.CartografiaVarianteDao;
import it.eng.tz.urbamid.prg.persistence.model.CatalogoGruppo;
import it.eng.tz.urbamid.prg.persistence.model.CatalogoLayer;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryCatalogoGruppo;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryCatalogoLayer;
import it.eng.tz.urbamid.prg.web.dto.AggiuntaLayerDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteLayerDTO;
import it.eng.tz.urbamid.prg.web.dto.CatalogoGruppoDTO;
import it.eng.tz.urbamid.prg.web.dto.converter.CatalogoGruppoConverter;

@Service
public class StratiInformativiServiceImpl implements StratiInformativiService {

	private static final Logger logger = LoggerFactory.getLogger(StratiInformativiServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	@Value("${urbamid.rest.geoserver.endpoint.url}")
	protected String GEOSERVER_ENDPOINT;
	public final String METHOD_GET 				= "GET";
	public final String METHOD_POST				= "POST";
	public final String FEATURES				= "features";
	public final String PROPERTIES				= "properties";
	public final String GEOMETRYNAME			= "geometry_name";
	public final String DEFAULT_GEOMETRYNAME	= "the_geom";
	public final String PROPERTY				= "LEGENDA";

	@Autowired
	private JpaRepositoryCatalogoGruppo catalogoGruppoRepository;
	
	@Autowired
	private CartografiaVarianteDao cartografiaDao;

	@Autowired
	private JpaRepositoryCatalogoLayer catalogoLayerRepository;

	@Autowired
	private CatalogoGruppoConverter catalogoGruppoConverter;

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public String salvaNuovoGruppo(String nomeGruppo) throws Exception {
		String result = null;
		try {
			CatalogoGruppo gruppo = new CatalogoGruppo();
			gruppo.setNomeGruppo(nomeGruppo);
			catalogoGruppoRepository.save(gruppo);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio del gruppo layer {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<CatalogoGruppoDTO> reperimentoCatalogoVariante() throws Exception {
		String idLog = "reperimentoCatalogoVariante";
		try{
			logger.info(START, idLog);

			List<CatalogoGruppo> resultModel = catalogoGruppoRepository.getAll();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return catalogoGruppoConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public AggiuntaLayerDTO salvaLayer(AggiuntaLayerDTO layer) throws Exception {
		AggiuntaLayerDTO result = null;
		try {
			CatalogoLayer cLayer = new CatalogoLayer();
			Optional<CatalogoGruppo> gruppo = catalogoGruppoRepository.findById(layer.getIdGruppo());
			cLayer.setCatalogoGruppo(gruppo.get());
			cLayer.setIdLayer(layer.getLayerName());
			cLayer.setNomeLayer(layer.getLayerTitle());
			cLayer.setSorgente(layer.getSorgente());
			cLayer.setHrefDetail(layer.getHrefDetail());
			cLayer.setNomeColonna(layer.getNomeColonnaLayer());
			catalogoLayerRepository.save(cLayer);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio del gruppo layer {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public String cancellaLayer(Long idLayer) throws Exception {
		try {
			catalogoLayerRepository.deleteById(idLayer);
		} catch (Exception e) {
			logger.error("Errore nella cancellazione del layer {}", e.getMessage(), e);
			throw (e);
		}
		return null;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public String cancellaGruppo(Long idGruppo) throws Exception {
		try {
			catalogoGruppoRepository.deleteById(idGruppo);
		} catch (Exception e) {
			logger.error("Errore nella cancellazione del gruppo {}", e.getMessage(), e);
			throw (e);
		}
		return null;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public List<CartografiaVarianteLayerDTO> varianteByNomeLayer(String nomeLayer) {
		String idLog = "varianteByNomeLayer";
		List<CartografiaVarianteLayerDTO> result = null;
		try {
			
			logger.info(START, idLog);
			result = cartografiaDao.varianteByNomeLayer(nomeLayer);
			
			logger.debug(DEBUG_INFO_END, idLog, result.size());
		} catch (Exception e) {
			logger.error("Errore" + idLog, e.getMessage(), e);
			throw(e);
		}
		
		return result;
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<String> reperimentoColonneLayer(String nomeLayer) throws Exception {
		String idLog = "reperimentoCatalogoVariante";
		try{
			logger.info(START, idLog);
			List<String> result = new ArrayList<>();

			String hrefFieldGeomGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=describeFeatureType&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json&count=1";
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefFieldGeomGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray("featureTypes");
						if (arr!=null) {
							for (int i = 0; i < arr.length(); i++) {
								JSONArray arr2 = arr.getJSONObject(i).getJSONArray("properties");
								for (int j = 0; j < arr2.length(); j++) {
									result.add(arr2.getJSONObject(j).getString("name"));
								}
							}
						}
						
					}
				}
				return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	/**
	 * Utilizzato per richiamare la API rest WFS di geoserver
	 * @param method
	 * @param urlEncoded
	 * @param postData
	 * @param contentType
	 * @param accept
	 * @return
	 * @throws IOException
	 */
	public String restGeoserverCall(String method, String urlEncoded, String postData, String contentType, String accept) throws IOException {
		
		boolean doOut = !METHOD_GET.equals(method) && postData != null;

		URL url = new URL(urlEncoded);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}
		connection.setRequestMethod(method.toString());

		connection.connect();
	    StringBuffer chaine = new StringBuffer("");
		InputStream inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = rd.readLine()) != null) {
            chaine.append(line);
        }
		return chaine.toString();
	}

}
