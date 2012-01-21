package evaluationserver.server.filemanagment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManagerImpl implements FileManager {
	private static final Logger logger = Logger.getLogger(FileManagerImpl.class.getPackage().getName());	

	/**
	 * Set of temporary created files to cleanup
	 */
	protected final Map<File, Set<Long>> files = new HashMap<File, Set<Long>>();
	protected final Map<Long, Set<File>> tags = new TreeMap<Long, Set<File>>();
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
			throw new IllegalStateException("Temp folder is not directory");
		if(!temp.canWrite())
			throw new IllegalStateException("Temp folder is not writable");
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
			FileOutputStream fos = new FileOutputStream(result);
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
			if(files.containsKey(file)) {
				logger.log(Level.FINER, ("Release file " + file.getAbsolutePath()));
				unregisterFile(file);
			}
		}
	}

	@Override
	public void releaseFiles(long tag) {
		synchronized(files) {
			if(tags.containsKey(tag)) {
				logger.log(Level.FINER, ("Release files with tag " + tag));
				unregisterFiles(tag);
			}
		}
	}	
	
	protected File getFreeFile(String ext, Long tag) {
		File file;
		synchronized(files) {
			do {
				String name = UUID.randomUUID().toString() + (ext == null ? "" : ext);
				file = new File(tempPath + File.separator + name);
			} while(files.containsKey(file) || file.exists());
			registerFile(file, tag);
		}
		return file;
	}
	
	protected void registerFile(File file, long tag) {
		if(!files.containsKey(file))
			files.put(file, new TreeSet<Long>());
		if(!tags.containsKey(tag))
			tags.put(tag, new HashSet<File>());
			
		if(!files.get(file).contains(tag))
			files.get(file).add(tag);
		if(tags.containsKey(tag) && !tags.get(tag).contains(file))
			tags.get(tag).add(file);
	}
	
	protected void unregisterFile(File file) {
		if(files.containsKey(file)) { // todo: only when contains?
			for(long tag : files.get(file)) {
				if(tags.get(tag).size() > 1)
					tags.get(tag).remove(file);
				else
					tags.remove(tag);
			}
			files.remove(file);
			file.delete();
		}
	}
	
	protected void unregisterFiles(long tag) {
		if(tags.containsKey(tag)) {
			for(File file : tags.get(tag)) {
				if(files.get(file).size() > 1)
					files.get(file).remove(tag);
				else {
					files.remove(file);
					file.delete();
				}
			}
			tags.remove(tag);
		}
	}

}
