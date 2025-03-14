package it.eng.tz.urbamid.toponomastica.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.ShapeFileFilter;
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ShapeResponseDTO;

public interface ShapeFileService {

	public PagedResult<ShapeResponseDTO> getShapeFiles(ShapeFileFilter filter) throws ToponomasticaServiceException;

	public boolean importShapeFile(MultipartFile file) throws ToponomasticaServiceException, IOException;

	public ShapeResponseDTO exportShapeFile(ToponimoFilter filter) throws ToponomasticaServiceException;

	public void eliminaFileShape(String nomeDocumento) throws ToponomasticaServiceException, Exception;

	public void eliminaFileZip(Long id) throws ToponomasticaServiceException;


	
	
	
}
