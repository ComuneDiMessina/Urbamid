package it.eng.tz.urbamid.web.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.MyUser;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.AggiuntaLayerDTO;
import it.eng.tz.urbamid.web.dto.AmbitoRicercaDTO;
import it.eng.tz.urbamid.web.dto.AmbitoVarianteDTO;
import it.eng.tz.urbamid.web.dto.AmbitoVarianteListDTO;
import it.eng.tz.urbamid.web.dto.CartografiaDTO;
import it.eng.tz.urbamid.web.dto.CartografiaVarianteDTO;
import it.eng.tz.urbamid.web.dto.CartografiaVarianteLayerDTO;
import it.eng.tz.urbamid.web.dto.CatalogoGruppoDTO;
import it.eng.tz.urbamid.web.dto.CduDTO;
import it.eng.tz.urbamid.web.dto.CodiceDTO;
import it.eng.tz.urbamid.web.dto.CodiceZtoDTO;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DocumentoIndiceCodiciDTO;
import it.eng.tz.urbamid.web.dto.DocumentoIndiceDTO;
import it.eng.tz.urbamid.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.web.dto.ImportIndiceByteDTO;
import it.eng.tz.urbamid.web.dto.ImportIndiceDTO;
import it.eng.tz.urbamid.web.dto.InserimentoDocumentoByteDTO;
import it.eng.tz.urbamid.web.dto.InserimentoDocumentoDTO;
import it.eng.tz.urbamid.web.dto.InserimentoIndiceDTO;
import it.eng.tz.urbamid.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.web.dto.TipoDocumentoVarianteDTO;
import it.eng.tz.urbamid.web.dto.VarianteDTO;
import it.eng.tz.urbamid.web.services.PianoRegolatoreService;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class PianoRegolatoreServiceImpl implements PianoRegolatoreService{
	
	private static final Logger logger = LoggerFactory.getLogger(PianoRegolatoreServiceImpl.class.getName());
	
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;

	@Override
	public DataTableDTO ricercaVarianti(String nome, String ente, String descrizione, String dataAdozioneDal, String dataAdozioneAl, String dataApprovazioneDal, String dataApporvazioneAl, Integer page, Integer size, Integer draw, String numColonna, String dir) throws Exception {
		List<VarianteDTO> lsVarianti = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_VARIANTE)+"?";
		if (nome != null && !nome.isEmpty() && !nome.equals("")) 
			url += "&nome=" + nome;
		if (ente != null && !ente.isEmpty() && !ente.equals("")) 
			url += "&ente=" + ente;
		if (descrizione != null && !descrizione.isEmpty() && !descrizione.equals("")) 
			url += "&descrizione=" + descrizione;
		if (dataAdozioneDal != null && !dataAdozioneDal.isEmpty() && !dataAdozioneDal.equals("")) 
			url += "&dataAdozioneDal=" + dataAdozioneDal;
		if (dataAdozioneAl != null && !dataAdozioneAl.isEmpty() && !dataAdozioneAl.equals("")) 
			url += "&dataAdozioneAl=" + dataAdozioneAl;
		if (dataApprovazioneDal != null && !dataApprovazioneDal.isEmpty() && !dataApprovazioneDal.equals("")) 
			url += "&dataApprovazioneDal=" + dataApprovazioneDal;
		if (dataApporvazioneAl != null && !dataApporvazioneAl.isEmpty() && !dataApporvazioneAl.equals("")) 
			url += "&dataApporvazioneAl=" + dataApporvazioneAl;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		if (numColonna != null && !numColonna.isEmpty() && !numColonna.equals(""))
			url += "&numColonna=" + numColonna;
		if(dir != null && !dir.isEmpty() && !dir.equals(""))
			url += "&dir=" + dir;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsVarianti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<VarianteDTO>>() {});
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsVarianti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<VarianteDTO> getAllVariantiByDate() throws Exception {
		
		List<VarianteDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_VARIANTE_ORDERBYDATE);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<VarianteDTO>>() {});		
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public VarianteDTO creaOrSalvaVariante(VarianteDTO variante, MyUser myUser) throws Exception {
		VarianteDTO result = null;
		variante.setUsername(myUser.getUsername());
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_SALVA_VARIANTE);	 
		ResponseData response = restService.restPostTable(url, variante);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<VarianteDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public VarianteDTO cancellaVariante(Long idVariante) throws Exception {
		VarianteDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_CANCELLA_VARIANTE)+"?idVariante="+idVariante;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<VarianteDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DocumentoVarianteDTO> cercaDocumentiByIdVariante(Long idVariante) throws Exception {
		List<DocumentoVarianteDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_BY_VARIANTE)+"?idVariante="+idVariante;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DocumentoVarianteDTO>>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public InserimentoDocumentoDTO creaOrSalvaDocumento(InserimentoDocumentoDTO documento) throws Exception {
		InserimentoDocumentoDTO result = null;
		InserimentoDocumentoByteDTO passaggio = new InserimentoDocumentoByteDTO();
		passaggio.setIdVariante(documento.getIdVariante());
		passaggio.setIdDocumento(documento.getIdDocumento());
		passaggio.setEnte(documento.getEnte());
		passaggio.setTipo(documento.getTipo());
		passaggio.setVigente(documento.getVigente());
		passaggio.setAzione(documento.getAzione());
		passaggio.setNomeFile(documento.getFile().getOriginalFilename());
		passaggio.setFile(documento.getFile().getBytes());
		passaggio.setEstensione(documento.getEstensione());
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_SALVA_DOCUMENTO);	 
		ResponseData response = restService.restPostTable(url, passaggio);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<InserimentoDocumentoDTO>() {});		
		}
		return result;
	}

	@Override
	public DataTableDTO ricercaDocumenti(String codice, String descrizione, String descrizioneCDU, String note, Integer page, Integer size, Integer draw) throws Exception {
		List<TipoDocumentoDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_TIPO_DOCUMENTO)+"?";
		
		if(codice != null && !codice.isEmpty() && !codice.equals(""))
			url += "&codice=" + codice;
		if(descrizione != null && !descrizione.isEmpty() && !descrizione.equals(""))
			url += "&descrizione=" + descrizione;
		if(descrizioneCDU != null && !descrizioneCDU.isEmpty() && !descrizioneCDU.equals("")) 
			url += "&descrizioneCDU=" + descrizioneCDU;
		if(note != null && !note.isEmpty() && !note.equals(""))
			url += "&note=" + note;
		if(page != null)
			url += "&page=" + page;
		if(size != null)
			url += "&size=" + size;
		if(draw != null)
			url += "&draw=" + draw;
		
		ResponseData response = restService.restGetRD(url);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoDocumentoDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(draw);
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalRecords());
		
		return item;
		
	}
	
	@Override
	public TipoDocumentoDTO insertOrUpdateTipoDocumento(TipoDocumentoDTO documento) throws Exception {
		TipoDocumentoDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_SALVA_TIPO_DOCUMENTO);	 
		ResponseData response = restService.restPostTable(url, documento);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<TipoDocumentoDTO>() {});		
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public TipoDocumentoDTO cancellaTipoDocumento(Long id) throws Exception {
		
		TipoDocumentoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_CANCELLA_TIPO_DOCUMENTO) + "?id=" + id;
		
		ResponseData response = restService.restGetRD(url);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<TipoDocumentoDTO>(){});	
		}
		
		return result;
		
	}
	
	@Override
	public List<TipoDocumentoDTO> findAllTipoDocumento() throws Exception {
		
		List<TipoDocumentoDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_FIND_ALL_TIPO_DOCUMENTO);

		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoDocumentoDTO>>(){});	
		}
		
		return result;
	}
	
	@Override
	public List<TipoDocumentoVarianteDTO> varianteByTipoDocumento(String tipoDocumento) throws Exception {
		
		List<TipoDocumentoVarianteDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_FIND_VARIANTE_BY_TIPO_DOCUMENTO) + "?tipoDocumento=" + tipoDocumento;

		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoDocumentoVarianteDTO>>(){});	
		} else {
			throw new UrbamidServiceException("Errore nel recupero dei tipi di documenti associati alla variante");
		}
		
		return result;
	}

	@Override
	public int countTipoDocumento(String codice) throws Exception {
		
		int result = 0;
		
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_COUNT_TIPO_DOCUMENTO) + "?codice=" + codice;

		ResponseData response = restService.restGetRD(url);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>(){});	
		}
		
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DocumentoVarianteDTO cancellaDocumento(Long idDocumento) throws Exception {
		DocumentoVarianteDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_CANCELLA_DOCUMENTO)+"?idDocumento="+idDocumento;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<DocumentoVarianteDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String downloadDocumento(Long idDocumento) throws Exception {
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_DOWNLOAD_DOCUMENTO)+"?idDocumento="+idDocumento;	
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public AmbitoVarianteDTO salvaAmbitoVarianteTracciato(AmbitoVarianteDTO data) throws Exception {
		AmbitoVarianteDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_AMBITO_VARIANTE_TRACCIATO);	 
		ResponseData response = restService.restPostTable(url, data);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AmbitoVarianteDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public AmbitoVarianteListDTO salvaAmbitoVarianteSelezione(AmbitoVarianteListDTO data) throws Exception {
		AmbitoVarianteListDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_AMBITO_VARIANTE_SELEZIONE);	 
		ResponseData response = restService.restPostTable(url, data);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AmbitoVarianteListDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public AmbitoVarianteListDTO salvaAmbitoVarianteSelezioneLayer(AmbitoVarianteListDTO data) throws Exception {
		AmbitoVarianteListDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_AMBITO_VARIANTE_SELEZIONE_LAYER);	 
		ResponseData response = restService.restPostTable(url, data);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AmbitoVarianteListDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<CartografiaVarianteDTO> cercaCartografieByIdVariante(Long idVariante) throws Exception {
		List<CartografiaVarianteDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_CARTOGRAFIE_BY_VARIANTE)+"?idVariante="+idVariante;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<CartografiaVarianteDTO>>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public CartografiaDTO salvaCartografia(CartografiaDTO variante) throws Exception {
		CartografiaDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_CARTOGRAFIE_SALVATAGGIO);	 
		ResponseData response = restService.restPostTable(url, variante);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<CartografiaDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<AmbitoRicercaDTO> salvaAmbitoVarianteRicerca(List<AmbitoRicercaDTO> lsParticelle) throws Exception {
		 List<AmbitoRicercaDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_AMBITO_VARIANTE_RICERCA);	 
		ResponseData response = restService.restPostTable(url, lsParticelle);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AmbitoRicercaDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipoDocumentoDTO> cercaTipiDocumentoMancanti(Long idVariante) throws Exception {
		List<TipoDocumentoDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_FIND_TIPO_DOCUMENTO_BY_VARIANTE)+"?idVariante="+idVariante;	 
		ResponseData response = restService.restGetRD(url);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoDocumentoDTO>>(){});	
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DocumentoIndiceDTO> cercaIndiciByIdDocumento(Long idDocumento) throws Exception {
		List<DocumentoIndiceDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE)+"?idDocumento="+idDocumento;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DocumentoIndiceDTO>>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DocumentoIndiceCodiciDTO> cercaCodiciByIdIndice(Long idIndice) throws Exception {
		List<DocumentoIndiceCodiciDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE_CODICE)+"?idIndice="+idIndice;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DocumentoIndiceCodiciDTO>>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public InserimentoIndiceDTO salvaIndice(InserimentoIndiceDTO indice) throws Exception {
		InserimentoIndiceDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE_SALVA);	 
		ResponseData response = restService.restPostTable(url, indice);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<InserimentoIndiceDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DocumentoIndiceDTO cancellaIndice(Long idIndice) throws Exception {
		DocumentoIndiceDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE_CANCELLA)+"?idIndice="+idIndice;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<DocumentoIndiceDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public ImportIndiceDTO importaIndice(ImportIndiceDTO indice) throws Exception {
		ImportIndiceDTO result = null;
		ImportIndiceByteDTO passaggio = new ImportIndiceByteDTO();
		passaggio.setIdDocumento(indice.getIdDocumento());
		passaggio.setNomeFile(indice.getFile().getOriginalFilename());
		passaggio.setFile(indice.getFile().getBytes());
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE_IMPORT);	 
		ResponseData response = restService.restPostTable(url, passaggio);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<ImportIndiceDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String esportaIndice(Long idDocumento) throws Exception {
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE_EXPORT)+"?idDocumento="+idDocumento;	
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<String> cercaAmbitoByIdVariante(Long idVariante) throws Exception {
		List<String> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_AMBITO_VARIANTE_CERCA_DA_VARIANTE)+"?idVariante="+idVariante;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<String>>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public CodiceDTO salvaCodici(CodiceDTO codice) throws Exception {
		CodiceDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_DOCUMENTI_INDICE_CODICE_SALVA);	 
		ResponseData response = restService.restPostTable(url, codice);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<CodiceDTO>() {});		
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<CodiceZtoDTO> ricercaCodiceZto() throws Exception {
		List<CodiceZtoDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_CODICI_ZTO);	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<CodiceZtoDTO>>() {});		
		} 
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String salvaNuovoGruppo(String nomeGruppo) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_SALVA_CATALOGO_GRUPPO)+"?nomeGruppo="+nomeGruppo;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<CartografiaVarianteLayerDTO> varianteByNomeLayer(String nomeLayer) throws Exception {
		List<CartografiaVarianteLayerDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_VARIANTE_BY_NOME_LAYER)+"?nomeLayer="+nomeLayer;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<CartografiaVarianteLayerDTO>>() {});		
		} else {
			throw new UrbamidServiceException("Errore nel recupero dei nomi layer associati alla variante");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<CatalogoGruppoDTO> reperimentoCatalogoVariante() throws Exception {
		List<CatalogoGruppoDTO> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_GET_CATALOGO_VARIANTE);	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<CatalogoGruppoDTO>>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public AggiuntaLayerDTO salvaLayer(AggiuntaLayerDTO layer) throws Exception {
		AggiuntaLayerDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_SALVA_CATALOGO_LAYER);	 
		ResponseData response = restService.restPostTable(url, layer);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AggiuntaLayerDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String cancellaLayer(Long idLayer) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_CANCELLA_CATALOGO_LAYER)+"?idLayer="+idLayer;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String cancellaGruppo(Long idGruppo) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_CANCELLA_CATALOGO_GRUPPO)+"?idGruppo="+idGruppo;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String downloadCdu(InterrogazionePianoDTO interrogazionePianoDTO) throws Exception {
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_CDU_RICERCA);	
		ResponseData response = restService.restPostTable(url, interrogazionePianoDTO);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String downloadCduByProtocollo(String protocollo) throws Exception {
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_CDU_DOWNLOAD_BY_PROTOCOLLO)+"?protocollo="+protocollo;
		logger.debug("URL DOWNLOAD CDU: "+url);
		ResponseData response = restService.restPostTable(url, null);
		return (String) response.getAaData();
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public CartografiaDTO cancellaCartografia(CartografiaDTO cartografia) throws Exception {
		CartografiaDTO result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_RICERCA_CARTOGRAFIE_CANCELLA);	 
		ResponseData response = restService.restPostTable(url, cartografia);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<CartografiaDTO>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String cercaProtocollo(String protocollo) throws Exception {
		String result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_CDU_CONTROLLO_PROTOCOLLO)+"?protocollo="+protocollo;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<String>() {});		
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public DataTableDTO cercaCdu(String protocollo, String dataCreazione, Integer page, Integer size, Integer draw) throws Exception {
		List<CduDTO> lsVarianti = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_CDU_ANAGRAFICA_RICERCA)+"?";
		if (protocollo != null && !protocollo.isEmpty() && !protocollo.equals("")) 
			url += "&protocollo=" + protocollo;
		if (dataCreazione != null && !dataCreazione.isEmpty() && !dataCreazione.equals("")) 
			url += "&dataCreazione=" + dataCreazione;
		if (page != null) 
			url += "&page=" + page;
		if (size != null) 
			url += "&size=" + size;
		if (draw != null) 
			url += "&draw=" + draw;
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			lsVarianti = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<CduDTO>>() {});
		}
		DataTableDTO item = new DataTableDTO();
		item.setDraw(draw);
		item.setData(lsVarianti);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		return item;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public String downloadCduAnagrafica(String protocollo) throws Exception {
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT) + env.getProperty(IConstants.WS_PRG_CDU_ANAGRAFICA_DOWNLOAD)+"?protocollo="+protocollo;	
		ResponseData response = restService.restGetRD(url);
		return (String) response.getAaData();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<String> reperimentoColonneLayer(String nomeLayer) throws Exception {
		List<String> result = null;
		String url = env.getProperty(IConstants.WS_PRG_ENDPOINT)+ env.getProperty(IConstants.WS_PRG_GET_COLONNE_LAYER) + "?nomeLayer=" + nomeLayer;	 
		ResponseData response = restService.restGetRD(url);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<String>>() {});		
		}
		return result;
	}

}