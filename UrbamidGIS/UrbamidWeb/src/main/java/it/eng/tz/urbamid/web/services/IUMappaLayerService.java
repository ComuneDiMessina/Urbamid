package it.eng.tz.urbamid.web.services;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.security.model.PermessoMappaDto;
import it.eng.tz.urbamid.security.model.UMappa;
import it.eng.tz.urbamid.security.model.UMappaLayer;
import it.eng.tz.urbamid.web.dto.GroupMapDTO;
import it.eng.tz.urbamid.web.dto.LayerDaAggiungereDTO;

 

@Service
public interface IUMappaLayerService {
	public List<UMappaLayer> getLayers() throws Exception;
	public String saveOrUpdate(UMappaLayer umappa) throws Exception;
	public Integer[] deletes(List<Integer> ids)throws Exception;
	public int delete(UMappaLayer entity)throws Exception;
	public LinkedHashMap<String,List<UMappaLayer>> getGroupLayerByMappa(UMappa entity) throws Exception;
	public int updateGruppo(UMappaLayer entity)throws Exception;
	public Integer[] deleteGruppo(UMappaLayer entity)throws Exception;
	public int saveGrups(LinkedHashMap<String, List<UMappaLayer>> grups) throws Exception;
//	public List<PermessoMappaDto> getAllLayerPermessi(Integer idLayer) throws Exception;
	public List<PermessoMappaDto> insertPermessi(List<PermessoMappaDto> permessi) throws Exception;
	public List<UMappaLayer> countPermessiLayer(String nomeLayer) throws Exception;
	List<PermessoMappaDto> getAllLayerPermessi(Integer idMappa, String nomeLayer) throws Exception;
	List<GroupMapDTO> getGroupTableMap(Integer idMappa) throws Exception;
	List<LayerDaAggiungereDTO> layerDaAggiungere(Integer idMappa, String nomeTavola) throws Exception;
}
