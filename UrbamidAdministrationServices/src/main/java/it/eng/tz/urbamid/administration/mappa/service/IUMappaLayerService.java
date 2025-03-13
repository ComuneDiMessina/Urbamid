package it.eng.tz.urbamid.administration.mappa.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;
import it.eng.tz.urbamid.administration.web.dto.GroupMapDTO;
import it.eng.tz.urbamid.administration.web.dto.LayerDaAggiungereDTO;

@Service
public interface IUMappaLayerService {
	public List<UMappaLayer> getLayers() throws Exception;
	public String saveOrUpdate(UMappaLayer umappa) throws Exception;
	public int[] deletes(List<Integer> ids)throws Exception;
	public int delete(UMappaLayer entity)throws Exception;
	public LinkedHashMap<String,List<UMappaLayer>> getGroupLayerByMappa(UMappa entity) throws Exception;
	public int updateGruppo(UMappaLayer entity)throws Exception;
	public int[] deleteGruppo(UMappaLayer entity)throws Exception;
//	public List<MappaPermessoBean> getLayerPermessi(Integer idLayer) throws Exception;
	public void insertPermesso(List<MappaPermessoBean> permesso) throws Exception;
	public int saveGrups(LinkedHashMap<String, List<UMappaLayer>> grups) throws Exception;
	List<UMappaLayer> countPermessiLayer(String nomeLayer) throws Exception;
//	List<UMappaLayer> countPermessiLayer(String nomeLayer, Integer idMappa) throws Exception;
	List<MappaPermessoBean> getLayerPermessi(Integer idMappa, String nomeLayer) throws Exception;
	List<MappaPermessoBean> getPermessi(String nomeLayer) throws Exception;
	List<GroupMapDTO> getGroupTableMap(Integer idMappa) throws Exception;
	List<LayerDaAggiungereDTO> layerDaAggiungere(Integer idMappa, String nomeTavola) throws Exception;
}
