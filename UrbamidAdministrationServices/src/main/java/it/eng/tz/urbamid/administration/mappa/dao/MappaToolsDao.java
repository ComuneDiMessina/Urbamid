package it.eng.tz.urbamid.administration.mappa.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaToolRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.ToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaTool;
import it.eng.tz.urbamid.administration.mappa.dao.model.Tool;
/**
 * @author Alessandro Paolillo
 */
@Repository
public class MappaToolsDao extends GenericDao<MappaTool> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String SQL_INSERT_JOIN_MAP_TOOL = "INSERT INTO u_admin_mappa_maptool(id_mappa_tool, id_mappa, id_maptool) VALUES (nextval('u_admin_mappa_maptool_id_seq'), ?, ?)";
	private final String SQL_DELETE_JOIN_MAP_TOOL = "DELETE FROM u_admin_mappa_maptool WHERE id_mappa = ?";
	private final String SQL_ALL_MAP_RICERCA = "SELECT * FROM u_admin_mappa_tool";
	private final String SQL_FIND_MAP_RICERCA = "SELECT * FROM u_admin_mappa_tool WHERE id = ?";
	private final String SQL_FIND_JOIN_MAP_TOOL = "SELECT t.* FROM public.u_admin_mappa_maptool AS j JOIN public.u_admin_mappa_tool AS t ON j.id_maptool = t.id" + 
			"									   													 JOIN public.u_admin_mappa AS m ON j.id_mappa = m.id WHERE m.id = ?";
	
	public void insertToolJoin(List<ToolBean> tools) {
		logger.debug("insertToolJoin");
		
		Long idMappa = null;
		List<Object[]> batch = new ArrayList<>();
		
		for(ToolBean i : tools) {
			
			idMappa = i.getId_mappa();
			
			if(i.getId_maptool() > 0) {
				batch.add(new Object[] { i.getId_mappa(),
						 			     i.getId_maptool() });
			}
	
		}
		
		if(idMappa != null) {
			getJdbcTemplate().update(SQL_DELETE_JOIN_MAP_TOOL, idMappa);
		}
		
		if(batch.size() > 0) {
			getJdbcTemplate().batchUpdate(SQL_INSERT_JOIN_MAP_TOOL, batch);
		}
		
	}
	
	public MappaTool getMapTool(Integer id) {
		logger.debug("getMapRicerca");
		return (MappaTool) getJdbcTemplate().queryForObject(SQL_FIND_MAP_RICERCA, new MappaToolRowMapper(), id);
	}
	
	public List<MappaTool> getAllMapTool() {
		logger.debug("getAllMapRicerca");
		return getJdbcTemplate().query(SQL_ALL_MAP_RICERCA, new MappaToolRowMapper());
	}
	
	public List<MappaTool> getMappaID(Integer idMappa) {
		logger.debug("getMappaID");
		return getJdbcTemplate().query(SQL_FIND_JOIN_MAP_TOOL, new MappaToolRowMapper(), idMappa);
	}
}
