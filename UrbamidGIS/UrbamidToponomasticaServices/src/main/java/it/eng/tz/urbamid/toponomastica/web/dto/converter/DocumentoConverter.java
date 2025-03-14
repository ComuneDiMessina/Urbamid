//package it.eng.tz.urbamid.toponomastica.web.dto.converter;
//
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
//import it.eng.tz.urbamid.toponomastica.persistence.model.Documento;
//import it.eng.tz.urbamid.toponomastica.persistence.model.TipoRisorsa;
//import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
//import it.eng.tz.urbamid.toponomastica.web.dto.DocumentoDTO;
//import it.eng.tz.urbamid.toponomastica.web.dto.TipoRisorsaDTO;
//
//@Component
//public class DocumentoConverter extends AbstractConverter<Documento, DocumentoDTO>{
//
//	@Override
//	public Documento toModel(DocumentoDTO dto, Map<String, Object> parameters) throws ConverterException {
//		
//		TipoRisorsa tipo = new TipoRisorsa();
//		
//		if(dto.getTipoRisorsa() != null) {
//		
//			tipo.setId(dto.getTipoRisorsa().getId());
//			tipo.setCodice(dto.getTipoRisorsa().getCodice());
//			tipo.setDescrizione(dto.getTipoRisorsa().getDescrizione());
//		
//		}
//		
//		Documento model = new Documento();
//		
//		model.setId(dto.getId());
//		
//		return model;
//		
//	}
//
//	@Override
//	public DocumentoDTO toDTO(Documento model, Map<String, Object> parameters) throws ConverterException {
//		
//		TipoRisorsaDTO tipo = new TipoRisorsaDTO();
//		
//		if(model.getTipoRisorsa() != null) {
//			
//			tipo.setId(model.getTipoRisorsa().getId());
//			tipo.setCodice(model.getTipoRisorsa().getCodice());
//			tipo.setDescrizione(model.getTipoRisorsa().getDescrizione());
//			
//		}
//		
//		DocumentoDTO dto = new DocumentoDTO();
//		
//		dto.setId(model.getId());
//		dto.setNomeDocumento(model.getNomeDocumento());
//		dto.setDataInserimento(model.getDataInserimento());
//		dto.setTipoRisorsa(tipo);
//		dto.setIdRisorsa(model.getIdRisorsa());
//		dto.setPath(model.getPath());
//		
//		return dto;
//		
//	}
//
//}
