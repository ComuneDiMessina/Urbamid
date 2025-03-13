package it.eng.tz.urbamid.catasto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.catasto.web.dto.FoglioGeomDTO;
import it.eng.tz.urbamid.catasto.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomCompleteDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;


@Service
public interface CatastoRicercheService {
	
	List<ParticellaGeomDTO> findParticellaWithFoglioAndMappale(String numFoglio, String mappale, String campoOrd,
			String tipoOrd, Integer page, Integer size) throws Exception;
	public List<ParticellaGeomDTO> findParticellaWithGeom(String wktGeom, String campoOrd, String tipoOrd) throws Exception;
	public List<ParticellaGeomDTO> findParticellaByWkt(String wktGeom) throws Exception;
	public List<ParticellaGeomCompleteDTO> findParticellaCompleteByWkt(String wktGeom) throws Exception;
	public List<GeometriaLayerDTO> findParticellaWithLayer(String layerName) throws Exception;
	List<FoglioGeomDTO> findFoglio(String numFoglio, String campoOrd, String tipoOrd, Integer page, Integer size)
			throws Exception;
	public List<FoglioGeomDTO> findFoglioWithGeom(String wktGeom, String campoOrd, String tipoOrd) throws Exception;
	
	List<ParticelleTerreniDTO> ricercaSuParticellePT(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception;
	List<UnitaImmobiliareDTO> ricercaSuParticelleUI(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception;
	List<ListaIntestatariDTO> ricercaListaIntestatari(Integer idImmobile, String campoOrd, String tipoOrd) throws Exception;
	List<ListaIntestatariDTO> ricercaPersoneFisiche(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception;
	List<ListaIntestatariDTO> ricercaSoggettiGiuridici(List<ParticellaCustomDTO> lsParticelle, String campoOrd, String tipoOrd) throws Exception;
	List<ParticellaGeomDTO> ricercaFoglioMappale(String numFoglio, String mappale, String campoOrd, String tipoOrd) throws Exception;
	
	
}