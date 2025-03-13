package it.eng.tz.urbamid.core.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Class that wrap a {@link java.sql.Connection} instance
 * @author Antonio La Gatta
 * @date 28 lug 2017
 * @version 1.0
 */
public class ConnectionWrapper implements Connection{

	/**
	 * Connection wrapped
	 */
	private Connection conn = null;
	/**
	 * 
	 * @param conn. Is connection to wrap
	 */
	public ConnectionWrapper(Connection conn) {
		if(conn == null)
			throw new IllegalArgumentException("Conn can't be null");
		this.conn = conn;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.conn.unwrap(iface);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.conn.isWrapperFor(iface);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createStatement()
	 */
	@Override
	public Statement createStatement() throws SQLException {
		return this.conn.createStatement();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareStatement(java.lang.String)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.conn.prepareStatement(sql);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareCall(java.lang.String)
	 */
	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.conn.prepareCall(sql);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#nativeSQL(java.lang.String)
	 */
	@Override
	public String nativeSQL(String sql) throws SQLException {
		return this.conn.nativeSQL(sql);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setAutoCommit(boolean)
	 */
	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.conn.setAutoCommit(autoCommit);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getAutoCommit()
	 */
	@Override
	public boolean getAutoCommit() throws SQLException {
		return this.conn.getAutoCommit();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#commit()
	 */
	@Override
	public void commit() throws SQLException {
		this.conn.commit();			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		this.conn.rollback();			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#close()
	 */
	@Override
	public void close() throws SQLException {
		this.conn.close();			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		return this.conn.isClosed();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getMetaData()
	 */
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return this.conn.getMetaData();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		this.conn.setReadOnly(readOnly);			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() throws SQLException {
		return this.conn.isReadOnly();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setCatalog(java.lang.String)
	 */
	@Override
	public void setCatalog(String catalog) throws SQLException {
		this.conn.setCatalog(catalog);			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getCatalog()
	 */
	@Override
	public String getCatalog() throws SQLException {
		return this.conn.getCatalog();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setTransactionIsolation(int)
	 */
	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		this.conn.setTransactionIsolation(level);
		
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getTransactionIsolation()
	 */
	@Override
	public int getTransactionIsolation() throws SQLException {
		return this.conn.getTransactionIsolation();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.conn.getWarnings();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
		this.conn.clearWarnings();			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createStatement(int, int)
	 */
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.conn.createStatement(resultSetType, resultSetConcurrency);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
	 */
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getTypeMap()
	 */
	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.conn.getTypeMap();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setTypeMap(java.util.Map)
	 */
	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.conn.setTypeMap(map);			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setHoldability(int)
	 */
	@Override
	public void setHoldability(int holdability) throws SQLException {
		this.conn.setHoldability(holdability);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getHoldability()
	 */
	@Override
	public int getHoldability() throws SQLException {
		return this.conn.getHoldability();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setSavepoint()
	 */
	@Override
	public Savepoint setSavepoint() throws SQLException {
		return this.conn.setSavepoint();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setSavepoint(java.lang.String)
	 */
	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return this.conn.setSavepoint(name);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		this.conn.rollback(savepoint);			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
	 */
	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.conn.releaseSavepoint(savepoint);			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createStatement(int, int, int)
	 */
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return this.conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
	 */
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return this.conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return this.conn.prepareStatement(sql, autoGeneratedKeys);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return this.conn.prepareStatement(sql, columnIndexes);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return this.conn.prepareStatement(sql, columnNames);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createClob()
	 */
	@Override
	public Clob createClob() throws SQLException {
		return this.conn.createClob();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createBlob()
	 */
	@Override
	public Blob createBlob() throws SQLException {
		return this.conn.createBlob();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createNClob()
	 */
	@Override
	public NClob createNClob() throws SQLException {
		return this.conn.createNClob();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createSQLXML()
	 */
	@Override
	public SQLXML createSQLXML() throws SQLException {
		return this.conn.createSQLXML();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#isValid(int)
	 */
	@Override
	public boolean isValid(int timeout) throws SQLException {
		return this.conn.isValid(timeout);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		this.conn.setClientInfo(name, value);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setClientInfo(java.util.Properties)
	 */
	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		this.conn.setClientInfo(properties);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getClientInfo(java.lang.String)
	 */
	@Override
	public String getClientInfo(String name) throws SQLException {
		return this.conn.getClientInfo(name);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getClientInfo()
	 */
	@Override
	public Properties getClientInfo() throws SQLException {
		return this.conn.getClientInfo();
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return this.conn.createArrayOf(typeName, elements);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return this.conn.createStruct(typeName, attributes);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setSchema(java.lang.String)
	 */
	@Override
	public void setSchema(String schema) throws SQLException {
		this.conn.setSchema(schema);			
	}

	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getSchema()
	 */
	@Override
	public String getSchema() throws SQLException {
		return this.conn.getSchema();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#abort(java.util.concurrent.Executor)
	 */
	@Override
	public void abort(Executor executor) throws SQLException {
		this.conn.abort(executor);			
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#setNetworkTimeout(java.util.concurrent.Executor, int)
	 */
	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		this.conn.setNetworkTimeout(executor, milliseconds);			
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Connection#getNetworkTimeout()
	 */
	@Override
	public int getNetworkTimeout() throws SQLException {
		return this.conn.getNetworkTimeout();
	}
}
