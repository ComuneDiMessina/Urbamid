package it.eng.tz.urbamid.core.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

/**
 * Class that wrap a {@link DataSource} instance
 * @author Antonio La Gatta
 * @date 28 lug 2017
 * @version 1.0
 */
public class DataSourceWrapper implements DataSource{
	
	/**
	 * Data source wrapped
	 */
	private DataSource ds;
	/**
	 * @param ds. Is datasource to wrap
	 */
	public DataSourceWrapper(DataSource ds) {
		if(this.ds == null)
			throw new IllegalArgumentException("ds can't be null");
		this.ds = ds;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.ds.getLogWriter();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.ds.setLogWriter(out);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.ds.setLoginTimeout(seconds);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		return this.ds.getLoginTimeout();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.ds.getParentLogger();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.ds.unwrap(iface);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.ds.isWrapperFor(iface);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.DataSource#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = new ConnectionWrapper(this.ds.getConnection());
		conn.setAutoCommit(false);
		return conn;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 28 lug 2017
	 * @version 1.0
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		Connection conn = new ConnectionWrapper(this.ds.getConnection(username, password));
		conn.setAutoCommit(false);
		return conn;
	}
}
