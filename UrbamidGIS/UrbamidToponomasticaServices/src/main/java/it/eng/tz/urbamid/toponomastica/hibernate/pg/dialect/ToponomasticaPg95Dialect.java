package it.eng.tz.urbamid.toponomastica.hibernate.pg.dialect;
import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.PostgresUUIDType;

public class ToponomasticaPg95Dialect extends PostgreSQL95Dialect {

	public ToponomasticaPg95Dialect() {
		super();
		this.registerHibernateType(
	            Types.OTHER, PostgresUUIDType.class.getName()
	        );
	}

}