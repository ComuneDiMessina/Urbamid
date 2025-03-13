package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class GSEntryDB implements Serializable {

	private static final long serialVersionUID = -1512564350693744629L;
	
	public static final String KEY_DB = "JDBC_VIRTUAL_TABLE";
	
	@SerializedName(value = "@key")
	private String key;
	
	private GSVirtualTable virtualTable;

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public GSVirtualTable getVirtualTable() {
		return virtualTable;
	}

	public void setVirtualTable(GSVirtualTable virtualTable) {
		this.virtualTable = virtualTable;
	}
}
