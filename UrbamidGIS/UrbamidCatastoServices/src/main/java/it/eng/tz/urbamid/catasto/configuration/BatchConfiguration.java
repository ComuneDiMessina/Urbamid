package it.eng.tz.urbamid.catasto.configuration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import it.eng.tz.urbamid.catasto.batch.JobCxfToShape;
import it.eng.tz.urbamid.catasto.batch.JobImportDatiCatastali;
import it.eng.tz.urbamid.catasto.batch.JobShapeToDB;
import it.eng.tz.urbamid.catasto.batch.JobStoreImport;
import it.eng.tz.urbamid.catasto.geokettle.service.GeoKettleService;
import it.eng.tz.urbamid.catasto.python.service.PythonService;
import it.eng.tz.urbamid.catasto.shapefile.service.ShapefileService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.storeimport.service.StoreImportService;
import it.eng.tz.urbamid.catasto.storico.service.StoricoService;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	public static final String STEP_KITCHEN_SH_DATI_CATASTALI = "STEP_KITCHEN_SH_DATI_CATASTALI";
	public static final String STEP_KITCHEN_SH_CXF_TO_SHAPE = "STEP_KITCHEN_SH_CXF_TO_SHAPE";
	public static final String STEP_SHAPEFILES_TO_DB = "STEP_SHAPEFILES_TO_DB";
	public static final String STEP_STOREIMPORT = "STEP_STOREIMPORT";
	public static final String JOB_IMPORT_DATI_CATASTALI_NAME = "JOB_IMPORT_DATI_CATASTALI";
	
	//parametri avvio job
	public static final String JOB_PARAMETER_UUID = "UUID";
	public static final String JOB_PARAMETER_DATA_AVVIO = "DATA_AVVIO";
	public static final String JOB_PARAMETER_LIVELLO_LOG = "LIVELLO_LOG";
	public static final String JOB_PARAMETER_TIPO_JOB = "TIPO_JOB";
	
	//job execution context key per il recupero delle informazioni di log
	public static final String KEY_LOG_STEP_IMPORT_DATI_CATASTALI = "logImportDatiCatastali";
	public static final String KEY_LOG_STEP_CXF_TO_SHAPE = "logCxfToShape";
	public static final String KEY_LOG_STEP_SHAPE_TO_DB = "logShapeToDb";
	
	/**
	 * Bean per il Job launcher asincrono
	 * @return {@link JobLauncher}
	 */
	@Bean("asynchronousJobLauncher")
	public JobLauncher asynchronousJobLauncher(JobRepository jobRepository, JobOperator jobOperator) {
		final SimpleJobLauncher asynchronousJobLauncher = new SimpleJobLauncher();
		asynchronousJobLauncher.setJobRepository(jobRepository);
		final SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asynchronousJobLauncher.setTaskExecutor(simpleAsyncTaskExecutor);
		return asynchronousJobLauncher;
	}
	
	/**
	 * Bean per lo step di lancio script kitchen.sh per il batch di import dati catastali
	 * @return {@link Step}
	 */
	@Bean(name="stepImportCatasto")
    public Step stepCatasto(StepBuilderFactory stepBuilderFactory, GeoKettleService geoKettleService, 
    		ImportCatastoPathService pathService){
        return stepBuilderFactory
        		.get(STEP_KITCHEN_SH_DATI_CATASTALI)
                .tasklet(new JobImportDatiCatastali(geoKettleService, pathService))
                .build();
    }
	
	/**
	 * Bean per lo step di lancio script kitchen.sh per il batch per la conversione dei file CXF in 
	 * Shapefile.
	 * @return {@link Step}
	 */
	@Bean(name="stepCxfToShape")
    public Step stepCxfToShape(
    		StepBuilderFactory stepBuilderFactory, GeoKettleService geoKettleService, 
    		PythonService pythonService, StoricoService storicoService, ImportCatastoPathService pathService){
        return stepBuilderFactory
        		.get(STEP_KITCHEN_SH_CXF_TO_SHAPE)
                .tasklet(new JobCxfToShape(geoKettleService, pythonService, storicoService, pathService))
                .build();
    }
	
	/**
	 * Bean per lo step di lancio della procedura di import degli Shapefile nel database.
	 * @return {@link Step}
	 */
	@Bean(name="stepShapefileToDB")
    public Step stepShapefileToDB(StepBuilderFactory stepBuilderFactory, ShapefileService shapefileService,
    		ImportCatastoPathService pathService){
        return stepBuilderFactory
        		.get(STEP_SHAPEFILES_TO_DB)
                .tasklet(new JobShapeToDB(shapefileService, pathService))
                .build();
    }
	
	/**
	 * Bean per lo step di lancio della procedura di salvataggio degli Shapefile ed eliminazione della cartella.
	 * @return {@link Step}
	 */
	@Bean(name="stepStoreImportCatasto")
    public Step stepStoreImportCatasto(StepBuilderFactory stepBuilderFactory, StoreImportService storeImportService,
    		ImportCatastoPathService pathService){
        return stepBuilderFactory
        		.get(STEP_STOREIMPORT)
                .tasklet(new JobStoreImport(storeImportService, pathService))
                .build();
    }
	
	@Bean
	public RunIdIncrementer runIdIncrementer() {
		return new RunIdIncrementer();
	}
	
}
