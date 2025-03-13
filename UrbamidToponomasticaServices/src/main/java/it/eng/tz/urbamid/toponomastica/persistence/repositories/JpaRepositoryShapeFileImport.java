package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.ShapeFileImport;

@Repository
public interface JpaRepositoryShapeFileImport extends JpaRepository<ShapeFileImport, Long> {

	@Query(value = "SELECT * FROM u_topo_import_shape WHERE processato = true", nativeQuery = true)
	List<ShapeFileImport> listaShapeFile();
	
	@Query(value = "SELECT COUNT (*) FROM u_topo_import_shape WHERE name_file=:nomeFile AND processato = true", nativeQuery = true)
	int countProcessati(@Param("nomeFile") String nomeFile);
}
