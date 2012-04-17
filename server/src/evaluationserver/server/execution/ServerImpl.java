package evaluationserver.server.execution;

import evaluationserver.server.compile.CompilerResolver;
import evaluationserver.server.filemanagment.FileManager;
import evaluationserver.server.sandbox.SandboxResolver;
import evaluationserver.server.datasource.DataSource;
import evaluationserver.server.datasource.DataSourceBlockingQueue;
import evaluationserver.server.datasource.DataSourceFactory;
import evaluationserver.server.inspection.Inspector;
import evaluationserver.server.notification.NotificatorList;
import java.util.logging.Logger;

public class ServerImpl implements Server {
	private static final Logger logger = Logger.getLogger(ServerImpl.class.getPackage().getName());	

	protected final DataSourceFactory dataSourceFactory;
	protected final SandboxResolver sandboxResolver;
	protected final CompilerResolver compilerResolver;
	protected final FileManager fileManager;
	protected final Inspector inspector;
	protected final NotificatorList notificators;
	protected final int threadCount;
	
	public ServerImpl(DataSourceFactory dataSourceFactory, SandboxResolver sandboxFactoryResolver, CompilerResolver compilerResolver, FileManager fileManager, Inspector inspector, NotificatorList notificators, int threadCount) {
		this.dataSourceFactory = dataSourceFactory;
		this.sandboxResolver = sandboxFactoryResolver;
		this.compilerResolver = compilerResolver;
		this.fileManager = fileManager;
		this.inspector = inspector;
		this.notificators = notificators;
		this.threadCount = (threadCount > 0 && threadCount < 1000) ? threadCount : Runtime.getRuntime().availableProcessors();
	}
	
	@Override
	public void runServer() {
		for(int i = 0; i < threadCount; i++) {
			DataSource ds = dataSourceFactory.createDataSource();
			DataSourceBlockingQueue queue = new DataSourceBlockingQueue(ds);
			notificators.addNotifiable(queue);
			Thread worker = new SolutionWorker(ds, sandboxResolver, compilerResolver, fileManager, queue, inspector);
//			worker.setDaemon(true);
			worker.setName("Worker-" + (i+1));
			worker.start();
		}
		notificators.runNotificators();
	}

}
