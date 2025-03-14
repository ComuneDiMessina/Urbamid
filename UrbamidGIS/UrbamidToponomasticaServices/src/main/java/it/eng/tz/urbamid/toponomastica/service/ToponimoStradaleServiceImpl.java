package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.AccessoDAO;
import it.eng.tz.urbamid.toponomastica.persistence.dao.GeocodingReverseGeocodingDAO;
import it.eng.tz.urbamid.toponomastica.persistence.dao.ToponimoStradaleDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.NumCivico;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryToponimoStradale;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ToponimoStradaleConverter;

@Service
public class ToponimoStradaleServiceImpl implements ToponimoStradaleService {

	private static final Logger logger = LoggerFactory.getLogger(ToponimoStradaleServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaRepositoryToponimoStradale repository;
	
	@Autowired
	private ToponimoStradaleDAO toponimoStradaleDAO;
	
	@Autowired
	private AccessoDAO accessoDAO;
	
	@Autowired
	private GeocodingReverseGeocodingDAO geocodingDAO;

	@Autowired
	private ToponimoStradaleConverter converter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<ToponimoStradaleDTO> getToponimoByFilter(ToponimoFilter filter) throws ToponomasticaServiceException {
		String idLog = "getToponimoByFilter";
		try {

			logger.info(START, idLog);
			
			Page<ToponimoStradale> pagedResult = repository.findAll(RepositoryUtils.buildToponimoPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());
			return converter.toDTO(new PagedResult<>(pagedResult));
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare il toponimo stradale");
		} finally {
			
			logger.info(END, idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public ToponimoStradaleDTO insertOrUpdate(ToponimoStradaleDTO toponimoDto) throws ToponomasticaServiceException {

		String idLog = "insertOrUpdate";
		try {
			if(toponimoDto.getId() != null) {
				
				List<ToponimoDto> toponimo = toponimoStradaleDAO.findById(toponimoDto.getId());
				if (toponimo!=null && !toponimo.isEmpty()) {
					
					logger.debug(START, " UPDATE "  + idLog);
					
					List<NumCivico> numeriCivici = accessoDAO.getNumberbyStreet(toponimoDto.getId());
		
					ToponimoStradale model = toponimoStradaleDAO.update( converter.toModel(toponimoDto) );
					
					if(numeriCivici.size() > 0)
						geocodingDAO.insertGeocodingToponimo(model.getId(), true);
					else
						geocodingDAO.insertGeocodingToponimo(model.getId(), false);
						
					return converter.toDTO(model);
				} else {

					logger.debug(START, " INSERT "  + idLog);
					ToponimoStradale model = toponimoStradaleDAO.insert( converter.toModel(toponimoDto) );
						
					geocodingDAO.insertGeocodingToponimo(model.getId(), false);
					
					return converter.toDTO(model);
				}
			} else {
				
				logger.debug(START, " INSERT "  + idLog);
				ToponimoStradale model = toponimoStradaleDAO.insert( converter.toModel(toponimoDto) );
					
				geocodingDAO.insertGeocodingToponimo(model.getId(), false);
				
				return converter.toDTO(model);
			}

		} catch (Exception e) {
			logger.debug(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException(e);
		} finally {
			logger.info(END, idLog);
		}
	}	
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public int pubblicaToponimoStradale(Long id) throws ToponomasticaServiceException {

		String idLog = "pubblicaToponimoStradale";
		try {
			
			logger.debug(START, idLog);
			return toponimoStradaleDAO.pubblicaToponimoStradale(id);
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile pubblicare il toponimo stradale con id "+id);
		} 
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void deleteToponimoById(Long id) throws ToponomasticaServiceException {

		String idLog = "deleteToponimoById";
		try {
			logger.debug(START, idLog);
			geocodingDAO.deleteGeocodingToponimo(id);
			toponimoStradaleDAO.delete(id);
		} catch (Exception e) {
			logger.error(ERROR, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare il toponimo stradale con id "+id);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void archiviaToponimoById(Long id) throws ToponomasticaServiceException {

		String idLog = "archiviaToponimoById";
		try {
			logger.debug(START, idLog);
			toponimoStradaleDAO.archivia(id);
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile archiviare il toponimo stradale con id "+id);
		} finally {
			logger.info(END, idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public List<ToponimoDugDTO> ricercaToponimoByDug(String toponimo) throws ToponomasticaServiceException {
		
		String idLog = "ricercaToponimoByDug";
		List<ToponimoDugDTO> result; 
		try {
			
			logger.debug(ERROR, idLog);
			result = toponimoStradaleDAO.ricerca(toponimo);
			return result;
		} catch (Exception e) {
			logger.error(ERROR, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile ricercare il toponimo stradale con nome "+toponimo);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public List<ToponimoStradaleDTO> ricercaToponimoByIdPadre(Long idPadre)  throws Exception {
		String idLog = "ricercaToponimoByIdPadre";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: idPadre: " +  idPadre);
			
			List<ToponimoStradale> resultModel = repository.findByIdPadre(idPadre);		
			List<ToponimoStradaleDTO> result =  converter.toDTO(resultModel) ;
			
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public boolean isFigliPubblicati(Long idPadre) throws ToponomasticaServiceException {
		
		String idLog = "isFigliPubblicati";
		
		boolean result = true;
		try {
			
			logger.info(START, idLog);
						
			List<ToponimoStradale> resultModel = repository.findByIdPadre(idPadre);
			
			for (int i = 0; i < resultModel.size(); i++) {
				if(!resultModel.get(i).getStato().equalsIgnoreCase("PUBBLICATO")) {
					 result = false;
					 break;
				 }
			}
			
			return result;
			
		} catch (Exception e) {
			
			logger.error(ERROR, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile ricercare lo stato dei figli con idPadre: "+ idPadre);
			
		} finally {
			
			logger.info(END, idLog);
			
		} 
		
	}
}
