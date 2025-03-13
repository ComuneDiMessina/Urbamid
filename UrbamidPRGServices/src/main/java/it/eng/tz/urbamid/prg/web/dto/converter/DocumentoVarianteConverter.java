package it.eng.tz.urbamid.prg.web.dto.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoVariante;
import it.eng.tz.urbamid.prg.web.dto.DocumentoVarianteDTO;
import it.eng.tz.urbamid.prg.web.dto.InserimentoDocumentoByteDTO;

@Component
public class DocumentoVarianteConverter implements IConverter<DocumentoVariante, DocumentoVarianteDTO> {

	@Override
	public DocumentoVariante toModel(DocumentoVarianteDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoVarianteDTO toDto(DocumentoVariante model) throws Exception {
		DocumentoVarianteDTO dto = new DocumentoVarianteDTO();
		dto.setIdDocumento(model.getIdDocumento());
		dto.setTipoDocumento(model.getTipoDocumento());
		dto.setNomeDocumento(model.getNomeDocumento());
		dto.setStatoDocumento(model.getStatoDocumento());
		File file = new File(model.getPathDocumento());
		//String[] documento = model.getPathDocumento().split("\\\\");
		dto.setPathDocumento(file.getName());
		dto.setEstensione(model.getEstensione());
		return dto;
	}

	@Override
	public List<DocumentoVarianteDTO> toDto(List<DocumentoVariante> models) throws Exception {
		List<DocumentoVarianteDTO> result = new ArrayList<DocumentoVarianteDTO>();
		try 
		{
			for (DocumentoVariante model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			throw (e);
		}

		return result;
	}

	public DocumentoVariante toModelFromByteModel(InserimentoDocumentoByteDTO documento, String pathFile) {
		DocumentoVariante model = new DocumentoVariante();
		model.setTipoDocumento(documento.getTipo());
		model.setNomeDocumento(documento.getNomeFile());
		model.setStatoDocumento(documento.getAzione());
		model.setPathDocumento(pathFile);
		model.setEstensione(documento.getEstensione());
		return model;
	}

}
