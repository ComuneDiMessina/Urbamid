package it.eng.tz.urbamid.catasto.web.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class VisuraTerrenoInput {

	private String dataAtti;
	private String comune;
	private String codComune;
	private String provincia;
	private String foglio;
	private String numero;
	private String sub;
	private Integer indicePt;
	private String porz;
	private String qualita;
	private String classe;
	private Integer ha;
	private Integer are;
	private Integer centiare;
	private String deduzione;
	private Double dominicale;
	private Double agrario;
	private String derivazione;
	private String notifica;
	private String partita;
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
	       dataSources.put("indicePt", indicePt);
	       dataSources.put("porz", porz);
	       dataSources.put("qualita", qualita);
	       dataSources.put("classe", classe);
	       dataSources.put("ha", ha);
	       dataSources.put("are", are);
	       dataSources.put("centiare", centiare);
	       dataSources.put("deduzione", deduzione);
	       dataSources.put("dominicale", dominicale);
	       dataSources.put("agrario", agrario);
	       dataSources.put("derivazione", derivazione);
	       dataSources.put("notifica", notifica);
	       dataSources.put("partita", partita);
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

	public Integer getIndicePt() {
		return indicePt;
	}

	public void setIndicePt(Integer indicePt) {
		this.indicePt = indicePt;
	}

	public String getPorz() {
		return porz;
	}

	public void setPorz(String porz) {
		this.porz = porz;
	}

	public String getQualita() {
		return qualita;
	}

	public void setQualita(String qualita) {
		this.qualita = qualita;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Integer getHa() {
		return ha;
	}

	public void setHa(Integer ha) {
		this.ha = ha;
	}

	public Integer getAre() {
		return are;
	}

	public void setAre(Integer are) {
		this.are = are;
	}

	public Integer getCentiare() {
		return centiare;
	}

	public void setCentiare(Integer centiare) {
		this.centiare = centiare;
	}

	public String getDeduzione() {
		return deduzione;
	}

	public void setDeduzione(String deduzione) {
		this.deduzione = deduzione;
	}

	public Double getDominicale() {
		return dominicale;
	}

	public void setDominicale(Double dominicale) {
		this.dominicale = dominicale;
	}

	public Double getAgrario() {
		return agrario;
	}

	public void setAgrario(Double agrario) {
		this.agrario = agrario;
	}

	public String getDerivazione() {
		return derivazione;
	}

	public void setDerivazione(String derivazione) {
		this.derivazione = derivazione;
	}

	public String getNotifica() {
		return notifica;
	}

	public void setNotifica(String notifica) {
		this.notifica = notifica;
	}

	public String getPartita() {
		return partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
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
