package it.eng.tz.urbamid.web.filter;

import java.io.Serializable;

public class OrderFilter implements Serializable {

	private static final long serialVersionUID = -7104243046823102703L;

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
