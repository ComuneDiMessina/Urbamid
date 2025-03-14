package it.eng.tz.urbamid.catasto.batch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.batch.core.scope.context.ChunkContext;

import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;

public abstract class AbstractJob {
	
	protected abstract ImportCatastoPathService getPathService();
	
	protected abstract Logger getLogger();

	protected abstract ImportType getBatchType();
	
	/**
	 * Metodo privato che inserisce le informazioni di log nel job execution context.
	 * Queste informazioni saranno poi disponibili nella tabella u_cat_catasto_batch_job_execution_context
	 * (serializzate però).
	 * 
	 * @param chunkContext {@link ChunkContext}
	 */
	protected void salvaInformazioniDiLog(ChunkContext chunkContext, String log) {
		
		Long jobId = chunkContext.getStepContext()
				.getStepExecution()
					.getJobExecutionId();
		
		Optional<File> logFile = this.getPathService().fileLogProceduraImport(this.getBatchType(), jobId);
		if(logFile.isPresent()) {
			try (
					BufferedWriter writer = new BufferedWriter( new FileWriter(logFile.get(), Boolean.TRUE));
					){
				writer.write(log);
				writer.flush();
			} catch (IOException ioe) {
				this.getLogger().error("Si è verificato un errore di scrittura del file di log.", ioe);
			}
				
		}
		
	}

}
