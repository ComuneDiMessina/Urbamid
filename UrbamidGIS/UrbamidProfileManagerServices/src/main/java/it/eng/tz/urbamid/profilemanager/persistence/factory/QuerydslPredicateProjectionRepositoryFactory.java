package it.eng.tz.urbamid.profilemanager.persistence.factory;

import static org.springframework.data.querydsl.QuerydslUtils.QUERY_DSL_PRESENT;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFragment;

import it.eng.tz.urbamid.profilemanager.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
import it.eng.tz.urbamid.profilemanager.persistence.custom.repositories.impl.QuerydslPredicateProjectionRepositoryImpl;
/**
 * Classe che estende il {@link JpaRepositoryFactory} e che permette la creazione del 
 * repository custom {@link QuerydslPredicateProjectionRepositoryImpl}
 * @author angelo
 *
 */
public class QuerydslPredicateProjectionRepositoryFactory extends JpaRepositoryFactory {
	private final EntityManager entityManager;
	private final EntityPathResolver entityPathResolver;
	/**
	 * Costruttore della classe
	 * @param entityManager - {@link EntityManager} da utilizzare
	 */
	public QuerydslPredicateProjectionRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
		this.entityPathResolver = SimpleEntityPathResolver.INSTANCE;
	}
	/**
	 * Controlla se possibile creare un {@link RepositoryFragment} di tipo {@link QuerydslPredicateProjectionRepositoryImpl} altrimenti
	 * restituisce il {@link RepositoryFragment} originario.
	 */
	@Override
	protected RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
		RepositoryComposition.RepositoryFragments fragments = super.getRepositoryFragments(metadata);
		boolean isQueryDslRepository = QUERY_DSL_PRESENT
				&& QuerydslPredicateProjectionRepository.class.isAssignableFrom(metadata.getRepositoryInterface());

		if (isQueryDslRepository) {

			JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

			Object querydslFragment = getTargetRepositoryViaReflection(QuerydslPredicateProjectionRepositoryImpl.class,
					entityInformation, entityManager, entityPathResolver, null);

			fragments = fragments.append(RepositoryFragment.implemented(querydslFragment));
		}

		return fragments;
	}

}