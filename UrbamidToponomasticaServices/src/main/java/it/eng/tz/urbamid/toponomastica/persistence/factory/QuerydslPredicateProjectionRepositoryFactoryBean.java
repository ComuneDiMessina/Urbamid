package it.eng.tz.urbamid.toponomastica.persistence.factory;
import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
/**
 * Factory bean per la creazione del {@link QuerydslPredicateProjectionRepositoryFactory}
 * @author angelo
 *
 * @param <T> classe che identifica il predicato della querydsl e che estende {@link Repository} di tipo {@link S} e {@link ID}
 * @param <S> paramtro di tipo che identificà l'entità che verrà manipolata dal {@link Repository}
 * @param <ID> parametro di tipo che identifica il tipo dell'ID utilizzato per identificare una tupla nel database
 */
public class QuerydslPredicateProjectionRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> 
														extends JpaRepositoryFactoryBean<T, S, ID> {
    public QuerydslPredicateProjectionRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new QuerydslPredicateProjectionRepositoryFactory(entityManager);
    }
}