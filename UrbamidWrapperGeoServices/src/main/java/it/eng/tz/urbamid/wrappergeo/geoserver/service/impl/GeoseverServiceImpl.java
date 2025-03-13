package it.eng.tz.urbamid.wrappergeo.geoserver.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.exception.WrappergeoStorageException;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSAttribute;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSEntryDB;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureType;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureTypeDBWrapper;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureTypeM;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureTypeWrapper;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSGeometry;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSKeywords;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayer;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroup;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroupLG;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroupLGWrapper;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerGroupWrapper;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSMetadata;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSPublishables;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSPublished;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSResFeatureTypeDB;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSResFeatureTypeWrapperDB;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyle;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyleShort;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyles;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSVirtualTable;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSWorkspaceLG;
import it.eng.tz.urbamid.wrappergeo.geoserver.json.GsonFactory;
import it.eng.tz.urbamid.wrappergeo.geoserver.service.AbstractGeoserverService;
import it.eng.tz.urbamid.wrappergeo.geoserver.service.GeoserverService;
import it.eng.tz.urbamid.wrappergeo.service.RestService;
import it.eng.tz.urbamid.wrappergeo.storage.service.WrapperPathService;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureType;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureTypeM;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureTypes;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayer;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayers;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResStyle;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResStyles;

@Service
public class GeoseverServiceImpl extends AbstractGeoserverService implements GeoserverService {
	
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(GeoserverService.class);
	
	public final String METHOD_DELETE 	= "DELETE";
	public final String METHOD_POST 	= "POST";
	public final String METHOD_PUT 		= "PUT";
	public final String METHOD_GET 		= "GET";
	
	
	public final String DS				= "dbUrbamid";
	public final String PREFIX_ATTR		= "viewField_";
	public final String PREFIX_TAB		= "u_geo_";
	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	public RestService restService;
	
	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	public WrapperPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param pathService {@link ImportWrappergeoPathService}
	 */
	public GeoseverServiceImpl(
			RestService restService, WrapperPathService pathService) {
		Assert.notNull(restService, "WrapperRestService MUST not be null but don't panic!");
		Assert.notNull(pathService, "WrapperPathService MUST not be null but don't panic!");
		this.restService = restService;
		this.pathService = pathService;
	}
	
	
	/********************************************************************************************************************************************************/
	/******************************************************** LAYERGROUP ************************************************************************************/
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 *  
//	 * @return ResLayerGroups layergroup
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	@Override
//	public ResLayerGroups layerGroups(String nameWs) throws WrapperGeoServiceException {
//		
//		ResLayerGroups res = getGSLayerGroups(nameWs);
//		return res;
//	}
//	private ResLayerGroups getGSLayerGroups(String nameWs) throws WrapperGeoServiceException {
//		
//		StringBuilder informazioniDiLog = new StringBuilder();
//		ResLayerGroups layerGroups = new ResLayerGroups();
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		if(LOG.isDebugEnabled()) {
//			LOG.debug("Recupero i layerGroup del workspaces {}.", nameWs);
//		}
//		
//		this.checkParams(nameWs);
//		try {
//			
//			informazioniDiLog.append("Verranno recuperati i layergroups del workspaces " + nameWs);
//			
//			String response = this.gsGetLayergroup(nameWs);
//			if( !response.isEmpty() ) {
//				
//				layerGroups = objectMapper.readValue(response, ResLayerGroups.class);
//				informazioniDiLog.append("Sono stati recuperati i layergroups del workspaces " + nameWs);
//			} else {
//				
//				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
//				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
//			}
//			return layerGroups;
//		} catch (Exception e) {
//			
//			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare i layergroups del workspace {}.", nameWs);
//			throw new WrappergeoStorageException("Non è stato possibile recuperare i layergroups del workspace "+nameWs+".");
//		}
//	}
//	public String gsGetLayergroup(String nameWs) throws IOException {
//
//		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layergroups";
//		String response = restCall(METHOD_GET, url, "");
//		return response;
//	}
//
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param nameLG è il layergroup di geoserver
//	 *  
//	 * @return ResLayerGroup layergroup
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	@Override
//	public ResLayerGroup layerGroup(String nameWs, String nameLG) throws WrapperGeoServiceException {
//		
//		ResLayerGroup response = getGSLayerGroup(nameWs, nameLG );
//		return response;
//	}
//	private ResLayerGroup getGSLayerGroup(String nameWs, String nameLG) throws WrapperGeoServiceException {
//		
//		StringBuilder informazioniDiLog = new StringBuilder();
//		ResLayerGroup layerGroups = new ResLayerGroup();
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//
//		
//		if(LOG.isDebugEnabled()) {
//			LOG.debug("Recupero il layerGroup {} del workspaces {}.", nameLG, nameWs);
//		}
//		
//		this.checkParams(nameWs);
//		try {
//			
//			informazioniDiLog.append("Verrà recuperato il layergroup "+nameLG+" del workspaces " + nameWs);
//			
//			String response = this.gsGetLayergroups(nameWs, nameLG);
//			if( !response.isEmpty() ) {
//				
//				if (response.contains("\"@type\":\"layer\""))
//					layerGroups = objectMapper.readValue(response, ResLayerGroup.class);
//				informazioniDiLog.append("Sono stati recuperati i layergroups del workspaces " + nameWs);
//			} else {
//				
//				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
//				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
//			}
//			return layerGroups;
//		} catch (Exception e) {
//			
//			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare il layergroup {} del workspace {}.", nameLG, nameWs);
//			throw new WrappergeoStorageException("Non è stato possibile recuperare il layergroup "+nameLG+" del workspace "+nameWs+".");
//		}
//	}
//	public String gsGetLayergroups(String nameWs, String nameLG) throws IOException {
//
//		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layergroups/"+nameLG+".json";
//		String response = restCall(METHOD_GET, url, "");
//		return response;
//	}
//	
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param GSLayerGroup è il layergroup di geoserver
//	 * 
//	 * @return una stringa con le informazioni di log
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	@Override
//	public String modLayerGroup(String nameWs, GSLayerGroup lg, String operation) throws WrapperGeoServiceException {
//		
//		String logPublish = modGSLayerGroup(nameWs, lg, operation );
//		return new StringBuilder(logPublish).toString();
//	}
//	private String modGSLayerGroup(String nameWs, GSLayerGroup lg, String operation) throws WrapperGeoServiceException {
//		
//		StringBuilder informazioniDiLog = new StringBuilder();
//		ResLayerGroup resLayergroup = new ResLayerGroup();
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		if(LOG.isDebugEnabled()) {
//			LOG.debug("Modifica del layergroup {} del workspaces {}.", lg.getName(), nameWs);
//		}
//		
//		this.checkParams(nameWs);
//		this.checkParams(lg.getPublishables().getPublished().get(0).getName());
//		try {
//			
//			informazioniDiLog.append("Verrà modificato il layergroup "+lg.getName()+" del workspaces " + nameWs);
//
//			/**RECUPERO IL LAYERGROUP SE PRESENTE**/
//			layergroup = this.getGSLayerGroup(nameWs, lg.getName());
//			if ( layergroup!=null ) {
//				
//				String layerName = lg.getPublishables().getPublished().get(0).getName();
//				ResLayer resLayer = this.getGSLayer(nameWs, layerName);
//				String layerLink = resLayer.getLayer().getResource().getHref();
//				
//				String styleName = resLayer.getLayer().getDefaultStyle().getName();
//				String styleLink = resLayer.getLayer().getDefaultStyle().getHref();
//				
//				/**MODIFICA LAYERGROUP**/
//				if ( !layerName.isEmpty() && !layerLink.isEmpty() &&
//						!styleName.isEmpty() && !styleLink.isEmpty() ) {
//					
//					/**Layer**/
//					GSPublishables gsPub = layergroup.getLayerGroup().getPublishables();
//					List<GSPublished> listLay = gsPub.getPublished();
//					GSPublished lay = new GSPublished();
//					lay.setName(layerName);
//					lay.setHref(layerLink);
//					listLay.add(lay);
//					gsPub.setPublished(listLay);
//					layergroup.getLayerGroup().setPublishables(gsPub);
//					/**Style**/
//					GSStyleShort gsSty = layergroup.getLayerGroup().getStyles();
//					List<String> listSty = gsSty.getStyle();
//					listSty.add(styleName);
//					gsSty.setStyle(listSty);
//					layergroup.getLayerGroup().setStyles(gsSty);
//				}
//			} else {
//				
//				LOG.error("WRAPPERGEO SERVICE -- il layergroup {} non è presente nel workspaces {}.", lg.getName(), nameWs);
//				throw new WrapperGeoServiceException("il layergroup "+lg.getName()+" non è presente nel workspaces "+nameWs+".");
//			}
//			
//			/**SALVA LAYERGROUP**/
//			boolean response = this.gsModLayerGroup(nameWs, layergroup.getLayerGroup());
//			if( response ) {
//				
//				informazioniDiLog.append("E' stato modificato il layergroup "+lg.getName()+"  del workspaces " + nameWs);
//			} else {
//				
//				LOG.error("WRAPPERGEO SERVICE -- non è presente il layergroup {} nel workspaces {}.", lg.getName(), nameWs);
//				throw new WrapperGeoServiceException("Non è presente il layergroup "+lg.getName()+" nel workspace "+nameWs+".");
//			}
//				
//			informazioniDiLog.append("E' stato modificato il layer e file associato.");
//			return informazioniDiLog.toString();
//		} catch (Exception e) {
//			
//			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile modificare il layergroup {} del workspace {}.", lg.getName(), nameWs);
//			throw new WrappergeoStorageException("Non è stato possibile modificare il layergroup "+lg.getName()+" del workspace "+nameWs+".");
//		}
//	}
//	public boolean gsModLayerGroup(String nameWs, GSLayerGroup lg) throws IOException {
//		ObjectMapper mapper = new ObjectMapper();
//		String xmlString = this.jaxbObjectToXML(lg);
//		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layergroups/"+lg.getName();
//		int sendRESTint = restPostXml(METHOD_POST, url, xmlString);
//		return 201 == sendRESTint;
//	}
//	
//	/**
//	 * @param nameWs è il workspace di geoserver
//	 * @param nameLg è il nome layergroup di geoserver da cancellare
//	 *  
//	 * @return String log informazioni
//	 * 
//	 * @throws WrapperGeoServiceException 
//	 */
//	@Override
//	public String delLayerGroup(String nameWs, String nameLg) throws WrapperGeoServiceException {
//		
//		String logPublish = delGSLayerGroup(nameWs, nameLg );
//		return new StringBuilder(logPublish).toString();
//	}
//	private String delGSLayerGroup(String nameWs, String nameLg) throws WrapperGeoServiceException {
//		
//		StringBuilder informazioniDiLog = new StringBuilder();
//		if(LOG.isDebugEnabled()) {
//			LOG.debug("Recupero il layergroup {} del workspaces {}.", nameLg, nameWs);
//		}
//		
//		this.checkParams(nameWs);
//		try {
//			
//			informazioniDiLog.append("Verrà eliminato il layergroup "+nameLg+" del workspaces " + nameWs);
//			
//			String response = this.gsDelLayerGroup(nameWs, nameLg);
//			if( !response.isEmpty() ) {
//				
//				informazioniDiLog.append("E' stato eliminato il layergroup "+nameLg+"  del workspaces " + nameWs);
//			} else {
//				
//				LOG.error("WRAPPERGEO SERVICE -- non è presente il layergroup {} nel workspaces {}.", nameLg, nameWs);
//				throw new WrapperGeoServiceException("Non è presente il layergroup "+nameLg+" nel workspace "+nameWs+".");
//			}
//			
//			informazioniDiLog.append("E' stato eliminato il layergroup e file associato.");
//			return informazioniDiLog.toString();
//		} catch (Exception e) {
//			
//			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile eliminare il layergroup {} del workspace {}.", nameLg, nameWs);
//			throw new WrappergeoStorageException("Non è stato possibile eliminare il layergroup "+nameLg+" del workspace "+nameWs+".");
//		}
//	}
//	public String gsDelLayerGroup(String nameWs, String nameLg) throws IOException {
//
//		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layergroups/"+nameLg;
//		String response = restCall(METHOD_DELETE, url, "");
//		return response;
//	}
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
//	@Override
//	public String addLayerToLayergroup(String nameWs, String nameLg, String nameL, String nameS) throws WrapperGeoServiceException {
//		
//		String logPublish = addLayerToLG(nameWs, nameLg, nameL, nameS);
//		return new StringBuilder(logPublish).toString();
//	}
//	private String addLayerToLG(String nameWs, String nameLg, String nameL, String nameS) throws WrapperGeoServiceException {
//		
//		StringBuilder informazioniDiLog = new StringBuilder();
//		if(LOG.isDebugEnabled()) {
//			LOG.debug("Pubblico il layer {} e style {} nel workspace {} e layergroup {}.", nameL, nameS, nameWs, nameLg);
//		}
//		
//		this.checkParams(nameL);
//		try {
//			
//			informazioniDiLog.append("Verrà associato il layer "+nameL+" e style "+nameS+"al layergroup "+ nameLg);
//			
//			/*Recupero il layer e quindi verifico l'esistenza del layer*/
//			GSPublished newLay = new GSPublished();
//			ResLayer layer = this.layer(nameWs, nameL);
//			if (layer!=null) {
//				newLay.setType("Layer");
//				newLay.setName( layer.getLayer().getResource().getName() );
//				newLay.setLink( GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layers/"+layer.getLayer().getName()+".json" );
//			} else
//				newLay = null;
//			
//			/*Recupero lo style*/
//			GSStyleShort newSty = null;
//			if ( (nameS==null || nameS.isEmpty()) && 
//					layer.getLayer().getDefaultStyle()!=null ) {
//				/*Associo lo style di default*/
//				newSty = new GSStyleShort();
//				newSty.setName(layer.getLayer().getDefaultStyle().getName());
//				newSty.setLink(layer.getLayer().getDefaultStyle().getHref());
//			} else if (nameS!=null && !nameS.isEmpty()){
//				/*Associo lo style indicato*/
//				newSty = new GSStyleShort();
//				ResStyle style = this.style(nameWs, nameS);
//				newSty.setName(style.getStyle().getName());
//			}
//			
//			/*Recupero il layergroup*/
//			ResLayerGroup layerGroup = this.layerGroup(nameWs, nameLg);
//			if ( layerGroup!=null ) {
//				
//				if (layerGroup.getLayerGroup().getPublishables()!=null && 
//						layerGroup.getLayerGroup().getPublishables().getPublished()!=null ){
//					layerGroup.getLayerGroup().getPublishables().getPublished().add(newLay);
//				}
//				
////				if (layerGroup.getLayerGroup().getStyles()!=null && 
////						layerGroup.getLayerGroup().getStyles().getStyle()!=null ) {
////					layerGroup.getLayerGroup().getStyles().getStyle().add(newSty);
////				}
//			}
//		    
//			GSLayerGroup lg = layerGroup.getLayerGroup();			
//			boolean response = this.gsAddLayerToLG(nameWs, nameLg, lg);
//			if( response ) {
//				
//				informazioniDiLog.append("E' stato aggiunto il layer al layergroup ");
//			} else {
//				
//				LOG.error("PUBLISHER WRAPPERGEO SERVICE -- è stato modificato il layergroup.");
//				throw new WrapperGeoServiceException("E' stato modificato il layergroup.");
//			}
//		    
//			informazioniDiLog.append("E' stato modificato il layergroup.");
//			return informazioniDiLog.toString();
//		} catch (Exception e) {
//			
//			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile associare il layer {} con lo style {} al layergroup {}.", nameL, nameS, nameLg);
//			throw new WrappergeoStorageException("Non è stato possibile associare il layer "+nameL+" con lo style "+nameS+" al layergroup "+nameLg+"." );
//		}
//	}
//	public boolean gsAddLayerToLG(String nameWs, String nameLg, GSLayerGroup lg) throws IOException {
//	    
//	    Gson gson = GsonFactory.getGson();
//		String jsonBody = gson.toJson(new ResLayerGroup(lg));
//	    
//		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layergroups/" + nameLg;
//		int sendRESTint = restPutJson(METHOD_PUT, url, jsonBody);
//		return 201 == sendRESTint;
//	}
	/********************************************************************************************************************************************************/

	/********************************************************************************************************************************************************/
	/******************************************************** LAYER *****************************************************************************************/
	/**
	 * @param nameWs è il workspace di geoserver
	 *  
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResLayers layers(String nameWs) throws WrapperGeoServiceException {
		
		ResLayers res = getGSLayers(nameWs);
		return res;
	}
	private ResLayers getGSLayers(String nameWs) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResLayers layers = new ResLayers();
		ObjectMapper objectMapper = new ObjectMapper();

		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero i layer del workspaces {}.", nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verranno recuperati i layers del workspaces " + nameWs);
			
			String response = this.gsGetLayers(nameWs);
			if( !response.isEmpty() ) {
				
				layers = objectMapper.readValue(response, ResLayers.class);
				informazioniDiLog.append("Sono stati recuperati i layers del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
			}
			return layers;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare i layers del workspace {}.", nameWs);
			throw new WrappergeoStorageException("Non è stato possibile recuperare i layers del workspace "+nameWs+".");
		}
	}
	public String gsGetLayers(String nameWs) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layers";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il layer di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResLayer layer(String nameWs, String nameL) throws WrapperGeoServiceException {
		
		ResLayer response = getGSLayer(nameWs, nameL );
		return response;
	}
	private ResLayer getGSLayer(String nameWs, String nameL) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResLayer layer = new ResLayer();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero il layer {} del workspaces {}.", nameL, nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà recuperato il layer "+nameL+" del workspaces " + nameWs);
			
			String response = this.gsGetLayer(nameWs, nameL);
			if( !response.isEmpty() ) {
				
				layer = objectMapper.readValue(response, ResLayer.class);
				informazioniDiLog.append("E' stato recuperato il layer "+nameL+"  del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
			}
			return layer;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare il layer {} del workspace {}.", nameL, nameWs);
			throw new WrappergeoStorageException("Non è stato possibile recuperare il layer "+nameL+" del workspace "+nameWs+".");
		}
	}
	public String gsGetLayer(String nameWs, String nameL) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layers/"+nameL+".json";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}	

	/**
	 * @param nameWs è il workspace di geoserver
	 * @param GSLayer è il layer di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String modLayer(String nameWs, GSLayer l) throws WrapperGeoServiceException {
		
		String logPublish = modGSLayer(nameWs, l );
		return new StringBuilder(logPublish).toString();
	}
	private String modGSLayer(String nameWs, GSLayer l) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureTypeM featureTypeLayer = new ResFeatureTypeM();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Modifica del layer {} del workspaces {}.", l.getName(), nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà modificato il layer "+l.getName()+" del workspaces " + nameWs);

			/*Recupero il layer se presente*/
			featureTypeLayer = this.getGSDatastore(nameWs, l.getName(), l.getName());
			if ( featureTypeLayer!=null ) {
				
				List<String> newKeywords = new ArrayList<String>();
				for (String keyword : featureTypeLayer.getFeatureType().getKeywords().getString()){
					String newKeyword = new String();
					if ( keyword.contains("vocabulary") ) {
						keyword = keyword.replace("vocabulary=", "").replaceAll("\\\\" , "").replace(";", "");
						newKeyword = keyword;
					}else {
						newKeyword=keyword;
					}
					newKeywords.add(newKeyword);
				}
				featureTypeLayer.getFeatureType().getKeywords().setString(newKeywords);
				/**MODIFICA LAYER E SALVA**/
				boolean response = this.gsModDatastoreLayer(nameWs, l.getName(), featureTypeLayer);
				if( response ) {
					
					informazioniDiLog.append("E' stato modificato il layer "+l.getName()+"  del workspaces " + nameWs);
				} else {
					
					LOG.error("WRAPPERGEO SERVICE -- non è presente il layer {} nel workspaces {}.", l.getName(), nameWs);
					throw new WrapperGeoServiceException("Non è presente il layer "+l.getName()+" nel workspace "+nameWs+".");
				}
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- il layer {} non è presente nel workspaces {}.", l.getName(), nameWs);
//				throw new WrapperGeoServiceException("il layer "+l.getName()+" non è presente nel workspaces "+nameWs+".");
			}
				
			informazioniDiLog.append("E' stato modificato il layer e file associato.");
			return informazioniDiLog.toString();
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile modificare il layer {} del workspace {}.", l.getName(), nameWs);
			throw new WrappergeoStorageException("Non è stato possibile modificare il layer "+l.getName()+" del workspace "+nameWs+".");
		}
	}
	private ResFeatureTypeM getGSDatastore(String nameWs, String nameDsLayer, String nameL)  {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureTypeM ft = new ResFeatureTypeM();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero il layer {} del workspaces {}.", nameL, nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			String response = this.gsGetDatastores(nameWs, nameL, nameL);
			if( response!=null && !response.isEmpty() ) {
				
				ft = objectMapper.readValue(response, ResFeatureTypeM.class);
				informazioniDiLog.append("E' stato recuperato il layer "+nameL+"  del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
			}
			return ft;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare il layer {} del workspace {}.", nameL, nameWs);
//			throw new WrappergeoStorageException("Non è stato possibile recuperare il layer "+nameL+" del workspace "+nameWs+".");
			return null;
		}
	}
	public String gsGetDatastores(String nameWs, String nameDSLayer, String nameL) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/"+nameDSLayer+"/featuretypes/"+nameL+".json";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	public boolean gsModDatastoreLayer(String nameWs, String nameDSLayer, ResFeatureTypeM f) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String xmlString = this.jaxbObjectToXML(f.getFeatureType());
		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/"+nameDSLayer+"/featuretypes/"+nameDSLayer+".xml";
		int sendRESTint = restPutXml(METHOD_PUT, url, xmlString);
		return 200 == sendRESTint;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il layer di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String delLayer(String nameWs, String nameL) throws WrapperGeoServiceException {
		
		String logPublish = delGSLayer(nameWs, nameL );
		return new StringBuilder(logPublish).toString();
	}
	private String delGSLayer(String nameWs, String nameL) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResLayer layer = new ResLayer();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero il layer {} del workspaces {}.", nameL, nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà eliminato il layer "+nameL+" del workspaces " + nameWs);
			
			boolean response = this.gsDelLayer(nameWs, nameL);
			if( response ) {
				
				informazioniDiLog.append("E' stato eliminato il layer "+nameL+"  del workspaces " + nameWs);
			} 
			
			informazioniDiLog.append("E' stato eliminato il layer e file associato.");
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
				LOG.debug("WRAPPERGEO SERVICE -- Non è presente il layer {} nel workspaces {} e datastores {}.", nameL, nameWs, DS);
				return informazioniDiLog.append("Non è presente la featureType.").toString();
		
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile eliminare il layer {} del workspace {}.", nameL, nameWs);
			throw new WrappergeoStorageException("Non è stato possibile eliminare il layer "+nameL+" del workspace "+nameWs+".");
		}
	}
	public boolean gsDelLayer(String nameWs, String nameL) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layers/"+nameL+"?recurse=true";
		restCall(METHOD_DELETE, url, "");
		return true;
	}
	/********************************************************************************************************************************************************/
	
	/********************************************************************************************************************************************************/
	/******************************************************** FEATURETYPE ***********************************************************************************/
	/**
	 * @param nameWs è il workspace di geoserver
	 *  
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResFeatureTypes featureTypes(String nameWs) throws WrapperGeoServiceException {
		
		ResFeatureTypes res = getGSFeatureTypes(nameWs);
		return res;
	}
	private ResFeatureTypes getGSFeatureTypes(String nameWs) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureTypes featureTypes = new ResFeatureTypes();
		ObjectMapper objectMapper = new ObjectMapper();

		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero le featureTypes del workspaces {} e datastores {}.", nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verranno recuperati le featureTypes del workspaces " + nameWs + " e datastores "+DS+".");
			
			String response = this.gsGetFeatureTypes(nameWs, DS);
			if( !response.isEmpty() ) {
				
				featureTypes = objectMapper.readValue(response, ResFeatureTypes.class);
				informazioniDiLog.append("Sono stati recuperati i layers del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
			}
			return featureTypes;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare i layers del workspace {}.", nameWs);
			throw new WrappergeoStorageException("Non è stato possibile recuperare i layers del workspace "+nameWs+".");
		}
	}
	public String gsGetFeatureTypes(String nameWs, String DS) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/" + DS + "/featuretypes.json";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param idLayer è l'ID dal layer per effettuare la query
	 * @param nomeLayer è il titolo del layer
	 * @param identificativo è il nome del layer
	 * 
	 * @return {@link Boolean}
	 * 
	 * @throws WrapperGeoServiceException
	 * @throws IOException 
	 */
	@Override
	public GSResFeatureTypeDB createNewLayer(String nameWs, Long idLayer, String nomeLayer, String identificativo) throws WrapperGeoServiceException, IOException {
		
		GSResFeatureTypeDB response = this.createFeatureTypeDB(nameWs, idLayer, nomeLayer, "u_admin_edtl_".concat(identificativo.toLowerCase()));
		return response;
		
	}
	private GSResFeatureTypeDB createFeatureTypeDB(String nameWs, Long idLayer, String nomeLayer, String identificativo) throws WrapperGeoServiceException, IOException {
		
		GSResFeatureTypeDB featureType = new GSResFeatureTypeDB();
		Gson gson = GsonFactory.getGson();
		
		LOG.info("WRAPPERGEO SERVICE --> Inizio creazione/modifica featureType {} del workspaces {} del datastrore {}", identificativo, nameWs, DS);
		
		this.checkParams(nameWs);
		try {
			LOG.debug("Recupero la featureType {} del workspaces {} e datasource {}.", identificativo, nameWs, DS);
			String response = this.gsGetFeatureType(nameWs, identificativo);
			
			if(!response.isEmpty()) {
				/** TRASFRORMO LA RISPOSTA DI GEOSERVER IN UNA CLASSE JAVA PER POTERLA MODIFICARE **/
				featureType = gson.fromJson(response, GSResFeatureTypeWrapperDB.class).getFeatureType();
				/** MODIFICO IL TITOLO E L'ABSTRACT **/
				featureType.setTitle(nomeLayer);
				featureType.setAbs(nomeLayer);
				/** TRASFORMO LA CLASSE JAVA IN JSON DA MANDARE A GEOSERVER **/
				String jsonBody = gson.toJson(new GSFeatureTypeDBWrapper(featureType));
				/** CREO LA CHIAMATA PUT DA MANDARE ALL'API DI GEOSERVER AGGIORNANDO IL Bounding Boxes **/
				restPutJson(METHOD_PUT, GEOSERVER_ENDPOINT + GEOSERVER_WORKSPACES + nameWs + GEOSERVER_DATASTORES + DS + "/featuretypes/" + identificativo.concat(".json") + "?recalculate=nativebbox,latlonbbox", jsonBody);
				LOG.debug("WRAPPERGEO SERVICE --> E' stata modificata la featureType {} del workspaces {} e datastore {}", identificativo, nameWs, DS);
			
			}
			
		} catch (IOException e) { /** SE LA FEATURETYPE NON ESISTE (IoException), ALLORA NEL CATCH NE CREO UNA NUOVA **/
			LOG.debug("WRAPPERGEO SERVICE --> Non esiste nessuna featureType {} nel workspace {} e datastore {}, nè verrà creata una featureType nuova.", identificativo, nameWs, DS);
			/** TRASFORMO LA CLASSA JAVA IN JSON DA MANDARE A GEOSERVER */
			featureType = this.createFeatureTypeObj(idLayer, nomeLayer, identificativo);
			String jsonBody = gson.toJson(new GSFeatureTypeDBWrapper(featureType));
			/** CREO LA CHIAMATA POST DA MANDARE ALL'API DI GEOSERVER **/ 
			restPostJson(METHOD_POST, GEOSERVER_ENDPOINT + GEOSERVER_WORKSPACES + nameWs + GEOSERVER_DATASTORES + DS + "/featuretypes", jsonBody);
			LOG.debug("WRAPPERGEO SERVICE --> E' stata creata la featureType {} nel workspaces {} e datastore {}", identificativo, nameWs, DS);
			
		} catch (Exception e) {
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile creare/modificare la featureType {} del workspace {} e datastore {}.", identificativo, nameWs, DS);
			throw new WrappergeoStorageException("Non è stato possibile creare/modificare la featureType " + identificativo + " del workspace " + nameWs + " e datastore " + DS + ".");
			
		}
		
		return featureType;
	
	}
	private GSResFeatureTypeDB createFeatureTypeObj(Long idLayer, String nomeLayer, String identificativo) {
		/** CREO LA QUERY SQL PER POTER RECUPERARE LE GEOMETRIE PASSANDOGLI L'ID DEL LAYER **/
		String sqlString = "SELECT * FROM u_admin_edtl_layers_attr WHERE id_layer = " + idLayer;
		
		/** CREO L'OGGETTO GEOMETRY SETTANDO IL SISTEMA DI RIFERIMENTO A 4326 **/
		GSGeometry gsGeometry = new GSGeometry();
		gsGeometry.setName("geom");
		gsGeometry.setType("Geometry");
		gsGeometry.setSrid(4326); 
		
		/** CREO L'OGGETTO VIRTUAL TABLE, IN CUI SETTO LA QUERY PER IL RECUPERO E L'OGGETTO GEOMETRY **/
		GSVirtualTable gsVirtualTable = new GSVirtualTable();
		gsVirtualTable.setName(identificativo);
		gsVirtualTable.setSql(sqlString);
		gsVirtualTable.setEscapeSql(false);
		gsVirtualTable.setGeometry(gsGeometry);
		
		/** CREO L'OGGETTO ENTRY DOVE SETTO LA VIRTUAL TABLE **/
		GSEntryDB gsEntryDB = new GSEntryDB();
		gsEntryDB.setKey(GSEntryDB.KEY_DB);
		gsEntryDB.setVirtualTable(gsVirtualTable);
		
		/** CREO L'OGGETTO METADATA **/
		GSMetadata gsMetadata = new GSMetadata();
		gsMetadata.setEntry(gsEntryDB);
		
		/** CREO LE KEYWORDS **/
		List<String> keywords = new ArrayList<>();
		
		keywords.add("features");
		keywords.add(identificativo);
		keywords.add("viewField_id@Id");
		keywords.add("viewField_id_layer@Id_layer");
		keywords.add("viewField_nome@Nome");
		keywords.add("viewField_descrizione@Descrizione");
		keywords.add("viewField_tipo@Tipo");
		keywords.add("shapeDb@true");
		
		GSKeywords gsKeywords = new GSKeywords();
		gsKeywords.setString(keywords);
		
		/** CREO L'OGGETTO FEATURE TYPE PER IL RECUPERO DA DB **/
		GSResFeatureTypeDB featureTypeDB = new GSResFeatureTypeDB();
		
		featureTypeDB.setName(identificativo);
		featureTypeDB.setNativeName(identificativo);
		featureTypeDB.setTitle(nomeLayer);
		featureTypeDB.setAbs(nomeLayer);
		featureTypeDB.setKeywords(gsKeywords);
		featureTypeDB.setSrs(GSResFeatureTypeDB.PROJ_SRID);
		featureTypeDB.setProjectionPolicy(GSResFeatureTypeDB.PROJ_POLICY_REPROJECT_FORCE_DECLARED);
		featureTypeDB.setEnabled(true);
		featureTypeDB.setAdvertised(true);
		featureTypeDB.setMetadata(gsMetadata);
		
		return featureTypeDB;
	}
	@Override
	public GSLayerGroup addLayerInLayerGroup(String nameWs, String identificativo) throws WrapperGeoServiceException, IOException {
		
		GSLayerGroup response = this.createLayerGroup(nameWs, identificativo);
		return response;
		
	}
	private GSLayerGroup createLayerGroup(String nameWs, String identificativo) throws WrapperGeoServiceException, IOException {
		
		GSLayerGroup layerGroup = new GSLayerGroup();
		Gson gson = GsonFactory.getGson();
		
		LOG.info("WRAPPERGEO SERVICE --> Inizio creazione/modifica layerGroup {} nel workspaces {}", identificativo, nameWs);
		
		this.checkParams(nameWs);
		try {
			LOG.debug("Recupero il layerGroup {} nel workspaces {}", identificativo, nameWs);
			String response = this.gsGetLayerGroup(nameWs, "EditingLayer_tematismo");
			
			if(!response.isEmpty()) {
				/** TRASFRORMO LA RISPOSTA DI GEOSERVER IN UNA CLASSE JAVA PER POTERLA MODIFICARE **/
				JsonObject e = new JsonParser().parse(response).getAsJsonObject();
				JsonElement element = e.get("layerGroup").getAsJsonObject().get("publishables").getAsJsonObject().get("published");
				/** CONTROLLO SE PUBLISHED SIA UN ARRAY IN QUANTO NON SAPPIAMO CHE JSON CI INVIERA' GEOSERVER **/
				if(element.isJsonArray()) {
					/** TRASFRORMO LA RISPOSTA DI GEOSERVER IN UNA CLASSE JAVA PER POTER AGGIUNGERE GLI ALTRI LAYERS **/
					layerGroup = gson.fromJson(e, GSLayerGroupWrapper.class).getLayerGroup();
				
				} else {
					/** TRASFORMO LA RISPOSTA DI GEOSERVER SE IL LAYERGROUP DOVESSE AVERE UN SOLO LAYER **/
					GSLayerGroupLG layerGroupLG = gson.fromJson(e, GSLayerGroupLGWrapper.class).getLayerGroup();
					/** TRASFORMO IL SINGOLO LAYER IN UN ARRAYLIST **/
					List<GSPublished> listaLayer = new ArrayList<GSPublished>(Arrays.asList(layerGroupLG.getPublishables().getPublished()));
					/** CREO L'OGGETTO PUBLISHABLED CON LA listaLayer **/
					GSPublishables gsPublishables = new GSPublishables();
					gsPublishables.setPublished(listaLayer);
					
					layerGroup.setName(layerGroupLG.getName());
					layerGroup.setMode(layerGroupLG.getMode());
					layerGroup.setTitle(layerGroupLG.getTitle());
					layerGroup.setWorkspace(layerGroupLG.getWorkspace());
					layerGroup.setPublishables(gsPublishables);
					layerGroup.setStyles(layerGroupLG.getStyles());
					layerGroup.setBounds(layerGroupLG.getBounds());
					
				}
				/** CREO L'OGGETTO LAYER **/
				GSPublished layerDaAggiungere = new GSPublished();
				
				layerDaAggiungere.setType("layer");
				layerDaAggiungere.setHref(GEOSERVER_ENDPOINT + GEOSERVER_WORKSPACES + nameWs + "/layers/" + identificativo.concat(".json"));
				layerDaAggiungere.setName("urbamid:"+identificativo);
				/** VERIFICO CHE NELLA LISTA RECUPERATA NON CONTENGA UN LAYER GIA' ESISTENTE NEL LAYERGROUP **/
				if(!layerGroup.getPublishables().getPublished().contains(layerDaAggiungere)) {
		
					layerGroup.getPublishables().getPublished().add(layerDaAggiungere);
					
					GSStyleShort styleShort = new GSStyleShort();
					styleShort.setName("generic");
					styleShort.setHref(GEOSERVER_ENDPOINT + "styles/generic.json");
					
					layerGroup.getStyles().getStyle().add(styleShort);
				} 
				
				/** TRASFORMO LA CLASSE JAVA IN JSON DA MANDARE A GEOSERVER **/
				String jsonBody = gson.toJson(new GSLayerGroupWrapper(layerGroup));
				/** CREO LA CHIAMATA PUT DA MANDARE ALL'API DI GEOSERVER **/
				restPutJson(METHOD_PUT, GEOSERVER_ENDPOINT + GEOSERVER_WORKSPACES + nameWs + "/layergroups/EditingLayer_tematismo", jsonBody);
				LOG.debug("WRAPPERGEO SERVICE --> E' stato modificato il layerGroup EditingLayer_tematismo nel workspaces {}, aggiungendo il layer {}", nameWs, identificativo.concat(".json"));
			
			}
			
		} catch (IOException ioe) {
			LOG.debug("WRAPPERGEO SERVICE --> Non esiste nessun layerGroup EditingLayer_tematismo nel workspace {}, nè verrà creato uno nuovo.", nameWs);
			/** MI CREO LA LISTA DEI LAYER GROUP EditingLayer_tematismo E EditingLayer_areaTematica **/
			List<GSLayerGroup> layerGroups = this.createLayerGroupObj(identificativo, nameWs);
			/** MI CICLO LA LISTA CONTENENTE I LAYERGROUP EditingLayer_tematismo E EditingLayer_areaTematica per poterli creare su geoserver */
			for (GSLayerGroup groupLayer : layerGroups) {
				/** TRASFORMO LA CLASSA JAVA IN JSON DA MANDARE A GEOSERVER */
				String jsonBody = gson.toJson(new GSLayerGroupWrapper(groupLayer));
				/** CREO LA CHIAMATA POST DA MANDARE ALL'API DI GEOSERVER **/ 
				restPostJson(METHOD_POST, GEOSERVER_ENDPOINT + GEOSERVER_WORKSPACES + nameWs + "/layergroups", jsonBody);
			}	
			
			LOG.debug("WRAPPERGEO SERVICE --> E' stata creato il layerGroup EditingLayer_tematismo nel workspaces {}", nameWs);
		
		} catch (Exception e) {
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile creare/modificare il layerGroup EditingLayer_tematismo nel workspace {}", nameWs);
			throw new WrappergeoStorageException(e);

		}
		
		return layerGroup;
	}
	private List<GSLayerGroup> createLayerGroupObj(String identificativo, String nameWs) {
		
		/** CREO I NOMI DEL TEMATISMO E DELL'AREA TEMATICA **/
		String[] nameLayerGroup = {"EditingLayer_tematismo", "EditingLayer_areaTematica"};
		/** CREO IL TITOLO CHE DOVRA' AVERE IL TEMASTIMO E L'AREA TEMATICA **/
		String[] titleLivelli = {"Livelli pubblicati", "Livelli"};
		
 		List<GSLayerGroup> layerGroups = new ArrayList<>();
		
		for (int i = 0; i <= 1; i++) {
			/** LAYER **/
			GSPublished layer = new GSPublished();
			if(nameLayerGroup[i].indexOf("EditingLayer_tematismo") != -1) {
				
				layer.setType("layer");
				layer.setName("urbamid:"+identificativo);
				layer.setHref(GEOSERVER_ENDPOINT + GEOSERVER_WORKSPACES + nameWs + "/layers/" + identificativo.concat(".json"));
			} else {
				
				layer.setType("layerGroup");
				layer.setName(nameLayerGroup[0]);
			}
			
			List<GSPublished> listaLayer = new ArrayList<>();
			listaLayer.add(layer);
			
			GSPublishables layers = new GSPublishables();
			layers.setPublished(listaLayer);
			
			/** STYLE **/	
			GSStyleShort styleShort = new GSStyleShort();
			styleShort.setName("generic");
			styleShort.setHref(GEOSERVER_ENDPOINT + "styles/generic.json");			
			List<GSStyleShort> style = new ArrayList<GSStyleShort>();
			style.add(styleShort);
			
			GSStyles styles = new GSStyles();
			styles.setStyle(style);
			
			/** WORKSPACE **/
			GSWorkspaceLG workspace = new GSWorkspaceLG();
			workspace.setName("urbamid");
			
			GSLayerGroup layerGroup = new GSLayerGroup();
			
			layerGroup.setName(nameLayerGroup[i]);
			layerGroup.setMode("SINGLE");
			layerGroup.setTitle(titleLivelli[i]);
			layerGroup.setWorkspace(workspace);
			layerGroup.setPublishables(layers);
			layerGroup.setStyles(styles);
			
			/** AGGIUNGO IL LAYER GROUP ALLA LISTA **/
			layerGroups.add(layerGroup);
		}
		
		return layerGroups;
	}


	public String gsGetLayerGroup(String nameWs, String nameLayerGroup) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/layergroups/" + nameLayerGroup;
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	

	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il layer di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResFeatureType featureType(String nameWs, String nameFt) throws WrapperGeoServiceException {
		
		ResFeatureType response = getGSFeatureType(nameWs, nameFt );
		return response;
	}
	private ResFeatureType getGSFeatureType(String nameWs, String nameFt) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureType featureType = new ResFeatureType();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero la featureType {} del workspaces {} e datasource {}.", nameFt, nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà recuperata la featureType "+nameFt+" del workspaces " + nameWs + " e datastore "+DS);
			
			String response = this.gsGetFeatureType(nameWs, nameFt);
			if( !response.isEmpty() ) {
				
				featureType = objectMapper.readValue(response, ResFeatureType.class);
				informazioniDiLog.append("E' stata recuperata la featureType "+nameFt+" del workspaces " + nameWs + " e datastore "+DS);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- la featureType {} non è presente nel workspaces {} e datastore {}.", nameFt, nameWs, DS);
				throw new WrapperGeoServiceException("la featureType "+nameFt+" non è presente nel workspaces "+nameWs+" e datastore "+DS+".");
			}
			return featureType;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare la featureType {} del workspace {} e datastore {}.", nameFt, nameWs, DS);
			throw new WrappergeoStorageException("Non è stato possibile recuperare la featureType "+nameFt+" del workspace "+nameWs+" e datastore "+DS+".");
		}
	}
	@Override
	public ResFeatureType getGSFeatureType(String nameWs, String nameDs, String nameFt)  {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureType featureType = new ResFeatureType();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero la featureType {} del workspaces {} e datasource {}.", nameFt, nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà recuperata la featureType "+nameFt+" del workspaces " + nameWs + " e datastore "+DS);
			
			String response = this.gsGetFeatureType(nameWs, nameDs, nameFt);
			if( !response.isEmpty() ) {
				
				featureType = objectMapper.readValue(response, ResFeatureType.class);
				informazioniDiLog.append("E' stata recuperata la featureType "+nameFt+" del workspaces " + nameWs + " e datastore "+DS);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- la featureType {} non è presente nel workspaces {} e datastore {}.", nameFt, nameWs, DS);
				throw new WrapperGeoServiceException("la featureType "+nameFt+" non è presente nel workspaces "+nameWs+" e datastore "+DS+".");
			}
			return featureType;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare la featureType {} del workspace {} e datastore {}.", nameFt, nameWs, DS);
//			throw new WrappergeoStorageException("Non è stato possibile recuperare la featureType "+nameFt+" del workspace "+nameWs+" e datastore "+DS+".");
			return null;
		}
	}
	@Override
	public ResFeatureTypeM getGSFeatureTypeM(String nameWs, String nameDs, String nameFt)  {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureTypeM featureType = new ResFeatureTypeM();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero la featureType {} del workspaces {} e datasource {}.", nameFt, nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà recuperata la featureType "+nameFt+" del workspaces " + nameWs + " e datastore "+DS);
			
			String response = this.gsGetFeatureType(nameWs, nameDs, nameFt);
			if( !response.isEmpty() ) {
				
				featureType = objectMapper.readValue(response, ResFeatureTypeM.class);
				informazioniDiLog.append("E' stata recuperata la featureType "+nameFt+" del workspaces " + nameWs + " e datastore "+DS);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- la featureType {} non è presente nel workspaces {} e datastore {}.", nameFt, nameWs, DS);
				throw new WrapperGeoServiceException("la featureType "+nameFt+" non è presente nel workspaces "+nameWs+" e datastore "+DS+".");
			}
			return featureType;
		} catch (Exception e) {
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare la featureType {} del workspace {} e datastore {}.", nameFt, nameWs, DS);
			return null;
		}
	}
	public String gsGetFeatureType(String nameWs, String nameFt) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/"+DS+"/featuretypes/"+nameFt+".json";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}	
	public String gsGetFeatureType(String nameWs, String nameDs, String nameFt) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/"+nameDs+"/featuretypes/"+nameFt+".json";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	
	
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il layer di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String modFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException {
		
		String logPublish = modGSFeatureType(nameWs, ft );
		return new StringBuilder(logPublish).toString();
	}
	private String modGSFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureType featureType = new ResFeatureType();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("modifico la featureType {} del workspaces {} e datasource {}.", ft.getName(), nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà modificata la featureType "+ft.getName()+" del workspaces " + nameWs + " e datastore "+DS);
			
			/*Recupero la featureType*/
			featureType = this.getGSFeatureType(nameWs, ft.getName());
			if ( featureType!=null ) {
				
				if (featureType.getFeatureType().getAttributes()!=null &&
						featureType.getFeatureType().getAttributes().getAttribute()!=null && 
						featureType.getFeatureType().getKeywords()!=null &&
						featureType.getFeatureType().getKeywords().getString()!=null) {

					featureType.getFeatureType().setKeywords( ft.getKeywords());
				}
				featureType.getFeatureType().setEnabled(true);
				featureType.getFeatureType().setTitle( ft.getTitle() );
				
				boolean response = this.gsRePublishFeatureType(nameWs, featureType.getFeatureType());
				if( response ) {
					
					informazioniDiLog.append("Sono state aggiunte le keywords alla featureType " + ft.getName());
				} else {
					
					LOG.error("WRAPPERGEO SERVICE -- non è stato possibile aggiungere gli attributi come keywords alla featureType {}.", ft.getName());
					throw new WrapperGeoServiceException("Non è stato possibile aggiungere gli attributi come keywords alla featureType "+ft.getName()+".");
				}
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- la featureType {} non è presente nel workspaces {} e datastore {}.", ft.getName(), nameWs, DS);
				throw new WrapperGeoServiceException("la featureType "+ft.getName()+" non è presente nel workspaces "+nameWs+" e datastore "+DS+".");
			}
			
			informazioniDiLog.append("E' stata modificata la featureType.");
			return informazioniDiLog.toString();
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile modificare la featureType {} del workspace {} e datastore {}.", ft.getName(), nameWs, DS);
			throw new WrappergeoStorageException("Non è stato possibile modificare la featureType "+ft.getName()+" del workspace "+nameWs+" e datastore "+DS+".");
		}
	}
	@Override
	public String modKeywordsFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureType featureType = new ResFeatureType();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("modifico la featureType {} del workspaces {} e datasource {}.", ft.getName(), nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà modificata la featureType "+ft.getName()+" del workspaces " + nameWs + " e datastore "+DS);
			
			/*Recupero la featureType*/
			featureType = this.getGSFeatureType(nameWs, ft.getName());
			if ( featureType!=null ) {
				
				List<String> newKeywords = new ArrayList<String>();
				for (String keyword : featureType.getFeatureType().getKeywords().getString()){
					String newKeyword = new String();
					if ( keyword.contains("vocabulary") ) {
						keyword = keyword.replace("vocabulary=", "").replaceAll("\\\\" , "").replace(";", "");
						newKeyword = keyword;
					}else {
						newKeyword=keyword;
					}
					newKeywords.add(newKeyword);
				}
				featureType.getFeatureType().getKeywords().setString(newKeywords);
				
				boolean response = this.gsModFeatureType(nameWs, featureType.getFeatureType());
				if( response ) {
					
					informazioniDiLog.append("E' stata modificata la featureType "+ft.getName()+" del workspaces " + nameWs + " e datastore "+DS);
				} else {
					
					LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile modificare la featureType {} del workspace {} e datastore {}.", ft.getName(), nameWs, DS);
					throw new WrapperGeoServiceException("Non è stato possibile modificare la featureType "+ft.getName()+" del workspace "+nameWs+" e datastore "+DS+".");
				}
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- la featureType {} non è presente nel workspaces {} e datastore {}.", ft.getName(), nameWs, DS);
				throw new WrapperGeoServiceException("la featureType "+ft.getName()+" non è presente nel workspaces "+nameWs+" e datastore "+DS+".");
			}
			
			informazioniDiLog.append("E' stata modificata la featureType.");
			return informazioniDiLog.toString();
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile modificare la featureType {} del workspace {} e datastore {}.", ft.getName(), nameWs, DS);
			throw new WrappergeoStorageException("Non è stato possibile modificare la featureType "+ft.getName()+" del workspace "+nameWs+" e datastore "+DS+".");
		}
	}
	public boolean gsModFeatureType(String nameWs, GSFeatureType ft) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
	    String xmlString = this.jaxbObjectToXML(ft);
	    String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/"+DS+"/featuretypes/"+ft.getName()+".json";
		int sendRESTint = restPutXml(METHOD_PUT, url, xmlString);
		return 200 == sendRESTint;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameL è il layer di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String delFeatureType(String nameWs, String nameFt) throws WrapperGeoServiceException {
		
		String logPublish = delGSFeatureType(nameWs, nameFt );
		return new StringBuilder(logPublish).toString();
	}
	private String delGSFeatureType(String nameWs, String nameFt) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResFeatureType featureType = new ResFeatureType();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Elimino la featureType {} e relativo layer del workspaces {} e datastores {}.", nameFt, nameWs, DS);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà eliminato la featureType "+nameFt+" e relativo layer del workspaces " + nameWs+" e datastore "+DS);
			
			String response = this.gsDelFeatureType(nameWs, nameFt);
			if( !response.isEmpty() ) {
				
				informazioniDiLog.append("E' stata eliminata la featureType "+nameFt+" e relativo layer del workspaces " + nameWs+" e datastores "+ DS);
			} 
			
			informazioniDiLog.append("E' stato eliminato la featuretype e file associato.");
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			LOG.debug("WRAPPERGEO SERVICE -- Non è presente la featureType {} nel workspaces {} e datastores {}.", nameFt, nameWs, DS);
			return informazioniDiLog.append("Non è presente la featureType.").toString();
			
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile eliminare la featureType {} e relativo layer del workspace {} e datastores {}.", nameFt, nameWs, DS);
			throw new WrappergeoStorageException("Non è stato possibile eliminare la featureType "+nameFt+" e relativo layer del workspace "+nameWs+" e datastores "+DS+".");
		}
	}
	public String gsDelFeatureType(String nameWs, String nameFt) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/"+DS+"/featuretypes/"+nameFt+"?recurse=true";
		String response = restCall(METHOD_DELETE, url, "", "application/json", "application/json");
		return response;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param GSFeatureType è la featureType da pubblicare
	 *  
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String publishFeatureType(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException {
		
		String logPublish = publishFT(nameWs, ft);
		return new StringBuilder(logPublish).toString();
	}
	private String publishFT(String nameWs, GSFeatureType ft) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		if(LOG.isDebugEnabled()) {
			LOG.debug("Pubblico la featureType {} nel workspace {} e datastore {}.", ft.getName(), nameWs, DS);
		}
		
		this.checkParams(ft.getName());
		try {
			ft.setName( PREFIX_TAB+ft.getName() );
			informazioniDiLog.append("Verrà pubblicata la featureType " + ft.getName());
			boolean response = this.gsPublishFeatureType(nameWs, ft);
			if( response ) {
				
				informazioniDiLog.append("E' stata pubblicata la featureType " + ft.getName());
			} else {
				
				LOG.error("PUBLISHER WRAPPERGEO SERVICE -- nome della featureType {} non e' un nome valido.", ft.getName());
				throw new WrapperGeoServiceException("Nome della featureType non e' un nome valido.");
			}
			
			informazioniDiLog.append("Verrà RI-pubblicata la featureType " + ft.getName());
			response = this.gsRePublishFeatureType(nameWs, ft);
			if( response ) {
				
				informazioniDiLog.append("E' stata pubblicata la featureType " + ft.getName());
			} else {
				
				LOG.error("PUBLISHER WRAPPERGEO SERVICE -- nome della featureType {} non e' un nome valido.", ft.getName());
				throw new WrapperGeoServiceException("Nome della featureType non e' un nome valido.");
			}
			
			informazioniDiLog.append("Verrà aggiornata la featureType "+ft.getName()+" appena pubblicata per aggiungere le keywords" );
			ResFeatureType featureType = this.getGSFeatureType(nameWs, ft.getName());
			if (featureType!=null && featureType.getFeatureType().getAttributes()!=null &&
					featureType.getFeatureType().getAttributes().getAttribute()!=null && 
					featureType.getFeatureType().getKeywords()!=null &&
					featureType.getFeatureType().getKeywords().getString()!=null) {
				
				List<GSAttribute> attributes = featureType.getFeatureType().getAttributes().getAttribute();
				for (GSAttribute attribute : attributes) {
					String keyword = new String(PREFIX_ATTR+attribute.getName()+"@"+attribute.getName());
					featureType.getFeatureType().getKeywords().getString().add(keyword);
				}
				
				response = this.gsRePublishFeatureType(nameWs, featureType.getFeatureType());
				if( response ) {
					
					informazioniDiLog.append("Sono state aggiunte le keywords alla featureType " + ft.getName());
				} else {
					
					LOG.error("PUBLISHER WRAPPERGEO SERVICE -- non è stato possibile aggiungere gli attributi come keywords alla featureType {}.", ft.getName());
					throw new WrapperGeoServiceException("Non è stato possibile aggiungere gli attributi come keywords alla featureType "+ft.getName()+".");
				}
			}
			
			informazioniDiLog.append("E' stata pubblicata la featureType " + ft.getName() + ".");
			return informazioniDiLog.toString();
		} catch (Exception e) {
			
			LOG.error("PUBLISHER WRAPPERGEO SERVICE --> Non e' stato possibile pubblicare la featureType {}.", ft.getName());
			throw new WrappergeoStorageException("Non e' stato possibile pubblicare la featureType "+ft.getName()+".");
		}
	}
	public boolean gsPublishFeatureType(String nameWs, GSFeatureType ft) throws IOException {
		String xml = "<featureType>" +
				"<name>"+ft.getName()+"</name>" +
				"<nativeCRS>PROJCS[\"WGS_1984_World_Mercator\",GEOGCS[\"GCS_WGS_1984\",DATUM[\"D_WGS_1984\",SPHEROID[\"WGS_1984\", 6378137.0, 298.257223563]],PRIMEM[\"Greenwich\", 0.0],UNIT[\"degree\", 0.017453292519943295],AXIS[\"Longitude\", EAST],AXIS[\"Latitude\", NORTH]],PROJECTION[\"Mercator_2SP\"],PARAMETER[\"standard_parallel_1\", 0.0],PARAMETER[\"latitude_of_origin\", 0.0],PARAMETER[\"central_meridian\", 0.0],PARAMETER[\"false_easting\", 0.0],PARAMETER[\"false_northing\", 0.0],UNIT[\"m\", 1.0],AXIS[\"x\", EAST],AXIS[\"y\", NORTH]]</nativeCRS>" +
				"<enabled>true</enabled>";
				/**TITLE**/
				xml += ((ft.getTitle()!=null || !ft.getTitle().isEmpty()) ? "<title>"+ft.getTitle()+"</title>" : "");
				/**KEYWORDS**/
				xml += "<keywords>";
				GSKeywords keywords = ft.getKeywords();
				for ( String keyword : keywords.getString() ) {
					xml += "<string>"+keyword+ "</string>";
				}
				xml += "</keywords>";
		xml += "</featureType>";
	    
		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/" + DS + "/featuretypes";
		int sendRESTint = restPostXml(METHOD_POST, url, xml);
		return 201 == sendRESTint;
	}
	public boolean gsRePublishFeatureType(String nameWs, GSFeatureType ft) throws IOException {
		
		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/datastores/" + DS + "/featuretypes/"+ft.getName()+"?recalculate=nativebbox,latlonbbox";
		Gson gson = GsonFactory.getGson();
		String jsonBody = gson.toJson(new GSFeatureTypeWrapper(ft));
		
		int sendRESTint = restPostJson(METHOD_PUT, url, jsonBody);
		return 200 == sendRESTint;
	}
	/********************************************************************************************************************************************************/
	
	/********************************************************************************************************************************************************/
	/******************************************************** STYLE *****************************************************************************************/
	/**
	 * @param nameWs è il workspace di geoserver
	 *  
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResStyles styles(String nameWs) throws WrapperGeoServiceException {
		
		ResStyles res = getGSStyles(nameWs);
		return res;
	}
	private ResStyles getGSStyles(String nameWs) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResStyles styles = new ResStyles();
		ObjectMapper objectMapper = new ObjectMapper();

		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero gli style del workspaces {}.", nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verranno recuperati gli style del workspaces " + nameWs);
			
			String response = this.gsGetStyles(nameWs);
			if( !response.isEmpty() ) {
				
				styles = objectMapper.readValue(response, ResStyles.class);
				informazioniDiLog.append("Sono stati recuperati gli styles del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
			}
			return styles;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare gli style del workspace {}.", nameWs);
			throw new WrappergeoStorageException("Non è stato possibile recuperare gli style del workspace "+nameWs+".");
		}
	}
	public String gsGetStyles(String nameWs) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/styles";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameS è lo style di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResStyle style(String nameWs, String nameS) throws WrapperGeoServiceException {
		
		ResStyle response = getGSStyle(nameWs, nameS );
		return response;
	}
	private ResStyle getGSStyle(String nameWs, String nameS) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResStyle style = new ResStyle();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero lo style {} del workspaces {}.", nameS, nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà recuperato lo style "+nameS+" del workspaces " + nameWs);
			
			String response = this.gsGetStyle(nameWs, nameS);
			if( !response.isEmpty() ) {
				
				style = objectMapper.readValue(response, ResStyle.class);
				informazioniDiLog.append("E' stato recuperato lo style "+nameS+" del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
			}
			return style;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare lo style {} del workspace {}.", nameS, nameWs);
			throw new WrappergeoStorageException("Non è stato possibile recuperare lo style "+nameS+" del workspace "+nameWs+".");
		}
	}
	public String gsGetStyle(String nameWs, String nameS) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/styles/"+nameS+".json";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameS è lo style di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public ResStyle newStyle(String nameWs, String nameS, String nameFS) throws WrapperGeoServiceException {
		
		ResStyle response = newGSStyle(nameWs, nameS, nameFS );
		return response;
	}
	private ResStyle newGSStyle(String nameWs, String nameS, String nameFS) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResStyle style = new ResStyle();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Creo lo style {} con file {} del workspaces {}.", nameS, nameFS, nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà creato lo style "+nameS+" con file "+nameFS+" del workspaces " + nameWs);
			
			GSStyle gsStyle = new GSStyle();
			gsStyle.setFilename(nameFS);
			gsStyle.setName(nameS);
			
			boolean response = this.gsNewStyle(nameWs, gsStyle);
			if( response ) {
				
				informazioniDiLog.append("E' stato creato lo style "+nameS+" del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è stato creato lo style {} del workspaces {}.", nameS, nameWs);
				throw new WrapperGeoServiceException("Non è stato creato lo style "+nameS+" nel workspaces "+nameWs+".");
			}

			return style;
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile creare lo style {} del workspace {}.", nameS, nameWs);
			throw new WrappergeoStorageException("Non è stato possibile creare lo style "+nameS+" del workspace "+nameWs+".");
		}
	}
	public boolean gsNewStyle(String nameWs, GSStyle style) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String xmlString = this.jaxbObjectToXML(style);
		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/styles";
		int sendRESTint = restPostXml(METHOD_POST, url, xmlString);
		return 201 == sendRESTint;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameS è lo style di geoserver
	 * @param nameFS è il nome del sld legato allo style di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String delStyle(String nameWs, String nameS, String nameFS) throws WrapperGeoServiceException {
		
		String logDelStyle = this.delGSStyle(nameWs, nameS, nameFS);
		return new StringBuilder(logDelStyle).toString();
	}
	private String delGSStyle(String nameWs, String nameS, String nameFS) throws WrapperGeoServiceException {
		
		Path rootStyleDirectory = Paths.get(this.pathService.jobDataStyleDirectory());
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Elimino lo style {} del workspaces {}.", nameS, nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà elinato lo style "+nameS+" del workspaces " + nameWs);
			ResStyle style = this.getGSStyle(nameWs, nameS);
			
			String response = this.gsDelStyle(nameWs, nameS);
			if( !response.isEmpty() ) {
				
				informazioniDiLog.append("E' stato eliminato lo style "+nameS+" del workspaces " + nameWs);

				String pathFilename = rootStyleDirectory.toFile().getAbsolutePath() + File.separator + style.getStyle().getFilename();
				File f= new File(pathFilename);            
				if(f.delete()) {  
					
					informazioniDiLog.append("E' stato eliminato il file sld dello style "+nameS+" del workspaces " + nameWs);  
				} else {
					
					LOG.error("WRAPPERGEO SERVICE -- non è stato eliminato il file sld dello style {} del workspaces {}.", nameS, nameWs);
					throw new WrapperGeoServiceException("Non stato eliminato il file sld dello style "+nameS+" nel workspaces "+nameWs+".");
				}  
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente lo style {} del workspaces {}.", nameS, nameWs);
				throw new WrapperGeoServiceException("Non è presente lo style "+nameS+" nel workspaces "+nameWs+".");
			}
			
			informazioniDiLog.append("E' stato eliminato lo style e file associato.");
			return informazioniDiLog.toString();
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile eliminare lo style {} del workspace {}.", nameS, nameWs);
			throw new WrappergeoStorageException("Non è stato possibile eliminare lo style "+nameS+" del workspace "+nameWs+".");
		}
	}
	public String gsDelStyle(String nameWs, String nameS) throws IOException {

		String url = GEOSERVER_ENDPOINT + "/styles/"+nameS+"?recurse=true";
		String response = restCall(METHOD_DELETE, url, "");
		return response;
	}
	
	/**
	 * @param nameWs è il workspace di geoserver
	 * @param nameS è lo style di geoserver
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws WrapperGeoServiceException 
	 */
	@Override
	public String publishStyle(String nameWs, GSStyle style) throws WrapperGeoServiceException {
		
		String logPublish = gsPublishStyle(nameWs, style );
		return new StringBuilder(logPublish).toString();
	}
	private String gsPublishStyle(String nameWs, GSStyle style) throws WrapperGeoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		ResStyle mStyle = new ResStyle();
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Recupero lo style {} del workspaces {}.", style.getName(), nameWs);
		}
		
		this.checkParams(nameWs);
		try {
			
			informazioniDiLog.append("Verrà recuperato lo style "+style.getName()+" del workspaces " + nameWs);
			
			String responseStyle = this.gsGetStyle(nameWs, style.getName());
			if( !responseStyle.isEmpty() ) {
				
				mStyle = objectMapper.readValue(responseStyle, ResStyle.class);
				informazioniDiLog.append("E' stato recuperato lo style "+style.getName()+" del workspaces " + nameWs);
			} else {
				
				LOG.error("WRAPPERGEO SERVICE -- non è presente il workspaces {}.", nameWs);
				throw new WrapperGeoServiceException("Non è presente il workspaces "+nameWs+".");
			}
			
//			if (mStyle!=null) {
//				/**Modifica**/
//			} else {
//				
//			}
			
			boolean response = this.gsPostStyle(nameWs, style);
			if( response ) {
				
				informazioniDiLog.append("E' stata pubblicata lo stile " + style.getName());
			} else {
				
				LOG.error("PUBLISHER WRAPPERGEO SERVICE -- nome dello style {} non e' un nome valido.", style.getName());
				throw new WrapperGeoServiceException("Nome dello style non e' un nome valido.");
			}
		    
			informazioniDiLog.append("E' stato pubblicato lo style " + style.getName() + ".");
			return informazioniDiLog.toString();
		} catch (Exception e) {
			
			LOG.error("WRAPPERGEO SERVICE --> Non è stato possibile recuperare lo style {} del workspace {}.", style.getName(), nameWs);
			throw new WrappergeoStorageException("Non è stato possibile recuperare lo style "+style.getName()+" del workspace "+nameWs+".");
		}
	}
	public boolean gsPostStyle(String nameWs, GSStyle style) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(style);
		String url = GEOSERVER_ENDPOINT + "/workspaces/" + nameWs + "/styles/" + style.getName();
		int sendRESTint = restPostJson(METHOD_POST, url, jsonString);
		return 201 == sendRESTint;
	}
	
	
	
	
	
	
	
	
	public String restCall(String method, String url, String xmlPostContent) throws IOException {
		return restCall(method, url, xmlPostContent, "application/json", "application/json");
	}
	
	public String restCall(String method, String urlEncoded, String postData, String contentType, String accept)
			throws IOException {
		
		boolean doOut = !METHOD_DELETE.equals(method) && postData != null;

		URL url = new URL(urlEncoded);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}
		connection.setRequestMethod(method.toString());

		connection.connect();
	    StringBuffer chaine = new StringBuffer("");
		InputStream inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = rd.readLine()) != null) {
            chaine.append(line);
        }
		return chaine.toString();
	}
	
	
	
	public int restPutXml(String method, String url, String xmlPostContent) throws IOException {
		HttpURLConnection connection = restPost(method, url, xmlPostContent, "application/xml", "application/xml");
		return connection.getResponseCode();
	}
	public int restPostXml(String method, String url, String xmlPostContent) throws IOException {
		HttpURLConnection connection = restPost(method, url, xmlPostContent, "application/xml", "application/xml");
		return connection.getResponseCode();
	}
	
	public int restPutJson(String method, String url, String jsonPutContent) throws IOException {
		HttpURLConnection connection = restPost(method, url, jsonPutContent, "application/json", "application/json");
		return connection.getResponseCode();
	}
	public int restPostJson(String method, String url, String jsonPostContent) throws IOException {
		HttpURLConnection connection = restPost(method, url, jsonPostContent, "application/json", "application/json");
		return connection.getResponseCode();
	}
	private HttpURLConnection restPost(String method, String urlEncoded, String postData, String contentType,
			String accept) throws MalformedURLException, IOException {
		StringReader postDataReader = postData == null ? null : new StringReader(postData);
		boolean doOut = !METHOD_DELETE.equals(method) && postDataReader != null;
		// boolean doIn = true; // !doOut

		URL url = new URL(urlEncoded);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		// uc.setDoInput(false);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}

		connection.setRequestMethod(method.toString());
		connection.connect();
		if (connection.getDoOutput()) {
			Writer writer = new OutputStreamWriter(connection.getOutputStream());
			char[] buffer = new char[1024];

			Reader reader = new BufferedReader(postDataReader);
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}

			writer.flush();
			writer.close();
		}
		return connection;
	}
	
	/**
	 * @param nome
	 */
	private void checkParams(String name)  {
		if (name!=null) {
			/**Controllo se i file caricati sono vuoti**/
			if (name.isEmpty()) {
				LOG.error("CONVERTER WRAPPERGEO SERVICE -- nome {} non è un nome valido.", name);
//				throw new WrapperGeoServiceException("Nome non è un nome valido.");
			}
		} else {
			LOG.error("CONVERTER WRAPPERGEO SERVICE -- Valore errato: {}.", name);
//			throw new WrapperGeoServiceException("Valore errato");
		}
	}
	
	
	private static String jaxbObjectToXML(ResFeatureTypeM lg)  {
		
		try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(ResFeatureTypeM.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(lg, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static String jaxbObjectToXML(GSFeatureTypeM f)  {
		
		try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(GSFeatureTypeM.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(f, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	private static String jaxbObjectToXML(GSLayerGroup lg)  {
		
		try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(GSLayerGroup.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(lg, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static String jaxbObjectToXML(GSLayer l)  {
		
		try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(GSLayer.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(l, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static String jaxbObjectToXML(GSFeatureType ft)  {
		
		try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(GSFeatureType.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(ft, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static String jaxbObjectToXML(GSStyle style)  {
		
		try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(GSStyle.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(style, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
