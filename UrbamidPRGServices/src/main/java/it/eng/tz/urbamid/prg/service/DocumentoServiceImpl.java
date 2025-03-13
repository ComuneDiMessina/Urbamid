package it.eng.tz.urbamid.prg.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.prg.exception.ConverterException;
import it.eng.tz.urbamid.prg.filter.TipoDocumentoFilter;
import it.eng.tz.urbamid.prg.persistence.dao.TipoDocumentoDao;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryTipoDocumento;
import it.eng.tz.urbamid.prg.util.RepositoryUtils;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.prg.web.dto.converter.TipoDocumentoConverter;

@Service
public class DocumentoServiceImpl implements DocumentoService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class.getName());
	
	@Autowired
	private JpaRepositoryTipoDocumento documentoRepository;
	
	@Autowired
	private TipoDocumentoDao documentoDao;
	
	@Autowired
	private TipoDocumentoConverter converter;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<TipoDocumentoDTO> getDocumenti(TipoDocumentoFilter filter) throws ConverterException {
		
		logger.debug("getDocumenti");
		
		Page<TipoDocumento> pagedResult = documentoRepository.findAll(RepositoryUtils.buildDocumentoPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize(), JpaRepositoryTipoDocumento.DEFAULT_SORT));
		
		return converter.toDTO(new PagedResult<>(pagedResult));
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void insertOrUpdateDocumento(TipoDocumentoDTO documento) throws ConverterException {
				
		if(documento.getId() != null) {
			
			logger.debug("insertDocumento");
			
			Optional<TipoDocumento> model = documentoRepository.findById(documento.getId());
			TipoDocumento daModificare = converter.toModel(documento);
			TipoDocumento daDB = model.get();
			
			daModificare.setId(daDB.getId());
				
			documentoDao.updateDocumento(daModificare);
			
		} else {
			
			logger.debug("updateDocumento");
			
			TipoDocumento daSalvare = converter.toModel(documento);
			
			documentoDao.insertDocumento(daSalvare);
			
		}
		
	}
	

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public int countCodiceDocumento(String codice) {

		logger.debug("countCodiceDocumento");
		
		int result = documentoRepository.countTipoDocumentoDaCodice(codice);
		
		return result;
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void deleteDocumento(Long id) {

		logger.debug("deleteDocumento");
		
		documentoRepository.deleteById(id);
		
	}

	@Override
	public Iterable<TipoDocumento> findTipoDocumenti() throws ConverterException {
		logger.debug("getDocumenti");
		
		Iterable<TipoDocumento> result = documentoRepository.findAll();
		
		return result;
	}

}
