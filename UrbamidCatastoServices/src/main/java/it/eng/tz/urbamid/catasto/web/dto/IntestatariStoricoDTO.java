package it.eng.tz.urbamid.catasto.web.dto;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class IntestatariStoricoDTO {

	private String dataDal;
	private List<DettaglioIntestatariStoricoDTO> listDettaglio = new ArrayList<>();
	private JRBeanCollectionDataSource dettaglioIntestatariDataSet;

	public String getDataDal() {
		return dataDal;
	}
	public void setDataDal(String dataDal) {
		this.dataDal = dataDal;
	}
	public List<DettaglioIntestatariStoricoDTO> getListDettaglio() {
		return listDettaglio;
	}
	public void setListDettaglio(List<DettaglioIntestatariStoricoDTO> listDettaglio) {
		this.listDettaglio = listDettaglio;
	}
	public JRBeanCollectionDataSource getDettaglioIntestatariDataSet() {
		return new JRBeanCollectionDataSource(listDettaglio, false);
	}
	public void setDettaglioIntestatariDataSet(JRBeanCollectionDataSource dettaglioIntestatariDataSet) {
		this.dettaglioIntestatariDataSet = dettaglioIntestatariDataSet;
	}

}
