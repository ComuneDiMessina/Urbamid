package it.eng.tz.urbamid.catasto.web.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class VisuraFabbricatoInput {

	private String dataAtti;
	private String comune;
	private String codComune;
	private String provincia;
	private String foglio;
	private String numero;
	private String sub;
	private Integer indiceUiu;
	private String sezione;
	private String zona;
	private String categoria;
	private String classe;
	private Double consistenza;
	private Double superficie;
	private Double renditaEuro;
	private String derivazione;
	private String indirizzo;
	private String note;
	private JRBeanCollectionDataSource intestatariDataSet;

	public Map<String, Object> getDataSources() {
       Map<String,Object> dataSources = new HashMap<>();
       dataSources.put("intestatariDataSet", intestatariDataSet);
       dataSources.put("dataAtti", dataAtti);
       dataSources.put("comune", comune);
       dataSources.put("codComune", codComune);
       dataSources.put("provincia", provincia);
       dataSources.put("foglio", foglio);
       dataSources.put("numero", numero);
       dataSources.put("sub", sub);
       dataSources.put("indiceUiu", indiceUiu);
       dataSources.put("sezione", sezione);
       dataSources.put("zona", zona);
       dataSources.put("categoria", categoria);
       dataSources.put("classe", classe);
       dataSources.put("consistenza", consistenza);
       dataSources.put("superficie", superficie);
       dataSources.put("renditaEuro", renditaEuro);
       dataSources.put("derivazione", derivazione);
       dataSources.put("indirizzo", indirizzo);
       dataSources.put("note", note);
       return dataSources;
	}

	public String getDataAtti() {
		return dataAtti;
	}
	public void setDataAtti(String dataAtti) {
		this.dataAtti = dataAtti;
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
	public Integer getIndiceUiu() {
		return indiceUiu;
	}
	public void setIndiceUiu(Integer indiceUiu) {
		this.indiceUiu = indiceUiu;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Double getConsistenza() {
		return consistenza;
	}
	public void setConsistenza(Double consistenza) {
		this.consistenza = consistenza;
	}
	public Double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	public Double getRenditaEuro() {
		return renditaEuro;
	}
	public void setRenditaEuro(Double renditaEuro) {
		this.renditaEuro = renditaEuro;
	}
	public String getDerivazione() {
		return derivazione;
	}
	public void setDerivazione(String derivazione) {
		this.derivazione = derivazione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public JRBeanCollectionDataSource getIntestatariDataSet() {
		return intestatariDataSet;
	}
	public void setIntestatariDataSet(JRBeanCollectionDataSource intestatariDataSet) {
		this.intestatariDataSet = intestatariDataSet;
	}

}
