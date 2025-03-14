package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.Particella;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCatastaleJoin;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaVisuraStorica;
import it.eng.tz.urbamid.catasto.persistence.model.Particellaterreni;

public interface JpaRepositoryParticella extends PagingAndSortingRepository<Particella, Long>, JpaSpecificationExecutor<Particella> {

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " + 
			"  WHERE cod_comune in :lsCodComuni" + 
			"		and foglio in :lsFogli" + 
			"		and numero in :lsMappale" +
			"		and id_mutaz_fin is null" +
			"		group by id, foglio, numero"
			/*"id_immobile in(SELECT id_immobile" + 
			"			FROM public.u_cat_identificativi_unita_immobiliari" + 
			"			WHERE cod_comune in :lsCodComuni" + 
			"			and foglio in :lsFogli" + 
			"			and numero in :lsMappale)"*/ , nativeQuery = true)
	List<Particella> ricercaSuParticellePT(
			 @Param("lsCodComuni") List<String> lsCodComuni,
			 @Param("lsFogli") List<String> lsFogli, 
			 @Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " + 
			"  WHERE cod_comune = :codComune" + 
			"		and foglio = :foglio" + 
			"		and numero in :lsMappale" +
			"		and id_mutaz_fin is null" +
			"		group by id, foglio, numero"
			, nativeQuery = true)
	List<Particella> ricercaFMSuParticellePT(
			 @Param("codComune") String codComune,
			 @Param("foglio") String foglio, 
			 @Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT id as Identificativo," + 
			"		cod_comune as Comune," + 
			//"		'Ute' as Ute," + 
			"		foglio as Foglio," + 
			"		numero as Numero," + 
			"		subalterno as Subalterno," + 
			//"		'Stadio' as Stadio," + 
			"		denominatore as Denominatore," + 
			"		partita as Partita," + 
			"		cq.descrizione as Qualita," + 
			"		classe as Classe," + 
			"		ettari as Ettari," + 
			"		are as Are," + 
			"		centiare as Centiare," + 
			"		reddito_dominicale_euro as RedditoDominicaleEuro," + 
			"		reddito_agrario_euro as RedditoAgrarioEuro" + 
			"	FROM public.u_cat_particelle_catastali pc," + 
			"		public.u_cat_codici_qualita cq" + 
			"	WHERE pc.qualita = cq.codice" + 
			"		and cod_comune in :lsCodComuni" + 
			"		and foglio in :lsFogli" + 
			"		and numero in :lsMappale"
			/*"		and id_immobile in(" +
			"							SELECT id_immobile" + 
			"							FROM public.u_cat_identificativi_unita_immobiliari" + 
			"							WHERE cod_comune in :lsCodComuni" + 
			"							and foglio in :lsFogli" + 
			"							and numero in :lsMappale)"*/ , nativeQuery = true)
	List<Particellaterreni> exportParticellaterreni(
			 @Param("lsCodComuni") List<String> lsCodComuni,
			 @Param("lsFogli") List<String> lsFogli, 
			 @Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT pt.id_immobile as Identificativo," + 
			"		ucc.denominazione as Comune," + 
			"		pt.foglio as Foglio," + 
			"		pt.numero as Numero," + 
			"		pt.subalterno as Subalterno," + 
			"		pt.denominatore as Denominatore," + 
			"		pt.partita as Partita," + 
			"		uccq.descrizione as Qualita," + 
			"		pt.classe as Classe," + 
			"		pt.ettari as Ettari," + 
			"		pt.are as Are," + 
			"		pt.centiare as Centiare," + 
			"		pt.reddito_dominicale_euro as RedditoDominicaleEuro," + 
			"		pt.reddito_agrario_euro as RedditoAgrarioEuro" + 
			"	FROM u_cat_particelle_catastali pt, u_cat_comuni ucc, u_cat_codici_qualita uccq" + 
			"	WHERE pt.cod_comune = ucc.codice_mf AND " +
			"		  pt.qualita = uccq.codice AND" + 
			"		  pt.cod_comune = :codComune AND" + 
			"		  pt.foglio = :foglio AND" + 
			"		  pt.numero in :lsMappale", nativeQuery = true)
	List<Particellaterreni> exportParticellaterreni(
			 @Param("codComune") String codComune,
			 @Param("foglio") String foglio, 
			 @Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT ST_Area(geom) * 0.3048 ^ 2 sqm" + 
			"  FROM public.u_cat_particelle" + 
			"  WHERE foglio = :foglio and mappale = :numero" , nativeQuery = true)
	Double findSuperficieGeometrica(
			@Param("foglio") String foglio, 
			@Param("numero") String numero);

	/*@Query(value="SELECT * " + 
				"FROM public.u_cat_particelle_catastali pc " + 
				"WHERE id_immobile in (" + 
				"	SELECT id_immobile" + 
				"	FROM public.u_cat_titolarita" + 
				"	WHERE id_soggetto = :idSoggetto" + 
				"	) " +
				"order by foglio, numero, subalterno", nativeQuery = true)*/
	@Query(value="SELECT pc.id," +
			"	pc.id_immobile as idImmobile," + 
			"	pc.sezione," + 
			"	pc.foglio," + 
			"	pc.numero," + 
			"	pc.subalterno," + 
			"	pc.cod_comune as comune," + 
			"	dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as diritto," + 
			"	pc.partita" + 
			"	FROM public.u_cat_particelle_catastali pc," + 
			"	public.u_cat_titolarita ti," + 
			"	public.u_cat_codici_diritto dr," + 
			"	public.u_cat_soggetti so" +
			"	WHERE pc.id_immobile = ti.id_immobile" + 
			"	and dr.codice = ti.codice_diritto" + 
			"	and ti.id_soggetto = so.id_soggetto" +
			"	and so.id_soggetto = :idSoggetto" +
			/*"	and pc.id_immobile in ( " + 
			"		SELECT distinct id_immobile " + 
			"		FROM public.u_cat_titolarita " + 
			"		WHERE id_soggetto = :idSoggetto " + 
			"		)" + */
			"	and pc.tipo_immobile = 'T'" + 
			"	and pc.id_mutaz_fin is null" + 
			"	and ti.id_mutaz_fin is null" + 
			"	and ti.id_soggetto = :idSoggetto" + 
			"	group by" + 
			"	pc.id," +
			"	pc.id_immobile," + 
			"	pc.sezione," + 
			"	pc.foglio," + 
			"	pc.numero," + 
			"	pc.subalterno," + 
			"	pc.cod_comune," + 
			"	dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore," + 
			"	pc.partita" + 
			"	order by foglio, numero, subalterno", nativeQuery = true)
	//List<Particella> dettaglioSoggettiPT(@Param("idSoggetto") Integer idSoggetto);
	List<ParticellaCatastaleJoin> dettaglioSoggettiPT(@Param("idSoggetto") Integer idSoggetto);

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " + 
			"  WHERE (1=:flagComune or cod_comune = :comune)" +
			"		and (1=:flagSezione or sezione = :sezione)" +
			"		and (1=:flagFoglio or foglio = :foglio)" +
			"		and (1=:flagNumero or numero = :numero)" +
			"		and (1=:flagSubalterno or subalterno = :subalterno)" +
			"		and (1=:flagPartita or partita = :partita)" +
			"		and (1=:flagRedditoDominicaleEuro or cast(reddito_dominicale_euro as text) = :redditoDominicaleEuro)" +
			"		and (1=:flagRedditoDominicaleLire or cast(reddito_dominicale_lire as text) = :redditoDominicaleLire)" +
			"		and (1=:flagRedditoAgrarioEuro or cast(reddito_agrario_euro as text) = :redditoAgrarioEuro)" +
			"		and (1=:flagRedditoAgrarioLire or cast(reddito_agrario_lire as text) = :redditoAgrarioLire)" +
			"		and (1=:flagSuperficie or cast(ettari as text) = :superficie)" +
			"		group by id, foglio, numero" +
			"		order by foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<Particella> ricercaParticellePTSoppresse(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagNumero") Integer flagNumero, 
			@Param("numero") String numero, 
			@Param("flagSubalterno") Integer flagSubalterno, 
			@Param("subalterno") String subalterno, 
			@Param("flagPartita") Integer flagPartita,
			@Param("partita") String partita, 
			@Param("flagRedditoDominicaleEuro") Integer flagRedditoDominicaleEuro, 
			@Param("redditoDominicaleEuro") String redditoDominicaleEuro, 
			@Param("flagRedditoDominicaleLire") Integer flagRedditoDominicaleLire, 
			@Param("redditoDominicaleLire") String redditoDominicaleLire, 
			@Param("flagRedditoAgrarioEuro") Integer flagRedditoAgrarioEuro, 
			@Param("redditoAgrarioEuro") String redditoAgrarioEuro,
			@Param("flagRedditoAgrarioLire") Integer flagRedditoAgrarioLire, 
			@Param("redditoAgrarioLire") String redditoAgrarioLire, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie, 
			Pageable of);

	/*@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " + 
			"  WHERE (1=:flagComune or cod_comune = :comune)" +
			"		and (1=:flagSezione or sezione = :sezione)" +
			"		and (1=:flagFoglio or foglio = :foglio)" +
			"		and (1=:flagNumero or numero = :numero)" +
			"		and (1=:flagSubalterno or subalterno = :subalterno)" +
			"		and (1=:flagPartita or partita = :partita)" +
			"		and (1=:flagRedditoDominicaleEuro or cast(reddito_dominicale_euro as text) = :redditoDominicaleEuro)" +
			"		and (1=:flagRedditoDominicaleLire or cast(reddito_dominicale_lire as text) = :redditoDominicaleLire)" +
			"		and (1=:flagRedditoAgrarioEuro or cast(reddito_agrario_euro as text) = :redditoAgrarioEuro)" +
			"		and (1=:flagRedditoAgrarioLire or cast(reddito_agrario_lire as text) = :redditoAgrarioLire)" +
			"		and (1=:flagSuperficie or cast(ettari as text) = :superficie)" +
			"		and id_mutaz_fin is null" +
			"		group by id, foglio, numero" +
			"		order by foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)*/
	@Query(value="SELECT" + 
			"       id_immobile as id, cod_comune, sezione, classe, tipo_nota_gen, numero_nota_gen, " + 
			"       progressivo_nota_gen, tipo_nota_concl, numero_nota_concl, progressivo_nota_concl, " + 
			"       partita, foglio, numero, subalterno, edificabilita, tipo_immobile, " + 
			"       flag_reddito, flag_porzione, flag_deduzioni, annotazione, codice_causale_atto_gen, " + 
			"       descrizione_atto_gen, codice_causale_atto_concl, descrizione_atto_concl, " + 
			"       id_immobile, progressivo, anno_nota_gen, anno_nota_concl, id_mutaz_in, " + 
			"       id_mutaz_fin, qualita, ettari, are, reddito_dominicale_lire, " + 
			"       reddito_agrario_lire, reddito_dominicale_euro, reddito_agrario_euro, " + 
			"       centiare, data_efficacia_gen, data_registrazione_gen, data_efficacia_concl, " + 
			"       data_registrazione_concl, denominatore  " + 
			"    FROM" + 
			"        public.u_cat_particelle_catastali p   " + 
			"    WHERE" + 
			"		(1=:flagComune or cod_comune = :comune)" +
			"		and (1=:flagSezione or sezione = :sezione)" +
			"		and (1=:flagFoglio or foglio = :foglio)" +
			"		and (1=:flagNumero or numero = :numero)" +
			"		and (1=:flagSubalterno or subalterno = :subalterno)" +
			"		and (1=:flagPartita or partita = :partita)" +
			"		and (1=:flagRedditoDominicaleEuro or cast(reddito_dominicale_euro as text) = :redditoDominicaleEuro)" +
			"		and (1=:flagRedditoDominicaleLire or cast(reddito_dominicale_lire as text) = :redditoDominicaleLire)" +
			"		and (1=:flagRedditoAgrarioEuro or cast(reddito_agrario_euro as text) = :redditoAgrarioEuro)" +
			"		and (1=:flagRedditoAgrarioLire or cast(reddito_agrario_lire as text) = :redditoAgrarioLire)" +
			"		and (1=:flagSuperficie or cast(ettari as text) = :superficie) " +
			"       and id_mutaz_fin is null  " + 
			"    group by" + 
			"       id_immobile, cod_comune, sezione, classe, tipo_nota_gen, numero_nota_gen, " + 
			"       progressivo_nota_gen, tipo_nota_concl, numero_nota_concl, progressivo_nota_concl, " + 
			"       partita, foglio, numero, subalterno, edificabilita, tipo_immobile, " + 
			"       flag_reddito, flag_porzione, flag_deduzioni, annotazione, codice_causale_atto_gen, " + 
			"       descrizione_atto_gen, codice_causale_atto_concl, descrizione_atto_concl, " + 
			"       id_immobile, progressivo, anno_nota_gen, anno_nota_concl, id_mutaz_in, " + 
			"       id_mutaz_fin, qualita, ettari, are, reddito_dominicale_lire, " + 
			"       reddito_agrario_lire, reddito_dominicale_euro, reddito_agrario_euro, " + 
			"       centiare, data_efficacia_gen, data_registrazione_gen, data_efficacia_concl, " + 
			"       data_registrazione_concl, denominatore  " + 
			"    order by" + 
			"        foglio asc," + 
			"        numero asc," + 
			"        subalterno asc"
			, nativeQuery = true)
	Page<Particella> ricercaParticellePT(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagNumero") Integer flagNumero, 
			@Param("numero") String numero, 
			@Param("flagSubalterno") Integer flagSubalterno, 
			@Param("subalterno") String subalterno, 
			@Param("flagPartita") Integer flagPartita,
			@Param("partita") String partita, 
			@Param("flagRedditoDominicaleEuro") Integer flagRedditoDominicaleEuro, 
			@Param("redditoDominicaleEuro") String redditoDominicaleEuro, 
			@Param("flagRedditoDominicaleLire") Integer flagRedditoDominicaleLire, 
			@Param("redditoDominicaleLire") String redditoDominicaleLire, 
			@Param("flagRedditoAgrarioEuro") Integer flagRedditoAgrarioEuro, 
			@Param("redditoAgrarioEuro") String redditoAgrarioEuro,
			@Param("flagRedditoAgrarioLire") Integer flagRedditoAgrarioLire, 
			@Param("redditoAgrarioLire") String redditoAgrarioLire, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie,
			Pageable of);

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " + 
			"  WHERE (1=:flagComune or cod_comune = :comune)" +
			"		and (1=:flagComune or cod_comune = :comune)" +
			"		and (1=:flagSezione or sezione = :sezione)" +
			"		and (1=:flagFoglio or foglio = :foglio)" +
			"		and (1=:flagNumero or numero = :numero)" +
			"		and (1=:flagSubalterno or subalterno = :subalterno)" +
			"		and (1=:flagPartita or partita = :partita)" +
			"		and (1=:flagRedditoDominicaleEuro or cast(reddito_dominicale_euro as text) = :redditoDominicaleEuro)" +
			"		and (1=:flagRedditoDominicaleLire or cast(reddito_dominicale_lire as text) = :redditoDominicaleLire)" +
			"		and (1=:flagRedditoAgrarioEuro or cast(reddito_agrario_euro as text) = :redditoAgrarioEuro)" +
			"		and (1=:flagRedditoAgrarioLire or cast(reddito_agrario_lire as text) = :redditoAgrarioLire)" +
			"		and (1=:flagSuperficie or cast(ettari as text) = :superficie)" +
			"		group by id, foglio, numero" +
			"		order by foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<Particella> exportRicercaParticellePTSoppresse(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagNumero") Integer flagNumero, 
			@Param("numero") String numero, 
			@Param("flagSubalterno") Integer flagSubalterno, 
			@Param("subalterno") String subalterno, 
			@Param("flagPartita") Integer flagPartita,
			@Param("partita") String partita, 
			@Param("flagRedditoDominicaleEuro") Integer flagRedditoDominicaleEuro, 
			@Param("redditoDominicaleEuro") String redditoDominicaleEuro, 
			@Param("flagRedditoDominicaleLire") Integer flagRedditoDominicaleLire, 
			@Param("redditoDominicaleLire") String redditoDominicaleLire, 
			@Param("flagRedditoAgrarioEuro") Integer flagRedditoAgrarioEuro, 
			@Param("redditoAgrarioEuro") String redditoAgrarioEuro,
			@Param("flagRedditoAgrarioLire") Integer flagRedditoAgrarioLire, 
			@Param("redditoAgrarioLire") String redditoAgrarioLire, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie);

	@Query(value="SELECT" + 
			"       id_immobile as id, cod_comune, sezione, classe, tipo_nota_gen, numero_nota_gen, " + 
			"       progressivo_nota_gen, tipo_nota_concl, numero_nota_concl, progressivo_nota_concl, " + 
			"       partita, foglio, numero, subalterno, edificabilita, tipo_immobile, " + 
			"       flag_reddito, flag_porzione, flag_deduzioni, annotazione, codice_causale_atto_gen, " + 
			"       descrizione_atto_gen, codice_causale_atto_concl, descrizione_atto_concl, " + 
			"       id_immobile, progressivo, anno_nota_gen, anno_nota_concl, id_mutaz_in, " + 
			"       id_mutaz_fin, qualita, ettari, are, reddito_dominicale_lire, " + 
			"       reddito_agrario_lire, reddito_dominicale_euro, reddito_agrario_euro, " + 
			"       centiare, data_efficacia_gen, data_registrazione_gen, data_efficacia_concl, " + 
			"       data_registrazione_concl, denominatore  " + 
			"    FROM" + 
			"        public.u_cat_particelle_catastali p   " + 
			"    WHERE" + 
			"		(1=:flagComune or cod_comune = :comune)" +
			"		and (1=:flagSezione or sezione = :sezione)" +
			"		and (1=:flagFoglio or foglio = :foglio)" +
			"		and (1=:flagNumero or numero = :numero)" +
			"		and (1=:flagSubalterno or subalterno = :subalterno)" +
			"		and (1=:flagPartita or partita = :partita)" +
			"		and (1=:flagRedditoDominicaleEuro or cast(reddito_dominicale_euro as text) = :redditoDominicaleEuro)" +
			"		and (1=:flagRedditoDominicaleLire or cast(reddito_dominicale_lire as text) = :redditoDominicaleLire)" +
			"		and (1=:flagRedditoAgrarioEuro or cast(reddito_agrario_euro as text) = :redditoAgrarioEuro)" +
			"		and (1=:flagRedditoAgrarioLire or cast(reddito_agrario_lire as text) = :redditoAgrarioLire)" +
			"		and (1=:flagSuperficie or cast(ettari as text) = :superficie) " +
			"       and id_mutaz_fin is null  " + 
			"    group by" + 
			"       id_immobile, cod_comune, sezione, classe, tipo_nota_gen, numero_nota_gen, " + 
			"       progressivo_nota_gen, tipo_nota_concl, numero_nota_concl, progressivo_nota_concl, " + 
			"       partita, foglio, numero, subalterno, edificabilita, tipo_immobile, " + 
			"       flag_reddito, flag_porzione, flag_deduzioni, annotazione, codice_causale_atto_gen, " + 
			"       descrizione_atto_gen, codice_causale_atto_concl, descrizione_atto_concl, " + 
			"       id_immobile, progressivo, anno_nota_gen, anno_nota_concl, id_mutaz_in, " + 
			"       id_mutaz_fin, qualita, ettari, are, reddito_dominicale_lire, " + 
			"       reddito_agrario_lire, reddito_dominicale_euro, reddito_agrario_euro, " + 
			"       centiare, data_efficacia_gen, data_registrazione_gen, data_efficacia_concl, " + 
			"       data_registrazione_concl, denominatore  " + 
			"    order by" + 
			"        foglio asc," + 
			"        numero asc," + 
			"        subalterno asc"
			, nativeQuery = true)
	List<Particella> exportRicercaParticellePT(
			@Param("flagComune") Integer flagComune, 
			@Param("comune") String comune, 
			@Param("flagSezione") Integer flagSezione, 
			@Param("sezione") String sezione,
			@Param("flagFoglio") Integer flagFoglio, 
			@Param("foglio") String foglio, 
			@Param("flagNumero") Integer flagNumero, 
			@Param("numero") String numero, 
			@Param("flagSubalterno") Integer flagSubalterno, 
			@Param("subalterno") String subalterno, 
			@Param("flagPartita") Integer flagPartita,
			@Param("partita") String partita, 
			@Param("flagRedditoDominicaleEuro") Integer flagRedditoDominicaleEuro, 
			@Param("redditoDominicaleEuro") String redditoDominicaleEuro, 
			@Param("flagRedditoDominicaleLire") Integer flagRedditoDominicaleLire, 
			@Param("redditoDominicaleLire") String redditoDominicaleLire, 
			@Param("flagRedditoAgrarioEuro") Integer flagRedditoAgrarioEuro, 
			@Param("redditoAgrarioEuro") String redditoAgrarioEuro,
			@Param("flagRedditoAgrarioLire") Integer flagRedditoAgrarioLire, 
			@Param("redditoAgrarioLire") String redditoAgrarioLire, 
			@Param("flagSuperficie") Integer flagSuperficie, 
			@Param("superficie") String superficie);

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " +
			"  WHERE id_immobile = :idImmobile", nativeQuery = true)
	Particella findByIdImmobile(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT * " + 
			"  FROM public.u_cat_particelle_catastali p " +
			"  WHERE id_immobile = :idImmobile" +
			"  order by data_efficacia_gen" + 
			"  limit 1", nativeQuery = true)
	Particella findByIdImmobileVisuraCatastale(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT cod_comune as codComune, " + 
			"	sezione, " + 
			"	classe, " + 
			"	tipo_nota_gen as tipoNotaGen, " + 
			"	numero_nota_gen as numeroNotaGen,  " + 
			"	progressivo_nota_gen as progressivoNotaGen, " + 
			"	tipo_nota_concl as tipoNotaConcl, " + 
			"	numero_nota_concl as numeroNotaConcl, " + 
			"	progressivo_nota_concl as progressivoNotaConcl,  " + 
			"	partita, " + 
			"	foglio, " + 
			"	numero, " + 
			"	subalterno, " + 
			"	edificabilita, " + 
			"	tipo_immobile as tipoImmobile,  " + 
			"	flag_reddito as flagReddito, " + 
			"	flag_porzione as flagPorzione, " + 
			"	flag_deduzioni as flagDeduzioni, " + 
			"	annotazione, " + 
			"	codice_causale_atto_gen as codiceCausaleAttoGen,  " + 
			"	descrizione_atto_gen as descrizioneAttoGen, " + 
			"	codice_causale_atto_concl as codiceCausaleAttoConcl, " + 
			"	descrizione_atto_concl as descrizioneAttoConcl,  " + 
			"	id_immobile as idImmobile, " + 
			"	progressivo, " + 
			"	anno_nota_gen as annoNotaGen, " + 
			"	anno_nota_concl as annoNotaConcl, " + 
			"	id_mutaz_in as idMutazIn,  " + 
			"	id_mutaz_fin as idMutazFin, " + 
			"	qualita, " + 
			"	ettari, " + 
			"	are, " + 
			"	reddito_dominicale_lire as redditoDominicaleLire,  " + 
			"	reddito_agrario_lire as redditoAgrarioLire, " + 
			"	reddito_dominicale_euro as redditoDominicaleEuro, " + 
			"	reddito_agrario_euro as redditoAgrarioEuro,  " + 
			"	centiare, " + 
			"	data_efficacia_gen as dataEfficaciaGen, " + 
			"	data_registrazione_gen as dataRegistrazioneGen, " + 
			"	data_efficacia_concl as dataEfficaciaConcl,  " + 
			"	data_registrazione_concl as dataRegistrazioneConcl, " + 
			"	denominatore  " + 
			"	  FROM public.u_cat_particelle_catastali p " + 
			"	  WHERE id_immobile = :idImmobile" + 
			"		group by cod_comune, sezione, classe, tipo_nota_gen, numero_nota_gen,  " + 
			"	       progressivo_nota_gen, tipo_nota_concl, numero_nota_concl, progressivo_nota_concl,  " + 
			"	       partita, foglio, numero, subalterno, edificabilita, tipo_immobile,  " + 
			"	       flag_reddito, flag_porzione, flag_deduzioni, annotazione, codice_causale_atto_gen,  " + 
			"	       descrizione_atto_gen, codice_causale_atto_concl, descrizione_atto_concl,  " + 
			"	       id_immobile, progressivo, anno_nota_gen, anno_nota_concl, id_mutaz_in,  " + 
			"	       id_mutaz_fin, qualita, ettari, are, reddito_dominicale_lire,  " + 
			"	       reddito_agrario_lire, reddito_dominicale_euro, reddito_agrario_euro,  " + 
			"	       centiare, data_efficacia_gen, data_registrazione_gen, data_efficacia_concl,  " + 
			"	       data_registrazione_concl, denominatore " + 
			"	  order by data_efficacia_concl desc", nativeQuery = true)
	List<ParticellaVisuraStorica> findByIdImmobileVisuraStorica(@Param("idImmobile") Long idImmobile);

}
