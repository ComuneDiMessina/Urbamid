package it.eng.tz.urbamid.web.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

public interface IToponomasticaService {
	
	public DocumentoStorageDTO upload(DocumentoStorageDTO documento) throws IOException;
	
	public DocumentoStorageDTO download(String fileName, Long idRisorsa, Long tipoRisorsa) throws IOException;
	
	public DataTableDTO getFile(DocumentoFilter filter) throws Exception;
	
	public DocumentoStorageDTO eliminaStorage(Long idRisorsa, Long tipoRisorsa, String nomeDocumento) throws Exception;
	
	public List<DugDTO> getDug(String dug) throws Exception;
	
	public List<TipologicaDTO> getComuniByMessina() throws Exception;
	
	public List<ToponimoDugDTO> getToponimo(String toponimo) throws Exception;
	
	public List<EstesaAmministrativaDTO> getEstesaAmministrativa(String descrizione) throws Exception;
	
	public List<TipoToponimoDTO> getTipoToponimo() throws Exception;
	
	public List<TipoLocalitaDTO> getTipoLocalita() throws Exception;
	
	public List<TipoAccessoDTO> getTipoAccesso() throws Exception;
	
	public List<EstesaAmministrativaDTO> findSiglaEstesaById(Long id) throws Exception;
		
	public LocalitaDTO eliminaLocalita(Long id) throws Exception;
		
	public DataTableDTO ricercaLocalita(LocalitaFilter filter) throws Exception;

	public List<LocalitaDTO> findAllLocalita() throws Exception;
	
	public LocalitaDTO insertOrUpdateLocalita(LocalitaDTO localita) throws Exception;
		
	public DataTableDTO ricercaToponimo(ToponimoFilter filter) throws Exception;
	
	public List<ToponimoStradaleDTO> getToponimoFigli(Long idPadre) throws Exception;
	
	public Boolean isFigliPubblicati(Long idPadre) throws Exception;

	public ToponimoStradaleDTO insertOrUpdateToponimo(ToponimoStradaleDTO toponimo) throws Exception;
	
	public Integer pubblicaToponimoStradale(Long id) throws Exception;
		
	public ToponimoStradaleDTO eliminaToponimo(Long id, Boolean archivia) throws Exception;

	public DataTableDTO ricercaAccesso(AccessoFilter filter) throws Exception;

	public AccessoDTO insertOrUpdateAccesso(AccessoDTO accesso) throws Exception;
	
	public AccessoDTO eliminaAccesso(Long id) throws Exception;
	
	public AccessoDTO eliminaAccessoByToponimo(Long idAccesso, Long idToponimo) throws Exception;
	
	public AccessoDTO eliminaAccessoByLocalita(Long idAccesso, Long idLocalita) throws Exception;

	public Long countAccessoByToponimo(Long toponimo) throws Exception;
	
	public Long countAccessoByLocalita(Long localita) throws Exception;
	
	public DataTableDTO ricercaEstesaAmministrativa(EstesaAmministrativaFilter filter) throws Exception;
	
	public List<EnteGestoreDTO> findAllEnteGestore() throws Exception;
	
	public List<ClassificaAmministrativaDTO> findAllClassificaAmministrativa() throws Exception;
	
	public List<ClassificaFunzionaleDTO> findAllClassificaFunzionale() throws Exception;
	
	public List<PatrimonialitaDTO> findAllPatrimonialita() throws Exception;
	
	public EstesaAmministrativaDTO insertOrUpdateEstesa(EstesaAmministrativaDTO estesaAmministrativa) throws Exception;
	
	public EstesaAmministrativaDTO eliminaEstesaAmministrativa(Long id) throws Exception;
		
	public DataTableDTO ricercaPercorso(PercorsoFilter filter) throws Exception;

	public PercorsoDTO insertOrUpdatePercorso(PercorsoDTO percorso) throws Exception;
	
	public PercorsoDTO eliminaPercorso(Long id) throws Exception;
	
	public DataTableDTO ricercaCippoChilometrico(CippoChilometricoFilter filter) throws Exception;

	public CippoChilometricoDTO insertOrUpdateCippo(CippoChilometricoDTO cippo) throws Exception;
	
	public CippoChilometricoDTO eliminaCippo(Long id) throws Exception;
	
	public Long countCippiByEstesa(Long idEstesa) throws Exception;
	
	public DataTableDTO ricercaGiunzioneStradale(GiunzioneFilter filter) throws Exception;
	
	public GiunzioneStradaleDTO insertOrUpdateGiunzione(GiunzioneStradaleDTO giunzione) throws Exception;
	
	public GiunzioneStradaleDTO eliminaGiunzione(Long id) throws Exception;
	
	public List<TipoTopologicoDTO> getTipoTopologico() throws Exception;
	
	public List<TipoFunzionaleDTO> getTipoFunzionale() throws Exception;

	public List<ToponimoStradaleDTO> findIntersections(String geom) throws Exception;

	public ShapeResponseDTO exportShapeFile(ToponimoFilter filter) throws Exception;

	public DataTableDTO getShapeFiles(ShapeFileFilter filter) throws Exception;

	public boolean importShapeFile(MultipartFile file) throws Exception;

	public boolean deleteFileShape(Long id) throws Exception;

}
