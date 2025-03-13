package it.eng.tz.urbamid.web.services;

import java.util.List;

import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DettaglioPtMultiploDTO;
import it.eng.tz.urbamid.web.dto.DettaglioSoggettiPTDTO;
import it.eng.tz.urbamid.web.dto.DettaglioSoggettiUIDTO;
import it.eng.tz.urbamid.web.dto.IdentificativiUiuDTO;
import it.eng.tz.urbamid.web.dto.ListaIntestatariDirittoDTO;
import it.eng.tz.urbamid.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.web.dto.PlanimetriaUiuDTO;
import it.eng.tz.urbamid.web.dto.SoggettiUiuDTO;
import it.eng.tz.urbamid.web.dto.UnitaImmobiliareDTO;

public interface RicercaSoggetti {

	DataTableDTO ricercaPersoneFisiche(String nome, String cognome, String sesso, String codiceFiscale,
			String dataNascitaDa, String dataNascitaA, String provincia, String comune, String note, Integer page,
			Integer size, Integer draw) throws Exception;

	List<DettaglioSoggettiPTDTO> dettaglioSoggettiPT(Integer idSoggetto) throws Exception;

	List<DettaglioSoggettiUIDTO> dettaglioSoggettiUIU(Integer idSoggetto) throws Exception;

	DataTableDTO ricercaSoggettiGiuridici(String denominazione, String provinciaNascita,
			String comuneNascita, String codiceFiscale, Integer pageIndex, Integer pageSize, Integer draw) throws Exception;

	String exportPersoneFisiche(String nome, String cognome, String sesso, String codiceFiscale, String dataNascitaDa,
			String dataNascitaA, String provincia, String comune, String note) throws Exception;

	String exportSoggettiGiuridici(String denominazione, String provinciaNascita, String comuneNascita,
			String codiceFiscale) throws Exception;

	DataTableDTO ricercaImmobiliUIU(String comune, String indirizzo, String zona, String sezioneUrbana,
			String consistenza, String categoria, String foglio, String superficie, String classe, String numero,
			String renditaLire, String partita, String subalterno, String renditaEuro, boolean soppresso, Integer page,
			Integer size, Integer draw) throws Exception;

	List<IdentificativiUiuDTO> dettaglioImmobiliUIUIdentificativi(Integer idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioImmobiliUIUPersoneFisiche(Integer idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioImmobiliUIUSoggettiGiuridici(Integer idImmobile) throws Exception;
	
	List<PlanimetriaUiuDTO> dettaglioImmobiliUIUPlanimetrie(Long foglio, Long numero, Long subalterno) throws Exception;

	String getUIUPlanimetrie(String nomeImage) throws Exception;
	
	String exportImmobiliUIU(String comune, String indirizzo, String zona, String sezioneUrbana,
			String consistenza, String categoria, String foglio, String superficie, String classe, String numero,
			String renditaLire, String partita, String subalterno, String renditaEuro, boolean soppresso) throws Exception;

	String localizzaUIU(Long foglio, Long numero) throws Exception;

	DataTableDTO ricercaParticellePT(String comune, String sezione, String foglio, String numero, String subalterno,
			String partita, String redditoDominicaleEuro, String redditoDominicaleLire, String redditoAgrarioEuro,
			String redditoAgrarioLire, String superficie, boolean soppresso, Integer page, Integer size, Integer draw) throws Exception;

	List<IdentificativiUiuDTO> dettagliParticelleUIUIdentificativi(Integer idImmobile) throws Exception;

	String localizzaPT(Long foglio, Long numero) throws Exception;

	DettaglioPtMultiploDTO dettagliParticellePTMultiplo(Integer idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioParticellePTPersoneFisiche(Integer idImmobile) throws Exception;

	List<SoggettiUiuDTO> dettaglioParticellePTSoggettiGiuridici(Integer idImmobile) throws Exception;

	String exportParticellePT(String comune, String sezione, String foglio, String numero, String subalterno,
			String partita, String redditoDominicaleEuro, String redditoDominicaleLire, String redditoAgrarioEuro,
			String redditoAgrarioLire, String superficie, boolean soppresso) throws Exception;

	DataTableDTO ricercaIntestazioniPersoneFisiche(String nome, String cognome, String codiceFiscale,
			boolean checkboxUiuPf, boolean checkboxPtPf, Integer page, Integer size, Integer draw) throws Exception;

	ParticelleTerreniDTO informazioniParticellaByIdImmobile(Integer idImmobile) throws Exception;

	UnitaImmobiliareDTO informazioniUnitaImmobiliareByIdImmobile(Integer idImmobile) throws Exception;

	String exportIntestazioniPF(String nome, String cognome, String codiceFiscale, boolean checkboxUiuPf,
			boolean checkboxPtPf) throws Exception;

	DataTableDTO ricercaIntestazioniSoggettiGiuridici(String denominazione, String provincia, String comune,
			String codiceFiscale, boolean checkboxUiuSg, boolean checkboxPtSg, Integer page, Integer size, Integer draw) throws Exception;

	String exportIntestazioniSG(String denominazione, String provincia, String comune, String codiceFiscale,
			boolean checkboxUiuSg, boolean checkboxPtSg) throws Exception;

	List<ListaIntestatariDirittoDTO> ricercaListaIntestatariTranneCorrenteConDiritto(Long idImmobile, Long idSoggetto) throws Exception;

	String getDataUltimoAggiornamento() throws Exception;

	String exportVisuraCatastaleFabbricati(Long idImmobile) throws Exception;

	String exportVisuraCatastaleTerreni(Long idImmobile) throws Exception;

	String exportVisuraCatastaleStoricaTerreni(Long idImmobile) throws Exception;

	String exportVisuraCatastaleStoricaFabbricati(Long idImmobile) throws Exception;

}
