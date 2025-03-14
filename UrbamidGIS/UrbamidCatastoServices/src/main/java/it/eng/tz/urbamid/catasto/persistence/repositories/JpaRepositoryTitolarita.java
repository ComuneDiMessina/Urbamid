package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.RicercaIntestazioni;
import it.eng.tz.urbamid.catasto.persistence.model.Titolarita;

public interface JpaRepositoryTitolarita extends PagingAndSortingRepository<Titolarita, Long>, JpaSpecificationExecutor<Titolarita> {

	/*@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			" FROM 	u_cat_soggetti so," + 
			"	u_cat_titolarita ti," + 
			"	u_cat_particelle_catastali pt," + 
			"	u_cat_identificativi_unita_immobiliari uiu" + 
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	ti.id_immobile = pt.id_immobile and" + 
			"	ti.id_immobile = uiu.id_immobile and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)*/
	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<RicercaIntestazioni> ricercaIntestazioniAll(
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale,
			@Param("codiceFiscale") String codiceFiscale,
			@Param("tipoSoggetto") String tipoSoggetto,
			Pageable pageable);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'T' and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<RicercaIntestazioni> ricercaIntestazioniPT(
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale,
			@Param("codiceFiscale") String codiceFiscale,
			@Param("tipoSoggetto") String tipoSoggetto,
			Pageable pageable);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'F' and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<RicercaIntestazioni> ricercaIntestazioniUIU(
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale,
			@Param("codiceFiscale") String codiceFiscale,
			@Param("tipoSoggetto") String tipoSoggetto,
			Pageable pageable);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'T' and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<RicercaIntestazioni> exportRicercaIntestazioniPT(
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale,
			@Param("codiceFiscale") String codiceFiscale,
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'F' and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<RicercaIntestazioni> exportRicercaIntestazioniUIU(
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale,
			@Param("codiceFiscale") String codiceFiscale,
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.cognome, " + 
			"	so.nome," + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.foglio" + 
			"		WHEN 'F' THEN uiu.foglio" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	(1=:flagNome or so.nome = :nome) and" +
			"	(1=:flagCognome or so.cognome = :cognome) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale)" +
			"	order by cognome asc, nome asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<RicercaIntestazioni> exportRicercaIntestazioniAll(
			@Param("flagNome") Integer flagNome, 
			@Param("nome") String nome, 
			@Param("flagCognome") Integer flagCognome, 
			@Param("cognome") String cognome, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale,
			@Param("codiceFiscale") String codiceFiscale,
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione, " + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.foglio as numeric)" + 
			"		WHEN 'F' THEN cast(uiu.foglio as numeric)" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'T' and" +
			"	(1=:flagDenominazione or so.denominazione like :denominazione) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale) and" +
			"	(1=:flagCodComune or so.sede = :codComune)" +
			"	order by denominazione asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<RicercaIntestazioni> ricercaIntestazioniSgPT(
			@Param("flagDenominazione") Integer flagDenominazione, 
			@Param("denominazione") String denominazione, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale, 
			@Param("codiceFiscale") String codiceFiscale,
			@Param("flagCodComune") Integer flagCodComune,
			@Param("codComune") String codComune,
			@Param("tipoSoggetto") String tipoSoggetto, 
			Pageable pageable);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione, " + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.foglio as numeric)" + 
			"		WHEN 'F' THEN cast(uiu.foglio as numeric)" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'F' and" +
			"	(1=:flagDenominazione or so.denominazione like :denominazione) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale) and" + 
			"	(1=:flagCodComune or so.sede = :codComune)" +
			"	order by denominazione asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<RicercaIntestazioni> ricercaIntestazioniSgUIU(
			@Param("flagDenominazione") Integer flagDenominazione, 
			@Param("denominazione") String denominazione, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale, 
			@Param("codiceFiscale") String codiceFiscale,
			@Param("flagCodComune") Integer flagCodComune,
			@Param("codComune") String codComune,
			@Param("tipoSoggetto") String tipoSoggetto, 
			Pageable pageable);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione, " + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.foglio as numeric)" + 
			"		WHEN 'F' THEN cast(uiu.foglio as numeric)" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	(1=:flagDenominazione or so.denominazione like :denominazione) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale) and " +
			"	(1=:flagCodComune or so.sede = :codComune)" +
			"	order by denominazione asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	Page<RicercaIntestazioni> ricercaIntestazioniSgAll(
			@Param("flagDenominazione") Integer flagDenominazione, 
			@Param("denominazione") String denominazione, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale, 
			@Param("codiceFiscale") String codiceFiscale,
			@Param("flagCodComune") Integer flagCodComune,
			@Param("codComune") String codComune,
			@Param("tipoSoggetto") String tipoSoggetto, 
			Pageable pageable);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione, " + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.foglio as numeric)" + 
			"		WHEN 'F' THEN cast(uiu.foglio as numeric)" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'T' and" +
			"	(1=:flagDenominazione or so.denominazione like :denominazione) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale) and" +
			"	(1=:flagCodComune or so.sede = :codComune)" +
			"	order by denominazione asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<RicercaIntestazioni> exportRicercaIntestazioniSgPT(
			@Param("flagDenominazione") Integer flagDenominazione, 
			@Param("denominazione") String denominazione, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale, 
			@Param("codiceFiscale") String codiceFiscale,
			@Param("flagCodComune") Integer flagCodComune,
			@Param("codComune") String codComune,
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione, " + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.foglio as numeric)" + 
			"		WHEN 'F' THEN cast(uiu.foglio as numeric)" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	ti.tipo_immobile = 'F' and" +
			"	(1=:flagDenominazione or so.denominazione like :denominazione) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale) and" + 
			"	(1=:flagCodComune or so.sede = :codComune)" +
			"	order by denominazione asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<RicercaIntestazioni> exportRicercaIntestazioniSgUIU(
			@Param("flagDenominazione") Integer flagDenominazione, 
			@Param("denominazione") String denominazione, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale, 
			@Param("codiceFiscale") String codiceFiscale,
			@Param("flagCodComune") Integer flagCodComune,
			@Param("codComune") String codComune,
			@Param("tipoSoggetto") String tipoSoggetto);

	@Query(value="SELECT distinct so.codice_fiscale as codiceFiscale," + 
			"	so.denominazione, " + 
			"	so.id_soggetto as idSoggetto," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN 'PT'" + 
			"		WHEN 'F' THEN 'UIU'" + 
			"		END AS tipo," + 
			"	CASE ti.sezione " + 
			"		WHEN 'T' THEN cast(pt.sezione as text)" + 
			"		WHEN 'F' THEN cast(uiu.sezione as text)" + 
			"		END AS sezione," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.foglio as numeric)" + 
			"		WHEN 'F' THEN cast(uiu.foglio as numeric)" + 
			"		END AS foglio," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN pt.numero" + 
			"		WHEN 'F' THEN uiu.numero" + 
			"		END AS numero," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.subalterno as text)" + 
			"		WHEN 'F' THEN cast(uiu.subalterno as text)" + 
			"		END AS subalterno," + 
			"	CASE ti.tipo_immobile " + 
			"		WHEN 'T' THEN cast(pt.cod_comune as text)" + 
			"		WHEN 'F' THEN cast(uiu.cod_comune as text)" + 
			"		END AS codComune," + 
			"	ti.id_immobile as idImmobile" + 
			"	FROM " +
			"		u_cat_soggetti so join u_cat_titolarita ti on (so.id_soggetto = ti.id_soggetto)" +
			"		full outer join u_cat_identificativi_unita_immobiliari uiu on (uiu.id_immobile = ti.id_immobile)" +
			"		full outer join u_cat_particelle_catastali pt on (pt.id_immobile = ti.id_immobile)" +
			" WHERE	so.id_soggetto = ti.id_soggetto and" + 
			"	so.tipo_soggetto = :tipoSoggetto and" +
			"	(1=:flagDenominazione or so.denominazione like :denominazione) and" +
			"	(1=:flagCodiceFiscale or so.codice_fiscale = :codiceFiscale) and " +
			"	(1=:flagCodComune or so.sede = :codComune)" +
			"	order by denominazione asc, foglio asc, numero asc, subalterno asc"
			, nativeQuery = true)
	List<RicercaIntestazioni> exportRicercaIntestazioniSgAll(
			@Param("flagDenominazione") Integer flagDenominazione, 
			@Param("denominazione") String denominazione, 
			@Param("flagCodiceFiscale") Integer flagCodiceFiscale, 
			@Param("codiceFiscale") String codiceFiscale,
			@Param("flagCodComune") Integer flagCodComune,
			@Param("codComune") String codComune,
			@Param("tipoSoggetto") String tipoSoggetto);

}
