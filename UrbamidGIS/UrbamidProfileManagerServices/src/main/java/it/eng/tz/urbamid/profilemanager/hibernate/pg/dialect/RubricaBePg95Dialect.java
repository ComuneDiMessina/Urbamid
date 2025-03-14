package it.eng.tz.urbamid.profilemanager.hibernate.pg.dialect;
import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

public class RubricaBePg95Dialect extends PostgreSQL95Dialect {

	public RubricaBePg95Dialect() {
		super();
		this.registerHibernateType(
	            Types.OTHER, PostgresUUIDType.class.getName()
	        );
	}

}