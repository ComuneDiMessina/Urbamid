package it.eng.tz.urbamid.catasto.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.eng.tz.urbamid.catasto.persistence.factory.QuerydslPredicateProjectionRepositoryFactoryBean;

@Configuration
@EnableTransactionManagement
@EnableCaching
@EnableJpaRepositories(basePackages = {
		"it.eng.tz.urbamid.catasto.persistence.repositories" }, //indico dove sono i reposotories
		repositoryFactoryBeanClass=QuerydslPredicateProjectionRepositoryFactoryBean.class //utilizzo questo factory bean
		)
@PropertySource(value = { "classpath:application.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
@ComponentScan(basePackages = {"it.eng.tz.urbamid.catasto"})
@Profile("default")
public class DBConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConfiguration.class.getName());
	
	@Autowired
	private Environment env;

	private HikariConfig hikaryConfig() throws Exception {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(env.getProperty("catasto.db.url"));
		String schema = env.getProperty("catasto.db.schema");
		if (StringUtils.hasText(schema)) {
			config.setSchema(schema);
		}
		config.setUsername(env.getProperty("catasto.db.username"));
		config.setPassword(env.getProperty("catasto.db.password"));
		config.setDriverClassName(env.getProperty("catasto.db.driver"));
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setIdleTimeout(Integer.parseInt(env.getProperty("catasto.db.maxIdleTime")));
		config.setConnectionTestQuery(env.getProperty("catasto.db.validationQuery"));
		config.setPoolName("catasto Hikari Pool");
		config.setMaximumPoolSize(env.getProperty("catasto.db.max.pool.dimension", Integer.class, 10)); // Dimensione massima del pool
		config.setMinimumIdle(env.getProperty("catasto.db.min.idle", Integer.class, 5)); // numero minimo di connessioni idle da tenere in vita
		return config;
	}

	@Bean
	public DataSource datasource() throws Exception {
		DataSource result = new HikariDataSource(hikaryConfig());
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inizio procedura controllo e migrazione versione DB");
		}
		FluentConfiguration fc = new FluentConfiguration();
		fc.baselineOnMigrate(true);
		fc.locations("db/migration/postgres");
		String schema = env.getProperty("catasto.db.schema");
		if (StringUtils.hasText(schema)) {
			fc.schemas(schema.split(","));
		}
		fc.dataSource(result);
		Flyway fl = new Flyway(fc);
		fl.migrate();
		return result;
	}

	private final Properties hibProps() {
		Properties props = new Properties();
		props.put(org.hibernate.cfg.AvailableSettings.DIALECT, env.getProperty("catasto.db.hibernate.dialect"));
		props.put(org.hibernate.cfg.AvailableSettings.SHOW_SQL, Boolean.valueOf(env.getProperty("catasto.db.hibernate.show.sql")));
		props.put(org.hibernate.cfg.AvailableSettings.GENERATE_STATISTICS, Boolean.valueOf(env.getProperty("catasto.db.hibernate.generate.statistics")));
		props.put(org.hibernate.cfg.AvailableSettings.FORMAT_SQL, Boolean.valueOf(env.getProperty("catasto.db.hibernate.format.sql")));
		String schema = env.getProperty("catasto.db.schema");
		if (StringUtils.hasText(schema)) {
			props.put(org.hibernate.cfg.AvailableSettings.DEFAULT_SCHEMA, schema);
		}
		props.put(org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE,	Boolean.valueOf(env.getProperty("catasto.db.hibernate.use.second.cache")));
		props.put(org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE, Boolean.valueOf(env.getProperty("catasto.db.hibernate.use.query.cache")));
		props.put(org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE, Integer.valueOf(env.getProperty("catasto.db.hibernate.batch.size")));
		props.put(org.hibernate.cfg.AvailableSettings.CACHE_REGION_FACTORY, CatastoCacheRegionFactory.class.getName());
		props.put("hibernate.javax.cache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Valorizzate hibernate properties {}", props);
		}
		return props;
	}

	@Bean
	@DependsOn(value = { "cacheManager" })
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSource);
		em.setPackagesToScan("it.eng.tz.urbamid.catasto.persistence.model");
		em.setPersistenceUnitName("rubrica.jpa.unit");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibProps());
		return em;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
