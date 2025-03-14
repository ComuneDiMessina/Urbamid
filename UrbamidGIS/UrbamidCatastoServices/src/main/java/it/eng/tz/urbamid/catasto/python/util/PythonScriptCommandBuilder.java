package it.eng.tz.urbamid.catasto.python.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Classe Builder per la generazione di un {@link CommandLine} per l'esecuzione dello script SH che serve
 * per lanciare la procedura python (da GeoKettle dava problemi).
 */
public class PythonScriptCommandBuilder {

	/**
	 * Nome dello script SH da lanciare
	 */
	private static final String PYTHON_SCRIPT_EXECUTABLE = "pythonscript.sh";
	
	/**
	 * Argomenti previsti dallo script (tutti obbligatori a parte lo shapefile prefix che se non specificato viene usato quello di default).
	 */
	private static final String PYTHON_ARGUMENTS = " ${PYTHON_SCRIPT_FILE} ${CASSINI_SOLDNER_DIRECTORY} ${GAUSS_BOAGA_DIRECTORY} ${SHAPEFILE_DIRECTORY} ${SHAPEFILE_PREFIX}";
	
	/**
	 * Prefisso di default per gli shapefile
	 */
	private static final String DEFAULT_SHAPEFILE_PREFIX = "u_cat_";
	
	/**
	 * Directory dello script SH. Questo path viene concatenato al valore del nome dello script sh
	 * per costruire l'executable string della CommandLine
	 */
	private String directoryScriptSH;
	
	/**
	 * Mappa di sostituzione degli argomenti
	 */
	protected Map<String, Object> substitutionMap;
	
	/**
	 * Costruttore.
	 * 
	 * @param directoryScriptSH è la directory dello script SH.
	 */
	public PythonScriptCommandBuilder(String directoryScriptSH) {
		super();
		Assert.hasLength(directoryScriptSH, "Script sh path MUST not be empty!");
		this.directoryScriptSH = directoryScriptSH;
		this.substitutionMap = new HashMap<>();
		this.substitutionMap.put(PythonScriptArgument.SHAPEFILE_PREFIX.name(), DEFAULT_SHAPEFILE_PREFIX);
	}
	
	/**
	 * Imposta l'argomento per specificare la locazione del file python (.py) che svolgerà la conversione dei file
	 * CXF in SHAPEFILE.
	 * 
	 * @param pythonFile è la procedura python da eseguire.
	 * 
	 * @return {@link PythonScriptCommandBuilder}
	 */
	public PythonScriptCommandBuilder pythonFile(String pythonFile) {
		Assert.hasLength(pythonFile, "Python file MUST not be empty!");
		this.substitutionMap.put(PythonScriptArgument.PYTHON_SCRIPT_FILE.name(), pythonFile);
		return this;
	}
	
	/**
	 * Imposta l'argomento per specificare il path dove sono presenti i file CXF in proiezione Cassini/Soldner.
	 * 
	 * @param cassiniSoldnerCXFDirectory è la directory dove sono presenti i file CXF in proiezione Cassini/Soldner.
	 * 
	 * @return {@link PythonScriptCommandBuilder}
	 */
	public PythonScriptCommandBuilder cassiniSoldnerCXFDirectory(String cassiniSoldnerCXFDirectory) {
		Assert.hasLength(cassiniSoldnerCXFDirectory, "Cassini/Soldner directory MUST not be empty!");
		this.substitutionMap.put(PythonScriptArgument.CASSINI_SOLDNER_DIRECTORY.name(), cassiniSoldnerCXFDirectory);
		return this;
	}
	
	/**
	 * Imposta l'argomento per specificare il path dove sono presenti i file CXF in proiezione Gauss/Boaga.
	 * 
	 * @param gaussBoagaCXFDirectory è la directory dove sono presenti i file CXF in proiezione Gauss/Boaga.
	 * 
	 * @return {@link PythonScriptCommandBuilder}
	 */
	public PythonScriptCommandBuilder gaussBoagaCXFDirectory(String gaussBoagaCXFDirectory) {
		Assert.hasLength(gaussBoagaCXFDirectory, "Gauss/Boaga directory MUST not be empty!");
		this.substitutionMap.put(PythonScriptArgument.GAUSS_BOAGA_DIRECTORY.name(), gaussBoagaCXFDirectory);
		return this;
	}
	
	/**
	 * Imposta l'argomento per specificare la directory di output degli shapefile generati dalla procedura python.
	 * 
	 * @param shapefileOutputDirectory è la directory di output degli shapefile generati dalla procedura python.
	 * 
	 * @return {@link PythonScriptCommandBuilder}
	 */
	public PythonScriptCommandBuilder shapefileOutputDirectory(String shapefileOutputDirectory) {
		Assert.hasLength(shapefileOutputDirectory, "Shapefile directory MUST not be empty!");
		this.substitutionMap.put(PythonScriptArgument.SHAPEFILE_DIRECTORY.name(), shapefileOutputDirectory);
		return this;
	}
	
	/**
	 * Imposta l'argomento per specificare il prefisso degli shapefile generati dalla procedura python.
	 * 
	 * @param shapefilePrefix è il prefisso degli shapefile che vengono generati dalla procedura python.
	 * 
	 * @return {@link PythonScriptCommandBuilder}
	 */
	public PythonScriptCommandBuilder shapefilePrefix(String shapefilePrefix) {
		if( StringUtils.hasLength(shapefilePrefix) ) {
			if(this.substitutionMap.containsKey(PythonScriptArgument.SHAPEFILE_PREFIX.name())) {
				this.substitutionMap.replace(PythonScriptArgument.SHAPEFILE_PREFIX.name(), shapefilePrefix);
			} else {
				this.substitutionMap.put(PythonScriptArgument.SHAPEFILE_PREFIX.name(), shapefilePrefix);
			}
		}
		return this;
	}
	
	/**
	 * Costruisce la {@link CommandLine} per lanciare lo script.
	 * 
	 * @return {@link CommandLine}
	 */
	public CommandLine build() {
		File file = Paths.get(this.directoryScriptSH, PythonScriptCommandBuilder.PYTHON_SCRIPT_EXECUTABLE).toFile();
		Assert.isTrue(file.exists(), "Python script file MUST exists in order to execute it!");
		String line = file.getAbsolutePath().concat(PythonScriptCommandBuilder.PYTHON_ARGUMENTS);
		CommandLine commandLine = CommandLine.parse(line);
		Assert.isTrue( this.substitutionMap.size() == PythonScriptArgument.values().length, "You MUST set all the script arguments in order to execute it!");
		commandLine.setSubstitutionMap(this.substitutionMap);
		return commandLine;
	}
	
}
