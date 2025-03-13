package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobileJoinDettaglio;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliare;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoin;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoinRicerca;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareUrbana;

public interface JpaRepositoryUnitaImmobiliare extends PagingAndSortingRepository<UnitaImmobiliare, Long>, JpaSpecificationExecutor<UnitaImmobiliare> {

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_unita_immobiliare p " + 
			"  WHERE id_immobile in(SELECT id_immobile" + 
			"			FROM public.u_cat_identificativi_unita_immobiliari" + 
			"			WHERE cod_comune in :lsCodComuni" + 
			"			and foglio in :lsFogli" + 
			"			and numero in :lsMappale)" +
			"			and tipo_immobile = 'F' ", nativeQuery = true)
	List<UnitaImmobiliare> ricercaSuParticelleUI(
			 @Param("lsCodComuni") List<String> lsCodComuni,
			 @Param("lsFogli") List<String> lsFogli, 
			 @Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT 	ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita" + 
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" + 
			"	and ui.id_immobile in ( " +
			" 	SELECT id_immobile" + 
			"			FROM public.u_cat_identificativi_unita_immobiliari" + 
			"			WHERE cod_comune in :lsCodComuni" + 
			"			and foglio in :lsFogli" + 
			"			and numero in :lsMappale)" +
			"	and ui.tipo_immobile = 'F' ", nativeQuery = true)
	List<UnitaImmobiliareJoin> ricercaSuParticelleUIJoin(
			 @Param("lsCodComuni") List<String> lsCodComuni,
			 @Param("lsFogli") List<String> lsFogli, 
			 @Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT 	ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita" + 
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" + 
			"	and ui.id_immobile in ( " +
			" 	SELECT id_immobile" + 
			"			FROM public.u_cat_identificativi_unita_immobiliari" + 
			"			WHERE cod_comune = :codComune" + 
			"			and foglio = :foglio" + 
			"			and numero in :lsMappale)" +
			"	and ui.tipo_immobile = 'F' ", nativeQuery = true)
	List<UnitaImmobiliareJoin> ricercaFMSuParticelleUIJoin(
			 @Param("codComune") String codComune,
			 @Param("foglio") String foglio, 
			 @Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT ui.id_immobile as Identificativo," + 
			"		ui.cod_comune as Comune," + 
			//"		'Ute' as Ute," + 
			"		ide.foglio as Foglio," + 
			"		ide.numero as Numero," + 
			"		ide.subalterno as Subalterno," + 
			//"		'Stadio' as Stadio," + 
			"		ui.sezione as Sezione, " + 
			"		ui.categoria as Categoria," + 
			"		ui.classe as Classe,  	" + 
			"		ui.zona as Zona," + 
			//"		--uui.denominatore as Denominatore," + 
			"		0 as Denominatore," + 
			"		ui.partita as Partita," + 
			"		ui.superficie as Superficie," + 
			"		ui.rendita_euro as RenditaEuro" + 
			"	FROM public.u_cat_unita_immobiliare ui," + 
			"		public.u_cat_indirizzi_unita_immobiliari ind," + 
			"		public.u_cat_identificativi_unita_immobiliari ide " + 
			//"		--, public.u_cat_utilita_unita_immobiliare uui" + 
			"	WHERE ui.id_immobile = ind.id_immobile " + 
			"		and ui.id_immobile = ide.id_immobile " + 
			//"		--and ui.id_immobile = uui.id_immobile" + 
			"		and ui.id_immobile in ( " + 
			"			SELECT id_immobile " + 
			"			FROM public.u_cat_identificativi_unita_immobiliari " + 
			"			WHERE cod_comune in :lsCodComuni" + 
			"				and foglio in :lsFogli" + 
			"				and numero in :lsMappale" +
			"			)" + 
			"		and ui.tipo_immobile = 'F' ", nativeQuery = true)
	List<UnitaImmobiliareUrbana> exportUnitaImmobiliareUrbana(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT ui.id_immobile as Identificativo," + 
			"		ui.cod_comune as Comune," + 
			"		ide.foglio as Foglio," + 
			"		ide.numero as Numero," + 
			"		ide.subalterno as Subalterno," + 
			"		ui.sezione as Sezione, " + 
			"		ui.categoria as Categoria," + 
			"		ui.classe as Classe,  	" + 
			"		ui.zona as Zona," + 
			"		ide.denominatore as Denominatore," + 
			"		ui.partita as Partita," + 
			"		ui.superficie as Superficie," + 
			"		ui.rendita_euro as RenditaEuro" + 
			"	FROM public.u_cat_unita_immobiliare ui," + 
			"		public.u_cat_indirizzi_unita_immobiliari ind," + 
			"		public.u_cat_identificativi_unita_immobiliari ide " +
			"	WHERE ui.id_immobile = ind.id_immobile " + 
			"		and ui.id_immobile = ide.id_immobile " + 
			"       and ide.cod_comune = :codComune and ide.foglio = :foglio and ide.numero in :lsMappale" +
			"		and ui.tipo_immobile = 'F' ", nativeQuery = true)
	List<UnitaImmobiliareUrbana> exportFMUnitaImmobiliareUrbana(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT row_number() over () as id," + 
			"		ui.id_immobile as idImmobile," +
			"		ui.categoria, " + 
			"		ui.sezione, " + 
			"		ide.foglio,   " + 
			"		ide.numero,   " + 
			"		ide.subalterno, " + 
			"		ind.indirizzo, " + 
			"		ui.cod_comune as codComune, " + 
			"		ui.partita, " + 
			"		ui.rendita_lire as renditaLire,   " + 
			"		ui.rendita_euro as renditaEuro," + 
			"		dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as diritto " + 
			"		FROM 	public.u_cat_unita_immobiliare ui,   " + 
			"		public.u_cat_indirizzi_unita_immobiliari ind,   " + 
			"		public.u_cat_identificativi_unita_immobiliari ide ," + 
			"		public.u_cat_titolarita ti," + 
			"		public.u_cat_codici_diritto dr, " + 
			"		public.u_cat_soggetti so  " +
			"		WHERE 	ui.id_immobile = ind.id_immobile  " + 
			"		and ui.id_immobile = ide.id_immobile  " + 
			"		and ti.id_immobile = ui.id_immobile" + 
			"		and dr.codice = ti.codice_diritto" + 
			"		and ti.id_soggetto = so.id_soggetto" +
			"		and so.id_soggetto = :idSoggetto" +
			/*"		and ui.id_immobile in (  " + 
			"			SELECT id_immobile " + 
			"			FROM public.u_cat_titolarita " + 
			"			WHERE id_soggetto = :idSoggetto) " +*/ 
			"		and ui.tipo_immobile = 'F'  " + 
			"		and ti.id_mutaz_fin is null " +
			"		and ui.id_mutaz_fin is null " +
			"		group by ui.id_immobile," + 
			"		ui.categoria, " + 
			"		ui.sezione, " + 
			"		ide.foglio,   " + 
			"		ide.numero,   " + 
			"		ide.subalterno, " + 
			"		ind.indirizzo, " + 
			"		ui.cod_comune, " + 
			"		ui.partita, " + 
			"		ui.rendita_lire,   " + 
			"		ui.rendita_euro," + 
			"		dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore" + 
			"		order by foglio, numero, subalterno", nativeQuery = true)
	List<UnitaImmobiliareJoin> dettaglioSoggettiUIU(@Param("idSoggetto") Integer idSoggetto);

	@Query(value="SELECT ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita," + 
			"	ui.lotto," +
	        "	ui.edificio," +
	        "	ui.scala," +
	        "	ui.interno_1 as interno," +
	        "	ui.piano_1 as piano," +
	        "	ui.annotazione," +
	        "	ind.civico_3 as civico3 " +
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" +
			"	and ui.tipo_immobile = 'F' " +
			"	and (1=:flagComune or ui.cod_comune = :comune)" +
			"	and (1=:flagIndirizzo or ind.indirizzo like upper(:indirizzo))" +
			"	and (1=:flagZona or ui.zona = :zona)" +
			"	and (1=:flagSezione or ui.sezione = :sezione)" +
			"	and (1=:flagConsistenza or cast(ui.consistenza as text) = cast(:consistenza as text))" +
			"	and (1=:flagCategoria or ui.categoria = :categoria)" +
			"	and (1=:flagFoglio or ide.foglio = :foglio)" +
			"	and (1=:flagSuperficie or cast(ui.superficie as text) = cast(:superficie as text))" +
			"	and (1=:flagClasse or ui.classe = :classe)" +
			"	and (1=:flagNumero or ide.numero = :numero)" +
			"	and (1=:flagRenditaLire or cast(ui.rendita_lire as text) = cast(:renditaLire as text))" +
			"	and (1=:flagPartita or ui.partita = :partita)" +
			"	and (1=:flagSubalterno or ide.subalterno = :subalterno)" +
			"	and (1=:flagRenditaEuro or cast(ui.rendita_euro as text) = cast(:renditaEuro as text))" +
			"	and ui.id_mutaz_fin is null" +
			"	order by ide. foglio asc, ide.numero asc, ide.subalterno asc"
			, nativeQuery = true)
	Page<UnitaImmobiliareJoinRicerca> ricercaImmobiliUIU(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagIndirizzo") Integer flagIndirizzo, 
			@Param("indirizzo") String indirizzo, 
			@Param("flagZona") Integer flagZona,
			@Param("zona") String zona, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione, 
			@Param("flagConsistenza") Integer flagConsistenza, 
			@Param("consistenza") String consistenza, 
			@Param("flagCategoria") Integer flagCategoria, 
			@Param("categoria") String categoria,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie, 
			@Param("flagClasse") Integer flagClasse, 
			@Param("classe") String classe, 
			@Param("flagNumero") Integer flagNumero,
			@Param("numero") String numero, 
			@Param("flagRenditaLire") Integer flagRenditaLire, 
			@Param("renditaLire") String renditaLire, 
			@Param("flagPartita") Integer flagPartita, 
			@Param("partita") String partita, 
			@Param("flagSubalterno") Integer flagSubalterno,
			@Param("subalterno") String subalterno, 
			@Param("flagRenditaEuro") Integer flagRenditaEuro, 
			@Param("renditaEuro") String renditaEuro, 
			Pageable pageable);

	@Query(value="SELECT ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita," + 
			"	ui.lotto," +
	        "	ui.edificio," +
	        "	ui.scala," +
	        "	ui.interno_1 as interno," +
	        "	ui.piano_1 as piano," +
	        "	ui.annotazione," +
	        "	ind.civico_3 as civico3 " +
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" +
			"	and ui.tipo_immobile = 'F' " +
			"	and (1=:flagComune or ui.cod_comune = :comune)" +
			"	and (1=:flagIndirizzo or ind.indirizzo like upper(:indirizzo))" +
			"	and (1=:flagZona or ui.zona = :zona)" +
			"	and (1=:flagSezione or ui.sezione = :sezione)" +
			"	and (1=:flagConsistenza or cast(ui.consistenza as text) = cast(:consistenza as text))" +
			"	and (1=:flagCategoria or ui.categoria = :categoria)" +
			"	and (1=:flagFoglio or ide.foglio = :foglio)" +
			"	and (1=:flagSuperficie or cast(ui.superficie as text) = cast(:superficie as text))" +
			"	and (1=:flagClasse or ui.classe = :classe)" +
			"	and (1=:flagNumero or ide.numero = :numero)" +
			"	and (1=:flagRenditaLire or cast(ui.rendita_lire as text) = cast(:renditaLire as text))" +
			"	and (1=:flagPartita or ui.partita = :partita)" +
			"	and (1=:flagSubalterno or ide.subalterno = :subalterno)" +
			"	and (1=:flagRenditaEuro or cast(ui.rendita_euro as text) = cast(:renditaEuro as text))" +
			"	order by ide. foglio asc, ide.numero asc, ide.subalterno asc"
			, nativeQuery = true)
	Page<UnitaImmobiliareJoinRicerca> ricercaImmobiliUIUSoppresse(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagIndirizzo") Integer flagIndirizzo, 
			@Param("indirizzo") String indirizzo, 
			@Param("flagZona") Integer flagZona,
			@Param("zona") String zona, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione, 
			@Param("flagConsistenza") Integer flagConsistenza, 
			@Param("consistenza") String consistenza, 
			@Param("flagCategoria") Integer flagCategoria, 
			@Param("categoria") String categoria,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie, 
			@Param("flagClasse") Integer flagClasse, 
			@Param("classe") String classe, 
			@Param("flagNumero") Integer flagNumero,
			@Param("numero") String numero, 
			@Param("flagRenditaLire") Integer flagRenditaLire, 
			@Param("renditaLire") String renditaLire, 
			@Param("flagPartita") Integer flagPartita, 
			@Param("partita") String partita, 
			@Param("flagSubalterno") Integer flagSubalterno,
			@Param("subalterno") String subalterno, 
			@Param("flagRenditaEuro") Integer flagRenditaEuro, 
			@Param("renditaEuro") String renditaEuro, 
			Pageable pageable);

	@Query(value="SELECT distinct (so.codice_fiscale)," + 
			"	so.nome," + 
			"	so.cognome," + 
			"	ti.codice_diritto as codiceDiritto, " +
			"	cast(ui.data_efficacia_gen as text) as dataEfficaciaGen, " +
			"	cast(ti.data_registrazione_gen as text) as dataRegistrazioneGen, " +
			"	ti.tipo_nota_gen as tipoNotaGen, " +
			"	ti.num_nota_gen as numNotaGen,  " +
			"	ti.progr_nota_gen as progrNotaGen, " +
			"	ti.anno_nota_gen as annoNotaGen, " +
			"	ti.descrizione_atto_gen as descrizioneAttoGen, " +
			"	so.tipo_soggetto as tipoSoggetto " +
			"	FROM 	public.u_cat_unita_immobiliare ui," + 
			"	public.u_cat_titolarita ti," + 
			"	public.u_cat_soggetti so" + 
			"	WHERE	ui.id_immobile = ti.id_immobile" + 
			"	and	ti.id_soggetto = so.id_soggetto" + 
			"	and 	so.tipo_soggetto = 'P'" + 
			"	and	ui.id_immobile = :idImmobile" + 
			//"	order by cast(ui.data_efficacia_gen as text) desc, so.cognome, so.nome asc ", nativeQuery = true)
			"	order by cast(ti.data_registrazione_gen as text) desc, so.cognome, so.nome asc  ", nativeQuery = true)
	List<UnitaImmobileJoinDettaglio> dettaglioImmobiliUIUPersoneFisiche(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT distinct (so.codice_fiscale)," + 
			"	so.denominazione," + 
			"	ti.codice_diritto as codiceDiritto, " +
			"	cast(ui.data_efficacia_gen as text) as dataEfficaciaGen, " +
			"	cast(ti.data_registrazione_gen as text) as dataRegistrazioneGen, " +
			"	ti.tipo_nota_gen as tipoNotaGen, " +
			"	ti.num_nota_gen as numNotaGen,  " +
			"	ti.progr_nota_gen as progrNotaGen, " +
			"	ti.anno_nota_gen as annoNotaGen, " +
			"	ti.descrizione_atto_gen as descrizioneAttoGen, " +
			"	so.tipo_soggetto as tipoSoggetto " +
			"	FROM 	public.u_cat_unita_immobiliare ui," + 
			"	public.u_cat_titolarita ti," + 
			"	public.u_cat_soggetti so" + 
			"	WHERE	ui.id_immobile = ti.id_immobile" + 
			"	and	ti.id_soggetto = so.id_soggetto" + 
			"	and 	so.tipo_soggetto = 'G'" + 
			"	and	ui.id_immobile = :idImmobile" + 
			//"	order by cast(ui.data_efficacia_gen as text) desc, so.denominazione asc ", nativeQuery = true)
			"	order by cast(ti.data_registrazione_gen as text) desc, so.denominazione asc ", nativeQuery = true)
	List<UnitaImmobileJoinDettaglio> dettaglioImmobiliUIUSoggettiGiuridici(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT distinct so.codice_fiscale," + 
			"	so.nome," + 
			"	so.cognome," + 
			"	ti.codice_diritto as codiceDiritto, " +
			"	pt.data_efficacia_gen as dataEfficaciaGen, " +
			"	cast(ti.data_registrazione_gen as text) as dataRegistrazioneGen, " +
			"	ti.tipo_nota_gen as tipoNotaGen, " +
			"	ti.num_nota_gen as numNotaGen,  " +
			"	ti.progr_nota_gen as progrNotaGen, " +
			"	ti.anno_nota_gen as annoNotaGen, " +
			"	ti.descrizione_atto_gen as descrizioneAttoGen, " +
			"	so.tipo_soggetto as tipoSoggetto " +
			"	FROM 	public.u_cat_particelle_catastali pt," + 
			"	public.u_cat_titolarita ti," + 
			"	public.u_cat_soggetti so" + 
			"	WHERE	pt.id_immobile = ti.id_immobile" + 
			"	and	ti.id_soggetto = so.id_soggetto" + 
			"	and 	so.tipo_soggetto = 'P'" + 
			"	and	pt.id_immobile = :idImmobile" + 
			//"	order by pt.data_efficacia_gen desc, so.cognome, so.nome asc ", nativeQuery = true)
			"	order by cast(ti.data_registrazione_gen as text) desc, so.cognome, so.nome asc  ", nativeQuery = true)
	List<UnitaImmobileJoinDettaglio> dettaglioParticellePTPersoneFisiche(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT distinct so.codice_fiscale," + 
			"	so.denominazione," + 
			"	ti.codice_diritto as codiceDiritto, " +
			"	pt.data_efficacia_gen as dataEfficaciaGen, " +
			"	cast(ti.data_registrazione_gen as text) as dataRegistrazioneGen, " +
			"	ti.tipo_nota_gen as tipoNotaGen, " +
			"	ti.num_nota_gen as numNotaGen,  " +
			"	ti.progr_nota_gen as progrNotaGen, " +
			"	ti.anno_nota_gen as annoNotaGen, " +
			"	ti.descrizione_atto_gen as descrizioneAttoGen, " +
			"	so.tipo_soggetto as tipoSoggetto " +
			"	FROM 	public.u_cat_particelle_catastali pt," + 
			"	public.u_cat_titolarita ti," + 
			"	public.u_cat_soggetti so" + 
			"	WHERE	pt.id_immobile = ti.id_immobile" + 
			"	and	ti.id_soggetto = so.id_soggetto" + 
			"	and 	so.tipo_soggetto = 'G'" + 
			"	and	pt.id_immobile = :idImmobile" + 
			//"	order by pt.data_efficacia_gen desc, so.denominazione asc ", nativeQuery = true)
			"	order by cast(ti.data_registrazione_gen as text) desc, so.denominazione asc ", nativeQuery = true)
	List<UnitaImmobileJoinDettaglio> dettaglioParticellePTSoggettiGiuridici(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita," + 
			"	ui.lotto," +
	        "	ui.edificio," +
	        "	ui.scala," +
	        "	ui.interno_1 as interno," +
	        "	ui.piano_1 as piano," +
	        "	ui.annotazione," +
	        "	ind.civico_3 as civico3 " +
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" +
			"	and ui.tipo_immobile = 'F' " +
			"	and (1=:flagComune or ui.cod_comune = :comune)" +
			"	and (1=:flagIndirizzo or ind.indirizzo like upper(:indirizzo))" +
			"	and (1=:flagZona or ui.zona = :zona)" +
			"	and (1=:flagSezione or ui.sezione = :sezione)" +
			"	and (1=:flagConsistenza or cast(ui.consistenza as text) = cast(:consistenza as text))" +
			"	and (1=:flagCategoria or ui.categoria = :categoria)" +
			"	and (1=:flagFoglio or ide.foglio = :foglio)" +
			"	and (1=:flagSuperficie or cast(ui.superficie as text) = cast(:superficie as text))" +
			"	and (1=:flagClasse or ui.classe = :classe)" +
			"	and (1=:flagNumero or ide.numero = :numero)" +
			"	and (1=:flagRenditaLire or cast(ui.rendita_lire as text) = cast(:renditaLire as text))" +
			"	and (1=:flagPartita or ui.partita = :partita)" +
			"	and (1=:flagSubalterno or ide.subalterno = :subalterno)" +
			"	and (1=:flagRenditaEuro or cast(ui.rendita_euro as text) = cast(:renditaEuro as text))" +
			"	and ui.id_mutaz_fin is null" +
			"	order by ide. foglio asc, ide.numero asc, ide.subalterno asc"
			, nativeQuery = true)
	List<UnitaImmobiliareJoinRicerca> exportImmobiliUIU(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagIndirizzo") Integer flagIndirizzo, 
			@Param("indirizzo") String indirizzo, 
			@Param("flagZona") Integer flagZona,
			@Param("zona") String zona, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione, 
			@Param("flagConsistenza") Integer flagConsistenza, 
			@Param("consistenza") String consistenza, 
			@Param("flagCategoria") Integer flagCategoria, 
			@Param("categoria") String categoria,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie, 
			@Param("flagClasse") Integer flagClasse, 
			@Param("classe") String classe, 
			@Param("flagNumero") Integer flagNumero,
			@Param("numero") String numero, 
			@Param("flagRenditaLire") Integer flagRenditaLire, 
			@Param("renditaLire") String renditaLire, 
			@Param("flagPartita") Integer flagPartita, 
			@Param("partita") String partita, 
			@Param("flagSubalterno") Integer flagSubalterno,
			@Param("subalterno") String subalterno, 
			@Param("flagRenditaEuro") Integer flagRenditaEuro, 
			@Param("renditaEuro") String renditaEuro);

	@Query(value="SELECT ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita," + 
			"	ui.lotto," +
	        "	ui.edificio," +
	        "	ui.scala," +
	        "	ui.interno_1 as interno," +
	        "	ui.piano_1 as piano," +
	        "	ui.annotazione," +
	        "	ind.civico_3 as civico3 " +
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" +
			"	and ui.tipo_immobile = 'F' " +
			"	and (1=:flagComune or ui.cod_comune = :comune)" +
			"	and (1=:flagIndirizzo or ind.indirizzo like upper(:indirizzo))" +
			"	and (1=:flagZona or ui.zona = :zona)" +
			"	and (1=:flagSezione or ui.sezione = :sezione)" +
			"	and (1=:flagConsistenza or cast(ui.consistenza as text) = cast(:consistenza as text))" +
			"	and (1=:flagCategoria or ui.categoria = :categoria)" +
			"	and (1=:flagFoglio or ide.foglio = :foglio)" +
			"	and (1=:flagSuperficie or cast(ui.superficie as text) = cast(:superficie as text))" +
			"	and (1=:flagClasse or ui.classe = :classe)" +
			"	and (1=:flagNumero or ide.numero = :numero)" +
			"	and (1=:flagRenditaLire or cast(ui.rendita_lire as text) = cast(:renditaLire as text))" +
			"	and (1=:flagPartita or ui.partita = :partita)" +
			"	and (1=:flagSubalterno or ide.subalterno = :subalterno)" +
			"	and (1=:flagRenditaEuro or cast(ui.rendita_euro as text) = cast(:renditaEuro as text))" +
			"	order by ide. foglio asc, ide.numero asc, ide.subalterno asc"
			, nativeQuery = true)
	List<UnitaImmobiliareJoinRicerca> exportImmobiliUIUSoppresse(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagIndirizzo") Integer flagIndirizzo, 
			@Param("indirizzo") String indirizzo, 
			@Param("flagZona") Integer flagZona,
			@Param("zona") String zona, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione, 
			@Param("flagConsistenza") Integer flagConsistenza, 
			@Param("consistenza") String consistenza, 
			@Param("flagCategoria") Integer flagCategoria, 
			@Param("categoria") String categoria,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie, 
			@Param("flagClasse") Integer flagClasse, 
			@Param("classe") String classe, 
			@Param("flagNumero") Integer flagNumero,
			@Param("numero") String numero, 
			@Param("flagRenditaLire") Integer flagRenditaLire, 
			@Param("renditaLire") String renditaLire, 
			@Param("flagPartita") Integer flagPartita, 
			@Param("partita") String partita, 
			@Param("flagSubalterno") Integer flagSubalterno,
			@Param("subalterno") String subalterno, 
			@Param("flagRenditaEuro") Integer flagRenditaEuro, 
			@Param("renditaEuro") String renditaEuro);

	@Query(value="SELECT ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita," + 
			"	ui.lotto," +
	        "	ui.edificio," +
	        "	ui.scala," +
	        "	ui.interno_1 as interno," +
	        "	ui.piano_1 as piano," +
	        "	ui.annotazione," +
	        "	ind.civico_3 as civico3 " +
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" +
			"	and ui.tipo_immobile = 'F' " +
			"	and ui.id_immobile = :idImmobile"
			, nativeQuery = true)
	UnitaImmobiliareJoinRicerca findByIdImmobile(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT ui.id, " + 
			"	ui.id_immobile as idImmobile, " + 
			"	ui.cod_comune as codComune, " + 
			"	ui.zona, " + 
			"	ui.categoria, " + 
			"	ui.classe, " + 
			"	ind.indirizzo, " + 
			"	ind.civico_1 as civico1, " + 
			"	ind.civico_2 as civico2, " + 
			"	ui.sezione, " +
			"	ide.foglio, " + 
			"	ide.numero, " + 
			"	ide.subalterno, " + 
			"	ui.consistenza, " + 
			"	ui.superficie, " + 
			"	ui.rendita_lire as renditaLire, " + 
			"	ui.rendita_euro as renditaEuro, " + 
			"	ui.partita," + 
			"	ui.lotto," +
	        "	ui.edificio," +
	        "	ui.scala," +
	        "	ui.interno_1 as interno," +
	        "	ui.piano_1 as piano," +
	        "	ui.annotazione," +
	        "	ind.civico_3 as civico3, " +
	        "	ui.descrizione_atto_gen as descrizioneAttoGen, " +
	        "	ui.data_registrazione_gen as dataRegistrazioneGen " +
			"	FROM 	public.u_cat_unita_immobiliare ui, " + 
			"	public.u_cat_indirizzi_unita_immobiliari ind, " + 
			"	public.u_cat_identificativi_unita_immobiliari ide" + 
			"	WHERE 	ui.id_immobile = ind.id_immobile" + 
			"	and ui.id_immobile = ide.id_immobile" +
			"	and ui.tipo_immobile = 'F' " +
			"	and ui.id_immobile = :idImmobile" +
			" 	order by ui.data_registrazione_gen desc" + 
			"	limit 1"
			, nativeQuery = true)
	UnitaImmobiliareJoinRicerca findByIdImmobileVisura(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT 	ui.id_immobile as idImmobile," + 
			"	ui.cod_comune as codComune," + 
			"	ui.zona," + 
			"	ui.categoria," + 
			"	ui.classe," + 
			"	ind.indirizzo," + 
			"	ind.civico_1 as civico1," + 
			"	ind.civico_2 as civico2," + 
			"	ui.sezione, " + 
			"	ide.foglio," + 
			"	ide.numero," + 
			"	ide.subalterno," + 
			"	ui.consistenza," + 
			"	ui.superficie," + 
			"	ui.rendita_lire as renditaLire," + 
			"	ui.rendita_euro as renditaEuro," + 
			"	ui.partita, " + 
			"	ui.lotto," + 
			"	ui.edificio," + 
			"	ui.scala," + 
			"	ui.interno_1 as interno," + 
			"	ui.piano_1 as piano," + 
			"	ui.annotazione," + 
			"	ind.civico_3 as civico3, " + 
			"	ui.descrizione_atto_gen as descrizioneAttoGen, " + 
			"	ui.data_efficacia_gen as dataEfficaciaGen " + 
			"	FROM 	public.u_cat_unita_immobiliare ui," + 
			"	public.u_cat_indirizzi_unita_immobiliari ind," + 
			"	public.u_cat_identificativi_unita_immobiliari ide " + 
			"	WHERE 	ui.id_immobile = ind.id_immobile " + 
			"	and ui.id_immobile = ide.id_immobile" + 
			"	and ui.tipo_immobile = 'F' " + 
			"	and ui.id_immobile = :idImmobile" + 
			"	group by" + 
			"	ui.id_immobile," + 
			"	ui.cod_comune," + 
			"	ui.zona," + 
			"	ui.categoria," + 
			"	ui.classe," + 
			"	ind.indirizzo," + 
			"	ind.civico_1," + 
			"	ind.civico_2," + 
			"	ui.sezione, " + 
			"	ide.foglio," + 
			"	ide.numero," + 
			"	ide.subalterno," + 
			"	ui.consistenza," + 
			"	ui.superficie," + 
			"	ui.rendita_lire," + 
			"	ui.rendita_euro," + 
			"	ui.partita, " + 
			"	ui.lotto," + 
			"	ui.edificio," + 
			"	ui.scala," + 
			"	ui.interno_1," + 
			"	ui.piano_1," + 
			"	ui.annotazione," + 
			"	ind.civico_3, " + 
			"	ui.descrizione_atto_gen, " + 
			"	ui.data_efficacia_gen "
			, nativeQuery = true)
	List<UnitaImmobiliareJoinRicerca> findByIdImmobileVisuraStorica(@Param("idImmobile") Long idImmobile);

}
