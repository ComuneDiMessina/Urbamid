package it.eng.tz.urbamid.web.dto;

import java.io.Serializable;

public class ParticelleTerreniDTO implements Serializable {

	private static final long serialVersionUID = 1175056265781535311L;

	private Long id;
	private String comune;
	private String provincia;
	private String sezione;
	private String foglio;
	private String numero;
	private String sub;
	private String partita;
	private String classe;
	private Integer qualita;
	private Integer ettari;
	private Integer are;
	private Integer centiare;
	private Double superficie;
	private Double redditoDominicaleLire;
	private Double redditoDominicaleEuro;
	private Double redditoAgrarioLire;
	private Double redditoAgrarioEuro;

	private Integer denominatore;
	private String annotazione;
	
	public Integer getDenominatore() {
		return denominatore;
	}

	public void setDenominatore(Integer denominatore) {
		this.denominatore = denominatore;
	}

	public String getAnnotazione() {
		return annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
	}
	
	public ParticelleTerreniDTO() {
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Integer getQualita() {
		return qualita;
	}
	public void setQualita(Integer qualita) {
		this.qualita = qualita;
	}
	public Integer getEttari() {
		return ettari;
	}
	public void setEttari(Integer ettari) {
		this.ettari = ettari;
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
	public Double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	public Double getRedditoDominicaleLire() {
		return redditoDominicaleLire;
	}
	public void setRedditoDominicaleLire(Double redditoDominicaleLire) {
		this.redditoDominicaleLire = redditoDominicaleLire;
	}
	public Double getRedditoDominicaleEuro() {
		return redditoDominicaleEuro;
	}
	public void setRedditoDominicaleEuro(Double redditoDominicaleEuro) {
		this.redditoDominicaleEuro = redditoDominicaleEuro;
	}
	public Double getRedditoAgrarioLire() {
		return redditoAgrarioLire;
	}
	public void setRedditoAgrarioLire(Double redditoAgrarioLire) {
		this.redditoAgrarioLire = redditoAgrarioLire;
	}
	public Double getRedditoAgrarioEuro() {
		return redditoAgrarioEuro;
	}
	public void setRedditoAgrarioEuro(Double redditoAgrarioEuro) {
		this.redditoAgrarioEuro = redditoAgrarioEuro;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
}
