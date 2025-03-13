package it.eng.tz.urbamid.catasto.web.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ElencoNominativoInput {

	private String titolo;
	private String tipologiaEstrazione;
	private JRBeanCollectionDataSource nominativiDataSource;

	public Map<String, Object> getDataSources() {
		Map<String,Object> dataSources = new HashMap<>();
		dataSources.put("nominativiDataSource", nominativiDataSource);
		dataSources.put("titolo", titolo);
		dataSources.put("tipologiaEstrazione", tipologiaEstrazione);
		return dataSources;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public JRBeanCollectionDataSource getNominativiDataSource() {
		return nominativiDataSource;
	}
	public void setNominativiDataSource(JRBeanCollectionDataSource nominativiDataSource) {
		this.nominativiDataSource = nominativiDataSource;
	}

	public String getTipologiaEstrazione() {
		return tipologiaEstrazione;
	}
	public void setTipologiaEstrazione(String tipologiaEstrazione) {
		this.tipologiaEstrazione = tipologiaEstrazione;
	}

}
