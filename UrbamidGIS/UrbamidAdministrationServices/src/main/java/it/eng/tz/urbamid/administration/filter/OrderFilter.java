package it.eng.tz.urbamid.administration.filter;

import java.io.Serializable;

public class OrderFilter implements Serializable {

	private static final long serialVersionUID = 6480437280737652536L;

	private String column;
	private String dir;
	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
}
