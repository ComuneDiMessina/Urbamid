package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.Planimetria;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaPlanimetrieJoinDettaglio;

public interface JpaRepositoryPlanimetrie extends PagingAndSortingRepository<Planimetria, Long>, JpaSpecificationExecutor<Planimetria> {

	@Query(value="SELECT  p.id, p.cod_comune, " 
					+"p.cat_catastale, p.superficie, p.foglio, p.mappale, p.subalterno, p.dug, " 
					+"p.indirizzo, p.num_civico, pi.nome_image, pi.scale_from, pi.scale_to "
				+"FROM public.u_cat_planimetrie p, u_cat_planimetrie_image pi "
				+"WHERE "
					+ "p.id_pla_immagine_1=pi.id_pla_immagine_1 "
					+ "and p.id_pla_immagine_2=pi.id_pla_immagine_2 "
					+ "and p.id_pla_immagine_3=pi.id_pla_immagine_3 "
					+ "and foglio = :foglio AND mappale = :mappale "
				,nativeQuery = true)
	 List<UnitaPlanimetrieJoinDettaglio> findByFoglioAndMappale(
			 @Param("foglio")String foglio, 
			 @Param("mappale")String mappale);

	@Query(value="SELECT  p.id, p.cod_comune, " 
					+"p.cat_catastale, p.superficie, p.foglio, p.mappale, p.subalterno, p.dug, " 
					+"p.indirizzo, p.num_civico, pi.nome_image, pi.scale_from, pi.scale_to "
				+"FROM public.u_cat_planimetrie p, u_cat_planimetrie_image pi "
				+"WHERE "
					+ "p.id_pla_immagine_1=pi.id_pla_immagine_1 "
					+ "and p.id_pla_immagine_2=pi.id_pla_immagine_2 "
					+ "and p.id_pla_immagine_3=pi.id_pla_immagine_3 "
					+ "and foglio = :foglio AND mappale = :mappale AND subalterno = :subalterno"
				,nativeQuery = true)
		List<UnitaPlanimetrieJoinDettaglio> findByFoglioAndMappaleAndSubalterno(
			 @Param("foglio")String foglio, 
			 @Param("mappale")String mappale,
			 @Param("subalterno")String subalterno);

	@Query(value="SELECT  p.id, p.cod_comune, " 
				+"p.cat_catastale, p.superficie, p.foglio, p.mappale, p.subalterno, p.dug, " 
				+"p.indirizzo, p.num_civico, pi.nome_image, pi.scale_from, pi.scale_to "
			+"FROM public.u_cat_planimetrie p, u_cat_planimetrie_image pi "
			+"WHERE "
				+ "p.id_pla_immagine_1=pi.id_pla_immagine_1 "
				+ "and p.id_pla_immagine_2=pi.id_pla_immagine_2 "
				+ "and p.id_pla_immagine_3=pi.id_pla_immagine_3 "
				+ "and id_pla_immagine_1 = :id_pla_immagine_1 "
				+ "and id_pla_immagine_2 = :id_pla_immagine_2 "
				+ "and id_pla_immagine_3 = :id_pla_immagine_3 "
			,nativeQuery = true)
	UnitaPlanimetrieJoinDettaglio findByIdImmagini(
		 @Param("id_pla_immagine_1")String id_pla_immagine_1,
		 @Param("id_pla_immagine_2")String id_pla_immagine_2,
		 @Param("id_pla_immagine_3")String id_pla_immagine_3);
	
}
