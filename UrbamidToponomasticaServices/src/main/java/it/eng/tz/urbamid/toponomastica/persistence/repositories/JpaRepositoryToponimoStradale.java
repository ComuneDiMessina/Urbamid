package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;

public interface JpaRepositoryToponimoStradale extends PagingAndSortingRepository<ToponimoStradale, Long>, JpaRepository<ToponimoStradale, Long>, JpaSpecificationExecutor<ToponimoStradale> {

	String DENOMINAZIONE_UFFICIALE_SORT_PROPERTY = "denominazioneUfficiale";
	String CLASSE_SORT_PROPERTY = "classe";
	Sort DEFAULT_SORT = new Sort( Sort.Direction.ASC, DENOMINAZIONE_UFFICIALE_SORT_PROPERTY, CLASSE_SORT_PROPERTY);
	
//	@Query(value = "SELECT * "
//			+ "		FROM public.u_topo_toponimo_stradale WHERE denominazione_ufficiale LIKE UPPER(%:duf%) and classe_label LIKE UPPER(%:classe%)", countQuery = "SELECT COUNT(*) FROM public.u_topo_toponimo_stradale WHERE denominazione_ufficiale LIKE UPPER(%:duf%) and classe_label LIKE UPPER(%:classe%)", nativeQuery = true)
//	Page<ToponimoStradale> paginated(@Param("duf") String duf, @Param("classe") String classe, Pageable pageable);
	
	@Query(value="SELECT * FROM public.u_topo_toponimo_stradale WHERE id_padre=:idPadre" ,nativeQuery = true)
	public List<ToponimoStradale> findByIdPadre(@Param("idPadre")Long idPadre);

}
