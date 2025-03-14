package it.eng.tz.urbamid.toponomastica.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureSource;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.MultiLineString;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ShapeResponseDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

/**
 * Classe che racchiude una serie di metodi statici per la creazione, l'import e l'export degli shapeFiles dei toponimi stradali.
 * @author Luca Tricarico
 */
public class ShapeFileUtils {

	/** LOGGER **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ShapeFileUtils.class.getName());
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	/**
	 * Metodo privato per la creazione del {@code SimpleFeatureType}, che serve per impostare le colonne degli attributi 
	 * dello shape file.
	 * @return {@code SimpleFeatureType}
	 */
	private final static SimpleFeatureType featureType() {
		
		SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
		
		featureTypeBuilder.setName("shapeToponimo");
		/** SETTO LA PROJ SUL SISTEMA DI RIFERIMENTO WGS84 (4326) PER LA GEOMETRIA**/
		featureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84);
		/** DEFINISCO I NOMI DEGLI ATTRIBUTI DELLO SHAPE FILE **/
		featureTypeBuilder.add("the_geom", MultiLineString.class);
		featureTypeBuilder.add("id", Long.class);
		featureTypeBuilder.add("comuneLabl", String.class);
		featureTypeBuilder.add("denominazi", String.class);
		featureTypeBuilder.add("denominUff", String.class);
		featureTypeBuilder.add("classeLabl", String.class);
		featureTypeBuilder.add("shapeLeng", String.class);
		featureTypeBuilder.add("cap", String.class);
		featureTypeBuilder.add("compendi", String.class);
		featureTypeBuilder.add("precdenomi", String.class);
		featureTypeBuilder.add("quartiere", String.class);
		featureTypeBuilder.add("numDelib", String.class);
		featureTypeBuilder.add("dataDelib", Date.class);
		featureTypeBuilder.add("codiceAuto", String.class);
		featureTypeBuilder.add("dataAuto", Date.class);
		featureTypeBuilder.add("comune", Long.class);
		featureTypeBuilder.add("classe", Long.class);
		featureTypeBuilder.add("note", String.class);
		featureTypeBuilder.add("tipo", Long.class);
		featureTypeBuilder.add("codice", String.class);
		featureTypeBuilder.add("stato", String.class);
		featureTypeBuilder.add("idPadre", Long.class);
		featureTypeBuilder.add("isCircle", Boolean.class);
		
		SimpleFeatureType featureType = featureTypeBuilder.buildFeatureType();
		
		return featureType;
	}

	/**
	 * Metodo privato per la creazione dello shape file passandogli la lista dei toponimi stradali e la rootpath dell'export di toponomastica
	 * @param listaToponimi lista dei toponimi stradali
	 * @param rootPathExport rootpath dell'export di toponomastica
	 * @throws Exception
	 */
	private final static void createShapeFile(List<ToponimoStradaleDTO> listaToponimi, String rootPathExport) throws Exception {

		String idLog = "createShapeFile";
		LOGGER.info(START, idLog);

		
		try(Transaction transaction = new DefaultTransaction("create")) {
			
			/** MI CREO IL FEATURE TYPE **/
			SimpleFeatureType featureType = featureType();
			
			SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
			List<SimpleFeature> features = new ArrayList<SimpleFeature>();
			
			/** MI CICLO LA LISTA DEI TOPONIMI STRADALI INSERENDOLI NEL featureBuilder **/
			for (ToponimoStradaleDTO toponimoStradaleDTO : listaToponimi) {
				
				featureBuilder.add(toponimoStradaleDTO.getGeom());
				featureBuilder.add(toponimoStradaleDTO.getId());
				featureBuilder.add(toponimoStradaleDTO.getComuneLabel());
				featureBuilder.add(toponimoStradaleDTO.getDenominazione());
				featureBuilder.add(toponimoStradaleDTO.getDenominazioneUfficiale());
				featureBuilder.add(toponimoStradaleDTO.getClasseLabel());
				featureBuilder.add(toponimoStradaleDTO.getShapeLeng());
				featureBuilder.add(toponimoStradaleDTO.getCap());
				featureBuilder.add(toponimoStradaleDTO.getCompendi());
				featureBuilder.add(toponimoStradaleDTO.getPrecdenomi());
				featureBuilder.add(toponimoStradaleDTO.getQuartiere());
				featureBuilder.add(toponimoStradaleDTO.getNumeroDelibera());
				featureBuilder.add(toponimoStradaleDTO.getDataDelibera());
				featureBuilder.add(toponimoStradaleDTO.getCodiceAutorizzazione());
				featureBuilder.add(toponimoStradaleDTO.getDataAutorizzazione());
				featureBuilder.add(toponimoStradaleDTO.getComune().getIdComune());
				featureBuilder.add(toponimoStradaleDTO.getClasse().getId());
				featureBuilder.add(toponimoStradaleDTO.getNote());
				featureBuilder.add(toponimoStradaleDTO.getTipo().getId());
				featureBuilder.add(toponimoStradaleDTO.getCodice());
				featureBuilder.add(toponimoStradaleDTO.getStato());
				featureBuilder.add(toponimoStradaleDTO.getIdPadre());
				featureBuilder.add(toponimoStradaleDTO.getIsCircle());
			
				features.add(featureBuilder.buildFeature(null));
			}
			
			/** CREO LA COLLEZIONE DI SIMPLEFEATURE, PASSANDOGLI IL FEATURETYPE E LA LISTA FEATURES **/
			SimpleFeatureCollection collection = new ListFeatureCollection(featureType, features);
			/** CREO UN DATASTORE PER LO SHAPEFILE **/
			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
			
			/** CREO IL FILE CHIAMATO EXPORT.SHP NELLA ROOTPATH DELL'EXPORT DI TOPONOMASTICA **/
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
			File file = new File(rootPathExport, formatter.format(new Date()) + "_ToponimiStradali.shp");			
			
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
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(e);

		} finally {
			LOGGER.info(END, idLog);
			
		}

	}
	
	/**
	 * Metodo pubblico che serve per esportare gli shape file in un archivio zip.
	 * <br>I file presenti nello zip sono:<b><br>.shp - contiene la geometria
	 * 						 		   		 <br>.prj - contiene la proj WSG84
	 * 						 		   		 <br>.dbf - contiene gli attributi</b>
	 * @param listaToponimi la lista dei Toponimi Stradali
	 * @param rootPathExport la rootPath dell'export di Toponomastica
	 * @return {@code ShapeResponseDTO}, il DTO di risposta per poter effettuare il download del'archivio zip
	 * @throws IOException
	 */
	public static ShapeResponseDTO exportShapeFile(List<ToponimoStradaleDTO> listaToponimi, String rootPathExport) throws Exception {
		String idLog = "exportShapeFile";
		LOGGER.info(START, idLog);
		
		/** CREO LE DIRECTORY CON LE SOTTODIRECTORY NEL CASO IN CUI NON ESISTESSERO **/
		Path pathExportZip = Paths.get(rootPathExport);
		Files.createDirectories(pathExportZip);
		/** CREO IL NUOVO ARCHIVIO .ZIP **/
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		File archivioZip = new File(rootPathExport + File.separator + formatter.format(new Date()) + "_ToponimiStradali.zip");
		
		try(ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(archivioZip.toPath()))) {
			/** CREA GLI SHAPEFILES **/
			createShapeFile(listaToponimi, rootPathExport);
			/** CREA UNA LISTA DI FILE DA ZIPPARE **/
			List<File> listaFileDaZippare = new ArrayList<>();
			
			/** POPOLA {@code listaFileDaZippare} CON I FILE PRESENTI NEL ROOTPATH DELL'EXPORT DI TOPONOMASTICA**/
			listaFileDaZippare = Files.walk(pathExportZip).map(Path::toFile)
											  			  .filter(File::isFile)
											  			  .filter(File::exists)
											  			  .filter(p -> !p.getName().endsWith(".zip"))
											  			  .collect(Collectors.toList());

			/** MI CICLO I FILE PRESENTI NELLA LISTA PER INSERIRIRLI NEL ARCHIVIO .ZIP **/
			for (File file : listaFileDaZippare) {				
				ZipEntry zipEntry = new ZipEntry(file.getName());
				
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.write(Files.readAllBytes(file.toPath()));
				file.delete();
			}
			/** CREO IL DTO DI RISPOSTA **/
			ShapeResponseDTO shapeDTO = new ShapeResponseDTO();
			shapeDTO.setNameFile(archivioZip.getName());
			shapeDTO.setPathFile(archivioZip.toPath());
			shapeDTO.setByteShape(Base64Utils.encode(Files.readAllBytes(archivioZip.toPath())));
			
			return shapeDTO;
		} catch (Exception e) {
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(e);
			
		} finally {
			LOGGER.info(END, idLog);
			
		}
		
	}

	/**
	 * Metodo pubblico che serve per importare uno shape file, nel quale verranno estratti le geometrie e gli attrubuti.
	 * @return {@code List<ToponimoStradaleDTO>}, la lista con gli attributi e le geometrie recuperate dallo shape file
	 * @throws Exception
	 */
	public static List<ToponimoStradaleDTO> importShapeFile(Path pathImportShape) throws Exception {
		String idLog = "importShapeFile";
		LOGGER.info(START, idLog);
		
		try {
			/** CREO L'HASHMAP CONTENENTE L'URL DELLO SHAPE FILE **/
			Map<String, Object> map = new HashMap<>();
			map.put("url", pathImportShape.toFile().toURI().toURL());
			map.put("charset", StandardCharsets.UTF_8);
			
			/** CERCO UN DATASTORE PASSANDOGLI L'HASHMAP CON L'URL DELLO SHAPEFILE**/
//			ShapefileDataStore dataStore = (ShapefileDataStore) DataStoreFinder.getDataStore(map);
			DataStore dataStore = DataStoreFinder.getDataStore(map);
			String typeName = dataStore.getTypeNames()[0];
			
			FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
			Filter filter = Filter.INCLUDE;
			
			FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
			FeatureIterator<SimpleFeature> features = collection.features();

			/** CREO UNA LISTA CHE CONTIENE ToponimoStradaleDTO **/
			List<ToponimoStradaleDTO> listaToponimi = new ArrayList<>();
			/** CICLO FEATURE ITERATOR PER PROCEDERE AL RECUPERO DEGLI ATTRIBUT E DELLE GEOMETRIE **/
			while(features.hasNext()) {
				SimpleFeature feature = features.next();
				
				ToponimoStradaleDTO toponimoDTO = new ToponimoStradaleDTO();
				ComuniDto comune = new ComuniDto();
				DugDTO classe = new DugDTO();
				TipoToponimoDTO tipo = new TipoToponimoDTO();
				
				comune.setIdComune((Long) feature.getAttribute("comune"));
				
				classe.setId((Long) feature.getAttribute("classe"));
				
				tipo.setId((Long) feature.getAttribute("tipo"));
				
				toponimoDTO.setGeom(feature.getDefaultGeometryProperty().getValue().toString());
				toponimoDTO.setId((Long) feature.getAttribute("id"));
				toponimoDTO.setComuneLabel((String) feature.getAttribute("comuneLabl"));
				toponimoDTO.setDenominazione((String) feature.getAttribute("denominazi"));
				toponimoDTO.setDenominazioneUfficiale((String) feature.getAttribute("denominUff"));
				toponimoDTO.setClasseLabel((String) feature.getAttribute("classeLabl"));
				toponimoDTO.setShapeLeng(feature.getDefaultGeometryProperty().getValue().toString());
				toponimoDTO.setCap((String) feature.getAttribute("cap"));
				toponimoDTO.setCompendi((String) feature.getAttribute("compendi"));
				toponimoDTO.setPrecdenomi((String) feature.getAttribute("precdenomi"));
				toponimoDTO.setQuartiere((String) feature.getAttribute("quartiere"));
				toponimoDTO.setNumeroDelibera((String) feature.getAttribute("numDelib"));
				toponimoDTO.setDataDelibera((Date) feature.getAttribute("dataDelib"));
				toponimoDTO.setCodiceAutorizzazione((String) feature.getAttribute("codiceAuto"));
				toponimoDTO.setDataAutorizzazione((Date) feature.getAttribute("dataAuto"));
				toponimoDTO.setComune(comune);
				toponimoDTO.setClasse(classe);
				toponimoDTO.setNote((String) feature.getAttribute("note"));
				toponimoDTO.setTipo(tipo);
				toponimoDTO.setCodice((String) feature.getAttribute("codice"));
				toponimoDTO.setStato((String) feature.getAttribute("stato"));
				toponimoDTO.setIdPadre((Long) feature.getAttribute("idPadre"));
				toponimoDTO.setIsCircle((Boolean) feature.getAttribute("isCircle"));
				
				listaToponimi.add(toponimoDTO);
			}
			
			features.close();
			
			return listaToponimi;
		} catch (Exception e) {
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception("Impossibile importare lo shape file");
		
		} finally {
			LOGGER.info(END, idLog);
			
		}
		
	}

}
