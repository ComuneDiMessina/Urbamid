package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoAccesso;

@Repository
public interface JpaRepositoryTipoAccesso extends JpaRepository<TipoAccesso, Long>{

}
