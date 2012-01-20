package evaluationserver.server.filemanagment;

import evaluationserver.server.entities.File;

public class DummyFileCacheResolver implements FileCacheResolver{

	@Override
	public boolean cache(File file) {
		return true;
	}
	
}
