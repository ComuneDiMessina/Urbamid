package it.eng.tz.urbamid.prg.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoVariante;

@Repository
public class DocumentoVarianteDao extends GenericDao<DocumentoVariante> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_UPDATE_DOCUMENTOE = "UPDATE public.u_prg_doc_variante " + 
			"	SET id_variante=?, tipo_documento=?, nome_documento=?, stato_documento=?, path_documento=?, estensione=?" + 
			" 	WHERE id_documento=?";

	private static final String SQL_INSERT_DOCUMENTO = "INSERT INTO public.u_prg_doc_variante( " + 
			"			id_variante, tipo_documento, nome_documento, stato_documento, path_documento, estensione)" + 
			"    		VALUES (?, ?, ?, ?, ?, ?)";

	public void updateDocumento(DocumentoVariante documento) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Modifica il documento");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_DOCUMENTOE,new Object[] { 
						documento.getVariante().getIdVariante(),
						documento.getTipoDocumento(),
						documento.getNomeDocumento(),
						documento.getStatoDocumento(),
						documento.getPathDocumento(),
						documento.getEstensione(),
						documento.getIdDocumento()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_DOCUMENTOE);
				}
		}catch (Exception e){
			String message = "Errore nella modifica del documento :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

	public void insertDocumento(DocumentoVariante documento) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salvo il documento");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_DOCUMENTO,new Object[] { 
						documento.getVariante().getIdVariante(),
						documento.getTipoDocumento(),
						documento.getNomeDocumento(),
						documento.getStatoDocumento(),
						documento.getPathDocumento(),
						documento.getEstensione()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_DOCUMENTO);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio del documento :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

}
