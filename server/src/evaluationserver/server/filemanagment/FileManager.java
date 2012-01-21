package evaluationserver.server.filemanagment;

import java.io.IOException;

public interface FileManager {
	java.io.File createFile(Long tag) throws IOException;
	java.io.File createFile(evaluationserver.server.entities.File file, Long tag) throws IOException;
	void releaseFile(java.io.File file);
	void releaseFiles(long tag);
}
