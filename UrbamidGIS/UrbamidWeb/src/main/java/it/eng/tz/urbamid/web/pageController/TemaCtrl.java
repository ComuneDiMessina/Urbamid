package it.eng.tz.urbamid.web.pageController;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.security.model.TemaBean;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.services.ITemaService;


@Controller(value = "Tema Controller")
@RequestMapping(value = "/temaController", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TemaCtrl extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(TemaCtrl.class.getName());
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	ITemaService temaService;
	
	@RequestMapping(value = "/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@RequestMapping(value="/getTemi", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getTemi(HttpServletRequest request) {
		logger.debug("POST getTemi");
		ResponseData response = null;
		 
		try {
		
			
		List<TemaBean> result=	 temaService.getTemi();
			
		if (result!=null && result.size()>0) {
			response = new ResponseData(true, result, result.size(), result.size(), null);
		} else
			response = new ResponseData(true, null, 0, 0, null);
		response.setMessage(SUCCESS);

		} catch (Exception e) {
			logger.error("Error in getTemi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	
	@RequestMapping(value="/saveOrUpdate", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData saveOrUpdate(@RequestBody TemaBean temaBean,HttpServletRequest request) {
		logger.debug("POST saveOrUpdate");
		ResponseData response = null;
		 
		try {
 
			if(temaBean!=null){
 
			String result=temaService.saveOrUpdate(temaBean);
			response = new ResponseData(true, result, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("temaBean:::null");
			}
		 
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@RequestMapping(value="/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestBody List<Integer> ids,HttpServletRequest request) {
		logger.debug("POST delete");
		ResponseData response = null;
		 
		try {
		 
			Integer[] res=temaService.deletes(ids);
			if(res.length>0) {
			response = new ResponseData(true, res, 0, 0, null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("temi eliminati:: 0");	
				
			}
		} catch (Exception e) {
			logger.error("Error in delete", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	
	@RequestMapping(value="/mappeToTema", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData mappeToTema(@RequestBody List<Integer> ids,HttpServletRequest request) {
		logger.debug("POST mappeToTema");
		ResponseData response = null;
		 
		try {
		 
			List<Integer> res=temaService.getMappeToTema(ids);
			if(res!=null && res.size()>0) {
			response = new ResponseData(true, res,res.size(), res.size(), null);
			response.setMessage(SUCCESS);
			}else {
				response = new ResponseData(true, res,0,0, null);
				response.setMessage("Nessun Mappa Associata a Tema");	
				
			}
		} catch (Exception e) {
			logger.error("Error in recupero mappe associate a tema", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	
}
