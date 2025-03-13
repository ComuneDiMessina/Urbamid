package it.eng.tz.urbamid.catasto.python.service;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.util.ImportType;

public interface PythonService {
	
	/**
	 * Esegue lo script SH che lancia la procedura python per la conversione dei file CXF in SHAPEFILE.
	 * N.B. tutti i parametri vengono dedotti dalle configurazioni sul database e/o dall'application.properties.
	 * 
	 * @return il log testuale prodotto dalla procedura.
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptPython(ImportType batchType) throws CatastoServiceException;

	/**
	 * Esegue lo script SH che lancia la procedura python per la conversione dei file CXF in SHAPEFILE.
	 * 
	 * @param scriptShPath path dello script sh
	 * @param cassiniSoldnerDirectory directory dove sono presenti i file CXF in proiezione Cassini/Soldner
	 * @param gaussBoagaDirectory directory dove sono presenti i file CXF in proiezione Gauss/Boaga
	 * @param pythonFile file python che esegue la conversione da CXF in SHAPEFILE
	 * @param shapefileOutputDirectory directory di output degli shapefile
	 * @param shapefilePrefix prefisso per gli SHAPEFILE prodotti
	 * 
	 * @return il log testuale prodotto dalla procedura.
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptPython(String scriptShPath, String cassiniSoldnerDirectory, String gaussBoagaDirectory,
			String pythonFile, String shapefileOutputDirectory, String shapefilePrefix) throws CatastoServiceException;

}
