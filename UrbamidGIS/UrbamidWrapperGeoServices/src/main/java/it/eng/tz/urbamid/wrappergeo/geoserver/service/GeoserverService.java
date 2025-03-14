package it.eng.tz.urbamid.wrappergeo.geoserver.service;

import java.io.IOException;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureType;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayer;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroup;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSResFeatureTypeDB;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyle;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureType;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureTypeM;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureTypes;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayer;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayers;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResStyle;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResStyles;

public interface GeoserverService {
	
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 *  
//	 * @return ResLayerGroups layergroup
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	public ResLayerGroups layerGroups(String nameWs) throws WrapperGeoServiceException;
//	
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param nameLG è il layergroup di geoserver
//	 *  
//	 * @return ResLayerGroup layergroup
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	public ResLayerGroup layerGroup(String nameWs, String nameLG) throws WrapperGeoServiceException;
//
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param GSLayerGroupB lg è il layergroup di geoserver con le modifiche possibili
//	 * @param operation operazione possibile è inserimento o cancellazione
//	 *  
//	 * @return String log informazioni
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	public String modLayerGroup(String nameWs, GSLayerGroupB lg, String operation) throws WrapperGeoServiceException;
//	
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param nameLg è il nome layergroup di geoserver da cancellare
//	 *  
//	 * @return String log informazioni
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	public String delLayerGroup(String nameWs, String nameLg) throws WrapperGeoServiceException;
//	
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param nameLg è il datastore di geoserver
//	 * @param nameL è il nome del layer da associare
//	 * @param nameS è il nome dello style da associare
//	 *  
//	 * @return una stringa con le informazioni di log
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	public String addLayerToLayergroup(String nameWs, String nameLg, String nameL, String nameS) throws WrapperGeoServiceException;
	
	
	/**
	 * @param nameWs è il workspace di geoserver
	 *  
	 * @return ResLayers layers
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResLayers layers(String nameWs) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il layer di geoserver
	 *  
	 * @return ResLayer layer
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResLayer layer(String nameWs, String nameL) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param GSLayer l è il layer di geoserver con le modifiche possibili
	 *  
	 * @return String log informazioni
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String modLayer(String nameWs, GSLayer l) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il nome layer di geoserver da cancellare
	 *  
	 * @return String log informazioni
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String delLayer(String nameWs, String nameL) throws WrapperGeoServiceException;
	

	/**
	 * @param nameWs è il workspace di geoserver
	 *  
	 * @return ResFeatureTypes featureTypes
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResFeatureTypes featureTypes(String nameWs) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameFt è il nome della featureType di geoserver
	 *  
	 * @return ResFeatureType featureType
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResFeatureType featureType(String nameWs, String nameFt) throws WrapperGeoServiceException;
	
	public ResFeatureType getGSFeatureType(String nameWs, String nameDs, String nameFt);
	public ResFeatureTypeM getGSFeatureTypeM(String nameWs, String nameDs, String nameFt);
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param GSFeatureType è la featureType di geoserver con le modifiche possibili
	 *  
	 * @return String log informazioni
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String modFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException;
	
	public String modKeywordsFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException;

	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameFt è il nome della featureType da cancellare
	 *  
	 * @return String log informazioni
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String delFeatureType(String nameWs, String nameFt) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param GSFeatureType è la featureType da pubblicare
	 *  
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String publishFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il workspace di geoserver
	 *  
	 * @return ResStyles styles
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResStyles styles(String nameWs) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il nome workspace di geoserver
	 * @param nameS è il nome dello style di geoserver
	 *  
	 * @return ResStyle style
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResStyle style(String nameWs, String nameS) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il nome workspace di geoserver
	 * @param nameS è il nome dello style di geoserver
	 * @param nameFS è il nome del file sld per lo style di geoserver
	 *  
	 * @return ResStyle style
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public ResStyle newStyle(String nameWs, String nameS, String nameFS) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il nome workspace di geoserver
	 * @param nameS è il nome dello style di geoserver
	 * @param nameFS è il nome del file sld per lo style di geoserver
	 *  
	 * @return String log informazioni
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String delStyle(String nameWs, String nameS, String nameFS) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il nome workspace di geoserver
	 * @param GSStyle è lo style di geoserver
	 *  
	 * @return ResStyle style
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	public String publishStyle(String nameWs, GSStyle style) throws WrapperGeoServiceException;
	
	/**
	 * @param nameWs è il nome del workspace di geoserver
	 * @param idLayer è l'ID del layer da creare
	 * @param nomeLayer è il title del layer
	 * @param identificativo è il nome che verrà utilizzato come nome layer
	 * @return
	 * @throws WrapperGeoServiceException
	 * @throws IOException 
	 */
	GSResFeatureTypeDB createNewLayer(String nameWs, Long idLayer, String nomeLayer, String identificativo)
			throws WrapperGeoServiceException, IOException;

	/**
	 * 
	 * @param nameWs è il nome del workspace di geoserver
	 * @param identificativo è il nome che verrà utilizzato come nome layer
	 * @return
	 * @throws WrapperGeoServiceException
	 * @throws IOException
	 */
	GSLayerGroup addLayerInLayerGroup(String nameWs, String identificativo)
			throws WrapperGeoServiceException, IOException;

	

}
