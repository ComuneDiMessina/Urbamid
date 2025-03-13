package it.eng.tz.urbamid.administration.mappa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.Tema;


/**
 * @author Salvatore Mariniello
 */
@Repository
public class TemaDao extends GenericDao<Tema> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_TEMA = "INSERT INTO public.u_admin_tema(id, idpadre, nome, descrizione, ordinamento, data_creazione, data_modifica, utente_creazione, utente_modifica) VALUES (nextval('u_tema_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE_TEMA = "UPDATE public.u_admin_tema SET idpadre=?, nome=?, descrizione=?, ordinamento=?, data_modifica=?, utente_modifica=? WHERE id=?";
	private static final String SQL_ALL_TEMA = "SELECT * FROM public.u_admin_tema ORDER BY nome ASC";
	private static final String SQL_FIND_TEMA = "SELECT * FROM public.u_admin_tema WHERE id = ?";
	private static final String SQL_NOME_TEMA_FIND = "SELECT nome FROM public.u_admin_tema WHERE nome = ?";
	private static final String SQL_DELETE_SELECTEDS = "DELETE FROM public.u_admin_tema WHERE id IN (?)";
	private static final String SQL_DELETE_ID = "DELETE FROM public.u_admin_tema WHERE id = ?";
	private static final String SQL_TEMA_TO_MAPPA_IN = "SELECT id_mappa FROM public.u_admin_mappa_tema WHERE id_tema IN (:ids)";
	private static final String SQL_TEMA_TO_MAPPA = "SELECT id_mappa FROM public.u_admin_mappa_tema WHERE id_tema=?";
	private Integer idCurrent=null;

	public Tema getTema(Long id) {
		logger.debug("getTema");
		return (Tema) getJdbcTemplate().queryForObject(SQL_FIND_TEMA, new TemaRowMapper(), id);
	}
	
	public List<Tema> getAllTemi() {
		logger.debug("getAllTemi");
 
	 return getJdbcTemplate().query(SQL_ALL_TEMA, new TemaRowMapper());
	}
	
	public void insert(Tema tema) { 
		getJdbcTemplate().update(SQL_INSERT_TEMA, new Object[] {
				tema.getIdPadre(),
				tema.getNome(), 
				tema.getDescrizione(), 
				tema.getOrdinamento(), 
				tema.getDataCreazione(),
				tema.getDataModifica(),
				tema.getUtenteCreazione(),
				tema.getUtenteModifica()});
	}
	
	public int delete(Integer id) throws Exception{ 
		
		int row=getJdbcTemplate().update(SQL_DELETE_ID,new Object[] {id.intValue()});
		 
		return row;
	}
	
	public int[] deletes(List<Integer> ids) throws Exception{ 
		
		int[] argTypes = {Types.INTEGER };	
		
		List<Object[]> batchArgs = new ArrayList<>();
		for(Integer i : ids)
		batchArgs.add(new Object[] {i.intValue()});

	   int[] rows=	getJdbcTemplate().batchUpdate(SQL_DELETE_ID, batchArgs, argTypes);
	   
	   return rows;

	}
	
	
	public List<Integer> getMappeToTema(List<Integer> ids) throws Exception{ 
		
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		List<Integer> listaId = template.query(SQL_TEMA_TO_MAPPA_IN, parameters, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Integer(rs.getInt("id_mappa"));
			}
		});
	   
	   
	   
	   return listaId;

	}
	
	
	public Integer getNewId(String sequenceName) { 
		
		return getJdbcTemplate().queryForObject("SELECT nextval('"+sequenceName+"');", Integer.class);
	}
	
	public List<Integer> getMappeToTema(Long idTema){
		List<Integer> listaId = getJdbcTemplate().query(SQL_TEMA_TO_MAPPA, new Object[] { idTema}, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Integer(rs.getInt("id_mappa"));
			}
		});
		
	return listaId;
	}
	
	public String saveOrUpdateTema(TemaBean tema) throws Exception {
		StopWatch sw = null;
		try{
		//	Integer idSequence=getNewId("u_tema_id_seq");
			sw = new StopWatch("Salva il tema");
			sw.start();	
			if( tema.getIsNew() ){
				tema.setDataCreazione(new Date());
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_TEMA);
				}
				getJdbcTemplate().update(SQL_INSERT_TEMA,new Object[] { 
						tema.getIdPadre(),
						tema.getNome(), 
						tema.getDescrizione(), 
						tema.getOrdinamento(), 
						tema.getDataCreazione(),
						tema.getDataModifica(),
						tema.getUtenteCreazione(),
						tema.getUtenteModifica()});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_NOME_TEMA_FIND);
				}
				List<String> listaId = getJdbcTemplate().query(SQL_NOME_TEMA_FIND, new Object[] { tema.getNome()}, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("nome");
					}
				});
				return listaId.get(0);
			} else {
				
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_TEMA);
				}
				 
				getJdbcTemplate().update(SQL_UPDATE_TEMA,new Object[] { 
						tema.getIdPadre(),
						tema.getNome(), 
						tema.getDescrizione(), 
						tema.getOrdinamento(), 
						tema.getDataModifica(),
						tema.getUtenteModifica(),
						tema.getId()});
				 return tema.getNome();
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio del tema :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
}
