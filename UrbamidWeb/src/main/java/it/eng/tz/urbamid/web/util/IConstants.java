package it.eng.tz.urbamid.web.util;

public class IConstants {

	public static final String WS_PROFILE_MANAGER_ENDPOINT 						= "urbamid.rest.profilemanager.endpoint.url";
	public static final String WS_PROFILE_MANAGER_PROFILO_UTENTE				= "urbamid.rest.profilemanager.profiloutente.findByUsername";
	public static final String WS_PROFILE_MANAGER_UTENTI						= "urbamid.rest.profilemanager.profiloutente.utenti";
	public static final String WS_PROFILE_MANAGER_SALVA_UTENTE					= "urbamid.rest.profilemanager.profiloutente.salvaUtente";
	public static final String WS_PROFILE_MANAGER_ELIMINA_UTENTE				= "urbamid.rest.profilemanager.profiloutente.eliminaUtente";
	public static final String WS_PROFILE_MANAGER_ELIMINA_UTENTE_BY_ID			= "urbamid.rest.profilemanager.profiloutente.eliminaUtenteById";
	public static final String WS_PROFILE_MANAGER_SALVA_UTENTE_RUOLO			= "urbamid.rest.profilemanager.profiloutente.salvaUtenteRuolo";
	public static final String WS_PROFILE_MANAGER_ELIMINA_UTENTE_RUOLO			= "urbamid.rest.profilemanager.profiloutente.eliminaUtenteRuolo";
	public static final String WS_PROFILE_MANAGER_RUOLI							= "urbamid.rest.profilemanager.profiloutente.ruoli";
	public static final String WS_PROFILE_MANAGER_PERMESSI						= "urbamid.rest.profilemanager.profiloutente.permessi";
	public static final String WS_PROFILE_MANAGER_SALVA_RUOLO_PERMESSO			= "urbamid.rest.profilemanager.profiloutente.salvaRuoloPermesso";
	public static final String WS_PROFILE_MANAGER_DELETE_RUOLO_PERMESSO			= "urbamid.rest.profilemanager.profiloutente.deleteRuoloPermesso";
	public static final String WS_PROFILE_MANAGER_SALVA_RUOLO					= "urbamid.rest.profilemanager.profiloutente.salvaRuolo";
	public static final String WS_PROFILE_MANAGER_DELETE_RUOLO					= "urbamid.rest.profilemanager.profiloutente.eliminaRuolo";

	public static final String WS_ADMINISTRATION_ENDPOINT 						= "urbamid.rest.administration.endpoint.url";
	public static final String WS_ADMINISTRATION_FUNZIONALITA					= "urbamid.rest.administration.funzionalita.findByPermissions";
	public static final String WS_ADMINISTRATION_FUNZIONALITA_MENU				= "urbamid.rest.administration.funzionalita.findMenuByPermissions";
	public static final String WS_ADMINISTRATION_MAPPA_GETMAPPE					= "urbamid.rest.administration.mappa.getMappe";
	public static final String WS_ADMINISTRATION_MAPPA_GETMAPPA					= "urbamid.rest.administration.mappa.getMappa";
	public static final String WS_ADMINISTRATION_MAPPA_SALVAMAPPA				= "urbamid.rest.administration.mappa.salvaMappa";
	public static final String WS_ADMINISTRATION_MAPPA_DUPLICAMAPPA				= "urbamid.rest.administration.mappa.duplicaMappa";
	public static final String WS_ADMINISTRATION_MAPPA_TOOLS					= "urbamid.rest.administration.mappa.getMappaTools";
	public static final String WS_ADMINISTRATION_MAPPA_INSERTJOINTOOL			= "urbamid.rest.administration.mappa.insertJoinTool";
	public static final String WS_ADMINISTRATION_MAPPA_ATTTOOL					= "urbamid.rest.administration.mappa.getMappaAttTool";
	public static final String WS_ADMINISTRATION_MAPPA_ALLRICERCHE				= "urbamid.rest.administration.mappa.getMappaAllRicerche";
	public static final String WS_ADMINISTRATION_MAPPA_ATTRICERCHE				= "urbamid.rest.administration.mappa.getMappaAttRicerche";
	public static final String WS_ADMINISTRATION_MAPPA_INSERTJOINRICERCA		= "urbamid.rest.administration.mappa.insertJoinRicerca";
	
	public static final String WS_ADMINISTRATION_MAPPA_GETALLRUOLI				= "urbamid.rest.administration.mappa.getAllRuoli";
	public static final String WS_ADMINISTRATION_MAPPA_INSERTPERMESSI			= "urbamid.rest.administration.mappa.insertPermessi";
	public static final String WS_ADMINISTRATION_MAPPA_GETALLMAPPERMESSI		= "urbamid.rest.administration.mappa.getAllMapPermessi";
	public static final String WS_ADMINISTRATION_MAPPA_GETRUOLOMAPPA			= "urbamid.rest.administration.mappa.getRuoloMappa";
	
	public static final String WS_ADMINISTRATION_TEMA_GETTEMI					= "urbamid.rest.administration.tema.getTemi";
	public static final String WS_ADMINISTRATION_SALVA_TEMA						= "urbamid.rest.administration.tema.salvaTema";
	public static final String WS_ADMINISTRATION_ELIMINA_TEMA					= "urbamid.rest.administration.tema.delete";
	public static final String WS_ADMINISTRATION_ELIMINA_TEMA_ALL				= "urbamid.rest.administration.tema.deleteSel";
	public static final String WS_ADMINISTRATION_MAPPA_TO_TEMA					= "urbamid.rest.administration.tema.mappeToTema";
	public static final String WS_ADMINISTRATION_GROUP_TO_MAP					= "urbamid.rest.administration.mappa.group.to.map";
	public static final String WS_ADMINISTRATION_ADD_LAYER						= "urbamid.rest.administration.mappa.add.layer";

	////NEWMAP
	public static final String WS_ADMINISTRATION_MAPPE_GETALLTEMAMAPPE 			= "urbamid.rest.administration.mappa.getAllTemaMappe";
	public static final String WS_ADMINISTRATION_MAPPE_GETALLTEMAMAPPEROLES 	= "urbamid.rest.administration.mappa.getAllTemaMappeByRoles";
	public static final String WS_ADMINISTRATION_MAPPE_DELETE_TEMI_TO_MAP 		= "urbamid.rest.administration.mappa.deleteTemiToMap";
	public static final String WS_ADMINISTRATION_MAPPE_ASSOCIA_TEMI_TO_MAP 		= "urbamid.rest.administration.mappa.associaTemiToMap";
	public static final String WS_ADMINISTRATION_MAPPE_TEMI_TO_MAP_ALL 			= "urbamid.rest.administration.mappa.temiToMappaAll";
	public static final String WS_ADMINISTRATION_MAPPE_TEMI_TO_MAP 				= "urbamid.rest.administration.mappa.temiToMappa";
	public static final String WS_ADMINISTRATION_MAPPE_DELETE 					= "urbamid.rest.administration.mappa.delete";
	public static final String WS_ADMINISTRATION_MAPPE_DELETE_SEL 				= "urbamid.rest.administration.mappa.deleteSel";
	public static final String WS_ADMINISTRATION_MAPPE_SAVE_OR_UPDATE 			= "urbamid.rest.administration.mappa.saveOrUpdate";
	public static final String WS_ADMINISTRATION_MAPPE_ALL_MAPPA 				= "urbamid.rest.administration.mappa.allmappa";
	public static final String WS_ADMINISTRATION_MAPPE_DUPLICA 					= "urbamid.rest.administration.mappa.duplica";
	public static final String WS_ADMINISTRATION_MAPPE_UPDATE_ZOOM				= "urbamid.rest.administration.mappa.updateZoomShowCat";
	
	//LAYER
	public static final String WS_ADMINISTRATION_LAYER_SAVE_OR_UPDATE 			= "urbamid.rest.administration.layer.saveOrUpdate";
	public static final String WS_ADMINISTRATION_LAYER_DELETE_SEL 				= "urbamid.rest.administration.layer.deleteSel";
	public static final String WS_ADMINISTRATION_LAYER_DELETE 					= "urbamid.rest.administration.layer.delete";
	public static final String WS_ADMINISTRATION_LAYER_LAYER_ALL 				= "urbamid.rest.administration.layer.layerall";
	public static final String WS_ADMINISTRATION_LAYER_GROUP_LAYER_MAPPA		= "urbamid.rest.administration.layer.groupLayerMappa";
	public static final String WS_ADMINISTRATION_LAYER_UPDATE_GROUP 			= "urbamid.rest.administration.layer.updateGruppo";
	public static final String WS_ADMINISTRATION_LAYER_DELETE_GROUP 			= "urbamid.rest.administration.layer.deleteGruppo";
	public static final String WS_ADMINISTRATION_LAYER_INSERT_PERMESSI			= "urbamid.rest.administration.layer.insertPermessi";
	public static final String WS_ADMINISTRATION_LAYER_GET_ALL_PERMESSI			= "urbamid.rest.administration.layer.getAllLayerPermessi";
	public static final String WS_ADMINISTRATION_LAYER_COUNT_PERMESSI			= "urbamid.rest.administration.layer.countPermessiLayer";
	public static final String WS_ADMINISTRATION_LAYER_INSERT_OR_UPDATE			= "urbamid.rest.administration.layer.insertEditingLayer";
	public static final String WS_ADMINISTRATION_LAYER_FINDALL_LAYERS			= "urbamid.rest.administration.layer.findAllLayers";
	public static final String WS_ADMINISTRATION_LAYER_FINDALL_GEOMETRY			= "urbamid.rest.administration.layer.findAllGeometry";
	public static final String WS_ADMINISTRATION_LAYER_DELETE_LAYER				= "urbamid.rest.administration.layer.deleteLayer";
	public static final String WS_ADMINISTRATION_LAYER_DELETE_GEOMETRY			= "urbamid.rest.administration.layer.deleteGeometry";
	public static final String WS_ADMINISTRATION_LAYER_COUNT_BY_IDENTIFICATIVO	= "urbamid.rest.administration.layer.countLayerByIdentificativo";
	
	public static final String WS_WRAPPER_ENDPOINT 								= "urbamid.rest.wrappergeo.service.endpoint.url";
	public static final String WS_WRAPPER_GEOMETRY_LAYER						= "urbamid.rest.wrappergeo.funzionalita.findGeometriaLayer";
	public static final String WS_WRAPPER_GEOMETRY_LAYER_BYWKT					= "urbamid.rest.wrappergeo.funzionalita.findGeometriaLayerByWkt";
	public static final String WS_WRAPPER_ADD_LAYER								= "urbamid.rest.wrappergeo.funzionalita.addLayer";
	public static final String WS_WRAPPER_DELETE_LAYER							= "urbamid.rest.wrappergeo.funzionalita.deleteLayer";
	
	public static final String WS_CATASTO_ENDPOINT 								= "urbamid.rest.catasto.service.endpoint.url";
	public static final String WS_CATASTO_PARTICELLA							= "urbamid.rest.catasto.funzionalita.findParticella";
	public static final String WS_CATASTO_PARTICELLA_BYGEOM						= "urbamid.rest.catasto.funzionalita.findParticellaByGeom";
	public static final String WS_CATASTO_PARTICELLA_BYWKT						= "urbamid.rest.catasto.funzionalita.findParticellaByWkt";
	public static final String WS_CATASTO_PARTICELLA_COMPLETE_BYWKT				= "urbamid.rest.catasto.funzionalita.findParticellaCompleteByWkt";
	public static final String WS_CATASTO_PARTICELLA_BYLAYER					= "urbamid.rest.catasto.funzionalita.findParticellaByLayer";
	public static final String WS_CATASTO_FOGLIO								= "urbamid.rest.catasto.funzionalita.findFoglio";
	public static final String WS_CATASTO_FOGLIO_BYGEOM							= "urbamid.rest.catasto.funzionalita.findFoglioByGeom";
	public static final String WS_CATASTO_PARTICELLA_PT							= "urbamid.rest.catasto.funzionalita.ricercaSuParticellePT";
	public static final String WS_CATASTO_PARTICELLA_UI							= "urbamid.rest.catasto.funzionalita.ricercaSuParticelleUI";
	public static final String WS_CATASTO_PARTICELLA_PF							= "urbamid.rest.catasto.funzionalita.ricercaPersoneFisiche";
	public static final String WS_CATASTO_PARTICELLA_SG							= "urbamid.rest.catasto.funzionalita.ricercaSoggettiGiuridici";
	public static final String WS_CATASTO_PARTICELLA_LI							= "urbamid.rest.catasto.funzionalita.ricercaListaIntestatari";
	public static final String WS_CATASTO_FOGLIO_MAPPALE						= "urbamid.rest.catasto.funzionalita.ricercaFoglioMappale";
	public static final String WS_CATASTO_EXPORT_SHAPE							= "urbamid.rest.catasto.funzionalita.exportShape";

	//REST API PER LE TIPOLOGICHE
	public static final String WS_CATASTO_CODICI_CATEGORIE_CATASTALI			= "urbamid.rest.catasto.codici.funzionalita.categorieCatastali";
	public static final String WS_CATASTO_CODICI_CODICI_DIRITTO					= "urbamid.rest.catasto.codici.funzionalita.codiciDiritto";
	public static final String WS_CATASTO_CODICI_CODICI_QUALITA					= "urbamid.rest.catasto.codici.funzionalita.codiciQualita";
	public static final String WS_CATASTO_COMUNI								= "urbamid.rest.catasto.codici.funzionalita.comuni";
	public static final String WS_CATASTO_PROVINCE								= "urbamid.rest.catasto.codici.funzionalita.province";
	public static final String WS_CATASTO_COMUNI_BY_PROVINCIA					= "urbamid.rest.catasto.codici.funzionalita.comunibyprovincia";

	// REST API PER LO STORAGE (QUESTO VALORE VIENE CONCATENATO A
	// WS_CATASTO_BASE_ENDPOINT PER FORMARE L'ENDPOINT)
	public static final String WS_CATASTO_STORAGE_REST_API						= "urbamid.rest.catasto.funzionalita.storage";
	// REST API PER IL BATCH MANAGEMENT (QUESTO VALORE VIENE CONCATENATO A
	// WS_CATASTO_BASE_ENDPOINT PER FORMARE L'ENDPOINT)
	public static final String WS_CATASTO_BATCH_MANAGEMENT_REST_API 			= "urbamid.rest.catasto.funzionalita.batch";
	//REST API PER GLI EXPORT
	public static final String WS_CATASTO_EXPORT_XLS							= "urbamid.rest.catasto.funzionalita.exportXls";
	public static final String WS_CATASTO_EXPORT_SOGGETTI_XLS					= "urbamid.rest.catasto.funzionalita.exportXls.soggetti";
	public static final String WS_CATASTO_EXPORT_TERRENI_XLS					= "urbamid.rest.catasto.funzionalita.exportXls.terreni";
	public static final String WS_CATASTO_EXPORT_UIU_XLS						= "urbamid.rest.catasto.funzionalita.exportXls.uiu";
	public static final String WS_CATASTO_EXPORT_INTESTAZIONI_XLS				= "urbamid.rest.catasto.funzionalita.exportXls.intestazioni";
	public static final String WS_CATASTO_EXPORT_PDF							= "urbamid.rest.catasto.funzionalita.exportPdf";
	public static final String WS_CATASTO_EXPORT_PDF_FABBRICATI_NOMINATIVO		= "urbamid.rest.catasto.funzionalita.fabbricati.nominativo";
	public static final String WS_CATASTO_EXPORT_PDF_FABBRICATI_PARTICELLA		= "urbamid.rest.catasto.funzionalita.fabbricati.particella";
	public static final String WS_CATASTO_EXPORT_PDF_TERRENI_NOMINATIVO			= "urbamid.rest.catasto.funzionalita.terreni.nominativo";
	public static final String WS_CATASTO_EXPORT_PDF_TERRENI_PARTICELLA			= "urbamid.rest.catasto.funzionalita.terreni.particella";

	//REST API PER LE RICERCHE SOGGETTI
	public static final String WS_CATASTO_RICERCHE_SOGGETTI_PF					= "urbamid.rest.catasto.ricerche.soggetti.pf";
	public static final String WS_CATASTO_RICERCHE_SOGGETTI_SG					= "urbamid.rest.catasto.ricerche.soggetti.sg";
	public static final String WS_CATASTO_RICERCHE_SOGGETTI_DETTAGLIO_PT		= "urbamid.rest.catasto.ricerche.soggetti.dettaglio.pt";
	public static final String WS_CATASTO_RICERCHE_SOGGETTI_DETTAGLIO_UIU		= "urbamid.rest.catasto.ricerche.soggetti.dettaglio.uiu";
	public static final String WS_CATASTO_RICERCHE_SOGGETTI_PF_EXPORT_XLS		= "urbamid.rest.catasto.ricerche.soggetti.pf.exportXls";
	public static final String WS_CATASTO_RICERCHE_SOGGETTI_SG_EXPORT_XLS		= "urbamid.rest.catasto.ricerche.soggetti.sg.exportXls";
	public static final String WS_CATASTO_RICERCHE_LISTA_INTESTATARI_DIRITTO	= "urbamid.rest.catasto.ricerche.ricercaListaIntestatariTranneCorrenteConDiritto";
	public static final String WS_CATASTO_RICERCHE_DATA_AGGIORNAMENTO			= "urbamid.rest.catasto.ricerche.dataAggiornamento";

	//REST API PER LE RICERCHE IMMOBILI
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU					   = "urbamid.rest.catasto.ricerche.immobili.uiu";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_IDENTIFICATIVI	   = "urbamid.rest.catasto.ricerche.immobili.uiu.identificativi";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_PF				   = "urbamid.rest.catasto.ricerche.immobili.uiu.pf";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_SG				   = "urbamid.rest.catasto.ricerche.immobili.uiu.sg";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_PLANIMETRIE		   = "urbamid.rest.catasto.ricerche.immobili.uiu.planimetrie";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_IMAGE_PLANIMETRIE  = "urbamid.rest.catasto.ricerche.immobili.uiu.image.planimetria";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_EXPORT			   = "urbamid.rest.catasto.ricerche.immobili.uiu.export";
	public static final String WS_CATASTO_RICERCHE_IMMOBILI_UIU_LOCALIZZA		   = "urbamid.rest.catasto.ricerche.immobili.uiu.localizza";

	//REST API PER LE RICERCHE PARTICELLE
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT				= "urbamid.rest.catasto.ricerche.particelle.pt";
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT_IDENTIFICATIVI	= "urbamid.rest.catasto.ricerche.particelle.pt.identificativi";
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT_LOCALIZZA		= "urbamid.rest.catasto.ricerche.particelle.pt.localizza";
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT_DETTAGLIO		= "urbamid.rest.catasto.ricerche.particelle.pt.dettaglio";
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT_PF				= "urbamid.rest.catasto.ricerche.particelle.pt.pf";
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT_SG				= "urbamid.rest.catasto.ricerche.particelle.pt.sg";
	public static final String WS_CATASTO_RICERCHE_PARTICELLE_PT_EXPORT			= "urbamid.rest.catasto.ricerche.particelle.pt.export";

	//REST API PER LE RICERCHE INTESTAZIONI
	public static final String WS_CATASTO_RICERCHE_INTESTAZIONI_PF				= "urbamid.rest.catasto.ricerche.intestazioni.pf";
	public static final String WS_CATASTO_RICERCHE_INTESTAZIONI_PF_INFO_PT		= "urbamid.rest.catasto.ricerche.intestazioni.pf.info.pt";
	public static final String WS_CATASTO_RICERCHE_INTESTAZIONI_PF_INFO_UIU		= "urbamid.rest.catasto.ricerche.intestazioni.pf.info.uiu";
	public static final String WS_CATASTO_RICERCHE_INTESTAZIONI_PF_EXPORT		= "urbamid.rest.catasto.ricerche.intestazioni.pf.export";
	public static final String WS_CATASTO_RICERCHE_INTESTAZIONI_SG				= "urbamid.rest.catasto.ricerche.intestazioni.sg";
	public static final String WS_CATASTO_RICERCHE_INTESTAZIONI_SG_EXPORT		= "urbamid.rest.catasto.ricerche.intestazioni.sg.export";

	
	//REST API TOPONOMASTICA
	public static final String WS_TOPONOMASTICA_ENDPOINT						= "urbamid.rest.toponomastica.service.endpoint.url";
	public static final String WS_TOPONOMASTICA_COMUNI							= "urbamid.rest.toponomastica.service.funzionalita.comuni";
	public static final String WS_TOPONOMASTICA_PROVINCE						= "urbamid.rest.toponomastica.service.funzionalita.province";
	public static final String WS_TOPONOMASTICA_COMUNI_BY_PROVINCIA				= "urbamid.rest.toponomastica.service.funzionalita.comunibyprovince";
	public static final String WS_TOPONOMASTICA_COMUNI_BY_MESSINA				= "urbamid.rest.toponomastica.service.funzionalita.comunibymessina";
	public static final String WS_TOPONOMASTICA_DUG								= "urbamid.rest.toponomastica.service.funzionalita.getdug";
	
	public static final String WS_TOPONOMASTICA_TIPO_TOPONIMO					= "urbamid.rest.toponomastica.service.funzionalita.getTipoToponimo";
	public static final String WS_TOPONOMASTICA_TIPO_ACCESSO					= "urbamid.rest.toponomastica.service.funzionalita.getTipoAccesso";
	public static final String WS_TOPONOMASTICA_PATRIMONIALITA					= "urbamid.rest.toponomastica.service.funzionalita.getPatrimonialita";
	public static final String WS_TOPONOMASTICA_ENTE_GESTORE					= "urbamid.rest.toponomastica.service.funzionalita.getEnteGestore";
	public static final String WS_TOPONOMASTICA_CLASSIFICA_FUNZIONALE			= "urbamid.rest.toponomastica.service.funzionalita.getClassificaFunzionale";
	public static final String WS_TOPONOMASTICA_CLASSIFICA_AMMINISTRATIVA		= "urbamid.rest.toponomastica.service.funzionalita.getClassificaAmministrativa";
	public static final String WS_TOPONOMASTICA_GET_TIPO_LOCALITA				= "urbamid.rest.toponomastica.service.funzionalita.getTipoLocalita";
	public static final String WS_TOPONOMASTICA_GET_TIPO_TOPOLOGICO				= "urbamid.rest.toponomastica.service.funzionalita.getTipoTopologico";
	public static final String WS_TOPONOMASTICA_GET_TIPO_FUNZIONALE				= "urbamid.rest.toponomastica.service.funzionalita.getTipoFunzionale";
	
	public static final String WS_TOPONOMASTICA_GET_LOCALITA					= "urbamid.rest.toponomastica.service.funzionalita.getLocalita";
	public static final String WS_TOPONOMASTICA_DELETE_LOCALITA					= "urbamid.rest.toponomastica.service.funzionalita.cancellaLocalita";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_LOCALITA		= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdateLocalita";
	public static final String WS_TOPONOMASTICA_FIND_ALL_LOCALITA				= "urbamid.rest.toponomastica.service.funzionalita.findAllLocalita";
	
	public static final String WS_TOPONOMASTICA_GET_TOPONIMO_STRADALE			= "urbamid.rest.toponomastica.service.funzionalita.getToponimoStradale";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_TOPONIMO		= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdateToponimoStradale";
	public static final String WS_TOPONOMASTICA_PUBBLICA_TOPONIMO_STRADALE		= "urbamid.rest.toponomastica.service.funzionalita.pubblicaToponimoStradale";
	public static final String WS_TOPONOMASTICA_DELETE_TOPONIMO					= "urbamid.rest.toponomastica.service.funzionalita.eliminaToponimoStradale";
	public static final String WS_TOPONOMASTICA_TOPONIMO_AUTOCOMPLETE			= "urbamid.rest.toponomastica.service.funzionalita.toponimoAutocomplete";
	public static final String WS_TOPONOMASTICA_GET_TOPONIMI_STRADALI_FIGLI		= "urbamid.rest.toponomastica.service.funzionalita.getToponimoFigli";
	public static final String WS_TOPONOMASTICA_IS_FIGLI_PUBBLICATI				= "urbamid.rest.toponomastica.service.funzionalita.isFigliPubblicati";
	
	public static final String WS_TOPONOMASTICA_GET_SHAPE_FILES					= "urbamid.rest.toponomastica.service.funzionalita.getShapeFiles";
	public static final String WS_TOPONOMASTICA_EXPORT_SHAPE_FILE				= "urbamid.rest.toponomastica.service.funzionalita.exportShapeFile";
	public static final String WS_TOPONOMASTICA_IMPORT_SHAPE_FILE				= "urbamid.rest.toponomastica.service.funzionalita.importShapeFile";
	public static final String WS_TOPONOMASTICA_ELIMINA_SHAPE_FILE				= "urbamid.rest.toponomastica.service.funzionalita.eliminaShapeFiles";
	
	public static final String WS_TOPONOMASTICA_GET_ESTESA_AMMINISTRATIVA		= "urbamid.rest.toponomastica.service.funzionalita.getEstesaAmministrativa";
	public static final String WS_TOPONOMASTICA_ESTESA_AMMINISTR_AUTOCOMPLETE	= "urbamid.rest.toponomastica.service.funzionalita.estesaAmministrativaAutocomplete";
	public static final String WS_TOPONOMASTICA_SIGLA_ESTESA_AMMINISTRATIVA		= "urbamid.rest.toponomastica.service.funzionalita.findSiglaById";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_ESTESA			= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdateEstesa";
	public static final String WS_TOPONOMASTICA_DELETE_ESTESA					= "urbamid.rest.toponomastica.service.funzionalita.eliminaEstesa";
	
	public static final String WS_TOPONOMASTICA_GET_GIUNZIONI_STRADALI			= "urbamid.rest.toponomastica.service.funzionalita.findAllGiunzioni";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_GIUNZIONI		= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdateGiunzioni";
	public static final String WS_TOPONOMASTICA_DELETE_GIUNZIONI_STRADALI		= "urbamid.rest.toponomastica.service.funzionalita.cancellaGiunzioni";
	public static final String WS_TOPONOMASTICA_FIND_INTERSECTION				= "urbamid.rest.toponomastica.service.funzionalita.findIntersections";
	
	public static final String WS_TOPONOMASTICA_GET_ELEMENTI_STRADALI			= "";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_ELEMENTI_STRADALI = "";
	public static final String WS_TOPONOMASTICA_DELETE_ELEMENTI_STRADALI		= "";
	
	public static final String WS_TOPONOMASTICA_GET_CIPPO_CHILOMETRICO			= "urbamid.rest.toponomastica.service.funzionalita.getCippoChilometrico";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_CIPPO			= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdateCippo";
	public static final String WS_TOPONOMASTICA_DELETE_CIPPO					= "urbamid.rest.toponomastica.service.funzionalita.eliminaCippo";
	public static final String WS_TOPONOMASTICA_COUNT_CIPPO_BY_ESTESA			= "urbamid.rest.toponomastica.service.funzionalita.countCippiByEstesa";
	
	public static final String WS_TOPONOMASTICA_GET_PERCORSO					= "urbamid.rest.toponomastica.service.funzionalita.getPercorso";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_PERCORSO		= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdatePercorso";
	public static final String WS_TOPONOMASTICA_DELETE_PERCORSO					= "urbamid.rest.toponomastica.service.funzionalita.eliminaPercorso";
	
	public static final String WS_TOPONOMASTICA_GET_ACCESSO						= "urbamid.rest.toponomastica.service.funzionalita.getAccesso";
	public static final String WS_TOPONOMASTICA_INSERT_OR_UPDATE_ACCESSO		= "urbamid.rest.toponomastica.service.funzionalita.insertOrUpdateAccesso";
	public static final String WS_TOPONOMASTICA_DELETE_ACCESSO					= "urbamid.rest.toponomastica.service.funzionalita.cancellaAccesso";
	public static final String WS_TOPONOMASTICA_DELETE_ACCESSO_BY_TOPONIMO		= "urbamid.rest.toponomastica.service.funzionalita.cancellaAccessoByToponimo";
	public static final String WS_TOPONOMASTICA_DELETE_ACCESSO_BY_LOCALITA		= "urbamid.rest.toponomastica.service.funzionalita.cancellaAccessoByLocalita";
	public static final String WS_TOPONOMASTICA_COUNT_ACCESSO_BY_TOPONIMO		= "urbamid.rest.toponomastica.service.funzionalita.countAccessiByToponimo";
	public static final String WS_TOPONOMASTICA_COUNT_ACCESSO_BY_LOCALITA		= "urbamid.rest.toponomastica.service.funzionalita.countAccessiByLocalita";
	
	public static final String WS_TOPONOMASTICA_UPLOAD_DOCUMENTI				= "urbamid.rest.toponomastica.service.funzionalita.upload";
	public static final String WS_TOPONOMASTICA_DOWNLOAD_DOCUMENTI				= "urbamid.rest.toponomastica.service.funzionalita.download";
	public static final String WS_TOPONOMASTICA_GET_DOCUMENTI					= "urbamid.rest.toponomastica.service.funzionalita.getStorage";
	public static final String WS_TOPONOMASTICA_ELIMINA_DOCUMENTI				= "urbamid.rest.toponomastica.service.funzionalita.eliminaStorage";
	
	public static final String WS_TOPONOMASTICA_VIARIO							= "urbamid.rest.toponomastica.service.funzionalita.viario.strade";
	public static final String WS_TOPONOMASTICA_GEOCODING_TOPONIMO				= "urbamid.rest.toponomastica.service.funzionalita.viario.geocodingToponimo";
	public static final String WS_TOPONOMASTICA_REVERSEGEOCODING_TOPONIMO		= "urbamid.rest.toponomastica.service.funzionalita.viario.reverseGeocodingToponimo";
	
	//REST API PER IL PIANO REGOLATORE
	public static final String WS_PRG_ENDPOINT 									= "urbamid.rest.prg.service.endpoint.url";
	public static final String WS_PRG_RICERCA_VARIANTE							= "urbamid.rest.prg.service.ricerche.varianti";
	public static final String WS_PRG_RICERCA_VARIANTE_ORDERBYDATE				= "urbamid.rest.prg.service.ricerche.variantiOrderByDate";
	public static final String WS_PRG_SALVA_VARIANTE							= "urbamid.rest.prg.service.crud.varianti.salva";
	public static final String WS_PRG_CANCELLA_VARIANTE							= "urbamid.rest.prg.service.crud.varianti.cancella";

	public static final String WS_PRG_RICERCA_DOCUMENTI_BY_VARIANTE				= "urbamid.rest.prg.service.crud.documenti.cercabyvariante";
	public static final String WS_PRG_SALVA_DOCUMENTO							= "urbamid.rest.prg.service.crud.documenti.salva";
	public static final String WS_PRG_CANCELLA_DOCUMENTO						= "urbamid.rest.prg.service.crud.documenti.cancella";
	public static final String WS_PRG_DOWNLOAD_DOCUMENTO						= "urbamid.rest.prg.service.crud.documenti.download";

	public static final String WS_PRG_RICERCA_TIPO_DOCUMENTO					= "urbamid.rest.prg.service.ricerche.documenti.tipo";
	public static final String WS_PRG_SALVA_TIPO_DOCUMENTO						= "urbamid.rest.prg.service.crud.documenti.tipo.salva";
	public static final String WS_PRG_CANCELLA_TIPO_DOCUMENTO					= "urbamid.rest.prg.service.crud.documenti.tipo.cancella";
	public static final String WS_PRG_COUNT_TIPO_DOCUMENTO						= "urbamid.rest.prg.service.crud.documenti.tipo.count";
	public static final String WS_PRG_FIND_ALL_TIPO_DOCUMENTO					= "urbamid.rest.prg.service.crud.documenti.tipo.findAll";
	public static final String WS_PRG_FIND_TIPO_DOCUMENTO_BY_VARIANTE			= "urbamid.rest.prg.service.crud.documenti.tipo.findMancanti";
	public static final String WS_PRG_FIND_VARIANTE_BY_TIPO_DOCUMENTO			= "urbamid.rest.prg.service.crud.documenti.tipo.varianteByTipoDocumento";
	
	public static final String WS_PRG_AMBITO_VARIANTE_TRACCIATO					= "urbamid.rest.prg.service.variante.ambito.tracciato";
	public static final String WS_PRG_AMBITO_VARIANTE_SELEZIONE					= "urbamid.rest.prg.service.variante.ambito.selezione";
	public static final String WS_PRG_AMBITO_VARIANTE_SELEZIONE_LAYER			= "urbamid.rest.prg.service.variante.ambito.layer";
	public static final String WS_PRG_AMBITO_VARIANTE_RICERCA					= "urbamid.rest.prg.service.variante.ambito.ricerca";
	public static final String WS_PRG_AMBITO_VARIANTE_CERCA_DA_VARIANTE			= "urbamid.rest.prg.service.variante.ambito.cerca.variante";

	public static final String WS_PRG_RICERCA_CARTOGRAFIE_BY_VARIANTE			= "urbamid.rest.prg.service.crud.cartografie.cercabyvariante";
	public static final String WS_PRG_RICERCA_CARTOGRAFIE_SALVATAGGIO			= "urbamid.rest.prg.service.crud.cartografie.salvaCartografia";
	public static final String WS_PRG_RICERCA_CARTOGRAFIE_CANCELLA				= "urbamid.rest.prg.service.crud.cartografie.cancellaCartografia";
	
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE					= "urbamid.rest.prg.service.crud.documenti.indice.ricerca";
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE_SALVA			= "urbamid.rest.prg.service.crud.documenti.indice.salva";
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE_CANCELLA			= "urbamid.rest.prg.service.crud.documenti.indice.cancella";
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE_IMPORT			= "urbamid.rest.prg.service.crud.documenti.indice.import";
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE_EXPORT			= "urbamid.rest.prg.service.crud.documenti.indice.export";
	
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE_CODICE			= "urbamid.rest.prg.service.crud.documenti.codice.ricerca";
	public static final String WS_PRG_RICERCA_DOCUMENTI_INDICE_CODICE_SALVA		= "urbamid.rest.prg.service.crud.documenti.codice.salva";
	public static final String WS_PRG_RICERCA_CODICI_ZTO						= "urbamid.rest.prg.service.crud.documenti.codice.zto";

	//REST API PER LA VISURA CATASTALE
	public static final String WS_CATASTO_VISURA_CATASTALE_FABBRICATI_ATTUALE	= "urbamid.rest.catasto.visura.fabbricati.attuale";
	public static final String WS_CATASTO_VISURA_CATASTALE_TERRENI_ATTUALE		= "urbamid.rest.catasto.visura.terreni.attuale";
	public static final String WS_CATASTO_VISURA_CATASTALE_FABBRICATI_STORICA	= "urbamid.rest.catasto.visura.fabbricati.storica";
	public static final String WS_CATASTO_VISURA_CATASTALE_TERRENI_STORICA		= "urbamid.rest.catasto.visura.terreni.storica";

	//REST API PER GLI STRATI INFORMATIVI
	public static final String WS_PRG_SALVA_CATALOGO_GRUPPO						= "urbamid.rest.prg.service.strinf.salva.gruppo";
	public static final String WS_PRG_SALVA_CATALOGO_LAYER						= "urbamid.rest.prg.service.strinf.salva.layer";
	public static final String WS_PRG_GET_CATALOGO_VARIANTE						= "urbamid.rest.prg.service.strinf.getcatalogo";
	public static final String WS_PRG_CANCELLA_CATALOGO_LAYER					= "urbamid.rest.prg.service.strinf.cancella.layer";
	public static final String WS_PRG_CANCELLA_CATALOGO_GRUPPO					= "urbamid.rest.prg.service.strinf.cancella.gruppo";
	public static final String WS_PRG_VARIANTE_BY_NOME_LAYER					= "urbamid.rest.prg.service.strinf.varianteByNomeLayer";
	public static final String WS_PRG_GET_COLONNE_LAYER							= "urbamid.rest.prg.service.strinf.colonne.layer";

	//REST API PER INTERROGAZIONE PIANO E CDU
	public static final String WS_PRG_CDU_RICERCA								= "urbamid.rest.prg.service.cdu.ricerca";
	public static final String WS_PRG_CDU_CONTROLLO_PROTOCOLLO					= "urbamid.rest.prg.service.cdu.controllo.protocollo";
	
	public static final String WS_PRG_CDU_ANAGRAFICA_RICERCA					= "urbamid.rest.prg.service.cdu.anagrafica.ricerca";
	public static final String WS_PRG_CDU_ANAGRAFICA_DOWNLOAD					= "urbamid.rest.prg.service.cdu.anagrafica.download";
	public static final String WS_PRG_CDU_DOWNLOAD_BY_PROTOCOLLO				= "urbamid.rest.prg.service.cdu.anagrafica.download.protocollo";
	
	public static final String PRINT_EXPORT_BASE_PATH							= "print.export.base.path";
	
	public enum WSConstant{

		/* WS OK Response */
		WS_OK("OK","Operazione eseguita con successo");
		
		private String codice;
		private String descrizione;

		private WSConstant(String codice, String descrizione) {
			this.codice = codice;
			this.descrizione = descrizione;
		}

		public String getCodice() {
			return codice;
		}
		//public void setCodice(String codice) {this.codice = codice;}

		public String getDescrizione() {
			return descrizione;
	}
		// public void setDescrizione(String descrizione) {this.descrizione =
		// descrizione;}
	}

}
