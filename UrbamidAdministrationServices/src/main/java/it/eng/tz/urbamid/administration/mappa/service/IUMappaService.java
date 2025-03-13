package it.eng.tz.urbamid.administration.mappa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaMappeBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;

@Service
public interface IUMappaService {
	public List<UMappa> getMappe() throws Exception;
	public String saveOrUpdate(UMappa umappa) throws Exception;
	public int[] deletes(List<Long> ids)throws Exception;
	public int delete(UMappa entity)throws Exception;
	public List<TemaBean> temaToMappa(UMappa entity)throws Exception;
	public List<TemaBean> temaToMappa(List<Long> ids)throws Exception;
	public int[] associaTemaToMappa(UMappa entity) throws Exception;
	public int[] deleteTemaToMappa(UMappa entity) throws Exception;
	
	public List<TemaMappeBean> getAllTemaMappe() throws Exception;
	public List<TemaMappeBean> getAllTemaMappeByRoles(List<Long> roles) throws Exception;
	public int duplica(UMappa uMappa)throws Exception;
	public int updateZoomAndShowCat(UMappa entity)throws Exception;
}
