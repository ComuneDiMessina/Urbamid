package it.eng.tz.urbamid.profilemanager.persistence.custom.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.sql.JPASQLQuery;
/**
 * Repository custom per utilizzare le projection tramite QueryDSL
 * @author angelo
 *
 * @param <T> - la entity JPA per la quale il repository viene utilizzato
 */
public interface QuerydslPredicateProjectionRepository<T> {
	/**
	 * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
	 * La ricerca è paginata tramite {@link Pageable} e permette di restituire in uscita un oggetto custom (il DTO)
	 * utilizzando il {@link FactoryExpression}
	 * @param predicate - i filtri da applicare sulla query. Può essere nullo
	 * @param pageable - i riferimenti della paginazione tramite {@link Pageable}. In questo oggetto è possibile indicare anche il {@link Sort}
	 * @param factoryExpression - il {@link FactoryExpression} da utilizzare per avere in uscita l'oggetto desiderato
	 * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
	 */
	<Projection> Page<Projection> findAll(Predicate predicate, Pageable pageable, FactoryExpression<Projection> factoryExpression);
	/**
	 * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
	 * La ricerca non è paginata ma ordinata tramite {@link Sort} e permette di restituire in uscita un oggetto custom (il DTO)
	 * utilizzando il {@link FactoryExpression}
	 * @param predicate - i filtri da applicare sulla query. Può essere nullo
	 * @param sort - i riferimenti dell'ordinamento {@link Sort}
	 * @param factoryExpression - il {@link FactoryExpression} da utilizzare per avere in uscita l'oggetto desiderato
	 * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
	 */	
	<Projection> List<Projection> findAll(Predicate predicate, Sort sort, FactoryExpression<Projection> factoryExpression);
	/**
	 * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
	 * La ricerca non è paginata e non è ordinata. Permette di restituire in uscita un oggetto custom (il DTO)
	 * utilizzando il {@link FactoryExpression}
	 * @param predicate - i filtri da applicare sulla query. Può essere nullo
	 * @param factoryExpression - il {@link FactoryExpression} da utilizzare per avere in uscita l'oggetto desiderato
	 * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
	 */		
	<Projection> List<Projection> findAll(Predicate predicate, FactoryExpression<Projection> factoryExpression);
	
	/**
	 * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
	 * La ricerca è paginata tramite {@link Pageable} e permette di restituire in uscita un oggetto custom (il DTO)
	 * utilizzando il {@link FactoryExpression}
	 * @param query -{@link JPQLQuery} i filtri da applicare sulla query. Può essere nullo
	 * @param pageable - {@link Pageable} i riferimenti della paginazione tramite {@link Pageable}. In questo oggetto è possibile indicare anche il {@link Sort}
	 * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
	 */
	<Projection> Page<Projection> findAll(JPQLQuery<Projection> query, Pageable pageable);
	/**
	 * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
	 * La ricerca non è paginata ma ordinata tramite {@link Sort} e permette di restituire in uscita un oggetto custom (il DTO)
	 * utilizzando il {@link FactoryExpression}
	 * @param query - i filtri da applicare sulla query. Può essere nullo
	 * @param sort - i riferimenti dell'ordinamento {@link Sort}
	 * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
	 */	
	<Projection> List<Projection> findAll(JPQLQuery<Projection> query, Sort sort);
	/**
	 * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
	 * La ricerca non è paginata e non è ordinata. Permette di restituire in uscita un oggetto custom (il DTO)
	 * utilizzando il {@link FactoryExpression}
	 * @param query - i filtri da applicare sulla query. Può essere nullo
	 * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
	 */		
	<Projection> List<Projection> findAll(JPQLQuery<Projection> query);
    /**
     * Effettua la ricerca dei record in base a determinati filtri creati con {@link Predicate}.
     * La ricerca non è paginata e non è ordinata. Permette di restituire in uscita un oggetto custom (il DTO)
     * utilizzando il {@link FactoryExpression}
     * @param query - i filtri da applicare sulla query. Può essere nullo
     * @return - i risultati della query
	 * @param <Projection> Projection utilizzata per ricostruire l'entità ottenuta dal database eg. {@link GruppoDtoEntity} per la query di un {@link Gruppo}
     */
    <Projection> List<Projection> findAll(JPASQLQuery<Projection> query);
}