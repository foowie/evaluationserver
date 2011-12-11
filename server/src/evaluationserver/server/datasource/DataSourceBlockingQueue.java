package evaluationserver.server.datasource;

import evaluationserver.entities.Solution;
import evaluationserver.server.notification.Notifiable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSourceBlockingQueue implements BlockingQueue<Solution>, Notifiable {
	private static final Logger logger = Logger.getLogger(DataSource.class.getPackage().getName());	

	protected final DataSource dataSource;

	public DataSourceBlockingQueue(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	@Override
	public void notification() {
		logger.log(Level.FINER, "Notification received !");
		synchronized(dataSource) {
			dataSource.notifyAll();
		}
	}
	
	// Not supported due one-way data access
	
	@Override
	public boolean add(Solution e) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean offer(Solution e) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public void put(Solution e) throws InterruptedException {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean offer(Solution e, long l, TimeUnit tu) throws InterruptedException {
		throw new UnsupportedOperationException("Not supported.");
	}

	// Remove element methods

	@Override
	public Solution remove() {
		throw new UnsupportedOperationException("Not supported yet.");
	}	
	
	@Override
	public Solution poll() {
		throw new UnsupportedOperationException("Not supported yet.");
	}	
	
	@Override
	public Solution take() throws InterruptedException {
		Solution solution;
		
		synchronized(dataSource) {
			while((solution = dataSource.takeNextUnresolvedSolution()) == null) {
				dataSource.wait();
			}
			return solution;
		}
	}

	@Override
	public Solution poll(long l, TimeUnit tu) throws InterruptedException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	// Examine

	@Override
	public Solution element() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Solution peek() {
		throw new UnsupportedOperationException("Not supported yet.");
	}	
	
	// Rest
	
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Not supported yet.");
	}	
	
	@Override
	public int remainingCapacity() {
		return Integer.MAX_VALUE;
	}


	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public int drainTo(Collection<? super Solution> clctn) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public int drainTo(Collection<? super Solution> clctn, int i) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Iterator<Solution> iterator() {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public <T> T[] toArray(T[] ts) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean containsAll(Collection<?> clctn) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean addAll(Collection<? extends Solution> clctn) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean removeAll(Collection<?> clctn) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean retainAll(Collection<?> clctn) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Not supported.");
	}

}
