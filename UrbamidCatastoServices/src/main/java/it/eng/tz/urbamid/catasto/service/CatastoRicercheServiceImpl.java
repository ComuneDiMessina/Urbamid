package it.eng.tz.urbamid.catasto.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.catasto.dao.CatastoDao;
import it.eng.tz.urbamid.catasto.persistence.model.FoglioGeom;
import it.eng.tz.urbamid.catasto.persistence.model.GeometriaLayer;
import it.eng.tz.urbamid.catasto.persistence.model.Particella;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCompleteGeom;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaGeom;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoIntestatario;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoin;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryFoglioGeom;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryParticellaGeom;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositorySoggetto;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryUnitaImmobiliare;
import it.eng.tz.urbamid.catasto.web.dto.FoglioGeomDTO;
import it.eng.tz.urbamid.catasto.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomCompleteDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.catasto.web.dto.converter.FoglioGeomConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.GeometriaLayerConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.IntestatariConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ParticellaGeomCompleteConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ParticellaGeomConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.RicerchePTConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.RicercheUIConverter;


@Service
public class CatastoRicercheServiceImpl  extends AbstractCatastoService implements CatastoRicercheService{

	private static final Logger logger = LoggerFactory.getLogger(CatastoRicercheServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO = "{} >>> Parametri passati in input sono: numFoglio: {} mappale: {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String DELIMITER = "MULTIPOLYGON";

	@Autowired
	private JpaRepositoryParticella particellaDao;

	@Autowired
	private JpaRepositoryUnitaImmobiliare unitaImmDao;

	@Autowired
	private JpaRepositorySoggetto soggettoDao;

	@Autowired
	private RicerchePTConverter ptConverter;

	@Autowired
	private RicercheUIConverter uiConverter;

	@Autowired
	private IntestatariConverter intestatariConverter;
	
	@Autowired
	private ParticellaGeomConverter particellaGeomConverter;
	
	@Autowired
	private ParticellaGeomCompleteConverter particellaGeomCompleteConverter;
	
	@Autowired
	private GeometriaLayerConverter geometriaLayerConverter;

	@Autowired
	private JpaRepositoryParticellaGeom particellaGeomDao;
	
	@Autowired
	private CatastoDao catastoDao;
	
	@Autowired
	private JpaRepositoryFoglioGeom foglioGeomDao;
	
	@Autowired
	private FoglioGeomConverter foglioGeomConverter;
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("init for {}", particellaGeomDao);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<ParticellaGeomDTO> findParticellaWithFoglioAndMappale(String numFoglio, String mappale, String campoOrd, String tipoOrd, Integer page, Integer size) throws Exception {
		String idLog = "findParticellaWithFoglioAndMappale";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: numFoglio: " +  numFoglio + " mappale:" + mappale 
					+ " ordinamento:" + campoOrd + " tipoOrd:" + tipoOrd);
			
			if ( numFoglio!=null	&& !(numFoglio.equals("")) ) {
				// converto il campo numFoglio per essere formattato
				int foglio = Integer.parseInt(numFoglio);
			
				//formatto correttamente il campo mappale			
				numFoglio = getFormattedNumber(foglio, "04");
			}
			
			// costruisco la richiesta 
			Pageable pageable = PageRequest.of(page, size, this.getSort(campoOrd, tipoOrd));
			
			List<ParticellaGeomDTO> result = new ArrayList<ParticellaGeomDTO>();
			 
			if (numFoglio!=null	&& !(numFoglio.equals("")) && mappale!=null && !(mappale.equals(""))) {
				Page<ParticellaGeom> resultModel = particellaGeomDao.findByFoglioAndMappale(numFoglio, mappale, pageable);
				result =  particellaGeomConverter.toDto(resultModel.getContent());
			} else if ( numFoglio!=null	&& !(numFoglio.equals("")) ) {
				Page<ParticellaGeom> resultModel = particellaGeomDao.findByFoglio(numFoglio, pageable);
				result =  particellaGeomConverter.toDto(resultModel.getContent());
			} else if ( mappale!=null && !(mappale.equals("")) ) {
				Page<ParticellaGeom> resultModel = particellaGeomDao.findByMappale(mappale, pageable);
				result =  particellaGeomConverter.toDto(resultModel.getContent());
			}
				
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<ParticellaGeomDTO> findParticellaWithGeom(String wktGeom, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "findParticellaWithGeom";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: wktGeom: " +  wktGeom + " ordinamento:" + campoOrd + " tipoOrd:" + tipoOrd);
			
			// costruisco la richiesta 
			Pageable pageable = PageRequest.of(0, 100, this.getSort(campoOrd, tipoOrd));
			Page<ParticellaGeom> resultModel = particellaGeomDao.findByGeom(wktGeom, pageable);		
			List<ParticellaGeomDTO> result =  particellaGeomConverter.toDto(resultModel.getContent()) ;
			
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<ParticellaGeomDTO> findParticellaByWkt(String wktGeom) throws Exception {
		String idLog = "findParticellaByWkt";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: wktGeom: " +  wktGeom);
			
			List<ParticellaGeom> resultModel = particellaGeomDao.findByWKT(wktGeom);		
			List<ParticellaGeomDTO> result =  particellaGeomConverter.toDto(resultModel) ;
			
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<ParticellaGeomCompleteDTO> findParticellaCompleteByWkt(String wktGeom) throws Exception {
		String idLog = "findParticellaCompleteByWkt";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: wktGeom: " +  wktGeom);
			
			List<ParticellaCompleteGeom> resultModel = catastoDao.findCompleteByWKT(wktGeom);		
			for (ParticellaCompleteGeom result : resultModel) {
				result.setArea( (result.getArea()).setScale(2, BigDecimal.ROUND_HALF_UP));
				result.setIntersectArea( (result.getIntersectArea()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			List<ParticellaGeomCompleteDTO> result =  particellaGeomCompleteConverter.toDto(resultModel) ;
			
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	/**TODO: PUNTARE AL DAO PER QUERY DINAMICA**/
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<GeometriaLayerDTO> findParticellaWithLayer(String layerName) throws Exception {
		String idLog = "findParticellaWithLayer";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: layerName: " +  layerName);
			
			// costruisco la richiesta 
			List<GeometriaLayer> resultModel = catastoDao.findParticellaByLayer(layerName);		
			List<GeometriaLayerDTO> result =  geometriaLayerConverter.toDto(resultModel) ;
			
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<FoglioGeomDTO> findFoglio(String numFoglio, String campoOrd, String tipoOrd, Integer page, Integer size) throws Exception {
		String idLog = "findFoglio";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: numFoglio: " +  numFoglio 
					+ " ordinamento:" + campoOrd + " tipoOrd:" + tipoOrd);
			
			// converto il campo numFoglio per essere formattato
			int foglio = Integer.parseInt(numFoglio);
			
			//formatto correttamente il campo mappale			
			numFoglio = getFormattedNumber(foglio, "04");
					
			// costruisco la richiesta per il dao
			Pageable pageable = PageRequest.of(page, size, this.getSort(campoOrd, tipoOrd));
						
			Page<FoglioGeom> resultModel = foglioGeomDao.findByNumFoglio(numFoglio, pageable);		
			List<FoglioGeomDTO> result =  foglioGeomConverter.toDto(resultModel.getContent());	
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<FoglioGeomDTO> findFoglioWithGeom(String wktGeom, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "findFoglioWithGeom";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: wktGeom: " +  wktGeom + " ordinamento:" + campoOrd + " tipoOrd:" + tipoOrd);
			
			// costruisco la richiesta 
			Pageable pageable = PageRequest.of(0, 100, this.getSort(campoOrd, tipoOrd));
			
			Page<FoglioGeom> resultModel = foglioGeomDao.findByGeom(wktGeom, pageable);		
			List<FoglioGeomDTO> result =  foglioGeomConverter.toDto(resultModel.getContent());	
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ParticelleTerreniDTO> ricercaSuParticellePT(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "ricercaSuParticellePT";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));
			
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<Particella> resultModel = new ArrayList<Particella>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				resultModel.addAll( particellaDao.ricercaFMSuParticellePT(comune,  foglio, entry.getValue()) );	
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return ptConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<UnitaImmobiliareDTO> ricercaSuParticelleUI(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "ricercaSuParticelleUI";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));
			
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<UnitaImmobiliareJoin> resultModel = new ArrayList<UnitaImmobiliareJoin>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				resultModel.addAll( unitaImmDao.ricercaFMSuParticelleUIJoin(comune,  foglio, entry.getValue()) );	
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return uiConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDTO> ricercaListaIntestatari(Integer idImmobile, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "ricercaListaIntestatari";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);
			
			List<SoggettoIntestatario> resultModel = soggettoDao.ricercaListaIntestatari(idImmobile);
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return intestatariConverter.toDtoCustom(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDTO> ricercaPersoneFisiche(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "ricercaPersoneFisiche";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));
			
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>();
			HashMap<String,List<String>> hmFoNormaMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 		= item.getCodice_com();
				String mappale 		= normalizzaNumeroMappale(item.getMappale());
				String foglio 		= item.getFoglio();
				
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
				
				String foglioNorma 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
				if (hmFoNormaMa.containsKey(comune+"|"+foglioNorma))
					hmFoNormaMa.get(comune+"|"+foglioNorma).add(mappale);
				else 
					hmFoNormaMa.put(comune+"|"+foglioNorma, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<SoggettoIntestatario> resultModelTmp = new ArrayList<SoggettoIntestatario>(); 
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				
				resultModelTmp.addAll(soggettoDao.ricercaFMPersoneFisicheUI(comune,  foglio, entry.getValue(), "P"));
			}
			for (Map.Entry<String,List<String>> entry : hmFoNormaMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				
				resultModelTmp.addAll(soggettoDao.ricercaFMPersoneFisichePT(comune,  foglio, entry.getValue(), "P"));
			}
			
			List<SoggettoIntestatario> resultModel = new ArrayList<SoggettoIntestatario>();
			HashMap<String,SoggettoIntestatario> hmSoggettoIntestatarioList = new HashMap<String,SoggettoIntestatario>();
			for (SoggettoIntestatario item : resultModelTmp) {
				if (!hmSoggettoIntestatarioList.containsKey(item.getCodice_fiscale())) {
					resultModel.add(item);
					hmSoggettoIntestatarioList.put(item.getCodice_fiscale(), item);
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return intestatariConverter.toDtoCustom(resultModel);	
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDTO> ricercaSoggettiGiuridici(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "ricercaSoggettiGiuridici";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));
			
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			HashMap<String,List<String>> hmFoNormaMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
				
				String foglioNorma 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
				if (hmFoNormaMa.containsKey(comune+"|"+foglioNorma))
					hmFoNormaMa.get(comune+"|"+foglioNorma).add(mappale);
				else 
					hmFoNormaMa.put(comune+"|"+foglioNorma, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<SoggettoIntestatario> resultModelTmp = new ArrayList<SoggettoIntestatario>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				resultModelTmp.addAll( soggettoDao.ricercaFMPersoneGiuridicheUI(comune,  foglio, entry.getValue(), "G") );	
			}
			
			for (Map.Entry<String,List<String>> entry : hmFoNormaMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				resultModelTmp.addAll( soggettoDao.ricercaFMPersoneFisichePT(comune,  foglio, entry.getValue(), "G") );	
			}
			
			List<SoggettoIntestatario> resultModel = new ArrayList<SoggettoIntestatario>();
			HashMap<String,SoggettoIntestatario> hmSoggettoIntestatarioList = new HashMap<String,SoggettoIntestatario>();
			for (SoggettoIntestatario item : resultModelTmp) {
				if (!hmSoggettoIntestatarioList.containsKey(item.getDenominazione())) {
					resultModel.add(item);
					hmSoggettoIntestatarioList.put(item.getDenominazione(), item);
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return intestatariConverter.toDtoCustom(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ParticellaGeomDTO> ricercaFoglioMappale(String numFoglio, String mappale, String campoOrd, String tipoOrd) throws Exception {
		String idLog = "ricercaFoglioMappale";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: numFoglio: {} mappale {}", numFoglio, mappale);

			List<ParticellaGeom> resultModel;
			
			if (numFoglio.isEmpty() && !mappale.isEmpty()) {
				resultModel = particellaGeomDao.ricercaByMappale(mappale);
			} else if (!numFoglio.isEmpty() && mappale.isEmpty()) {
				resultModel = particellaGeomDao.ricercaByFoglio(numFoglio);
			} else {
				resultModel = particellaGeomDao.ricercaByFoglioMappale(numFoglio, mappale);
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return particellaGeomConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}
}
