package it.eng.tz.urbamid.core.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * Extractor for paginate query
 * @author Antonio La Gatta
 * @date 04 mag 2017
 * @version 1.0
 * @param <T>
 */
public class ResultSetPaginateExtractor<T> implements ResultSetExtractor<List<T>>{

	/**
	 * idx start based on -1 strategy
	 */
	private int start;
	/**
	 * idx end based on -1 strategy
	 */
	private int end;
	/**
	 * Row mapper to cast {@link ResultSet} in bean
	 */
	private RowMapper<T> rowmapper;

	public ResultSetPaginateExtractor(int start, int end, RowMapper<T> rowmapper) {
		super();
		this.start     = start;
		this.end       = end;
		this.rowmapper = rowmapper;
	}



	/**
	 * @author Antonio La Gatta
	 * @date 04 mag 2017
	 * @version 1.0
	 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
	 */
	@Override
	public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<T> list = new ArrayList<T>();
		int idx = 0;
		while(rs.next()
		&& idx <= this.end
		){
			if(idx >= this.start)
				list.add(this.rowmapper.mapRow(rs, idx));
			idx++;
		}
		return list;
	}
}
