package it.eng.tz.urbamid.web.services;

import java.util.List;

import it.eng.tz.urbamid.web.dto.FoglioGeomDTO;
import it.eng.tz.urbamid.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.web.dto.ParticellaGeomCompleteDTO;
import it.eng.tz.urbamid.web.dto.ParticellaGeomDTO;
import it.eng.tz.urbamid.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.web.dto.UnitaImmobiliareDTO;

public interface CatastoServices {

	public List<ParticellaGeomDTO> findParticella(String foglio, String particella) throws Exception;
	public List<ParticellaGeomDTO> findParticellaByGeom(String wktGeom) throws Exception;
	public List<ParticellaGeomDTO> findParticellaByWkt(String wktGeom) throws Exception;
	public List<ParticellaGeomCompleteDTO> findParticellaCompleteByWkt(String wktGeom) throws Exception;
	public List<ParticellaGeomDTO> findParticellaByLayer(String layerName) throws Exception;
	public List<FoglioGeomDTO> findFoglio(String numFoglio) throws Exception;
	public List<FoglioGeomDTO> findFoglioByGeom(String wktGeom) throws Exception;
	
	public List<ParticelleTerreniDTO> ricercaSuParticellePT(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public List<UnitaImmobiliareDTO> ricercaSuParticelleUI(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public List<ListaIntestatariDTO> ricercaListaIntestatari(Integer idImmobile) throws Exception;
	public List<ListaIntestatariDTO> ricercaPersoneFisiche(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public List<ListaIntestatariDTO> ricercaSoggettiGiuridici(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public List<ParticellaGeomDTO> ricercaFoglioMappale(String numFoglio, String mappale) throws Exception;
	
	/**
	 * Eseue l'export in formato zip
	 * @param lsParticelle
	 * @return il file zippato come stringa in codifica base 64
	 * @throws Exception
	 */
	public String exportXls(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public String exportXlsSoggetti(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public String exportXlsTerreni(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public String exportXlsUiu(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	public String exportXlsIntestazioni(List<ParticellaCustomDTO> lsParticelle) throws Exception;
	
	public List<TipologicaDTO> categorieCatastali() throws Exception;
	public List<TipologicaDTO> codiciDiritto() throws Exception;
	public List<TipologicaDTO> codiciQualita() throws Exception;

	public String exportPdf(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione);
	public String exportShape(List<ParticellaCustomDTO> lsParticelle, String titolo);
	public String exportFabbricatiPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione);
	public String exportTerreniPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione);
	public String exportFabbricatiPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione);
	public String exportTerreniPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione);

	public List<TipologicaDTO> getComuni() throws Exception;
	public List<TipologicaDTO> getProvince() throws Exception;
	public List<TipologicaDTO> getComuniByProvincia(Long idProvincia) throws Exception;

}
