package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.Intestazioni;
import it.eng.tz.urbamid.catasto.persistence.model.Soggetti;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettiReportParticelle;
import it.eng.tz.urbamid.catasto.persistence.model.Soggetto;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoIntestatario;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoModel;

public interface JpaRepositorySoggetto extends PagingAndSortingRepository<Soggetto, Long>, JpaSpecificationExecutor<Soggetto> {

	String 	DEFAULT_SORT_PROPERTY_COGNOME = "cognome";
	String 	DEFAULT_SORT_PROPERTY_NOME = "nome";
	Sort 	DEFAULT_SORT_COGNOME = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY_COGNOME );
	Sort 	DEFAULT_SORT_NOME = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY_NOME );

	String 	DEFAULT_SORT_PROPERTY_SG = "denominazione";
	Sort 	DEFAULT_SORT_SG = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY_SG );

	@Query(value="SELECT distinct nome, cognome, codice_fiscale, denominazione, tipo_soggetto " + 
					"FROM public.u_cat_soggetti " + 
					"WHERE id_soggetto in (	" + 
					"	SELECT id_soggetto" + 
					"	FROM public.u_cat_titolarita" + 
					"	WHERE id_immobile = :idImmobile" +
					"	and id_mutaz_fin is null)", nativeQuery = true)
	List<SoggettoIntestatario> ricercaListaIntestatari(
			 @Param("idImmobile") Integer idImmobile);

	@Query(value="SELECT distinct id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, codice_fiscale, denominazione, indicazioni_supplementari " + 
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_particelle_catastali  " + 
			"			WHERE cod_comune in :lsCodComuni " + 
			"						and foglio in :lsFogli " + 
			"						and numero in :lsMappale" + 
			"		)" + 
			"	)" + 
			"	and tipo_soggetto = :tipoSoggetto ", nativeQuery = true)
	List<SoggettoIntestatario> ricercaPersoneFisichePT(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale, 
			@Param("tipoSoggetto") String tipoSoggetto);
	
	@Query(value="SELECT distinct (codice_fiscale), id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, denominazione, indicazioni_supplementari " + 
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_particelle_catastali  " + 
			"			WHERE cod_comune = :codComune " + 
			"						and foglio = :foglio " + 
			"						and numero in :lsMappale" + 
			"		)" + 
			"	)" + 
			"	and tipo_soggetto = :tipoSoggetto ", nativeQuery = true)
	List<SoggettoIntestatario> ricercaFMPersoneFisichePT(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale, 
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct (denominazione), id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, codice_fiscale, indicazioni_supplementari " + 
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_particelle_catastali  " + 
			"			WHERE cod_comune = :codComune " + 
			"						and foglio = :foglio " + 
			"						and numero in :lsMappale" + 
			"		)" + 
			"	)" + 
			"	and tipo_soggetto = :tipoSoggetto ", nativeQuery = true)
	List<SoggettoIntestatario> ricercaFMPersoneGiuridichePT(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale, 
			@Param("tipoSoggetto") String tipoSoggetto);
	
	@Query(value="SELECT distinct id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, codice_fiscale, denominazione, indicazioni_supplementari  " + 
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_identificativi_unita_immobiliari   " + 
			"			WHERE cod_comune in :lsCodComuni " + 
			"						and foglio in :lsFogli " + 
			"						and numero in :lsMappale" + 
			"		)" + 
			"	)" + 
			"	and tipo_soggetto = :tipoSoggetto ", nativeQuery = true)
	List<SoggettoIntestatario> ricercaPersoneFisicheUI(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale, 
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct(codice_fiscale), id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, denominazione, indicazioni_supplementari  " + 
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_identificativi_unita_immobiliari   " + 
			"			WHERE cod_comune = :codComune " + 
			"						and foglio = :foglio " + 
			"						and numero in :lsMappale" + 
			"		)" + 
			"	)" + 
			"	and tipo_soggetto = :tipoSoggetto ", nativeQuery = true)
	List<SoggettoIntestatario> ricercaFMPersoneFisicheUI(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale, 
			@Param("tipoSoggetto") String tipoSoggetto);
	
	@Query(value="SELECT distinct(denominazione), id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, codice_fiscale , indicazioni_supplementari  " + 
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_identificativi_unita_immobiliari   " + 
			"			WHERE cod_comune = :codComune " + 
			"						and foglio = :foglio " + 
			"						and numero in :lsMappale" + 
			"		)" + 
			"	)" + 
			"	and tipo_soggetto = :tipoSoggetto ", nativeQuery = true)
	List<SoggettoIntestatario> ricercaFMPersoneGiuridicheUI(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale, 
			@Param("tipoSoggetto") String tipoSoggetto);
	
	@Query(value="SELECT titolarita.id_immobile as IdPtUiu," + 
			"		soggetti.id_soggetto as IdSoggetto," + 
			"		titolarita.tipo_immobile as Tipo," + 
			"		soggetti.tipo_soggetto as Persona," + 
			"		codici.descrizione as Diritto" + 
			"  	FROM public.u_cat_soggetti soggetti," + 
			"       public.u_cat_titolarita titolarita," + 
			"       public.u_cat_codici_diritto codici" + 
			"	WHERE soggetti.id_soggetto = titolarita.id_soggetto" + 
			"		and titolarita.codice_diritto = codici.codice" + 
			"		and soggetti.id_soggetto in (" + 
			"					SELECT id_soggetto" + 
			"					FROM public.u_cat_titolarita" + 
			"					WHERE id_immobile in (" + 
			"						SELECT id_immobile" + 
			"						FROM public.u_cat_particelle_catastali" + 
			"						WHERE cod_comune in :lsCodComuni " + 
			"									and foglio in :lsFogli " + 
			"									and numero in :lsMappale" + 
			"					)" + 
			"				) ", nativeQuery = true)
	List<Intestazioni> exportIntestazioniPT(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT titolarita.id_immobile as IdPtUiu," + 
			"		soggetti.id_soggetto as IdSoggetto," + 
			"		titolarita.tipo_immobile as Tipo," + 
			"		soggetti.tipo_soggetto as Persona," + 
			"		codici.descrizione || ' ' || titolarita.quota_numeratore || '/' || titolarita.quota_denominatore as Diritto" + 
			"  	FROM public.u_cat_soggetti soggetti," + 
			"        public.u_cat_titolarita titolarita," + 
			"        public.u_cat_codici_diritto codici," +
			"        public.u_cat_particelle_catastali as identificativo" +
			"	WHERE soggetti.id_soggetto = titolarita.id_soggetto" + 
			"		and titolarita.codice_diritto = codici.codice" + 
			"       and identificativo.id_immobile = titolarita.id_immobile" + 
			"       and identificativo.cod_comune = :codComune AND identificativo.foglio = :foglio AND numero in :lsMappale", nativeQuery = true)
	List<Intestazioni> exportFMIntestazioniPT(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT titolarita.id_immobile as IdPtUiu," + 
			"		soggetti.id_soggetto as IdSoggetto," + 
			"		titolarita.tipo_immobile as Tipo," + 
			"		soggetti.tipo_soggetto as Persona," + 
			"		codici.descrizione as Diritto" + 
			"  	FROM public.u_cat_soggetti soggetti," + 
			"       public.u_cat_titolarita titolarita," + 
			"       public.u_cat_codici_diritto codici" + 
			"	WHERE soggetti.id_soggetto = titolarita.id_soggetto" + 
			"		and titolarita.codice_diritto = codici.codice" + 
			"		and soggetti.id_soggetto in (" + 
			"					SELECT id_soggetto" + 
			"					FROM public.u_cat_titolarita" + 
			"					WHERE id_immobile in (" + 
			"						SELECT id_immobile" + 
			"						FROM public.u_cat_particelle_catastali" + 
			"						WHERE cod_comune = :codComune " + 
			"									and foglio = :foglio " + 
			"									and numero in :lsMappale" + 
			"					)" + 
			"				) ", nativeQuery = true)
	List<Intestazioni> exportFMIntestazioniPT(
			@Param("codComune") List<String> codComune,
			@Param("foglio") List<String> foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT titolarita.id_immobile as IdPtUiu," + 
			"		soggetti.id_soggetto as IdSoggetto," + 
			"		titolarita.tipo_immobile as Tipo," + 
			"		soggetti.tipo_soggetto as Persona," + 
			"		codici.descrizione as Diritto" + 
			"  	FROM public.u_cat_soggetti soggetti," + 
			"       public.u_cat_titolarita titolarita," + 
			"       public.u_cat_codici_diritto codici" + 
			"	WHERE soggetti.id_soggetto = titolarita.id_soggetto" + 
			"		and titolarita.codice_diritto = codici.codice" + 
			"		and soggetti.id_soggetto in (" + 
			"					SELECT id_soggetto" + 
			"					FROM public.u_cat_titolarita" + 
			"					WHERE id_immobile in (" + 
			"						SELECT id_immobile" + 
			"						FROM public.u_cat_identificativi_unita_immobiliari" + 
			"						WHERE cod_comune in :lsCodComuni " + 
			"									and foglio in :lsFogli " + 
			"									and numero in :lsMappale" + 
			"					)" + 
			"				) ", nativeQuery = true)
	List<Intestazioni> exportIntestazioniUI(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT titolarita.id_immobile as IdPtUiu," + 
			"		soggetti.id_soggetto as IdSoggetto," + 
			"		titolarita.tipo_immobile as Tipo," + 
			"		soggetti.tipo_soggetto as Persona," + 
			"		codici.descrizione || ' ' || titolarita.quota_numeratore || '/' || titolarita.quota_denominatore as Diritto" + 
			"  	FROM public.u_cat_soggetti soggetti," + 
			"        public.u_cat_titolarita titolarita," + 
			"        public.u_cat_codici_diritto codici," +
			"        public.u_cat_identificativi_unita_immobiliari as identificativo" +
			"	WHERE soggetti.id_soggetto = titolarita.id_soggetto" + 
			"		and titolarita.codice_diritto = codici.codice" + 
			"       and identificativo.id_immobile = titolarita.id_immobile" + 
			"       and identificativo.cod_comune = :codComune AND identificativo.foglio = :foglio AND numero in :lsMappale", nativeQuery = true)
	List<Intestazioni> exportFMIntestazioniUI(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT id as Identificativo," + 
			//"		'Ute' as Ute," + 
			"		tipo_soggetto as PersonaFisica," + 
			"		sesso as Sesso," + 
			"		nome as Nome," + 
			"		cognome as Cognome," + 
			"		codice_fiscale as CodiceFiscale," + 
			"		data_nascita as DataNascita," + 
			//"		'LuogoNascita' as LuogoNascita," + 
			"		luogo_nascita as idbelfioreNascita," + 
			"		indicazioni_supplementari as Annotazioni" + 
			"		, denominazione" +
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_particelle_catastali" +
			"			WHERE cod_comune in :lsCodComuni " + 
			"					and foglio in :lsFogli " + 
			"					and numero in :lsMappale)" + 
			"			)", nativeQuery = true)
	List<Soggetti> exportSoggettiPT(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT distinct(codice_fiscale) as CodiceFiscale, id as Identificativo," + 
			"		tipo_soggetto as PersonaFisica," + 
			"		CASE " +
			"			WHEN sesso = '1' THEN 'M'" + 
			"           WHEN sesso = '2' THEN 'F'" + 
			"			ELSE NULL" + 
			"		END as Sesso," + 
			"		nome as Nome," + 
			"		cognome as Cognome," + 
			"		data_nascita as DataNascita," + 
			"		luogo_nascita as idbelfioreNascita," + 
			"		indicazioni_supplementari as Annotazioni" + 
			"		, denominazione" +
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_particelle_catastali" +
			"			WHERE cod_comune = :codComune " + 
			"					and foglio = :foglio " + 
			"					and numero in :lsMappale)" + 
			"			)", nativeQuery = true)
	List<Soggetti> exportFMSoggettiPT(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT id as Identificativo," + 
			//"		'Ute' as Ute," + 
			"		tipo_soggetto as PersonaFisica," + 
			"		sesso as Sesso," + 
			"		nome as Nome," + 
			"		cognome as Cognome," + 
			"		codice_fiscale as CodiceFiscale," + 
			"		data_nascita as DataNascita," + 
			//"		'LuogoNascita' as LuogoNascita," + 
			"		luogo_nascita as idbelfioreNascita," + 
			"		indicazioni_supplementari as Annotazioni" + 
			"		, denominazione" +
			"	FROM public.u_cat_soggetti" + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT id_immobile   " + 
			"			FROM public.u_cat_identificativi_unita_immobiliari" +
			"			WHERE cod_comune in :lsCodComuni " + 
			"					and foglio in :lsFogli " + 
			"					and numero in :lsMappale)" + 
			"			)", nativeQuery = true)
	List<Soggetti> exportSoggettiUI(
			@Param("lsCodComuni") List<String> lsCodComuni,
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT distinct(codice_fiscale) as CodiceFiscale, id_soggetto as Identificativo," + 
			"		tipo_soggetto as PersonaFisica," + 
			"		CASE " +
			"			WHEN sesso = '1' THEN 'M'" +
			"			WHEN sesso = '2' THEN 'F'" +
			"			ELSE null " +
			"		END as Sesso," + 
			"		nome as Nome," + 
			"		cognome as Cognome," + 
			"		data_nascita as DataNascita," + 
			"		luogo_nascita as idbelfioreNascita," + 
			"		indicazioni_supplementari as Annotazioni," +
			"		denominazione " + 
			"	FROM public.u_cat_soggetti " + 
			"	WHERE id_soggetto in (	" + 
			"		SELECT distinct id_soggetto" + 
			"		FROM public.u_cat_titolarita" + 
			"		WHERE id_immobile in (" + 
			"			SELECT distinct id_immobile   " + 
			"			FROM public.u_cat_identificativi_unita_immobiliari" +
			"			WHERE cod_comune = :codComune " + 
			"					and foglio = :foglio " + 
			"					and numero in :lsMappale)" + 
			"			)", nativeQuery = true)
	List<Soggetti> exportFMSoggettiUI(
			@Param("codComune") String codComune,
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value = "SELECT ucc.denominazione"
			+ "		FROM u_cat_comuni ucc"
			+ "		WHERE ucc.codice_mf = :IdbelfioreNascita LIMIT 1", nativeQuery = true)
	String luogoNascita(@Param("IdbelfioreNascita") String idSoggetto);
	
	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"			so.cognome," + 
			"			so.nome," + 
			"			so.denominazione," + 
			"			so.luogo_nascita as luogoNascita," + 
			"			so.data_nascita as dataNascita," + 
			"			so.codice_fiscale as codiceFiscale," + 
			"			pt.foglio," + 
			"			pt.numero," + 
			//"			pt.subalterno," + 
			//"			dr.descrizione" +
			"			dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_particelle_catastali pt," + 
			"	u_cat_codici_diritto dr" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = pt.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	pt.foglio in (:lsFogli) and" + 
			"	pt.numero in (:lsMappale) and" + 
			"	pt.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" + 
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.denominazione," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	pt.foglio," + 
			"	pt.numero," + 
			//"	pt.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by so.cognome asc, so.denominazione asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiTerreniNominativo(
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"			so.cognome," + 
			"			so.nome," + 
			"			so.denominazione," + 
			"			so.luogo_nascita as luogoNascita," + 
			"			so.data_nascita as dataNascita," + 
			"			so.codice_fiscale as codiceFiscale," + 
			"			pt.foglio," + 
			"			pt.numero," + 
			//"			pt.subalterno," + 
			//"			dr.descrizione" +
			"			dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_particelle_catastali pt," + 
			"	u_cat_codici_diritto dr" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = pt.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	pt.foglio = :foglio and" + 
			"	pt.numero in (:lsMappale) and" + 
			"	pt.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" + 
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.denominazione," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	pt.foglio," + 
			"	pt.numero," + 
			//"	pt.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by so.cognome asc, so.denominazione asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiFMTerreniNominativo(
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"			so.cognome," + 
			"			so.nome," + 
			"			so.denominazione," + 
			"			so.luogo_nascita as luogoNascita," + 
			"			so.data_nascita as dataNascita," + 
			"			so.codice_fiscale as codiceFiscale," + 
			"			pt.foglio," + 
			"			pt.numero," + 
			//"			pt.subalterno," + 
			//"			dr.descrizione" +
			"			dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_particelle_catastali pt," + 
			"	u_cat_codici_diritto dr" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = pt.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	pt.foglio in (:lsFogli) and" + 
			"	pt.numero in (:lsMappale) and" + 
			"	pt.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" + 
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.denominazione," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	pt.foglio," + 
			"	pt.numero," + 
			//"	pt.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by pt.foglio asc, pt.numero asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiTerreniParticella(
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"			so.cognome," + 
			"			so.nome," + 
			"			so.denominazione," + 
			"			so.luogo_nascita as luogoNascita," + 
			"			so.data_nascita as dataNascita," + 
			"			so.codice_fiscale as codiceFiscale," + 
			"			pt.foglio," + 
			"			pt.numero," + 
			//"			pt.subalterno," + 
			//"			dr.descrizione" +
			"			dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_particelle_catastali pt," + 
			"	u_cat_codici_diritto dr" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = pt.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	pt.foglio = :foglio and" + 
			"	pt.numero in (:lsMappale) and" + 
			"	pt.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" + 
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.denominazione," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	pt.foglio," + 
			"	pt.numero," + 
			//"	pt.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by pt.foglio asc, pt.numero asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiFMTerreniParticella(
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita as luogoNascita," + 
			"	so.data_nascita as dataNascita," + 
			"	so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione," + 
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			//"	dr.descrizione" + 
			"	dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_unita_immobiliare ui," + 
			"	u_cat_codici_diritto dr," + 
			"	u_cat_identificativi_unita_immobiliari iui" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = ui.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	iui.id_immobile = ui.id_immobile and" + 
			"	iui.foglio in (:lsFogli) and" + 
			"	iui.numero in (:lsMappale) and" + 
			"	ui.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" + 
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	so.denominazione," +
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by so.cognome asc, so.denominazione asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiFabbricatiNominativo(
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita as luogoNascita," + 
			"	so.data_nascita as dataNascita," + 
			"	so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione," + 
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			//"	dr.descrizione" + 
			"	dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_unita_immobiliare ui," + 
			"	u_cat_codici_diritto dr," + 
			"	u_cat_identificativi_unita_immobiliari iui" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = ui.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	iui.id_immobile = ui.id_immobile and" + 
			"	iui.foglio = :foglio and" + 
			"	iui.numero in (:lsMappale) and" + 
			"	ui.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" + 
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	so.denominazione," +
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by so.cognome asc, so.denominazione asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiFMFabbricatiNominativo(
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita as luogoNascita," + 
			"	so.data_nascita as dataNascita," + 
			"	so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione," + 
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			//"	dr.descrizione" + 
			"	dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_unita_immobiliare ui," + 
			"	u_cat_codici_diritto dr," + 
			"	u_cat_identificativi_unita_immobiliari iui" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = ui.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	iui.id_immobile = ui.id_immobile and" + 
			"	iui.foglio in (:lsFogli) and" + 
			"	iui.numero in (:lsMappale) and" + 
			"	ui.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" +  
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	so.denominazione," +
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by iui.foglio asc, iui.numero asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiFabbricatiParticella(
			@Param("lsFogli") List<String> lsFogli, 
			@Param("lsMappale") List<String> lsMappale);

	@Query(value="SELECT 	so.tipo_soggetto as tipoSoggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita as luogoNascita," + 
			"	so.data_nascita as dataNascita," + 
			"	so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione," + 
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			//"	dr.descrizione" + 
			"	dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as descrizione" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_unita_immobiliare ui," + 
			"	u_cat_codici_diritto dr," + 
			"	u_cat_identificativi_unita_immobiliari iui" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = ui.id_immobile and" + 
			"	dr.codice = ti.codice_diritto and" + 
			"	iui.id_immobile = ui.id_immobile and" + 
			"	iui.foglio = :foglio and" + 
			"	iui.numero in (:lsMappale) and" + 
			"	ui.id_mutaz_fin is null and" + 
			"	ti.id_mutaz_fin is null" +  
			" group by so.tipo_soggetto," + 
			"	so.cognome," + 
			"	so.nome," + 
			"	so.luogo_nascita," + 
			"	so.data_nascita," + 
			"	so.codice_fiscale," + 
			"	so.denominazione," +
			"	iui.foglio," + 
			"	iui.numero," + 
			//"	iui.subalterno," + 
			"	dr.descrizione," + 
			"	ti.quota_numeratore," + 
			"	ti.quota_denominatore" + 
			" order by iui.foglio asc, iui.numero asc", nativeQuery = true)
	List<SoggettiReportParticelle> soggettiFMFabbricatiParticella(
			@Param("foglio") String foglio, 
			@Param("lsMappale") List<String> lsMappale);
	
	@Query(value="SELECT distinct so.codice_fiscale, " +
			"		so.cognome, " +
			"		so.nome, " + 
			"		so.denominazione, " +
			"		so.tipo_soggetto, " +
			"		dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as diritto, " +
			"		ti.data_validita_gen as dataValiditaGen" +	
			"			FROM public.u_cat_soggetti so," + 
			"			public.u_cat_titolarita ti," + 
			"			public.u_cat_codici_diritto dr " + 
			"			WHERE so.id_soggetto = ti.id_soggetto" + 
			"			and dr.codice = ti.codice_diritto" + 
			/*"			and so.id_soggetto in (	 " + 
			"				SELECT id_soggetto " + 
			"				FROM public.u_cat_titolarita " + 
			"				WHERE id_immobile = :idImmobile" + 
			"				and id_soggetto != :idSoggetto)" +*/
			"			and ti.id_soggetto != :idSoggetto and ti.id_immobile = :idImmobile" +
			"			and id_mutaz_fin is null" +
			"			order by ti.data_validita_gen desc", nativeQuery = true)
	List<SoggettoIntestatario> ricercaListaIntestatariTranneCorrenteConDiritto(
		 @Param("idImmobile") Long idImmobile,
		 @Param("idSoggetto") Long idSoggetto);

	@Query(value="SELECT distinct so.nome, so.cognome, so.codice_fiscale, so.denominazione, so.tipo_soggetto, dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as diritto, " + 
			"		ti.data_validita_gen as dataValiditaGen" +
			"			FROM public.u_cat_soggetti so," + 
			"			public.u_cat_titolarita ti," + 
			"			public.u_cat_codici_diritto dr " + 
			"			WHERE so.id_soggetto = ti.id_soggetto" + 
			"			and dr.codice = ti.codice_diritto" + 
			"			and ti.id_immobile = :idImmobile" +
			"			and id_mutaz_fin is null " + 
			"			order by ti.data_validita_gen desc", nativeQuery = true)
	List<SoggettoIntestatario> ricercaListaIntestatariConDiritto(@Param("idImmobile") Long idImmobile);

	@Query(value="SELECT distinct so.codice_fiscale, " +
			"		so.cognome, " +
			"		so.nome, " + 
			"		so.denominazione, " +
			"		so.tipo_soggetto, " +
			"		dr.descrizione || ' ' || ti.quota_numeratore || '/' || ti.quota_denominatore as diritto, " +
			"		ti.data_validita_gen as dataValiditaGen" +	
			"			FROM public.u_cat_soggetti so," + 
			"			public.u_cat_titolarita ti," + 
			"			public.u_cat_codici_diritto dr " + 
			"			WHERE so.id_soggetto = ti.id_soggetto" + 
			"			and dr.codice = ti.codice_diritto" + 
			"			and ti.id_immobile = :idImmobile" +
			"			order by ti.data_validita_gen desc", nativeQuery = true)
	List<SoggettoIntestatario> ricercaListaIntestatariConDirittoVisuraStorica(@Param("idImmobile") Long idImmobile);

	/*@Query(value="SELECT cod_comune as codComune," + 
			"	sezione," + 
			"	id_soggetto as idSoggetto," + 
			"	tipo_soggetto as tipoSoggetto," + 
			"	cognome, " + 
			"	nome," + 
			"	sesso," + 
			"	data_nascita as dataNascita," + 
			"	luogo_nascita as luogoNascita," + 
			"	codice_fiscale as codiceFiscale," + 
			"	denominazione, " + 
			"	sede," + 
			"	indicazioni_supplementari as indicazioniSupplementari  " + 
			" FROM public.u_cat_soggetti " + 
			" WHERE tipo_soggetto = :tipoSoggetto " +
			"	and (1=:flagNome or nome = upper(:nome)) " + 
			"	and (1=:flagCognome or cognome = upper(:cognome)) " + 
			"	and (1=:fieldSesso or sesso = :sesso) " + 
			"	and (1=:fieldCodFis or codice_fiscale = upper(:codFis)) " + 
			"	and (1=:flagNote or indicazioni_supplementari = :note) " + 
			"	and (1=:fieldNascitaDa or data_nascita >= :nascitaDa) " + 
			"	and (1=:flagNascitaA or data_nascita <= :nascitaA) " + 
			"	and (1=:flagLuogoNascita or luogo_nascita = :luogoNascita) " + 
			" GROUP BY cod_comune, sezione, id_soggetto, tipo_soggetto, cognome, " + 
			"       nome, sesso, data_nascita, luogo_nascita, codice_fiscale, denominazione, " + 
			"       sede, indicazioni_supplementari", nativeQuery = true)*/
//	@Query(nativeQuery = true, name	= "SoggettoModel.findSoggetti", countName = "SoggettoModel.countFindSoggetti")
//	Page<SoggettoModel> findSoggetti(
//			@Param("tipoSoggetto") String tipoSoggetto,
//			@Param("flagNome") Integer flagNome, 
//			@Param("nome") String nome, 
//			@Param("flagCognome") Integer flagCognome, 
//			@Param("cognome") String cognome, 
//			@Param("fieldSesso") Integer fieldSesso, 
//			@Param("sesso") String sesso,
//			@Param("fieldCodFis") Integer fieldCodFis, 
//			@Param("codFis") String codFis, 
//			@Param("flagNote") Integer flagNote, 
//			@Param("note") String note, 
//			@Param("fieldNascitaDa") Integer fieldNascitaDa, 
//			@Param("nascitaDa") Date nascitaDa, 
//			@Param("flagNascitaA") Integer flagNascitaA,
//			@Param("nascitaA") Date nascitaA, 
//			@Param("flagLuogoNascita") Integer flagLuogoNascita, 
//			@Param("luogoNascita") String luogoNascita, 
//			Pageable of);

	@Query(nativeQuery = true, name	= "SoggettoModel.findSoggetti", countName = "SoggettoModel.countFindSoggetti")
	List<SoggettoModel> exportFindSoggetti(
			@Param("tipoSoggetto") String tipoSoggetto,
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("fieldSesso") Integer fieldSesso, 
			@Param("sesso") String sesso,
			@Param("fieldCodFis") Integer fieldCodFis, 
			@Param("codFis") String codFis, 
			@Param("flagNote") Integer flagNote, 
			@Param("note") String note, 
			@Param("fieldNascitaDa") Integer fieldNascitaDa, 
			@Param("nascitaDa") Date nascitaDa, 
			@Param("flagNascitaA") Integer flagNascitaA,
			@Param("nascitaA") Date nascitaA, 
			@Param("flagLuogoNascita") Integer flagLuogoNascita, 
			@Param("luogoNascita") String luogoNascita, 
			@Param("flagDenominazione") Integer flagDenominazione,
			@Param("denominazione") String denominazione, 
			@Param("flagSede") Integer flagSede, 
			@Param("sede") String sede);

}
