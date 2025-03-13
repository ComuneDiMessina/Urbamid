package it.eng.tz.urbamid.administration.mappa.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.mappa.dao.TemaDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.Tema;
import it.eng.tz.urbamid.administration.mappa.service.converter.TemaConverter;

@Service
public class TemaService implements ITemaService{
	private static final Logger logger = LoggerFactory.getLogger(TemaService.class.getName());

	@Autowired
	TemaDao temaDao;
	
	@Autowired
	TemaConverter temaConverter;
	
	@Override
	public List<TemaBean> getTemi() throws Exception {
	 
			String idLog = "getTemi";		
			List<TemaBean> result = new ArrayList<TemaBean>();
			try{
				logger.info("START >>> " + idLog);
				List<Tema> resultModel =  temaDao.getAllTemi();
				for (Tema model : resultModel) {
					TemaBean temaBean = new TemaBean();
					temaBean = temaConverter.toDto(model);
					result.add(temaBean);
				}
				return result;
			} catch (Exception e) {
				logger.error(idLog + " >>>>>>>>>>>> " + e, e);
				throw (e);
			} finally {
				logger.info("END <<< " + idLog);
			}
 
	}

	
	@Override
	public String salvaOrUpdate(TemaBean temaBean) throws Exception {
 
		String result=temaDao.saveOrUpdateTema(temaBean);
		return result;
	}
	
	@Override
	public int[] deletes(List<Integer> ids) throws Exception {
		 
		return temaDao.deletes(ids);
	}
	
	@Override
	public int delete(Integer id) throws Exception {
		 
		return temaDao.delete(id);
	}
	
	@Override
	public List<Integer> mappeToTema(Long idTema) throws Exception {
		 
		return temaDao.getMappeToTema(idTema);
	}
	
	@Override
	public List<Integer> mappeToTema(List<Integer> ids) throws Exception {
	 
		return temaDao.getMappeToTema(ids);
	}
}
