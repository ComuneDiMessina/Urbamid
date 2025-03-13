package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class DettaglioEliminazioneFileDTO implements Serializable {
	
	private static final long serialVersionUID = 3500241200556086976L;
	
	private String cartella;
	private List<String> files;

	public DettaglioEliminazioneFileDTO() {
		super();
	}

	public DettaglioEliminazioneFileDTO(String cartella, List<String> files) {
		super();
		this.cartella = cartella;
		this.files = (files != null && !files.isEmpty()) ? files : Collections.emptyList();
	}

	public String getCartella() {
		return cartella;
	}

	public void setCartella(String cartella) {
		this.cartella = cartella;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return String.format("DettaglioEliminazioneFileDTO [cartella=%s, numero_files=%s]", cartella, files.size());
	}

}
