package it.eng.tz.urbamid.toponomastica.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.DocumentoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaStorageRepository;
import it.eng.tz.urbamid.toponomastica.web.dto.DocumentoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;

@Service
public class StorageServiceImpl implements StorageService {

	private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> in corso del file: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaStorageRepository repository;
		
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<DocumentoDTO> getDocumenti(DocumentoFilter filter) throws ToponomasticaServiceException {
		
		String idLog = "getDocumenti";
		logger.info(START, idLog);
		
		try {
			
			String rootPath = repository.findPath(0L);
			Path path = Paths.get(rootPath + repository.findPath(filter.getTipoRisorsa()) + File.separator + filter.getIdRisorsa());
			
			List<File> files = new ArrayList<>();
						
			if(Files.exists(path)) {
				
				files = Files.walk(path).map(Path::toFile)
					 	   				.filter(File::isFile)
					 	   				.collect(Collectors.toList());
			
			}
			
			List<DocumentoDTO> lista = new ArrayList<>();
			
			if(files.size() != 0) {
			
				for (int i = 0; i < files.size(); i++) {
					
					BasicFileAttributes attr = Files.readAttributes(files.get(i).toPath(), BasicFileAttributes.class);
					
					DocumentoDTO doc = new DocumentoDTO();
					
					doc.setIdRisorsa(filter.getIdRisorsa());
					doc.setPath(path.toString());
					doc.setNomeDocumento(files.get(i).getName());
					doc.setDataInserimento(new Date(attr.creationTime().toMillis()));
								
					lista.add(doc);
					
				}
				
			}
			
			PagedListHolder<DocumentoDTO> paginated = new PagedListHolder<>();
			
			paginated.setPage(filter.getPageIndex());
			paginated.setPageSize(filter.getPageSize());
			paginated.setSource(lista);
						
			logger.debug(DEBUG_INFO_END, idLog, paginated.getNrOfElements());
			
			return new PagedResult<>(paginated);
		
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i file");
			
		} finally {
			
			logger.info(END, idLog);
			
		}

	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public void upload(DocumentoDTO file) throws ToponomasticaServiceException {
		
		String idLog = "upload";
		logger.info(START, idLog);
		
		try {
			Path rootSavePath = Paths.get(repository.findPath(0L) + repository.findPath(file.getTipoRisorsa().getId()) + file.getIdRisorsa());
			Files.createDirectories(rootSavePath);
			
			if(file != null) {
				Path fileSavePath = Paths.get(rootSavePath.toString() + File.separator + file.getNomeDocumento());
				Files.write(fileSavePath, Base64Utils.decode(file.getFile()));
				
			}
			
		} catch (Exception e) {
			logger.error(ERROR, e);
			throw new ToponomasticaServiceException("Non è stato possibile effettuare l'upload del file col nome: " + file.getNomeDocumento());
						
		} finally {
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DocumentoDTO download(Long idRisorsa, Long tipoRisorsa, String nomeDocumento) throws ToponomasticaServiceException {
		
		String idLog = "download";
		logger.info(START, idLog);
		
		try {
			DocumentoDTO file = new DocumentoDTO();
			
			String rootPath = repository.findPath(0L);
			
			if(idRisorsa != null && tipoRisorsa != null && StringUtils.hasText(nomeDocumento)) {
				
				file.setPath(rootPath + repository.findPath(tipoRisorsa) + idRisorsa + File.separator + nomeDocumento);
				file.setFile(Base64Utils.encode(Files.readAllBytes(Paths.get(file.getPath()))));
				file.setNomeDocumento(nomeDocumento);
				file.setIdRisorsa(idRisorsa);
			
			}
								
			return file;
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile effettuare il download del file col nome: " + nomeDocumento);
		
		}  finally {
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public void elimina(Long idRisorsa, Long tipoRisorsa, String nomeDocumento) throws ToponomasticaServiceException {
		
		String idLog = "elimina";
		logger.info(START, idLog);
		
		try {

			String rootPath = repository.findPath(0L);
			File file = null;
			
			if(idRisorsa != null && tipoRisorsa != null && StringUtils.hasText(nomeDocumento)) {
				
				file = new File(rootPath + repository.findPath(tipoRisorsa) + idRisorsa + File.separator + nomeDocumento);
				file.delete();
			} 
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare il file col nome:" + nomeDocumento);
			
		} finally {
		
			logger.info(END, idLog);
			
		}
		
	}

}
