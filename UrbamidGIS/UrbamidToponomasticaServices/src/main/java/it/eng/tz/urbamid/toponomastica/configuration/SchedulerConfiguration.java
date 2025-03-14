package it.eng.tz.urbamid.toponomastica.configuration;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import it.eng.tz.urbamid.toponomastica.batch.job.GeocodingReverseGeocodingJob;
import it.eng.tz.urbamid.toponomastica.batch.quartz.SpringJobFactory;

@Configuration
public class SchedulerConfiguration {

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		
		SpringJobFactory jobFactory = new SpringJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
			Trigger simpleJobTrigger) throws IOException {
		
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		factory.setTriggers(simpleJobTrigger);
		return factory;
	}
	
	@Bean
	public SimpleTriggerFactoryBean geocodingReverseGeocodingJobTrigger(
			@Qualifier("geocodingReverseGeocodingJobDetail") JobDetail jobDetail,
			@Value("${codiging.reverseGeocoding.job.frequency}") long frequency) {
		
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(frequency);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factoryBean;
	}
	
	@Bean
	public Properties quartzProperties() throws IOException {
		
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
	
	@Bean
	public JobDetailFactoryBean geocodingReverseGeocodingJobDetail() {
		
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(GeocodingReverseGeocodingJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}
}
