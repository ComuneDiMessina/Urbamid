package it.eng.tz.urbamid.prg.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.prg.filter.VarianteFilter;
import it.eng.tz.urbamid.prg.persistence.dao.AmbitoVarianteDao;
import it.eng.tz.urbamid.prg.persistence.dao.CartografiaVarianteDao;
import it.eng.tz.urbamid.prg.persistence.dao.CduDao;
import it.eng.tz.urbamid.prg.persistence.dao.DocumentoIndiceCodiceDao;
import it.eng.tz.urbamid.prg.persistence.dao.DocumentoIndiceDao;
import it.eng.tz.urbamid.prg.persistence.dao.DocumentoVarianteDao;
import it.eng.tz.urbamid.prg.persistence.dao.StoricoCduDao;
import it.eng.tz.urbamid.prg.persistence.dao.StoricoVarianteDao;
import it.eng.tz.urbamid.prg.persistence.dao.VarianteDao;
import it.eng.tz.urbamid.prg.persistence.model.AmbitoVariante;
import it.eng.tz.urbamid.prg.persistence.model.AmbitoVarianteParticella;
import it.eng.tz.urbamid.prg.persistence.model.AmbitoVarianteParticellaZto;
import it.eng.tz.urbamid.prg.persistence.model.CartografiaVariante;
import it.eng.tz.urbamid.prg.persistence.model.Cdu;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndice;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndiceCodici;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoVariante;
import it.eng.tz.urbamid.prg.persistence.model.FabbricatiGeom;
import it.eng.tz.urbamid.prg.persistence.model.ParticellaGeom;
import it.eng.tz.urbamid.prg.persistence.model.Variante;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryAmbitoVariante;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryCartografiaVariante;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryCdu;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryDocumentoIndice;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryDocumentoIndiceCodici;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryDocumentoVariante;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryFabbricatiGeom;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryParticellaGeom;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryStoricoCdu;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryStoricoVariante;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryVariante;
import it.eng.tz.urbamid.prg.util.RepositoryUtils;
import it.eng.tz.urbamid.prg.web.dto.AmbitoRicercaDTO;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteListDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.CertificatoUrbanisticoInput;
import it.eng.tz.urbamid.prg.web.dto.CodiceDTO;
import it.eng.tz.urbamid.prg.web.dto.CodiceZtoDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceCodiciDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.ElencoDocumentiCduDTO;
import it.eng.tz.urbamid.prg.web.dto.ElencoIndiciCduDTO;
import it.eng.tz.urbamid.prg.web.dto.GeometriaCduDTO;
import it.eng.tz.urbamid.prg.web.dto.ImportIndiceByteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoDocumentoByteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoIndiceDTO;
import it.eng.tz.urbamid.prg.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.RicadenzeC04DTO;
import it.eng.tz.urbamid.prg.web.dto.RicadenzeDTO;
import it.eng.tz.urbamid.prg.web.dto.VarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.converter.CartografiaVarianteConverter;
import it.eng.tz.urbamid.prg.web.dto.converter.DocumentoIndiceConverter;
import it.eng.tz.urbamid.prg.web.dto.converter.DocumentoVarianteConverter;
import it.eng.tz.urbamid.prg.web.dto.converter.DucomentoIndiceCodiciConverter;
import it.eng.tz.urbamid.prg.web.dto.converter.VarianteConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class VarianteServiceImpl implements VarianteService {

	private static final Logger logger = LoggerFactory.getLogger(VarianteServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	/**VARIABILI STATICHE UTILIZZATE PER LE CHIAMATE A GEOSERVER**/
	@Value("${urbamid.rest.geoserver.endpoint.url}")
	protected String GEOSERVER_ENDPOINT;
	public final String METHOD_GET 				= "GET";
	public final String METHOD_POST				= "POST";
	public final String FEATURES				= "features";
	public final String PROPERTIES				= "properties";
	public final String GEOMETRYNAME			= "geometry_name";
	public final String DEFAULT_GEOMETRYNAME	= "the_geom";
	public final String PROPERTY				= "LEGENDA";

	@Value("${prg.export.cdu.areestabili}")
	protected String AREE_STABILI;
	
	/**TIPOLOGICHE" STATO VARIANTE**/
	private static final String ADOTTATA = "A";
	private static final String STORICIZZATA = "S";

	//"Tipologiche" azioni storico
	private static final String CREAZIONE = "Creazione";
	private static final String MODIFICA = "Modifica";
	private static final String STORICIZZAZIONE = "Storicizzazione";
	private static final String DOCUMENTO_INSERIMENTO = "Inserimento Doc";
	private static final String DOCUMENTO_MODIFICA = "Modifica Doc";
	private static final String DOCUMENTO_CANCELLAZIONE = "Cancellazione Doc";
	private static final String CARTOGRAFIA_INSERIMENTO = "Inserimento Cart.";
	private static final String AMBITO_INSERIMENTO = "Inserimento Amb.";
	private static final String INDICE_INSERIMENTO = "Inserimento Indice";
	private static final String INDICE_CANCELLAZIONE = "Cancellazione Indice";
	private static final String INDICE_IMPORTAZIONE = "Importazione Indice";
	private static final String INDICE_ESPORTAZIONE = "Esportazione Indice";
	private static final String CODICE_INSERIMENTO = "Associazione Codice";

	//"Tipologiche" descrizione azioni storico
	private static final String CREAZIONE_DESC = "Creazione della variante";
	private static final String MODIFICA_DESC = "Eseguita modifica della variante";
	private static final String STORICIZZAZIONE_DESC = "Eseguita storicizzazione della variante";
	private static final String DOCUMENTO_INSERIMENTO_DESC = "Eseguita associazione di un documento alla variante";
	private static final String DOCUMENTO_MODIFICA_DESC = "Eseguita modifica di un documento associato alla variante";
	private static final String DOCUMENTO_CANCELLAZIONE_DESC = "Eseguita cancellazione di un documento associato alla variante";
	private static final String CARTOGRAFIA_INSERIMENTO_DESC = "Eseguita associazione di cartografia alla variante";
	private static final String AMBITO_INSERIMENTO_DESC = "Eseguita associazione di un ambito alla variante";
	private static final String INDICE_INSERIMENTO_DESC = "Eseguita associazione di un indice ad un documento";
	private static final String INDICE_CANCELLAZIONE_DESC = "Eseguita cancellazione di un indice ad un documento";
	private static final String INDICE_IMPORTAZIONE_DESC = "Eseguita importazione degli indici";
	private static final String INDICE_ESPORTAZIONE_DESC = "Eseguita esportazione degli indici";
	private static final String CODICE_INSERIMENTO_DESC = "Eseguita associazione di un codice ad indice";

	
	private static final String LAYER_SORGENTE_GEOSERVER = "geoserver";
	private static final String LAYER_SORGENTE_DB = "db";
	
	@Autowired
	private Environment env;

	@Autowired
	private JpaRepositoryVariante varianteRepository;

	@Autowired
	private JpaRepositoryStoricoVariante storicoVarianteRepository;

	@Autowired
	private JpaRepositoryDocumentoVariante documentoVarianteRepository;

	@Autowired
	private JpaRepositoryAmbitoVariante ambitoVarianteRepository;

	@Autowired
	private JpaRepositoryCartografiaVariante cartografiaVarianteRepository;

	@Autowired
	private JpaRepositoryParticellaGeom particellaRepository;

	@Autowired
	private JpaRepositoryFabbricatiGeom fabbricatoRepository;

	@Autowired
	private JpaRepositoryDocumentoIndice indiceRepository;

	@Autowired
	private JpaRepositoryDocumentoIndiceCodici codiciRepository;

	@Autowired
	private JpaRepositoryStoricoCdu storicoCduRepository;

	@Autowired
	private JpaRepositoryCdu cduRepository;

	@Autowired
	private VarianteDao	varianteDao;

	@Autowired
	private DocumentoVarianteDao documentoVarianteDao;

	@Autowired
	private StoricoVarianteDao storicoVarianteDao;

	@Autowired
	private AmbitoVarianteDao ambitoVarianteDao;

	@Autowired
	private CartografiaVarianteDao cartografiaDao;

	@Autowired
	private DocumentoIndiceCodiceDao codiceDao;
	
	@Autowired
	private DocumentoIndiceDao indiceDao;

	@Autowired
	private StoricoCduDao storicoCduDao;

	@Autowired
	private CduDao cduDao;

	@Autowired
	private VarianteConverter varianteConverter;

	@Autowired
	private DocumentoVarianteConverter documentoVarianteConverter;

	@Autowired
	private CartografiaVarianteConverter cartografiaVarianteConverter;

	@Autowired
	private DocumentoIndiceConverter indiceConverter;

	@Autowired
	private DucomentoIndiceCodiciConverter codiciConverter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<VarianteDTO> getVarianti(VarianteFilter filter) throws Exception {
		String idLog = "getVarianti";
		try{
			logger.info(START, idLog);
			Page<Variante> pagedResult = varianteRepository.findAll(
					//SPECIFICATION
					RepositoryUtils.buildVariantePredicate( filter ), 
					//PAGE & SORT
					PageRequest.of( 
							filter.getPageIndex()/filter.getPageSize(), 
							filter.getPageSize()) 
					);
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getTotalElements());

			return varianteConverter.toDTO( new PagedResult<>(pagedResult) );
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<VarianteDTO> findAllOrderByDate() throws Exception {
		String idLog = "getVarianti";
		try{
			logger.info(START, idLog);
			List<Variante> result = varianteRepository.findAllOrderByDate();

			return varianteConverter.toDTO( result );
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public Variante creaOrSalvaVariante(VarianteDTO variante) throws Exception {
		Variante result = null;
		try {
			if (variante.getIdVariante() != null) {
				Optional<Variante> model = varianteRepository.findById(variante.getIdVariante());
					Variante daModificare = varianteConverter.toModel(variante);
					Variante daDb = model.get();
					daModificare.setIdVariante(daDb.getIdVariante());
					if (daDb.getDataDelAppr() == null && daModificare.getDataDelAppr() != null) {
						daModificare.setStato(STORICIZZATA);
						popolaStorico(daModificare.getIdVariante(), STORICIZZAZIONE, STORICIZZAZIONE_DESC, null);
					} else {
						popolaStorico(daModificare.getIdVariante(), MODIFICA, MODIFICA_DESC, null);
					}
					varianteDao.updateVariante(daModificare, variante.getUsername());
					Variante ritornoDaDb = varianteRepository.findById(variante.getIdVariante()).get();
					//result = varianteConverter.toDTO(ritornoDaDb);
			} else {
				 Variante daSalvare = varianteConverter.toModel(variante);
				 boolean storicizzata = false;
				 if (daSalvare.getDataDelAdoz() != null && !daSalvare.getDataDelAdoz().equals("") && daSalvare.getDataDelAppr() != null && !daSalvare.getDataDelAppr().equals("")) {
					 daSalvare.setStato(STORICIZZATA);
					 varianteDao.insertVariante(daSalvare, variante.getUsername());
					 storicizzata = true;
				 } else {
					 daSalvare.setStato(ADOTTATA);
					 varianteDao.insertVariante(daSalvare, variante.getUsername());
				 }
				 /*Variante varianteInserita = varianteRepository.cercaVarianteDaNome(daSalvare.getNome());
				 popolaStorico(varianteInserita.getIdVariante(), CREAZIONE, CREAZIONE_DESC, null);
				 if (storicizzata) {
					 popolaStorico(varianteInserita.getIdVariante(), STORICIZZAZIONE, STORICIZZAZIONE_DESC, null);
				 }*/
				 daSalvare.setStato("NUOVO");
				 result = daSalvare;
			 }
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public VarianteDTO cancellaVariante(Long idVariante) throws Exception {
		try {
			storicoVarianteRepository.cancellaStoricoFromIdVariante(idVariante);
			documentoVarianteRepository.cancellaDocumentiFromIdVariante(idVariante);
			cartografiaVarianteRepository.deleteAllCartografieByIdVariante(idVariante);
			ambitoVarianteRepository.deleteAllAmbitoByIdVariante(idVariante);
			varianteRepository.deleteById(idVariante);
		} catch (Exception e) {
			logger.error("Errore nella cancellazione della variante {}", e.getMessage(), e);
			throw (e);
		}
		return null;
	}

	public void popolaStorico(Long idVariante, String azione, String descrizione, String note) throws Exception {
		try {
			storicoVarianteDao.insertStoricoVariante(idVariante, azione, descrizione, note);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio dello storico della variante {}", e.getMessage(), e);
			throw (e);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DocumentoVarianteDTO> cercaDocumentiByIdVariante(Long idVariante) throws Exception {
		String idLog = "cercaDocumentiByIdVariante";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idVariante: {}", idVariante);

			List<DocumentoVariante> resultModel = documentoVarianteRepository.cercaDocumentiByIdVariante(idVariante);
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return documentoVarianteConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public InserimentoDocumentoByteDTO creaOrSalvaDocumento(InserimentoDocumentoByteDTO documento) throws Exception {
		InserimentoDocumentoByteDTO result = null;
		try {
			
			Optional<Variante> variante = varianteRepository.findById(documento.getIdVariante());

			String baseImportPath = env.getProperty("prg.import.base.path");
			String savePath = baseImportPath + File.separator + documento.getEnte() + File.separator + variante.get().getNome().replaceAll("\\s+", "_").replaceAll("/", "-") + File.separator;
			File dir = new File(savePath);
			dir.mkdirs();
			
			Path path = Paths.get(savePath + documento.getNomeFile());
			Files.write(path, documento.getFile());


			if (documento.getIdDocumento() != null) {
				Optional<DocumentoVariante> model = documentoVarianteRepository.findById(documento.getIdDocumento());
				DocumentoVariante daModificare = documentoVarianteConverter.toModelFromByteModel(documento, path.toString());
				daModificare.setIdDocumento(model.get().getIdDocumento());
				daModificare.setVariante(variante.get());
				daModificare.setStatoDocumento("M");
				if (documento.getEstensione().equalsIgnoreCase("application/pdf")) {
					daModificare.setEstensione(documento.getEstensione());					
				} else {
					daModificare.setEstensione("application/msword");
				}
				//DocumentoVariante daDb = model.get();
				//daModificare.setIdVariante(daDb.getIdVariante());
				documentoVarianteDao.updateDocumento(daModificare);
				popolaStorico(variante.get().getIdVariante(), DOCUMENTO_MODIFICA, DOCUMENTO_MODIFICA_DESC, null);
				//DocumentoVariante ritornoDaDb = documentoVarianteRepository.findById(documento.getIdDocumento()).get();
				//result = documentoVarianteConverter.toDto(ritornoDaDb);
			} else {
				DocumentoVariante daSalvare = documentoVarianteConverter.toModelFromByteModel(documento, path.toString());
				daSalvare.setVariante(variante.get());
				daSalvare.setStatoDocumento("N");
				if (documento.getEstensione().equalsIgnoreCase("application/pdf")) {
					daSalvare.setEstensione(documento.getEstensione());					
				} else {
					daSalvare.setEstensione("application/msword");
				}
				documentoVarianteDao.insertDocumento(daSalvare);
				popolaStorico(variante.get().getIdVariante(), DOCUMENTO_INSERIMENTO, DOCUMENTO_INSERIMENTO_DESC, null);
				/*Variante varianteInserita = varianteRepository.cercaVarianteDaNome(daSalvare.getNome());
				popolaStorico(varianteInserita.getIdVariante(), CREAZIONE, CREAZIONE_DESC, null);
				if (storicizzata) {
					popolaStorico(varianteInserita.getIdVariante(), STORICIZZAZIONE, STORICIZZAZIONE_DESC, null);
				}*/
			 }

			//DocumentoVariante ritornoDaDb = varianteRepository.findById(variante.getIdVariante()).get();
			//result = varianteConverter.toDTO(ritornoDaDb);

		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public DocumentoVarianteDTO cancellaDocumento(Long idDocumento) throws Exception {
		try {
			Optional<DocumentoVariante> documento = documentoVarianteRepository.findById(idDocumento);
			if (documento.isPresent()) {
				DocumentoVariante documentoVar = documento.get();
				File file = new File(documentoVar.getPathDocumento());
				org.apache.commons.io.FileUtils.deleteQuietly(file);
			}
			Optional<Variante> variante = varianteRepository.findById(documento.get().getVariante().getIdVariante());
			popolaStorico(variante.get().getIdVariante(), DOCUMENTO_CANCELLAZIONE, DOCUMENTO_CANCELLAZIONE_DESC, null);
			documentoVarianteRepository.deleteById(idDocumento);
		} catch (Exception e) {
			logger.error("Errore nella cancellazione della variante {}", e.getMessage(), e);
			throw (e);
		}
		return null;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public File downloadDocumento(Long idDocumento) throws Exception {
		File file = null;
		try {
			Optional<DocumentoVariante> documento = documentoVarianteRepository.findById(idDocumento);
			if (documento.isPresent()) {
				file = new File(documento.get().getPathDocumento());
			}
		} catch (Exception e) {
			logger.error("Errore nel download del documento{}", e.getMessage(), e);
			throw (e);
		}
		return file;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public AmbitoVarianteDTO salvaAmbitoVarianteTracciato(AmbitoVarianteDTO data) throws Exception {
		AmbitoVarianteDTO result = null;
		try {
			ambitoVarianteRepository.deleteAllAmbitoByIdVariante(data.getIdVariante());
			ambitoVarianteDao.insertAmbito(data.getIdVariante(), data.getWktGeom());
			popolaStorico(data.getIdVariante(), AMBITO_INSERIMENTO, AMBITO_INSERIMENTO_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public AmbitoVarianteListDTO salvaAmbitoVarianteSelezione(AmbitoVarianteListDTO data) throws Exception {
		AmbitoVarianteListDTO result = null;
		try {
			ambitoVarianteRepository.deleteAllAmbitoByIdVariante(data.getIdVariante());
			for (String item : data.getWktGeom()) {
				ambitoVarianteDao.insertAmbito(data.getIdVariante(), item);
			}
			popolaStorico(data.getIdVariante(), AMBITO_INSERIMENTO, AMBITO_INSERIMENTO_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public AmbitoVarianteListDTO salvaAmbitoVarianteSelezioneLayer(AmbitoVarianteListDTO data) throws Exception {
		AmbitoVarianteListDTO result = null;
		try {
			ambitoVarianteRepository.deleteAllAmbitoByIdVariante(data.getIdVariante());
			for (String item : data.getWktGeom()) {
				ambitoVarianteDao.insertAmbito(data.getIdVariante(), item);
			}
			popolaStorico(data.getIdVariante(), AMBITO_INSERIMENTO, AMBITO_INSERIMENTO_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<CartografiaVarianteDTO> cercaCartografieByIdVariante(Long idVariante) throws Exception {
		String idLog = "cercaCartografieByIdVariante";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idVariante: {}", idVariante);

			List<CartografiaVariante> resultModel = cartografiaVarianteRepository.cercaCartografieByIdVariante(idVariante);
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return cartografiaVarianteConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public CartografiaDTO salvaCartografia(CartografiaDTO cartografia) throws Exception {
		CartografiaDTO result = null;
		try {
			//cartografiaVarianteRepository.deleteAllCartografieByIdVariante(cartografia.getIdVariante());
			for (String item : cartografia.getLayers()) {
				String[] splitLayer = item.split("\\+");
				Optional<CartografiaVariante> giaSalvata = cartografiaVarianteRepository.cercaCartografiaByIdVarianteAndNomeLayer(cartografia.getIdVariante(), splitLayer[0].trim());
				if (!giaSalvata.isPresent()) {
					cartografiaDao.insertCartografia(cartografia.getIdVariante(), splitLayer[0].trim(), splitLayer[1].trim(), splitLayer[2].trim(), splitLayer[3].trim(), splitLayer[4].equalsIgnoreCase("null") ? null : splitLayer[4]);					
				}
			}
			popolaStorico(cartografia.getIdVariante(), CARTOGRAFIA_INSERIMENTO, CARTOGRAFIA_INSERIMENTO_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della cartografia della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public List<AmbitoRicercaDTO> salvaAmbitoVarianteRicerca(List<AmbitoRicercaDTO> list) throws Exception {
		List<AmbitoRicercaDTO> result = null;
		try {
			if (!list.isEmpty()) {
				ambitoVarianteRepository.deleteAllAmbitoByIdVariante(list.get(0).getIdVariante());
				for (AmbitoRicercaDTO item : list) {
					String mappale = item.getMappale().toString().replaceFirst("^0+(?!$)", "");
					String foglio = normalizzaNumeroFoglio(item.getFoglio().toString());
					ParticellaGeom part = particellaRepository.findByFoglioAndMappale(foglio, mappale);
					if (part == null) {
						mappale = item.getMappale().toString().replaceFirst("^0+(?!$)", "") + "+";
						foglio = normalizzaNumeroFoglio(item.getFoglio());
						FabbricatiGeom fabb = fabbricatoRepository.findByFoglioAndMappale(foglio, mappale);
						if (fabb != null) {
							ambitoVarianteDao.insertAmbito(item.getIdVariante(), fabb.getGeometry());
						}
					} else {
						ambitoVarianteDao.insertAmbito(item.getIdVariante(), part.getGeom());						
					}
				}				
			}
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	protected String normalizzaNumeroMappale(String mappale) {
		if (mappale != null) {
			if(mappale.chars().allMatch(Character::isDigit)) {
				if (mappale.length() == 1) {
					return "0000" + mappale;
				} else if (mappale.length() == 2) {
					return "000" + mappale;
				} else if (mappale.length() == 3) {
					return "00" + mappale;
				} else if (mappale.length() == 4) {
					return "0" + mappale;
				} else {
					return mappale;
				}
			} else {
				return mappale;
			}			
		} else {
			return mappale;
		}
	}

	protected String normalizzaNumeroFoglio(String foglio) {
		if(foglio != null) {
			if(foglio.chars().allMatch(Character::isDigit)) {
				if (foglio.length() == 1) {
					return "000" + foglio;
				} else if (foglio.length() == 2) {
					return "00" + foglio;
				} else if (foglio.length() == 3) {
					return "0" + foglio;
				} else {
					return foglio;
				}
			} else {
				return foglio;
			}			
		} else {
			return foglio;
		}
	}

	@Override
	public void aggiornaStoricoNuovo(VarianteDTO variante) throws Exception {
		boolean storicizzata = false;
		if (variante.getDataDelAdoz() != null && !variante.getDataDelAdoz().equals("") && variante.getDataDelAppr() != null && !variante.getDataDelAppr().equals("")) {
			storicizzata = true;
		}
		Variante varianteInserita = varianteRepository.cercaVarianteDaNome(variante.getNome().toUpperCase());
		popolaStorico(varianteInserita.getIdVariante(), CREAZIONE, CREAZIONE_DESC, null);
		if (storicizzata) {
			popolaStorico(varianteInserita.getIdVariante(), STORICIZZAZIONE, STORICIZZAZIONE_DESC, null);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DocumentoIndiceDTO> cercaIndiciByIdDocumento(Long idDocumento) throws Exception {
		String idLog = "cercaDocumentiByIdVariante";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idDocumento: {}", idDocumento);

			List<DocumentoIndice> resultModel = indiceRepository.cercaIndiciByIdDocumento(idDocumento);
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return indiceConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DocumentoIndiceCodiciDTO> cercaCodiciByIdIndice(Long idIndice) throws Exception {
		String idLog = "cercaCodiciByIdIndice";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idIndice: {}", idIndice);

			List<DocumentoIndiceCodici> resultModel = codiciRepository.cercaCodiciByIdIndice(idIndice);
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return codiciConverter.toDto(resultModel);
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public InserimentoIndiceDTO salvaIndice(InserimentoIndiceDTO indice) throws Exception {
		InserimentoIndiceDTO result = null;
		try {
			indiceDao.insertIndice(indice);
			Optional<DocumentoVariante> documento = documentoVarianteRepository.findById(indice.getIdDocumento());
			popolaStorico(documento.get().getVariante().getIdVariante(), INDICE_INSERIMENTO, INDICE_INSERIMENTO_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio dell'indice {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public DocumentoIndiceDTO cancellaIndice(Long idIndice) throws Exception {
		Optional<DocumentoIndice> indice = indiceRepository.findById(idIndice);
		popolaStorico(indice.get().getDocumento().getVariante().getIdVariante(), INDICE_CANCELLAZIONE, INDICE_CANCELLAZIONE_DESC, null);
		indiceRepository.deleteById(idIndice);
		return null;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public ImportIndiceByteDTO importaIndice(ImportIndiceByteDTO indice) throws Exception {
		ImportIndiceByteDTO result = null;
		try {
			String baseImportPath = env.getProperty("prg.import.base.path");
			String savePath = baseImportPath + File.separator + File.separator;
			File dir = new File(savePath);
			dir.mkdir();

			Path path = Paths.get(savePath + indice.getNomeFile());
			Files.write(path, indice.getFile());

			indiceRepository.deleteAllIndiciByIdDocumento(indice.getIdDocumento());
			
			FileInputStream fis=new FileInputStream(path.toString());       
			Scanner sc=new Scanner(fis);
			while(sc.hasNextLine())  
			{
				InserimentoIndiceDTO dto = new InserimentoIndiceDTO();
				String[] split = sc.nextLine().split(";");
				dto.setIdDocumento(indice.getIdDocumento());
				dto.setArticolo(Integer.valueOf(split[0]));
				dto.setElencoPagine(split[1]);
				indiceDao.insertIndice(dto);
			}  
			sc.close();
			
			Files.delete(path);
			
			Optional<DocumentoVariante> documento = documentoVarianteRepository.findById(indice.getIdDocumento());
			popolaStorico(documento.get().getVariante().getIdVariante(), INDICE_IMPORTAZIONE, INDICE_IMPORTAZIONE_DESC, null);

//			Optional<Variante> variante = varianteRepository.findById(documento.getIdVariante());
//
//			if (documento.getIdDocumento() != null) {
//				Optional<DocumentoVariante> model = documentoVarianteRepository.findById(documento.getIdDocumento());
//				DocumentoVariante daModificare = documentoVarianteConverter.toModelFromByteModel(documento, path.toString());
//				daModificare.setIdDocumento(model.get().getIdDocumento());
//				daModificare.setVariante(variante.get());
//				//DocumentoVariante daDb = model.get();
//				//daModificare.setIdVariante(daDb.getIdVariante());
//				documentoVarianteDao.updateDocumento(daModificare);
//				popolaStorico(variante.get().getIdVariante(), DOCUMENTO_MODIFICA, DOCUMENTO_MODIFICA_DESC, null);
//				//DocumentoVariante ritornoDaDb = documentoVarianteRepository.findById(documento.getIdDocumento()).get();
//				//result = documentoVarianteConverter.toDto(ritornoDaDb);
//			} else {
//				DocumentoVariante daSalvare = documentoVarianteConverter.toModelFromByteModel(documento, path.toString());
//				daSalvare.setVariante(variante.get());
//				documentoVarianteDao.insertDocumento(daSalvare);
//				popolaStorico(variante.get().getIdVariante(), DOCUMENTO_INSERIMENTO, DOCUMENTO_INSERIMENTO_DESC, null);
//				/*Variante varianteInserita = varianteRepository.cercaVarianteDaNome(daSalvare.getNome());
//				popolaStorico(varianteInserita.getIdVariante(), CREAZIONE, CREAZIONE_DESC, null);
//				if (storicizzata) {
//					popolaStorico(varianteInserita.getIdVariante(), STORICIZZAZIONE, STORICIZZAZIONE_DESC, null);
//				}*/
//			 }

			//DocumentoVariante ritornoDaDb = varianteRepository.findById(variante.getIdVariante()).get();
			//result = varianteConverter.toDTO(ritornoDaDb);

		} catch (Exception e) {
			logger.error("Errore nel salvataggio della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public File esportaIndice(Long idDocumento) throws Exception {
		File file = null;
		try {
			String baseExportPath = env.getProperty("prg.export.base.path");
			String savePath = baseExportPath + File.separator + File.separator;

			Path path = Paths.get(savePath + "export.txt");
			file = new File(path.toString());
			
			List<DocumentoIndice> list = indiceRepository.cercaIndiciByIdDocumento(idDocumento);
			FileWriter myWriter = new FileWriter(path.toString());
			for (DocumentoIndice documentoIndice : list) {
				myWriter.write(documentoIndice.getArticolo() + ";" + documentoIndice.getElencoPagine() + "\n");	
			}
		    myWriter.close();
		    
		    Optional<DocumentoVariante> documento = documentoVarianteRepository.findById(idDocumento);
			popolaStorico(documento.get().getVariante().getIdVariante(), INDICE_ESPORTAZIONE, INDICE_ESPORTAZIONE_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel download del documento{}", e.getMessage(), e);
			throw (e);
		}
		return file;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<String> cercaAmbitoByIdVariante(Long idVariante) throws Exception {
		String idLog = "cercaCartografieByIdVariante";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: idVariante: {}", idVariante);
			
			List<String> result = new ArrayList<>();
			List<AmbitoVariante> resultModel = ambitoVarianteRepository.findByIdVariante(idVariante);
			if (!resultModel.isEmpty()) {
				for (AmbitoVariante ambitoVariante : resultModel) {
					result.add(ambitoVariante.getGeom());
				}
			}
			
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public CodiceDTO salvaCodici(CodiceDTO codice) throws Exception {
		CodiceDTO result = null;
		try {
			codiciRepository.deleteAllCodiciByIdIndice(codice.getIdIndice());
			for (String item : codice.getLayers()) {
				String[] splitLayer = item.split("\\+");
				codiceDao.insertCodice(codice.getIdIndice(), splitLayer[0].trim(), splitLayer[1].trim());
			}
			Optional<DocumentoIndice> indice = indiceRepository.findById(codice.getIdIndice());
			popolaStorico(indice.get().getDocumento().getVariante().getIdVariante(), CODICE_INSERIMENTO, CODICE_INSERIMENTO_DESC, null);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio dei codici {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public List<CodiceZtoDTO> cercaCodiceZto() throws Exception {
		List<CodiceZtoDTO> result = new ArrayList<>();
		
		try {
			logger.debug("cercaCodiceZto");
			
			result = codiceDao.cercaCodiceZTO();
		
			return result;
		} catch (Exception e) {
			logger.error("Errore nel salvataggio dei codici {}", e.getMessage(), e);
			throw (e);
		}
		
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public File downloadCdu(InterrogazionePianoDTO interrogazionePianoDto) throws Exception {
		
		try {
			
			long startTime = System.nanoTime();
			long endTime = 0L;
			
			/***************************		VARIABILI C01	***************************************************/
			List<RicadenzeDTO> listaRicadenze  		= new ArrayList<RicadenzeDTO>();

			/***************************		VARIABILI C02	***************************************************/
			List<RicadenzeC04DTO> listaVincoliPiano = new ArrayList<RicadenzeC04DTO>();

			/***************************		VARIABILI C03	***************************************************/
			String listaStabili = null;
			String listaNonStabili = null;
			
			/***************************		VARIABILI C004	***************************************************/
			HashMap<String,HashMap<String,List<RicadenzeDTO>>> hmC03 	= new HashMap<String,HashMap<String,List<RicadenzeDTO>>>();
			String pianoPaesaggistico = new String();
			
			/***************************		VARIABILI C006	***************************************************/
			List<RicadenzeC04DTO> listaIntersezioniAggiuntive = new ArrayList<RicadenzeC04DTO>();
			
			/***************************		VARIABILI C007	***************************************************/
			HashMap<String,HashMap<String,String>> hmFMLayerC004 	= new HashMap<String,HashMap<String,String>>();
			String fmNoLayerC004					= new String();
			String fmLayerC004						= new String();

			/***************************	VARIABILI DOCUMENTI/INDICE	  *****************************************/
			List<ElencoDocumentiCduDTO> listaDocumenti = new ArrayList<>();
			
			Long idVariante 					= (interrogazionePianoDto.getVariante()!=null) ? interrogazionePianoDto.getVariante().getIdVariante() : null;
			Variante variante 					= varianteRepository.findById(idVariante).get();
			String nomeVariante					= (interrogazionePianoDto.getVariante().getNome()!=null) ? interrogazionePianoDto.getVariante().getNome() : null;
			String dataVariante					= (interrogazionePianoDto.getVariante().getDataDelAppr()!=null) ? interrogazionePianoDto.getVariante().getDataDelAppr() : 
													(interrogazionePianoDto.getVariante().getDataDelAdoz()!=null  ? interrogazionePianoDto.getVariante().getDataDelAdoz() : null);
			String numeroVariante				= (interrogazionePianoDto.getVariante().getNumDelAppr()!=null) ? interrogazionePianoDto.getVariante().getNumDelAppr() : 
													(interrogazionePianoDto.getVariante().getNumDelAdoz()!=null) ? interrogazionePianoDto.getVariante().getNumDelAdoz() : null;
			HashMap<String,List<GeometriaCduDTO>> hmFoglio = new HashMap<String,List<GeometriaCduDTO>>();
			
			if (interrogazionePianoDto.getEntity()!=null) {
				
				/**1. Raggruppo le interrogazioni piano per foglio**/
				for (int i = 0; i<interrogazionePianoDto.getEntity().size(); i++){
					
					if( hmFoglio.get(interrogazionePianoDto.getEntity().get(i).getFoglio())!=null ){
						
						List<GeometriaCduDTO> list = hmFoglio.get(interrogazionePianoDto.getEntity().get(i).getFoglio());
						list.add( interrogazionePianoDto.getEntity().get(i) );
						hmFoglio.put(interrogazionePianoDto.getEntity().get(i).getFoglio(), list);
					} else {
						
						List<GeometriaCduDTO> list = new ArrayList<GeometriaCduDTO>();
						list.add( interrogazionePianoDto.getEntity().get(i) );
						hmFoglio.put(interrogazionePianoDto.getEntity().get(i).getFoglio(), list);
					}
				}
					
				for (CartografiaVariante cartografia : variante.getCartografiaVariante()) {
					
					String layerName = cartografia.getNomeLayer();
					String layerDesc = cartografia.getDescrizioneLayer();
					String layerGruppo = cartografia.getGruppoLayer();
					String layerSorgente = cartografia.getSorgente();
					String nomeColonna = cartografia.getNomeColonna();
					if (layerGruppo.contains("CDU 01")) {
						
						/* Elaborazione tabella CDU01 - ZTO */
						listaRicadenze.addAll( estraiRicadenzaZto(hmFoglio, idVariante, nomeVariante, layerName) );
					} else if (layerGruppo.contains("CDU 02")) {
						/* Elaborazione tabella CDU02 - VINCOLI DI PIANO */
						/*List<RicadenzeDTO> list = estraiRicadenzaLayerCD02(hmFoglio, idVariante, nomeVariante, layerName, layerDesc, layerSorgente);
						List<RicadenzeDTO> listaRicadenza = new ArrayList<>();
						if (list!=null && !list.isEmpty()) {
							for (RicadenzeDTO item : list) {
								if (item.getRicadenza().equalsIgnoreCase("1")) {
									listaRicadenza.add(item);
								}
							}
							
							if (list.size() == listaRicadenza.size()) {
								listaVincoliPiano.add(new RicadenzeC04DTO("Tutte all'INTERNO di " + layerDesc + formattaValoreColonna(listaRicadenza.get(0).getProprieta())));
							} else if (!listaRicadenza.isEmpty()) {
								for (RicadenzeDTO item : listaRicadenza) {
									listaVincoliPiano.add(new RicadenzeC04DTO("La particella n.<style isBold='true'>" + item.getParticelle() + " </style>del foglio n." + item.getFoglio() + " ricade all'interno di " + layerDesc + formattaValoreColonna(listaRicadenza.get(0).getProprieta())));
								}
								listaVincoliPiano.add(new RicadenzeC04DTO("Le restanti particelle ricadono all'esterno di " + layerDesc + formattaValoreColonna(listaRicadenza.get(0).getProprieta())));
							} else {
								listaVincoliPiano.add(new RicadenzeC04DTO("Tutte all'ESTERNO di " + layerDesc));
							}
						} else {
							listaVincoliPiano.add(new RicadenzeC04DTO("Tutte all'ESTERNO di " + layerDesc));
						}*/
						System.out.println("CDU 02 " + layerName + " " + layerGruppo);
					} else if (layerGruppo.contains("CDU 03")) {
						
						/* Elaborazione tabella CDU03 - AREE STABILI/NON STABILI */
						List<RicadenzeDTO> list = estraiRicadenzaLayerCD03(hmFoglio, idVariante, nomeVariante, layerName, layerDesc, layerSorgente);
						List<RicadenzeDTO> listaStabiliAppo = new ArrayList<>();
						List<RicadenzeDTO> listaNonStabiliAppo = new ArrayList<>();
						
						HashMap<String,List<RicadenzeDTO>> mapStabili = new HashMap<String,List<RicadenzeDTO>>();
						HashMap<String,List<RicadenzeDTO>> mapNonStabili = new HashMap<String,List<RicadenzeDTO>>();

						String[] areeStabili = AREE_STABILI.split("\\|");
						
						for (RicadenzeDTO item : list) {
							boolean isAreaStabile = Arrays.stream(areeStabili).anyMatch(item.getProprieta()::contains);
							if (isAreaStabile) {
								listaStabiliAppo.add(item);
							} else {
								listaNonStabiliAppo.add(item);
							}
						}
						
						for (RicadenzeDTO appo01 : listaStabiliAppo) {
							mapStabili.put(appo01.getFoglio(), listaStabiliAppo.stream().filter(x -> x.getFoglio().equals(appo01.getFoglio())).collect(Collectors.toList()));
						}

						for (RicadenzeDTO appo02 : listaNonStabiliAppo) {
							mapNonStabili.put(appo02.getFoglio(), listaNonStabiliAppo.stream().filter(x -> x.getFoglio().equals(appo02.getFoglio())).collect(Collectors.toList()));
						}

						listaStabili = costruisciStringaCDU03(mapStabili);
						listaNonStabili = costruisciStringaCDU03(mapNonStabili);
						System.out.println("CDU 03 " + layerName + " " + layerGruppo);
					} else if (layerGruppo.contains("CDU 04")) {
						
						/* Elaborazione tabella CDU04 - PIANI PAESAGGISTICI */
						List<RicadenzeDTO> list = estraiRicadenzaLayerCD04(hmFoglio, idVariante, nomeVariante, layerName, layerDesc, layerSorgente);
						if( list!=null && list.size()>0 ) {
							
							for (RicadenzeDTO item : list) {
								if (hmC03.get(item.getRicadenza()) != null) {
									hmC03.get(item.getRicadenza()).put(item.getFoglio(), list.stream().filter(x -> x.getFoglio().equals(item.getFoglio())).collect(Collectors.toList()));
								} else {
									HashMap<String, List<RicadenzeDTO>> secondMap = new HashMap<String, List<RicadenzeDTO>>();
									secondMap.put(item.getFoglio(), list.stream().filter(x -> x.getFoglio().equals(item.getFoglio())).collect(Collectors.toList()));
									hmC03.put(item.getRicadenza(), secondMap);
								}
							}

						}
						System.out.println("CDU 04" + layerName + " " + layerGruppo);
					} else if (layerGruppo.contains("CDU 05")) {
						/* Elaborazione tabella CDU05 - VINCOLO PAESAGGISTICO/AREE DI TUTELA */
						List<RicadenzeDTO> list = estraiRicadenzaLayerCD05(hmFoglio, idVariante, nomeVariante, layerName, layerDesc, layerSorgente);
					} else if (layerGruppo.contains("CDU 06")) {
						
						/* Elaborazione tabella CDU06 - SIC & ZPS */
						List<RicadenzeDTO> list = estraiRicadenzaLayerCD06(hmFoglio, idVariante, nomeVariante, layerName, layerDesc, layerSorgente, nomeColonna);
						List<RicadenzeDTO> listaConRicadenza = new ArrayList<>();
						if (list!=null && !list.isEmpty()) {
							for (RicadenzeDTO item : list) {
								if (item.getRicadenza().equalsIgnoreCase("1")) {
									listaConRicadenza.add(item);
								}
							}
							
							if (list.size() == listaConRicadenza.size()) {
								listaIntersezioniAggiuntive.add(new RicadenzeC04DTO("Tutte all'INTERNO di " + layerDesc + formattaValoreColonna(listaConRicadenza.get(0).getProprieta())));
							} else if (!listaConRicadenza.isEmpty()) {
								for (RicadenzeDTO item : listaConRicadenza) {
									listaIntersezioniAggiuntive.add(new RicadenzeC04DTO("La particella n.<style isBold='true'>" + item.getParticelle() + " </style>del foglio n." + item.getFoglio() + " ricade all'interno di " + layerDesc + formattaValoreColonna(listaConRicadenza.get(0).getProprieta())));
								}
								listaIntersezioniAggiuntive.add(new RicadenzeC04DTO("Le restanti particelle ricadono all'esterno di " + layerDesc + formattaValoreColonna(listaConRicadenza.get(0).getProprieta())));
							} else {
								listaIntersezioniAggiuntive.add(new RicadenzeC04DTO("Tutte all'ESTERNO di " + layerDesc));
							}
						} else {
							listaIntersezioniAggiuntive.add(new RicadenzeC04DTO("Tutte all'ESTERNO di " + layerDesc));
						}
						System.out.println("CDU 06 " + layerName + " " + layerGruppo);
					} else if (layerGruppo.contains("CDU 07")) {
						
						/* Elaborazione tabella CDU07 - Aree Percorse dal fuoco */
						List<RicadenzeDTO> listRicadenzaMappaleLayer = estraiRicadenzaLayerCD07(hmFoglio, idVariante, nomeVariante, layerName, layerDesc, layerSorgente);
						if( listRicadenzaMappaleLayer!=null && listRicadenzaMappaleLayer.size()>0 ) { 
							
							for (int i = 0;i<listRicadenzaMappaleLayer.size();i++){
								String foglio  = listRicadenzaMappaleLayer.get(i).getFoglio();
								String mappale = listRicadenzaMappaleLayer.get(i).getParticelle();
								
								if ( hmFMLayerC004.get(layerDesc)!=null ){
									
									if( hmFMLayerC004.get(layerDesc).get(foglio)!=null ){
										
										String mappali = hmFMLayerC004.get(layerDesc).get(foglio);
										hmFMLayerC004.get(layerDesc).put(foglio,mappali+","+mappale);
									} else {
										
										hmFMLayerC004.get(layerDesc).put(foglio, mappale);
									}
									
								} else {
									
									HashMap<String,String> hmFM = new HashMap<String,String>();
									hmFM.put(foglio, mappale);
									hmFMLayerC004.put(layerDesc, hmFM);
								}
							}
						} else {
							
							fmNoLayerC004 += fmNoLayerC004.isEmpty()? layerDesc: ", "+layerDesc;
						}
						System.out.println("CDU 05 " + layerName + " " + layerGruppo);
					}
				}
			}

			if (hmC03 != null) {
				for (Map.Entry mapLayer : hmC03.entrySet()) {
					String layerName = (String)mapLayer.getKey();
					HashMap<String,List<RicadenzeDTO>> hmFogli = (HashMap<String,List<RicadenzeDTO>>)mapLayer.getValue();
					String daStampare = new String();
					for (Map.Entry mapFogli : hmFogli.entrySet()) {
						String foglio = (String)mapFogli.getKey();
						List<RicadenzeDTO> ricadenze = (List<RicadenzeDTO>) mapFogli.getValue();
						if (ricadenze.size() == 1) {
							daStampare = !daStampare.isEmpty() ? daStampare + "la particella <style isBold='true'>" + ricadenze.get(0).getParticelle() + "</style>": "La particella <style isBold='true'>" + ricadenze.get(0).getParticelle()  + "</style>";
						} else {
							daStampare = !daStampare.isEmpty() ? daStampare + "le particelle: " : "Le Particelle: ";
							for (RicadenzeDTO item: ricadenze) {
								daStampare += "<style isBold='true'>" + item.getParticelle() + "</style>, ";
							}
							daStampare = daStampare.substring(0, daStampare.length()-2);
						}
						daStampare += !daStampare.isEmpty() ? " del foglio " + foglio + ", " : null;
					}
					daStampare = daStampare.substring(0, daStampare.length()-2);
					daStampare += " ALL'INTERNO di: " + layerName + ", ";
					pianoPaesaggistico += daStampare;
				}
			}
			
			if ( hmFMLayerC004!=null ) {
				
				for (Map.Entry meHmFMappali : hmFMLayerC004.entrySet()) {
					
					String tmpStr = new String();
					String layerC004						= (String)meHmFMappali.getKey();
					HashMap<String,String> hmFMapppali 	= (HashMap<String,String>)meHmFMappali.getValue();
					tmpStr = tmpStr.isEmpty() ? " nelle "+layerC004 + " RICADONO le suddette particelle: " : 
						tmpStr + ", " + " nelle "+layerC004 + " RICADONO le suddette particelle: ";
					for (Map.Entry meHmFMappale : hmFMapppali.entrySet()) {
						
						String foglioMappali 	= (String)meHmFMappale.getValue() +" del foglio "+ (String)meHmFMappale.getKey()+" ";
						tmpStr +=  foglioMappali+", ";
					}
					tmpStr = tmpStr.substring(0, tmpStr.length()-2);
					
					fmLayerC004 += fmLayerC004.isEmpty() ? tmpStr : "; " + tmpStr;
				}
			}

			//Visto che il valore è stato commentato dal front-end questo blocco non verrà mai eseguito
			if (interrogazionePianoDto.isIndiciVariante()) {
				for (DocumentoVariante documenti : variante.getDocumentoVariante()) {
					ElencoDocumentiCduDTO docDto = new ElencoDocumentiCduDTO();
					docDto.setNomeDocumento(documenti.getNomeDocumento());
					for (DocumentoIndice indice : documenti.getDocumentoIndice()) {
						for (DocumentoIndiceCodici codici : indice.getIndiceCodici()) {
							ElencoIndiciCduDTO indDto = new ElencoIndiciCduDTO();
							indDto.setCodice(codici.getCodice());
							indDto.setArticoloElenco("Articolo " + indice.getArticolo() + " - " + "Elenco pagine " + indice.getElencoPagine());
							docDto.getListIndici().add(indDto);
						}
					}
					listaDocumenti.add(docDto);
				}
			}
			
			/*List<String> lsFogli = new ArrayList<>();
			List<String> lsNumeri = new ArrayList<>();
			
			for (Entry<String, List<GeometriaCduDTO>> item : hmFoglio.entrySet()) {
				lsFogli.add(item.getKey());
				for (GeometriaCduDTO numeri : item.getValue()) {
					lsNumeri.add(numeri.getMappale());
				}
			}
			
			storicoCduRepository.deleteByProtocollo(interrogazionePianoDto.getProtocollo());
			
			List<StoricoCduProtocolloData> cduPassati = storicoCduRepository.cercaCduPassatiProtocolloData(lsFogli, lsNumeri);
			StringBuilder sbProtocollo = new StringBuilder();
			StringBuilder sbDate = new StringBuilder();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for (StoricoCduProtocolloData storicoCdu : cduPassati) {
				sbProtocollo.append(storicoCdu.getProtocollo() + ", ");
				sbDate.append(sdf.format(storicoCdu.getData_creazione()) + ", ");
			}
			String protocolliStorici = sbProtocollo.toString();
			String dateStorici = sbDate.toString();
			
			if (interrogazionePianoDto.getProtocollo() != null) {
				for (Entry<String, List<GeometriaCduDTO>> item : hmFoglio.entrySet()) {
					StoricoCdu storicoCdu = new StoricoCdu();
					storicoCdu.setFoglio(item.getKey());
					storicoCdu.setProtocollo(interrogazionePianoDto.getProtocollo());
					storicoCdu.setDataCreazione(new Date());
					for (GeometriaCduDTO numeri : item.getValue()) {
						storicoCdu.setNumero(numeri.getMappale());
						storicoCduDao.insertStoricoCdu(storicoCdu);
					}
				}
			}*/
			
			endTime = System.nanoTime();
			System.out.println("################################### TIME ELEBORA DATI: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
			
			String pathTemplateReport = env.getProperty("prg.export.base.path") + env.getProperty("prg.export.template.path");
			String templateReport = pathTemplateReport + File.separator + "cdu.jasper";
			
			CertificatoUrbanisticoInput input = new CertificatoUrbanisticoInput();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaRicadenze, false);
			input.setListaRicadenze(dataSource);
			
			input.setDipartimento(interrogazionePianoDto.getDipartimento()!=null && !interrogazionePianoDto.getDipartimento().isEmpty() ? interrogazionePianoDto.getDipartimento() : "Dipartimento Servizi Territoriali ed Urbanistici");
			input.setServizio(interrogazionePianoDto.getServizio()!=null && !interrogazionePianoDto.getServizio().isEmpty() ? interrogazionePianoDto.getServizio().toString() : "Servizio Pianificazione Urbanistica");
			input.setEmailReferente(interrogazionePianoDto.getEmailReferente()!=null && !interrogazionePianoDto.getEmailReferente().isEmpty() ? interrogazionePianoDto.getEmailReferente() : "umberto.costa@comune.messina.it");
			input.setResponsabileServizio(interrogazionePianoDto.getResponsabileServizio()!=null && !interrogazionePianoDto.getResponsabileServizio().isEmpty() ? interrogazionePianoDto.getResponsabileServizio() : "Geom. Umberto COSTA");
			input.setResponsabilePianoTerritoriale(interrogazionePianoDto.getResponsabilePianoTerritoriale()!=null && !interrogazionePianoDto.getResponsabilePianoTerritoriale().isEmpty() ? interrogazionePianoDto.getResponsabilePianoTerritoriale().toString() : "Ing. Giuseppe MESSINA");
			input.setDirigenteDipartimento(interrogazionePianoDto.getDirigenteDipartimento()!=null && !interrogazionePianoDto.getDirigenteDipartimento().isEmpty() ? interrogazionePianoDto.getDirigenteDipartimento() : "Arch. Antonella CUTRONEO");
			
			input.setPathImage( env.getProperty("prg.export.base.path") + env.getProperty("prg.export.template.path") );
			
			input.setProtocollo(interrogazionePianoDto.getProtocollo()!=null && !interrogazionePianoDto.getProtocollo().isEmpty() ? interrogazionePianoDto.getProtocollo() : "________");
			input.setDataIstanza(interrogazionePianoDto.getDataRichiesta()!=null && !interrogazionePianoDto.getDataRichiesta().isEmpty() ? interrogazionePianoDto.getDataRichiesta().toString() : "__/__/____");
			input.setDitta(interrogazionePianoDto.getNomeDitta()!=null && !interrogazionePianoDto.getNomeDitta().isEmpty() ? interrogazionePianoDto.getNomeDitta() : "___________________________________");
			
			input.setNomeVariante(nomeVariante);
			input.setDataVariante(dataVariante);
			input.setNumeroVariante(numeroVariante);
			
			input.setImportoVersamento1(interrogazionePianoDto.getImportoPrimoVersamento()!=null && !interrogazionePianoDto.getImportoPrimoVersamento().isEmpty() ? interrogazionePianoDto.getImportoPrimoVersamento() : "_______");
			input.setDataVersamento1(interrogazionePianoDto.getDataPrimoVersamento()!=null && !interrogazionePianoDto.getDataPrimoVersamento().isEmpty() ? interrogazionePianoDto.getDataPrimoVersamento() : "__/__/____");
			input.setCodiceVersamento1(interrogazionePianoDto.getCodicePrimoVersamento()!=null && !interrogazionePianoDto.getCodicePrimoVersamento().isEmpty() ? interrogazionePianoDto.getCodicePrimoVersamento() : "___________");
			
			input.setImportoVersamento2(interrogazionePianoDto.getImportoSecondoVersamento()!=null && !interrogazionePianoDto.getImportoSecondoVersamento().isEmpty() ? interrogazionePianoDto.getImportoSecondoVersamento() : "_______");
			input.setDataVersamento2(interrogazionePianoDto.getDataSecondoVersamento()!=null && !interrogazionePianoDto.getDataSecondoVersamento().isEmpty() ? interrogazionePianoDto.getDataSecondoVersamento() : "__/__/____");
			input.setCodiceVersamento2(interrogazionePianoDto.getCodiceSecondoVersamento()!=null && !interrogazionePianoDto.getCodiceSecondoVersamento().isEmpty() ? interrogazionePianoDto.getCodiceSecondoVersamento() : "___________");
			
			input.setCodiceModelloF23(interrogazionePianoDto.getCodiceModelloF23()!=null && !interrogazionePianoDto.getCodiceModelloF23().isEmpty() ? interrogazionePianoDto.getCodiceModelloF23() : "___________");
			input.setDataModelloF23(interrogazionePianoDto.getDataModelloF23()!=null && !interrogazionePianoDto.getDataModelloF23().isEmpty() ? interrogazionePianoDto.getDataModelloF23() : "__/__/____");
		
			input.setRiferimentoMappaCatastale(interrogazionePianoDto.getRiferimentoMappaCatastale()!=null && !interrogazionePianoDto.getRiferimentoMappaCatastale().isEmpty() ? interrogazionePianoDto.getRiferimentoMappaCatastale() : null);
			input.setRiferimentoVisuraCatastale(interrogazionePianoDto.getRiferimentoVisuraCatastale()!=null && !interrogazionePianoDto.getRiferimentoVisuraCatastale().isEmpty() ? interrogazionePianoDto.getRiferimentoVisuraCatastale() : null);
			input.setDataVisuraCatastale(interrogazionePianoDto.getDataVisuraCatastale()!=null && !interrogazionePianoDto.getDataVisuraCatastale().isEmpty() ? interrogazionePianoDto.getDataVisuraCatastale() : null);
			
			input.setListaStabili(listaStabili != null && !listaStabili.equals("") ? listaStabili.substring(0, listaStabili.length()-2) : null);
			input.setListaNonStabili(listaNonStabili != null && !listaNonStabili.equals("") ? listaNonStabili.substring(0, listaNonStabili.length()-2) : null);
			input.setPianoPaesaggistico(pianoPaesaggistico.isEmpty() ? "All'esterno " : pianoPaesaggistico.substring(0, pianoPaesaggistico.length()-2));
			
			input.setListaInApf(!fmLayerC004.equals("") ? fmLayerC004 : null);
			input.setListaInApfAltriAnni(!fmNoLayerC004.equals("") ? fmNoLayerC004 : null);
			
			input.setListaFuoriApfParticelle(null);
			input.setListaFuoriApfAnni(null);
			
			input.setListaIntersezioniAggiuntive(new JRBeanCollectionDataSource(listaIntersezioniAggiuntive, false));
			//Visto che il valore è stato commentato dal front-end questo blocco non verrà mai eseguito
			if (interrogazionePianoDto.isIndiciVariante()) {
				input.setIndiciVariante(true);
				input.setListaDocumenti(new JRBeanCollectionDataSource(listaDocumenti, false));
			}
			
			//input.setListaVincoliPiano(new JRBeanCollectionDataSource(listaVincoliPiano, false));
			//Valore forzato a NULL per non far comparire questa elaborazione @TODO
			input.setListaVincoliPiano(null);
			
			return creaDocEffettivo(input, templateReport, "Certificato_Urbanistico", interrogazionePianoDto.getProtocollo());
		} catch (Exception e) {
			
			logger.error("Errore nel download del documento{}", e.getMessage(), e);
			throw (e);
		}
	}
	
	

	private String formattaValoreColonna(String valoreColonna) {
		String result = null;
		if (valoreColonna != null && !valoreColonna.isEmpty()) {
			result = " [" + valoreColonna + "]";
		} else {
			result = "";
		}
		return result;
	}

	private String costruisciStringaCDU03(HashMap<String, List<RicadenzeDTO>> mapStabili) {
		String daStampare = new String();
		for (Map.Entry map : mapStabili.entrySet()) {
			StringBuilder sb = new StringBuilder();
			List<RicadenzeDTO> listAppo = (List<RicadenzeDTO>) map.getValue();
			if (listAppo.size() == 1) {
				daStampare += "La particella n." + "<style isBold='true'>" + listAppo.get(0).getParticelle() + "</style>";
			} else if (listAppo.size() > 1) {
				sb.append("Le particelle: ");
				for (RicadenzeDTO item :listAppo) {
					sb.append("<style isBold='true'>" + item.getParticelle() + "</style>" + ", ");
				}
				daStampare += sb.substring(0, sb.length()-2);
			}
			daStampare += " del foglio n." + map.getKey().toString() + ", ";
		}
		return daStampare;
	}

	public List<RicadenzeDTO> estraiRicadenzaDaAmbito(HashMap<String, List<GeometriaCduDTO>> hmFoglio, Long idVariante, String nomeVariante) throws Exception {
		
		List<RicadenzeDTO> listaRicadenze  	= new ArrayList<RicadenzeDTO>();
		
		/**2. I valori raggruppati per foglio sono utili per 
		 *     recuperare le particelle del foglio e riportarli nel certificato raggruppati con la stessa percentuale
		 **/
		Integer numero = new Integer(1);
		for (Map.Entry meHmF : hmFoglio.entrySet()) {
			
			HashMap<Double,String> hmPercentuale = new HashMap<Double,String>();
			String foglio 				= (String)meHmF.getKey();
			List<GeometriaCduDTO> list 	= (ArrayList<GeometriaCduDTO>)meHmF.getValue();
			for (int i = 0; i<list.size(); i++) {

				String mappale = list.get(i).getMappale();
				List<AmbitoVarianteParticella> variantePercentList = varianteDao.getPercentIntersect(foglio, mappale, idVariante);
				Double totPercent = new Double(0);
				Double percentualeDbl = new Double(0);
				if ( variantePercentList!=null && variantePercentList.size()>0 ) {
					
					for(int idxVarPer = 0; idxVarPer<variantePercentList.size();idxVarPer++){
						
						totPercent = totPercent+variantePercentList.get(idxVarPer).getArea_intersect();
					}
					percentualeDbl = (double)Math.round((totPercent/variantePercentList.size()))*100;
				}
				if ( hmPercentuale.get(percentualeDbl)!=null ) {
					
					hmPercentuale.put(percentualeDbl, hmPercentuale.get(percentualeDbl) +mappale+",");
				} else {
					
					hmPercentuale.put(percentualeDbl, mappale+",");
				}
			}
			
			for (Map.Entry meHmP : hmPercentuale.entrySet()) {
				
//				String particelle = ((String)meHmP.getValue()!=null) ?
//										((String)meHmP.getValue()).substring(0,((String)meHmP.getValue()).length()-1) : "Nessuna particella";
				String particelle = this.splitParticelleString( (String)meHmP.getValue() );
				RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, particelle, this.getLabelPercentuale( ((Double)meHmP.getKey()).intValue(), nomeVariante ));
				listaRicadenze.add(ricDto);
				numero++;
			};
		}
		return listaRicadenze;
	}

	/* PER ALESSANDRO */
	private List<RicadenzeDTO> estraiRicadenzaZto(HashMap<String, List<GeometriaCduDTO>> hmFoglio, Long idVariante, String nomeVariante, String layerName) throws Exception {
		
		List<RicadenzeDTO> listaRicadenze  	= new ArrayList<RicadenzeDTO>();
		/**2. I valori raggruppati per foglio sono utili per 
		 *     recuperare le particelle del foglio e riportarli nel certificato raggruppati con la stessa percentuale
		 **/
		Integer numero = new Integer(1);
		for (Map.Entry meHmF : hmFoglio.entrySet()) {
			
			HashMap<String,String> hmPercentualeZto = new HashMap<String,String>();
			//HashMap<Double,String> hmPercentuale = new HashMap<Double,String>();
			String foglio 				= (String)meHmF.getKey();
			List<GeometriaCduDTO> list 	= (ArrayList<GeometriaCduDTO>)meHmF.getValue();
			for (int i = 0; i<list.size(); i++) {
				
				List<AmbitoVarianteParticellaZto> variantePercentList = new ArrayList<>();
				String mappale = list.get(i).getMappale();
				variantePercentList = varianteDao.getPercentIntersectZto(foglio, mappale);					
				Double totPercent = new Double(0);
				Integer percentualeInt = new Integer(0);
				String zto = null;
				if ( variantePercentList!=null && variantePercentList.size()>1 ) {
					
					StringBuilder sb = new StringBuilder();
					HashMap<String,Double> rimozioneZtoUguali = new HashMap<>();
					for(int idxVarPer = 0; idxVarPer<variantePercentList.size();idxVarPer++){
						rimozioneZtoUguali.put(variantePercentList.get(idxVarPer).getZto(), variantePercentList.get(idxVarPer).getArea_intersect());
						//Da utilizzare in caso vogliono di nuovo la % di ricadenza
						/*totPercent = totPercent+variantePercentList.get(idxVarPer).getArea_intersect();
						percentualeInt = (int) Math.round((totPercent/variantePercentList.size())*100);
						if (variantePercentList.get(idxVarPer).getZto() != null) {
							
							sb.append(this.getLabelPercentuale( percentualeInt.intValue(), variantePercentList.get(idxVarPer).getZto()));
						}*/
						//sb.append("Parte in zona "+variantePercentList.get(idxVarPer).getZto() + ", ");
					}
					
					for (Entry<String, Double> ricadenzeDTO : rimozioneZtoUguali.entrySet()) {
						sb.append("Parte in zona "+ricadenzeDTO.getKey() + ", ");
					}
					
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, sb.toString().substring(0, sb.toString().length()-2) );
					listaRicadenze.add(ricDto);
					numero++;
				} else if (variantePercentList!=null && variantePercentList.size()==1) {
					
					StringBuilder sb = new StringBuilder();
					totPercent = totPercent+variantePercentList.get(0).getArea_intersect();
					if (variantePercentList.get(0).getZto() != null) {
						sb.append(this.getLabelPercentuale( 100, variantePercentList.get(0).getZto()));						}
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, sb.toString().substring(0, sb.toString().length()-2) );
					listaRicadenze.add(ricDto);
					numero++;
				}
			}
		}
		return listaRicadenze;
	}

	private List<RicadenzeDTO> estraiRicadenzaLayerCD02(HashMap<String, List<GeometriaCduDTO>> hmFoglio,
			Long idVariante, String nomeVariante, String layerName, String layerDesc, String layerSorgente) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	/* PER ALESSANDRO */
	private List<RicadenzeDTO> estraiRicadenzaLayerCD03(HashMap<String, List<GeometriaCduDTO>> hmFoglio, 
			Long idVariante, String nomeVariante, String layerName, String layerDesc, String layerSorgente) throws Exception {
		
		List<RicadenzeDTO> listaRicadenze  	= new ArrayList<RicadenzeDTO>();
		/**
		 * 1. I valori raggruppati per foglio sono utili per recuperare le particelle del foglio e riportarli nel certificato raggruppati con la stessa percentuale
		 **/
		Integer numero = new Integer(1);
		for (Map.Entry meHmF : hmFoglio.entrySet()) {
			
			HashMap<String,String> hmPercentualeZto = new HashMap<String,String>();
			String foglio 				= (String)meHmF.getKey();
			List<GeometriaCduDTO> list 	= (ArrayList<GeometriaCduDTO>)meHmF.getValue();
			for (int i = 0; i<list.size(); i++) {
				
				List<AmbitoVarianteParticella> variantePercentList = new ArrayList<AmbitoVarianteParticella>();
				String valoreAmbito		= null;
				String mappale 			= list.get(i).getMappale();
				String wktMappale 		= list.get(i).getWkt();
				
				if (layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_DB)) {
					
					variantePercentList = varianteDao.getPercentIntersectLayerDb(layerName, foglio, mappale);					
				} else if ( layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_GEOSERVER) ) {
					
					valoreAmbito = getIntersectLayerPropertiesGeoserver(layerName, foglio, mappale, wktMappale, PROPERTY);
				}
				
				if ( (variantePercentList!=null && variantePercentList.size()>0) || (valoreAmbito!=null && !valoreAmbito.isEmpty()) ) {
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, layerDesc, valoreAmbito );
					listaRicadenze.add(ricDto);
					numero++;
				}
			}
		}
		return listaRicadenze;
	}
	
	/* PER ALESSANDRO */
	private List<RicadenzeDTO> estraiRicadenzaLayerCD04(HashMap<String, List<GeometriaCduDTO>> hmFoglio, 
			Long idVariante, String nomeVariante, String layerName, String layerDesc, String layerSorgente) throws Exception {
		
		List<RicadenzeDTO> listaRicadenze  	= new ArrayList<RicadenzeDTO>();
		/**2. I valori raggruppati per foglio sono utili per 
		 *     recuperare le particelle del foglio e riportarli nel certificato raggruppati con la stessa percentuale
		 **/
		Integer numero = new Integer(1);
		for (Map.Entry meHmF : hmFoglio.entrySet()) {
			
			HashMap<String,String> hmPercentualeZto = new HashMap<String,String>();
			//HashMap<Double,String> hmPercentuale = new HashMap<Double,String>();
			String foglio 				= (String)meHmF.getKey();
			List<GeometriaCduDTO> list 	= (ArrayList<GeometriaCduDTO>)meHmF.getValue();
			for (int i = 0; i<list.size(); i++) {
				
				List<AmbitoVarianteParticella> variantePercentList = new ArrayList<AmbitoVarianteParticella>();
				Boolean contains = false;
				String mappale = list.get(i).getMappale();
				String wktMappale = list.get(i).getWkt();
				
				if (layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_DB)) {
					
					variantePercentList = varianteDao.getPercentIntersectLayerDb(layerName, foglio, mappale);					
				} else if ( layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_GEOSERVER) ) {
					
					contains = getIntersectLayerGeoserver(layerName, foglio, mappale, wktMappale);
				}
				
				if ( (variantePercentList!=null && variantePercentList.size()>0) || contains ) {
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, layerDesc );
					listaRicadenze.add(ricDto);
					numero++;
				} /*else {
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, "0" );
					listaRicadenze.add(ricDto);
					numero++;
				}*/
			}
		}
		return listaRicadenze;
	}

	private List<RicadenzeDTO> estraiRicadenzaLayerCD05(HashMap<String, List<GeometriaCduDTO>> hmFoglio,
			Long idVariante, String nomeVariante, String layerName, String layerDesc, String layerSorgente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* PER ALESSANDRO */
	private List<RicadenzeDTO> estraiRicadenzaLayerCD06(HashMap<String, List<GeometriaCduDTO>> hmFoglio, 
			Long idVariante, String nomeVariante, String layerName, String layerDesc, String layerSorgente, String nomeColonna) throws Exception {
		
		List<RicadenzeDTO> listaRicadenze  	= new ArrayList<RicadenzeDTO>();
		/**2. I valori raggruppati per foglio sono utili per 
		 *     recuperare le particelle del foglio e riportarli nel certificato raggruppati con la stessa percentuale
		 **/
		Integer numero = new Integer(1);
		for (Map.Entry meHmF : hmFoglio.entrySet()) {
			
			HashMap<String,String> hmPercentualeZto = new HashMap<String,String>();
			//HashMap<Double,String> hmPercentuale = new HashMap<Double,String>();
			String foglio 				= (String)meHmF.getKey();
			List<GeometriaCduDTO> list 	= (ArrayList<GeometriaCduDTO>)meHmF.getValue();
			for (int i = 0; i<list.size(); i++) {
				
				List<AmbitoVarianteParticella> variantePercentList = new ArrayList<AmbitoVarianteParticella>();
				Boolean contains = false;
				String mappale = list.get(i).getMappale();
				String wktMappale = list.get(i).getWkt();
				
				if (layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_DB)) {
					
					variantePercentList = varianteDao.getPercentIntersectLayerDb(layerName, foglio, mappale);					
				} else if ( layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_GEOSERVER) ) {
					
					contains = getIntersectLayerGeoserver(layerName, foglio, mappale, wktMappale);
				}
				
				if ( (variantePercentList!=null && variantePercentList.size()>0) || contains ) {
					
					String valoreColonna = null;
					valoreColonna = getValoreDaColonnaLayer(layerName, foglio, mappale, wktMappale, nomeColonna);
					
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, "1", valoreColonna );
					listaRicadenze.add(ricDto);
					numero++;
				} else {
					RicadenzeDTO ricDto = new RicadenzeDTO(numero, foglio, mappale, "0" );
					listaRicadenze.add(ricDto);
					numero++;
				}
			}
		}
		return listaRicadenze;
	}
	
	/* PER ALESSANDRO */
	private List<RicadenzeDTO> estraiRicadenzaLayerCD07(HashMap<String, List<GeometriaCduDTO>> hmFoglio, 
			Long idVariante, String nomeVariante, String layerName, String layerDesc, String layerSorgente) throws Exception {
		
		List<RicadenzeDTO> listaRicadenze  	= new ArrayList<RicadenzeDTO>();
		for (Map.Entry meHmF : hmFoglio.entrySet()) {
			
			String foglio 				= (String)meHmF.getKey();
			List<GeometriaCduDTO> list 	= (ArrayList<GeometriaCduDTO>)meHmF.getValue();
			for (int i = 0; i<list.size(); i++) {
				
				List<AmbitoVarianteParticella> variantePercentList = new ArrayList<AmbitoVarianteParticella>();
				Boolean contains = false;
				String mappale = list.get(i).getMappale();
				String wktMappale = list.get(i).getWkt();
				
				if (layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_DB)) {
					
					variantePercentList = varianteDao.getPercentIntersectLayerDb(layerName, foglio, mappale);					
				} else if ( layerSorgente.equalsIgnoreCase(LAYER_SORGENTE_GEOSERVER) ) {
					
					contains = getIntersectLayerGeoserver(layerName, foglio, mappale, wktMappale);
				}
				
				if ( (variantePercentList!=null && variantePercentList.size()>0) || contains ) {
					
					listaRicadenze.add( new RicadenzeDTO(null, foglio, mappale, null ) );
				}
			}
		}
		return listaRicadenze;
	}
	
	/**
	 * Recupero se il layer "nomeLayer" si interseca con il WKT del foglio-particella
	 * @param nomeLayer, nome del layer di geoserver da intersecare
	 * @param foglio, numero di foglio
	 * @param mappale, numero di particella
	 * @param wktMappale, wkt della geometria lagata al foglio-particella
	 * 
	 * @return contains, booleano che indica se il layer "nomeLayer" si interseca con il WKT della geometria foglio-particella 
	 */
	public boolean getIntersectLayerGeoserver(String nomeLayer, String foglio, String mappale, String wktMappale) {
		
		Boolean contains	= false;	
		String fieldGeom 	= DEFAULT_GEOMETRYNAME;
		
		if (wktMappale != null && !wktMappale.isEmpty()) {
			
			/***************************************/
			/**1. RECUPERO LA GEOMETRIA DEL CAMPO **/
			/***************************************/
			String hrefFieldGeomGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=GetFeature&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json&count=1";
			try {
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefFieldGeomGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray("features");
						if (arr!=null) {
							
							for (int i = 0; i < arr.length(); i++) {
								fieldGeom = arr.getJSONObject(i).getString(GEOMETRYNAME);
							}
						}
					}
				}
			} catch (IOException e) {
				
				logger.error(e.getMessage());
			} catch (JSONException e) {
				
				logger.error(e.getMessage());
			}
			
			/************************************************************************************************************/
			/**2. RECUPERO SE LA GEOMETRIA (wkt) DEL FOGLIO-MAPPALE NORMALIZZATA COME POINT E' CONTANUTA NEL LAYERNAME **/
			/************************************************************************************************************/
			String hrefIntersectGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=GetFeature&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json" +
					"&CQL_FILTER=Intersects("+fieldGeom+","+normalizeWKT(wktMappale)+")";		
			try {
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefIntersectGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray("features");
						if (arr!=null && arr.length()>0)
							contains = true;
					}
				}
				
			} catch (IOException e) {
				
				logger.error(e.getMessage());
			} catch (JSONException e) {
				
				logger.error(e.getMessage());
			}
		}
		return contains;
	}

public String getValoreDaColonnaLayer(String nomeLayer, String foglio, String mappale, String wktMappale, String nomeColonna) {
		
		String contains	= null;	
		String fieldGeom 	= DEFAULT_GEOMETRYNAME;
		
		if (wktMappale != null && !wktMappale.isEmpty()) {
			
			/***************************************/
			/**1. RECUPERO LA GEOMETRIA DEL CAMPO **/
			/***************************************/
			String hrefFieldGeomGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=GetFeature&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json&count=1";
			try {
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefFieldGeomGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray("features");
						if (arr!=null) {
							
							for (int i = 0; i < arr.length(); i++) {
								fieldGeom = arr.getJSONObject(i).getString(GEOMETRYNAME);
							}
						}
					}
				}
			} catch (IOException e) {
				
				logger.error(e.getMessage());
			} catch (JSONException e) {
				
				logger.error(e.getMessage());
			}
			
			/************************************************************************************************************/
			/**2. RECUPERO SE LA GEOMETRIA (wkt) DEL FOGLIO-MAPPALE NORMALIZZATA COME POINT E' CONTANUTA NEL LAYERNAME **/
			/************************************************************************************************************/
			String hrefIntersectGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=GetFeature&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json" +
					"&CQL_FILTER=Intersects("+fieldGeom+","+normalizeWKT(wktMappale)+")";		
			try {
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefIntersectGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray("features");
						if (arr!=null && arr.length()>0)
							for (int i = 0; i < arr.length(); i++) {
								JSONObject obj2 = (JSONObject)arr.get(0);
								JSONObject test = (JSONObject) obj2.get("properties");
								if (nomeColonna != null) {
									try {
										contains = test.get(nomeColonna).toString();																			
									} catch (Exception e) {
										logger.error("Chiave " + nomeColonna + " non trovata");
									}
								}
//								obj.getJSONArray("properties");
//								JSONArray arr2 = arr.getJSONObject(1).getJSONArray("properties");
//								for (int j = 0; j < arr2.length(); j++) {
//									contains = arr2.getJSONObject(j).getString(nomeColonna);
//								}
							}
					}
				}
				
			} catch (IOException e) {
				
				logger.error(e.getMessage());
			} catch (JSONException e) {
				
				logger.error(e.getMessage());
			}
		}
		return contains;
	}
	
	/**
	 * Recupero del valore AMBITO dal layer intersecato
	 * @param nomeLayer, nome del layer di geoserver da intersecare
	 * @param foglio, numero di foglio
	 * @param mappale, numero di particella
	 * @param wktMappale, wkt della geometria lagata al foglio-particella
	 * @param property, proprietà del layer intersecato da ricercare nella feature
	 * 
	 * @return valoreAmbito, è il valore della proprietà "property" del layer "nomeLayer" intersecato con il WKT della geometria foglio-particella 
	 */
	public String getIntersectLayerPropertiesGeoserver(String nomeLayer, String foglio, String mappale, String wktMappale, String property) {
		
		String valoreAmbito	= null;
		String fieldGeom 	= DEFAULT_GEOMETRYNAME;
		
		if (wktMappale != null && !wktMappale.isEmpty()) {
			
			/***************************************/
			/**1. RECUPERO LA GEOMETRIA DEL CAMPO **/
			/***************************************/
			String hrefFieldGeomGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=GetFeature&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json&count=1";
			try {
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefFieldGeomGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray(FEATURES);
						if (arr!=null) {
							
							for (int i = 0; i < arr.length(); i++) {
								fieldGeom = arr.getJSONObject(i).getString(GEOMETRYNAME);
							}
						}
					}
				}
			} catch (IOException e) {
				
				logger.error(e.getMessage());
			} catch (JSONException e) {
				
				logger.error(e.getMessage());
			}
			
			/************************************************************************************************************/
			/**2. RECUPERO SE LA GEOMETRIA (wkt) DEL FOGLIO-MAPPALE NORMALIZZATA COME POINT E' CONTANUTA NEL LAYERNAME **/
			/************************************************************************************************************/
			String hrefIntersectGeoserver = GEOSERVER_ENDPOINT + "SERVICE=WFS&version=2.0.0&request=GetFeature&typeNames=urbamid:"+nomeLayer+"&outputFormat=application/json" +
												"&CQL_FILTER=Intersects("+fieldGeom+","+normalizeWKT(wktMappale)+")";		
			try {
				
				String resultJson = restGeoserverCall(METHOD_POST, hrefIntersectGeoserver, "", "application/json", "application/json");
				if (resultJson!=null) {
					
					JSONObject obj = new JSONObject(resultJson);
					if (obj!=null) {
						
						JSONArray arr = obj.getJSONArray(FEATURES);
						if (arr!=null && arr.length()>0) {
							
							//Ho trovato features
							for (int i = 0; i < arr.length(); i++) {
								
								valoreAmbito = arr.getJSONObject(i).getJSONObject(PROPERTIES).getString(property);
							}
						}
					}
				}
			} catch (IOException e) {
				
				logger.error(e.getMessage());
			} catch (JSONException e) {
				
				logger.error(e.getMessage());
			}
		}
		return valoreAmbito;
	}

	/**
	 * Riporta una WKT da MULTIPOLYGON A POINT
	 * @param wkt, wkt del MULTIPOLYGON originale
	 * @return normalizeWkt, wkt normalizzato e riportato in POINT
	 */
	public String normalizeWKT(String wkt) {
		
		String normalizeWkt = "";
		if (!wkt.isEmpty()) {
			
			normalizeWkt = "POINT(%COORDS%)";
			List<String> items = Arrays.asList(wkt.split("\\s*,\\s*"));
			if (items!=null && items.size()>2) {
				
				String coords = items.get(1);
				List<String> itemCoords = Arrays.asList(coords.split("\\s* \\s*"));
				if (itemCoords!=null && itemCoords.size()>1)
					normalizeWkt = normalizeWkt.replace("%COORDS%", itemCoords.get(1)+"%20"+itemCoords.get(0) );
			}
		}
		return normalizeWkt;
	}
	
	/**
	 * Utilizzato per richiamare la API rest WFS di geoserver
	 * @param method
	 * @param urlEncoded
	 * @param postData
	 * @param contentType
	 * @param accept
	 * @return
	 * @throws IOException
	 */
	public String restGeoserverCall(String method, String urlEncoded, String postData, String contentType, String accept) throws IOException {
		
		boolean doOut = !METHOD_GET.equals(method) && postData != null;

		URL url = new URL(urlEncoded);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}
		connection.setRequestMethod(method.toString());

		connection.connect();
	    StringBuffer chaine = new StringBuffer("");
		InputStream inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = rd.readLine()) != null) {
            chaine.append(line);
        }
		return chaine.toString();
	}
	
	private String splitParticelleString(String particelle){
		
		String particelleFormatted = "";
		if (particelle!=null && !particelle.isEmpty()){
			
//			List<String> items = Arrays.asList(particelle.split("\\s*,\\s*"));
//			for (int i=0; i < items.size()-1; i++) {
//				particelleFormatted += items+",";
//				if (i % 4 == 0) 
//					particelleFormatted +="/n";
//			}
			particelleFormatted = particelle.substring(0, particelle.length()-1);
		} else {
			
			particelleFormatted = "Nessuna particella";
		}
		return particelleFormatted;
	}
	
	private String getLabelPercentuale(int percent, String nomeVariante) {
		String percentLabel = new String("");
		if (nomeVariante.equalsIgnoreCase("Nessuna Ricadenza")) {
			percentLabel = "Nessuna Ricadenza";
		} else {
			switch(percent) {
				case 100:
					percentLabel = "Totalmente in zona "+nomeVariante+ ", ";
					break;
				case 0:
					//percentLabel = "Non ricade in zona "+nomeVariante+ ", ";
					break;
				/*default:
					percentLabel = "Ricade in zona "+nomeVariante+" per il "+percent+"% ";*/
				default:
					percentLabel = "Parte in zona "+nomeVariante/*+" per il "+percent+"% " */+ ", ";
			}
		}
		return percentLabel;
	}
	private File creaPdfEffettivo(CertificatoUrbanisticoInput input, String templateReport, String nomeFile) {
		
		long startTime = System.nanoTime();
		long endTime = 0L;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");  
		Date date = new Date();  
		    
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("prg.export.base.path");
		LocalDate currentDate = LocalDate.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonth().getValue()+currentDate.getDayOfMonth() + File.separator;
		File dir = new File(exportPath);
		dir.mkdir();
		/**CREATE NAME FILE**/
		File pdfFile = new File(exportPath + formatter.format(date) + "_" + nomeFile+".pdf");
		
		try {
			
			if (pdfFile.createNewFile()) {
		        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
		        JasperPrint jasperPrint = JasperFillManager.fillReport(
		        		templateReport,
		        		input.getDataSources(), 
		        		mapArray
		        );
		        JRPdfExporter cdu = new JRPdfExporter();
		        cdu.setExporterInput(new SimpleExporterInput(jasperPrint));
		        cdu.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
		        cdu.exportReport();
			}
	        
	        endTime = System.nanoTime();
	        System.out.println("################################### TIME CREA PDF: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
	        
		} catch (JRException e) {
			
			logger.error(e.getMessage());
		} catch (IOException e) {
			
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

private File creaDocEffettivo(CertificatoUrbanisticoInput input, String templateReport, String nomeFile, String protocollo) {
		
		long startTime = System.nanoTime();
		long endTime = 0L;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();  
		    
		/**CREATE PATH**/
		String baseExportPath = env.getProperty("prg.export.base.path");
		LocalDate currentDate = LocalDate.now();
		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonth().getValue()+currentDate.getDayOfMonth() + File.separator;
		File dir = new File(exportPath);
		dir.mkdir();
		/**CREATE NAME FILE**/
		File pdfFile = new File(exportPath + formatter.format(date) + "_" + nomeFile+".doc");
		
		try {
			
			if (pdfFile.createNewFile()) {
		        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
		        JasperPrint jasperPrint = JasperFillManager.fillReport(
		        		templateReport,
		        		input.getDataSources(), 
		        		mapArray
		        );
		        JRDocxExporter cdu = new JRDocxExporter();
		        cdu.setExporterInput(new SimpleExporterInput(jasperPrint));
		        cdu.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
		        cdu.exportReport();
			}
			
			Cdu alreadyExist = cduRepository.findByProtocollo(protocollo);
			if (alreadyExist != null) {
				cduDao.updateCduAnagrafica(protocollo, new Date(), pdfFile.getAbsolutePath());
			} else {
				cduDao.insertCduAnagrafica(protocollo, new Date(), pdfFile.getAbsolutePath());
			}
	        
	        endTime = System.nanoTime();
	        System.out.println("################################### TIME CREA PDF: "+((double) (endTime - startTime) / 1_000_000_000) + "secondi");
	        
		} catch (JRException e) {
			
			logger.error(e.getMessage());
		} catch (IOException e) {
			
			logger.error(e.getMessage());
		} catch (Exception e) {
			
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {Exception.class}, readOnly = false)
	public CartografiaDTO cancellaCartografia(CartografiaDTO cartografia) throws Exception {
		CartografiaDTO result = null;
		try {
			for (String item : cartografia.getLayers()) {
				String[] splitLayer = item.split("\\+");
				cartografiaVarianteRepository.deleteCartografieByIdVarianteAndLayer(cartografia.getIdVariante(), splitLayer[0].trim());
			}
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della cartografia della variante {}", e.getMessage(), e);
			throw (e);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String cercaProtocollo(String protocollo) throws Exception {
		String idLog = "cercaProtocollo";
		try{
			logger.info(START, idLog);
			logger.debug("Parametri passati in input sono: protocollo: {}", protocollo);
			
			String resultModel = storicoCduRepository.findByProtocollo(protocollo);
			
			return resultModel;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}
}
