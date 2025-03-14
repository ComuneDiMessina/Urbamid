package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;
import java.util.Date;

public class DocumentoStorageDTO implements Serializable {
	
	private static final long serialVersionUID = -63271281546258370L;
	
	private String nomeDocumento;
	private Date dataInserimento;
	private TipoRisorsaDTO tipoRisorsa;
	private Long idRisorsa;
	private String path;
	private byte[] file;
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public TipoRisorsaDTO getTipoRisorsa() {
		return tipoRisorsa;
	}
	public void setTipoRisorsa(TipoRisorsaDTO tipoRisorsa) {
		this.tipoRisorsa = tipoRisorsa;
	}
	public Long getIdRisorsa() {
		return idRisorsa;
	}
	public void setIdRisorsa(Long idRisorsa) {
		this.idRisorsa = idRisorsa;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	
}
