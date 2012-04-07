package evaluationserver.server.filemanagment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CachingFileManager extends FileManagerImpl {
	private static final Logger logger = Logger.getLogger(CachingFileManager.class.getPackage().getName());	

	protected FileCacheResolver resolver;
	
	public CachingFileManager(String tempPath, FileCacheResolver resolver) {
		super(tempPath);
		this.resolver = resolver;
	}

	/**
	 * If file entity contains data, creates new file and fill it
	 * If file entity contains reference of file, return file showing to that file
	 * If file is from "file.getData()", and should be cached by resolver, it is cached
	 * @param file Entity where-to-get data
	 * @return File with data
	 * @throws IOException 
	 */
	@Override
	public File createFile(evaluationserver.server.entities.File file, Long tag) throws IOException {
		if(file == null)
			throw new NullPointerException("File can't be null !");
		
		if(file.getPath() == null && file.getData() != null && resolver.cache(file)) {
			final String fileName = formatFileName(file);
			final File result = new File(tempPath + File.separator + fileName);
			if(!result.exists()) {
//				if(!result.canWrite())
//					throw new IOException("Can't write into temp file " + result.getAbsolutePath());
				logger.log(Level.FINER, ("Creating new cached file with data " + result.getAbsolutePath()));
				
				FileOutputStream fos = new FileOutputStream(result);
				fos.write(file.getData().getFileData());
				fos.flush();
				fos.close();
			}
			return result;
		} else
			return super.createFile(file, tag);
	}
	
	protected String formatFileName(evaluationserver.server.entities.File file) {
		int dotIndex = file.getName() == null ? -1 : file.getName().lastIndexOf(".");
		String ext = dotIndex == -1 ? "" : file.getName().substring(dotIndex);		
		return file.getId() + "-" + file.getCreated().getTime() + ext;
	}
	
	
}
