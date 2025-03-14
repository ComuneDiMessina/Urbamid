package it.eng.tz.urbamid.core.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "it.eng.tz.urbamid" })
@PropertySource(value = { "classpath:configuration.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
//@EnableJms
public class UrbamidConfiguration {
	
	@Autowired
	Environment environment;

	private static final Logger logger = LoggerFactory.getLogger(UrbamidConfiguration.class.getName());
	
	
	@Bean("objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}

	@Bean("datasource")
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty("urbamid.datasource.url"));
		driverManagerDataSource.setUsername(environment.getProperty("urbamid.datasource.username"));
		driverManagerDataSource.setPassword(environment.getProperty("urbamid.datasource.password"));
		driverManagerDataSource.setDriverClassName(environment.getProperty("urbamid.datasource.driver"));
		return driverManagerDataSource;
	}
	
	@Bean(value="transactionManager")
	public PlatformTransactionManager txMgr() {
		DataSourceTransactionManager result = new DataSourceTransactionManager();
		result.setDataSource(this.dataSource());
		return result;
	}
		
}
