package it.eng.tz.urbamid.administration.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.administration.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.administration.web.dto.MenuFunzionalitaDto;
import it.eng.tz.urbamid.administration.web.response.BaseResponse;
import it.eng.tz.urbamid.administration.web.response.ResponseData;

@RestController
@RequestMapping("/administration")
public class AdministrationController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class.getName());

	
	@ApiOperation("Recupera le info di un utente dato l'username")
	@PostMapping(path = "/funzionalita/findByPermissions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<FunzionalitaDto>>> getFunzionalita(@RequestBody List<String> authorities 
																		   ){
		logger.info("Start  " + getClass().getName() + " getFunzionalita");
		try {
			if (!authorities.isEmpty()) {
				for(String authority : authorities) {logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>authority:"+authority);}
			}else logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>authorities is empty");
			List<FunzionalitaDto> response = baseService.getFunzionalita(authorities);
			return okResponse(response);
		} catch (Exception e) {
			logger.error("AdministrationController :: getFunzionalita :: ERROR ::  " + e.getMessage(),e);
			return koResponseError(e);
		}
	}
	
	@ApiOperation("Recupera le voci di menu per permessi.")
	@RequestMapping(value="/funzionalita/findMenuByPermissions", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getMenuFunzionalita(HttpServletRequest request,
			@ApiParam(value="Valore del Permesso per il recupero del menu", required=true)
			@RequestParam(name="authority", required=true) String authority){
		logger.info("Start  " + getClass().getName() + " getMenuFunzionalita");
		ResponseData response = null;
		try {
			MenuFunzionalitaDto res = baseService.getMenuFunzionalita(authority);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getMappa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
}
