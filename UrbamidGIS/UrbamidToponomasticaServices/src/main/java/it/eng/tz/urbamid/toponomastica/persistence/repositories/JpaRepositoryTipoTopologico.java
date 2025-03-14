package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoTopologico;

@Repository
public interface JpaRepositoryTipoTopologico extends JpaRepository<TipoTopologico, Long>{

}
