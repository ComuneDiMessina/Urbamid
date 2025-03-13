//package it.eng.tz.urbamid.catasto.web.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.swagger.annotations.Api;
//import it.eng.tz.urbamid.catasto.geokettle.service.GeoKettleService;
//
//@RestController
//@RequestMapping("/geokettle")
//@Api(value = "vivipass-core", description = "Servizio di test...")
//
//
//@RestController
//@RequestMapping("rest/api/batch")
//@Api(value = "urbamid geokettle", tags= {"BatchManagement"})
//public class GeoKettleRESTController {
//	
//	private static final Logger LOG = LoggerFactory.getLogger(GeoKettleRESTController.class);
//	
//	@Autowired
//	private GeoKettleService service;
//	
//	public GeoKettleRESTController( GeoKettleService service ) {
//		Assert.notNull(service, "GeoKettleService must not be null but don't panic!");
//		this.service = service;
//	}
//	
//	
//	
//	@GetMapping("/versione")
//	public ResponseEntity<String> provaAPIGeokettle() {
//		LOG.debug("Inizio il JOB GeoKettle.");
//		this.serviceGeoKettle.eseguiGeoKettleJob();
//        return ResponseEntity.ok().body("OK!");
//    }
//	
//	@GetMapping("/jobImportTerreni")
//	public ResponseEntity<String> jobImportTerreni() {
//		LOG.debug("Inizio il JOB GeoKettle.");
//		this.serviceShellExecution.eseguiScriptKettleJobImportTerreni();
//        return ResponseEntity.ok().body("OK!");
//    }
//	
//	@GetMapping("/jobImportFabbricati")
//	public ResponseEntity<String> jobImportFabbricati() {
//		LOG.debug("Inizio il JOB GeoKettle.");
//		this.serviceShellExecution.eseguiScriptKettleJobImportFabbricati();
//        return ResponseEntity.ok().body("OK!");
//    }
//	
//}