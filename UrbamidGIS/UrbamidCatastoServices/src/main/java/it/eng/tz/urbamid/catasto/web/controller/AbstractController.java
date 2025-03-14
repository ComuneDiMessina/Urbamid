package it.eng.tz.urbamid.catasto.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.eng.tz.urbamid.catasto.exception.CatastoDbException;
import it.eng.tz.urbamid.catasto.service.CatastoRicercheService;
import it.eng.tz.urbamid.catasto.util.IConstants;
import it.eng.tz.urbamid.catasto.web.response.BaseResponse;

public abstract class AbstractController {

	@Autowired
	CatastoRicercheService baseService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractController.class.getName());
	
	
	
	protected <T> ResponseEntity<BaseResponse<T>> okResponse(T payload) {
		BaseResponse<T> body = new BaseResponse<>();
		if (payload != null) {
			body.setPayload(payload);
			body.setCodiceEsito(IConstants.BASE_RESPONSE_OK);
			body.setDescrizioneEsito("success");
			body.setNumeroOggettiRestituiti(body.getPayloadSize(payload));
		} else
			koResponseError(new CatastoDbException());

		return new ResponseEntity<BaseResponse<T>>(body, HttpStatus.OK);
	}
	

	protected <T> ResponseEntity<T> koResponseError(Exception e) {
		return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
