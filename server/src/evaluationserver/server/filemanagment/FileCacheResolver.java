package evaluationserver.server.filemanagment;

import evaluationserver.server.entities.File;

public interface FileCacheResolver {
	boolean cache(File file);
}
