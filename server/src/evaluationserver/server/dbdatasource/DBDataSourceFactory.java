package evaluationserver.server.dbdatasource;

import evaluationserver.server.datasource.DataSource;
import evaluationserver.server.datasource.DataSourceFactory;
import javax.persistence.EntityManagerFactory;

/**
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class DBDataSourceFactory implements DataSourceFactory {

	private EntityManagerFactory factory;

	public DBDataSourceFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}

	@Override
	public DataSource createDataSource() {
		return new DBDataSource(factory.createEntityManager());
	}
}
