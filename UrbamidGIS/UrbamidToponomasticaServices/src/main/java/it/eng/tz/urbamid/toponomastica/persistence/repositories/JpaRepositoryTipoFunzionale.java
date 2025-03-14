package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoFunzionale;

@Repository
public interface JpaRepositoryTipoFunzionale extends JpaRepository<TipoFunzionale, Long> {

}
