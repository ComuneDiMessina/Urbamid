package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.security.model.TemaBean;
import it.eng.tz.urbamid.security.model.TemaMappeBean;
import it.eng.tz.urbamid.security.model.UMappa;

 

@Service
public interface IUMappaService {
	public List<UMappa> getMappe() throws Exception;
	public String saveOrUpdate(UMappa umappa) throws Exception;
	public Integer[] deletes(List<Long> ids)throws Exception;
	public int delete(UMappa entity)throws Exception;
	public List<TemaBean> temaToMappa(UMappa entity)throws Exception;
	public List<TemaBean> temaToMappa(List<Long> ids)throws Exception;
	public Integer[] associaTemaToMappa(UMappa entity) throws Exception;
	public Integer[] deleteTemaToMappa(UMappa entity) throws Exception;
	
	public List<TemaMappeBean> getAllTemaMappe()throws Exception;
	public List<TemaMappeBean> getAllTemaMappebyRoles(List<Long> roles)throws Exception;
	public int duplica(UMappa uMappa)throws Exception;
	public int updateZoomAndShowCat(UMappa entity)throws Exception;
}
