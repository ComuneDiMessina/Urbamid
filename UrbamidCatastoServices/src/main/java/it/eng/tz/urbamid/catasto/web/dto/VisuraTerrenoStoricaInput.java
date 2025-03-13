package it.eng.tz.urbamid.catasto.web.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class VisuraTerrenoStoricaInput {

	private String dataAttiDal;
	private String dataAttiAl;
	private String comune;
	private String codComune;
	private String provincia;
	private String foglio;
	private String numero;
	private String sub;
	private JRBeanCollectionDataSource terreniDataSet;
	private JRBeanCollectionDataSource intestatariDataSet;
	private JRBeanCollectionDataSource dettaglioIntestatariDataSet;

	public Map<String, Object> getDataSources() {
	       Map<String,Object> dataSources = new HashMap<>();
	       dataSources.put("dataAttiDal", dataAttiDal);
	       dataSources.put("dataAttiAl", dataAttiAl);
	       dataSources.put("comune", comune);
	       dataSources.put("codComune", codComune);
	       dataSources.put("provincia", provincia);
	       dataSources.put("foglio", foglio);
	       dataSources.put("numero", numero);
	       dataSources.put("sub", sub);
	       dataSources.put("terreniDataSet", terreniDataSet);
	       dataSources.put("intestatariDataSet", intestatariDataSet);
	       dataSources.put("dettaglioIntestatariDataSet", dettaglioIntestatariDataSet);
	       return dataSources;
		}


	public String getDataAttiDal() {
		return dataAttiDal;
	}


	public void setDataAttiDal(String dataAttiDal) {
		this.dataAttiDal = dataAttiDal;
	}


	public String getDataAttiAl() {
		return dataAttiAl;
	}


	public void setDataAttiAl(String dataAttiAl) {
		this.dataAttiAl = dataAttiAl;
	}


	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

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

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public JRBeanCollectionDataSource getTerreniDataSet() {
		return terreniDataSet;
	}

	public void setTerreniDataSet(JRBeanCollectionDataSource terreniDataSet) {
		this.terreniDataSet = terreniDataSet;
	}

	public JRBeanCollectionDataSource getIntestatariDataSet() {
		return intestatariDataSet;
	}

	public void setIntestatariDataSet(JRBeanCollectionDataSource intestatariDataSet) {
		this.intestatariDataSet = intestatariDataSet;
	}

	public JRBeanCollectionDataSource getDettaglioIntestatariDataSet() {
		return dettaglioIntestatariDataSet;
	}

	public void setDettaglioIntestatariDataSet(JRBeanCollectionDataSource dettaglioIntestatariDataSet) {
		this.dettaglioIntestatariDataSet = dettaglioIntestatariDataSet;
	}

}
