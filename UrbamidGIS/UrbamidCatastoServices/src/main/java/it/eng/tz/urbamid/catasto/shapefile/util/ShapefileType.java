package it.eng.tz.urbamid.catasto.shapefile.util;

import org.springframework.util.StringUtils;


public enum ShapefileType {
	
	ACQUE("u_cat_acque"),
	CONFINE("u_cat_confine"),
	FABBRICATI("u_cat_fabbricati"),
	FIDUCIALI("u_cat_fiduciali"),
	LINEE("u_cat_linee"),
	PARTICELLE("u_cat_particelle"),
	SIMBOLI("u_cat_simboli"),
	STRADE("u_cat_strade"),
	TESTI("u_cat_testi");
	
	private String table;
	
	private ShapefileType(String table) {
		this.table = table;
	}

	public String table() {
		return table;
	}
	
	public static ShapefileType fromString(String shapefileType) {
		ShapefileType t = null;
		if(StringUtils.hasText(shapefileType)) {
			for(ShapefileType st : ShapefileType.values()) {
				if(st.name().equalsIgnoreCase(shapefileType)) {
					t = st;
					break;
				}
			}
		}
		return t;
	}

}
