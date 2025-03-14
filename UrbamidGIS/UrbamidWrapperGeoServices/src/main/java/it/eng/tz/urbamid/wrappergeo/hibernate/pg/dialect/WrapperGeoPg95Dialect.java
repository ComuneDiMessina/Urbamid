package it.eng.tz.urbamid.wrappergeo.hibernate.pg.dialect;
import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

public class WrapperGeoPg95Dialect extends PostgreSQL95Dialect {

	public WrapperGeoPg95Dialect() {
		super();
		this.registerHibernateType(
	            Types.OTHER, PostgresUUIDType.class.getName()
	        );
	}

}