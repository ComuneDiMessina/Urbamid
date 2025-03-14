package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndice;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndiceCodici;

public interface JpaRepositoryDocumentoIndiceCodici extends PagingAndSortingRepository<DocumentoIndiceCodici, Long>, JpaSpecificationExecutor<DocumentoIndice> {

	@Query(value="SELECT * FROM public.u_prg_doc_indice_codici WHERE id_indice = :idIndice", nativeQuery = true)
	List<DocumentoIndiceCodici> cercaCodiciByIdIndice(@Param("idIndice") Long idIndice);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_doc_indice_codici WHERE id_indice = :idIndice", nativeQuery = true)
	void deleteAllCodiciByIdIndice(@Param("idIndice") Long idIndice);

}
