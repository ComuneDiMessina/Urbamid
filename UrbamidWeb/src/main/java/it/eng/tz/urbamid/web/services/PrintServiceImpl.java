package it.eng.tz.urbamid.web.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.BaseResponse;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.web.dto.PrintInput;
import it.eng.tz.urbamid.web.dto.RequestReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDTO;
import it.eng.tz.urbamid.web.pageController.PianoRegolatoreCtrl;
import it.eng.tz.urbamid.web.util.IConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service(value = "print")
public class PrintServiceImpl implements PrintService {

	private static final Logger logger = LoggerFactory.getLogger(PrintServiceImpl.class.getName());

	@Autowired
	private Environment env;
	
	@SuppressWarnings("unchecked")
	@Override
	public void print(String denom) throws Exception {
		
//		long startTime = System.nanoTime();
//		long endTime = 0L;
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");  
//		Date date = new Date();  
//		    
//		logger.debug("Definisco i path pathTemplateReport e templateReport");
//		String pathTemplateReport = env.getProperty("print.export.base.path") + env.getProperty("print.export.template.path");
//		String templateReport = pathTemplateReport + File.separator + "Stampa_A4_v.jasper";
//		logger.debug("Definisco i path pathTemplateReport {} e templateReport {}.",pathTemplateReport,templateReport);
//		
//		/**CREATE PATH**/
//		logger.debug("Definisco il path del file da creare");
//		String baseExportPath = env.getProperty("print.export.base.path");
//		LocalDate currentDate = LocalDate.now();
//		String exportPath = baseExportPath + File.separator + currentDate.getYear() + currentDate.getMonth().getValue()+currentDate.getDayOfMonth() + File.separator;
//		File dir = new File(exportPath);
//		dir.mkdir();
//		/**CREATE NAME FILE**/
//		File pdfFile = new File(exportPath + formatter.format(date) + "_print.pdf");
//		logger.debug("Definisco il path del file da creare {}.",exportPath + formatter.format(date) + "_print.pdf");
//		
//		logger.debug("Leggo il file passato in input");
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile multipartFile = multipartRequest.getFile("file");
//		
//		File targetFile = null;
//		try {		
//			InputStream initialStream = multipartFile.getInputStream();
//			byte[] buffer = new byte[initialStream.available()];
//			initialStream.read(buffer);
//	
//			targetFile = new File(pathTemplateReport + File.separator + "image.jpeg");
//	
//			try (OutputStream outStream = new FileOutputStream(targetFile)) {
//			    outStream.write(buffer);
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		logger.debug("Ho letto il file e salvato al path {}.",pathTemplateReport + File.separator + "image.jpeg");
//		
//		return result;

	}

}
