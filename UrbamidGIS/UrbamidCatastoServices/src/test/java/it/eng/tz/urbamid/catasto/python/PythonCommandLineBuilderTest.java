package it.eng.tz.urbamid.catasto.python;

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

import it.eng.tz.urbamid.catasto.python.util.PythonScriptCommandBuilder;

/**
 * Classe di test per shp2pgsql eseguito via Apache Common Exec
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PythonCommandLineBuilderTest {

	
	@Test
	public void shp2pgsql_test_1() {
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			CommandLine commandLine = CommandLine.parse("/home/fesposit/Scrivania/pythonscript.sh ${PYTHON_SCRIPT_FILE} ${CASSINI_SOLDNER_DIRECTORY} ${GAUSS_BOAGA_DIRECTORY} ${SHAPEFILE_DIRECOTY} ${SHAPEFILE_PREFIX}");
			Map<String, String> substitutionMap = new HashMap<>();
			substitutionMap.put("PYTHON_SCRIPT_FILE", "/opt/IMPORT_CATASTO/PYTHON_WORKSPACE/CXFToShape/src/cxfToShape.py");
			substitutionMap.put("CASSINI_SOLDNER_DIRECTORY", "/opt/IMPORT_CATASTO/DATI/ATTUALITA/MANAGE/CATASTO/work/Cassini-Soldner");
			substitutionMap.put("GAUSS_BOAGA_DIRECTORY", "/opt/IMPORT_CATASTO/DATI/ATTUALITA/MANAGE/CATASTO/work/Gauss-Boaga");
			substitutionMap.put("SHAPEFILE_DIRECOTY", "/opt/IMPORT_CATASTO/DATI/ATTUALITA/SHAPEFILE/");
			substitutionMap.put("SHAPEFILE_PREFIX", "u_cat_");
			commandLine.setSubstitutionMap(substitutionMap );
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
			
			CommandLine commandLine = new PythonScriptCommandBuilder("/home/fesposit/Scrivania/")
					.cassiniSoldnerCXFDirectory("/opt/IMPORT_CATASTO/DATI/ATTUALITA/MANAGE/CATASTO/work/Cassini-Soldner")
					.gaussBoagaCXFDirectory("/opt/IMPORT_CATASTO/DATI/ATTUALITA/MANAGE/CATASTO/work/Gauss-Boaga")
					.pythonFile("/opt/IMPORT_CATASTO/PYTHON_WORKSPACE/CXFToShape/src/cxfToShape.py")
					.shapefileOutputDirectory("/opt/IMPORT_CATASTO/DATI/ATTUALITA/SHAPEFILE/")
					.shapefilePrefix("u_cat_")
						.build();
			
			DefaultExecutor executor = new DefaultExecutor();
			final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
			executor.setStreamHandler(streamHandler);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
			executor.setWatchdog(watchdog);
			
			System.out.println("EXECUTABLE: " + commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				System.out.println("ARGOMENTO: " + arg);
			}
			
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
