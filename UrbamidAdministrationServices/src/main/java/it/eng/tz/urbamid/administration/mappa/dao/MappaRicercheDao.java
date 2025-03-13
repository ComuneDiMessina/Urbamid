package it.eng.tz.urbamid.administration.mappa.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRicercaRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaRicerca;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class MappaRicercheDao extends GenericDao<MappaRicerca> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String SQL_INSERT_JOIN_MAP_RICERCA = "INSERT INTO u_admin_mappa_mapricerca(id_mappa_ricerca, id_mappa, id_mapricerca) VALUES (nextval('u_admin_mappa_mapparicerca_id_seq'), ?, ?)";
	private final String SQL_DELETE_ALL_JOIN_MAP_RICERCA = "DELETE FROM u_admin_mappa_mapricerca WHERE id_mappa=?"; 
	private final String SQL_ALL_MAP_RICERCA = "SELECT * FROM u_admin_mappa_ricerca";
	private final String SQL_FIND_MAP_RICERCA = "SELECT * FROM u_admin_mappa_ricerca WHERE id = ?";
	private final String SQL_FIND_MAP_RICERCA_JOIN = "SELECT r.* FROM public.u_admin_mappa_mapricerca AS j JOIN public.u_admin_mappa AS m ON j.id_mappa = m.id "
			+ "                                                                                            JOIN public.u_admin_mappa_ricerca AS r ON j.id_mapricerca = r.id WHERE m.id = ?";


	public void insertRicercaJoin(List<RicercaBean> ricerca) {

		logger.debug("InsertJoinRicerca");

		Long idMappa = null;
		
		List<Object[]> batchInsert = new ArrayList<>();
		
		for(RicercaBean i : ricerca) {
			
			idMappa = i.getId_mappa();
			
			if(i.getId_mapricerca() > 0) {
				batchInsert.add(new Object[] { i.getId_mappa(),
		   				   					   i.getId_mapricerca() });
			}
			
		}

		if(idMappa != null) {
			getJdbcTemplate().update(SQL_DELETE_ALL_JOIN_MAP_RICERCA, idMappa);
		}
		
		if(batchInsert.size() > 0) {
			getJdbcTemplate().batchUpdate(SQL_INSERT_JOIN_MAP_RICERCA, batchInsert);		
		}
	
	}

	public MappaRicerca getMapRicerca(Integer id) {
		logger.debug("getMapRicerca");
		return (MappaRicerca) getJdbcTemplate().queryForObject(SQL_FIND_MAP_RICERCA, new MappaRicercaRowMapper(), id);
	}

	public List<MappaRicerca> getAllMapRicerca() {
		logger.debug("getAllMapRicerca");
		return getJdbcTemplate().query(SQL_ALL_MAP_RICERCA, new MappaRicercaRowMapper());
	}

	public List<MappaRicerca> getMappaID(Integer idMappa) {
		logger.debug("getMappaID");
		return getJdbcTemplate().query(SQL_FIND_MAP_RICERCA_JOIN, new MappaRicercaRowMapper(), idMappa);
	}

}