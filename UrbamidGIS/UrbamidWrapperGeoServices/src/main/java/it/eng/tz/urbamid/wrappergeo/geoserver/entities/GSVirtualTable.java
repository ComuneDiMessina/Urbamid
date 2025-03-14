package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSVirtualTable implements Serializable {

	private static final long serialVersionUID = 84602244062498869L;

	private String name;
	private String sql;
	private boolean escapeSql;
	private GSGeometry geometry;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public boolean isEscapeSql() {
		return escapeSql;
	}
	public void setEscapeSql(boolean escapeSql) {
		this.escapeSql = escapeSql;
	}
	public GSGeometry getGeometry() {
		return geometry;
	}
	public void setGeometry(GSGeometry geometry) {
		this.geometry = geometry;
	}
	
}
