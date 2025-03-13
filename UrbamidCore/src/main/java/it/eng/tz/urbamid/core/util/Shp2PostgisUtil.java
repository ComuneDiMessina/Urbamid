/**
 * 
 */
package it.eng.tz.urbamid.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class Shp2PostgisUtil {

	//private static final Logger logger = LogManager.getLogger(Shp2PostgisUtil.class);
	
	//private static final String OUTPUT_TEMPORARY_SQL_FOLDER = "/home/notroot/test";
	
	/**
	 * Converts a source Shapefile to SQL
	 * 
	 * @param sourceShape
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static File shp2Pgsql(File sourceShape, String schemaName, String tableName, String srid, String encode, boolean dropsTheTableIfAlreadyExists) 
//			throws Shp2PostgisException 
	{
		
		//Output stream (sql file)
		//Remove spaces, all lowercase, etc...
		String outputTemporaryFolder = sourceShape.getParent();
		String outputSqlFilename = outputTemporaryFolder + "/" + schemaName.trim().toLowerCase() 
				+ "_" + tableName.trim().toLowerCase() + new Date().getTime() + ".sql";		
		
		File outputSqlFile = new File(outputSqlFilename);
		FileOutputStream fos = null;
		
//		try {
//			
//			fos = new FileOutputStream(outputSqlFile);
//			
//			//Execution command
//			//String line = "shp2pgsql -c -g my_geom -W UTF-8 ${sourceShape} ${schema}.${table}";
//			//String line = "shp2pgsql -c -g my_geom -W LATIN1 -s ${srid} ${sourceShape} ${schema}.${table}";
//			//TODO aggiungere -I per indice GIST
//			String line = "shp2pgsql";
//			
//			if (dropsTheTableIfAlreadyExists) {
//				line +=" -d ";
//			}
//			else {
//				line +=" -c ";
//			}
//			line +="-g geom -W ${encode} -I -s ${srid} ${sourceShape} ${schema}.${table}";
//			
//			CommandLine cmdLine = CommandLine.parse(line);
//			Map<String, Object> map = new HashMap<String, Object>();
//
//			map.put("sourceShape", sourceShape);
//	    	map.put("schema", schemaName);
//	    	map.put("table", tableName);
//	    	map.put("encode", encode);
//	    	map.put("srid", srid);
//	    	
//	    	cmdLine.setSubstitutionMap(map);
//			
//	    	logger.debug("Converting SHP -> SQL (from " + sourceShape.toString() + " -> " + outputSqlFilename);
//			
//	    	/*
//	    	 * Redirect command output to FileOutputStream
//	    	 */
//	    	ByteArrayOutputStream stderr = new ByteArrayOutputStream();
//	    	PumpStreamHandler psh = new PumpStreamHandler(fos, stderr);
//			DefaultExecutor executor = new DefaultExecutor();
//			executor.setStreamHandler(psh);
//			
//			/*
//			 * Set a watchdog
//			 * 120  seconds
//			 */
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(120 * 1000);
//			executor.setWatchdog(watchdog);
//			
//			try {
//				
//				/*
//				 * Execute command
//				 */
//				executor.execute(cmdLine);
//				
//				//Flush the file...
//				fos.flush();
//				
//			} catch (ExecuteException ee) {
//				logger.error("Error executing shp2pgsql to convert file " + tableName, ee);
//				
//				String shp2postgisError = stderr.toString();
//				String err = Shp2PostgisException.tryParseError(shp2postgisError);
//				throw new Shp2PostgisException(err);
//			} 
//			return outputSqlFile;
//			
//		}  
//		catch (FileNotFoundException fox) {
//			logger.error("Error opening file: " + outputSqlFile.getAbsolutePath());
//			throw new Shp2PostgisException("Errore I/O");
//		}
//		catch (IOException iox) {
//			logger.error("Error executing shp2pgsql to convert file " + tableName, iox);
//			throw new Shp2PostgisException("Errore I/O");
//		}
//		finally {
//			if (fos != null) {
//				//Close the file
//				IOUtils.closeQuietly(fos);
//			}
//		}
		return null;
	}
	
	public static void loadSqlIntoPostgisPsql(File sourceSql, String dbHost, 
			String databaseName, String user, String pwd) throws Exception {
		
//		//Execution command
//		
//		/*
//		 * Attenzione!!!
//		 * Password settata in TRUST per questo utente!!!!
//		 */
//		//String line = "PGPASSWORD=${pwd} psql -h ${dbHost} -U ${username} -d ${databaseName} -f ${sourceSql}";
//		String line = "psql -h ${dbHost} -U ${username} -d ${databaseName} -w -f ${sourceSql}";
//		
//		CommandLine cmdLine = CommandLine.parse(line);
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("sourceSql", sourceSql);
//    	map.put("dbHost", dbHost);
//    	map.put("username", user);
//    	map.put("databaseName", databaseName);
//    	map.put("pwd", pwd);
//    	
//    	cmdLine.setSubstitutionMap(map);
//		
//    	logger.debug("Loading SQL -> Postgis (from " + sourceSql.toString() + " -> " + user + "@" + dbHost + ":" + databaseName);
//		
//    	DefaultExecutor executor = new DefaultExecutor();
//    	
//    	/*
//		 * Set a watchdog
//		 * 60 seconds
//		 */
//		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
//		executor.setWatchdog(watchdog);
//		
//		/*
//		 * Execute command
//		 */
//		executor.execute(cmdLine);
//		
	}
	
}
