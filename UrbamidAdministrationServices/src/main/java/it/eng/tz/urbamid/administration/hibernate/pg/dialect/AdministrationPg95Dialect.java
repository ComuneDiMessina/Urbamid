package it.eng.tz.urbamid.administration.hibernate.pg.dialect;
import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

public class AdministrationPg95Dialect extends PostgreSQL95Dialect {

	public AdministrationPg95Dialect() {
		super();
		this.registerHibernateType(
	            Types.OTHER, PostgresUUIDType.class.getName()
	        );
	}

}