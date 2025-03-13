package it.eng.tz.urbamid.catasto.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.catasto.filter.ImmobileFilter;
import it.eng.tz.urbamid.catasto.filter.IntestatariFilter;
import it.eng.tz.urbamid.catasto.filter.ParticellaFilter;
import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.persistence.model.AggiornamentoCatasto;
import it.eng.tz.urbamid.catasto.persistence.model.CategorieCatastali;
import it.eng.tz.urbamid.catasto.persistence.model.CodiciQualita;
import it.eng.tz.urbamid.catasto.persistence.model.Comuni;
import it.eng.tz.urbamid.catasto.persistence.model.DeduzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.model.Intestazioni;
import it.eng.tz.urbamid.catasto.persistence.model.IntestazioniExport;
import it.eng.tz.urbamid.catasto.persistence.model.Particella;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaVisuraStorica;
import it.eng.tz.urbamid.catasto.persistence.model.Particellaterreni;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaterreniExport;
import it.eng.tz.urbamid.catasto.persistence.model.PorzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.model.Province;
import it.eng.tz.urbamid.catasto.persistence.model.RicercaIntestazioni;
import it.eng.tz.urbamid.catasto.persistence.model.Soggetti;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettiExport;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettiReportParticelle;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoIntestatario;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoModel;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoinRicerca;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareUrbana;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareUrbanaExport;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryAggiornamentoCatasto;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryCategorieCatastali;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryCodiciQualita;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryComuni;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryDeduzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryPorzioneParticella;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryProvince;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositorySoggetto;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryTitolarita;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryUnitaImmobiliare;
import it.eng.tz.urbamid.catasto.service.AbstractCatastoService;
import it.eng.tz.urbamid.catasto.service.ExportService;
import it.eng.tz.urbamid.catasto.util.FieldFilter;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioIntestatariStoricoDTO;
import it.eng.tz.urbamid.catasto.web.dto.ElencoNominativoDTO;
import it.eng.tz.urbamid.catasto.web.dto.ElencoNominativoInput;
import it.eng.tz.urbamid.catasto.web.dto.ElencoNominativoProprietaDTO;
import it.eng.tz.urbamid.catasto.web.dto.ElencoParticellaDTO;
import it.eng.tz.urbamid.catasto.web.dto.ElencoParticellaInput;
import it.eng.tz.urbamid.catasto.web.dto.ElencoParticellaProprietaDTO;
import it.eng.tz.urbamid.catasto.web.dto.FabbricatoStoricoDTO;
import it.eng.tz.urbamid.catasto.web.dto.IntestatariStoricoDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariVisuraDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaIntestazioniDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaSoggettiDTO;
import it.eng.tz.urbamid.catasto.web.dto.TerrenoStoricoDTO;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;
import it.eng.tz.urbamid.catasto.web.dto.VisuraFabbricatoInput;
import it.eng.tz.urbamid.catasto.web.dto.VisuraFabbricatoStoricaInput;
import it.eng.tz.urbamid.catasto.web.dto.VisuraTerrenoInput;
import it.eng.tz.urbamid.catasto.web.dto.VisuraTerrenoStoricaInput;
import it.eng.tz.urbamid.catasto.web.dto.converter.IntestatariConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.IntestatariRicercaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ParticellaRicercaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.SoggettoConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.UnitaImmobiliareRicercaConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Service
public class ExportServiceImpl extends AbstractCatastoService implements ExportService {

	private static final Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String CONTENT = "CONTENT >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO = "{} >>> Parametri passati in input sono: numFoglio: {} mappale: {}";

	@Autowired
	private Environment env;
	
	@Autowired
	private JpaRepositoryParticella particellaDao;

	@Autowired
	private JpaRepositoryUnitaImmobiliare unitaImmDao;

	@Autowired
	private JpaRepositorySoggetto soggettoDao;

	@Autowired
	private JpaRepositoryTitolarita titolaritaDao;

	@Autowired
	private JpaRepositoryComuni comuniDao;

	@Autowired
	private JpaRepositoryProvince provinceDao;

	@Autowired
	private JpaRepositoryAggiornamentoCatasto aggiornamentoCatastoDao;

	@Autowired
	private JpaRepositoryPorzioneParticella porzioneParticellaDao;

	@Autowired
	private JpaRepositoryDeduzioneParticella deduzioneParticellaDao;

	@Autowired
	private JpaRepositoryCodiciQualita qualitaDao;

	@Autowired
	private JpaRepositoryCategorieCatastali categorieDao;

	@Autowired
	private SoggettoConverter converter;

	@Autowired
	private UnitaImmobiliareRicercaConverter uiuConverter;

	@Autowired
	private ParticellaRicercaConverter ptConverter;

	@Autowired
	private IntestatariRicercaConverter intestatariConverter;

	@Autowired
	private IntestatariConverter intConverter;

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public File exportXls(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String idLog = "exportXls";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>();
			HashMap<String,List<String>> hmFoNormaMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
				
//				String foglioNorma 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
//				if (hmFoNormaMa.containsKey(comune+"|"+foglioNorma))
//					hmFoNormaMa.get(comune+"|"+foglioNorma).add(mappale);
//				else 
//					hmFoNormaMa.put(comune+"|"+foglioNorma, new ArrayList<String>(Arrays.asList(mappale)));
//				
			}

			Set<Intestazioni> lsIntestazioni = new HashSet<Intestazioni>();
			List<Soggetti> lsSoggetti = new ArrayList<Soggetti>();
			List<UnitaImmobiliareUrbana> lsUiu = new ArrayList<UnitaImmobiliareUrbana>();
			List<Particellaterreni> lsParticellaterreni = new ArrayList<Particellaterreni>();
			
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				
				lsIntestazioni.addAll( soggettoDao.exportFMIntestazioniUI(comune, foglio, entry.getValue()) );
				lsIntestazioni.addAll( soggettoDao.exportFMIntestazioniPT(comune, foglio.replaceFirst("^0+(?!$)", ""), entry.getValue()) );
				lsUiu.addAll( unitaImmDao.exportFMUnitaImmobiliareUrbana(comune, foglio, entry.getValue()) );
				lsSoggetti.addAll(soggettoDao.exportFMSoggettiUI( comune, foglio, entry.getValue()));
				lsSoggetti.addAll(soggettoDao.exportFMSoggettiPT( comune, foglio.replaceFirst("^0+(?!$)", ""), entry.getValue()));
				lsParticellaterreni.addAll( particellaDao.exportParticellaterreni(comune, foglio.replaceFirst("^0+(?!$)", ""), entry.getValue()));
			}
			
			List<Soggetti> resultModelSoggetti = new ArrayList<Soggetti>();			
			HashMap<String, Soggetti> hmSoggettoList = new HashMap<String, Soggetti>();
			for (Soggetti item : lsSoggetti) {
				if (!hmSoggettoList.containsKey(item.getCodiceFiscale())) {
					resultModelSoggetti.add(item);
					hmSoggettoList.put(item.getCodiceFiscale(), item);
				}
			}
							   
			/**SCRIVO SINGOLI FILE - ZIP FILE**/
			List<File> listaFile = new ArrayList<>();
			listaFile.add(esportaIntestatari(lsIntestazioni));
			listaFile.add(esportaSoggetti(resultModelSoggetti));
			listaFile.add(esportaUiu(lsUiu));
			listaFile.add(esportaParticellaterreni(lsParticellaterreni));
			
			return creaZip(listaFile);
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
		
	}
	
	@Override
	public File exportXlsSoggetti(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String idLog = "exportXlsSoggetti";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<Soggetti> lsSoggetti = new ArrayList<Soggetti>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				
				lsSoggetti.addAll(soggettoDao.exportFMSoggettiUI( comune, foglio, entry.getValue()));
				lsSoggetti.addAll(soggettoDao.exportFMSoggettiPT( comune, foglio.replaceFirst("^0+(?!$)", ""), entry.getValue()));
			}
							   
							   
			List<Soggetti> resultModelSoggetti = new ArrayList<Soggetti>();			
			HashMap<String, Soggetti> hmSoggettoList = new HashMap<String, Soggetti>();
			for (Soggetti item : lsSoggetti) {
				if (!hmSoggettoList.containsKey(item.getCodiceFiscale())) {
					resultModelSoggetti.add(item);
					hmSoggettoList.put(item.getCodiceFiscale(), item);
				}
			}
			
			/**SCRIVO IL FILE**/
			File soggettiXls = esportaSoggetti(resultModelSoggetti != null ? resultModelSoggetti : null);
			
			return soggettiXls;

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	public File exportXlsIntestazioni(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String idLog = "exportXlsIntestazioni";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));				
			}

			HashSet<Intestazioni> lsIntestazioni = new HashSet<Intestazioni>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
			
				lsIntestazioni.addAll( soggettoDao.exportFMIntestazioniUI(comune, foglio, entry.getValue()) );
				lsIntestazioni.addAll( soggettoDao.exportFMIntestazioniPT(comune, foglio.replaceFirst("^0+(?!$)", ""), entry.getValue()) );
			}

			/**SCRIVO IL FILE**/
			File intestazioniXls = esportaIntestatari(lsIntestazioni != null ? lsIntestazioni : null);
			
			return intestazioniXls;

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	public File exportXlsUiu(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String idLog = "exportXlsUiu";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>();
			HashMap<String,List<String>> hmFoNormaMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(comune+"|"+foglio))
					hmFoMa.get(comune+"|"+foglio).add(mappale);
				else 
					hmFoMa.put(comune+"|"+foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}

			List<UnitaImmobiliareUrbana> lsUiu = new ArrayList<UnitaImmobiliareUrbana>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
			
				lsUiu.addAll( unitaImmDao.exportFMUnitaImmobiliareUrbana(comune, foglio, entry.getValue()) );
			}

			/**SCRIVO IL FILE**/
			File UiuXls = esportaUiu(lsUiu != null ? lsUiu : null);
			
			return UiuXls;

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	public File exportXlsTerreni(List<ParticellaCustomDTO> lsParticelle) throws Exception {
		String idLog = "exportXlsTerreni";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoNormaMa = new HashMap<String,List<String>>();
			for (ParticellaCustomDTO item : lsParticelle) {
				String comune 	= item.getCodice_com();
				String mappale 	= normalizzaNumeroMappale(item.getMappale());				
				String foglioNorma 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
				
				if (hmFoNormaMa.containsKey(comune+"|"+foglioNorma))
					hmFoNormaMa.get(comune+"|"+foglioNorma).add(mappale);
				else 
					hmFoNormaMa.put(comune+"|"+foglioNorma, new ArrayList<String>(Arrays.asList(mappale)));
				
			}

			List<Particellaterreni> lsParticellaterreni = new ArrayList<Particellaterreni>();
			for (Map.Entry<String,List<String>> entry : hmFoNormaMa.entrySet()) {
				String comune = (entry.getKey().split("\\|"))[0];
				String foglio = (entry.getKey().split("\\|"))[1];
				logger.debug(comune+" --- "+foglio+" --- "+entry.getValue());
				
				lsParticellaterreni.addAll( particellaDao.exportParticellaterreni( comune, foglio, entry.getValue()) );
			}

			/**SCRIVO SINGOLI FILE - ZIP FILE**/
			File particellaTerreniXls = esportaParticellaterreni(lsParticellaterreni != null ? lsParticellaterreni : null);
			
			return particellaTerreniXls;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private File esportaIntestatari(Set<Intestazioni> lsIntestazioni) {
		List<IntestazioniExport> list = new ArrayList<>();
		for (Intestazioni item : lsIntestazioni) {
			IntestazioniExport appo = new IntestazioniExport();
			list.add(appo.convert(item));
		}
		
		/**Parametrizzazione del path di Export particellari - Intestazioni **/
		String baseExportPath = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
		String templateReport = baseExportPath + File.separator + "Intestazioni.jasper";
		return creaXlsEffettivo(templateReport, list, "Intestazioni");
	}

	private File esportaParticellaterreni(List<Particellaterreni> lsParticellaterreni) {
		List<ParticellaterreniExport> list = new ArrayList<>();
		for (Particellaterreni item : lsParticellaterreni) {
//			String foglio = "0" + item.getFoglio();
//			String mappale = item.getNumero().replaceFirst("^0+(?!$)", "");
//			Double superficieGeometrica = particellaDao.findSuperficieGeometrica(foglio, mappale);
			ParticellaterreniExport appo = new ParticellaterreniExport();
			list.add(appo.convert(item));
		}
		
		/**Parametrizzazione del path di Export particellari - Particellaterreni **/
		String baseExportPath = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
		String templateReport = baseExportPath + File.separator + "Particellaterreni.jasper";
		return creaXlsEffettivo(templateReport, list, "Particellaterreni");
	}

	private File esportaSoggetti(List<Soggetti> lsSoggetti) {
		final List<SoggettiExport> list = new ArrayList<>();
		for (Soggetti item : lsSoggetti) {
			final SoggettiExport appo = new SoggettiExport();
			/** CREO IL SIMPLEDATEFORMAT PER IL FORMATO ITALIANO **/
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			appo.setIdentificativo(item.getIdentificativo());
			appo.setUte(item.getUte());
			appo.setPersonafisica(item.getPersonaFisica());				
			appo.setSesso(item.getSesso());
			appo.setNome(item.getPersonaFisica().equalsIgnoreCase("G") ? item.getDenominazione() : item.getNome());
			appo.setCognome(item.getCognome());
			appo.setCodicefiscale(item.getCodiceFiscale());
			appo.setDatanascita(item.getDataNascita() != null ? sdf.format(item.getDataNascita()) : null);
			appo.setLuogonascita(item.getIdbelfioreNascita() != null ? soggettoDao.luogoNascita(item.getIdbelfioreNascita()) : null);
			appo.setIdbelfiorenascita(item.getIdbelfioreNascita());
			appo.setAnnotazioni(item.getAnnotazioni());
			
			list.add(appo);
		}
		
		/**Parametrizzazione del path di Export particellari - Soggetti **/
		final String baseExportPath = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
		final String templateReport = baseExportPath + File.separator + "Soggetti.jasper";
		return creaXlsEffettivo(templateReport, list, "Soggetti");
	}

	private File esportaUiu(List<UnitaImmobiliareUrbana> lsUiu) {
		List<UnitaImmobiliareUrbanaExport> list = new ArrayList<>();
		for (UnitaImmobiliareUrbana item : lsUiu) {
			UnitaImmobiliareUrbanaExport appo = new UnitaImmobiliareUrbanaExport();
			list.add(appo.convert(item));
		}
		
		/**Parametrizzazione del path di Export particellari - UnitaImmobiliareUrbana **/
		String baseExportPath = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
		String templateReport = baseExportPath + File.separator + "UnitaImmobiliareUrbana.jasper";
		return creaXlsEffettivo(templateReport, list, "UnitaImmobiliareUrbana");
	}

	private File creaXlsEffettivo(String templateReport, List<?> listaOggetti, String nomeFile) {
		
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
		File dir = new File(exportPath);
		dir.mkdir();
		/**CREATE NAME FILE**/
		File xlsfile = new File(exportPath + nomeFile + ".xls");
		
		try {
			HashMap<String, Object> xlsParams = new HashMap<>();
			xlsParams.put(JRParameter.IS_IGNORE_PAGINATION, true);
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listaOggetti);		
	        JasperPrint xlsPrint = JasperFillManager.fillReport(templateReport, xlsParams, beanCollectionDataSource);
	
	        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
	        xlsReportConfiguration.setOnePagePerSheet(false);
	        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
	        xlsReportConfiguration.setDetectCellType(false);
	        xlsReportConfiguration.setWrapText(true);
	        xlsReportConfiguration.setWhitePageBackground(false);
	        xlsReportConfiguration.setCollapseRowSpan(true);
	        xlsReportConfiguration.setFitHeight(0);
	        xlsReportConfiguration.setFitWidth(1);
	        
	        JRXlsExporter xlsExporter = new JRXlsExporter();
	        xlsExporter.setExporterInput(new SimpleExporterInput(xlsPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsfile));
	        xlsExporter.setConfiguration(xlsReportConfiguration);
	        xlsExporter.exportReport();
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return xlsfile;
	}
	
    public static boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
		return dir.delete();
	}

	private File creaZip(List<File> listaFile) {
		
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
		if (!dir.isDirectory()) {
        	dir.mkdir();
		}
		
		/**CREATE NAME FILE**/
		LocalTime localTime = LocalTime.now();
		String filename = env.getProperty("catasto.export.filename") + currentDate.getYear() + currentDate.getDayOfMonth() + "_" + localTime.getHourOfDay() + localTime.getMinuteOfHour();
		
		/**CREATE FILE**/
		String filePath = exportPath + filename + ".zip";
		File zipfile = new File(filePath);
	    // Creo un buffer per leggere i file
	    byte[] buf = new byte[1024];
	    try {
	        // Creo il file ZIP
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
	        for (File file : listaFile) {
	            FileInputStream in = new FileInputStream(file.getCanonicalPath());
	            // aggiungo il file all'output stream dello ZIP
	            out.putNextEntry(new ZipEntry(file.getName()));
	            // trasferisco i byte dal file allo ZIP 
	            int len;
	            while((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	            out.closeEntry();
	            in.close();
	            // cancello i file .xls creati precedentemente
	            FileUtils.deleteQuietly(file);
	        }
	        out.close();
	    } catch (IOException ex) {
	    	logger.error(ex.getMessage());
	    }
	    
	    return zipfile;
	}

	@Override
	@SuppressWarnings("unchecked")
	public File exportPersoneFisiche(SoggettoFilter filter) throws Exception {
		String idLog = "exportPersoneFisiche";
		try{
			logger.info(START, idLog);

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			final FieldFilter<String> fieldNome = getFilteredFieldEqual(filter.getNome());
			final FieldFilter<String> fieldCognome = getFilteredFieldEqual(filter.getCognome());
			final FieldFilter<String> fieldSesso = getFilteredFieldEqual(filter.getSesso());
			final FieldFilter<String> fieldCodFis = getFilteredFieldEqual(filter.getCodiceFiscale());
			final FieldFilter<String> fieldNote = getFilteredFieldEqual(filter.getNote());
			final FieldFilter<String> fieldDenominazione = getFilteredFieldLike(filter.getDenominazione());
			final FieldFilter<Date> fieldNascitaDa = getFilteredFieldDate(filter.getDataNascitaDa());
			final FieldFilter<Date> fieldNascitaA = getFilteredFieldDate(filter.getDataNascitaA());
			final FieldFilter<String> fieldLuogoNascita = getFilteredFieldEqual(filter.getComune());
			
			List<SoggettoModel> pagedResult = null;

			pagedResult = soggettoDao.exportFindSoggetti(
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
				""
			);

			List<Comuni> lsComuni = comuniDao.getComuni();
			List<Province> lsProvince = provinceDao.getProvince();
			List<RicercaSoggettiDTO> dtoList = converter.toDTO(pagedResult);

			for (RicercaSoggettiDTO dto : dtoList) {
				String codBelfiore = dto.getLuogoNascita();
				dto.setLuogoNascita(decodificaComune(codBelfiore, lsComuni));
				dto.setProvincia(decodificaProvinciaDaComune(codBelfiore, lsComuni, lsProvince));
			}

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "RicercaSoggettiPF.jasper";
			return creaXlsEffettivo(templateReport, dtoList, "ExportPersoneFisiche");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public File exportSoggettiGiuridici(SoggettoFilter filter) throws Exception {
		String idLog = "exportSoggettiGiuridici";
		try{
			logger.info(START, idLog);

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			final FieldFilter<String> fieldNome = getFilteredFieldEqual(filter.getNome());
			final FieldFilter<String> fieldCognome = getFilteredFieldEqual(filter.getCognome());
			final FieldFilter<String> fieldSesso = getFilteredFieldEqual(filter.getSesso());
			final FieldFilter<String> fieldCodFis = getFilteredFieldEqual(filter.getCodiceFiscale());
			final FieldFilter<String> fieldNote = getFilteredFieldEqual(filter.getNote());
			final FieldFilter<String> fieldDenominazione = getFilteredFieldLike(filter.getDenominazione());
			final FieldFilter<Date> fieldNascitaDa = getFilteredFieldDate(filter.getDataNascitaDa());
			final FieldFilter<Date> fieldNascitaA = getFilteredFieldDate(filter.getDataNascitaA());
			final FieldFilter<String> fieldSede = getFilteredFieldEqual(filter.getComune());
			
			List<SoggettoModel> pagedResult = null;

			pagedResult = soggettoDao.exportFindSoggetti(
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
					fieldSede.getValue()
			);
			
			List<Comuni> lsComuni = comuniDao.getComuni();
			List<Province> lsProvince = provinceDao.getProvince();
			List<RicercaSoggettiDTO> dtoList = converter.toDTO(pagedResult);

			for (RicercaSoggettiDTO dto : dtoList) {
				String codBelfiore = dto.getSede();
				dto.setSede(decodificaComune(codBelfiore, lsComuni));
				dto.setProvincia(decodificaProvinciaDaComune(codBelfiore, lsComuni, lsProvince));
			}

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "RicercaSoggettiSG.jasper";
			return creaXlsEffettivo(templateReport, dtoList, "ExportPersoneGiuridiche");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private String decodificaProvinciaDaComune(String luogoNascita, List<Comuni> lsComuni, List<Province> lsProvince) {
		if (luogoNascita != null) {
			Optional<Comuni> comune = lsComuni.stream().filter(x -> x.getCodiceMf().equalsIgnoreCase(luogoNascita)).findFirst();
			if (comune.isPresent()) {
				Long idProvincia = lsComuni.stream().filter(x -> x.getCodiceMf().equalsIgnoreCase(luogoNascita)).findFirst().get().getIdProvincia();
				return lsProvince.stream().filter(x -> x.getIdProvincia().equals(idProvincia)).findFirst().get().getDenominazione();
			} else {
				return "N/D";
			}
		} else {
			return "N/D";
		}
	}

	private String decodificaComune(String luogoNascita, List<Comuni> lsComuni) {
		if (luogoNascita != null) {
			Optional<Comuni> comune = lsComuni.stream().filter(x -> x.getCodiceMf().equalsIgnoreCase(luogoNascita)).findFirst();
			if (comune.isPresent()) {
				return comune.get().getDenominazione();
			} else {
				return "N/D";
			}
		} else {
			return "N/D";
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public File exportImmobiliUIU(ImmobileFilter filter) throws Exception {
		String idLog = "exportImmobiliUIU";
		try{
			logger.info(START, idLog);

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));
			
			List<UnitaImmobiliareJoinRicerca> result = new ArrayList<>();

			final FieldFilter<String> fieldComune = getFilteredFieldEqual(filter.getComune());
			final FieldFilter<String> fieldIndirizzo = getFilteredFieldLike(filter.getIndirizzo());
			final FieldFilter<String> fieldZona = getFilteredFieldEqual(normalizzaZona(filter.getZona()));
			final FieldFilter<String> fieldSezione = getFilteredFieldEqual(filter.getSezioneUrbana());
			final FieldFilter<String> fieldConsistenza = getFilteredFieldEqual(filter.getConsistenza());
			final FieldFilter<String> fieldCategoria= getFilteredFieldEqual(filter.getCategoria());
			final FieldFilter<String> fieldFoglio= getFilteredFieldEqual(normalizzaNumeroFoglio(filter.getFoglio()));
			final FieldFilter<String> fieldSuperficie = getFilteredFieldEqual(filter.getSuperficie());
			final FieldFilter<String> fieldClasse = getFilteredFieldEqual(filter.getClasse());
			final FieldFilter<String> fieldNumero = getFilteredFieldEqual(normalizzaNumero(filter.getNumero()));
			final FieldFilter<String> fieldRenditaLire = getFilteredFieldEqual(filter.getRenditaLire());
			final FieldFilter<String> fieldPartita = getFilteredFieldEqual(filter.getPartita());
			final FieldFilter<String> fieldSubalterno = getFilteredFieldEqual(normalizzaSubalterno(filter.getSubalterno()));
			final FieldFilter<String> fieldRenditaEuro = getFilteredFieldEqual(filter.getRenditaEuro());

			if (filter.isSoppresso()) {
				result = unitaImmDao.exportImmobiliUIUSoppresse(
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
						fieldRenditaEuro.getValue()
						);
			} else {
				result = unitaImmDao.exportImmobiliUIU(
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
						fieldRenditaEuro.getValue()
						);
			}
			
			List<UnitaImmobiliareDTO> dtoList = uiuConverter.toDTO(result);

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "RicercaUnitaImmobiliareUrbana.jasper";
			return creaXlsEffettivo(templateReport, dtoList, "ExportImmobili");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public File exportParticellePT(ParticellaFilter filter) throws Exception {
		String idLog = "exportParticellePT";
		try{
			logger.info(START, idLog);

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));

			final FieldFilter<String> fieldComune = getFilteredFieldEqual(filter.getComune());
			final FieldFilter<String> fieldSezione = getFilteredFieldEqual(filter.getSezione());
			final FieldFilter<String> fieldFoglio = getFilteredFieldEqual(filter.getFoglio());
			final FieldFilter<String> fieldNumero = getFilteredFieldEqual(filter.getNumero());
			final FieldFilter<String> fieldSubalterno = getFilteredFieldEqual(filter.getSubalterno());
			final FieldFilter<String> fieldPartita = getFilteredFieldEqual(filter.getPartita());
			final FieldFilter<String> fieldRedditoDominicaleEuro = getFilteredFieldEqual(filter.getRedditoDominicaleEuro());
			final FieldFilter<String> fieldRedditoDominicaleLire = getFilteredFieldEqual(filter.getRedditoDominicaleLire());
			final FieldFilter<String> fieldRedditoAgrarioEuro = getFilteredFieldEqual(filter.getRedditoAgrarioEuro());
			final FieldFilter<String> fieldRedditoAgrarioLire = getFilteredFieldEqual(filter.getRedditoAgrarioLire());
			final FieldFilter<String> fieldSuperficie = getFilteredFieldEqual(filter.getSuperficie());
			List<Particella> result = new ArrayList<>();
			
			if(filter.isSoppresso()) {
				result = particellaDao.exportRicercaParticellePTSoppresse(
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
						fieldRedditoDominicaleEuro.getValue(),
						fieldRedditoDominicaleLire.getFlag(),
						fieldRedditoDominicaleLire.getValue(),
						fieldRedditoAgrarioEuro.getFlag(),
						fieldRedditoAgrarioEuro.getValue(),
						fieldRedditoAgrarioLire.getFlag(),
						fieldRedditoAgrarioLire.getValue(),
						fieldSuperficie.getFlag(),
						fieldSuperficie.getValue());
			} else {
				result = particellaDao.exportRicercaParticellePT(
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
						fieldRedditoDominicaleEuro.getValue(),
						fieldRedditoDominicaleLire.getFlag(),
						fieldRedditoDominicaleLire.getValue(),
						fieldRedditoAgrarioEuro.getFlag(),
						fieldRedditoAgrarioEuro.getValue(),
						fieldRedditoAgrarioLire.getFlag(),
						fieldRedditoAgrarioLire.getValue(),
						fieldSuperficie.getFlag(),
						fieldSuperficie.getValue());
			}

			List<ParticelleTerreniDTO> dtoList = ptConverter.toDTO(result);

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "RicercaParticella.jasper";
			return creaXlsEffettivo(templateReport, dtoList, "ExportParticelle");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public File exportIntestazioniPF(IntestatariFilter filter) throws Exception {
		String idLog = "exportParticellePT";
		try{
			logger.info(START, idLog);

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));

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

			List<RicercaIntestazioniDTO> dtoList = intestatariConverter.toDTO(result);

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "RicercaIntestatariPF.jasper";
			return creaXlsEffettivo(templateReport, dtoList, "ExportIntestazioni");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public File exportIntestazioniSG(IntestatariFilter filter) throws Exception {
		String idLog = "exportIntestazioniSG";
		try{
			logger.info(START, idLog);

			/**DELETE FILES ON FOLDER**/
			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getDayOfMonth() + File.separator;
			deleteDirectory(new File(exportPath));

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

			List<RicercaIntestazioniDTO> dtoList = intestatariConverter.toDTO(result);

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "RicercaIntestatariSG.jasper";
			return creaXlsEffettivo(templateReport, dtoList, "ExportIntestazioniSG");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	public File exportFabbricatiPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception {
		String idLog = "exportFabbricatiPerNominativo";
		try{

			long startTime = 0L;
			long endTime = 0L;
			
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			for (ParticellaCustomDTO item : lsParticelle) {
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(foglio))
					hmFoMa.get(foglio).add(mappale);
				else 
					hmFoMa.put(foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<SoggettiReportParticelle> list = new ArrayList<SoggettiReportParticelle>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String foglio = entry.getKey();
				logger.debug(foglio+" --- "+entry.getValue());
				list.addAll( soggettoDao.soggettiFMFabbricatiNominativo(foglio, entry.getValue()) );
				
			}
			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERO DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			List<ElencoNominativoDTO> resultDTO = creaOggettoPerNominativo(list);
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			ElencoNominativoInput input = new ElencoNominativoInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "FabbricatiPerNominativo.jasper";

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultDTO, false);
			input.setNominativiDataSource(dataSource);
			input.setTitolo(titolo);
			input.setTipologiaEstrazione(tipologiaEstrazione);
			
			return creaPdfEffettivo(input, templateReport, "ExportFabbricatiPerNominativo");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	public File exportTerreniPerNominativo(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception {
		String idLog = "exportTerreniPerNominativo";
		try{
			long startTime = 0L;
			long endTime = 0L;
			
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			for (ParticellaCustomDTO item : lsParticelle) {
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
				if (hmFoMa.containsKey(foglio))
					hmFoMa.get(foglio).add(mappale);
				else 
					hmFoMa.put(foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<SoggettiReportParticelle> list = new ArrayList<SoggettiReportParticelle>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String foglio = entry.getKey();
				logger.debug(foglio+" --- "+entry.getValue());
				list.addAll( soggettoDao.soggettiFMTerreniNominativo(foglio, entry.getValue()) );
			}
			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			List<ElencoNominativoDTO> resultDTO = creaOggettoPerNominativo(list);
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			ElencoNominativoInput input = new ElencoNominativoInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "TerreniPerNominativo.jasper";

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultDTO, false);
			input.setNominativiDataSource(dataSource);
			input.setTitolo(titolo);
			input.setTipologiaEstrazione(tipologiaEstrazione);

			return creaPdfEffettivo(input, templateReport, "ExportTerreniPerNominativo");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	public File exportFabbricatiPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception {
		String idLog = "exportFabbricatiPerNominativo";
		try{
			long startTime = 0L;
			long endTime = 0L;

			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			for (ParticellaCustomDTO item : lsParticelle) {
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio();
				if (hmFoMa.containsKey(foglio))
					hmFoMa.get(foglio).add(mappale);
				else 
					hmFoMa.put(foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<SoggettiReportParticelle> list = new ArrayList<SoggettiReportParticelle>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String foglio = entry.getKey();
				logger.debug(foglio+" --- "+entry.getValue());
				list.addAll( soggettoDao.soggettiFMFabbricatiParticella(foglio, entry.getValue()) );	
			}
			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");

			startTime = System.nanoTime();
			List<ElencoParticellaDTO> resultDTO = creaOggettoPerParticella(list);
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			ElencoParticellaInput input = new ElencoParticellaInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "FabbricatiPerParticella.jasper";

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultDTO, false);
			input.setParticelleDataSource(dataSource);
			input.setTitolo(titolo);
			input.setTipologiaEstrazione(tipologiaEstrazione);

			return creaPdfEffettivo(input, templateReport, "ExportFabbricatiPerParticella");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	public File exportTerreniPerParticella(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception {
		String idLog = "exportTerreniPerParticella";
		try{
			long startTime = 0L;
			long endTime = 0L;

			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			HashMap<String,List<String>> hmFoMa = new HashMap<String,List<String>>(); 
			for (ParticellaCustomDTO item : lsParticelle) {
				String mappale 	= normalizzaNumeroMappale(item.getMappale());
				String foglio 	= item.getFoglio().replaceFirst("^0+(?!$)", "");
				if (hmFoMa.containsKey(foglio))
					hmFoMa.get(foglio).add(mappale);
				else 
					hmFoMa.put(foglio, new ArrayList<String>(Arrays.asList(mappale)));
			}
			
			List<SoggettiReportParticelle> list = new ArrayList<SoggettiReportParticelle>();
			for (Map.Entry<String,List<String>> entry : hmFoMa.entrySet()) {
				String foglio = entry.getKey();
				logger.debug(foglio+" --- "+entry.getValue());
				list.addAll( soggettoDao.soggettiFMTerreniParticella(foglio, entry.getValue()) );
				
			}
			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");

			startTime = System.nanoTime();
			List<ElencoParticellaDTO> resultDTO = creaOggettoPerParticella(list);
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			ElencoParticellaInput input = new ElencoParticellaInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "TerreniPerParticella.jasper";

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultDTO, false);
			input.setParticelleDataSource(dataSource);
			input.setTitolo(titolo);
			input.setTipologiaEstrazione(tipologiaEstrazione);

			return creaPdfEffettivo(input, templateReport, "ExportTerreniPerParticella");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private List<ElencoParticellaDTO> creaOggettoPerParticella(List<SoggettiReportParticelle> list) {

		List<ElencoParticellaDTO> resultDTO = new ArrayList<>();

		List<Comuni> lsComuni = comuniDao.getComuni();

		for (SoggettiReportParticelle item : list) {
			if (item.getFoglio() != null) {
				if (resultDTO.stream().noneMatch(e -> e.getFoglio().equalsIgnoreCase(item.getFoglio())) && resultDTO.stream().noneMatch(e -> e.getNumero().equalsIgnoreCase(item.getNumero()))) {
					ElencoParticellaDTO dto = new ElencoParticellaDTO();
					dto.setFoglio(item.getFoglio());
					dto.setNumero(item.getNumero());
					ElencoParticellaProprietaDTO proprietarioDto = creaOggettoPerParticella(resultDTO, item, lsComuni);
					dto.getListProprietari().add(proprietarioDto);
					resultDTO.add(dto);
				} else if (resultDTO.stream().anyMatch(e -> e.getFoglio().equalsIgnoreCase(item.getFoglio())) && resultDTO.stream().noneMatch(e -> e.getNumero().equalsIgnoreCase(item.getNumero()))) {
					ElencoParticellaDTO dto = new ElencoParticellaDTO();
					dto.setFoglio(item.getFoglio());
					dto.setNumero(item.getNumero());
					ElencoParticellaProprietaDTO proprietarioDto = creaOggettoPerParticella(resultDTO, item, lsComuni);
					dto.getListProprietari().add(proprietarioDto);
					resultDTO.add(dto);
				} else {
					Optional<ElencoParticellaDTO> optional = resultDTO.stream().filter(e -> e.getFoglio().equalsIgnoreCase(item.getFoglio())).filter(e -> e.getNumero().equalsIgnoreCase(item.getNumero())).findFirst();
					if (optional.isPresent()) {
						ElencoParticellaDTO dto = optional.get();
						if (item.getCodiceFiscale() != null) {
							if (dto.getListProprietari().parallelStream().noneMatch(e -> e.getCodiceFiscale().equalsIgnoreCase(item.getCodiceFiscale()))) {
								ElencoParticellaProprietaDTO proprietarioDto = creaOggettoPerParticella(resultDTO, item, lsComuni);
								dto.getListProprietari().add(proprietarioDto);
							}
						} else {
							ElencoParticellaProprietaDTO proprietarioDto = creaOggettoPerParticella(resultDTO, item, lsComuni);
							dto.getListProprietari().add(proprietarioDto);
						}						
					}
				}
			} else {
				//TODO
			}
		}
		return resultDTO;
	}

	private ElencoParticellaProprietaDTO creaOggettoPerParticella(List<ElencoParticellaDTO> resultDTO, SoggettiReportParticelle item, List<Comuni> lsComuni) {
		ElencoParticellaProprietaDTO proprietarioDto = new ElencoParticellaProprietaDTO();
		if (item.getTipoSoggetto().equalsIgnoreCase("P")) {
			proprietarioDto.setNome(item.getNome());
			proprietarioDto.setCognome(item.getCognome());
		} else {
			proprietarioDto.setCognome(item.getDenominazione());
		}
			proprietarioDto.setCodiceFiscale(item.getCodiceFiscale() != null ? item.getCodiceFiscale() : "");
			proprietarioDto.setDataNascita(item.getDataNascita() == null ? null : formatter.format(item.getDataNascita()));
			proprietarioDto.setCitta(getNomeCitta(item.getLuogoNascita(), lsComuni));
			proprietarioDto.setDiritto(item.getDescrizione());
			proprietarioDto.setTipoSoggetto(item.getTipoSoggetto());
		return proprietarioDto;
	}

	private List<ElencoNominativoDTO> creaOggettoPerNominativo(List<SoggettiReportParticelle> list) {

		List<ElencoNominativoDTO> resultDTO = new ArrayList<>();
		
		List<Comuni> lsComuni = comuniDao.getComuni();

		for (SoggettiReportParticelle item : list) {
			if (item.getCodiceFiscale() != null) {
				if (resultDTO.stream().noneMatch(e -> e.getCodiceFiscale().equalsIgnoreCase(item.getCodiceFiscale()))) {
					ElencoNominativoDTO dto = popolaOggettoPerNominativo(resultDTO, item, lsComuni);
					resultDTO.add(dto);
				} else {
					Optional<ElencoNominativoDTO> optional = resultDTO.stream().filter(e -> e.getCodiceFiscale().equalsIgnoreCase(item.getCodiceFiscale())).filter(x -> x.getDiritto().equalsIgnoreCase(item.getDescrizione())).findFirst();
					if (optional.isPresent()) {
						ElencoNominativoDTO dto = optional.get();
						if (dto.getListProprieta().stream().anyMatch(x -> x.getFoglio().equalsIgnoreCase(item.getFoglio()))) {
							ElencoNominativoProprietaDTO proprietaDto = new ElencoNominativoProprietaDTO();
							proprietaDto.setFoglio("");
							proprietaDto.setNumero(item.getNumero());
							dto.getListProprieta().add(proprietaDto);
						} else if (dto.getListProprieta().stream().noneMatch(x -> x.getFoglio().equalsIgnoreCase(item.getFoglio()))) {
							ElencoNominativoProprietaDTO proprietaDto = new ElencoNominativoProprietaDTO();
							proprietaDto.setFoglio(item.getFoglio());
							proprietaDto.setNumero(item.getNumero());
							dto.getListProprieta().add(proprietaDto);
						}	
					} else {
						ElencoNominativoDTO newNominativo = popolaOggettoPerNominativo(resultDTO, item, lsComuni);
						resultDTO.add(newNominativo);
					}				
				}
			} else {
				if (item.getTipoSoggetto().equalsIgnoreCase("G")) {
					Optional<ElencoNominativoDTO> optional = resultDTO.stream().filter(x -> x.getCognome().equalsIgnoreCase(item.getDenominazione())).findFirst();
					if(optional.isPresent()) {
						ElencoNominativoDTO dto = optional.get();
						if (dto.getListProprieta().stream().anyMatch(x -> x.getFoglio().equalsIgnoreCase(item.getFoglio()))) {
							ElencoNominativoProprietaDTO proprietaDto = new ElencoNominativoProprietaDTO();
							proprietaDto.setFoglio("");
							proprietaDto.setNumero(item.getNumero());
							dto.getListProprieta().add(proprietaDto);
						} else if (dto.getListProprieta().stream().noneMatch(x -> x.getFoglio().equalsIgnoreCase(item.getFoglio()))) {
							ElencoNominativoProprietaDTO proprietaDto = new ElencoNominativoProprietaDTO();
							proprietaDto.setFoglio(item.getFoglio());
							proprietaDto.setNumero(item.getNumero());
							dto.getListProprieta().add(proprietaDto);
						}						
					} else {
						ElencoNominativoDTO dto = popolaOggettoPerNominativo(resultDTO, item, lsComuni);
						resultDTO.add(dto);
					}
				} else {
					ElencoNominativoDTO dto = popolaOggettoPerNominativo(resultDTO, item, lsComuni);
					resultDTO.add(dto);					
				}
			}
		}
		return resultDTO;
	}

	private ElencoNominativoDTO popolaOggettoPerNominativo(List<ElencoNominativoDTO> resultDTO,	SoggettiReportParticelle item, List<Comuni> lsComuni) {
				ElencoNominativoDTO dto = new ElencoNominativoDTO();
				if (item.getTipoSoggetto().equalsIgnoreCase("P")) {
					dto.setNome(item.getNome());
					dto.setCognome(item.getCognome());
				} else {
					dto.setCognome(item.getDenominazione());
				}
				dto.setCodiceFiscale(item.getCodiceFiscale() != null ? item.getCodiceFiscale() : "");
				dto.setDataNascita(item.getDataNascita() == null ? null : formatter.format(item.getDataNascita()));
				dto.setCitta(getNomeCitta(item.getLuogoNascita(), lsComuni));
				dto.setDiritto(item.getDescrizione());
				dto.setTipoSoggetto(item.getTipoSoggetto());
				ElencoNominativoProprietaDTO proprietaDto = new ElencoNominativoProprietaDTO();
				proprietaDto.setFoglio(item.getFoglio());
				proprietaDto.setNumero(item.getNumero());
				dto.getListProprieta().add(proprietaDto);
		return dto;
	}

	private String getNomeCitta(String luogoNascita, List<Comuni> lsComuni) {
		if (luogoNascita != null) {
			Optional<Comuni> comune = lsComuni.stream().filter(x -> x.getCodiceMf().equalsIgnoreCase(luogoNascita)).findFirst();
			if (comune.isPresent()) {
				Comuni temp = comune.get();
				return temp.getDenominazione();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private String getNomeProvinciaFromComune(String luogoNascita, List<Comuni> lsComuni, List<Province> lsProvince) {
		if (luogoNascita != null) {
			Optional<Comuni> comune = lsComuni.stream().filter(x -> x.getCodiceMf().equalsIgnoreCase(luogoNascita)).findFirst();
			if (comune.isPresent()) {
				Comuni temp = comune.get();
				Optional<Province> provincia = lsProvince.stream().filter(x -> x.getIdProvincia().equals(temp.getIdProvincia())).findFirst();
				return provincia.get().getDenominazione();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private File creaPdfEffettivo(ElencoParticellaInput input, String templateReport, String nomeFile) {
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + File.separator;

		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
		if (!dir.isDirectory()) {
        	dir.mkdir();
			System.out.println("############################################## LA DIRECTORY NON ESISTE");
			logger.info("############################################## LA DIRECTORY NON ESISTE");
        } else { 
			System.out.println("############################################## LA DIRECTORY ESISTE");
			logger.info("############################################## LA DIRECTORY ESISTE");
		}
		
		/**CREATE NAME FILE**/
		String filename = env.getProperty("catasto.export.filename") + nomeFile + "_" + localTime.getHourOfDay()+localTime.getMinuteOfHour(); 
		File pdfFile = new File(exportPath + filename + ".pdf");
		if (!pdfFile.exists()) {
			System.out.println("############################################## IL FILE NON ESISTE");
			logger.info("############################################## IL FILE NON ESISTE");
		} else { 
			System.out.println("############################################## IL FILE ESISTE");
			logger.info("############################################## IL FILE ESISTE");
		}
		try {
	        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
	        
	           JasperPrint jasperPrint = JasperFillManager.fillReport(
	        		   templateReport,
	                   input.getDataSources(), 
	                   mapArray);
			
	
	        JRPdfExporter xlsExporter = new JRPdfExporter();
	
	        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
	
	        xlsExporter.exportReport();
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	private File creaPdfEffettivo(ElencoNominativoInput input, String templateReport, String nomeFile) {
		
		long startTime = 0L;
		long endTime = 0L;
		
		startTime = System.nanoTime();
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + File.separator;
		
		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
		if (!dir.isDirectory()) {
        	dir.mkdir();
		}
        
		/**CREATE NAME FILE**/
		String filename = env.getProperty("catasto.export.filename") + nomeFile + "_" + localTime.getHourOfDay()+localTime.getMinuteOfHour(); 
		File pdfFile = new File(exportPath + filename + ".pdf");
		try {
	        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
	        
	           JasperPrint jasperPrint = JasperFillManager.fillReport(
	        		   templateReport,
	                   input.getDataSources(), 
	                   mapArray);
			
	
	        JRPdfExporter xlsExporter = new JRPdfExporter();
	
	        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
	
	        xlsExporter.exportReport();
	        endTime = System.nanoTime();
	        System.out.println("################################### TIME CREA PDF: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	@Override
	public File exportPdf(List<ParticellaCustomDTO> lsParticelle, String titolo, String tipologiaEstrazione) throws Exception {
		String idLog = "exportPdf";
		try{
			logger.info(START, idLog);
			logger.debug(DEBUG_INFO, idLog, lsParticelle.stream().map(ParticellaCustomDTO::getFoglio).distinct().collect(Collectors.toList()), 
					lsParticelle.stream().map(ParticellaCustomDTO::getMappale).collect(Collectors.toList()));

			String baseExportPath = env.getProperty("catasto.export.base.path");
			LocalDate currentDate = LocalDate.now();
			String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + File.separator;
			/**Verifico se la cartella esiste, se non esiste la creo**/
			File dir = new File(exportPath);		 
			if (!dir.isDirectory()) {
	        	dir.mkdir();
			}
			
			List<File> listaFile = new ArrayList<>();

			/**SCRIVO SINGOLI FILE - ZIP FILE**/
			listaFile.add(exportFabbricatiPerNominativo(lsParticelle, titolo, tipologiaEstrazione));
			listaFile.add(exportFabbricatiPerParticella(lsParticelle, titolo, tipologiaEstrazione));
			listaFile.add(exportTerreniPerNominativo(lsParticelle, titolo, tipologiaEstrazione));
			listaFile.add(exportTerreniPerParticella(lsParticelle, titolo, tipologiaEstrazione));

			return creaZip(listaFile);

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
		
	}

	public File exportVisuraCatastaleFabbricati(Long idImmobile) throws Exception {
		String idLog = "exportVisuraCatastaleFabbricati";
		try{
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			long startTime = 0L;
			long endTime = 0L;
			
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono idImmobile: {}" + idImmobile);

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			UnitaImmobiliareJoinRicerca result = unitaImmDao.findByIdImmobileVisura(idImmobile);
			List<SoggettoIntestatario> listaIntestatari = soggettoDao.ricercaListaIntestatariConDiritto(idImmobile);
			AggiornamentoCatasto aggiornamentoCatasto = aggiornamentoCatastoDao.getDataUltimoAggiornamento();
			List<Comuni> lsComuni = comuniDao.getComuni();
			List<Province> lsProvince = provinceDao.getProvince();

			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERO DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			List<SoggettoIntestatario> listaFiltrata = new ArrayList<>();
			for (SoggettoIntestatario soggettoIntestatario : listaIntestatari) {
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
			List<ListaIntestatariVisuraDTO> resultDTO = intConverter.toDtoVisura(listaFiltrata, getNomeCitta(result.getCodComune(), lsComuni), 1);	
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			VisuraFabbricatoInput input = new VisuraFabbricatoInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "VisuraCatastaleFabbricato.jasper";

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultDTO, false);
			input.setIntestatariDataSet(dataSource);
			input.setDataAtti(formatter.format(aggiornamentoCatasto.getDataFineValidita()));
			input.setCodComune(result.getCodComune());
			input.setComune(getNomeCitta(result.getCodComune(), lsComuni));
			input.setProvincia(getNomeProvinciaFromComune(result.getCodComune(), lsComuni, lsProvince));
			input.setFoglio(result.getFoglio());
			input.setNumero(result.getNumero());
			input.setSub(result.getSubalterno());
			input.setIndiceUiu(1);
			input.setSezione(result.getSezione());
			input.setZona(result.getZona());
			input.setCategoria(result.getCategoria());
			input.setClasse(result.getClasse());
			input.setConsistenza(result.getConsistenza());
			input.setSuperficie(result.getSuperficie());
			input.setRenditaEuro(result.getRenditaEuro());
			if (result.getDataEfficaciaGen() != null) {
				input.setDerivazione(result.getDescrizioneAttoGen() + " " + formatter.format(result.getDataEfficaciaGen()));
			} else {
				input.setDerivazione(result.getDescrizioneAttoGen());
			}
			input.setIndirizzo(result.getIndirizzo());
			input.setNote(result.getAnnotazione());
			
			return creaPdfEffettivo(input, templateReport, "VisuraCatastaleFabbricato");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private File creaPdfEffettivo(VisuraFabbricatoInput input, String templateReport, String nomeFile) {
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + currentDate.getDayOfMonth() + File.separator;
		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
		if (!dir.isDirectory()) {
        	dir.mkdir();
		}
        
		/**CREATE NAME FILE**/
		LocalTime localTime = LocalTime.now();
		String filename = env.getProperty("catasto.export.filename") + nomeFile + "_" + localTime.getHourOfDay() + localTime.getMinuteOfHour(); 
		File pdfFile = new File(exportPath + filename + ".pdf");
		try {
	        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
	        
	           JasperPrint jasperPrint = JasperFillManager.fillReport(
	        		   templateReport,
	                   input.getDataSources(), 
	                   mapArray);
			
	
	        JRPdfExporter xlsExporter = new JRPdfExporter();
	
	        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
	
	        xlsExporter.exportReport();
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	public File exportVisuraCatastaleTerreni(Long idImmobile) throws Exception {
		String idLog = "exportVisuraCatastaleTerreni";
		try{
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			long startTime = 0L;
			long endTime = 0L;
			
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono idImmobile: {}" + idImmobile);

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			Particella result = particellaDao.findByIdImmobileVisuraCatastale(idImmobile);
			List<SoggettoIntestatario> listaIntestatari = soggettoDao.ricercaListaIntestatariConDiritto(idImmobile);
			AggiornamentoCatasto aggiornamentoCatasto = aggiornamentoCatastoDao.getDataUltimoAggiornamento();
			List<Comuni> lsComuni = comuniDao.getComuni();
			List<Province> lsProvince = provinceDao.getProvince();
			List<CodiciQualita> lsQualita = qualitaDao.codiciQualita();

			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERO DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			List<SoggettoIntestatario> listaFiltrata = new ArrayList<>();
			for (SoggettoIntestatario soggettoIntestatario : listaIntestatari) {
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
			List<ListaIntestatariVisuraDTO> resultDTO = intConverter.toDtoVisura(listaFiltrata, getNomeCitta(result.getCodComune(), lsComuni), 1);	
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			VisuraTerrenoInput input = new VisuraTerrenoInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "VisuraCatastaleTerreno.jasper";

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultDTO, false);
			input.setIntestatariDataSet(dataSource);
			input.setDataAtti(formatter.format(aggiornamentoCatasto.getDataFineValidita()));
			input.setCodComune(result.getCodComune());
			input.setComune(getNomeCitta(result.getCodComune(), lsComuni));
			input.setProvincia(getNomeProvinciaFromComune(result.getCodComune(), lsComuni, lsProvince));
			input.setFoglio(result.getFoglio());
			input.setNumero(result.getNumero());
			input.setSub(result.getSubalterno() != null ? result.getSubalterno() : "");
			input.setIndicePt(1);
			input.setQualita(lsQualita.stream().filter(x -> x.getCodice().equals(Long.valueOf(result.getQualita()))).findFirst().get().getDescrizione());
			input.setClasse(result.getClasse());
			input.setHa(result.getEttari());
			input.setAre(result.getAre());
			input.setCentiare(result.getCentiare());
			input.setDominicale(result.getRedditoDominicaleEuro());
			input.setAgrario(result.getRedditoAgrarioEuro());
			input.setDerivazione(result.getDescrizioneAttoGen());
			//input.setDerivazione("");
			input.setNotifica("");
			input.setPartita(result.getPartita());
			input.setNote(result.getAnnotazione());
			
			if(result.getFlagPorzione().equals("1")) {
				List<PorzioneParticella> porzioni = porzioneParticellaDao.findByIdImmobile(idImmobile);
				StringBuilder porzioneCompleta = new StringBuilder();
				for (PorzioneParticella porzioneParticella : porzioni) {
					porzioneCompleta.append(porzioneParticella.getIdentificativoPorzione() + " ");
				}
				input.setPorz(porzioneCompleta.toString());
			}

			if(result.getFlagDeduzioni().equals("1")) {
				List<DeduzioneParticella> deduzioni = deduzioneParticellaDao.findByIdImmobile(idImmobile);
				StringBuilder deduzioneCompleta = new StringBuilder();
				for (DeduzioneParticella deduzioneParticella : deduzioni) {
					deduzioneCompleta.append(deduzioneParticella.getSimboloDeduzioni() + " ");
				}
				input.setDeduzione(deduzioneCompleta.toString());
			}
			
			return creaPdfEffettivo(input, templateReport, "VisuraCatastaleTerreno");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private File creaPdfEffettivo(VisuraTerrenoInput input, String templateReport, String nomeFile) {
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + File.separator;
		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
        if (!dir.isDirectory()) {
        	dir.mkdir();
		}
        	
        
		/**CREATE NAME FILE**/
		String filename = env.getProperty("catasto.export.filename") + nomeFile + "_" + localTime.getHourOfDay() + localTime.getMinuteOfHour(); 
		File pdfFile = new File(exportPath + filename + ".pdf");
		try {
	        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
	        
	           JasperPrint jasperPrint = JasperFillManager.fillReport(
	        		   templateReport,
	                   input.getDataSources(), 
	                   mapArray);
			
	
	        JRPdfExporter xlsExporter = new JRPdfExporter();
	
	        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
	
	        xlsExporter.exportReport();
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	@Override
	public File exportVisuraCatastaleStoricaTerreni(Long idImmobile) throws Exception {
		String idLog = "exportVisuraCatastaleStoricaTerreni";
		try{
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			long startTime = 0L;
			long endTime = 0L;
			
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono idImmobile: {}" + idImmobile);

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			List<ParticellaVisuraStorica> result = particellaDao.findByIdImmobileVisuraStorica(idImmobile);
			List<SoggettoIntestatario> listaIntestatari = soggettoDao.ricercaListaIntestatariConDirittoVisuraStorica(idImmobile);
			AggiornamentoCatasto ultimoAggiornamentoCatasto = aggiornamentoCatastoDao.getDataUltimoAggiornamento();
			AggiornamentoCatasto primoAggiornamentoCatasto = aggiornamentoCatastoDao.getDataPrimoAggiornamento();
			List<Comuni> lsComuni = comuniDao.getComuni();
			List<Province> lsProvince = provinceDao.getProvince();
			List<CodiciQualita> lsQualita = qualitaDao.codiciQualita();

			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERO DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			List<TerrenoStoricoDTO> immobileStoricoDTO = creaListaTerreniStorici(result, formatter.format(ultimoAggiornamentoCatasto.getDataFineValidita()), lsQualita);
			
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			VisuraTerrenoStoricaInput input = new VisuraTerrenoStoricaInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "VisuraCatastaleStoricaTerreno.jasper";

			JRBeanCollectionDataSource dataSourceTerreni = new JRBeanCollectionDataSource(immobileStoricoDTO, false);
			input.setDataAttiDal(formatter.format(primoAggiornamentoCatasto.getDataInizioValidita()));
			input.setDataAttiAl(formatter.format(ultimoAggiornamentoCatasto.getDataFineValidita()));
			ParticellaVisuraStorica primoCheCapita = null;
			List<IntestatariStoricoDTO> intestatariStoricoDTO = new ArrayList<>();
			if (result != null && !result.isEmpty()) {
				primoCheCapita = result.get(0);
				input.setCodComune(primoCheCapita.getCodComune());
				input.setComune(getNomeCitta(primoCheCapita.getCodComune(), lsComuni));
				input.setProvincia(getNomeProvinciaFromComune(primoCheCapita.getCodComune(), lsComuni, lsProvince));
				input.setFoglio(primoCheCapita.getFoglio());
				input.setNumero(primoCheCapita.getNumero());
				input.setSub(primoCheCapita.getSubalterno() != null ? primoCheCapita.getSubalterno() : "");
				intestatariStoricoDTO = creaListaIntestatariStorico(listaIntestatari, getNomeCitta(primoCheCapita.getCodComune(), lsComuni));
			}
			input.setTerreniDataSet(dataSourceTerreni);
			JRBeanCollectionDataSource dataSourceIntestatari = new JRBeanCollectionDataSource(intestatariStoricoDTO, false);
			input.setIntestatariDataSet(dataSourceIntestatari);
			
			return creaPdfEffettivo(input, templateReport, "VisuraCatastaleStoricaTerreno");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private List<TerrenoStoricoDTO> creaListaTerreniStorici(List<ParticellaVisuraStorica> result, String data, List<CodiciQualita> lsQualita) {
		List<TerrenoStoricoDTO> list = new ArrayList<>();
		int count = 1;
		for (ParticellaVisuraStorica item : result) {
			TerrenoStoricoDTO dto = new TerrenoStoricoDTO();
			dto.setIndicePt(count);
			dto.setFoglio(item.getFoglio());
			dto.setNumero(item.getNumero());
			dto.setSub(item.getSubalterno() != null ? item.getSubalterno() : "");
			dto.setIndicePt(count);
			dto.setQualita(lsQualita.stream().filter(x -> x.getCodice().equals(Long.valueOf(item.getQualita()))).findFirst().get().getDescrizione());
			dto.setClasse(item.getClasse());
			dto.setHa(item.getEttari());
			dto.setAre(item.getAre());
			dto.setCentiare(item.getCentiare());
			dto.setDominicale(item.getRedditoDominicaleEuro());
			dto.setAgrario(item.getRedditoAgrarioEuro());
			dto.setDerivazione(item.getDescrizioneAttoGen());
			dto.setNotifica("");
			dto.setPartita(item.getPartita());
			dto.setNote(item.getAnnotazione());

			if(item.getFlagPorzione() != null && item.getFlagPorzione().equals("1")) {
				List<PorzioneParticella> porzioni = porzioneParticellaDao.findByIdImmobile(item.getIdImmobile());
				StringBuilder porzioneCompleta = new StringBuilder();
				for (PorzioneParticella porzioneParticella : porzioni) {
					porzioneCompleta.append(porzioneParticella.getIdentificativoPorzione() + " ");
				}
				dto.setPorz(porzioneCompleta.toString());
			}

			if(item.getFlagDeduzioni() != null && item.getFlagDeduzioni().equals("1")) {
				List<DeduzioneParticella> deduzioni = deduzioneParticellaDao.findByIdImmobile(item.getIdImmobile());
				StringBuilder deduzioneCompleta = new StringBuilder();
				for (DeduzioneParticella deduzioneParticella : deduzioni) {
					deduzioneCompleta.append(deduzioneParticella.getSimboloDeduzioni() + " ");
				}
				dto.setDeduzione(deduzioneCompleta.toString());
			}

			if(item.getDataEfficaciaConcl() == null) {
				dto.setData(data);
			} else {
				dto.setData(item.getDataEfficaciaConcl());
			}

			list.add(dto);
			count++;
		}
		return list;
	}

	private List<IntestatariStoricoDTO> creaListaIntestatariStorico(List<SoggettoIntestatario> listaIntestatari, String comune) {
		List<IntestatariStoricoDTO> listIntestatari = new ArrayList<>();
		HashMap<String, List<DettaglioIntestatariStoricoDTO>> map = new HashMap<>();
		for (SoggettoIntestatario soggettoIntestatario : listaIntestatari) {
			if (map.containsKey(formatter.format(soggettoIntestatario.getDataValiditaGen()))) {
				List<DettaglioIntestatariStoricoDTO> listaDaChiave = map.get(formatter.format(soggettoIntestatario.getDataValiditaGen()));
				boolean isPresentCodFis = listaDaChiave.stream().anyMatch(x -> x.getCodiceFiscale().equalsIgnoreCase(soggettoIntestatario.getCodice_fiscale()));
				boolean isPresentDiritto = listaDaChiave.stream().anyMatch(x -> x.getDiritto().equalsIgnoreCase(soggettoIntestatario.getDiritto()));
				if (isPresentCodFis && isPresentDiritto) {
					break;
				} else {
					DettaglioIntestatariStoricoDTO dtoDettaglio = new DettaglioIntestatariStoricoDTO();
					dtoDettaglio.setIndiceIntestatario(1);
					if (soggettoIntestatario.getTipo_soggetto().equalsIgnoreCase("P")) {
						String anagrafica = soggettoIntestatario.getCognome() + " " + soggettoIntestatario.getNome() + " nato a " + comune;
						if (soggettoIntestatario.getData_nascita() != null) {
							anagrafica += " il " + soggettoIntestatario.getData_nascita();
						}
						dtoDettaglio.setDatiAnagrafici(anagrafica);
					} else {
						dtoDettaglio.setDatiAnagrafici(soggettoIntestatario.getDenominazione());
					}
					dtoDettaglio.setCodiceFiscale(soggettoIntestatario.getCodice_fiscale());
					dtoDettaglio.setDiritto(soggettoIntestatario.getDiritto());
					listaDaChiave.add(dtoDettaglio);
					map.replace(formatter.format(soggettoIntestatario.getDataValiditaGen()), listaDaChiave);
				}
			} else {
				List<DettaglioIntestatariStoricoDTO> nuovaLista = new ArrayList<>();
				DettaglioIntestatariStoricoDTO dtoDettaglio = new DettaglioIntestatariStoricoDTO();
				dtoDettaglio.setIndiceIntestatario(1);
				if (soggettoIntestatario.getTipo_soggetto().equalsIgnoreCase("P")) {
					String anagrafica = soggettoIntestatario.getCognome() + " " + soggettoIntestatario.getNome() + " nato a " + comune;
					if (soggettoIntestatario.getData_nascita() != null) {
						anagrafica += " il " + soggettoIntestatario.getData_nascita();
					}
					dtoDettaglio.setDatiAnagrafici(anagrafica);
				} else {
					dtoDettaglio.setDatiAnagrafici(soggettoIntestatario.getDenominazione());
				}
				dtoDettaglio.setCodiceFiscale(soggettoIntestatario.getCodice_fiscale());
				dtoDettaglio.setDiritto(soggettoIntestatario.getDiritto());
				nuovaLista.add(dtoDettaglio);
				map.put(formatter.format(soggettoIntestatario.getDataValiditaGen()), nuovaLista);
			}
		}
		LinkedHashMap<String, List<DettaglioIntestatariStoricoDTO>> sortedMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		for (Map.Entry<String, List<DettaglioIntestatariStoricoDTO>> item : sortedMap.entrySet()) {
			IntestatariStoricoDTO intStorico = new IntestatariStoricoDTO();
			intStorico.setDataDal(item.getKey());
			intStorico.setListDettaglio(item.getValue());
			listIntestatari.add(intStorico);
		}
		return listIntestatari;
	}

	private File creaPdfEffettivo(VisuraTerrenoStoricaInput input, String templateReport, String nomeFile) {
		
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + File.separator;
		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
		if (!dir.isDirectory()) {
        	dir.mkdir();
		}
        
		/**CREATE NAME FILE**/
		String filename = env.getProperty("catasto.export.filename") + nomeFile + "_" + localTime.getHourOfDay() + localTime.getMinuteOfHour(); 
		File pdfFile = new File(exportPath + filename + ".pdf");
		try {
	        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
	        
	           JasperPrint jasperPrint = JasperFillManager.fillReport(
	        		   templateReport,
	                   input.getDataSources(), 
	                   mapArray);
			
	
	        JRPdfExporter xlsExporter = new JRPdfExporter();
	
	        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
	
	        xlsExporter.exportReport();
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	@Override
	public File exportVisuraCatastaleStoricaFabbricati(Long idImmobile) throws Exception {
		String idLog = "exportVisuraCatastaleStoricaFabbricati";
		try{
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			long startTime = 0L;
			long endTime = 0L;
			
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono idImmobile: {}" + idImmobile);

			startTime = System.nanoTime();
			/**RECUPERO DATI**/
			List<UnitaImmobiliareJoinRicerca> result = unitaImmDao.findByIdImmobileVisuraStorica(idImmobile);
			//List<ParticellaVisuraStorica> result = particellaDao.findByIdImmobileVisuraStorica(idImmobile);
			List<SoggettoIntestatario> listaIntestatari = soggettoDao.ricercaListaIntestatariConDirittoVisuraStorica(idImmobile);
			AggiornamentoCatasto ultimoAggiornamentoCatasto = aggiornamentoCatastoDao.getDataUltimoAggiornamento();
			AggiornamentoCatasto primoAggiornamentoCatasto = aggiornamentoCatastoDao.getDataPrimoAggiornamento();
			List<Comuni> lsComuni = comuniDao.getComuni();
			List<Province> lsProvince = provinceDao.getProvince();
			List<CategorieCatastali> lsCategorie = categorieDao.categorieCatastali();

			endTime = System.nanoTime();
			System.out.println("################################### TIME RECUPERO DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			startTime = System.nanoTime();
			List<FabbricatoStoricoDTO> immobileStoricoDTO = creaListaFabbricatiStorici(result, formatter.format(ultimoAggiornamentoCatasto.getDataFineValidita()), lsCategorie);
			
			endTime = System.nanoTime();
			System.out.println("################################### TIME LAVORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			VisuraFabbricatoStoricaInput input = new VisuraFabbricatoStoricaInput();

			String pathFile = env.getProperty("catasto.export.base.path") + env.getProperty("catasto.export.template.path");
			String templateReport = pathFile + File.separator + "VisuraCatastaleStoricaFabbricato.jasper";

			JRBeanCollectionDataSource dataSourceFabbricati = new JRBeanCollectionDataSource(immobileStoricoDTO, false);
			input.setDataAttiDal(formatter.format(primoAggiornamentoCatasto.getDataInizioValidita()));
			input.setDataAttiAl(formatter.format(ultimoAggiornamentoCatasto.getDataFineValidita()));
			UnitaImmobiliareJoinRicerca primoCheCapita = null;
			List<IntestatariStoricoDTO> intestatariStoricoDTO = new ArrayList<>();
			if (result != null && !result.isEmpty()) {
				primoCheCapita = result.get(0);
				input.setCodComune(primoCheCapita.getCodComune());
				input.setComune(getNomeCitta(primoCheCapita.getCodComune(), lsComuni));
				input.setProvincia(getNomeProvinciaFromComune(primoCheCapita.getCodComune(), lsComuni, lsProvince));
				input.setFoglio(primoCheCapita.getFoglio());
				input.setNumero(primoCheCapita.getNumero());
				input.setSub(primoCheCapita.getSubalterno() != null ? primoCheCapita.getSubalterno() : "");
				intestatariStoricoDTO = creaListaIntestatariStorico(listaIntestatari, getNomeCitta(primoCheCapita.getCodComune(), lsComuni));
			}
			input.setImmobiliDataSet(dataSourceFabbricati);
			JRBeanCollectionDataSource dataSourceIntestatari = new JRBeanCollectionDataSource(intestatariStoricoDTO, false);
			input.setIntestatariDataSet(dataSourceIntestatari);
			
			return creaPdfEffettivo(input, templateReport, "VisuraCatastaleStoricaFabbricato");

		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	private List<FabbricatoStoricoDTO> creaListaFabbricatiStorici(List<UnitaImmobiliareJoinRicerca> result, String data, List<CategorieCatastali> lsQualita) {
		List<FabbricatoStoricoDTO> list = new ArrayList<>();
		int count = 1;
		for (UnitaImmobiliareJoinRicerca item : result) {
			FabbricatoStoricoDTO dto = new FabbricatoStoricoDTO();
			dto.setFoglio(item.getFoglio());
			dto.setNumero(item.getNumero());
			dto.setSub(item.getSubalterno());
			dto.setIndiceUiu(count);
			dto.setSezione(item.getSezione());
			dto.setZona(item.getZona());
			dto.setCategoria(item.getCategoria());
			dto.setClasse(item.getClasse());
			dto.setConsistenza(item.getConsistenza());
			dto.setSuperficie(item.getSuperficie());
			dto.setRenditaEuro(item.getRenditaEuro());
			if (item.getDataEfficaciaGen() != null) {
				dto.setDerivazione(item.getDescrizioneAttoGen() + " " + formatter.format(item.getDataEfficaciaGen()));
			} else {
				dto.setDerivazione(item.getDescrizioneAttoGen());
			}
			dto.setIndirizzo(item.getIndirizzo());
			dto.setNote(item.getAnnotazione());

			if(item.getDataEfficaciaGen() == null) {
				dto.setData(data);
			} else {
				dto.setData(formatter.format(item.getDataEfficaciaGen()));
			}

			list.add(dto);
			count++;
		}
		return list;
	}

	private File creaPdfEffettivo(VisuraFabbricatoStoricaInput input, String templateReport, String nomeFile) {
		
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("catasto.export.base.path");
		LocalDate currentDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonthValue() + File.separator;
		/**Verifico se la cartella esiste, se non esiste la creo**/
		File dir = new File(exportPath);		 
		if (!dir.isDirectory()) {
        	dir.mkdir();
		}
        
		/**CREATE NAME FILE**/
		String filename = env.getProperty("catasto.export.filename") + nomeFile + "_" + localTime.getHourOfDay() + localTime.getMinuteOfHour();; 
		File pdfFile = new File(exportPath + filename + ".pdf");
		try {
	        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
	        
	           JasperPrint jasperPrint = JasperFillManager.fillReport(
	        		   templateReport,
	                   input.getDataSources(), 
	                   mapArray);
			
	
	        JRPdfExporter xlsExporter = new JRPdfExporter();
	
	        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
	
	        xlsExporter.exportReport();
	        
		} catch (JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

}
