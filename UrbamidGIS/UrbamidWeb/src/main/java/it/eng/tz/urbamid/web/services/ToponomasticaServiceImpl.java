package it.eng.tz.urbamid.web.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.AccessoDTO;
import it.eng.tz.urbamid.web.dto.CippoChilometricoDTO;
import it.eng.tz.urbamid.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DocumentoStorageDTO;
import it.eng.tz.urbamid.web.dto.DugDTO;
import it.eng.tz.urbamid.web.dto.EnteGestoreDTO;
import it.eng.tz.urbamid.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.web.dto.GiunzioneStradaleDTO;
import it.eng.tz.urbamid.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.web.dto.PatrimonialitaDTO;
import it.eng.tz.urbamid.web.dto.PercorsoDTO;
import it.eng.tz.urbamid.web.dto.ShapeResponseDTO;
import it.eng.tz.urbamid.web.dto.TipoAccessoDTO;
import it.eng.tz.urbamid.web.dto.TipoFunzionaleDTO;
import it.eng.tz.urbamid.web.dto.TipoLocalitaDTO;
import it.eng.tz.urbamid.web.dto.TipoTopologicoDTO;
import it.eng.tz.urbamid.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDugDTO;
import it.eng.tz.urbamid.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.web.filter.AccessoFilter;
import it.eng.tz.urbamid.web.filter.CippoChilometricoFilter;
import it.eng.tz.urbamid.web.filter.DocumentoFilter;
import it.eng.tz.urbamid.web.filter.EstesaAmministrativaFilter;
import it.eng.tz.urbamid.web.filter.GiunzioneFilter;
import it.eng.tz.urbamid.web.filter.LocalitaFilter;
import it.eng.tz.urbamid.web.filter.PercorsoFilter;
import it.eng.tz.urbamid.web.filter.ShapeFileFilter;
import it.eng.tz.urbamid.web.filter.ToponimoFilter;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class ToponomasticaServiceImpl implements IToponomasticaService {

	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@Override
	public DocumentoStorageDTO upload(DocumentoStorageDTO file) throws IOException {
		
		DocumentoStorageDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_UPLOAD_DOCUMENTI);
		
		ResponseData response = restService.restPostTable(url, file);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<DocumentoStorageDTO>() {});		
		}
		
		return result;
		
	}

	@Override
	public DocumentoStorageDTO download(String fileName, Long idRisorsa, Long tipoRisorsa) throws IOException {
		
		DocumentoStorageDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DOWNLOAD_DOCUMENTI) + "/" + fileName + "?risorsa=" + idRisorsa + "&tipo=" + tipoRisorsa;
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<DocumentoStorageDTO>() {});		
		}
		
		return result;
		
	}

	@Override
	public DataTableDTO getFile(DocumentoFilter filter) throws Exception {
		
		List<DocumentoStorageDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_DOCUMENTI);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DocumentoStorageDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
		
	}

	@Override
	public DocumentoStorageDTO eliminaStorage(Long idRisorsa, Long tipoRisorsa, String nomeDocumento) throws Exception {
		
		DocumentoStorageDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_ELIMINA_DOCUMENTI) + "?risorsa=" + idRisorsa + "&tipo=" + tipoRisorsa + "&nome=" + nomeDocumento;
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<DocumentoStorageDTO>() {});		
		}
		
		return result;
		
	}
	
	@Override
	public List<DugDTO> getDug(String dug) throws Exception {
		
		List<DugDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DUG) + "?" + dug.toUpperCase();
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<DugDTO>>() {});		
		}
		return result;
		
	}
	
	@Override
	public List<TipologicaDTO> getComuniByMessina() throws Exception {
		
		List<TipologicaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_COMUNI_BY_MESSINA);
		
		ResponseData response = restService.restGetRD(url);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipologicaDTO>>() {});		
		}
		
		return result;
	
	}
	
	@Override
	public List<ToponimoDugDTO> getToponimo(String toponimo) throws Exception {
		
		List<ToponimoDugDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_TOPONIMO_AUTOCOMPLETE) + "?" + toponimo.toUpperCase();
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ToponimoDugDTO>>() {});		
		}
		
		return result;
		
	}
	
	@Override
	public List<EstesaAmministrativaDTO> getEstesaAmministrativa(String descrizione) throws Exception {
		
		List<EstesaAmministrativaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_ESTESA_AMMINISTR_AUTOCOMPLETE) + "?" + descrizione.toUpperCase();
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<EstesaAmministrativaDTO>>() {});		
		}
		
		return result;
		
	}
	
	@Override
	public List<EstesaAmministrativaDTO> findSiglaEstesaById(Long id) throws Exception {
		
		List<EstesaAmministrativaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_SIGLA_ESTESA_AMMINISTRATIVA) + "?id=" + id;
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<EstesaAmministrativaDTO>>() {});		
		}
		
		return result;
		
	}

	@Override
	public List<TipoLocalitaDTO> getTipoLocalita() throws Exception {
		
		List<TipoLocalitaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_TIPO_LOCALITA);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoLocalitaDTO>>() {});		
		}
		
		return result;
	
	}
	
	@Override
	public List<TipoAccessoDTO> getTipoAccesso() throws Exception {
		
		List<TipoAccessoDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_TIPO_ACCESSO);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoAccessoDTO>>() {});		
		}
		
		return result;
	
	}
	
	@Override
	public LocalitaDTO eliminaLocalita(Long id) throws Exception {
		
		LocalitaDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_LOCALITA)+"?id="+id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<LocalitaDTO>(){});	
		} else {
			throw new UrbamidServiceException("Errore nella cancellazione della località");
		}
		
		return result;
	}

	@Override
	public DataTableDTO ricercaLocalita(LocalitaFilter filter) throws Exception {
		
		List<LocalitaDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_LOCALITA);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<LocalitaDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public List<LocalitaDTO> findAllLocalita() throws Exception {
		
		List<LocalitaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_FIND_ALL_LOCALITA);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<LocalitaDTO>>() {});		
		}
		
		return result;
	
	}

	@Override
	public LocalitaDTO insertOrUpdateLocalita(LocalitaDTO localita) throws Exception {

		LocalitaDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_LOCALITA);
		ResponseData response = restService.restPostTable(url, localita);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<LocalitaDTO>() {});		
		} else {
			throw new UrbamidServiceException("Errore nell'inserimento o nella modifica delle località");
		}
		
		return result;
	}

	@Override
	public List<TipoToponimoDTO> getTipoToponimo() throws Exception {
		
		List<TipoToponimoDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_TIPO_TOPONIMO);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoToponimoDTO>>() {});		
		}
		
		return result;
	
	}
	
	@Override
	public DataTableDTO ricercaToponimo(ToponimoFilter filter) throws Exception {
		
		List<ToponimoStradaleDTO> lista = null;
		
		if(filter.getPageIndex() == null || filter.getPageSize() == null) {
			filter.setPageIndex(Integer.valueOf(env.getProperty("urbamid.ricerche.page")));
			filter.setPageSize(Integer.valueOf(env.getProperty("urbamid.ricerche.size")));
		}
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_TOPONIMO_STRADALE);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ToponimoStradaleDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public DataTableDTO getShapeFiles(ShapeFileFilter filter) throws Exception {
		
		List<ShapeResponseDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_SHAPE_FILES);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ShapeResponseDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
		
	}
	
	@Override
	public ShapeResponseDTO exportShapeFile(ToponimoFilter filter) throws Exception {
		ShapeResponseDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_EXPORT_SHAPE_FILE);
		ResponseData response = restService.restPostTable(url, filter);
		
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<ShapeResponseDTO>() {});
		} else {
			throw new UrbamidServiceException("Non è stato possibile creare lo shape file dei toponimi stradali");
		}
		
		return result;
		
	}
	
	@Override
	public boolean importShapeFile(MultipartFile file) throws Exception {
		
		boolean result = false;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		
		body.add("file", file.getResource());
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_IMPORT_SHAPE_FILE);
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<ResponseData> response = restTemplate.postForEntity(url, requestEntity, ResponseData.class);
		
		if(response.getBody() != null && response.getBody().isSuccess())
			result = response.getBody().isSuccess();
		else
			throw new UrbamidServiceException(response.getBody().getsEcho());
			
		return result;
	}
	
	@Override
	public boolean deleteFileShape(Long id) throws Exception {
		
		boolean result = false;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_ELIMINA_SHAPE_FILE) + "?id=" + id;
		ResponseData response = restService.restPostTable(url, null);
		
		if(response!=null && response.isSuccess()) {
			result = true;
		} else {
			throw new UrbamidServiceException("Non è stato possibile eliminare lo shape file dei toponimi stradali");
		}
		
		return result;
	}
	
	@Override
	public List<ToponimoStradaleDTO> getToponimoFigli(Long idPadre) throws Exception {
		List<ToponimoStradaleDTO> figli = new ArrayList<ToponimoStradaleDTO>();
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT)+ env.getProperty(IConstants.WS_TOPONOMASTICA_GET_TOPONIMI_STRADALI_FIGLI) + "?idPadre="+idPadre;	 
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			figli.addAll(  new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ToponimoStradaleDTO>>() {}) );		
		}
		return figli;
	}
	
	@Override
	public Boolean isFigliPubblicati(Long idPadre) throws Exception {
		Boolean result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_IS_FIGLI_PUBBLICATI) + "?idPadre=" + idPadre;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Boolean>() {});		
		} else {
			throw new UrbamidServiceException("Non sono stati trovati dei figli con lo stato impostato su PUBBLICATO");
		}
		
		return result;
	}
	
	@Override
	public ToponimoStradaleDTO insertOrUpdateToponimo(ToponimoStradaleDTO toponimo) throws Exception {

		ToponimoStradaleDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_TOPONIMO);
		ResponseData response = restService.restPostTable(url, toponimo);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<ToponimoStradaleDTO>() {});		
		}
		
		return result;
		
	}
	
	@Override
	public Integer pubblicaToponimoStradale(Long id) throws UrbamidServiceException {
		Integer result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_PUBBLICA_TOPONIMO_STRADALE) + "?id=" + id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Integer>() {});		
		} else {
			throw new UrbamidServiceException("Non è possibile pubblicare il toponimo stradale per l'assenza dei dati di delibera e autorizzazione");
		}
		
		return result;
	}
	
	@Override
	public ToponimoStradaleDTO eliminaToponimo(Long id, Boolean archivia) throws Exception {
		
		ToponimoStradaleDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_TOPONIMO)+"?id="+id+"&archivia="+archivia;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<ToponimoStradaleDTO>(){});	
		}
		
		return result;
		
	}
	
	@Override
	public DataTableDTO ricercaAccesso(AccessoFilter filter) throws Exception {
		
		List<AccessoDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_ACCESSO);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<AccessoDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public AccessoDTO insertOrUpdateAccesso(AccessoDTO accesso) throws Exception {
		
		AccessoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_ACCESSO);
		ResponseData response = restService.restPostTable(url, accesso);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AccessoDTO>() {});		
		}
		
		return result;
	}
	
	
	@Override
	public AccessoDTO eliminaAccesso(Long id) throws Exception {
		
		AccessoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_ACCESSO)+"?id="+id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AccessoDTO>(){});	
		}
		
		return result;
		
	}

	@Override
	public AccessoDTO eliminaAccessoByToponimo(Long idAccesso, Long idToponimo) throws Exception {
		
		AccessoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_ACCESSO_BY_TOPONIMO) + "?id=" + idAccesso + "&toponimo=" + idToponimo;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AccessoDTO>(){});	
		}
		
		return result;
		
	}
	
	@Override
	public AccessoDTO eliminaAccessoByLocalita(Long idAccesso, Long idLocalita) throws Exception {
		AccessoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_ACCESSO_BY_LOCALITA) + "?id=" + idAccesso + "&localita=" + idLocalita;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<AccessoDTO>(){});	
		}
		
		return result;
	}
	
	@Override
	public Long countAccessoByToponimo(Long toponimo) throws Exception {
		
		Long result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_COUNT_ACCESSO_BY_TOPONIMO) + "?toponimo=" + toponimo;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Long>(){});	
		}
		
		return result;
		
	}

	@Override
	public Long countAccessoByLocalita(Long localita) throws Exception {
		Long result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_COUNT_ACCESSO_BY_LOCALITA) + "?localita=" + localita;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Long>(){});	
		}
		
		return result;
	}

	@Override
	public DataTableDTO ricercaEstesaAmministrativa(EstesaAmministrativaFilter filter) throws Exception {
		
		List<EstesaAmministrativaDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_ESTESA_AMMINISTRATIVA);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<EstesaAmministrativaDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public List<EnteGestoreDTO> findAllEnteGestore() throws Exception {
		
		List<EnteGestoreDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_ENTE_GESTORE);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<EnteGestoreDTO>>() {});		
		}
		
		return result;
	
	}
	
	@Override
	public List<ClassificaAmministrativaDTO> findAllClassificaAmministrativa() throws Exception {
		
		List<ClassificaAmministrativaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_CLASSIFICA_AMMINISTRATIVA);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ClassificaAmministrativaDTO>>() {});		
		}
		
		return result;
	
	}

	@Override
	public List<ClassificaFunzionaleDTO> findAllClassificaFunzionale() throws Exception {
		
		List<ClassificaFunzionaleDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_CLASSIFICA_FUNZIONALE);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ClassificaFunzionaleDTO>>() {});		
		}
		
		return result;
		
	}

	@Override
	public List<PatrimonialitaDTO> findAllPatrimonialita() throws Exception {
		
		List<PatrimonialitaDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_PATRIMONIALITA);
		
		ResponseData response = restService.restPostTable(url, null);;

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PatrimonialitaDTO>>() {});		
		}
		
		return result;
		
	}
	
	@Override
	public EstesaAmministrativaDTO insertOrUpdateEstesa(EstesaAmministrativaDTO estesaAmministrativa) throws Exception {
		
		EstesaAmministrativaDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_ESTESA);
		
		ResponseData response = restService.restPostTable(url, estesaAmministrativa);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<EstesaAmministrativaDTO>() {});		
		} else {
			throw new UrbamidServiceException("Errore nell'inserimento o nella modifica dell'estesa amministrativa");
		}
		
		return result;
	}
	
	@Override
	public EstesaAmministrativaDTO eliminaEstesaAmministrativa(Long id) throws Exception {
		
		EstesaAmministrativaDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_ESTESA)+ "?id=" + id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<EstesaAmministrativaDTO>() {});		
		} else {
			throw new UrbamidServiceException("Errore nella cancellazione dell'estesa amministrativa");
		}
		
		return result;
	}

	@Override
	public DataTableDTO ricercaPercorso(PercorsoFilter filter) throws Exception {
		
		List<PercorsoDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_PERCORSO);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<PercorsoDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public PercorsoDTO insertOrUpdatePercorso(PercorsoDTO percorso) throws Exception {
		
		PercorsoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_PERCORSO);
		
		ResponseData response = restService.restPostTable(url, percorso);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<PercorsoDTO>() {});		
		}
		
		return result;
	
	}

	@Override
	public PercorsoDTO eliminaPercorso(Long id) throws Exception {
		
		PercorsoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_PERCORSO) + "?id=" + id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<PercorsoDTO>(){});	
		}
		
		return result;
	
	}
	
	@Override
	public DataTableDTO ricercaCippoChilometrico(CippoChilometricoFilter filter) throws Exception {
		
		List<CippoChilometricoDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_CIPPO_CHILOMETRICO);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<CippoChilometricoDTO>>() {});
		}
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}
	
	@Override
	public CippoChilometricoDTO insertOrUpdateCippo(CippoChilometricoDTO cippo) throws Exception {
		
		CippoChilometricoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_CIPPO);
		ResponseData response = restService.restPostTable(url, cippo);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<CippoChilometricoDTO>() {});		
		}
		
		return result;

	}

	@Override
	public CippoChilometricoDTO eliminaCippo(Long id) throws Exception {
		CippoChilometricoDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_CIPPO)+ "?id=" + id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<CippoChilometricoDTO>(){});	
		}
		
		return result;
	}

	@Override
	public Long countCippiByEstesa(Long idEstesa) throws Exception {
		
		Long result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_COUNT_CIPPO_BY_ESTESA) + "?idEstesa=" + idEstesa;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response != null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<Long>(){});	
		}
		
		return result;
		
	}

	@Override
	public DataTableDTO ricercaGiunzioneStradale(GiunzioneFilter filter) throws Exception {
		List<GiunzioneStradaleDTO> lista = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_GIUNZIONI_STRADALI);
		
		ResponseData response = restService.restPostTable(url, filter);
		if(response != null && response.isSuccess()) {
			lista = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<GiunzioneStradaleDTO>>() {});
		} 
		
		DataTableDTO item = new DataTableDTO();
		
		item.setDraw(filter.getPageDraw());
		item.setData(lista);
		item.setRecordsTotal(response.getiTotalDisplayRecords());
		item.setRecordsFiltered(response.getiTotalDisplayRecords());
		
		return item;
	}

	@Override
	public GiunzioneStradaleDTO insertOrUpdateGiunzione(GiunzioneStradaleDTO giunzione) throws Exception {
		GiunzioneStradaleDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_INSERT_OR_UPDATE_GIUNZIONI);
		
		ResponseData response = restService.restPostTable(url, giunzione);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<GiunzioneStradaleDTO>() {});		
		} else {
			throw new UrbamidServiceException("Errore nell'inserimento o nella modifica delle giunzioni stradali");
		}
		
		return result;
	}

	@Override
	public GiunzioneStradaleDTO eliminaGiunzione(Long id) throws Exception {
		GiunzioneStradaleDTO result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_DELETE_GIUNZIONI_STRADALI)+"?id="+id;
		
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<GiunzioneStradaleDTO>() {});		
		} else {
			throw new UrbamidServiceException("Errore nella cancellazione delle giunzioni stradali");
		}
		
		return result;
	}
	
	@Override
	public List<ToponimoStradaleDTO> findIntersections(String geom) throws Exception {
		List<ToponimoStradaleDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_FIND_INTERSECTION);
		
		ResponseData response = restService.restPostTable(url, geom);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<ToponimoStradaleDTO>>() {});		
		} else {
			throw new UrbamidServiceException("Errore nell'intersezione dei toponimi stradali");
		}
		
		return result;
	}

	@Override
	public List<TipoTopologicoDTO> getTipoTopologico() throws Exception {
		List<TipoTopologicoDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_TIPO_TOPOLOGICO);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoTopologicoDTO>>() {});		
		} else {
			throw new UrbamidServiceException("Errore nel recupero dei tipi topologici");
		}
		
		return result;
	}

	@Override
	public List<TipoFunzionaleDTO> getTipoFunzionale() throws Exception {
		List<TipoFunzionaleDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GET_TIPO_FUNZIONALE);
		
		ResponseData response = restService.restPostTable(url, null);

		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<TipoFunzionaleDTO>>() {});		
		} else {
			throw new UrbamidServiceException("Errore nel recupero dei tipi funzionali");
		}
		
		return result;
	}

}
