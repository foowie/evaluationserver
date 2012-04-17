package evaluationserver.server.dbdatasource;

import evaluationserver.server.entities.File;
import evaluationserver.server.filemanagment.FileCacheResolver;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

public class TypeFileCacheResolver implements FileCacheResolver {
	private static final Logger logger = Logger.getLogger(TypeFileCacheResolver.class.getPackage().getName());

	protected final EntityManager em;

	public TypeFileCacheResolver(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * Cache all other files then solutions
	 * @param file
	 * @return 
	 */
	@Override
	public boolean cache(File file) {
		Long count;
		synchronized(em) {
			count = (Long)em.createQuery("SELECT COUNT(s) FROM Solution s WHERE s.file=:file")
					.setParameter("file", file)
					.getSingleResult();
		}
		return count == 0;
	}
	
}
