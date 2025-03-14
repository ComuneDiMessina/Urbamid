package it.eng.tz.urbamid.web.services;

import java.util.List;

import it.eng.tz.urbamid.security.model.MyUser;
import it.eng.tz.urbamid.web.dto.AggiuntaLayerDTO;
import it.eng.tz.urbamid.web.dto.AmbitoRicercaDTO;
import it.eng.tz.urbamid.web.dto.AmbitoVarianteDTO;
import it.eng.tz.urbamid.web.dto.AmbitoVarianteListDTO;
import it.eng.tz.urbamid.web.dto.CartografiaDTO;
import it.eng.tz.urbamid.web.dto.CartografiaVarianteDTO;
import it.eng.tz.urbamid.web.dto.CartografiaVarianteLayerDTO;
import it.eng.tz.urbamid.web.dto.CatalogoGruppoDTO;
import it.eng.tz.urbamid.web.dto.CodiceDTO;
import it.eng.tz.urbamid.web.dto.CodiceZtoDTO;
import it.eng.tz.urbamid.web.dto.DataTableDTO;
import it.eng.tz.urbamid.web.dto.DocumentoIndiceCodiciDTO;
import it.eng.tz.urbamid.web.dto.DocumentoIndiceDTO;
import it.eng.tz.urbamid.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.web.dto.ImportIndiceDTO;
import it.eng.tz.urbamid.web.dto.InserimentoDocumentoDTO;
import it.eng.tz.urbamid.web.dto.InserimentoIndiceDTO;
import it.eng.tz.urbamid.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.web.dto.TipoDocumentoVarianteDTO;
import it.eng.tz.urbamid.web.dto.VarianteDTO;

public interface PianoRegolatoreService {

	DataTableDTO ricercaVarianti(String nome, String ente, String descrizione, String dataAdozioneDal,
			String dataAdozioneAl, String dataApprovazioneDal, String dataApporvazioneAl, Integer page, Integer size,
			Integer draw, String numColonna, String dir) throws Exception;

	List<VarianteDTO> getAllVariantiByDate() throws Exception;
	
	VarianteDTO creaOrSalvaVariante(VarianteDTO variante, MyUser myUser) throws Exception;

	VarianteDTO cancellaVariante(Long idVariante) throws Exception;
	
	List<DocumentoVarianteDTO> cercaDocumentiByIdVariante(Long idVariante) throws Exception;

	InserimentoDocumentoDTO creaOrSalvaDocumento(InserimentoDocumentoDTO documento) throws Exception;
	
	DataTableDTO ricercaDocumenti(String codice, String descrizione, String descrizioneCDU, String note, Integer page, Integer size, Integer draw) throws Exception;

	TipoDocumentoDTO insertOrUpdateTipoDocumento(TipoDocumentoDTO documento) throws Exception;

	int countTipoDocumento(String codice) throws Exception;

	List<TipoDocumentoDTO> findAllTipoDocumento() throws Exception;

	TipoDocumentoDTO cancellaTipoDocumento(Long id) throws Exception;

	DocumentoVarianteDTO cancellaDocumento(Long idDocumento) throws Exception;

	String downloadDocumento(Long idDocumento) throws Exception;

	AmbitoVarianteDTO salvaAmbitoVarianteTracciato(AmbitoVarianteDTO data) throws Exception;

	AmbitoVarianteListDTO salvaAmbitoVarianteSelezione(AmbitoVarianteListDTO data) throws Exception;

	AmbitoVarianteListDTO salvaAmbitoVarianteSelezioneLayer(AmbitoVarianteListDTO data) throws Exception;

	List<CartografiaVarianteDTO> cercaCartografieByIdVariante(Long idVariante) throws Exception;

	CartografiaDTO salvaCartografia(CartografiaDTO variante) throws Exception;

	List<AmbitoRicercaDTO> salvaAmbitoVarianteRicerca(List<AmbitoRicercaDTO> lsParticelle) throws Exception;

	List<TipoDocumentoDTO> cercaTipiDocumentoMancanti(Long idVariante) throws Exception;

	List<DocumentoIndiceDTO> cercaIndiciByIdDocumento(Long idDocumento) throws Exception;

	List<DocumentoIndiceCodiciDTO> cercaCodiciByIdIndice(Long idIndice) throws Exception;

	InserimentoIndiceDTO salvaIndice(InserimentoIndiceDTO indice) throws Exception;

	DocumentoIndiceDTO cancellaIndice(Long idIndice) throws Exception;

	ImportIndiceDTO importaIndice(ImportIndiceDTO documento) throws Exception;

	String esportaIndice(Long idDocumento) throws Exception;

	List<String> cercaAmbitoByIdVariante(Long idVariante) throws Exception;

	CodiceDTO salvaCodici(CodiceDTO codice) throws Exception;
	
	List<CodiceZtoDTO> ricercaCodiceZto() throws Exception;

	String salvaNuovoGruppo(String nomeGruppo) throws Exception;

	List<CatalogoGruppoDTO> reperimentoCatalogoVariante() throws Exception;

	AggiuntaLayerDTO salvaLayer(AggiuntaLayerDTO layer) throws Exception;

	String cancellaLayer(Long idLayer) throws Exception;

	String cancellaGruppo(Long idGruppo) throws Exception;

	String downloadCdu(InterrogazionePianoDTO interrogazionePianoDTO) throws Exception;
	
	String downloadCduByProtocollo(String protocollo) throws Exception;

	CartografiaDTO cancellaCartografia(CartografiaDTO cartografia) throws Exception;

	String cercaProtocollo(String protocollo) throws Exception;

	List<CartografiaVarianteLayerDTO> varianteByNomeLayer(String nomeLayer) throws Exception;

	List<TipoDocumentoVarianteDTO> varianteByTipoDocumento(String tipoDocumento) throws Exception;

	DataTableDTO cercaCdu(String protocollo, String dataCreazione, Integer page, Integer size, Integer draw) throws Exception;

	String downloadCduAnagrafica(String protocollo) throws Exception;

	List<String> reperimentoColonneLayer(String nomeLayer) throws Exception;

}
