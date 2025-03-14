package it.eng.tz.urbamid.webgis.dao.model;

public class LayerAttribute implements java.io.Serializable {

	private static final long serialVersionUID = 3467989111284292050L;

	private String columnName;
	private String dataType;

	public LayerAttribute() {
	}

	public LayerAttribute(String columnName, String dataType) {
		super();
		this.columnName = columnName;
		this.dataType = dataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
