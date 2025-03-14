package it.eng.tz.urbamid.catasto.persistence.model;

import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@NamedNativeQuery(
		name = "SoggettoModel.findSoggetti",
		query= "SELECT id_soggetto as idSoggetto, " + 
				"				sezione, " + 
				"				cod_comune as codComune, " + 
				"				tipo_soggetto as tipoSoggetto, " + 
				"				cognome,  " + 
				"				nome, " + 
				"				sesso, " + 
				"				data_nascita as dataNascita, " + 
				"				luogo_nascita as luogoNascita, " + 
				"				codice_fiscale as codiceFiscale, " + 
				"				denominazione,  " + 
				"				sede, " + 
				"				indicazioni_supplementari as indicazioniSupplementari   " + 
				"			 FROM public.u_cat_soggetti  " + 
				"			 WHERE tipo_soggetto = :tipoSoggetto " + 
				"				and (1=:flagNome or nome = upper(:nome))  " + 
				"				and (1=:flagCognome or cognome = upper(:cognome))  " + 
				"				and (1=:fieldSesso or sesso = :sesso)  " + 
				"				and (1=:fieldCodFis or codice_fiscale = upper(:codFis))  " + 
				"				and (1=:flagNote or indicazioni_supplementari = :note)  " + 
				"				and (1=:fieldNascitaDa or data_nascita >= :nascitaDa)  " + 
				"				and (1=:flagNascitaA or data_nascita <= :nascitaA)  " + 
				"				and (1=:flagLuogoNascita or luogo_nascita = :luogoNascita)  " + 
				"				and (1=:flagDenominazione or denominazione like upper(:denominazione))  " +
				"				and (1=:flagSede or sede = :sede)  " + 
				"			 GROUP BY cod_comune, sezione, id_soggetto, tipo_soggetto, cognome,  " + 
				"			       nome, sesso, data_nascita, luogo_nascita, codice_fiscale, denominazione,  " + 
				"			       sede, indicazioni_supplementari " +
				"					order by cognome, nome, denominazione asc",
		resultSetMapping = "SoggettoModel.findSoggetti"
		)

@NamedNativeQuery(
		name = "SoggettoModel.countFindSoggetti",
		query= "SELECT count(*)" + 
				"					 FROM public.u_cat_soggetti  " + 
				"					 WHERE tipo_soggetto = :tipoSoggetto " + 
				"						and (1=:flagNome or nome = upper(:nome))  " + 
				"						and (1=:flagCognome or cognome = upper(:cognome))  " + 
				"						and (1=:fieldSesso or sesso = :sesso)  " + 
				"						and (1=:fieldCodFis or codice_fiscale = upper(:codFis))  " + 
				"						and (1=:flagNote or indicazioni_supplementari = :note)  " + 
				"						and (1=:fieldNascitaDa or data_nascita >= :nascitaDa)  " + 
				"						and (1=:flagNascitaA or data_nascita <= :nascitaA)  " + 
				"						and (1=:flagLuogoNascita or luogo_nascita = :luogoNascita)  " + 
				"						and (1=:flagDenominazione or denominazione like upper(:denominazione))  " + 
				"						and (1=:flagSede or sede = :sede)  " + 
				"					 GROUP BY cod_comune, sezione, id_soggetto, tipo_soggetto, cognome,  " + 
				"					       nome, sesso, data_nascita, luogo_nascita, codice_fiscale, denominazione,  " + 
				"					       sede, indicazioni_supplementari ",
				resultSetMapping = "SoggettoModel.countFindSoggetti"
		)
@SqlResultSetMapping(name = "SoggettoModel.countFindSoggetti", columns = @ColumnResult(name = "count"))
@SqlResultSetMapping(
		name	= "SoggettoModel.findSoggetti",
	 	classes	= {
	    @ConstructorResult(
	    	targetClass = SoggettoModel.class
	    ,	columns		= {
	    		@ColumnResult(name = "idSoggetto"				, type = Integer.class)
	    	,	@ColumnResult(name = "sezione"					, type = String	.class)
    		,	@ColumnResult(name = "codComune"				, type = String.class)
    		,	@ColumnResult(name = "tipoSoggetto"				, type = String.class)
	    	,	@ColumnResult(name = "cognome"					, type = String	.class)
    		,	@ColumnResult(name = "nome"          			, type = String	.class)
    		,	@ColumnResult(name = "sesso"		           	, type = String.class)
	    	,	@ColumnResult(name = "dataNascita"           	, type = java.util.Date.class)
	    	,	@ColumnResult(name = "luogoNascita"           	, type = String.class)
	    	,	@ColumnResult(name = "codiceFiscale"			, type = String.class)
		    ,	@ColumnResult(name = "denominazione"			, type = String	.class)
	    	,	@ColumnResult(name = "sede"						, type = String	.class)
	    	,	@ColumnResult(name = "indicazioniSupplementari"	, type = String	.class)
	    	}
		)
	})
@Entity
public class SoggettoModel {

	@Id
	private Integer idSoggetto;
	private String codComune;
	private String sezione;
	private String tipoSoggetto;
	private String cognome;
	private String nome;
	private String sesso;
	private Date dataNascita;
	private String luogoNascita;
	private String codiceFiscale;
	private String denominazione;
	private String sede;
	private String indicazioniSupplementari;

	public SoggettoModel(Integer idSoggetto, String codComune, String sezione, String tipoSoggetto,  String cognome, String nome, String sesso,                    
			Date dataNascita, String luogoNascita, String codiceFiscale, String denominazione, String sede, String indicazioniSupplementari) {
		super();
		this.codComune = codComune;
		this.sezione = sezione;
		this.idSoggetto = idSoggetto;
		this.tipoSoggetto = tipoSoggetto;
		this.cognome = cognome;
		this.nome = nome;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.codiceFiscale = codiceFiscale;
		this.denominazione = denominazione;
		this.sede = sede;
		this.indicazioniSupplementari = indicazioniSupplementari;
	}

	public Integer getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getCodComune() {
		return codComune;
	}
	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public String getIndicazioniSupplementari() {
		return indicazioniSupplementari;
	}
	public void setIndicazioniSupplementari(String indicazioniSupplementari) {
		this.indicazioniSupplementari = indicazioniSupplementari;
	}
	
}
