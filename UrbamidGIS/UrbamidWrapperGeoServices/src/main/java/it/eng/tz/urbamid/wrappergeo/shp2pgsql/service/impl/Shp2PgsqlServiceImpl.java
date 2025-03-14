package it.eng.tz.urbamid.wrappergeo.shp2pgsql.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.wrappergeo.exception.Shp2PostgisException;
import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.shp2pgsql.Shp2PgsqlCommandLineBuilder;
import it.eng.tz.urbamid.wrappergeo.shp2pgsql.Shp2PgsqlSQLStatementsMode;
import it.eng.tz.urbamid.wrappergeo.shp2pgsql.bean.ReprojectBean;
import it.eng.tz.urbamid.wrappergeo.shp2pgsql.service.Shp2PgsqlService;
import it.eng.tz.urbamid.wrappergeo.storage.service.WrapperPathService;
import it.eng.tz.urbamid.wrappergeo.storage.util.PrintingLogOutputStream;

@Service
public class Shp2PgsqlServiceImpl implements Shp2PgsqlService {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql
	 */
	private final WrapperPathService pathService;
	
	public Shp2PgsqlServiceImpl( WrapperPathService pathService ) {
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.pathService = pathService;
	}
	
	@Override
	public String convertiShapefileInScriptSQL(String nameShapeFile, String shapefile, String schema, String table, String geometryColumnName) 
			throws WrapperGeoServiceException {
		return this.convertiShapefileInScriptSQL(nameShapeFile, new File(shapefile), schema, table, geometryColumnName);
	}
	
	@Override
	public String convertiShapefileInScriptSQL(String nameShapeFile, File shapefile, String schema, String table, String geometryColumnName) 
			throws WrapperGeoServiceException {
		
		long inizio = new Date().getTime()/1000;
		if(LOG.isDebugEnabled())
			LOG.debug("Eseguo lo script shp2pgsql per lo shapefile {}.", shapefile);
		try(FileOutputStream fileOutputStream = new FileOutputStream( this.getOutputSqlFile(nameShapeFile, table))) {
			
			//lo statement mode deve essere di append per l'AGGIORNAMENTO ed il drop per l'ATTUALITA
			Shp2PgsqlSQLStatementsMode sqlStatementsMode = Shp2PgsqlSQLStatementsMode.DROP_AND_RECREATE_TABLE;
			//ora si va sempre in drop and recreate
			CommandLine commandLine = new Shp2PgsqlCommandLineBuilder(shapefile, schema, table)
					.sqlStatementMode(sqlStatementsMode)
					.encoding(Shp2PgsqlCommandLineBuilder.DEFAULT_ENCODING)
					.createSpatialIndexOnGeocolumn()
					.geocolumn(geometryColumnName)
//					.srid(Shp2PgsqlCommandLineBuilder.DEFAULT_SRID)
					.build();
			
			LOG.info(":::::::::::::::::########	"+commandLine.toString());
			
			PrintingLogOutputStream printingLogOutputStream = new PrintingLogOutputStream();
			PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(fileOutputStream, printingLogOutputStream);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(pumpStreamHandler);
			//42 è la risposta alla vita, l'universo e tutto quanto
			ExecuteWatchdog watchdog = new ExecuteWatchdog(42_0__0___0____0_____0);
			executor.setWatchdog(watchdog);
			executor.execute(commandLine);
			fileOutputStream.flush();
			if(LOG.isDebugEnabled()) {
				long fine = new Date().getTime() / 1_0__0___0;
				LOG.debug("Eseguito con successo lo script shp2pgsql per il file {} in {} secondi.", 
						shapefile, fine-inizio);
			}
			return printingLogOutputStream.getOutput();
		} catch (FileNotFoundException fnf) {
			LOG.error("Si è verificato un <<FileNotFoundException>> nell'apertura del file.");
			throw new Shp2PostgisException(fnf);
		} catch (IOException ioe) {
			LOG.error("Si è verificato un <<IOException>> durante l'esecuzione di shp2pgsql.");
			throw new Shp2PostgisException(ioe);
		}
		
	}
	
	@Override
	public String reproject (String nameShapeFile, File shapefile, String schema, String table, String geometryColumnName) throws WrapperGeoServiceException {
		
		Path rootFileReproject = Paths.get(this.pathService.sqlScriptDirectory(nameShapeFile));
		StringBuilder informazioniDiLog = new StringBuilder();
		if(LOG.isDebugEnabled()) {
			LOG.debug("Riproietto la geometria 'geom' in EPSG:4326 per aggiornare la tabella.");
		}
		
		List<ReprojectBean> reprojectList = new ArrayList<ReprojectBean>();
		try {
			
			ShapefileDataStore myShape = new ShapefileDataStore(shapefile.toURL());
			CoordinateReferenceSystem sourceCRS = CRS.parseWKT(myShape.getSchema().getCoordinateReferenceSystem().toString());
			CoordinateReferenceSystem targetCRS = CRS.parseWKT("GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]]");
			SimpleFeatureIterator itr  = myShape.getFeatureSource().getFeatures().features();
	        SimpleFeature feature;
	        long i = 1;
	        while (itr .hasNext()) {
	        	feature = itr .next();
	            Geometry sourceGeometry = (Geometry) feature.getDefaultGeometry();
	            MathTransform mTrans = CRS.findMathTransform(sourceCRS, targetCRS);
	            
	            String wkt = new String("");
	            if (!mTrans.isIdentity()) {
	            	wkt =  JTS.transform(sourceGeometry, mTrans).toString();
	            } 
	            ReprojectBean rbean = new ReprojectBean( );
	            rbean.setGid( i );
	            rbean.setWkt( wkt);
	            reprojectList.add(rbean);i++;
	        }
	        itr.close();
	        myShape.dispose();
	        informazioniDiLog.append("Lo shape " +nameShapeFile +" è stato riproiettato in EPSG:4326." );
		} catch (Exception e) {
			e.printStackTrace();
		}
	        
		if (!reprojectList.isEmpty()) {
	        	
    		PrintStream printStream=null;
    		try(FileOutputStream fileOutputStream = new FileOutputStream( this.getOutputUpdateSqlFile(nameShapeFile, table))) {
    			
    			printStream=new PrintStream(fileOutputStream);
    			String HEADER_UPDATE_SQL = "SET CLIENT_ENCODING TO UTF8;";
    			printStream.println(HEADER_UPDATE_SQL);
    			HEADER_UPDATE_SQL = "SET STANDARD_CONFORMING_STRINGS TO ON;";
    			printStream.println(HEADER_UPDATE_SQL);
		        for (ReprojectBean rBean : reprojectList){
		        	String UPDATE_SQL = "UPDATE "+schema+"."+table+" SET "+geometryColumnName+"=ST_GeomFromText('"+rBean.getWkt()+"', 4326) WHERE gid="+rBean.getGid()+";";
		        	printStream.println(UPDATE_SQL);
		        }
		        String FOOTER_UPDATE_SQL = "COMMIT;";
	    		printStream.println(FOOTER_UPDATE_SQL);
    		} catch (Exception e) {
    			
    			e.printStackTrace();
    		}
	        informazioniDiLog.append("Lo shape " +nameShapeFile +" contiente "+reprojectList.size()+" feature." );
		}
		return informazioniDiLog.toString();
		
		
	}

	/**
	 * Metodo privato che crea un oggetto {@link File} che verrà usato per scrivere i dati di output del comando shp2pgsql e,
	 * quindi, creare lo script SQL.
	 * N.B. Casomai il file dovesse esistere, viene cancellato preventivamente.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA). Serve per risalire al path dove creare il file.
	 * @param nomeFile è il nome del file SQL
	 * 
	 * @return {@link File}
	 * 
	 * @throws WrapperGeoServiceException
	 */
	private File getOutputSqlFile(String nameShapeFile, String nomeFile) throws WrapperGeoServiceException {
		
		File outputSqlFile = Paths.get(
				this.pathService.sqlScriptDirectory(nameShapeFile),
				"INSERT_"+nomeFile+".sql")
					.toFile();
		if(outputSqlFile.exists()) {
			try {
				Files.delete(outputSqlFile.toPath());
			} catch(IOException ioe ) {
				LOG.error("Si è verificato un errore di I/O durante la cancellazione del file {}.", outputSqlFile.getName());
				throw new WrapperGeoServiceException(ioe);
			}
		}
		return outputSqlFile;
	}
	
	/**
	 * Metodo privato che crea un oggetto {@link File} che verrà usato per scrivere i dati di output del comando shp2pgsql e,
	 * quindi, creare lo script SQL.
	 * N.B. Casomai il file dovesse esistere, viene cancellato preventivamente.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA). Serve per risalire al path dove creare il file.
	 * @param nomeFile è il nome del file SQL
	 * 
	 * @return {@link File}
	 * 
	 * @throws WrapperGeoServiceException
	 */
	private File getOutputUpdateSqlFile(String nameShapeFile, String nomeFile) throws WrapperGeoServiceException {
		
		File outputSqlFile = Paths.get(
				this.pathService.sqlScriptDirectory(nameShapeFile),
				"UPDATE_"+nomeFile+".sql")
					.toFile();
		if(outputSqlFile.exists()) {
			try {
				Files.delete(outputSqlFile.toPath());
			} catch(IOException ioe ) {
				LOG.error("Si è verificato un errore di I/O durante la cancellazione del file {}.", outputSqlFile.getName());
				throw new WrapperGeoServiceException(ioe);
			}
		}
		return outputSqlFile;
	}

	public WrapperPathService getPathService() {
		return pathService;
	}

}
