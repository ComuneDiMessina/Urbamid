package it.eng.tz.urbamid.web.pageController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dto.DettaglioBatchDTO;
import it.eng.tz.urbamid.web.services.CatastoBatchManagementService;
import it.eng.tz.urbamid.web.services.CatastoStorageService;

@RestController
@RequestMapping("/catasto/batch")
public class CatastoBatchManagementCtrl {
	
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CatastoBatchManagementCtrl.class.getName());
	
	/**
	 * Service
	 */
	private final CatastoBatchManagementService service;
	
	/**
	 * Costruttore
	 * 
	 * @param service {@link CatastoStorageService}
	 */
	public CatastoBatchManagementCtrl(CatastoBatchManagementService service) {
		Assert.notNull(service, "CatastoBatchManagementService MUST not be null but don't panic!");
		this.service = service;
	}
	
	@PostMapping(value="/attualita", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DettaglioBatchDTO> avviaProcessoBatchAttualita() throws UrbamidServiceException {
		if(LOG.isDebugEnabled()) {
			LOG.debug("CATASTO BATCH MANAGEMENT --> POST REQUEST per avvio nuovo processo batch di attualita'");
		}
		DettaglioBatchDTO dettaglio = this.service.avviaProcessoBatchAttualita();
		return ResponseEntity.ok(dettaglio);
	}

	@PostMapping(value="/aggiornamento", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DettaglioBatchDTO> avviaProcessoBatchAggiornamento() throws UrbamidServiceException {
		if(LOG.isDebugEnabled()) {
			LOG.debug("CATASTO BATCH MANAGEMENT --> POST REQUEST per avvio nuovo processo batch di aggiornamento");
		}
		DettaglioBatchDTO dettaglio = this.service.avviaProcessoBatchAggiornamento();
		return ResponseEntity.ok(dettaglio);
	}

	@GetMapping(value="/{jobId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DettaglioBatchDTO> recuperaDettaglioBatchJob(
			@PathVariable(value="jobId", required=true) final Long jobId ) throws UrbamidServiceException {
		if(LOG.isDebugEnabled())
			LOG.debug("CATASTO BATCH MANAGEMENT --> GET REQUEST recupero dettaglio batch job con id {}.", jobId);
		DettaglioBatchDTO dettaglio = this.service.recuperaDettaglioBatchJob(jobId);
		return ResponseEntity.ok(dettaglio);
	}
	
}
