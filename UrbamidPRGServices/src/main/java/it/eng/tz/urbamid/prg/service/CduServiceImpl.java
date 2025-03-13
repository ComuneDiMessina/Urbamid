package it.eng.tz.urbamid.prg.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.prg.filter.CduFilter;
import it.eng.tz.urbamid.prg.persistence.model.Cdu;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryCdu;
import it.eng.tz.urbamid.prg.util.RepositoryUtils;
import it.eng.tz.urbamid.prg.web.dto.CduDTO;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.converter.CduConverter;

@Service
public class CduServiceImpl implements CduService {

	private static final Logger logger = LoggerFactory.getLogger(CduServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	@Autowired
	private JpaRepositoryCdu cduRepository;

	@Autowired
	private CduConverter cduConverter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<CduDTO> cercaCdu(CduFilter filter) throws Exception {
		String idLog = "cercaCdu";
		try{
			logger.info(START, idLog);
			Page<Cdu> pagedResult = cduRepository.findAll(
					//SPECIFICATION
					RepositoryUtils.buildCduPredicate( filter ), 
					//PAGE & SORT
					PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize(),
							Sort.by(Sort.Direction.DESC, "dataCreazione"))
							
					);
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getTotalElements());

			return cduConverter.toDTO( new PagedResult<>(pagedResult) );
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public File downloadCduAnagrafica(String protocollo) throws Exception {
		File file = null;
		try {
			Cdu cdu = cduRepository.findByProtocollo(protocollo);
			if (cdu != null) {
				file = new File(cdu.getPathDocumento());
			}
		} catch (Exception e) {
			logger.error("Errore nel download del documento{}", e.getMessage(), e);
			throw (e);
		}
		return file;
	}


}
