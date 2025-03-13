package it.eng.tz.urbamid.catasto.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.catasto.filter.ImmobileFilter;
import it.eng.tz.urbamid.catasto.filter.IntestatariFilter;
import it.eng.tz.urbamid.catasto.filter.ParticellaFilter;
import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioPtMultiploDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioSoggettiPTDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioSoggettiUIDTO;
import it.eng.tz.urbamid.catasto.web.dto.IdentificativiUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDirittoDTO;
import it.eng.tz.urbamid.catasto.web.dto.PagedResult;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.catasto.web.dto.PlanimetriaUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaIntestazioniDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaSoggettiDTO;
import it.eng.tz.urbamid.catasto.web.dto.SoggettiUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;

@Service
public interface RicercaSoggettiService {

	PagedResult<RicercaSoggettiDTO> ricercaPersoneFisiche(SoggettoFilter filter) throws Exception;

	List<DettaglioSoggettiPTDTO> dettaglioSoggettiPT(Integer idSoggetto) throws Exception;

	List<DettaglioSoggettiUIDTO> dettaglioSoggettiUIU(Integer idSoggetto) throws Exception;

	PagedResult<UnitaImmobiliareDTO> ricercaImmobiliUIU(ImmobileFilter filter) throws Exception;

	List<IdentificativiUiuDTO> dettaglioImmobiliUIUIdentificativi(Long idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioImmobiliUIUPersoneFisiche(Long idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioImmobiliUIUSoggettiGiuridici(Long idImmobile) throws Exception;
	
	List<PlanimetriaUiuDTO> dettaglioImmobiliUIUPlanimetrie(Long foglio, Long numero, Long subalterno) throws Exception;

	String localizzaUIU(Long foglio, Long numero) throws Exception;

	PagedResult<ParticelleTerreniDTO> ricercaParticellePT(ParticellaFilter filter) throws Exception;

	List<IdentificativiUiuDTO> dettagliParticelleUIUIdentificativi(Long idImmobile) throws Exception;

	String localizzaPT(Long foglio, Long numero) throws Exception;

	DettaglioPtMultiploDTO dettagliParticellePTMultiplo(Long idImmobile) throws Exception;

	File getUIUPlanimetria(String nomeImage) throws Exception;
	
	List<SoggettiUiuDTO> dettaglioParticellePTPersoneFisiche(Long idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioParticellePTSoggettiGiuridici(Long idImmobile) throws Exception;

	PagedResult<RicercaIntestazioniDTO> ricercaIntestazioniPersoneFisiche(IntestatariFilter filter) throws Exception;

	ParticelleTerreniDTO informazioniParticellaByIdImmobile(Long idImmobile) throws Exception;

	UnitaImmobiliareDTO informazioniUnitaImmobiliareByIdImmobile(Long idImmobile) throws Exception;

	PagedResult<RicercaIntestazioniDTO> ricercaIntestazioniSoggettiGiuridici(IntestatariFilter filter) throws Exception;

	List<ListaIntestatariDirittoDTO> ricercaListaIntestatariTranneCorrenteConDiritto(Long idImmobile, Long idSoggetto) throws Exception;

	String getDataUltimoAggiornamento() throws Exception;

	List<RicercaIntestazioniDTO> soggettiFisiciQueryCount(IntestatariFilter filter) throws Exception;

	List<RicercaIntestazioniDTO> soggettiGiuridiciQueryCount(IntestatariFilter filter) throws Exception;

}
