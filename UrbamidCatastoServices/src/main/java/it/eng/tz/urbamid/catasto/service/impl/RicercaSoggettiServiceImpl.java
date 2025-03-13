package it.eng.tz.urbamid.catasto.service.impl;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.catasto.filter.ImmobileFilter;
import it.eng.tz.urbamid.catasto.filter.IntestatariFilter;
import it.eng.tz.urbamid.catasto.filter.ParticellaFilter;
import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.persistence.model.AggiornamentoCatasto;
import it.eng.tz.urbamid.catasto.persistence.model.DeduzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.model.FabbricatiGeom;
import it.eng.tz.urbamid.catasto.persistence.model.IdentificativoUnitaImmobiliari;
import it.eng.tz.urbamid.catasto.persistence.model.Particella;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCatastaleJoin;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaGeom;
import it.eng.tz.urbamid.catasto.persistence.model.PorzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.model.RicercaIntestazioni;
import it.eng.tz.urbamid.catasto.persistence.model.RiservaParticella;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoIntestatario;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoModel;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobileJoinDettaglio;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoin;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoinRicerca;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaPlanimetrieJoinDettaglio;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryAggiornamentoCatasto;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryDeduzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryFabbricatiGeom;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryIdentificativoUnitaImmobiliari;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryParticellaGeom;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryPlanimetrie;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryPorzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryRiservaParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositorySoggetto;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryTitolarita;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryUnitaImmobiliare;
import it.eng.tz.urbamid.catasto.persistence.repositories.TestRepository;
import it.eng.tz.urbamid.catasto.service.AbstractCatastoService;
import it.eng.tz.urbamid.catasto.service.RicercaSoggettiService;
import it.eng.tz.urbamid.catasto.util.FieldFilter;
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
import it.eng.tz.urbamid.catasto.web.dto.converter.DeduzioneParticellaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.IdentificativiUnitaImmobiliareConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.IntestatariConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.IntestatariRicercaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ParticellaRicercaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.PlanimetriaUIConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.PorzioneParticellaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.RicerchePTConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.RicercheUIConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.RiservaParticellaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.SoggettoConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.UnitaImmobiliareDettaglioConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.UnitaImmobiliareRicercaConverter;

@Service
public class RicercaSoggettiServiceImpl extends AbstractCatastoService implements RicercaSoggettiService {

	private static final Logger logger = LoggerFactory.getLogger(RicercaSoggettiServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JpaRepositorySoggetto soggettoDao;

	@Autowired
	private TestRepository testDao;

	@Autowired
	private JpaRepositoryParticella particellaDao;

	@Autowired
	private JpaRepositoryParticellaGeom particellaGeomDao;

	@Autowired
	private JpaRepositoryUnitaImmobiliare unImmDao;
	
	@Autowired
	private JpaRepositoryPlanimetrie unImmPlanimetriaDao;

	@Autowired
	private JpaRepositoryFabbricatiGeom unImmGeomDao;

	@Autowired
	private JpaRepositoryIdentificativoUnitaImmobiliari identificativiUiuDao;

	@Autowired
	private JpaRepositoryDeduzioneParticella deduzioneDao;

	@Autowired
	private JpaRepositoryPorzioneParticella porzioneDao;

	@Autowired
	private JpaRepositoryRiservaParticella riservaDao;

	@Autowired
	private JpaRepositoryTitolarita titolaritaDao;

	@Autowired
	private JpaRepositoryAggiornamentoCatasto aggiornamentoCatastoDao;

	@Autowired
	private SoggettoConverter converter;

	@Autowired
	private RicerchePTConverter ptConverter;

	@Autowired
	private RicercheUIConverter uiConverter;

	@Autowired
	private UnitaImmobiliareRicercaConverter uiuConverter;

	@Autowired
	private IdentificativiUnitaImmobiliareConverter identificativiUiuConverter;

	@Autowired
	private UnitaImmobiliareDettaglioConverter dettaglioUiuConverter;
	
	@Autowired
	private PlanimetriaUIConverter uiPlanimetrieConverter;

	@Autowired
	private ParticellaRicercaConverter ptRicercaConverter;

	@Autowired
	private DeduzioneParticellaConverter deduzioneParticellaConverter;

	@Autowired
	private PorzioneParticellaConverter porzioneParticellaConverter;

	@Autowired
	private RiservaParticellaConverter riservaParticellaConverter;

	@Autowired
	private IntestatariRicercaConverter intestatariRicercaConverter;

	@Autowired
	private IntestatariConverter intestatariConverter;

	@Override
	@SuppressWarnings("unchecked")
	public PagedResult<RicercaSoggettiDTO> ricercaPersoneFisiche(SoggettoFilter filter) throws Exception {

		final FieldFilter<String> fieldNome = getFilteredFieldEqual(filter.getNome());
		final FieldFilter<String> fieldCognome = getFilteredFieldEqual(filter.getCognome());
		final FieldFilter<String> fieldSesso = getFilteredFieldEqual(filter.getSesso());
		final FieldFilter<String> fieldCodFis = getFilteredFieldEqual(filter.getCodiceFiscale());
		final FieldFilter<String> fieldNote = getFilteredFieldEqual(filter.getNote());
		final FieldFilter<String> fieldDenominazione = getFilteredFieldLike(filter.getDenominazione());
		final FieldFilter<Date> fieldNascitaDa = getFilteredFieldDate(filter.getDataNascitaDa());
		final FieldFilter<Date> fieldNascitaA = getFilteredFieldDate(filter.getDataNascitaA());
		
		Page<SoggettoModel> pagedResult = null;

		if (filter.getTipo().equals("P")) {
			final FieldFilter<String> fieldLuogoNascita = getFilteredFieldEqual(filter.getComune());
			pagedResult = testDao.findSoggetti(
					filter.getTipo(),
					fieldNome.getFlag(),
					fieldNome.getValue(),
					fieldCognome.getFlag(),
					fieldCognome.getValue(),
					fieldSesso.getFlag(),
					fieldSesso.getValue(),
					fieldCodFis.getFlag(),
					fieldCodFis.getValue(),
					fieldNote.getFlag(),
					fieldNote.getValue(),
					fieldNascitaDa.getFlag(),
					fieldNascitaDa.getValue(),
					fieldNascitaA.getFlag(),
					fieldNascitaA.getValue(),
					fieldLuogoNascita.getFlag(),
					fieldLuogoNascita.getValue(),
					fieldDenominazione.getFlag(),
					fieldDenominazione.getValue(),
					1,
					"",
					PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()
						)
					);
		} else {
			final FieldFilter<String> fieldSede = getFilteredFieldEqual(filter.getComune());
			pagedResult = testDao.findSoggetti(
					filter.getTipo(),
					fieldNome.getFlag(),
					fieldNome.getValue(),
					fieldCognome.getFlag(),
					fieldCognome.getValue(),
					fieldSesso.getFlag(),
					fieldSesso.getValue(),
					fieldCodFis.getFlag(),
					fieldCodFis.getValue(),
					fieldNote.getFlag(),
					fieldNote.getValue(),
					fieldNascitaDa.getFlag(),
					fieldNascitaDa.getValue(),
					fieldNascitaA.getFlag(),
					fieldNascitaA.getValue(),
					1,
					"",
					fieldDenominazione.getFlag(),
					fieldDenominazione.getValue(),
					fieldSede.getFlag(),
					fieldSede.getValue(),
					PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()
						)
					);
		}
		return converter.toDTO( new PagedResult<>(pagedResult) );
		/*Page<Soggetto> pagedResult = soggettoDao.findAll( 
				//SPECIFICATION
				RepositoryUtils.buildSoggettoPredicate( filter ), 
				//PAGE & SORT
				PageRequest.of( 
						filter.getPageIndex()/filter.getPageSize(), 
						filter.getPageSize() , 
						filter.getTipo().equalsIgnoreCase("P") ? soggettoDao.DEFAULT_SORT_COGNOME.and(soggettoDao.DEFAULT_SORT_NOME) : soggettoDao.DEFAULT_SORT_SG
						) 
				);
		return converter.toDTO( new PagedResult<>(pagedResult) );*/
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DettaglioSoggettiPTDTO> dettaglioSoggettiPT(Integer idSoggetto) throws Exception {
		String idLog = "dettaglioSoggettiPT";
		try{
			//List<DettaglioSoggettiPTDTO> result = new ArrayList<>();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idSoggetto: {}", idSoggetto);

			//Pageable pageable = PageRequest.of(0, 100, this.getSort("gid", "asc"));
			
			List<ParticellaCatastaleJoin> resultModel = particellaDao.dettaglioSoggettiPT(idSoggetto);
			/*for (Particella particella : resultModel) {
				particella.setFoglio(normalizzaNumeroFoglio(particella.getFoglio()));
				particella.setNumero(particella.getNumero().replaceFirst("^0+(?!$)", ""));
				Page<ParticellaGeom> geometry = particellaGeomDao.findByFoglioAndMappale(particella.getFoglio(), particella.getNumero(), pageable);
				result.add(ptConverter.toDtoDettaglioSoggetti(particella, geometry.getContent()));
			}*/
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return ptConverter.toDtoDettaglioSoggetti(resultModel);
			//return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DettaglioSoggettiUIDTO> dettaglioSoggettiUIU(Integer idSoggetto) throws Exception {
		String idLog = "dettaglioSoggettiUIU";
		try{
			//List<DettaglioSoggettiUIDTO> result = new ArrayList<>();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idSoggetto: {}", idSoggetto);
			
			//Pageable pageable = PageRequest.of(0, 100, this.getSort("gid", "asc"));
			
			List<UnitaImmobiliareJoin> resultModel = unImmDao.dettaglioSoggettiUIU(idSoggetto);
			/*for (UnitaImmobiliareJoin unitaImmobiliareJoin : resultModel) {
				//String foglio = normalizzaNumeroFoglio(unitaImmobiliareJoin.getFoglio());
				String mappale = unitaImmobiliareJoin.getNumero().replaceFirst("^0+(?!$)", "") + "+";
				Page<FabbricatiGeom> geometry = unImmGeomDao.findByFoglioAndMappale(unitaImmobiliareJoin.getFoglio(), mappale, pageable);
				result.add(uiConverter.toDtoDettaglioSoggetti(unitaImmobiliareJoin, geometry.getContent()));
			}*/
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return uiConverter.toDtoDettaglioSoggetti(resultModel);
			//return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagedResult<UnitaImmobiliareDTO> ricercaImmobiliUIU(ImmobileFilter filter) throws Exception {
		String idLog = "ricercaImmobiliUIU";
		try{
			logger.info(START, idLog);
			
			filter.setFoglio(normalizzaNumeroFoglio(filter.getFoglio()));
			filter.setNumero(normalizzaNumeroMappale(filter.getNumero()));
			filter.setSubalterno(normalizzaNumeroFoglio(filter.getSubalterno()));
			
			final FieldFilter<String> fieldComune = getFilteredFieldEqual(filter.getComune());
			final FieldFilter<String> fieldIndirizzo = getFilteredFieldLike(filter.getIndirizzo());
			final FieldFilter<String> fieldZona = getFilteredFieldEqual(filter.getZona());
			final FieldFilter<String> fieldSezione = getFilteredFieldEqual(filter.getSezioneUrbana());
			final FieldFilter<String> fieldConsistenza = getFilteredFieldEqual(filter.getConsistenza());
			final FieldFilter<String> fieldCategoria= getFilteredFieldEqual(filter.getCategoria());
			final FieldFilter<String> fieldFoglio= getFilteredFieldEqual(filter.getFoglio());
			final FieldFilter<String> fieldSuperficie = getFilteredFieldEqual(filter.getSuperficie());
			final FieldFilter<String> fieldClasse = getFilteredFieldEqual(filter.getClasse());
			if (isNumeric(filter.getNumero())) filter.setNumero(completaNumero(filter.getNumero()));		//Completo il numero a 5 caratteri aggiungendo zeri a sinistra
			final FieldFilter<String> fieldNumero = getFilteredFieldEqual(filter.getNumero());
			final FieldFilter<String> fieldRenditaLire = getFilteredFieldEqual(filter.getRenditaLire());
			final FieldFilter<String> fieldPartita = getFilteredFieldEqual(filter.getPartita());
			final FieldFilter<String> fieldSubalterno = getFilteredFieldEqual(filter.getSubalterno());
			final FieldFilter<String> fieldRenditaEuro = getFilteredFieldEqual(filter.getRenditaEuro());
			Page<UnitaImmobiliareJoinRicerca> pagedResult = null;
			
			if(filter.isSoppresso()) {
				pagedResult = unImmDao.ricercaImmobiliUIUSoppresse(
						fieldComune.getFlag(),
						fieldComune.getValue(),
						fieldIndirizzo.getFlag(),
						fieldIndirizzo.getValue(),
						fieldZona.getFlag(),
						fieldZona.getValue(),
						fieldSezione.getFlag(),
						fieldSezione.getValue(),
						fieldConsistenza.getFlag(),
						fieldConsistenza.getValue(),
						fieldCategoria.getFlag(),
						fieldCategoria.getValue(),
						fieldFoglio.getFlag(),
						fieldFoglio.getValue(),
						fieldSuperficie.getFlag(),
						fieldSuperficie.getValue(),
						fieldClasse.getFlag(),
						fieldClasse.getValue(),
						fieldNumero.getFlag(),
						fieldNumero.getValue(),
						fieldRenditaLire.getFlag(),
						fieldRenditaLire.getValue(),
						fieldPartita.getFlag(),
						fieldPartita.getValue(),
						fieldSubalterno.getFlag(),
						fieldSubalterno.getValue(),
						fieldRenditaEuro.getFlag(),
						fieldRenditaEuro.getValue(),
						PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()
						) );
			} else {
				pagedResult = unImmDao.ricercaImmobiliUIU(
						fieldComune.getFlag(),
						fieldComune.getValue(),
						fieldIndirizzo.getFlag(),
						fieldIndirizzo.getValue(),
						fieldZona.getFlag(),
						fieldZona.getValue(),
						fieldSezione.getFlag(),
						fieldSezione.getValue(),
						fieldConsistenza.getFlag(),
						fieldConsistenza.getValue(),
						fieldCategoria.getFlag(),
						fieldCategoria.getValue(),
						fieldFoglio.getFlag(),
						fieldFoglio.getValue(),
						fieldSuperficie.getFlag(),
						fieldSuperficie.getValue(),
						fieldClasse.getFlag(),
						fieldClasse.getValue(),
						fieldNumero.getFlag(),
						fieldNumero.getValue(),
						fieldRenditaLire.getFlag(),
						fieldRenditaLire.getValue(),
						fieldPartita.getFlag(),
						fieldPartita.getValue(),
						fieldSubalterno.getFlag(),
						fieldSubalterno.getValue(),
						fieldRenditaEuro.getFlag(),
						fieldRenditaEuro.getValue(),
						PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()
						) );
			}
			return uiuConverter.toDTO( new PagedResult<>(pagedResult) );
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<IdentificativiUiuDTO> dettaglioImmobiliUIUIdentificativi(Long idImmobile) throws Exception {
		String idLog = "dettaglioImmobiliUIUIdentificativi";
		try{
			List<IdentificativiUiuDTO> result = new ArrayList<>();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);

			Pageable pageable = PageRequest.of(0, 100, this.getSort("gid", "asc"));
			
			List<IdentificativoUnitaImmobiliari> resultModel = identificativiUiuDao.dettaglioImmobiliUIUIdentificativi(idImmobile);
			for (IdentificativoUnitaImmobiliari particella : resultModel) {
				String mappale = particella.getNumero().replaceFirst("^0+(?!$)", "") + "+";
				Page<FabbricatiGeom> geometry = unImmGeomDao.findByFoglioAndMappale(particella.getFoglio(), mappale, pageable);
				result.add(identificativiUiuConverter.toDtoDettaglio(particella, geometry.getContent()));
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			//return ptConverter.toDtoDettaglioSoggetti(resultModel);
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<SoggettiUiuDTO> dettaglioImmobiliUIUPersoneFisiche(Long idImmobile) throws Exception {
		String idLog = "dettaglioImmobiliUIUPersoneFisiche";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);

			List<UnitaImmobileJoinDettaglio> resultModel = unImmDao.dettaglioImmobiliUIUPersoneFisiche(idImmobile);
			List<UnitaImmobileJoinDettaglio> listaFiltrata = new ArrayList<>();
			for (UnitaImmobileJoinDettaglio item : resultModel) {
				if (listaFiltrata.isEmpty()) {
					listaFiltrata.add(item);
				} else {
					if (listaFiltrata.get(listaFiltrata.size() - 1).getDataRegistrazioneGen().equals(item.getDataRegistrazioneGen())) {
						listaFiltrata.add(item);
					} else {
						break;
					}
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, listaFiltrata.size());
			
			return dettaglioUiuConverter.toDTOUI(listaFiltrata);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<SoggettiUiuDTO> dettaglioImmobiliUIUSoggettiGiuridici(Long idImmobile) throws Exception {
		String idLog = "dettaglioImmobiliUIUSoggettiGiuridici";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);

			List<UnitaImmobileJoinDettaglio> resultModel = unImmDao.dettaglioImmobiliUIUSoggettiGiuridici(idImmobile);
			List<UnitaImmobileJoinDettaglio> listaFiltrata = new ArrayList<>();
			for (UnitaImmobileJoinDettaglio item : resultModel) {
				if (listaFiltrata.isEmpty()) {
					listaFiltrata.add(item);
				} else {
					if (listaFiltrata.get(listaFiltrata.size() - 1).getDataRegistrazioneGen().equals(item.getDataRegistrazioneGen())) {
						listaFiltrata.add(item);
					} else {
						break;
					}
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, listaFiltrata.size());
			
			return dettaglioUiuConverter.toDTOUI(listaFiltrata);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<PlanimetriaUiuDTO> dettaglioImmobiliUIUPlanimetrie(Long foglio, Long numero, Long subalterno) throws Exception {
		String idLog = "dettaglioImmobiliUIUPlanimetrie";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: foglio: {} e numero {}", foglio, numero, subalterno);

			String mappale = String.format("%05d", numero );
			String foglioString = normalizzaNumeroFoglio(foglio.toString());
			List<UnitaPlanimetrieJoinDettaglio> resultModel = unImmPlanimetriaDao.findByFoglioAndMappaleAndSubalterno(foglioString, mappale, subalterno.toString());
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return uiPlanimetrieConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public File getUIUPlanimetria(String nomeImage) throws Exception {
		String idLog = "getUIUPlanimetria";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: nomeImage {} ", nomeImage);

			String baseExportPath = env.getProperty("planimetrie.image.base.path");
			
			return new File(baseExportPath+ nomeImage+".001");
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}
	
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<SoggettiUiuDTO> dettaglioParticellePTPersoneFisiche(Long idImmobile) throws Exception {
		String idLog = "dettaglioImmobiliUIUPersoneFisiche";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);

			List<UnitaImmobileJoinDettaglio> resultModel = unImmDao.dettaglioParticellePTPersoneFisiche(idImmobile);
			List<UnitaImmobileJoinDettaglio> listaFiltrata = new ArrayList<>();
			for (UnitaImmobileJoinDettaglio item : resultModel) {
				if (listaFiltrata.isEmpty()) {
					listaFiltrata.add(item);
				} else {
					if (listaFiltrata.get(listaFiltrata.size() - 1).getDataRegistrazioneGen().equals(item.getDataRegistrazioneGen())) {
						listaFiltrata.add(item);
					} else {
						break;
					}
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, listaFiltrata.size());
			
			return dettaglioUiuConverter.toDTOPT(listaFiltrata);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<SoggettiUiuDTO> dettaglioParticellePTSoggettiGiuridici(Long idImmobile) throws Exception {
		String idLog = "dettaglioImmobiliUIUSoggettiGiuridici";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);

			List<UnitaImmobileJoinDettaglio> resultModel = unImmDao.dettaglioParticellePTSoggettiGiuridici(idImmobile);
			List<UnitaImmobileJoinDettaglio> listaFiltrata = new ArrayList<>();
			for (UnitaImmobileJoinDettaglio item : resultModel) {
				if (listaFiltrata.isEmpty()) {
					listaFiltrata.add(item);
				} else {
					if (listaFiltrata.get(listaFiltrata.size() - 1).getDataRegistrazioneGen().equals(item.getDataRegistrazioneGen())) {
						listaFiltrata.add(item);
					} else {
						break;
					}
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, listaFiltrata.size());
			
			return dettaglioUiuConverter.toDTOPT(listaFiltrata);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String localizzaUIU(Long foglio, Long numero) throws Exception {
		String idLog = "localizzaUIU";
		try{
			String result = null;
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: foglio: {} e numero {}", foglio, numero);
			
			Pageable pageable = PageRequest.of(0, 100, this.getSort("gid", "asc"));
			
			String mappale = numero.toString().replaceFirst("^0+(?!$)", "") + "+";
			String foglioString = normalizzaNumeroFoglio(foglio.toString());
			Page<FabbricatiGeom> geometry = unImmGeomDao.findByFoglioAndMappale(foglioString, mappale, pageable);
			result = geometry.getContent().get(0).getGeometry();
			
			logger.debug(DEBUG_INFO_END, idLog, 1);
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagedResult<ParticelleTerreniDTO> ricercaParticellePT(ParticellaFilter filter) throws Exception {
		String idLog = "ricercaParticellePT";
		try{
			logger.info(START, idLog);
			//Normalizzazione del foglio e della particella per la ricerca particellare
			filter.setFoglio(normalizzaNumeroFoglioPerRP(filter.getFoglio()));
			filter.setNumero(normalizzaNumeroMappalePerRP(filter.getNumero()));
			
			final FieldFilter<String> fieldComune = getFilteredFieldEqual(filter.getComune());
			final FieldFilter<String> fieldSezione = getFilteredFieldEqual(filter.getSezione());
			final FieldFilter<String> fieldFoglio = getFilteredFieldEqual(filter.getFoglio());
			if (isNumeric(filter.getNumero())) filter.setNumero(completaNumero(filter.getNumero()));		//Completo il numero a 5 caratteri aggiungendo zeri a sinistra
			final FieldFilter<String> fieldNumero = getFilteredFieldEqual(filter.getNumero());
			final FieldFilter<String> fieldSubalterno = getFilteredFieldEqual(filter.getSubalterno());
			final FieldFilter<String> fieldPartita = getFilteredFieldEqual(filter.getPartita());
			final FieldFilter<String> fieldRedditoDominicaleEuro = getFilteredFieldEqual(filter.getRedditoDominicaleEuro());
			final FieldFilter<String> fieldRedditoDominicaleLire = getFilteredFieldEqual(filter.getRedditoDominicaleLire());
			final FieldFilter<String> fieldRedditoAgrarioEuro = getFilteredFieldEqual(filter.getRedditoAgrarioEuro());
			final FieldFilter<String> fieldRedditoAgrarioLire = getFilteredFieldEqual(filter.getRedditoAgrarioLire());
			final FieldFilter<String> fieldSuperficie = getFilteredFieldEqual(filter.getSuperficie());
			Page<Particella> pagedResult = null;
			
			if(filter.isSoppresso()) {
				pagedResult = particellaDao.ricercaParticellePTSoppresse(
						fieldComune.getFlag(),
						fieldComune.getValue(),
						fieldSezione.getFlag(),
						fieldSezione.getValue(),
						fieldFoglio.getFlag(),
						fieldFoglio.getValue(),
						fieldNumero.getFlag(),
						fieldNumero.getValue(),
						fieldSubalterno.getFlag(),
						fieldSubalterno.getValue(),
						fieldPartita.getFlag(),
						fieldPartita.getValue(),
						fieldRedditoDominicaleEuro.getFlag(),
						normalizzaDecimale(fieldRedditoDominicaleEuro.getValue()),
						fieldRedditoDominicaleLire.getFlag(),
						fieldRedditoDominicaleLire.getValue(),
						fieldRedditoAgrarioEuro.getFlag(),
						normalizzaDecimale(fieldRedditoAgrarioEuro.getValue()),
						fieldRedditoAgrarioLire.getFlag(),
						fieldRedditoAgrarioLire.getValue(),
						fieldSuperficie.getFlag(),
						fieldSuperficie.getValue(),
						PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()
						) );
			} else {
				pagedResult = particellaDao.ricercaParticellePT(
						fieldComune.getFlag(),
						fieldComune.getValue(),
						fieldSezione.getFlag(),
						fieldSezione.getValue(),
						fieldFoglio.getFlag(),
						fieldFoglio.getValue(),
						fieldNumero.getFlag(),
						fieldNumero.getValue(),
						fieldSubalterno.getFlag(),
						fieldSubalterno.getValue(),
						fieldPartita.getFlag(),
						fieldPartita.getValue(),
						fieldRedditoDominicaleEuro.getFlag(),
						normalizzaDecimale(fieldRedditoDominicaleEuro.getValue()),
						fieldRedditoDominicaleLire.getFlag(),
						fieldRedditoDominicaleLire.getValue(),
						fieldRedditoAgrarioEuro.getFlag(),
						normalizzaDecimale(fieldRedditoAgrarioEuro.getValue()),
						fieldRedditoAgrarioLire.getFlag(),
						fieldRedditoAgrarioLire.getValue(),
						fieldSuperficie.getFlag(),
						fieldSuperficie.getValue(),
						PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()
						) );
			}

			return ptRicercaConverter.toDTO( new PagedResult<>(pagedResult) );

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<IdentificativiUiuDTO> dettagliParticelleUIUIdentificativi(Long idImmobile) throws Exception {
		String idLog = "dettagliParticelleUIUIdentificativi";
		try{
			List<IdentificativiUiuDTO> result = new ArrayList<>();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);

			Pageable pageable = PageRequest.of(0, 100, this.getSort("gid", "asc"));
			
			List<IdentificativoUnitaImmobiliari> resultModel = identificativiUiuDao.dettaglioImmobiliUIUIdentificativi(idImmobile);
			for (IdentificativoUnitaImmobiliari particella : resultModel) {
				String mappale = particella.getNumero().replaceFirst("^0+(?!$)", "") + "+";
				Page<FabbricatiGeom> geometry = unImmGeomDao.findByFoglioAndMappale(particella.getFoglio(), mappale, pageable);
				result.add(ptConverter.toDtoDettaglio(particella, geometry.getContent()));
			}
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String localizzaPT(Long foglio, Long numero) throws Exception {
		String idLog = "localizzaPT";
		try{
			String result = null;
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: foglio: {} e numero {}", foglio, numero);
			
			Pageable pageable = PageRequest.of(0, 100, this.getSort("gid", "asc"));
			
			String mappale = numero.toString().replaceFirst("^0+(?!$)", "");
			String foglioString = normalizzaNumeroFoglio(foglio.toString());
			Page<ParticellaGeom> geometry = particellaGeomDao.findByFoglioAndMappale(foglioString, mappale, pageable);
			result = geometry.getContent().get(0).getGeom();
			
			logger.debug(DEBUG_INFO_END, idLog, 1);
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DettaglioPtMultiploDTO dettagliParticellePTMultiplo(Long idImmobile) throws Exception {
		String idLog = "dettagliParticellePTMultiplo";
		try{
			DettaglioPtMultiploDTO result = new DettaglioPtMultiploDTO();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);
			
			List<DeduzioneParticella> resultDeduzione = deduzioneDao.findByIdImmobile(idImmobile);
			List<PorzioneParticella> resultPorzione = porzioneDao.findByIdImmobile(idImmobile);
			List<RiservaParticella> resultRiserva = riservaDao.findByIdImmobile(idImmobile);

			result.setListDeduzione(deduzioneParticellaConverter.toDTO(resultDeduzione));
			result.setListPorzione(porzioneParticellaConverter.toDTO(resultPorzione));
			result.setListRiserva(riservaParticellaConverter.toDTO(resultRiserva));
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagedResult<RicercaIntestazioniDTO> ricercaIntestazioniPersoneFisiche(IntestatariFilter filter) throws Exception {
		String idLog = "ricercaIntestazioniPersoneFisiche";
		try{
			logger.info(START, idLog);
			final FieldFilter<String> fieldNome = getFilteredFieldEqual(filter.getNome() != null ? filter.getNome().toUpperCase() : null);
			final FieldFilter<String> fieldCognome = getFilteredFieldEqual(filter.getCognome() != null ? filter.getCognome().toUpperCase() : null);
			final FieldFilter<String> fieldCodiceFiscale = getFilteredFieldEqual(filter.getCodiceFiscale() != null ? filter.getCodiceFiscale().toUpperCase() : null);
			Page<RicercaIntestazioni> pagedResult = null;
	
			Pageable pageable = PageRequest.of( 
					filter.getPageIndex()/filter.getPageSize(), 
					filter.getPageSize()
				);
			
			if (filter.isCheckboxPtPf() && !filter.isCheckboxUiuPf()) {
				pagedResult = titolaritaDao.ricercaIntestazioniPT(
						fieldNome.getFlag(),
						fieldNome.getValue(),
						fieldCognome.getFlag(),
						fieldCognome.getValue(),
						fieldCodiceFiscale.getFlag(),
						fieldCodiceFiscale.getValue(),
						"P",
						pageable );
			} else if (!filter.isCheckboxPtPf() && filter.isCheckboxUiuPf()) {
				pagedResult = titolaritaDao.ricercaIntestazioniUIU(
						fieldNome.getFlag(),
						fieldNome.getValue(),
						fieldCognome.getFlag(),
						fieldCognome.getValue(),
						fieldCodiceFiscale.getFlag(),
						fieldCodiceFiscale.getValue(),
						"P",
						pageable );
			} else {
				pagedResult = titolaritaDao.ricercaIntestazioniAll(
						fieldNome.getFlag(),
						fieldNome.getValue(),
						fieldCognome.getFlag(),
						fieldCognome.getValue(),
						fieldCodiceFiscale.getFlag(),
						fieldCodiceFiscale.getValue(),
						"P",
						pageable );
			}

		return intestatariRicercaConverter.toDTO( new PagedResult<>(pagedResult) );

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public ParticelleTerreniDTO informazioniParticellaByIdImmobile(Long idImmobile) throws Exception {
		String idLog = "informazioniParticellaByIdImmobile";
		try{
			ParticelleTerreniDTO result = new ParticelleTerreniDTO();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);
			
			Particella particella = particellaDao.findByIdImmobile(idImmobile);

			result = ptRicercaConverter.toDTOinformazioni(particella);
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public UnitaImmobiliareDTO informazioniUnitaImmobiliareByIdImmobile(Long idImmobile) throws Exception {
		String idLog = "informazioniUnitaImmobiliareByIdImmobile";
		try{
			UnitaImmobiliareDTO result = new UnitaImmobiliareDTO();
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {}", idImmobile);
			
			UnitaImmobiliareJoinRicerca particella = unImmDao.findByIdImmobile(idImmobile);

			result = uiuConverter.toDTOinformazioni(particella);
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagedResult<RicercaIntestazioniDTO> ricercaIntestazioniSoggettiGiuridici(IntestatariFilter filter)
			throws Exception {
		String idLog = "ricercaIntestazioniSoggettiGiuridici";
		try{
			logger.info(START, idLog);
			final FieldFilter<String> fieldDenominazione = getFilteredFieldLike(filter.getDenominazione() != null ? filter.getDenominazione().toUpperCase() : null);
			final FieldFilter<String> fieldCodiceFiscale = getFilteredFieldEqual(filter.getCodiceFiscale() != null ? filter.getCodiceFiscale().toUpperCase() : null);
			final FieldFilter<String> fieldCodComune = getFilteredFieldEqual(filter.getComune() != null ? filter.getComune().toUpperCase() : null);
			Page<RicercaIntestazioni> pagedResult = null;
	
			Pageable pageable = PageRequest.of( 
					filter.getPageIndex()/filter.getPageSize(), 
					filter.getPageSize()
				);
			
			if (filter.isCheckboxPtSg() && !filter.isCheckboxUiuSg()) {
				pagedResult = titolaritaDao.ricercaIntestazioniSgPT(
						fieldDenominazione.getFlag(),
						fieldDenominazione.getValue(),
						fieldCodiceFiscale.getFlag(),
						fieldCodiceFiscale.getValue(),
						fieldCodComune.getFlag(),
						fieldCodComune.getValue(),
						"G",
						pageable );
			} else if (!filter.isCheckboxPtSg() && filter.isCheckboxUiuSg()) {
				pagedResult = titolaritaDao.ricercaIntestazioniSgUIU(
						fieldDenominazione.getFlag(),
						fieldDenominazione.getValue(),
						fieldCodiceFiscale.getFlag(),
						fieldCodiceFiscale.getValue(),
						fieldCodComune.getFlag(),
						fieldCodComune.getValue(),
						"G",
						pageable );
			} else {
				pagedResult = titolaritaDao.ricercaIntestazioniSgAll(
						fieldDenominazione.getFlag(),
						fieldDenominazione.getValue(),
						fieldCodiceFiscale.getFlag(),
						fieldCodiceFiscale.getValue(),
						fieldCodComune.getFlag(),
						fieldCodComune.getValue(),
						"G",
						pageable );
			}

		return intestatariRicercaConverter.toDTO( new PagedResult<>(pagedResult) );

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<ListaIntestatariDirittoDTO> ricercaListaIntestatariTranneCorrenteConDiritto(Long idImmobile, Long idSoggetto) throws Exception {
		String idLog = "ricercaListaIntestatariTranneCorrenteConDiritto";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idImmobile: {} idSoggetto: {}", idImmobile, idSoggetto);
			
			List<SoggettoIntestatario> resultModel = soggettoDao.ricercaListaIntestatariTranneCorrenteConDiritto(idImmobile, idSoggetto);
			List<SoggettoIntestatario> listaFiltrata = new ArrayList<>();
			for (SoggettoIntestatario soggettoIntestatario : resultModel) {
				if (listaFiltrata.isEmpty()) {
					listaFiltrata.add(soggettoIntestatario);
				} else {
					if (listaFiltrata.get(listaFiltrata.size() - 1).getDataValiditaGen().equals(soggettoIntestatario.getDataValiditaGen())) {
						listaFiltrata.add(soggettoIntestatario);
					} else {
						break;
					}
				}
			}
			
			logger.debug(DEBUG_INFO_END, idLog, listaFiltrata.size());
			
			return intestatariConverter.toDtoCustomDiritto(listaFiltrata);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String getDataUltimoAggiornamento() throws Exception {
		String idLog = "getDataUltimoAggiornamento";
		try{
			logger.info(START, idLog);
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			AggiornamentoCatasto resultModel = aggiornamentoCatastoDao.getDataUltimoAggiornamento();
			
			logger.debug(DEBUG_INFO_END, idLog, 1);
			
			return formatter.format(resultModel.getDataFineValidita());
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	public String normalizzaDecimale(String decimal) {
		String preVirgola = null;
		String postVirgola = null;
		try {
			if (decimal != null) {
				String[] appo = decimal.split("\\.");
				preVirgola = appo[0];
				if (appo[1].length() == 1) {
					postVirgola = appo[1] + "00";
				} else if (appo[1].length() == 2) {
					postVirgola = appo[1] + "0";
				} else {
					postVirgola = appo[1];
				}
			}
		} catch (Exception e) {
			
		}
		return preVirgola + "." + postVirgola;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<RicercaIntestazioniDTO> soggettiFisiciQueryCount(IntestatariFilter filter) throws Exception {
		final FieldFilter<String> fieldNome = getFilteredFieldEqual(filter.getNome() != null ? filter.getNome().toUpperCase() : null);
		final FieldFilter<String> fieldCognome = getFilteredFieldEqual(filter.getCognome() != null ? filter.getCognome().toUpperCase() : null);
		final FieldFilter<String> fieldCodiceFiscale = getFilteredFieldEqual(filter.getCodiceFiscale() != null ? filter.getCodiceFiscale().toUpperCase() : null);
		List<RicercaIntestazioni> result = new ArrayList<>();
		
		if (filter.isCheckboxPtPf() && !filter.isCheckboxUiuPf()) {
			result = titolaritaDao.exportRicercaIntestazioniPT(
					fieldNome.getFlag(),
					fieldNome.getValue(),
					fieldCognome.getFlag(),
					fieldCognome.getValue(),
					fieldCodiceFiscale.getFlag(),
					fieldCodiceFiscale.getValue(),
					"P" );
		} else if (!filter.isCheckboxPtPf() && filter.isCheckboxUiuPf()) {
			result = titolaritaDao.exportRicercaIntestazioniUIU(
					fieldNome.getFlag(),
					fieldNome.getValue(),
					fieldCognome.getFlag(),
					fieldCognome.getValue(),
					fieldCodiceFiscale.getFlag(),
					fieldCodiceFiscale.getValue(),
					"P");
		} else {
			result = titolaritaDao.exportRicercaIntestazioniAll(
					fieldNome.getFlag(),
					fieldNome.getValue(),
					fieldCognome.getFlag(),
					fieldCognome.getValue(),
					fieldCodiceFiscale.getFlag(),
					fieldCodiceFiscale.getValue(),
					"P" );
		}

		List<RicercaIntestazioniDTO> dtoList = intestatariRicercaConverter.toDTO(result);
		return dtoList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<RicercaIntestazioniDTO> soggettiGiuridiciQueryCount(IntestatariFilter filter) throws Exception {
		final FieldFilter<String> fieldDenominazione = getFilteredFieldLike(filter.getDenominazione() != null ? filter.getDenominazione().toUpperCase() : null);
		final FieldFilter<String> fieldCodiceFiscale = getFilteredFieldEqual(filter.getCodiceFiscale() != null ? filter.getCodiceFiscale().toUpperCase() : null);
		final FieldFilter<String> fieldCodComune = getFilteredFieldEqual(filter.getComune() != null ? filter.getComune().toUpperCase() : null);
		List<RicercaIntestazioni> result = new ArrayList<>();
		
		if (filter.isCheckboxPtSg() && !filter.isCheckboxUiuSg()) {
			result = titolaritaDao.exportRicercaIntestazioniSgPT(
					fieldDenominazione.getFlag(),
					fieldDenominazione.getValue(),
					fieldCodiceFiscale.getFlag(),
					fieldCodiceFiscale.getValue(),
					fieldCodComune.getFlag(),
					fieldCodComune.getValue(),
					"G" );
		} else if (!filter.isCheckboxPtSg() && filter.isCheckboxUiuSg()) {
			result = titolaritaDao.exportRicercaIntestazioniSgUIU(
					fieldDenominazione.getFlag(),
					fieldDenominazione.getValue(),
					fieldCodiceFiscale.getFlag(),
					fieldCodiceFiscale.getValue(),
					fieldCodComune.getFlag(),
					fieldCodComune.getValue(),
					"G" );
		} else {
			result = titolaritaDao.exportRicercaIntestazioniSgAll(
					fieldDenominazione.getFlag(),
					fieldDenominazione.getValue(),
					fieldCodiceFiscale.getFlag(),
					fieldCodiceFiscale.getValue(),
					fieldCodComune.getFlag(),
					fieldCodComune.getValue(),
					"G" );
		}

		List<RicercaIntestazioniDTO> dtoList = intestatariRicercaConverter.toDTO(result);
		return dtoList;
	}

	public boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
	}
	
	public String completaNumero(String strNum) {
	    
	    if (strNum.length()==1) strNum = "0000"+strNum;
	    else if (strNum.length()==2) strNum = "000"+strNum;
	    else if (strNum.length()==3) strNum = "00"+strNum;
	    else if (strNum.length()==4) strNum = "0"+strNum;
	    
	    return strNum;
	}
}
