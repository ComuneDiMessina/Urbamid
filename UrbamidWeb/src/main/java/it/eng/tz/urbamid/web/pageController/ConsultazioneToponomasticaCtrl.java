package it.eng.tz.urbamid.web.pageController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DugDTO;
import it.eng.tz.urbamid.web.dto.EnteGestoreDTO;
import it.eng.tz.urbamid.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.web.dto.PatrimonialitaDTO;
import it.eng.tz.urbamid.web.dto.TipoAccessoDTO;
import it.eng.tz.urbamid.web.dto.TipoLocalitaDTO;
import it.eng.tz.urbamid.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDugDTO;
import it.eng.tz.urbamid.web.filter.AccessoFilter;
import it.eng.tz.urbamid.web.filter.CippoChilometricoFilter;
import it.eng.tz.urbamid.web.filter.EstesaAmministrativaFilter;
import it.eng.tz.urbamid.web.filter.LocalitaFilter;
import it.eng.tz.urbamid.web.filter.ToponimoFilter;
import it.eng.tz.urbamid.web.services.IToponomasticaService;

@Controller(value = "ConsultazioneToponomastica Controller")
@RequestMapping(value = "/toponomasticaConsultazioneCtrl")
public class ConsultazioneToponomasticaCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ConsultazioneToponomasticaCtrl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String SUCCESS = "SUCCESS";
	private static final String ERROR = "ERROR";
	
	@Autowired
	private IToponomasticaService toponomasticaService;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getTipoToponimo")
	public @ResponseBody ResponseData getTipoToponimo(HttpServletRequest request) {
		String idLog = "getTipoToponimo";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<TipoToponimoDTO> dto = toponomasticaService.getTipoToponimo();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getTipoToponimo", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/findAllLocalita")
	public @ResponseBody ResponseData findAllLocalita(HttpServletRequest request) {
		String idLog = "findAllLocalita";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<LocalitaDTO> dto = toponomasticaService.findAllLocalita();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in findAllLocalita", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getTipoAccesso")
	public @ResponseBody ResponseData getTipoAccesso(HttpServletRequest request) {
		String idLog = "getTipoAccesso";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<TipoAccessoDTO> dto = toponomasticaService.getTipoAccesso();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getTipoAccesso", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getTipoLocalita")
	public @ResponseBody ResponseData getTipoLocalita(HttpServletRequest request) {
		String idLog = "getTipoLocalita";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<TipoLocalitaDTO> dto = toponomasticaService.getTipoLocalita();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getTipoLocalita", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getEnteGestore")
	public @ResponseBody ResponseData getEnteGestore(HttpServletRequest request) {
		String idLog = "getEnteGestore";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<EnteGestoreDTO> dto = toponomasticaService.findAllEnteGestore();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getEnteGestore", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getClassificaAmministrativa")
	public @ResponseBody ResponseData getClassificaAmministrativa(HttpServletRequest request) {
		String idLog = "getClassificaAmministrativa";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<ClassificaAmministrativaDTO> dto = toponomasticaService.findAllClassificaAmministrativa();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getClassificaAmministrativa", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getClassificaFunzionale")
	public @ResponseBody ResponseData getClassificaFunzionale(HttpServletRequest request) {
		String idLog = "getClassificaFunzionale";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<ClassificaFunzionaleDTO> dto = toponomasticaService.findAllClassificaFunzionale();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getClassificaFunzionale", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getPatrimonialita")
	public @ResponseBody ResponseData getPatrimonialita(HttpServletRequest request) {
		String idLog = "getPatrimonialita";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<PatrimonialitaDTO> dto = toponomasticaService.findAllPatrimonialita();
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getPatrimonialita", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaToponimo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO getToponimo(HttpServletRequest request, @RequestBody ToponimoFilter filter) {
		logger.debug("GET ricercaToponimo");
		DataTableDTO response = null;
		try {
						
			response = toponomasticaService.ricercaToponimo(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaToponimo: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaAccesso", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO getAcceso(HttpServletRequest request, @RequestBody AccessoFilter filter) {
		logger.debug("GET ricercaAccesso");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.ricercaAccesso(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaAccesso: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaEstesaAmministrativa", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaEstesaAmministrativa(HttpServletRequest request, @RequestBody EstesaAmministrativaFilter filter) {
		logger.debug("GET ricercaEstesaAmministrativa");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.ricercaEstesaAmministrativa(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaEstesaAmministrativa: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaCippo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO ricercaCippo(HttpServletRequest request, @RequestBody CippoChilometricoFilter filter) {
		logger.debug("GET ricercaCippo");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.ricercaCippoChilometrico(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in ricercaCippo: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @return
	 */
	@PostMapping(value="/ricercaLocalita", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataTableDTO getLocalita(HttpServletRequest request, @RequestBody LocalitaFilter filter) {
		logger.debug("GET getLocalita");
		DataTableDTO response = null;
		try {
			
			response = toponomasticaService.ricercaLocalita(filter);
		} catch (Exception e) {
			
			logger.debug("Errore in getLocalita: {}", e, e.getMessage());
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param term
	 * @return
	 */
	@PostMapping(value = "/getDug")
	public @ResponseBody ResponseData getDug(HttpServletRequest request, @RequestBody String term) {
		String idLog = "getDug";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<DugDTO> dto = toponomasticaService.getDug(term.toUpperCase());
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getDug", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param term
	 * @return
	 */
	@PostMapping(value = "/getToponimo")
	public @ResponseBody ResponseData getToponimo(HttpServletRequest request, @RequestBody String term) {
		String idLog = "getToponimo";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<ToponimoDugDTO> dto = toponomasticaService.getToponimo(term);
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getToponimo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * 
	 * @param request
	 * @param term
	 * @return
	 */
	@PostMapping(value = "/getEstesaAmministrativa")
	public @ResponseBody ResponseData getEstesaAmministrativa(HttpServletRequest request, @RequestBody String term) {
		String idLog = "getEstesaAmministrativa";
		logger.info(START, idLog);
		ResponseData response = null;
		try {
			
			List<EstesaAmministrativaDTO> dto = toponomasticaService.getEstesaAmministrativa(term);
			response = new ResponseData(true, dto, dto.size(), dto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {

			logger.error("Error in getEstesaAmministrativa", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
}
