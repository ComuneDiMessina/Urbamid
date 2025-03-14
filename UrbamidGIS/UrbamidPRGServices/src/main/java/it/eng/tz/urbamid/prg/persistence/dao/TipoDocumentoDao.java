package it.eng.tz.urbamid.prg.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoVarianteDTO;

/**
 * @author Luca Tricarico
 */
@Repository
public class TipoDocumentoDao extends GenericDao<TipoDocumento>{

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_TIPO_DOCUMENTO = "INSERT INTO public.u_prg_tipo_documento(id, codice, descrizione, descrizione_cdu, note) VALUES (nextval('u_prg_documento_id_seq'), ?, ?, ?, ?)";
	private static final String SQL_UPDATE_TIPO_DOCUMENTO = "UPDATE public.u_prg_tipo_documento SET descrizione=?, descrizione_cdu=?, note=? WHERE id = ?";
	private static final String SQL_DELETE_TIPO_DOCUMENTO = "DELETE FROM public.u_prg_tipo_documento WHERE id = ?";
	private static final String SQL_SEL_TIPO_DOCUMENTO 	  = "SELECT * FROM public.u_prg_tipo_documento";
	private static final String SQL_SELECT_TIPO_DOCUMENTO_VARIANTE = "SELECT doc.id_documento, doc.tipo_documento, v.nome\r\n" + 
																	 "FROM public.u_prg_doc_variante doc JOIN public.u_prg_variante AS v ON doc.id_variante = v.id_variante\r\n" + 
																	 "WHERE UPPER(doc.tipo_documento) = UPPER(?)";
	
	public void insertDocumento(TipoDocumento documento) {
		
		logger.debug("insertDocumento");
		
		getJdbcTemplate().update(SQL_INSERT_TIPO_DOCUMENTO, new Object[] { documento.getCodice().toUpperCase(),
																	  documento.getDescrizione(),
																	  documento.getDescrizioneCDU(),
																	  documento.getNote() });
		
	}
	
	public void updateDocumento(TipoDocumento documento) {
		
		logger.debug("updateDocumento");
		
		getJdbcTemplate().update(SQL_UPDATE_TIPO_DOCUMENTO, new Object[] { documento.getDescrizione(),
																	  documento.getDescrizioneCDU(),
																	  documento.getNote(),
																	  documento.getId() });
		
	}
	
	public void deleteDocumento(Long id) {
		
		logger.debug("deleteDocumento");
		
		getJdbcTemplate().update(SQL_DELETE_TIPO_DOCUMENTO, id);
		
	}
	
	public List<TipoDocumento> findAll() {
		
		return getJdbcTemplate().query(SQL_SEL_TIPO_DOCUMENTO, new RowMapper<TipoDocumento>() {

			@Override
			public TipoDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				TipoDocumento tipo = new TipoDocumento();
				
				tipo.setId(rs.getLong("id"));
				tipo.setCodice(rs.getString("codice"));
				tipo.setDescrizione(rs.getString("descrizione"));
				tipo.setDescrizioneCDU(rs.getString("descrizione_cdu"));
				tipo.setNote(rs.getString("note"));
				
				return tipo;
			}
			
		});
		
	}
	
	public List<TipoDocumentoVarianteDTO> varianteByTipoDocumento(String tipoDocumento) {
		
		return getJdbcTemplate().query(SQL_SELECT_TIPO_DOCUMENTO_VARIANTE, new RowMapper<TipoDocumentoVarianteDTO>() {

			@Override
			public TipoDocumentoVarianteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				TipoDocumentoVarianteDTO documento = new TipoDocumentoVarianteDTO();
				
				documento.setIdDocumento(rs.getLong("id_documento"));
				documento.setTipoDocumento(rs.getString("tipo_documento"));
				documento.setNomeVariante(rs.getString("nome"));
				
				return documento;
			}
			
		}, tipoDocumento);
		
	}
	
}
