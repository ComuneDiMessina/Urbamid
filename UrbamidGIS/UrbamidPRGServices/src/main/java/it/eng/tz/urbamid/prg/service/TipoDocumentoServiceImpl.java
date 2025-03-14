package it.eng.tz.urbamid.prg.service;

import java.util.ArrayList;
import java.util.List;
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
import it.eng.tz.urbamid.prg.persistence.model.DocumentoVariante;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryDocumentoVariante;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryTipoDocumento;
import it.eng.tz.urbamid.prg.util.RepositoryUtils;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.converter.TipoDocumentoConverter;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoServiceImpl.class.getName());
	
	@Autowired
	private JpaRepositoryTipoDocumento documentoRepository;

	@Autowired
	private JpaRepositoryDocumentoVariante documentoVarianteRepository;
	
	@Autowired
	private TipoDocumentoDao documentoDao;
	
	@Autowired
	private TipoDocumentoConverter converter;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<TipoDocumentoDTO> getDocumenti(TipoDocumentoFilter filter) throws ConverterException {
		String idLog = "getDocumenti";
		
		try {
			
			logger.debug("getDocumenti");
			
			Page<TipoDocumento> pagedResult = documentoRepository.findAll(RepositoryUtils.buildDocumentoPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize(), JpaRepositoryTipoDocumento.DEFAULT_SORT));
			
			return converter.toDTO(new PagedResult<>(pagedResult));
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void insertOrUpdateDocumento(TipoDocumentoDTO documento) throws ConverterException {
		String idLog = "insertOrUpdateDocumento";
		
		try {
		
			if(documento.getId() != null) {
				
				logger.debug(idLog);
				
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
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		}
		
	}
	

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public int countCodiceDocumento(String codice) {
		String idLog = "countCodiceDocumento";
		int result = 0;
		
		try {
			logger.debug(idLog);
			
			result = documentoRepository.countTipoDocumentoDaCodice(codice);
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		}
	
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void deleteDocumento(Long id) {
		String idLog = "deleteDocumento";
		
		try {
			
			logger.debug(idLog);
			documentoRepository.deleteById(id);
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipoDocumento> findTipoDocumenti() throws ConverterException {
		String idLog = "getDocumenti";
		
		try {
			
			logger.debug(idLog);
			return documentoRepository.findAll(JpaRepositoryTipoDocumento.DEFAULT_SORT);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipoDocumentoVarianteDTO> varianteByTipoDocumento(String tipoDocumento) throws ConverterException {
		String idLog = "getDocumenti";
		
		try {
			
			logger.debug(idLog);
			return documentoDao.varianteByTipoDocumento(tipoDocumento);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipoDocumento> cercaTipiDocumentoMancanti(Long idVariante) throws Exception {
		String idLog = "cercaDocumentiByIdVariante";
		List<TipoDocumento> listDocumentiConvertita = new ArrayList<>();
		try{
			List<TipoDocumento> listTipoDocumenti = documentoRepository.findAll(JpaRepositoryTipoDocumento.DEFAULT_SORT);
			List<DocumentoVariante> listDocumenti = documentoVarianteRepository.cercaDocumentiByIdVariante(idVariante);
			
			for (DocumentoVariante documentoVariante : listDocumenti) {
				Optional<TipoDocumento> item = listTipoDocumenti.stream().filter(x -> x.getCodice().equals(documentoVariante.getTipoDocumento())).findFirst();
				listDocumentiConvertita.add(item.get());
			}
			
			listTipoDocumenti.removeAll(listDocumentiConvertita);
			
			return listTipoDocumenti;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
		}
	}

}
