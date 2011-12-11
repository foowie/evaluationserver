package evaluationserver.server.filemanagment;

import java.io.IOException;

public interface FileManager {
	java.io.File createFile() throws IOException;
	java.io.File createFile(evaluationserver.entities.File file) throws IOException;
	void releaseFile(java.io.File file);
}
