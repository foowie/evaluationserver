package evaluationserver.server.filemanagment;

import evaluationserver.server.util.Tags;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManagerImpl implements FileManager {
	private static final Logger logger = Logger.getLogger(FileManagerImpl.class.getPackage().getName());	

	/**
	 * Temporary created files to cleanup
	 */
	protected final Tags<File, Long> files = new Tags<File, Long>();
	/**
	 * Path where-to create new files
	 */
	protected final String tempPath;

	public FileManagerImpl(String tempPath) {
		this.tempPath = tempPath;

		prepareTemp(new File(tempPath));
	}
	
	private void prepareTemp(File temp) {
		if(!temp.exists())
			if(!temp.mkdirs())
				throw new IllegalStateException("Can't create temp folder");
		if(!temp.isDirectory())
			throw new IllegalStateException("Temp folder is not directory: " + temp.getAbsolutePath());
		if(!temp.canWrite())
			throw new IllegalStateException("Temp folder is not writable: " + temp.getAbsolutePath());
	}

	/**
	 * Get empty file with unique name in temp (dont create it)
	 * @return Empty file
	 * @throws IOException 
	 */
	@Override
	public File createFile(Long tag) throws IOException {
		final File file = getFreeFile(null, tag);
		logger.log(Level.FINER, ("Create empty file " + file.getAbsolutePath() + " tag=" + tag));
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
	public File createFile(evaluationserver.server.entities.File file, Long tag) throws IOException {
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
			final File result = getFreeFile(file.getName(), tag);
			final boolean isCreated = result.createNewFile();
			if(!isCreated || !result.canWrite())
				throw new IOException("Can't write into temp file " + result.getAbsolutePath());
			
			logger.log(Level.FINER, ("Creating new file with data " + result.getAbsolutePath() + " tag=" + tag));
			final FileOutputStream fos = new FileOutputStream(result);
			fos.write(file.getData().getFileData());
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
			if(files.containsValue(file)) {
				logger.log(Level.FINER, ("Release file " + file.getAbsolutePath()));
				files.removeByValue(file);
				file.delete();
			}
		}
	}

	@Override
	public void releaseFiles(long tag) {
		synchronized(files) {
			if(files.containsTag(tag)) {
				final StringBuilder sb = new StringBuilder("Release files with tag ").append(tag);
				final Set<File> delete = files.removeByTag(tag);
				for(File file : delete) {
					file.delete();
					sb.append("\n   ").append(file.getAbsolutePath());
				}
				logger.log(Level.FINER, sb.toString());
			}
		}
	}	
	
	protected File getFreeFile(String fileName, Long tag) {
		final String ext = getExtenxion(fileName);
		File file;
		synchronized(files) {
			do {
				String name = UUID.randomUUID().toString() + ext;
				file = new File(tempPath + File.separator + name);
			} while(files.containsValue(file) || file.exists());
			files.add(file, tag);
		}
		return file;
	}

	protected String getExtenxion(String fileName) {
		final int dotIndex = fileName == null ? -1 : fileName.lastIndexOf(".");
		return dotIndex == -1 ? "" : fileName.substring(dotIndex);
	}
	
}
