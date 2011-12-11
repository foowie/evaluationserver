package evaluationserver.server.filemanagment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManagerImpl implements FileManager {
	private static final Logger logger = Logger.getLogger(FileManagerImpl.class.getPackage().getName());	

	/**
	 * Set of temporary created files to cleanup
	 */
	private final Set<File> files = new HashSet<File>();
	/**
	 * Path where-to create new files
	 */
	protected final String tempPath;

	public FileManagerImpl(String tempPath) {
		this.tempPath = tempPath;
	}

	/**
	 * Creates empty file with unique name in temp
	 * @return Empty file
	 * @throws IOException 
	 */
	@Override
	public File createFile() throws IOException {
		final File file = getFreeFile(null);
		logger.log(Level.FINER, ("Create empty file " + file.getAbsolutePath()));
		return file;
	}

	/**
	 * If file entity contains data, creates new file and fill it
	 * If file entity contains reference of file, return file showing to that file
	 * @param file Entity where-to-get data
	 * @return File with data
	 * @throws IOException 
	 */
	@Override
	public File createFile(evaluationserver.server.entities.File file) throws IOException {
		if(file == null)
			throw new NullPointerException("File can't be null !");
		if(file.getPath() != null) {
			final File result = new File(file.getPath());
			logger.log(Level.FINER, ("Get pointer of existing file " + result.getAbsolutePath()));
			if(!result.exists())
				throw new IOException("File " + result.getAbsolutePath() + " doesn't exists !");
			return result;
		}
		if(file.getData() != null) {
			final File result = getFreeFile(file.getName()); // todo
			logger.log(Level.FINER, ("Creating new file with data " + result.getAbsolutePath()));
			FileOutputStream fos = new FileOutputStream(result);
			fos.write(file.getData());
			fos.flush();
			fos.close();
			return result;
		}
		return null;
	}

	/**
	 * If given file is managed by this object, delete it
	 * @param file 
	 */
	@Override
	public void releaseFile(File file) {
		if(file == null)
			return;
		synchronized(files) {
			if(files.contains(file)) {
				logger.log(Level.FINER, ("Release file " + file.getAbsolutePath()));
				file.delete(); // TODO
				files.remove(file);
			}
		}
	}
	
	protected File getFreeFile(String ext) {
		File file;
		synchronized(files) {
			do {
				String name = UUID.randomUUID().toString() + (ext == null ? "" : ext);
				file = new File(tempPath + File.separator + name);
			} while(files.contains(file) || file.exists());
			files.add(file);
		}
		return file;
	}
}
