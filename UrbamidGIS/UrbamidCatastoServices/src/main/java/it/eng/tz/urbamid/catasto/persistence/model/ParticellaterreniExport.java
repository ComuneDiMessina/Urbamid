package it.eng.tz.urbamid.catasto.persistence.model;

public class ParticellaterreniExport {

	private Long identificativo;
	private String comune;
	private String ute;
	private String foglio;
	private String numero;
	private String subalterno;
	private String stadio;
	private String denominatore;
	private String partita;
	private String qualita;
	private String classe;
	private String sezione;
	private Integer ettari;
	private Integer are;
	private Integer centiare;
	private Double superficie;
	private Double superficiegeometrica;
	private Double superficieesproprio;
	private Double redditodominicaleeuro;
	private Double redditoagrarioeuro;

	public Long getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getUte() {
		return ute;
	}
	public void setUte(String ute) {
		this.ute = ute;
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
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getStadio() {
		return stadio;
	}
	public void setStadio(String stadio) {
		this.stadio = stadio;
	}
	public String getDenominatore() {
		return denominatore;
	}
	public void setDenominatore(String denominatore) {
		this.denominatore = denominatore;
	}
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
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
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
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
	public Double getSuperficiegeometrica() {
		return superficiegeometrica;
	}
	public void setSuperficiegeometrica(Double superficiegeometrica) {
		this.superficiegeometrica = superficiegeometrica;
	}
	public Double getSuperficieesproprio() {
		return superficieesproprio;
	}
	public void setSuperficieesproprio(Double superficieesproprio) {
		this.superficieesproprio = superficieesproprio;
	}
	public Double getRedditodominicaleeuro() {
		return redditodominicaleeuro;
	}
	public void setRedditodominicaleeuro(Double redditodominicaleeuro) {
		this.redditodominicaleeuro = redditodominicaleeuro;
	}
	public Double getRedditoagrarioeuro() {
		return redditoagrarioeuro;
	}
	public void setRedditoagrarioeuro(Double redditoagrarioeuro) {
		this.redditoagrarioeuro = redditoagrarioeuro;
	}

	public ParticellaterreniExport convert(Particellaterreni item) {
		ParticellaterreniExport pt = new ParticellaterreniExport();
		pt.setIdentificativo(item.getIdentificativo());
		pt.setComune(item.getComune());
		pt.setUte(item.getUte());
		pt.setFoglio(item.getFoglio());
		pt.setNumero(item.getNumero());
		pt.setSubalterno(item.getSubalterno());
		pt.setStadio(item.getStadio());
		pt.setDenominatore(item.getDenominatore());
		pt.setPartita(item.getPartita());
		pt.setQualita(item.getQualita());
		pt.setClasse(item.getClasse());
		pt.setSezione(item.getSezione());
		pt.setEttari(item.getEttari());
		pt.setAre(item.getAre());
		pt.setCentiare(item.getCentiare());
		String sup = "0";
		if (item.getEttari()!=null && item.getAre()!=null && item.getCentiare()!=null)
			sup = String.valueOf(item.getEttari()*10000.0 + item.getAre()*100.0 + item.getCentiare());
		if (sup.length()>5) {
			sup = sup.substring(0, 5);
		}
		pt.setSuperficie(Double.valueOf(sup));
		pt.setRedditoagrarioeuro(item.getRedditoAgrarioEuro());
		pt.setRedditodominicaleeuro(item.getRedditoDominicaleEuro());
		return pt;
	}

}
