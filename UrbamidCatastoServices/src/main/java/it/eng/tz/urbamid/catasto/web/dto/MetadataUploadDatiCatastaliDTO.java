package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

public class MetadataUploadDatiCatastaliDTO implements Serializable {

	private static final long serialVersionUID = 8517387272368899548L;
	
	private Long indice;
	private String nomeFile;
	private Long dimensioneFile;
	
	public MetadataUploadDatiCatastaliDTO() {
		super();
	}

	public Long getIndice() {
		return indice;
	}

	public void setIndice(Long indice) {
		this.indice = indice;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Long getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(Long dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	@Override
	public String toString() {
		return String.format("MetadataUploadDatiCatastaliDTO [indice=%s, nomeFile=%s, dimensioneFile=%s]", indice,
				nomeFile, dimensioneFile);
	}
	
}
