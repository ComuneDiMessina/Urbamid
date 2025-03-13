package it.eng.tz.urbamid.prg.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndice;
import it.eng.tz.urbamid.prg.web.dto.InserimentoIndiceDTO;

@Repository
public class DocumentoIndiceDao extends GenericDao<DocumentoIndice> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_INDICE = "INSERT INTO public.u_prg_doc_indice" +
			"	(id_documento, articolo, elenco_pagine)" + 
			"    VALUES (?, ?, ?)";

	public void insertIndice(InserimentoIndiceDTO indice) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_INDICE,new Object[] { 
						indice.getIdDocumento(),
						indice.getArticolo(),
						indice.getElencoPagine()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_INDICE);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio dell'indice :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

}
