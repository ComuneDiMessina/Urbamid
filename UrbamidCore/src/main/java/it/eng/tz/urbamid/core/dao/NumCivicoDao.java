package it.eng.tz.urbamid.core.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.core.dao.mapper.NumCivicoRowMapper;
import it.eng.tz.urbamid.webgis.dao.model.NumCivico;

/**
 * @author Rocco Russo
 */
@Repository
public class NumCivicoDao extends GenericDao<NumCivico> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String SQL_ALL_NUMBERS = "SELECT * FROM geo_numcivico";
	private final String SQL_FIND_NUMBERS_BY_STREET = "SELECT * FROM geo_numcivico where codStrada = ? order by numero, esponente is not null";
	private final String SQL_INSERT_NUMBER = "INSERT INTO geo_numcivico  (codStrada, numero, esponente, sysref, latitudine, longitudine) VALUES "
			+ "( ?, ?, ?, ?, ?, ?)";
	
	
	public List<NumCivico> getNumberbyStreet(String streetCode) {
		logger.debug("getStreet");
		return getJdbcTemplate().query(SQL_FIND_NUMBERS_BY_STREET, new NumCivicoRowMapper(), streetCode);
	}
	
	
	/*
	 * CREATE TABLE table_10 (id integer PRIMARY KEY, other text NOT NULL);
	 * CREATE SEQUENCE table_10_id_seq OWNED BY table_10.id;
	 * ALTER TABLE table_10 ALTER id SET DEFAULT nextval('table_10_id_seq');
	 */
	
	/*
	 * delete from geo_numcivico where id > 11
	 */
	
	public int insertCivico(NumCivico numCivico) {
		logger.debug("insertCivico");
		return getJdbcTemplate().update(SQL_INSERT_NUMBER, numCivico.getCodStrada(), numCivico.getNumero(), numCivico.getEsponente(), 
				numCivico.getSysRef(), numCivico.getLatitudine(), numCivico.getLongitudine());
	}
	
}
