package it.eng.tz.urbamid.catasto.persistence.util;

public enum TabellaStorico {
	
	ACQUE("u_cat_acque_storico"),
	CONFINI("u_cat_confine_storico"),
	FABBRICATI("u_cat_fabbricati_storico"),
	FIDUCIALI("u_cat_fiduciali_storico"),
	LINEE("u_cat_linee_storico"),
	PARTICELLE("u_cat_particelle_storico"),
	SIMBOLI("u_cat_simboli_storico"),
	STRADE("u_cat_strade_storico"),
	TESTI("u_cat_testi_storico");
	
	private String table;
	
	private TabellaStorico(String table) {
		this.table = table;
	}
	
	public String table() {
		return this.table;
	}
	
	public String queryStoricizzazione() {
		String query = null;
		switch(this) {
		case ACQUE:
			query = TabellaStorico.QUERY_ACQUE;
			break;
		case CONFINI:
			query = TabellaStorico.QUERY_CONFINI;
			break;
		case FABBRICATI:
			query = TabellaStorico.QUERY_FABBRICATI;
			break;
		case FIDUCIALI:
			query = TabellaStorico.QUERY_FIDUCIALI;
			break;
		case LINEE:
			query = TabellaStorico.QUERY_LINEE;
			break;
		case PARTICELLE:
			query = TabellaStorico.QUERY_PARTICELLE;
			break;
		case SIMBOLI:
			query = TabellaStorico.QUERY_SIMBOLI;
			break;
		case STRADE:
			query = TabellaStorico.QUERY_STRADE;
			break;
		case TESTI:
			query = TabellaStorico.QUERY_TESTI;
			break;
		default:
			break;
		}
		return query;
	}
	
	
	
	private static final String QUERY_ACQUE = ""
			+ " INSERT INTO " + 
			"		storico.u_cat_acque_storico( " + 
			"		versione, data_inizio_validita, data_fine_validita, " + 
			"		codice_com, foglio, mappale, " + 
			"		allegato, sviluppo, htxt, " + 
			"		rtxt, xtxt, ytxt, geom) " + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com,\n" + 
			"		a.foglio, " + 
			"		a.mappale, " + 
			"		a.allegato, " + 
			"		a.sviluppo, " + 
			"		a.htxt, " + 
			"		a.rtxt, " + 
			"		a.xtxt, " + 
			"		a.ytxt, " + 
			"		a.geom " + 
			"	FROM public.u_cat_acque a;";
	
	private static final String QUERY_CONFINI = ""
			+ " INSERT INTO " + 
			"	storico.u_cat_confine_storico( " + 
			"		versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"		foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, " + 
			"		geom) " + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.foglio, " + 
			"		a.mappale, " + 
			"		a.allegato, " + 
			"		a.sviluppo, " + 
			"		a.htxt, " + 
			"		a.rtxt, " + 
			"		a.xtxt, " + 
			"		a.ytxt, " + 
			"		a.geom " + 
			"	FROM public.u_cat_confine a;";
	
	private static final String QUERY_FABBRICATI = ""
			+ " INSERT INTO " + 
			"	storico.u_cat_fabbricati_storico( " + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"            foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, " + 
			"            geom) " + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.foglio, " + 
			"		a.mappale, " + 
			"		a.allegato, " + 
			"		a.sviluppo, " + 
			"		a.htxt, " + 
			"		a.rtxt, " + 
			"		a.xtxt, " + 
			"		a.ytxt, " + 
			"		a.geom " + 
			"	FROM public.u_cat_fabbricati a;";
	
	private static final String QUERY_FIDUCIALI = ""
			+ " INSERT INTO " + 
			"	storico.u_cat_fiduciali_storico( " + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"            fg, mappale, \"all\", sez, codice, simbolo, posx, posy, relposx, " + 
			"            relposy, geom)" + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.fg, " +
			"		a.mappale, " + 
			"		a.\"all\", " + 
			"		a.sez, " + 
			"		a.codice, " + 
			"		a.simbolo, " + 
			"		a.posx, " + 
			"		a.posy, " + 
			"		a.relposx, " + 
			"		a.relposy, " + 
			"		a.geom " + 
			"	FROM public.u_cat_fiduciali a;";
	
	private static final String QUERY_LINEE = ""
			+ "INSERT INTO storico.u_cat_linee_storico(\n" + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, \n" + 
			"            fg, mappale, \"all\", sez, cod_linea, geom)\n" + 
			"	SELECT\n" + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.fg, " + 
			"		a.mappale, " + 
			"		a.\"all\", " + 
			"		a.sez, " + 
			"		a.cod_linea, " + 
			"		a.geom " + 
			"	FROM public.u_cat_linee a;";
	
	private static final String QUERY_PARTICELLE = ""
			+ " INSERT INTO storico.u_cat_particelle_storico( " + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"            foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, " + 
			"            geom) " + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.foglio, " + 
			"		a.mappale, " + 
			"		a.allegato, " + 
			"		a.sviluppo, " + 
			"		a.htxt, " + 
			"		a.rtxt, " + 
			"		a.xtxt, " + 
			"		a.ytxt, " + 
			"		a.geom " + 
			"	FROM public.u_cat_particelle a;";
	
	private static final String QUERY_SIMBOLI = ""
			+ " INSERT INTO " + 
			"	storico.u_cat_simboli_storico( " + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"            fg, mappale, \"all\", sez, simbolo, rot, geom) " + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.fg, " + 
			"		a.mappale, " + 
			"		a.\"all\", " + 
			"		a.sez, " + 
			"		a.simbolo, " + 
			"		a.rot, " + 
			"		a.geom " + 
			"	FROM " + 
			"		public.u_cat_simboli a;";
	
	private static final String QUERY_STRADE = ""
			+ " INSERT INTO storico.u_cat_strade_storico( " + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"            foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, " + 
			"            geom) " + 
			"	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.foglio, " + 
			"		a.mappale, " + 
			"		a.allegato, " + 
			"		a.sviluppo, " + 
			"		a.htxt, " + 
			"		a.rtxt, " + 
			"		a.xtxt, " + 
			"		a.ytxt, " + 
			"		a.geom " + 
			"	FROM " + 
			"		public.u_cat_strade a;";
	
	private static final String QUERY_TESTI = ""
			+ " INSERT INTO storico.u_cat_testi_storico( " + 
			"            versione, data_inizio_validita, data_fine_validita, codice_com, " + 
			"            fg, mappale, \"all\", sez, testo, htxt, rtxt, xtxt, ytxt, geom) " + 
			"    	SELECT " + 
			"		:versione, :dataInizioValidita, :dataFineValidita, " + 
			"		a.codice_com, " + 
			"		a.fg, " + 
			"		a.mappale, " + 
			"		a.\"all\", " + 
			"		a.sez, " + 
			"		a.testo, " + 
			"		a.htxt, " + 
			"		a.rtxt, " + 
			"		a.xtxt, " + 
			"		a.ytxt, " + 
			"		a.geom" + 
			"	FROM " + 
			"		public.u_cat_testi a;";
	
}