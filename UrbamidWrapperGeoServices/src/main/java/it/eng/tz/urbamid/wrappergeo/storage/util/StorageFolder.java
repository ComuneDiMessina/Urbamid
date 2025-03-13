package it.eng.tz.urbamid.wrappergeo.storage.util;

import java.util.Optional;

public enum StorageFolder {

	//primo livello dopo la root
	DATI, 
	GEOKETTLE_JOB_FILE, 
	PYTHON_WORKSPACE,
	LOG,
	SCRIPT,
	
	//sotto directory di DATI
	AGGIORNAMENTO, 
	ATTUALITA,
	
	//sotto directory di AGGIORNAMENTO ed ATTUALITA
	CARTOGRAFIA,
	FABBRICATI,
	PLANIMETRIE,
	SHAPEFILE,
	SQL_SCRIPT,
	SQL_UPDATE_SCRIPT,
	TERRENI,
	
	STYLE;
	
	/**
	 * Metodo che restituisce il valore di una delle folder dove si effettua l'upload.
	 * Ricordiamo che l'upload è possibile sono in una di queste 4 cartelle:<br/>
	 * <ol>
	 *  <li><b>CARTOGRAFIA</b></li>
	 *  <li><b>FABBRICATI</b></li>
	 *  <li><b>PLANIMETRIE</b></li>
	 *  <li><b>TERRENI</b></li>
	 * </ol>
	 * 
	 * @param folderAsString è il valore della folder come stringa
	 *  
	 * @return
	 */
	public static Optional<StorageFolder> uploadFolderFromString(String folderAsString) {
		StorageFolder storageFolder = null;
		if( StorageFolder.CARTOGRAFIA.name().equalsIgnoreCase(folderAsString) ) {
			storageFolder = StorageFolder.CARTOGRAFIA;
		}
		if( StorageFolder.FABBRICATI.name().equalsIgnoreCase(folderAsString) ) {
			storageFolder = StorageFolder.FABBRICATI;
		}
		if( StorageFolder.PLANIMETRIE.name().equalsIgnoreCase(folderAsString) ) {
			storageFolder = StorageFolder.PLANIMETRIE;
		}
		if( StorageFolder.TERRENI.name().equalsIgnoreCase(folderAsString) ) {
			storageFolder = StorageFolder.TERRENI;
		}
		return Optional.ofNullable(storageFolder);
	}
	
}
