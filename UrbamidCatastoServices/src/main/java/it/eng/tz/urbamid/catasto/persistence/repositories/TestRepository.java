package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.SoggettoModel;

public interface TestRepository extends PagingAndSortingRepository<SoggettoModel, Long>, JpaSpecificationExecutor<SoggettoModel> {

	@Query(nativeQuery = true, name	= "SoggettoModel.findSoggetti", countName = "SoggettoModel.countFindSoggetti")
	Page<SoggettoModel> findSoggetti(
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
			@Param("sede") String sede, 
			Pageable of);

}
