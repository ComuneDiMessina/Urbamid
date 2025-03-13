package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.security.model.TemaBean;
 

@Service
public interface ITemaService {

	public List<TemaBean> getTemi()throws Exception;
	public String saveOrUpdate(TemaBean temaBean) throws Exception;
	public Integer[] deletes(List<Integer> ids)throws Exception;
	public int delete(Integer id)throws Exception;
	public List<Integer> getMappeToTema(Long idTema)throws Exception;
	public List<Integer> getMappeToTema(List<Integer> ids)throws Exception;
}
