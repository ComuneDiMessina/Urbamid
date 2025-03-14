package it.eng.tz.urbamid.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("");
		Properties props = new Properties();
		props.setProperty("user", "");
		props.setProperty("password", "");
		return DriverManager.getConnection("", props);
	}

}
