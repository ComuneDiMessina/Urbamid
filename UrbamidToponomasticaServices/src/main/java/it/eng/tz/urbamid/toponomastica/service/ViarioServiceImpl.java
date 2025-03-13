package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.dao.AccessoDAO;
import it.eng.tz.urbamid.toponomastica.persistence.dao.ToponimoStradaleDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.GeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.persistence.model.NumCivico;
import it.eng.tz.urbamid.toponomastica.persistence.model.Toponimo;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryGeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.NumCivicoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.GeocodingReverseGeocodingConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.NumCivicoConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ToponimoConverter;

@Service
public class ViarioServiceImpl implements ViarioService {

	private static final Logger logger = LoggerFactory.getLogger(ToponimoStradaleServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	

	@Autowired
	private ToponimoConverter toponimoConverter; 
	
	@Autowired
	private GeocodingReverseGeocodingConverter geocodingConverter;
	
	@Autowired
	private JpaRepositoryGeocodingReverseGeocoding repositoryGeocoding;
	
	@Autowired
	private NumCivicoConverter numCivicoConverter;
	
	@Autowired
	private ToponimoStradaleDAO toponimoStradaleDao;
	
	@Autowired
	private AccessoDAO accessoDao;
		
	
	
	@Override
	public List<ToponimoDto> findStreetByName(String streetName) throws ToponomasticaServiceException {
		String idLog = "findStreetByName";
		
		try {
			logger.info(START, idLog);
			
			List<Toponimo> toponimi = toponimoStradaleDao.findStreetByName(streetName);
			List<ToponimoDto> dtos = this.toponimoConverter.toDto(toponimi);
		
			logger.debug(DEBUG_INFO_END, idLog, dtos.size());
			
			return dtos;
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			
			throw new ToponomasticaServiceException("Non è stato possibile recuperare il toponimo stradale");
		} finally {
			
			logger.info(END);
		}
		
	}



	@Override
	public List<NumCivicoDto> findNumberByStreet(Long codStrada) throws ToponomasticaServiceException {
		String idLog = "findNumberByStreet";
		
		try {
			logger.info(START, idLog);
			
			List<NumCivico> numCivici = accessoDao.getNumberbyStreet(codStrada);
			List<NumCivicoDto> dtos = this.numCivicoConverter.toDto(numCivici);
			
			logger.debug(DEBUG_INFO_END, idLog, dtos.size());
			
			return dtos;
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i civici stradali");
		}finally {
			
			logger.info(END);
		}
		
	}



	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<GeocodingReverseGeocodingDTO> geocodingToponimo(String denominazione) throws ToponomasticaServiceException {
		String idLog = "geocodingToponimo";
		logger.info(START, idLog);
		
		List<GeocodingReverseGeocoding> listaToponimi = null;
		try {
			
			if(StringUtils.hasText(denominazione)) {
			
				denominazione = denominazione.replaceAll(",", "");
				String[] parametri = denominazione.split(" ");
									
				listaToponimi = repositoryGeocoding.findAll(RepositoryUtils.buildGeocoding(parametri));
				
				logger.debug(DEBUG_INFO_END, idLog, listaToponimi.size());
				
			}
				
			return geocodingConverter.toDTO(listaToponimi);
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i toponimo stradali");
		}
		
	}
	
}