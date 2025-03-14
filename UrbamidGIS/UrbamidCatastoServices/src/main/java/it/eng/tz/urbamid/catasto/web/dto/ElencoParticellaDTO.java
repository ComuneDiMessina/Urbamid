package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ElencoParticellaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String foglio;
	private String numero;
	private List<ElencoParticellaProprietaDTO> listProprietari = new ArrayList<>();
	private JRBeanCollectionDataSource proprietariDataSource;

	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public List<ElencoParticellaProprietaDTO> getListProprietari() {
		return listProprietari;
	}
	public void setListProprietari(List<ElencoParticellaProprietaDTO> listProprietari) {
		this.listProprietari = listProprietari;
	}
	public JRBeanCollectionDataSource getProprietariDataSource() {
	       return new JRBeanCollectionDataSource(listProprietari, false);
	}

}
