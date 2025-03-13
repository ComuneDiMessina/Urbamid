package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndice;

public interface JpaRepositoryDocumentoIndice extends PagingAndSortingRepository<DocumentoIndice, Long>, JpaSpecificationExecutor<DocumentoIndice> {

	@Query(value="SELECT * FROM public.u_prg_doc_indice WHERE id_documento = :idDocumento order by articolo asc", nativeQuery = true)
	List<DocumentoIndice> cercaIndiciByIdDocumento(@Param("idDocumento") Long idDocumento);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_doc_indice WHERE id_documento = :idDocumento", nativeQuery = true)
	void deleteAllIndiciByIdDocumento(@Param("idDocumento") Long idDocumento);

}
