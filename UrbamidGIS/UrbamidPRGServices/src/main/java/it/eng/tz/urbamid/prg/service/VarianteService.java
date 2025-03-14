package it.eng.tz.urbamid.prg.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.filter.VarianteFilter;
import it.eng.tz.urbamid.prg.persistence.model.Variante;
import it.eng.tz.urbamid.prg.web.dto.AmbitoRicercaDTO;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteListDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.CodiceDTO;
import it.eng.tz.urbamid.prg.web.dto.CodiceZtoDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceCodiciDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceDTO;
import it.eng.tz.urbamid.prg.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.ImportIndiceByteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoDocumentoByteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoIndiceDTO;
import it.eng.tz.urbamid.prg.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.VarianteDTO;

@Service
public interface VarianteService {

	public PagedResult<VarianteDTO> getVarianti(VarianteFilter filter) throws Exception;

	public List<VarianteDTO> findAllOrderByDate() throws Exception;
	
	public Variante creaOrSalvaVariante(VarianteDTO variante) throws Exception;

	public VarianteDTO cancellaVariante(Long idVariante) throws Exception;

	public List<DocumentoVarianteDTO> cercaDocumentiByIdVariante(Long idVariante) throws Exception;

	public InserimentoDocumentoByteDTO creaOrSalvaDocumento(InserimentoDocumentoByteDTO documento) throws Exception;

	public DocumentoVarianteDTO cancellaDocumento(Long idDocumento) throws Exception;

	public File downloadDocumento(Long idDocumento) throws Exception;

	public AmbitoVarianteDTO salvaAmbitoVarianteTracciato(AmbitoVarianteDTO data) throws Exception;

	public AmbitoVarianteListDTO salvaAmbitoVarianteSelezione(AmbitoVarianteListDTO data) throws Exception;

	public AmbitoVarianteListDTO salvaAmbitoVarianteSelezioneLayer(AmbitoVarianteListDTO data) throws Exception;

	public List<CartografiaVarianteDTO> cercaCartografieByIdVariante(Long idVariante) throws Exception;

	public CartografiaDTO salvaCartografia(CartografiaDTO variante) throws Exception;

	public List<AmbitoRicercaDTO> salvaAmbitoVarianteRicerca(List<AmbitoRicercaDTO> lsParticelle) throws Exception;

	public void aggiornaStoricoNuovo(VarianteDTO variante) throws Exception;

	public List<DocumentoIndiceDTO> cercaIndiciByIdDocumento(Long idDocumento) throws Exception;

	public List<DocumentoIndiceCodiciDTO> cercaCodiciByIdIndice(Long idIndice) throws Exception;

	public InserimentoIndiceDTO salvaIndice(InserimentoIndiceDTO indice) throws Exception;

	public DocumentoIndiceDTO cancellaIndice(Long idIndice) throws Exception;

	public ImportIndiceByteDTO importaIndice(ImportIndiceByteDTO documento) throws Exception;

	public File esportaIndice(Long idDocumento) throws Exception;

	public List<String> cercaAmbitoByIdVariante(Long idVariante) throws Exception;

	public CodiceDTO salvaCodici(CodiceDTO codice) throws Exception;

	public File downloadCdu(InterrogazionePianoDTO interrogazionePianoDto) throws Exception;

	public CartografiaDTO cancellaCartografia(CartografiaDTO cartografia) throws Exception;

	public String cercaProtocollo(String protocollo) throws Exception;

	public List<CodiceZtoDTO> cercaCodiceZto() throws Exception;
	
}
