package it.eng.tz.urbamid.prg.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndiceCodici;
import it.eng.tz.urbamid.prg.web.dto.CodiceZtoDTO;

@Repository
public class DocumentoIndiceCodiceDao extends GenericDao<DocumentoIndiceCodici> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_CODICE = "INSERT INTO public.u_prg_doc_indice_codici" +
			"	(id_indice, codice, descrizione)" + 
			"    VALUES (?, ?, ?)";

	private static final String SQL_SELECT_ZTO = "SELECT zto, zto_1 FROM public.u_geo_zto GROUP BY zto, zto_1 ORDER BY zto_1 ASC";
	
	public void insertCodice(Long idIndice, String nomeLayer, String descrizioneLayer) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_CODICE,new Object[] { 
						idIndice,
						nomeLayer,
						descrizioneLayer
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_CODICE);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio del codice :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public List<CodiceZtoDTO> cercaCodiceZTO() {
		
		return getJdbcTemplate().query(SQL_SELECT_ZTO, new RowMapper<CodiceZtoDTO>() {

			@Override
			public CodiceZtoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				CodiceZtoDTO codiceZto = new CodiceZtoDTO();
			
				codiceZto.setCodice(rs.getString("zto_1"));
				codiceZto.setDescrizione(rs.getString("zto"));
				
				return codiceZto;
			}
			
		});
		
	}

}
