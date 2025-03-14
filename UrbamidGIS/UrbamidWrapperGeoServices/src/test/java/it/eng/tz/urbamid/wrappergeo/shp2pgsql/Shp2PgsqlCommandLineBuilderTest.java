package it.eng.tz.urbamid.wrappergeo.shp2pgsql;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Classe di test per shp2pgsql eseguito via Apache Common Exec
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Shp2PgsqlCommandLineBuilderTest {

	@Test
	public void shp2pgsql_test_1() {
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String shapefile = "/opt/IMPORT_CATASTO/DATI/ATTUALITA/SHAPEFILE/u_cat_Acque.shp";
			String schema = "public";
			String tabella = "tabella";
			String geometryColumn = "geom";
			String encoding = "UTF-8";
			CommandLine commandLine = CommandLine.parse("shp2pgsql ${MODE} ${SHAPEFILE} ${SCHEMA}.${TABELLA}");
			commandLine.addArguments(new String[]{"-D", "-g ${GEOMETRY_COLUMN}", "-W ${ENCODING}", "-I"});
			Map<String, Object> substitutionMap = new HashMap<>();
			substitutionMap.put("SHAPEFILE", shapefile);
			substitutionMap.put("SCHEMA", schema);
			substitutionMap.put("TABELLA", tabella);
			substitutionMap.put("GEOMETRY_COLUMN", geometryColumn);
			substitutionMap.put("ENCODING", encoding);
			substitutionMap.put("MODE", "-d");
			commandLine.setSubstitutionMap(substitutionMap );
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			//impostaMappaSostituzioneParametriComandoCreazioneScriptSQL(commandLine, shapefile, schema, tabella, srid, encoding);
			//L'output del comando viene rediretto sull'FileOutputStream in modo da creare lo script SQL
			DefaultExecutor executor = new DefaultExecutor();
			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			executor.setStreamHandler(streamHandler);
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
	
	@Test
	public void shp2pgsql_test_2() {
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String shapefile = "/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SHAPEFILE/u_cat_Acque.shp";
			String schema = "public";
			String tabella = "tabella";
			String geometryColumn = "geom";
			String encoding = "UTF-8";
			CommandLine commandLine = CommandLine.parse("shp2pgsql " + shapefile + " " + schema + "." + tabella);
			commandLine.addArguments(new String[]{"-a", "-D", "-g " + geometryColumn, "-W " + encoding, "-I"});
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			DefaultExecutor executor = new DefaultExecutor();
			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			executor.setStreamHandler(streamHandler);
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
	
	@Test
	public void shp2pgsql_test_3() {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String shapefile = "/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/DATI_1/SHAPEFILE/u_cat_Acque.shp";
			String schema = "public";
			String tabella = "tabella";
			String geometryColumn = "geom";
			String encoding = "UTF-8";
			
			CommandLine commandLine = 
					new Shp2PgsqlCommandLineBuilder(shapefile, schema, tabella)
						.encoding(encoding)
						.createSpatialIndexOnGeocolumn()
						.geocolumn(geometryColumn)
						.sqlStatementMode(Shp2PgsqlSQLStatementsMode.APPEND_INTO_CURRENT_TABLE)
						.build();
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			DefaultExecutor executor = new DefaultExecutor();
			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			executor.setStreamHandler(streamHandler);
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
	
}
