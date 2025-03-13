package it.eng.tz.urbamid.catasto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.catasto.web.dto.TipologicaDTO;

@Service
public interface CodiciService {

	List<TipologicaDTO> categorieCatastali() throws Exception;

	List<TipologicaDTO> codiciDiritto() throws Exception;

	List<TipologicaDTO> codiciQualita() throws Exception;

	List<TipologicaDTO> getComuni() throws Exception;

	List<TipologicaDTO> getProvince() throws Exception;

	List<TipologicaDTO> getComuniByProvincia(Long idProvincia) throws Exception;

}
