package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.GeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryGeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.RequestReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.GeocodingReverseGeocodingConverter;

@Service
public class GeocodingReverseGeocodingServiceImpl implements GeocodingReverseGeocodingService{

	private static final Logger logger = LoggerFactory.getLogger(GeocodingReverseGeocodingServiceImpl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private GeocodingReverseGeocodingConverter geocodingConverter;
	
	@Autowired
	private JpaRepositoryGeocodingReverseGeocoding repositoryGeocoding;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<GeocodingReverseGeocodingDTO> geocoding(String denominazione) throws ToponomasticaServiceException {
		
		String idLog = "geocoding";
		logger.info(START, idLog);
		
		try {
			
			List<GeocodingReverseGeocoding> listaToponimi = null;
			
			if(StringUtils.hasText(denominazione)) {
				
				denominazione = denominazione.replaceAll(",", "");
				String[] parametri = denominazione.split(" ");
									
				listaToponimi = repositoryGeocoding.findAll(RepositoryUtils.buildGeocoding(parametri));
				
				logger.debug(DEBUG_INFO_END, idLog, listaToponimi.size());
			}
				
			return geocodingConverter.toDTO(listaToponimi);
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i toponimi stradali");
			
		} finally {
			logger.info(END, idLog);
			
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<GeocodingReverseGeocodingDTO> reverseGeocoding(RequestReverseGeocodingDTO reverseGeocoding) throws ToponomasticaServiceException {
		
		String idLog = "reverseGeocoding";
		logger.info(START, idLog);
		
		try {
			List<GeocodingReverseGeocoding> listaToponimi = null;
			
			if(reverseGeocoding != null && reverseGeocoding.getLat()!=null && reverseGeocoding.getLon()!=null) {
				
				if ( reverseGeocoding.getDistance()!=null ) {
					
					listaToponimi = repositoryGeocoding.rGeocoding(reverseGeocoding.getLat(), reverseGeocoding.getLon(), Integer.valueOf(reverseGeocoding.getDistance()) );
				} else if ( reverseGeocoding.getGradi()!=null ) {
					
					listaToponimi = repositoryGeocoding.reverseGeocoding(reverseGeocoding.getLat(), reverseGeocoding.getLon(), reverseGeocoding.getGradi() );
				} else {
					
					listaToponimi = repositoryGeocoding.reverseGeocoding(reverseGeocoding.getLat(), reverseGeocoding.getLon(), new Double(0.001));
				}
				
				logger.debug(DEBUG_INFO_END, idLog, listaToponimi.size());
			} else {
				
				throw new ToponomasticaServiceException("Non è stato possibile recuperare i toponimi stradali");
			}
			
			return geocodingConverter.toDTO(listaToponimi);
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i toponimi stradali");
			
		} finally {
			logger.info(END, idLog);
			
		}
		
	}
	
}
