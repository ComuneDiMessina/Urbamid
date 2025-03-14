package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;

public class ShapeResponseDTO implements Serializable {
	
	private static final long serialVersionUID = -2136939968231786498L;
	
	private long id;
	private String nameFile;
	private Path pathFile;
	private long sizeFile;
	private Date dataImport;
	private byte[] byteShape;
	private boolean processato;
	
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public Path getPathFile() {
		return pathFile;
	}
	public void setPathFile(Path pathFile) {
		this.pathFile = pathFile;
	}
	public byte[] getByteShape() {
		return byteShape;
	}
	public void setByteShape(byte[] byteShape) {
		this.byteShape = byteShape;
	}
	public long getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(long sizeFile) {
		this.sizeFile = sizeFile;
	}
	public Date getDataImport() {
		return dataImport;
	}
	public void setDataImport(Date dataImport) {
		this.dataImport = dataImport;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isProcessato() {
		return processato;
	}
	public void setProcessato(boolean processato) {
		this.processato = processato;
	}

}
