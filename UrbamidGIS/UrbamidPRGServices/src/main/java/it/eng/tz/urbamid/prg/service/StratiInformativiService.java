package it.eng.tz.urbamid.prg.service;

import java.util.List;

import it.eng.tz.urbamid.prg.web.dto.AggiuntaLayerDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteLayerDTO;
import it.eng.tz.urbamid.prg.web.dto.CatalogoGruppoDTO;

public interface StratiInformativiService {

	String salvaNuovoGruppo(String nomeGruppo) throws Exception;

	List<CatalogoGruppoDTO> reperimentoCatalogoVariante() throws Exception;

	AggiuntaLayerDTO salvaLayer(AggiuntaLayerDTO layer) throws Exception;

	String cancellaLayer(Long idLayer) throws Exception;

	String cancellaGruppo(Long idGruppo) throws Exception;

	List<CartografiaVarianteLayerDTO> varianteByNomeLayer(String nomeLayer) throws Exception;

	List<String> reperimentoColonneLayer(String nomeLayer) throws Exception;

}
