package evaluationserver.server.execution;

import evaluationserver.server.compile.CompilerResolver;
import evaluationserver.server.filemanagment.FileManager;
import evaluationserver.server.sandbox.SandboxFactoryResolver;
import evaluationserver.server.datasource.DataSource;
import evaluationserver.server.datasource.DataSourceBlockingQueue;
import java.util.logging.Logger;

public class ServerImpl implements Server {
	private static final Logger logger = Logger.getLogger(ServerImpl.class.getPackage().getName());	

	protected final DataSource dataSource;
	protected final SandboxFactoryResolver sandboxFactoryResolver;
	protected final CompilerResolver compilerResolver;
	protected final FileManager fileManager;
	protected final DataSourceBlockingQueue solutions;

	public ServerImpl(DataSource dataSource, SandboxFactoryResolver sandboxFactoryResolver, CompilerResolver compilerResolver, FileManager fileManager) {
		this.dataSource = dataSource;
		this.sandboxFactoryResolver = sandboxFactoryResolver;
		this.compilerResolver = compilerResolver;
		this.fileManager = fileManager;
		solutions = new DataSourceBlockingQueue(dataSource);
	}

	@Override
	public void runServer() {
		for(int i = 0; i < 2; i++) {
			Thread worker = new SolutionWorker(dataSource, sandboxFactoryResolver, compilerResolver, fileManager, solutions);
//			worker.setDaemon(true);
			worker.setName("Worker-" + (i+1));
			worker.start();
		}
	}

}
