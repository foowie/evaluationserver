package evaluationserver.server.execution;

import evaluationserver.server.compile.CompilerResolver;
import evaluationserver.server.filemanagment.FileManager;
import evaluationserver.server.sandbox.SandboxResolver;
import evaluationserver.server.datasource.DataSource;
import evaluationserver.server.datasource.DataSourceBlockingQueue;
import evaluationserver.server.inspection.Inspector;
import java.util.logging.Logger;

public class ServerImpl implements Server {
	private static final Logger logger = Logger.getLogger(ServerImpl.class.getPackage().getName());	

	protected final DataSource dataSource;
	protected final SandboxResolver sandboxResolver;
	protected final CompilerResolver compilerResolver;
	protected final FileManager fileManager;
	protected final DataSourceBlockingQueue solutions;
	protected final Inspector inspector;
	protected final int threadCount;
	
	public ServerImpl(DataSource dataSource, SandboxResolver sandboxFactoryResolver, CompilerResolver compilerResolver, FileManager fileManager, Inspector inspector, int threadCount) {
		this.dataSource = dataSource;
		this.sandboxResolver = sandboxFactoryResolver;
		this.compilerResolver = compilerResolver;
		this.fileManager = fileManager;
		solutions = new DataSourceBlockingQueue(dataSource);
		this.inspector = inspector;
		this.threadCount = threadCount > 0 ? threadCount : Runtime.getRuntime().availableProcessors();;
	}
	
	public ServerImpl(DataSource dataSource, SandboxResolver sandboxFactoryResolver, CompilerResolver compilerResolver, FileManager fileManager, Inspector inspector) {
		this(dataSource, sandboxFactoryResolver, compilerResolver, fileManager, inspector, -1);
	}

	@Override
	public void runServer() {
		for(int i = 0; i < threadCount; i++) {
			Thread worker = new SolutionWorker(dataSource, sandboxResolver, compilerResolver, fileManager, solutions, inspector);
//			worker.setDaemon(true);
			worker.setName("Worker-" + (i+1));
			worker.start();
		}
	}

}
