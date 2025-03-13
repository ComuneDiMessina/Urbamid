package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.IdentificativoUnitaImmobiliari;

public interface JpaRepositoryIdentificativoUnitaImmobiliari extends PagingAndSortingRepository<IdentificativoUnitaImmobiliari, Long>, JpaSpecificationExecutor<IdentificativoUnitaImmobiliari> {

	@Query(value="SELECT cod_comune, sezione, id_immobile, progressivo, sezione_urbana, " + 
			"       foglio, numero, denominatore, subalterno, edificabilita, id, " + 
			"       tipo_immobile, foglio_orig, numero_orig" + 
			"  FROM public.u_cat_identificativi_unita_immobiliari" + 
			"  WHERE id_immobile = :idImmobile", nativeQuery = true)
	List<IdentificativoUnitaImmobiliari> dettaglioImmobiliUIUIdentificativi(@Param("idImmobile") Long idImmobile);
}
