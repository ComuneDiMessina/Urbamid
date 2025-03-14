package it.eng.tz.urbamid.toponomastica.batch.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.eng.tz.urbamid.toponomastica.service.ToponimoStradaleService;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDugDTO;

public class GeocodingReverseGeocodingJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(GeocodingReverseGeocodingJob.class.getName());
	
	@Autowired
	private ToponimoStradaleService toponimoStradaleService;
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		
		String idLog = "populateTableGeoCodingAndReverseJob";	
		
		try {
			
			logger.info("START >>> " + idLog);
			logger.info("Popolare tabella per il Geocoding e reverseGeocoding");
			List<ToponimoDugDTO> list = toponimoStradaleService.ricercaToponimoByDug("Viale Europa");
			logger.info("Ci sono "+list.size()+" toponimi di viale europa");
			
		} catch (Exception e) {
			
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
}
