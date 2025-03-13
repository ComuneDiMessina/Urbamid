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
import it.eng.tz.urbamid.toponomastica.filter.LocalitaFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.LocalitaDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryLocalita;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.LocalitaConverter;

@Service
public class LocalitaServiceImpl implements LocalitaService {

	private static final Logger logger = LoggerFactory.getLogger(LocalitaServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";

	@Autowired
	private JpaRepositoryLocalita localitaRepository;
	
	@Autowired
	private LocalitaDAO localitaDAO;

	@Autowired
	private LocalitaConverter localitaConverter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<LocalitaDTO> getLocalita(LocalitaFilter filter) throws ToponomasticaServiceException {

		String idLog = "getLocalita";

		try {

			logger.debug(START, idLog);

			Page<Localita> pagedResult = localitaRepository.findAll(RepositoryUtils.buildLocalitaPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));

			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());

			return localitaConverter.toDTO(new PagedResult<>(pagedResult));

		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare le località");
			
		} finally {

			logger.info(END, idLog);

		}

	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public LocalitaDTO insertOrUpdate(LocalitaDTO localita) throws ToponomasticaServiceException {

		String idLog = "insertOrUpdate";

		try {

			if(localita.getId() != null) {
				
				logger.debug(START, " UPDATE "  + idLog);
				
				Localita model = localitaDAO.updateLocalita(localitaConverter.toModel(localita));
				return localitaConverter.toDTO(model);
			} else {
				
				logger.debug(START, " INSERT "  + idLog);
				Localita model = new Localita();
				if (localita.getGeom()!=null && !localita.getGeom().isEmpty()) 
					model = localitaDAO.insertLocalita(localitaConverter.toModel(localita));
				else
					model = localitaDAO.insertLocalitaNoGeom(localitaConverter.toModel(localita));	
				
				return localitaConverter.toDTO(model);
			}
			
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire o modificare la località");

		} finally {

			logger.info(END, idLog);

		}

	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void deleteById(Long id) throws ToponomasticaServiceException {

		String idLog = "deleteLocalita";

		try {

			logger.debug(START, idLog);

			localitaRepository.deleteById(id);

		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare la località con id:" + id);

		} finally {

			logger.info(END, idLog);

		}

	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public List<LocalitaDTO> findAll() throws ToponomasticaServiceException {

		String idLog = "findAll";

		List<LocalitaDTO> result;
		
		try {

			logger.debug(START, idLog);

			result = localitaConverter.toDTO(localitaRepository.findAll());
			
			return result;
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare le località");

		} finally {
			
			logger.info(END, idLog);
			
		} 
			
	}

}
