package it.eng.tz.urbamid.prg.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.prg.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.prg.web.response.BaseResponse;

@RestController
@RequestMapping("/prg")
public class PRGController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(PRGController.class.getName());

	
	@ApiOperation("Recupera le info di un utente dato l'username")
	@PostMapping(path = "/funzionalita/findByPermissions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<FunzionalitaDto>>> getFunzionalita(@RequestBody List<String> authorities 
																		   ){
		logger.info("Start  " + getClass().getName() + " getFunzionalita");
		try {
			List<FunzionalitaDto> response = baseService.getFunzionalita(authorities);
			return okResponse(response);
		} catch (Exception e) {
			logger.error("UserProfileController :: getFunzionalita :: ERROR ::  " + e.getMessage(),e);
			return koResponseError(e);
		}
	}
	
}
