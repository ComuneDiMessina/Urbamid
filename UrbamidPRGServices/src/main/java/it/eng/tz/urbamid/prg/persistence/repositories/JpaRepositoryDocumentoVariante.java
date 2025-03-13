package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoVariante;

public interface JpaRepositoryDocumentoVariante extends PagingAndSortingRepository<DocumentoVariante, Long>, JpaSpecificationExecutor<DocumentoVariante> {

	@Query(value="SELECT * FROM public.u_prg_doc_variante WHERE id_variante = :idVariante order by tipo_documento asc", nativeQuery = true)
	List<DocumentoVariante> cercaDocumentiByIdVariante(@Param("idVariante") Long idVariante);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_doc_variante WHERE id_variante = :idVariante", nativeQuery = true)
	void cancellaDocumentiFromIdVariante(@Param("idVariante") Long idVariante);

}
