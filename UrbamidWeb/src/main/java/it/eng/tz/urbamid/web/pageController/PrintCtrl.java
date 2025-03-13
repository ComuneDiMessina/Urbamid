package it.eng.tz.urbamid.web.pageController;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.web.dto.PrintInput;
import it.eng.tz.urbamid.web.services.PrintService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


/**
 * @author Alessandro Paolillo
 */
@Controller(value = "Print Controller")
@RequestMapping("/printController")
public class PrintCtrl extends AbstractController  {

	private static final Logger logger = LoggerFactory.getLogger(PrintCtrl.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private Environment env;
	
	@Autowired
	private PrintService printService;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	
	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param files
	 * @return
	 * @throws CatastoServiceException
	 */
	@PostMapping(value="/printMap")
	public @ResponseBody void printMap(HttpServletRequest request,
			@RequestBody MultipartFile file,
			@RequestParam(value = "formato", required = false) String formato,
			HttpServletResponse response) {
		
		long startTime = System.nanoTime();
		long endTime = 0L;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");  
		Date date = new Date();  
		String baseExportPath = env.getProperty("print.export.base.path");
		String pathTemplateReport = env.getProperty("print.export.template.path");
		String templateReport = pathTemplateReport;
		if (formato.equals("a4v")) 
			templateReport += "StampaA4_v.jasper";
		String imageTemplateReport = env.getProperty("print.export.images.path");
		
		logger.debug(">>>>>>>>> CREO IL FILE");
		/**CREATE PATH**/
		LocalDate currentDate = LocalDate.now();
		String exportPath = baseExportPath + currentDate.getYear() + currentDate.getMonth().getValue()+currentDate.getDayOfMonth() + File.separator;
		File dir = new File(exportPath);
		dir.mkdir();
		/**CREATE NAME FILE**/
		File pdfFile = new File(exportPath + formatter.format(date) + "_print.pdf");
		
		try {
			byte[] data = file.getBytes();
			try (OutputStream stream = new FileOutputStream(imageTemplateReport + "image.jpeg")) {
			    stream.write(data);
			}catch(IOException e){  
		        e.printStackTrace();
		        response.setStatus(500);
		    }
		}catch(IOException e){  
	        e.printStackTrace();
	        response.setStatus(500);
	    }
		
		try {
			
			PrintInput input = new PrintInput();
			if (pdfFile.createNewFile()) {
		        JRMapArrayDataSource mapArray = new JRMapArrayDataSource(new Object[]{input.getDataSources()});
		        JasperPrint jasperPrint = JasperFillManager.fillReport(
		        		templateReport,
		        		input.getDataSources(), 
		        		mapArray
		        );
		        JRPdfExporter print = new JRPdfExporter();
		        print.setExporterInput(new SimpleExporterInput(jasperPrint));
		        print.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
		        print.exportReport();
			}
	        
	        endTime = System.nanoTime();
	        logger.debug(">>>>>>>>> SCRIVO IL PDF >>>>>>>>>> TIME CREA PDF: {} secondi",((double) (endTime - startTime) / 1_000_000_000));
	        
	        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(pdfFile));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
	        
		    PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(base64String);
			
		} catch (JRException e) {
			
			logger.error(e.getMessage());
		} catch (IOException e) {
			
			logger.error(e.getMessage());
		}
	}
	
//	@PostMapping(value="/upload")
//	public @ResponseBody ResponseData uploadDocumenti(HttpServletRequest request, @RequestBody MultipartFile file, 
//			  @RequestParam(value = "risorsa", required = false) Long risorsa,
//			  @RequestParam(value = "tipo", required = true) Long tipoRisorsa) {
//		logger.debug("GET upload");
//		ResponseData response = null;
//		try {
//			
//			if(file.getSize() != 0) {
//												
//				TipoRisorsaDTO tipo = new TipoRisorsaDTO();
//				tipo.setId(tipoRisorsa);
//				DocumentoStorageDTO dto = new DocumentoStorageDTO();
//				dto.setNomeDocumento(StringUtils.cleanPath(file.getOriginalFilename()));
//				dto.setFile(Base64Utils.encode(file.getBytes()));
//				dto.setIdRisorsa(risorsa);
//				dto.setTipoRisorsa(tipo);
//				toponomasticaService.upload(dto);
//				response = new ResponseData(true, dto, 1, 1, null);
//				response.setMessage(SUCCESS);
//			} else {
//				
//				logger.debug("Errore in upload");
//				response = new ResponseData(false, null,0, 0, null);
//				response.setMessage(ERROR);
//			}
//		} catch (Exception e) {
//			
//			logger.debug("Errore in upload: {}", e, e.getMessage());
//			response = new ResponseData(false, null,0, 0, null);
//			response.setMessage(ERROR);
//		}
//		return response;
//	}
}
