package it.eng.tz.urbamid.web.services.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShapeToponomasticaConvert {

	private static final Logger logger = LoggerFactory.getLogger(ShapeToponomasticaConvert.class.getName());
	
	public static final String START = "START >>> {}";
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String idLog = "POST importShapeFile";
		logger.debug(START, idLog);
		
		try {
			
//			if(file != null && file.getSize() != 0) {
//				
//				boolean fileCorretti = false;
//				
//				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//				
//				if(extension.equalsIgnoreCase("zip"))
//					fileCorretti = true;
//				
//				if(fileCorretti) {
//					boolean responseService = service.importShapeFile(file);
//					
//					
//				} else {
//					
//				}
//				
//			}
	
		} catch (Exception e) {
			logger.error(ERROR);
			
		}
	}

}
