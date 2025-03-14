package it.eng.tz.urbamid.catasto.shapefile.service;

import java.io.File;
import java.util.List;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;

public interface ShapefileService {
	
	/**
	 * Metodo che si occupa dell'import di tutti gli shapefile nel DB.
	 * Vengono elaborati gli shapefile nella relativa cartella del file system condiviso e lanciati
	 * due script tramite apache common exec: il primo si occupa di convertire lo shapefile in un file
	 * sql ed il secondo di eseguire gli statement del file sql per l'import dei dati a DB.
	 * 
	 * @param importType è il tipo di import (aggiornamento/attualita). Viene utilizzato per risalire alla
	 * cartella dove risiedono gli shapefile.
	 * 
	 * @return una stringa con le informazioni di log.
	 * 
	 * @throws CatastoServiceException
	 */
	String importaShapefile(ImportType importType) throws CatastoServiceException;
	
	
	File exportShapefile(List<ParticellaCustomDTO> lsParticelle, String titolo) throws CatastoServiceException;
}
