package it.eng.tz.urbamid.catasto.shapefile.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.MultiPolygon;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.CatastoStorageException;
import it.eng.tz.urbamid.catasto.export.service.ExportCatastoPathService;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaGeom;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryParticellaGeom;
import it.eng.tz.urbamid.catasto.psql.service.PsqlService;
import it.eng.tz.urbamid.catasto.shapefile.service.ShapefileService;
import it.eng.tz.urbamid.catasto.shapefile.util.ShapefileType;
import it.eng.tz.urbamid.catasto.shp2pgsql.service.Shp2PgsqlService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;

@Service
public class ShapefileServiceImpl extends AbstractShapefileService 	implements ShapefileService {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ShapefileServiceImpl.class.getName());
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	public static final Charset USER_CHARSET = Charset.forName("UTF-8");
	
	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	private final ImportCatastoPathService pathService;

	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	private final ExportCatastoPathService pathExportService;
	
	/**
	 * Service per la conversione di uno shapefile in script SQL
	 */
	private final Shp2PgsqlService shp2PgsqlService;

	/**
	 * Service per l'esecuzione/import nel database di uno script SQL
	 */
	private final PsqlService psqlService;
	
	@Autowired
	private JpaRepositoryParticellaGeom particellaGeomDao;

	private ShapefileDataStore shpDataStore;
	
	/**
	 * Costruttore.
	 * 
	 * @param psqlService {@link PsqlService}
	 * @param shp2PgsqlService {@link Shp2PgsqlService}
	 * @param pathService {@link ImportCatastoPathService}
	 */
	public ShapefileServiceImpl(
			PsqlService psqlService, Shp2PgsqlService shp2PgsqlService, ImportCatastoPathService pathService, ExportCatastoPathService pathExportService) {
		Assert.notNull(psqlService, "PsqlService MUST not be null but don't panic!");
		Assert.notNull(shp2PgsqlService, "Shp2PgsqlService MUST not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.psqlService = psqlService;
		this.shp2PgsqlService = shp2PgsqlService;
		this.pathService = pathService;
		this.pathExportService = pathExportService;
	}

	@Override
	public String importaShapefile(ImportType importType) throws CatastoServiceException {
		
		String logShp2Pgsql = creaScriptSQLDagliShapefile(importType);
		String logPsql = importaScriptSQLNelDatabase(importType);
		return new StringBuilder(logShp2Pgsql).append(logPsql).toString();
	}
	
	/**
	 * Metodo privato che crea gli script SQL a partire dagli shapefile in una determinata cartella
	 * attraverso shp2pgsql.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String creaScriptSQLDagliShapefile(ImportType importType) throws CatastoServiceException {
		
		Path rootDirectoryShapefile = Paths.get(this.pathService.shapefileDirectory(importType));
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(logger.isDebugEnabled()) {
			logger.debug("Prelevo gli shapefile presenti nella cartella {}.", rootDirectoryShapefile.toFile().getAbsolutePath());
		}

		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootDirectoryShapefile)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaShapefile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(PATTERN_PUNTO_SHP))
						.collect( Collectors.toList());
			
			if(logger.isDebugEnabled()) {
				logger.debug("Sono presenti {} shapefile da importare tramite il comando shp2pgsql.", listaShapefile.size());
			}
			
			//lo schema deve essere "temporary" per l'AGGIORNAMENTO e "public" per l'ATTUALITA'
//			String SCHEMA = ImportType.AGGIORNAMENTO.equals(importType) ? this.TEMP_DATABASE_SCHEMA_IMPORT
//									: this.DEFAULT_DATABASE_SCHEMA_IMPORT;
			for (File shapefile : listaShapefile) {
				Optional<String> optionalTable = getTableName(shapefile);
				if(optionalTable.isPresent()) {
					String log = this.shp2PgsqlService.convertiShapefileInScriptSQL(
//							importType, shapefile, this.DEFAULT_DATABASE_SCHEMA_IMPORT, 
							importType, shapefile, this.DEFAULT_DATABASE_SCHEMA_IMPORT,
							optionalTable.get(), this.DEFAULT_GEOMETRY_COLUMN_NAME);
					informazioniDiLog.append(log);
				} else {
					logger.error("Non è stato possibile associare ad una tabella lo shapefile col nome {}.",
							shapefile.getName());
				}
			}
			
			return informazioniDiLog.toString();
			
		} catch (IOException ioe) {
			if( logger.isErrorEnabled() )
				logger.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new CatastoStorageException(ioe);
		}
		
	}
	
	/**
	 * Metodo privato che importa tutti gli script SQL nel database tramite psql.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String importaScriptSQLNelDatabase(ImportType importType) throws CatastoServiceException {
		
		Path rootDirectorySQLScriptFile = Paths.get(this.pathService.sqlScriptDirectory(importType));
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(logger.isDebugEnabled()) {
			logger.debug("Prelevo gli script SQL presenti nella cartella {}.", rootDirectorySQLScriptFile.toFile().getAbsolutePath());
		}

		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootDirectorySQLScriptFile)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaSQLScriptFile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(PATTERN_PUNTO_SQL))
						.collect( Collectors.toList());
			
			if(logger.isDebugEnabled()) {
				logger.debug("Sono presenti {} script SQL da importare tramite psql.", listaSQLScriptFile.size());
			}
			
			for (File scriptSQL : listaSQLScriptFile) {
				String log = this.psqlService.eseguiScriptSQL(scriptSQL);
				informazioniDiLog.append(log);
			}
			
			return informazioniDiLog.toString();
			
		} catch (IOException ioe) {
			if( logger.isErrorEnabled() )
				logger.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new CatastoStorageException(ioe);
		}
	}
	
	
	private Optional<String> getTableName(File shapefile) {
		String ret = null;
		String filename = shapefile.getName().toLowerCase();
		for(ShapefileType t : ShapefileType.values()) {
			if(filename.toLowerCase().contains(t.name().toLowerCase())){
				ret = t.table();
			}
		}
		return Optional.ofNullable(ret);
	}
	
	public PsqlService getPsqlService() {
		return psqlService;
	}

	public Shp2PgsqlService getShp2PgsqlService() {
		return shp2PgsqlService;
	}

	public ImportCatastoPathService getPathService() {
		return pathService;
	}

	@Override
	public File exportShapefile(List<ParticellaCustomDTO> lsParticelle, String titolo) throws CatastoServiceException {

		String idLog = "createShapeFile";
		logger.info(START, idLog);
		
		/***********************/
		/**	1. CREAZIONE FILE **/
		File output = null;
		LocalTime currentTime = LocalTime.now();
		String exportShapeFolderStr = this.pathExportService.exportShapefileDirectory();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		exportShapeFolderStr = exportShapeFolderStr + File.separator +
										dtf.format(localDate) + File.separator;
		File exportFolder = new File (exportShapeFolderStr); 
		if (!exportFolder.exists()) 
			exportFolder.mkdir();
		exportShapeFolderStr += ""+(currentTime.getHour()<10 ? "0"+currentTime.getHour() : currentTime.getHour()) + 
										(currentTime.getMinute()<10?"0"+currentTime.getMinute():currentTime.getMinute()) + File.separator;
		exportFolder = new File (exportShapeFolderStr); 
		if (!exportFolder.exists()) 
			exportFolder.mkdir();
		
		/***************************/
		/** 2. RECUPERO LE FEATURE**/
		List<Long> ids = new ArrayList<Long>();
		HashMap<Long,ParticellaCustomDTO> hm = new HashMap<Long,ParticellaCustomDTO>();
		for (ParticellaCustomDTO particellaCustomDTO : lsParticelle) {
			if (particellaCustomDTO.getGid()!=null) {
				hm.put(particellaCustomDTO.getGid(),particellaCustomDTO);
				ids.add(particellaCustomDTO.getGid());
			}
		}
		List<ParticellaGeom> lsGeometrie = particellaGeomDao.findByGids(ids);
//		for (ParticellaGeom geom : lsGeometrie) {
//			String foglio = (geom.getFoglio()!=null) ? (isNumeric(geom.getFoglio())? geom.getFoglio().replaceFirst("^0+(?!$)", "") : geom.getFoglio()) : null;
//			String mappale = (geom.getMappale()!=null)? (isNumeric(geom.getMappale()) ? (Integer.valueOf(geom.getMappale())).toString() : geom.getMappale()) : null;		
//		}
		
		/*************************************************/
		/** 3. IMPOSTO LO SHAPEFILE, POPOLO LO SHAPEFILE**/
		try(Transaction transaction = new DefaultTransaction("create")) {
			
			/** MI CREO IL FEATURE TYPE **/
			SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
			
			featureTypeBuilder.setName("shapeCatasto");
			/** SETTO LA PROJ SUL SISTEMA DI RIFERIMENTO WGS84 (4326) PER LA GEOMETRIA**/
			featureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84);
			/** DEFINISCO I NOMI DEGLI ATTRIBUTI DELLO SHAPE FILE **/
			featureTypeBuilder.add("the_geom", MultiPolygon.class);
			featureTypeBuilder.add("id", Long.class);
			featureTypeBuilder.add("foglio", String.class);
			featureTypeBuilder.add("mappale", String.class);
			featureTypeBuilder.add("codiceCom", String.class);
			featureTypeBuilder.add("allegato", String.class);
			featureTypeBuilder.add("sviluppo", String.class);
			featureTypeBuilder.add("area", Double.class);
			featureTypeBuilder.add("intersectArea", Double.class);
			featureTypeBuilder.add("chiave", String.class);
			SimpleFeatureType featureType = featureTypeBuilder.buildFeatureType();
			
			SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
			List<SimpleFeature> features = new ArrayList<SimpleFeature>();
			WKTReader2        wkt      = new WKTReader2();
			/** MI CICLO LA LISTA DEI TOPONIMI STRADALI INSERENDOLI NEL featureBuilder **/
			for (ParticellaGeom particellaGeom : lsGeometrie) {
				
				ParticellaCustomDTO particellaCustomDTO = hm.get(particellaGeom.getGid());
				featureBuilder.add(wkt.read(particellaGeom.getGeom()));
				featureBuilder.add(particellaGeom.getGid());
				featureBuilder.add(particellaGeom.getFoglio());
				featureBuilder.add(particellaGeom.getMappale());
				featureBuilder.add(particellaGeom.getCodiceCom());
				featureBuilder.add(particellaGeom.getAllegato());
				featureBuilder.add(particellaGeom.getSviluppo());
				featureBuilder.add(particellaCustomDTO.getArea());
				featureBuilder.add(particellaCustomDTO.getIntersectArea());
				featureBuilder.add( particellaGeom.getCodiceCom()+"|"+particellaGeom.getFoglio()+"|"+particellaGeom.getMappale());
			
				features.add(featureBuilder.buildFeature(null));
			}
			
			/** CREO LA COLLEZIONE DI SIMPLEFEATURE, PASSANDOGLI IL FEATURETYPE E LA LISTA FEATURES **/
			SimpleFeatureCollection collection = new ListFeatureCollection(featureType, features);
			/** CREO UN DATASTORE PER LO SHAPEFILE **/
			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
			
			/** CREO IL FILE CHIAMATO EXPORT.SHP NELLA ROOTPATH DELL'EXPORT DI TOPONOMASTICA **/
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
			File file = new File(exportShapeFolderStr, formatter.format(new Date()) + "_Export.shp");			
			
			/** CREO UN HASMAP CONTENENTE L'URL DEL FILE E LO SPATIAL INDEX
			 * 	Si possono inserire altri parametri, andare su: <a>https://docs.geotools.org/stable/userguide/library/data/shape.html</a> per ulteriori informazioni**/
			Map<String, Serializable> map = new HashMap<>();
			map.put("url", file.toURI().toURL());
			map.put("create spatial index", Boolean.TRUE);
			
			/** CREO UN SHAPEFILEDATASTORE IN CUI GLI PASSO L'HASHMAP **/
			ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(map);
			/** SETTO LO SCHEMA E FORZO LA PROJ NEL SISTEMA DI RIFERIMENTO A 4326 **/
			newDataStore.createSchema(featureType);
			newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);
			
			String typeName = newDataStore.getTypeNames()[0];
			SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
			
			if(featureSource instanceof SimpleFeatureStore) {
				
				SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
				featureStore.setTransaction(transaction);
				featureStore.addFeatures(collection);
				transaction.commit();
			}
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
		} finally {
			
			logger.info(END, idLog);
		}
		
		/****************************************/
		/**	4. SCRIVO I SINGOLI FILE - ZIP FILE**/
		List<File> listaFile = new ArrayList<>();
		File folder = new File (exportShapeFolderStr);
		for (final File file : folder.listFiles()) {
	        if (!file.isDirectory()) {
	        	listaFile.add( file );
	        } 
	    }
		try {
			output = creaZip(exportShapeFolderStr, listaFile, titolo);
		} catch (IOException ex){
			logger.error(ex.getMessage());
		}
		
		return output;
	}

	private File creaZip(String folder, List<File> listaFile, String titolo) throws MalformedURLException {

		/**CREATE FILE**/
		String filePath = folder + "Estrazione_" + titolo + ".zip";
		File zipfile = new File(filePath);
		
	    try {
			
		    // Creo un buffer per leggere i file
		    byte[] buf = new byte[1024];
		    
	        // Creo il file ZIP
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
	        for (File file : listaFile) {
	            FileInputStream in = new FileInputStream(file.getCanonicalPath());
	            // aggiungo il file all'output stream dello ZIP
	            out.putNextEntry(new ZipEntry(file.getName()));
	            // trasferisco i byte dal file allo ZIP 
	            int len;
	            while((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	            out.closeEntry();
	            in.close();
	            // cancello i file .xls creati precedentemente
	            FileUtils.deleteQuietly(file);
	        }
	        out.close();
	    } catch (IOException ex) {
	    	logger.error(ex.getMessage());
	    }
	    
	    return zipfile;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
}
