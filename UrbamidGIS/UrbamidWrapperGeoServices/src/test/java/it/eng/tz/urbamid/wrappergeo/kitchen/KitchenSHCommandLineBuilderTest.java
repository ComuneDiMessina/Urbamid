package it.eng.tz.urbamid.wrappergeo.kitchen;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.eng.tz.urbamid.wrappergeo.geokettle.util.GeoKettleJobImportParameter;
import it.eng.tz.urbamid.wrappergeo.geokettle.util.KitchenSHCommandLineBuilder;
import it.eng.tz.urbamid.wrappergeo.geokettle.util.KitchenScriptStatusCode;

/**
 * Classe di test per kitchen.sh eseguito via Apache Common Exec
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KitchenSHCommandLineBuilderTest {

	/*
	 
	Obiettivo è lanciare comandi di questo tipo: 
	 
	~/GeoKettle/kitchen.sh 
	-norep 
	-param:DATABASE_NAME=URBAMID 
	-param:DATABASE_PORT=5432 
	-param:DATABASE_USERNAME=postgres 
	-param:DATABASE_PASSWORD=postgres 
	-param:DATABASE_HOST=localhost
	-param:BASE_JOB_DIRECTORY=/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO
	-file=/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/JOB_IMPORT_CATASTO.kjb
	
	*/
	
	/**
	 * Test 1... stampa help
	 */
	@Test
	public void psql_test_1() {
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CommandLine commandLine = new KitchenSHCommandLineBuilder("/home/fesposit/GeoKettle")
				.version()
					.build();
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			DefaultExecutor executor = new DefaultExecutor();
			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			executor.setStreamHandler(streamHandler);
			executor.setExitValues(KitchenScriptStatusCode.statiOK());
			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
			executor.setWatchdog(watchdog);
			int res = executor.execute(commandLine);
			outputStream.flush();
			System.out.println(outputStream.toString());
			assertTrue(res == 0);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false); //XD
		} 
	}
//	
//	@Test
//	public void psql_test_2() {
//		try {
//			
//			//System.setProperty("PGPASSWORD", "postgres");
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			CommandLine commandLine = CommandLine.parse(
//					"psql -h localhost -U postgres -d dbUrbamid -w -f /home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SQL_SCRIPT/test.sql");
//			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
//			for(String arg : commandLine.getArguments()) {
//				System.out.println("ARGOMENTO: " + arg);
//			}
//			//impostaMappaSostituzioneParametriComandoCreazioneScriptSQL(commandLine, shapefile, schema, tabella, srid, encoding);
//			//L'output del comando viene rediretto sull'FileOutputStream in modo da creare lo script SQL
//			DefaultExecutor executor = new DefaultExecutor();
//			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
//			executor.setStreamHandler(streamHandler);
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
//			executor.setWatchdog(watchdog);
//			int res = executor.execute(commandLine);
//			outputStream.flush();
//			System.out.println(outputStream.toString());
//			assertTrue(res == 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//			assertTrue(false); //XD
//		} 
//	}
//	
//	
//	@Test
//	public void psql_test_3() {
//		ByteArrayOutputStream outputStreamError = new ByteArrayOutputStream();
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		
//		try {
//			
//			//PSQL è un po' particolare... gli argomenti li vuole separati
//			
//			CommandLine commandLine = CommandLine.parse("psql");	
//			commandLine.addArgument("-U", false);
//			commandLine.addArgument("postgres", false);
//			commandLine.addArgument("-h", false);
//			commandLine.addArgument("localhost", false);
//			commandLine.addArgument("-p", false);
//			commandLine.addArgument("5432", false);
//			commandLine.addArgument("-d", false);
//			commandLine.addArgument("dbUrbamid", false);
//			commandLine.addArgument("-w", false);
//			commandLine.addArgument("-f", false);
//			commandLine.addArgument("/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SQL_SCRIPT/test.sql", false);
//			
//			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
//			for(String arg : commandLine.getArguments()) {
//				System.out.println("ARGOMENTO: " + arg);
//			}
//			//impostaMappaSostituzioneParametriComandoCreazioneScriptSQL(commandLine, shapefile, schema, tabella, srid, encoding);
//			//L'output del comando viene rediretto sull'FileOutputStream in modo da creare lo script SQL
//			DefaultExecutor executor = new DefaultExecutor();
//			
//	    	final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, outputStreamError);
//			executor.setStreamHandler(streamHandler);
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
//			executor.setWatchdog(watchdog);
//			int res = executor.execute(commandLine);
//			outputStream.flush();
//			System.out.println(outputStream.toString());
//			assertTrue(res == 0);
//		} catch (Exception e) {
//			String error = outputStreamError.toString();
//			System.out.println(error);
//			e.printStackTrace();
//			assertTrue(false); //XD
//		} 
//	}
//	
//	@Test
//	public void psql_test_4() {
//		ByteArrayOutputStream outputStreamError = new ByteArrayOutputStream();
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		
//		try {
//			
//			//PSQL è un po' particolare... gli argomenti li vuole separati... 
//			
//			CommandLine commandLine = CommandLine.parse("psql");	
//			commandLine.addArguments(new String[] {"-f", 
//					"${FILE}",
//					"-h", "localhost", "-p", "5432", "-d", "dbUrbamid", "-w", "-U", "postgres",});
//			Map<String, Object> substitutionMap = new HashMap<>();
//			//substitutionMap.put("FILE", "/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SQL_SCRIPT/test.sql");
//			
//			substitutionMap.put("FILE", new File("/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SQL_SCRIPT/test.sql"));
//			
//			commandLine.setSubstitutionMap(substitutionMap);
//			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
//			for(String arg : commandLine.getArguments()) {
//				System.out.println("ARGOMENTO: " + arg);
//			}
//			//impostaMappaSostituzioneParametriComandoCreazioneScriptSQL(commandLine, shapefile, schema, tabella, srid, encoding);
//			//L'output del comando viene rediretto sull'FileOutputStream in modo da creare lo script SQL
//			DefaultExecutor executor = new DefaultExecutor();
//			
//	    	final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, outputStreamError);
//			executor.setStreamHandler(streamHandler);
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
//			executor.setWatchdog(watchdog);
//			int res = executor.execute(commandLine);
//			outputStream.flush();
//			System.out.println(outputStream.toString());
//			assertTrue(res == 0);
//		} catch (Exception e) {
//			String error = outputStreamError.toString();
//			System.out.println(error);
//			e.printStackTrace();
//			assertTrue(false); //XD
//		} 
//	}
	
	@Test
	public void psql_test_2() {
		
		ByteArrayOutputStream outputStreamError = new ByteArrayOutputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			
			CommandLine commandLine = new KitchenSHCommandLineBuilder("/home/fesposit/GeoKettle")
					.version()
						.build();
			
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			DefaultExecutor executor = new DefaultExecutor();
	    	final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, outputStreamError);
			executor.setStreamHandler(streamHandler);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
			executor.setExitValues(KitchenScriptStatusCode.statiOK());
			executor.setWatchdog(watchdog);
			int res = executor.execute(commandLine); 
			outputStream.flush();
			System.out.println(outputStream.toString());
			assertTrue(res == 0);
		} catch (Exception e) {
			String error = outputStreamError.toString();
			System.out.println(error);
			e.printStackTrace();
			assertTrue(false); //XD
		} 
	}
	
	
	@Test
	public void psql_test_5() {
		
		ByteArrayOutputStream outputStreamError = new ByteArrayOutputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			
			/*
			 
			Obiettivo è lanciare comandi di questo tipo: 
			 
			~/GeoKettle/kitchen.sh 
			-norep 
			-param:DATABASE_NAME=URBAMID 
			-param:DATABASE_PORT=5432 
			-param:DATABASE_USERNAME=postgres 
			-param:DATABASE_PASSWORD=postgres 
			-param:DATABASE_HOST=localhost
			-param:BASE_JOB_DIRECTORY=/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO
			-file=/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/JOB_IMPORT_CATASTO.kjb
			
			*/
			
			
			
			CommandLine commandLine = new KitchenSHCommandLineBuilder("/home/fesposit/GeoKettle")
					.noRepositories()
					.parameter(GeoKettleJobImportParameter.DATABASE_NAME, "dbUrbamid")
					.parameter(GeoKettleJobImportParameter.DATABASE_PORT, "5432")
					.parameter(GeoKettleJobImportParameter.DATABASE_USER, "postgres")
					.parameter(GeoKettleJobImportParameter.DATABASE_PASSWORD, "postgres")
					.parameter(GeoKettleJobImportParameter.DATABASE_HOST, "localhost")
					.parameter(GeoKettleJobImportParameter.BASE_JOB_DIRECTORY, "/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO")
					.file("/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/JOB_IMPORT_CATASTO.kjb")
						.build();
			
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			DefaultExecutor executor = new DefaultExecutor();
	    	final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, outputStreamError);
			executor.setStreamHandler(streamHandler);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
			executor.setExitValues(KitchenScriptStatusCode.statiOK());
			executor.setWatchdog(watchdog);
			int res = executor.execute(commandLine); 
			outputStream.flush();
			System.out.println(outputStream.toString());
			assertTrue(res == 0);
		} catch (Exception e) {
			String error = outputStreamError.toString();
			System.out.println(error);
			e.printStackTrace();
			assertTrue(false); //XD
		} 
	}
	
	
//	
//	@Test
//	public void shp2pgsql_test_2() {
//		try {
//			
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			String shapefile = "/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SHAPEFILE/u_cat_Acque.shp";
//			String schema = "public";
//			String tabella = "tabella";
//			String geometryColumn = "geom";
//			String encoding = "UTF-8";
//			CommandLine commandLine = CommandLine.parse("shp2pgsql " + shapefile + " " + schema + "." + tabella);
//			commandLine.addArguments(new String[]{"-a", "-D", "-g " + geometryColumn, "-W " + encoding, "-I"});
//			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
//			for(String arg : commandLine.getArguments()) {
//				System.out.println("ARGOMENTO: " + arg);
//			}
//			DefaultExecutor executor = new DefaultExecutor();
//			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
//			executor.setStreamHandler(streamHandler);
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
//			executor.setWatchdog(watchdog);
//			int res = executor.execute(commandLine);
//			outputStream.flush();
//			System.out.println(outputStream.toString());
//			assertTrue(res == 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//			assertTrue(false); //XD
//		} 
//	}
//	
//	@Test
//	public void shp2pgsql_test_3() {
//		try {
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			String shapefile = "/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SHAPEFILE/u_cat_Acque.shp";
//			String schema = "public";
//			String tabella = "tabella";
//			String geometryColumn = "geom";
//			String encoding = "UTF-8";
//			
//			CommandLine commandLine = 
//					new Shp2PgsqlCommandLineBuilder(shapefile, schema, tabella)
//						.encoding(encoding)
//						.createSpatialIndexOnGeocolumn()
//						.geocolumn(geometryColumn)
//						.sqlStatementMode(Shp2PgsqlSQLStatementsMode.APPEND_INTO_CURRENT_TABLE)
//						.build();
//			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
//			for(String arg : commandLine.getArguments()) {
//				System.out.println("ARGOMENTO: " + arg);
//			}
//			DefaultExecutor executor = new DefaultExecutor();
//			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
//			executor.setStreamHandler(streamHandler);
//			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
//			executor.setWatchdog(watchdog);
//			int res = executor.execute(commandLine);
//			outputStream.flush();
//			System.out.println(outputStream.toString());
//			assertTrue(res == 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//			assertTrue(false); //XD
//		} 
//	}
	
}
