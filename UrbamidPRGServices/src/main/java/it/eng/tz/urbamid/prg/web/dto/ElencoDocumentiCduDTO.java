package it.eng.tz.urbamid.prg.web.dto;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ElencoDocumentiCduDTO {

	private String nomeDocumento;
	private List<ElencoIndiciCduDTO> listIndici = new ArrayList<>();
	private JRBeanCollectionDataSource listaCodiciIndici;

	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public List<ElencoIndiciCduDTO> getListIndici() {
		return listIndici;
	}
	public void setListIndici(List<ElencoIndiciCduDTO> listIndici) {
		this.listIndici = listIndici;
	}
	public JRBeanCollectionDataSource getListaCodiciIndici() {
		return new JRBeanCollectionDataSource(listIndici, false);
	}

}
