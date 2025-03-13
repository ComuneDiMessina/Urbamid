package it.eng.tz.urbamid.catasto.persistence.custom.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.support.PageableExecutionUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.sql.JPASQLQuery;

import it.eng.tz.urbamid.catasto.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
/**
 * Classe che implementa il {@link QuerydslPredicateProjectionRepository} ed estende {@link QuerydslJpaPredicateExecutor}
 * @author angelo
 *
 * @param <T> - l'entity per la quale la classe viene utilizzata
 */
public class QuerydslPredicateProjectionRepositoryImpl<T> extends QuerydslJpaPredicateExecutor<T>
		implements QuerydslPredicateProjectionRepository<T> {
	
	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;
	private final Querydsl querydsl;
	/**
	 * Costruttore della classe
	 * @param entityInformation - {@link JpaEntityInformation} che restituisce informazioni sulla entity
	 * @param entityManager - {@link EntityManager} da utilizzare
	 */
	public QuerydslPredicateProjectionRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {
		this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
	}
	/**
	 * Costruttore della classe
	 * @param entityInformation - {@link JpaEntityInformation} che restituisce informazioni sulla entity
	 * @param entityManager - {@link EntityManager} da utilizzare
	 * @param resolver - il {@link EntityPathResolver}
	 */
	public QuerydslPredicateProjectionRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager, EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver, null);

		EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());
		PathBuilder<T> builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}
	/**
	 * Costruttore della classe
	 * @param entityInformation - {@link JpaEntityInformation} che restituisce informazioni sulla entity
	 * @param entityManager - {@link EntityManager} da utilizzare
	 * @param resolver - il {@link EntityPathResolver}
	 * @param metadata - il {@link CrudMethodMetadata}
	 */
	public QuerydslPredicateProjectionRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager, EntityPathResolver resolver, CrudMethodMetadata metadata) {
		super(entityInformation, entityManager, resolver, metadata);
		EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());
		PathBuilder<T> builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Projection> List<Projection> findAll(Predicate predicate, FactoryExpression<Projection> factoryExpression) {
		return createQuery(predicate).select(factoryExpression).fetch();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Projection> List<Projection> findAll(Predicate predicate, Sort sort,
			FactoryExpression<Projection> factoryExpression) {
		JPQLQuery<Projection> query = createQuery(predicate).select(factoryExpression);
		querydsl.applySorting(sort, query);

		return query.fetch();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Projection> Page<Projection> findAll(Predicate predicate, Pageable pageable,
			FactoryExpression<Projection> factoryExpression) {
		JPQLQuery<Projection> query = createQuery(predicate).select(factoryExpression);
		querydsl.applyPagination(pageable, query);
		querydsl.applySorting(pageable.getSort(), query);

		QueryResults<Projection> queryResults = query.fetchResults();
		return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Projection> Page<Projection> findAll(JPQLQuery<Projection> query, Pageable pageable) {
		querydsl.applyPagination(pageable, query);
		querydsl.applySorting(pageable.getSort(), query);		
		return PageableExecutionUtils.getPage(query.fetch(), pageable, query::fetchCount);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Projection> List<Projection> findAll(JPQLQuery<Projection> query, Sort sort) {
		querydsl.applySorting(sort, query);
		return query.fetch();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <Projection> List<Projection> findAll(JPQLQuery<Projection> query) {
		
		return query.fetch();
	}
    /**
     * {@inheritDoc}
     */
    @Override
    public <Projection> List<Projection> findAll(JPASQLQuery<Projection> query) {
        return query.fetch();
    }
}