package it.eng.tz.urbamid.wrappergeo.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSDataStoreShort;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSDefaultStyle;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSFeatureType;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSKeywords;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayer;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSLayerShort;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSResFeatureTypeDB;
import it.eng.tz.urbamid.wrappergeo.geoserver.entities.GSStyle;
import it.eng.tz.urbamid.wrappergeo.geoserver.service.GeoserverService;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureType;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureTypeM;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResFeatureTypes;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayer;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayers;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResStyle;
import it.eng.tz.urbamid.wrappergeo.web.entities.ResStyles;
import it.eng.tz.urbamid.wrappergeo.web.response.ResponseData;

@RestController
@RequestMapping("/wrappergeo/rest/api/geoserver")
@Api(value = "urbamid geoserver", tags= {"GeoserverWrapperGeo"})
public class ApiGeoserverController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(ApiGeoserverController.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	public final String DS				= "dbUrbamid";
	
	@Autowired
	private GeoserverService geoserverService;
	
//	@ApiOperation("Recupera i layerGroups da geoserver")
//	@RequestMapping(value="/layergroups", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
//	public @ResponseBody ResponseData layerGroups(HttpServletRequest request,
//			@ApiParam(value="Nome del workspace", required=true)
//			@RequestParam(name="workspaces", required=true) String workspaces) {
//		
//		logger.debug("GET layergroups");
//		ResponseData response = null;
//		try {
//			
//			ResLayerGroups layergroups = geoserverService.layerGroups(workspaces);
//			response = new ResponseData(true, layergroups, layergroups.getLayerGroups().getLayerGroup().size(), 
//					layergroups.getLayerGroups().getLayerGroup().size(), null);
//			response.setMessage(SUCCESS);
//			logger.debug("GET REQUEST eseguita con successo. Sono stati recuperati i layergroup del workspaces {}.", workspaces);
//		} catch (Exception e) {
//			
//			logger.error("Error in wrapper geoserver", e);
//			response = new ResponseData(false, null,0, 0, e.getMessage());
//			response.setMessage(ERROR);
//		}
//		return response;
//	}
//	
//	@ApiOperation("Recupera il layerGroup da geoserver")
//	@RequestMapping(value="/layergroup", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
//	public @ResponseBody ResponseData layerGroup(HttpServletRequest request,
//			@ApiParam(value="Nome del workspace", required=true)
//			@RequestParam(name="workspaces", required=true) String workspaces,
//			@ApiParam(value="Nome del layerGroup", required=true)
//			@RequestParam(name="layergroup", required=true) String layergroup) {
//		
//		logger.debug("GET layergroup");
//		ResponseData response = null;
//		try {
//			
//			ResLayerGroup resLayergroup = geoserverService.layerGroup(workspaces, layergroup);
//			response = new ResponseData(true, resLayergroup, 1, 1, null);
//			response.setMessage(SUCCESS);
//			logger.debug("GET REQUEST eseguita con successo. E' stato recuperato il layergroup {} del workspaces {}.", layergroup, workspaces);
//		} catch (Exception e) {
//			
//			logger.error("Error in wrapper geoserver", e);
//			response = new ResponseData(false, null,0, 0, e.getMessage());
//			response.setMessage(ERROR);
//		}
//		return response;
//	}
//	
//	@ApiOperation("Modifica il layergroup da geoserver prevede l'aggiunta di un layer con relativo style")
//	@RequestMapping(value="/modLayerGroup", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
//	public @ResponseBody ResponseData modLayerGroup(HttpServletRequest request,
//			@ApiParam(value="Nome del workspace", required=true)
//			@RequestParam(name="workspaces", required=true) String workspaces,
//			@ApiParam(value="Nome del layergroup", required=true)
//			@RequestParam(name="layergroup", required=true) String layergroup,
//			@ApiParam(value="Nome del layer da aggiungere", required=true)
//			@RequestParam(name="layer", required=true) String layer,
//			@ApiParam(value="Operazione da eseguire sul layergroup. Valori possibili 'I':insert e 'D':delete", required=true)
//			@RequestParam(name="operation", required=true) String operation) {
//		
//		logger.debug("POST modifica layer");
//		ResponseData response = null;
//		try {
//			
//			GSLayerGroup lg = new GSLayerGroup();
//			/*BASE INFO*/
//			lg.setName(layergroup);
//			/*AGGIUNGO LAYER*/
//			GSPublishables gSPublishables = new GSPublishables();
//			List<GSPublished> list = new ArrayList<GSPublished>();
//			GSPublished pub = new GSPublished();
//			pub.setName(layer);
//			list.add(pub);
//			gSPublishables.setPublished(list);
//			lg.setPublishables(gSPublishables);
////			/*AGGIUNGO STYLE*/
////			GSStyles styles = new GSStyles();
////			List<GSStyleShort> lisStyle = new ArrayList<GSStyleShort>();
////			GSStyleShort defStyle = new GSStyleShort();
////			defStyle.setName(style);
////			lisStyle.add(defStyle);
////			styles.setStyle(lisStyle);
////			lg.setStyles(styles);
//
//			if (operation!=null && !operation.isEmpty()) {
//				String logAddLayerToLg = geoserverService.modLayerGroup(workspaces, lg, operation);
//			} else {
//				response = new ResponseData(false, null, 1, 1, null);
//				response.setMessage(ERROR);
//				logger.debug("DELETE REQUEST non eseguita. Deve essere indicata un operazione.", layer, workspaces);
//			}
//			
//			response = new ResponseData(true, null, 1, 1, null);
//			response.setMessage(SUCCESS);
//			logger.debug("DELETE REQUEST eseguita con successo. E' stato modificato il Layer del workspaces {}.", layer, workspaces);
//		} catch (Exception e) {
//			
//			logger.error("Error in wrapper geoserver", e);
//			response = new ResponseData(false, null,0, 0, e.getMessage());
//			response.setMessage(ERROR);
//		}
//		return response;
//	}
//	
//	@ApiOperation("Elimina il layergroup da geoserver")
//	@RequestMapping(value="/delLayerGroup", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
//	public @ResponseBody ResponseData delLayerGroup(HttpServletRequest request,
//			@ApiParam(value="Nome del workspace", required=true)
//			@RequestParam(name="workspaces", required=true) String workspaces,
//			@ApiParam(value="Nome del layergroup da eliminare", required=true)
//			@RequestParam(name="layergroup", required=true) String layergroup) {
//		
//		logger.debug("POST layergroup");
//		ResponseData response = null;
//		try {
//			
//			String logAddLayerToLg = geoserverService.delLayerGroup(workspaces, layergroup);
//			response = new ResponseData(true, null, 1, 1, null);
//			response.setMessage(SUCCESS);
//			logger.debug("DELETE REQUEST eseguita con successo. E' stato eliminato il layergroup {} del workspaces {}.", layergroup, workspaces);
//		} catch (Exception e) {
//			
//			logger.error("Error in wrapper geoserver", e);
//			response = new ResponseData(false, null,0, 0, e.getMessage());
//			response.setMessage(ERROR);
//		}
//		return response;
//	}
//	
//	@ApiOperation("Aggiungi un layer con style ad un layerGroup geoserver")
//	@RequestMapping(value="/addLayerToLayergroup", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
//	public @ResponseBody ResponseData addLayerToLayergroup(HttpServletRequest request,
//			@ApiParam(value="Nome del workspace", required=true)
//			@RequestParam(name="workspaces", required=true) String workspaces,
//			@ApiParam(value="Nome del layergroup", required=true)
//			@RequestParam(name="layergroup", required=true) String layergroup,
//			@ApiParam(value="Nome del layer", required=true)
//			@RequestParam(name="layer", required=true) String layer,
//			@ApiParam(value="Nome dello style da associare al layer. Se non associato viene utilizzato lo style di default del layer", required=true)
//			@RequestParam(name="style", required=true) String style) {
//		
//		logger.debug("POST addLayerToLayergroup");
//		ResponseData response = null;
//		try {
//			
//			String logAddLayerToLg = geoserverService.addLayerToLayergroup(workspaces, layergroup, layer, style);
//			response = new ResponseData(true, null, 1, 1, null);
//			response.setMessage(SUCCESS);
//			logger.debug("POST REQUEST eseguita con successo. E' stato modificato il layergroup {} del workspaces {}.", layergroup, workspaces);
//		} catch (Exception e) {
//			
//			logger.error("Error in wrapper geoserver", e);
//			response = new ResponseData(false, null,0, 0, e.getMessage());
//			response.setMessage(ERROR);
//		}
//		return response;
//	}
	
	@ApiOperation("Recupera le featureTypes da geoserver")
	@RequestMapping(value="/featureTypes", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData featureTypes(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces) {
		
		logger.debug("GET featureTypes");
		ResponseData response = null;
		try {
			
			ResFeatureTypes featureTypes = geoserverService.featureTypes(workspaces);
			response = new ResponseData(true, featureTypes, featureTypes.getFeatureTypes().getFeatureType().size(), 
					featureTypes.getFeatureTypes().getFeatureType().size(), null);
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. Sono state recuperate le featureTypes del workspaces {}.", workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Crea le featureTypes per geoserver")
	@RequestMapping(value="/addLayer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData featureTypes(HttpServletRequest request, 
			@ApiParam(value = "l'ID dal layer", required = true)
			@RequestParam(name = "idLayer", required = true) Long idLayer,
			@ApiParam(value = "Nome del layer", required = true)
			@RequestParam(name = "nomeLayer", required = true) String nomeLayer,
			@ApiParam(value = "L'identificativo del layer", required = true)
			@RequestParam(name = "identificativo", required = true) String identificativo) {
		
		logger.debug("POST addLayer");
		ResponseData response = null;
		try {
			
			if(idLayer != null || StringUtils.hasText(nomeLayer) || StringUtils.hasText(identificativo)) {
					
				GSResFeatureTypeDB responseRequest = geoserverService.createNewLayer("urbamid", idLayer, nomeLayer, identificativo);
					
				if(responseRequest != null) {
					/** INSERIRE IL LAYER NEL LAYER GROUP **/
					geoserverService.addLayerInLayerGroup("urbamid", responseRequest.getName());
					response = new ResponseData(true, responseRequest, 0, 0, null);
					response.setMessage(SUCCESS);
					logger.debug("POST REQUEST eseguita con successo. E' stata inserita/modificata la featureType nel workspaces {}.", "urbamid");
					
				} else {
					response = new ResponseData(false, null, 0, 0, null);
					response.setMessage(SUCCESS);
					logger.debug("POST REQUEST non eseguita con successo. Non è stata inserita/modificata la featureType nel workspaces {}.", "urbamid");
					
				}
			
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				logger.debug("POST REQUEST non eseguita con successo. Non è stato possibile inserire/modificare la featureType nel workspaces {}, in quanto i parametri sono vuoti", "urbamid");
				
			}
			
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	@ApiOperation("Recupera la featureType da geoserver")
	@RequestMapping(value="/featureType", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData featureType(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome della featureType da recuperare", required=true)
			@RequestParam(name="featuretype", required=true) String featuretype) {
		
		logger.debug("GET featureTypes");
		ResponseData response = null;
		try {
			
			featuretype = featuretype.startsWith("u_geo")?featuretype.toLowerCase():featuretype;
			ResFeatureType resFeatureType = geoserverService.featureType(workspaces, featuretype);
			response = new ResponseData(true, resFeatureType, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. E' stata recuperata la featureType {} del workspaces {}.", featuretype, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera la featureType da geoserver dato workspaces e datastore")
	@RequestMapping(value="/datastoreFeatureType", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData datastoreFeatureType(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome del datastores", required=true)
			@RequestParam(name="datastores", required=true) String datastores,
			@ApiParam(value="Nome della featureType da recuperare", required=true)
			@RequestParam(name="featuretype", required=true) String featuretype) {
		
		logger.debug("GET featureType del datastore");
		ResponseData response = null;
		try {
			
			featuretype = featuretype.startsWith("u_geo")?featuretype.toLowerCase():featuretype;
			ResFeatureType resFeatureType = geoserverService.getGSFeatureType(workspaces, datastores, featuretype);
			if (resFeatureType!=null) {
				response = new ResponseData(true, resFeatureType, 1, 1, null);
				response.setMessage(SUCCESS);
				logger.debug("GET REQUEST eseguita con successo. E' stato recuperata la featuretype {} del workspaces {} e datastores {}.", featuretype, workspaces);
			} else {
				ResFeatureTypeM resFeatureTypeM = geoserverService.getGSFeatureTypeM(workspaces, datastores, featuretype);
				response = new ResponseData(true, resFeatureTypeM, 1, 1, null);
				response.setMessage(SUCCESS);
				logger.debug("GET REQUEST eseguita con successo. E' stato recuperata la featuretype {} del workspaces {} e datastores {}.", featuretype, workspaces);
				
			}
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Modifica la featureType da geoserver")
	@RequestMapping(value="/modFeatureType", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData modFeatureType(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome del datastores", required=true)
			@RequestParam(name="featuretype", required=true) String featuretype,
			@ApiParam(value="Nuovo valore del titolo. Se passato null o stringa vuota non viene modificato", required=true)
			@RequestParam(name="title", required=true) String title,
			@ApiParam(value="Lista di keywords in formato stringa. Esempio 'chiave1=valore1;chiave2=valore2'", required=true)
			@RequestParam(name="keywords", required=true) String keywords) {
		
		logger.debug("POST modifica featureType");
		ResponseData response = null;
		try {
			
			GSFeatureType ft = new GSFeatureType();
			/*BASE INFO*/
			ft.setName(featuretype);
			ft.setTitle(title);
			/*KEYWORDS*/
			List<String> items = Arrays.asList(keywords.split("\\s*;\\s*"));
			List<String> strings = new ArrayList<String>();
			for (String keyword : items) {
				
				strings.add( keyword.replace("=", "@") );
			}
			GSKeywords gsKeywords = new GSKeywords();
			gsKeywords.setString(strings);
			ft.setKeywords(gsKeywords);
			GSDataStoreShort store = new GSDataStoreShort();
				store.setName(workspaces + ":" + DS);
			ft.setStore(store);
			
			String logAddLayerToLg = geoserverService.modFeatureType(workspaces, ft);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("POST REQUEST eseguita con successo. E' stata modificata la featureType {} e relativo Layer del workspaces {} e datastores {}.", featuretype, workspaces, DS);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Elimina la featureType da geoserver")
	@RequestMapping(value="/delFeatureType", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delFeatureType(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome del layer da eliminare", required=true)
			@RequestParam(name="featuretype", required=true) String featuretype) {		
		logger.debug("POST featureType");
		ResponseData response = null;
		try {
			
			String logAddLayerToLg = geoserverService.delFeatureType(workspaces, featuretype);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("DELETE REQUEST eseguita con successo. E' stato eliminato la featureType {} e relativo Layer del workspaces {} e datastores {}.", featuretype, workspaces, DS);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera i layers da geoserver")
	@RequestMapping(value="/layers", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData layers(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces) {
		
		logger.debug("GET layers");
		ResponseData response = null;
		try {
			
			ResLayers layers = geoserverService.layers(workspaces);
			response = new ResponseData(true, layers, layers.getLayers().getLayer().size(), 
					layers.getLayers().getLayer().size(), null);
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. Sono stati recuperati i layer del workspaces {}.", workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera il layer da geoserver")
	@RequestMapping(value="/layer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData layer(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome del layer da recuperare", required=true)
			@RequestParam(name="layer", required=true) String layer) {
		
		logger.debug("GET layer");	
		ResponseData response = null;
		try {
			
			ResLayer resLayer = geoserverService.layer(workspaces, layer);
			response = new ResponseData(true, resLayer, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. E' stato recuperato il layer {} del workspaces {}.", layer, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Modifica il layer da geoserver")
	@RequestMapping(value="/modLayer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData modLayer(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome del layer da modificare", required=true)
			@RequestParam(name="layer", required=true) String layer,
			@ApiParam(value="Nome dello style che verrà recuperato e associato al layer", required=true)
			@RequestParam(name="style", required=true) String style) {
		
		logger.debug("POST modifica layer");
		ResponseData response = null;
		try {
			
			GSLayer l = new GSLayer();
			/*BASE INFO*/
			l.setName(layer);
			/*AGGIUNGO STYLE*/
			GSDefaultStyle defStyle = new GSDefaultStyle();
			defStyle.setName(style);
			l.setDefaultStyle(defStyle);
			
			String logAddLayerToLg = geoserverService.modLayer(workspaces, l);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("POST REQUEST eseguita con successo. E' stato modificato il Layer del workspaces {}.", layer, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Elimina il layer da geoserver")
	@RequestMapping(value="/delLayer", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delLayer(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome del layer da eliminare", required=true)
			@RequestParam(name="layer", required=true) String layer) {
		
		logger.debug("POST layer");
		ResponseData response = null;
		try {
			
			String logAddLayerToLg = geoserverService.delLayer(workspaces, layer);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("DELETE REQUEST eseguita con successo. E' stato eliminato il layer {} del workspaces {}.", layer, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera gli stili da geoserver")
	@RequestMapping(value="/styles", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData styles(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces) {
		
		logger.debug("GET styles");
		ResponseData response = null;
		try {
			
			ResStyles styles = geoserverService.styles(workspaces);
			response = new ResponseData(true, styles, styles.getStyles().getStyle().size(), 
					styles.getStyles().getStyle().size(), null);
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. Sono stati recuperati gli stili del workspaces {}.", workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera lo style da geoserver")
	@RequestMapping(value="/style", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData style(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome dello style da recuperare", required=true)
			@RequestParam(name="style", required=true) String style) {
		
		logger.debug("GET style");
		ResponseData response = null;
		try {
			
			ResStyle resStyle = geoserverService.style(workspaces, style);
			response = new ResponseData(true, resStyle, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. E' stato recuperato lo style {} del workspaces {}.", style, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Elimina lo style da geoserver con eliminazione del relativo layer")
	@RequestMapping(value="/delStyle", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delStyle(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome dello style da eliminare", required=true)
			@RequestParam(name="style", required=true) String style,
			@ApiParam(value="Nome del file sld legato allo style da eliminare", required=true)
			@RequestParam(name="sldFile", required=true) String sldFile) {
		
		logger.debug("GET delStyle");
		ResponseData response = null;
		try {
			
			String logAddLayerToLg = geoserverService.delStyle(workspaces, style, sldFile);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("DELETE REQUEST eseguita con successo. E' stato eliminato lo style {} e il file {} del workspaces {}.", style, sldFile, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Modifica/Aggiunge uno style a geoserver")
	@RequestMapping(value="/modStyle", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData modStyle(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome dello style da modificare", required=true)
			@RequestParam(name="nome", required=true) String nome) {
		
		logger.debug("POST style");
		ResponseData response = null;
		try {
			
			GSStyle st = new GSStyle();
			st.setName(nome);
			
			String logPublish = geoserverService.publishStyle(workspaces, st);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("POST REQUEST eseguita con successo. E' stato modificato lo style {} del workspaces {}.", nome, workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Pubblica una tabella con Geometria, già presente nel datasource dbUrbamid come featureType e layer su geoserver")
	@RequestMapping(value="/publishFT", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData publishFT(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces,
			@ApiParam(value="Nome dello shape caricato", required=true)
			@RequestParam(name="name", required=true) String name,
			@ApiParam(value="Titolo della featureType da utilizzare come title della featureType/Layer.", required=true)
			@RequestParam(name="title", required=true) String title,
			@ApiParam(value="Lista di keywords di default (shapeDb;estrazionePart;defaultView). Alle keywords appena citate verranno "
					+ "aggiunte quelle degli attributi", required=true)
			@RequestParam(name="keywords", required=true) String keywords) {
		
		logger.debug("POST publish on geoserver");
		ResponseData response = null;
		try {
			
			GSFeatureType ft = new GSFeatureType();
			/*BASE INFO*/
			ft.setName(name.toLowerCase());
			ft.setTitle(title);
			/*KEYWORDS*/
			List<String> items = Arrays.asList(keywords.split("\\s*;\\s*"));
			List<String> strings = new ArrayList<String>();
			for (String keyword : items) {
				String[] arrKW = keyword.split("=");
				strings.add( arrKW[0]+"@"+arrKW[1] );
			}
			GSKeywords gsKeywords = new GSKeywords();
			gsKeywords.setString(strings);
			ft.setKeywords(gsKeywords);
			/*PROJECTION INFO*/
			ft.setNativeCRS("GEOGCS[\"WGS 84\", \n  DATUM[\"World Geodetic System 1984\", \n    SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], \n    AUTHORITY[\"EPSG\",\"6326\"]], \n  PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], \n  UNIT[\"degree\", 0.017453292519943295], \n  AXIS[\"Geodetic longitude\", EAST], \n  AXIS[\"Geodetic latitude\", NORTH], \n  AUTHORITY[\"EPSG\",\"4326\"]]");
			ft.setSrs("EPSG:4326");
			ft.setProjectionPolicy(GSFeatureType.PROJ_POLICY_REPROJECT_NATIVE_TO_DECLARED);
			ft.setEnabled(true);
			GSDataStoreShort store = new GSDataStoreShort();
				store.setName(workspaces + ":" + DS);
			ft.setStore(store);
			
			String logPublish = geoserverService.publishFeatureType(workspaces, ft);
			
			response = new ResponseData(true, name , 1, 1, null);
			response.setMessage(SUCCESS);
			logger.debug("POST REQUEST eseguita con successo. Tabella pubblicata come layer {} correttamente nel workspace {} e datastore {}.", name, workspaces, DS);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
		
	}

	
	
	@ApiOperation("Recupera i layers da geoserver")
	@RequestMapping(value="/normalizeKeywordsLayers", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
	public @ResponseBody ResponseData normalizeKeywordsLayers(HttpServletRequest request,
			@ApiParam(value="Nome del workspace", required=true)
			@RequestParam(name="workspaces", required=true) String workspaces) {
		
		logger.debug("GET layers");
		ResponseData response = null;
		try {
			
			ResLayers layers = geoserverService.layers(workspaces);
			response = new ResponseData(true, layers, layers.getLayers().getLayer().size(), 
					layers.getLayers().getLayer().size(), null);
			
			for (GSLayerShort layerShort :layers.getLayers().getLayer()) {
				if (!layerShort.getName().startsWith("u_geo") && !layerShort.getName().startsWith("u_cat")) {
					GSLayer l = new GSLayer();
					l.setName(layerShort.getName());
					geoserverService.modLayer(workspaces, l);
				} else if (layerShort.getName().startsWith("u_geo") || layerShort.getName().startsWith("u_cat") ) {
					GSFeatureType ft = new GSFeatureType();
					ft.setName(layerShort.getName());
					geoserverService.modKeywordsFeatureType(workspaces, ft);
				}
			}
			response.setMessage(SUCCESS);
			logger.debug("GET REQUEST eseguita con successo. Sono stati recuperati i layer del workspaces {}.", workspaces);
		} catch (Exception e) {
			
			logger.error("Error in wrapper geoserver", e);
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
}
