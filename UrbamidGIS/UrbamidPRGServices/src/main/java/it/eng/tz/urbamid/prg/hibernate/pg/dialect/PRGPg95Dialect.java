package it.eng.tz.urbamid.prg.hibernate.pg.dialect;
import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

public class PRGPg95Dialect extends PostgreSQL95Dialect {

	public PRGPg95Dialect() {
		super();
		this.registerHibernateType(
	            Types.OTHER, PostgresUUIDType.class.getName()
	        );
	}

}