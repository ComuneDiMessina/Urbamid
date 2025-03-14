package it.eng.tz.urbamid.toponomastica.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.ShapeFileFilter;
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.model.ShapeFileImport;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryShapeFileImport;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryToponimoStradale;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaStorageRepository;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.util.ShapeFileUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ShapeResponseDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ShapeFileConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ToponimoStradaleConverter;

@Service
public class ShapeFileServiceImpl implements ShapeFileService {

	private static final Logger logger = LoggerFactory.getLogger(ShapeFileServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaStorageRepository repository;
	
	@Autowired
	private JpaRepositoryShapeFileImport repositoryImportShape;
	
	@Autowired
	private ToponimoStradaleService toponimoService;
	
	@Autowired
	private ShapeFileConverter converterShape;
	
	@Autowired
	private ToponimoStradaleConverter converter;
	
	@Autowired
	private JpaRepositoryToponimoStradale repositoryToponimo;
	
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	@Override
	public PagedResult<ShapeResponseDTO> getShapeFiles(ShapeFileFilter filter) throws ToponomasticaServiceException {
		String idLog = "getShapeFiles";
		logger.info(START, idLog);
		
		try {
			
			List<ShapeFileImport> listaShapeFiles = repositoryImportShape.listaShapeFile();
			
			/** CREO L'ORDINAMENTO (ASC / DESC) IN BASE ALLE COLONNE **/
			PropertyComparator.sort(listaShapeFiles, new MutableSortDefinition(filter.getPageOrder().getColumn(), true, filter.getPageOrder().getDir().equalsIgnoreCase("asc") ? true : false));
			PagedListHolder<ShapeFileImport> paginated = new PagedListHolder<>();
			
			/** MI POPOLO L'OGGETTO PAGEDLISTHOLDER **/
			paginated.setPage(filter.getPageIndex());
			paginated.setPageSize(filter.getPageSize());
			paginated.setSource(Collections.unmodifiableList(listaShapeFiles));
			
			logger.debug(DEBUG_INFO_END, idLog, listaShapeFiles.size());
			
			return converterShape.toDTO(new PagedResult<>(paginated));
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i file");
			
		} finally {
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public boolean importShapeFile(MultipartFile file) throws ToponomasticaServiceException, IOException {
		String idLog = "importShapeFile";
		logger.info(START, idLog);
		
		boolean response = true;
		String nomeFile = null;
		try {
			if(file != null && file.getSize() != 0) {
				/** CREO LE DIRECTORY PER L'IMPORT **/
				Path savePath = Paths.get(repository.findPath(0L), repository.findPath(10L));
				Files.createDirectories(savePath);
				/** MI SALVO LO .ZIP **/
				Path pathSaveToZip = Paths.get(savePath.toString(), file.getOriginalFilename());
				if(!Files.exists(pathSaveToZip)) {
					Files.write(pathSaveToZip, file.getBytes());
					
					try(ZipFile zipFile = new ZipFile(pathSaveToZip.toFile())) {
						
						/** MI CREO UNA LISTA DEI FILE PRESENTI ALL'INTERNO DELL'ARCHIVIO .ZIP **/
						List<ZipEntry> listaZipEntry = zipFile.stream().collect(Collectors.toList());
						nomeFile = FilenameUtils.getBaseName(listaZipEntry.get(0).getName());
					
						/** CONTROLLO SE LA LISTA E' UGUALE A 3 **/
						if(listaZipEntry.size() >= 3) {
							/** MI CICLO I FILE DELLA LISTA**/
							int countValidFile = 0;
							for (ZipEntry zipEntry : listaZipEntry) {
								/** PRENDO L'ESTENSIONE DEL FILE **/
								String extension = FilenameUtils.getExtension(zipEntry.getName());
								/** VERIFICO SE E' UN ESTENSIONE ACCETTATA **/
								/** VERIFICO CHE IL NOME DEI FILE .DBF, .SHP E .SHX SIA UGUALE **/
								
								if( nomeFile.equalsIgnoreCase(FilenameUtils.getBaseName(zipEntry.getName())) ) {
									byte[] buffer = new byte[2048];
									
									try(InputStream inputStream = zipFile.getInputStream(zipEntry);
										BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
										BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(savePath.toString(), zipEntry.getName())))) {
										
										int bytes = 0;
										/** SCRIVO SUL FILESYSTEM I FILE RECUPERATI DALL'ARCHIVIO **/
										while((bytes = bufferedInputStream.read(buffer)) > 0) {
											bufferedOutputStream.write(buffer, 0, bytes);
											bufferedOutputStream.flush();
										}
									}
									if ( (extension.equalsIgnoreCase("dbf") || extension.equalsIgnoreCase("shp") || extension.equalsIgnoreCase("shx")) ) {
										countValidFile++;
									}
								} else {
									
									throw new ToponomasticaServiceException("I file all'interno dell'archivio zip devono avere lo stesso nome del file zip");
								}
							}
							if (countValidFile>3) {
								
								throw new ToponomasticaServiceException("Non sono presenti i file utili per l'import: .dbf, .shp e .shx");
							}
							
							/** VERIFICO SE IL FILE CON ESTENSIONE .SHX ESISTA **/
							if(Files.exists(Paths.get(savePath.toString(), nomeFile + ".shx"))) {
								List<ToponimoStradaleDTO> listaToponimoDTO = ShapeFileUtils.importShapeFile(Paths.get(savePath.toString(), nomeFile + ".shp"));
								/** CHIAMO IL SERVIZIO PER L'INSERIMENTO/MODIFICA DELLA TABELLA U_TOPO_TOPONIMO_STRADALE E U_TOPO_TOPONIMO_GECODOING **/
								for (ToponimoStradaleDTO toponimoDTO : listaToponimoDTO)
									toponimoService.insertOrUpdate(toponimoDTO);	
							} else {
								throw new ToponomasticaServiceException("Il file con estensione .shx non si trova all'interno dell'archivio zip!");
								
							}
							
						} else {
							throw new ToponomasticaServiceException("I file all'interno dell'archivio zip devono avere almeno le seguenti estensioni: .dbf, .shp e .shx dello stesso shape file");
							
						}
						
					}
				} else {
					throw new ToponomasticaServiceException("Il file: " + FilenameUtils.getBaseName(file.getOriginalFilename()) + " esiste già!");
					
				}
				/** SALVO NEL DB LE INFORMAZIONI DEL FILE .ZIP APPENA CARICATO **/
				ShapeFileImport shapeFile = new ShapeFileImport();
				shapeFile.setNameFile(file.getOriginalFilename());
				shapeFile.setSizeFile(file.getSize());
				shapeFile.setDataImport(new Date());
				shapeFile.setProcessato(response);
				
				repositoryImportShape.save(shapeFile);
				
			} else {
				throw new ToponomasticaServiceException("Non è stato inserito nessun file!");
				
			}
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			/** ELIMINO L'ARCHIVIO ZIP SE L'APPLICATIVO VA IN ERRORE **/
			if(repositoryImportShape.countProcessati(file.getOriginalFilename()) == 0)
				eliminaFileShape(file.getOriginalFilename());
			
			throw new ToponomasticaServiceException(e.getMessage());
			
		} finally {
			/** ELIMINA I FILE CON ESTENSIONE .DBF, .SHP e .SHX **/
			eliminaFileShape(nomeFile);
			logger.info(END, idLog);
			
		}
		
		return response;
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public ShapeResponseDTO exportShapeFile(ToponimoFilter filter) throws ToponomasticaServiceException {
		String idLog = "exportShapeFile";
		logger.info(START, idLog);
		
		try {	
			List<ToponimoStradaleDTO> listaToponimi = converter.toDTO(repositoryToponimo.findAll(RepositoryUtils.buildToponimoPredicate(filter)));
			String rootPathExport = repository.findPath(9L);
			
			return ShapeFileUtils.exportShapeFile(listaToponimi, rootPathExport);
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile creare lo shapeFile del toponimo stradale");
			
		} finally {
			logger.info(END, idLog);
			
		}
		
    }
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public void eliminaFileShape(String nomeDocumento) throws ToponomasticaServiceException {
		String idLog = "eliminaFileShape";
		logger.info(START, idLog);
		
		try {
			
			Path pathFile = Paths.get(repository.findPath(0L), repository.findPath(10L));
			List<File> fileDaEliminare = new ArrayList<>();
			
			if(StringUtils.hasText(nomeDocumento) && !FilenameUtils.getExtension(nomeDocumento).equalsIgnoreCase("zip")) {
				
				if(Files.exists(pathFile)) {
					
					fileDaEliminare = Files.walk(pathFile).map(Path::toFile)
 							  							  .filter(File::isFile)
 							  							  .filter(p -> FilenameUtils.getBaseName(p.getName()).equalsIgnoreCase(nomeDocumento))
 							  							  .filter(zip -> !FilenameUtils.getExtension(nomeDocumento).equalsIgnoreCase("zip"))
 							  							  .collect(Collectors.toList());
				}
				
			} else if(StringUtils.hasText(nomeDocumento) && FilenameUtils.getExtension(nomeDocumento).equalsIgnoreCase("zip")) {
				
				if(Files.exists(pathFile)) {
					
					fileDaEliminare = Files.walk(pathFile).map(Path::toFile)
														  .filter(File::isFile)
														  .filter(p -> p.getName().equalsIgnoreCase(nomeDocumento))
														  .collect(Collectors.toList());
					
				}
				
			}
			
			for (File file : fileDaEliminare)
				file.delete();
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException();
			
		} finally {
			logger.info(END, idLog);
			
		}
				
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void eliminaFileZip(Long id) throws ToponomasticaServiceException {
		String idLog = "eliminaFileZip";
		logger.info(START, idLog);
		
		try {
			
			if(id != null) {	
				eliminaFileShape(repositoryImportShape.getOne(id).getNameFile());
//				Files.delete(Paths.get(repositoryImportShape.getOne(id).getPathFile()));
				repositoryImportShape.deleteById(id);

			}
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare l'archivio .zip");
			
		} finally {
			logger.info(END, idLog);
			
		}
			
	}

}
