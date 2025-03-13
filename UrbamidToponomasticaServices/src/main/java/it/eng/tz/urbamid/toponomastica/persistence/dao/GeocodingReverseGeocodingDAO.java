package it.eng.tz.urbamid.toponomastica.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.dao.GenericDao;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.GeocodingReverseGeocoding;

@Repository
public class GeocodingReverseGeocodingDAO extends GenericDao<GeocodingReverseGeocoding>{

	/** LOGGER **/
	private static final Logger LOGGER = LoggerFactory.getLogger(GeocodingReverseGeocoding.class.getName());
	private static final String START = "START >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero risultati: {}";
	private static final String END = "END >>> {}";
	private static final String ERROR = "ERROR {} >>> {}";

	/** SQL **/
	private static final String SQL_COUNT_GEOCODING_TOPONIMO = "SELECT COUNT(*) FROM public.u_topo_toponimo_geocoding WHERE id_toponimo = ? AND id_accesso IS NULL";
	private static final String SQL_COUNT_GEOCODING_ACCESSO_NOT_NULL = "SELECT COUNT(*) FROM public.u_topo_toponimo_geocoding WHERE id_toponimo = ? AND id_accesso IS NOT NULL";
	private static final String SQL_COUNT_GEOCODING_ACCESSO = "SELECT COUNT(*) FROM public.u_topo_toponimo_geocoding WHERE id_accesso = ?";
	private static final String SQL_INSERT_GEOCODING_TOPONIMO = "INSERT INTO public.u_topo_toponimo_geocoding(id, comune_label, denominazione_ufficiale, shape_leng, cap, compendi, precdenomi, quartiere, id_comune, note, codice, id_padre, stato, data_fine_validita, geom, id_toponimo)\r\n" + 
																"SELECT nextval('u_topo_toponimo_geocoding_gid_seq'), toponimo.comune_label, toponimo.denominazione_ufficiale, toponimo.shape_leng, toponimo.cap, toponimo.compendi, toponimo.precdenomi, toponimo.quartiere, toponimo.id_comune, toponimo.note, toponimo.codice, toponimo.id_padre, toponimo.stato, toponimo.data_fine_validita, toponimo.geom, toponimo.id\r\n" + 
																"FROM public.u_topo_toponimo_stradale AS toponimo\r\n" + 
																"WHERE toponimo.id = ?";
	private static final String SQL_UPDATE_GEOCODING_TOPONIMO = "UPDATE public.u_topo_toponimo_geocoding AS geocoding\r\n" + 
																"SET comune_label=toponimo.comune_label, denominazione_ufficiale=toponimo.denominazione_ufficiale, shape_leng=toponimo.shape_leng, cap=toponimo.cap, compendi=toponimo.compendi, precdenomi=toponimo.precdenomi, quartiere=toponimo.quartiere, id_comune=toponimo.id_comune, note=toponimo.note, codice=toponimo.codice, id_padre=toponimo.id_padre, stato=toponimo.stato, data_fine_validita=toponimo.data_fine_validita, geom=toponimo.geom, id_toponimo=toponimo.id\r\n" + 
																"FROM (SELECT *\r\n" + 
																"	  FROM public.u_topo_toponimo_stradale\r\n" + 
																"	  WHERE id = ?) AS toponimo\r\n" + 
																"WHERE geocoding.id_toponimo = toponimo.id";
	private static final String SQL_DELETE_GEOCODING_TOPONIMO = "DELETE FROM public.u_topo_toponimo_geocoding WHERE id_toponimo = ?";

	private static final String SQL_INSERT_GEOCODING_NUMCIVICI = "INSERT INTO public.u_topo_toponimo_geocoding(id, comune_label, denominazione_ufficiale, cap, compendi, precdenomi, quartiere, id_comune, note, codice, id_padre, stato, data_fine_validita, geom, id_toponimo, id_accesso)\r\n" + 
															   "SELECT nextval('u_topo_toponimo_geocoding_gid_seq'), toponimo.comune_label, CONCAT(toponimo.denominazione_ufficiale, ', ', accesso.numero, accesso.esponente), toponimo.cap, toponimo.compendi, toponimo.precdenomi, toponimo.quartiere, toponimo.id_comune, toponimo.note, toponimo.codice, toponimo.id_padre, toponimo.stato, toponimo.data_fine_validita, accesso.geom, toponimo.id, accesso.id\r\n" + 
															   "FROM public.u_topo_toponimo_stradale AS toponimo JOIN public.u_topo_accesso AS accesso ON toponimo.id = accesso.toponimo_stradale\r\n" + 
															   "WHERE toponimo.id = ?";
	private static final String SQL_INSERT_GEOCODING_ACCESSO = "INSERT INTO public.u_topo_toponimo_geocoding(id, comune_label, denominazione_ufficiale, cap, compendi, precdenomi, quartiere, id_comune, note, codice, id_padre, stato, data_fine_validita, geom, id_toponimo, id_accesso)\r\n" + 
			   												   "SELECT nextval('u_topo_toponimo_geocoding_gid_seq'), toponimo.comune_label, CONCAT(toponimo.denominazione_ufficiale, ', ', accesso.numero, accesso.esponente), toponimo.cap, toponimo.compendi, toponimo.precdenomi, toponimo.quartiere, toponimo.id_comune, toponimo.note, toponimo.codice, toponimo.id_padre, toponimo.stato, toponimo.data_fine_validita, accesso.geom, toponimo.id, accesso.id\r\n" + 
			   												   "FROM public.u_topo_toponimo_stradale AS toponimo JOIN public.u_topo_accesso AS accesso ON toponimo.id = accesso.toponimo_stradale\r\n" + 
			   												   "WHERE accesso.id = ?";
	private static final String SQL_UPDATE_GEOCODING_ACCESSO = "UPDATE public.u_topo_toponimo_geocoding AS geocoding\r\n" + 
															   "SET comune_label=strada.comune_label, denominazione_ufficiale=CONCAT(strada.denominazione_ufficiale, ', ', strada.numero, strada.esponente), cap=strada.cap, shape_leng=null, compendi=strada.compendi, precdenomi=strada.precdenomi, quartiere=strada.quartiere, id_comune=strada.id_comune, note=strada.note, codice=strada.codice, id_padre=strada.id_padre, stato=strada.stato, data_fine_validita=strada.data_fine_validita, geom=strada.geom\r\n" + 
															   "FROM (SELECT toponimo.id AS idToponimo, toponimo.comune_label, toponimo.denominazione_ufficiale, accesso.numero, accesso.esponente, toponimo.cap, toponimo.compendi, toponimo.precdenomi, toponimo.quartiere, toponimo.id_comune, toponimo.note, toponimo.codice, toponimo.id_padre, toponimo.stato, toponimo.data_fine_validita, accesso.geom, accesso.id AS idAccesso\r\n" + 
															   "	  FROM public.u_topo_toponimo_stradale AS toponimo JOIN public.u_topo_accesso AS accesso ON toponimo.id = accesso.toponimo_stradale\r\n" + 
															   "  	  WHERE toponimo.id = ?) AS strada \r\n" + 
															   "WHERE geocoding.id_toponimo = strada.idToponimo AND geocoding.id_accesso = strada.idAccesso";
	private static final String SQL_DELETE_GEOCODING_ACCESSO = "DELETE FROM public.u_topo_toponimo_geocoding WHERE id_accesso = ?";
	
	public void insertGeocodingToponimo(Long idToponimo, boolean isNumCivici) throws Exception {
		String idLog = "insertGeocodingToponimo";
		LOGGER.info(START, idLog);

		try {
			
			int countGeocoding = countGeocodingToponimo(idToponimo);
			int countGeocodingAccessoNotNull = countGeocodingAccessoNotNull(idToponimo);
			
			if(isNumCivici) {
				
				if(countGeocoding == 0 && countGeocodingAccessoNotNull != 0) {
					
					getJdbcTemplate().update(SQL_INSERT_GEOCODING_TOPONIMO, idToponimo);
				} else if(countGeocoding == 0) {
					getJdbcTemplate().update(SQL_INSERT_GEOCODING_TOPONIMO, idToponimo);
					
					int risultatiInsertNumCivici = getJdbcTemplate().update(SQL_INSERT_GEOCODING_NUMCIVICI, idToponimo);
					LOGGER.debug(DEBUG_INFO_END, "insertGecodingAccesso", risultatiInsertNumCivici);
				
				} else {
					getJdbcTemplate().update(SQL_UPDATE_GEOCODING_TOPONIMO, idToponimo);
					
					int risultatiUpdateNumCivici = getJdbcTemplate().update(SQL_UPDATE_GEOCODING_ACCESSO, idToponimo);
					LOGGER.debug(DEBUG_INFO_END, idLog, risultatiUpdateNumCivici);
				}
			} else {
				
				if(countGeocoding == 0) {
					int risultatiInsert = getJdbcTemplate().update(SQL_INSERT_GEOCODING_TOPONIMO, idToponimo);
					LOGGER.debug(DEBUG_INFO_END, idLog, risultatiInsert);
				
				} else {
					int risultatiUpdate = getJdbcTemplate().update(SQL_UPDATE_GEOCODING_TOPONIMO, idToponimo);
					LOGGER.debug(DEBUG_INFO_END, idLog, risultatiUpdate);
				
				}
			}
			
		} catch (Exception e) {

			String message = "Errore nel inserimento del toponimo stradale:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}

	}

	public void deleteGeocodingToponimo(Long idToponimo) throws Exception {
		String idLog = "deleteGeocodingToponimo";
		LOGGER.info(START, idLog);

		try {

			int risultatiDelete = getJdbcTemplate().update(SQL_DELETE_GEOCODING_TOPONIMO, idToponimo);
			LOGGER.debug(DEBUG_INFO_END, idLog, risultatiDelete);
		} catch (Exception e) {

			String message = "Errore nella cancellazione del toponimo stradale:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}

	}
	
	public void insertGeocodingAccesso(Long idToponimo, Long idAccesso) throws Exception {
		String idLog = "insertGeocodingAccesso";
		LOGGER.info(START, idLog);

		try {
			
			int countGeocoding = countGeocodingToponimo(idToponimo);
			int countGeocodingAccesso = countGeocodingAccesso(idAccesso);
			
			if(countGeocoding == 0) {
				getJdbcTemplate().update(SQL_INSERT_GEOCODING_TOPONIMO, idToponimo);
					
				int risultatiInsertNumCivici = getJdbcTemplate().update(SQL_INSERT_GEOCODING_NUMCIVICI, idToponimo);
				LOGGER.debug(DEBUG_INFO_END, idLog, risultatiInsertNumCivici);
			} else if(countGeocodingAccesso == 0) {
			
				
				int risultatiInsertNumCivici = getJdbcTemplate().update(SQL_INSERT_GEOCODING_ACCESSO, idAccesso);
				LOGGER.debug(DEBUG_INFO_END, idLog, risultatiInsertNumCivici);
			} else {
				int risultatiUpdateNumCivici = getJdbcTemplate().update(SQL_UPDATE_GEOCODING_ACCESSO, idToponimo);
				LOGGER.debug(DEBUG_INFO_END, idLog, risultatiUpdateNumCivici);
			
			}
				
			
		} catch (Exception e) {

			String message = "Errore nell'inserimento del numero civico:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}
		
	}
	
	public void deleteGeocodingAccesso(Long idAccesso) throws Exception {
		String idLog = "deleteGeocodingAccesso";
		LOGGER.info(START, idLog);

		try {

			int risultatiDelete = getJdbcTemplate().update(SQL_DELETE_GEOCODING_ACCESSO, idAccesso);
			LOGGER.debug(DEBUG_INFO_END, idLog, risultatiDelete);
		} catch (Exception e) {

			String message = "Errore nella cancellazione del numero civico:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}

	}
	
	public int countGeocodingToponimo(Long idToponimo) throws Exception{
		String idLog = "selectGeocoding";
		LOGGER.info(START, idLog);

		try {

			return getJdbcTemplate().query(SQL_COUNT_GEOCODING_TOPONIMO, new ResultSetExtractor<Integer>() {

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					if(rs.next()) {
						return rs.getInt(1);
					} else {
						return 0;
					}
				}
				
			}, idToponimo);
			
		} catch (Exception e) {

			String message = "Errore nella select del numero civico:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}
		
	}
	
	public int countGeocodingAccesso(Long idAccesso) throws Exception{
		String idLog = "selectGeocoding";
		LOGGER.info(START, idLog);

		try {

			return getJdbcTemplate().query(SQL_COUNT_GEOCODING_ACCESSO, new ResultSetExtractor<Integer>() {

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					if(rs.next()) {
						return rs.getInt(1);
					} else {
						return 0;
					}
				}
				
			}, idAccesso);
			
		} catch (Exception e) {

			String message = "Errore nella select del numero civico:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}
		
	}

	public int countGeocodingAccessoNotNull(Long idToponimo) throws Exception{
		String idLog = "selectGeocoding";
		LOGGER.info(START, idLog);

		try {

			return getJdbcTemplate().query(SQL_COUNT_GEOCODING_ACCESSO_NOT_NULL, new ResultSetExtractor<Integer>() {

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					if(rs.next()) {
						return rs.getInt(1);
					} else {
						return 0;
					}
				}
				
			}, idToponimo);
			
		} catch (Exception e) {

			String message = "Errore nella select del numero civico:" + e.getMessage();
			LOGGER.error(ERROR, idLog, e.getMessage());
			throw new Exception(message);
		} finally {

			LOGGER.info(END, idLog);
		}
		
	}
	
	public List<GeocodingReverseGeocoding> rGeocoding(String lat, String lon, String distance){
		
		logger.debug("rGeocoding");
		
		String SQL_SELECT_FIND_BY_NEAR_POINT =  
				"SELECT  id, comune_label, denominazione_ufficiale, shape_leng, cap, compendi, precdenomi, quartiere, id_comune, note, codice, id_padre, stato, data_fine_validita, ST_AsText(geom) As geom, id_toponimo, id_accesso\n"
				  + "FROM public.u_topo_toponimo_geocoding\n"
				  + "WHERE round(CAST(ST_DistanceSphere(ST_Centroid(geom), ST_GeomFromText('POINT(%LAT% %LON%)',4326)) As numeric),2) < %DISTANCE%";
		SQL_SELECT_FIND_BY_NEAR_POINT = SQL_SELECT_FIND_BY_NEAR_POINT.replace("%LAT%", lat).replace("%LON%", lon).replace("%DISTANCE%", distance);
		
		return getJdbcTemplate().query(SQL_SELECT_FIND_BY_NEAR_POINT, new RowMapper<GeocodingReverseGeocoding>() {

			@Override
			public GeocodingReverseGeocoding mapRow(ResultSet rs, int rowNum) throws SQLException {				
				GeocodingReverseGeocoding geocodingReverseGeocoding = new GeocodingReverseGeocoding();
				
				Comuni comune = new Comuni();
				comune.setIdComune(rs.getLong("id_comune"));
				
				geocodingReverseGeocoding.setId(rs.getLong("id"));
				geocodingReverseGeocoding.setComuneLabel(rs.getString("comune_label"));
				geocodingReverseGeocoding.setDenominazioneUfficiale(rs.getString("denominazione_ufficiale"));
				geocodingReverseGeocoding.setShapeLeng(rs.getString("shape_leng"));
				geocodingReverseGeocoding.setCap(rs.getString("cap"));
				geocodingReverseGeocoding.setCompendi(rs.getString("compendi"));
				geocodingReverseGeocoding.setPrecdenomi(rs.getString("precdenomi"));
				geocodingReverseGeocoding.setQuartiere(rs.getString("quartiere"));
				geocodingReverseGeocoding.setGeom(rs.getString("geom"));
				geocodingReverseGeocoding.setComune(comune);
				geocodingReverseGeocoding.setNote(rs.getString("note"));
				geocodingReverseGeocoding.setStato(rs.getString("stato"));
				geocodingReverseGeocoding.setCodice(rs.getString("codice"));
				geocodingReverseGeocoding.setIdPadre(rs.getLong("id_padre"));
				
				return geocodingReverseGeocoding;
			}
		});
	}

}
