package it.eng.tz.urbamid.administration.mappa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;

@Service
public interface ITemaService {

	public List<TemaBean> getTemi() throws Exception;
	public String salvaOrUpdate(TemaBean temaBean) throws Exception;
	public int[] deletes(List<Integer> ids)throws Exception;
	public int delete(Integer id)throws Exception;
	public List<Integer> mappeToTema(Long idTema)throws Exception;
	public List<Integer> mappeToTema(List<Integer> ids)throws Exception;
}
