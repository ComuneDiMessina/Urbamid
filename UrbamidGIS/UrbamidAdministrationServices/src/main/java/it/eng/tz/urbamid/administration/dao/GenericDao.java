package it.eng.tz.urbamid.administration.dao;

import java.sql.Connection;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 * @author Alessandro Paolillo
 */
public abstract class GenericDao<T> extends NamedParameterJdbcDaoSupport {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected Connection nativeConn = null;

	public static final String SQL_MAX_DATE = "SELECT TO_TIMESTAMP('31.12.2099 23:59:59', 'dd.mm.yyyy hh24:mi:ss') FROM dual";

	/**
	 * Data source
	 */
	@Autowired
	@Qualifier("datasource")
	private DataSource dataSource;


	/**
	 * @author Alessandro Paolillo
	 */
	@PostConstruct
	private void init()
	{
		this.setDataSource(dataSource);
		//getJdbcTemplate().setNativeJdbcExtractor(extractor);
		this.getJdbcTemplate().setFetchSize(50000);
	}

	
public Integer getNewId(String sequenceName) { 
	
	return this.getJdbcTemplate().queryForObject("SELECT nextval('"+sequenceName+"');", Integer.class);
}	
	
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected Connection getOracleConnection() throws SQLException
//	{
//		return this.dataSource.getConnection().unwrap(OracleConnection.class);
//	}
//
//	// Get the native connection
//	protected Connection getNativeConn() throws SQLException
//	{
//		if ((nativeConn == null) || nativeConn.isClosed())
//		{
//			nativeConn = this.getJdbcTemplate().getNativeJdbcExtractor().getNativeConnection(getOracleConnection());
//		}
//		return nativeConn;
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected T namedExtract(String sql, Map<String, Object> namedParameters, ResultSetExtractor<T> rse)
//	{
//		if (namedParameters != null)
//			return getNamedParameterJdbcTemplate().query(sql, namedParameters, rse);
//		return getNamedParameterJdbcTemplate().query(sql, rse);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> namedExtract(String sql, Map<String, Object> namedParameters, RowMapper<T> rowMapper)
//	{
//		return getNamedParameterJdbcTemplate().query(sql, namedParameters, rowMapper);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected T extract(String sql, Object[] parameters, ResultSetExtractor<T> rse)
//	{
//		return this.getJdbcTemplate().query(sql, parameters, rse);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> list(String sql, Map<String, Object> namedParameters, RowMapper<T> rowMapper)
//	{
//		return this.getNamedParameterJdbcTemplate().query(sql, namedParameters, rowMapper);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> list(String sql, RowMapper<T> rowMapper, Object... args)
//	{
//		return this.getJdbcTemplate().query(sql, rowMapper, args);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> list(String sql, Class<T> classe)
//	{
//		return this.getJdbcTemplate().queryForList(sql, classe);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<Map<String, Object>> list(String sql, List<Object> parameters)
//	{
//		if (parameters == null)
//			return this.list(sql, (Object[]) null);
//		return this.list(sql, parameters.toArray());
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<Map<String, Object>> list(String sql, Object[] parameters)
//	{
//		if (parameters == null)
//			return this.getJdbcTemplate().queryForList(sql);
//		return this.getJdbcTemplate().queryForList(sql, parameters);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> list(String sql, Class<T> classe, Object... args)
//	{
//		return this.getJdbcTemplate().queryForList(sql, classe, args);
//	}
//
	/**
	 * @author Alessandro Paolillo
	 */
	protected T singleRow(String sql, Object[] args, RowMapper<T> rowMapper)
	{
		try
		{
			return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
		}
		catch (EmptyResultDataAccessException e)
		{
			logger.error("Error in singleRow. No data found", e);
			return null;
		}
	}
//
//	protected Map<String, Object> singleRow(String sql, Object[] args)
//	{
//		try
//		{
//			return this.getJdbcTemplate().queryForMap(sql, args);
//		}
//		catch (EmptyResultDataAccessException e)
//		{
//			logger.error("Error in singleRow. No data found", e);
//			return null;
//		}
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected T singleRow(String sql, RowMapper<T> rowMapper, Object... args)
//	{
//		try
//		{
//			return this.getJdbcTemplate().queryForObject(sql, rowMapper, args);
//		}
//		catch (EmptyResultDataAccessException e)
//		{
//			logger.error("Error in singleRow. No data found", e);
//			return null;
//		}
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected T singleRow(String sql, Class<T> requiredType, Object... args)
//	{
//		try
//		{
//			return this.getJdbcTemplate().queryForObject(sql, requiredType, args);
//		}
//		catch (EmptyResultDataAccessException e)
//		{
//			logger.error("Error in singleRow. No data found", e);
//			return null;
//		}
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected T singleRow(String sql, Class<T> requiredType)
//	{
//		try
//		{
//			return this.getJdbcTemplate().queryForObject(sql, requiredType, new Object[] {});
//		}
//		catch (EmptyResultDataAccessException e)
//		{
//			logger.error("Error in singleRow. No data found", e);
//			return null;
//		}
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	public int update(String sql, Object... args)
//	{
//		return this.getJdbcTemplate().update(sql, args);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected int update(String sql, List<Object> args)
//	{
//		return this.update(sql, ListUtil.asArray(args));
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> paginateExtractorList(String sql, Object[] parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		ResultSetPaginateExtractor<T> extractor = new ResultSetPaginateExtractor<T>(start, end, rowMapper);
//		return this.getJdbcTemplate().query(sql, parameters, extractor);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> paginateExtractorList(String sql, List<Object> parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		return this.paginateExtractorList(sql, parameters == null ? null : ListUtil.asArray(parameters), rowMapper, start, end);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> paginateSqlList(String sql, Object[] parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		sql += " OFFSET " + start + " ROWS FETCH NEXT " + (end - start) + " ROWS ONLY";
//		return this.getJdbcTemplate().query(sql, parameters, rowMapper);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected List<T> paginateSqlList(String sql, List<Object> parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		return this.paginateSqlList(sql, parameters == null ? null : ListUtil.asArray(parameters), rowMapper, start, end);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected TPagingList<T> paginateAndCountExtractorList(String sql, Object[] parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		List<T> list = this.paginateExtractorList(sql, parameters, rowMapper, start, end);
//		int count = this.countFromSql(sql, parameters);
//		return new TPagingList<>(list, count);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected TPagingList<T> paginateAndCountExtractorList(String sql, List<Object> parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		return this.paginateAndCountExtractorList(sql, parameters == null ? null : ListUtil.asArray(parameters), rowMapper, start, end);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected TPagingList<T> paginateAndCountSqlList(String sql, Object[] parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		List<T> list = this.paginateSqlList(sql, parameters, rowMapper, start, end);
//		int count = this.countFromSql(sql, parameters);
//		return new TPagingList<>(list, count);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	protected TPagingList<T> paginateAndCountSqlList(String sql, List<Object> parameters, RowMapper<T> rowMapper, int start, int end)
//	{
//		return this.paginateAndCountSqlList(sql, parameters == null ? null : ListUtil.asArray(parameters), rowMapper, start, end);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	private Integer countFromSql(String sql, Object[] parameters)
//	{
//		int indexOf = sql.toUpperCase().indexOf("FROM");
//		sql = "select count(*) " + sql.substring(indexOf);
//		List<Integer> countList = this.getJdbcTemplate().queryForList(sql, Integer.class, parameters);
//		if (ListUtil.isEmpty(countList))
//			return 0;
//		return countList.get(0);
//	}
//
//	/**
//	 * @author Alessandro Paolillo
//	 */
//	public T callStoredProcedure(SqlParameterSource inParams, Class<T> elementType, String operationName, String packageName) throws Exception
//	{
//
//		SimpleJdbcCall call = null;
//		JdbcTemplate jdbcTemplate = getJdbcTemplate();
//		jdbcTemplate.setResultsMapCaseInsensitive(true);
//
//		call = new SimpleJdbcCall(jdbcTemplate).withFunctionName(operationName).withCatalogName(packageName);
//		return call.executeFunction(elementType, inParams);
//
//	}
}
