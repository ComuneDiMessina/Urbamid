package it.eng.tz.urbamid.toponomastica.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.AccessoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.AccessoDAO;
import it.eng.tz.urbamid.toponomastica.persistence.dao.GeocodingReverseGeocodingDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.Accesso;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryAccesso;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.AccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.AccessoConverter;

@Service
public class AccessoServiceImpl implements AccessoService {

	private static final Logger logger = LoggerFactory.getLogger(AccessoServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";

	@Autowired
	private JpaRepositoryAccesso repository;

	@Autowired
	private AccessoDAO dao;
	
	@Autowired
	private GeocodingReverseGeocodingDAO geocodingDAO;

	@Autowired
	private AccessoConverter converter;

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = true)
	public PagedResult<AccessoDTO> getAccesso(AccessoFilter filter) throws ToponomasticaServiceException {

		String idLog = "getAccesso";

		try {

			logger.info(START, idLog);
			Page<Accesso> pagedResult = repository.findAll(RepositoryUtils.buildAccessoPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());
			return converter.toDTO(new PagedResult<>(pagedResult));
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare gli accessi");
		} finally {
			logger.info(END, idLog);

		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public AccessoDTO insertOrUpdate(AccessoDTO accesso) throws ToponomasticaServiceException {

		String idLog = "insertOrUpdate";

		try {

			if (accesso.getId() != null) {

				logger.debug(START, " UPDATE " + idLog);
				
				Accesso model = dao.update(converter.toModel(accesso));
				geocodingDAO.insertGeocodingAccesso(model.getToponimo().getId(), model.getId());
				
				return converter.toDTO(model);
			} else {

				logger.debug(START, " INSERT " + idLog);
				Accesso model = dao.insert(converter.toModel(accesso));
			
				geocodingDAO.insertGeocodingAccesso(model.getToponimo().getId(), model.getId());
				
				return converter.toDTO(model);
			}

		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire o aggiornare l'accesso");
		} finally {

			logger.info(END, idLog);

		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void deleteAccessoById(Long id) throws ToponomasticaServiceException {

		String idLog = "deleteAccesso";

		try {

			logger.debug(START, idLog);
			geocodingDAO.deleteGeocodingAccesso(id);
			repository.deleteById(id);
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire o aggiornare l'accesso");
		} finally {

			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void deleteByToponimo(Long id, Long toponimo) throws ToponomasticaServiceException {

		String idLog = "deleteByToponimo";
		try {

			logger.debug(START, idLog);
			geocodingDAO.deleteGeocodingAccesso(id);
			dao.deleteByToponimo(id, toponimo);
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile cancellare l'accesso con l'id toponimo: " + toponimo);
		} finally {

			logger.info(END, idLog);
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void deleteByLocalita(Long id, Long localita) throws ToponomasticaServiceException {

		String idLog = "deleteByToponimo";

		try {

			logger.debug(START, idLog);
			geocodingDAO.deleteGeocodingAccesso(id);
			dao.deleteByLocalita(id, localita);
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile cancellare l'accesso con l'id località: " + localita);
		} finally {
			logger.info(END, idLog);

		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = true)
	public Long countAccessiByIdToponimo(Long idToponimo) throws ToponomasticaServiceException {

		String idLog = "countAccessiByToponimo";
		try {

			logger.debug(START, idLog);
			Long result = repository.countByIdToponimo(idToponimo);
			return result;
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile contare gli accessi con l'id toponimo: " + idToponimo);
		} finally {
			
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = true)
	public Long countAccessiByIdLocalita(Long idLocalita) throws ToponomasticaServiceException {

		String idLog = "countAccessiByToponimo";
		try {

			logger.debug(START, idLog);
			Long result = repository.countAccessiByIdLocalita(idLocalita);
			return result;
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile contare gli accessi con l'id località: " + idLocalita);
		} finally {
			
			logger.info(END, idLog);
		}
	}

}
