package it.eng.tz.urbamid.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImportIndiceDTO {

	private Long idDocumento;
	private MultipartFile file;

	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
